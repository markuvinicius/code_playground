package com.markuvinicius

import com.markuvinicius.dtos.DTOS.StatusMessage
import com.markuvinicius.schemas.Schemas
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession, functions}
import org.apache.spark.sql.streaming.{GroupState, GroupStateTimeout, OutputMode}

object StatefulOperations {

  val spark = SparkSession.builder()
    .appName("Stateful Operations App")
    .master("local[2]")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  def createStream(port:Long) = {
    spark.readStream
      .format("socket")
      .option("host","localhost")
      .option("port",port)
      .load()
  }

  def updateState(userName: Any, inputs: Any, oldSate: Any): Dataset[StatusMessage] = ???

  private val MappingFunction: (Long, Iterator[Row], GroupState[Seq[String]]) => Seq[String] = (key, values, state) => {
    val stateNames = state.getOption.getOrElse(Seq.empty)
    val stateNewNames = stateNames ++ values.map(row => row.getAs[String]("name"))
    state.update(stateNewNames)
    stateNewNames
  }

  def main(args: Array[String]): Unit = {
    import spark.implicits._


    val stream = createStream(12345)
    val tweetsDF = stream.select(from_json(col("value"), Schemas.statusMessageSchema)
      .as("tweet"))
      .selectExpr("tweet.*")

    val statusMessageDS = tweetsDF.as[StatusMessage]

    val groupedByUserMessagesDS = statusMessageDS.groupByKey(_.user_name)

    val maxUserIdDS = groupedByUserMessagesDS
      .mapGroups { case (username, statusMessage) => (username, statusMessage.toList.size ) }
      /*.mapGroupsWithState(GroupStateTimeout.NoTimeout())(
        ( userName:String, inputs:Iterator[StatusMessage], oldState:GroupState[Array[Byte]]) =>
    updateState(userName, inputs, oldState) )*/

      /*.mapGroupsWithState(groupStateTimeout)(
        (clusterId: String, inputs: Iterator[AnomalyCluster], oldState: GroupState[Array[Byte]]) =>
          updateAcrossEvents(clusterId, inputs, oldState, clock, appProps)*/

    printStream(maxUserIdDS)
  }


  private def printStream(stream: Dataset[(String,Int)]) = {
    stream.writeStream
      .format("console")
      .outputMode(OutputMode.Append())
      .start()
      .awaitTermination()
  }
}
