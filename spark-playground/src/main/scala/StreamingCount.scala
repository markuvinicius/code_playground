package com.markuvinicius

import org.apache.spark.sql.{Column, SparkSession}
import org.apache.spark.sql.functions.{avg, col, sum}

class StreamingCount(sparkSession: SparkSession) {
  def printStream() = {
    val lines = this.sparkSession.readStream
      .format("socket")
      .option("host","localhost")
      .option("port",12345)
      .load()

    lines.writeStream
      .format("console")
      .outputMode("append")
      .start()
      .awaitTermination()
  }

  def aggregateStream(aggregation: Column => Column) = {
    val lines = this.sparkSession.readStream
      .format("socket")
      .option("host","localhost")
      .option("port",12345)
      .load()

    val numericDF = lines.select(col("value").cast("integer").as("int_value"))
    val aggregatedDF = numericDF.select(aggregation(col("int_value")).as("agg_so_far"))

    aggregatedDF.writeStream
      .format("console")
      .outputMode("complete")
      .start()
      .awaitTermination()
  }

  def groupNames() = {
    val lines = this.sparkSession.readStream
      .format("socket")
      .option("host","localhost")
      .option("port",12345)
      .load()

    val names = lines
      .select( col("value").as("name"))
      .groupBy(col("name"))
      .count()

    names.writeStream
      .format("console")
      .outputMode("complete")
      .start()
      .awaitTermination()

  }
}

object StreamingCount {

  def main(args: Array[String]): Unit = {
    val streaming = new StreamingCount(setupSparkSession())
    //streaming.printStream()
    //streaming.aggregateStream(avg)
    //streaming.aggregateStream(sum)
    //streaming.aggregateStream(count)
    streaming.groupNames()
  }

  private def setupSparkSession() = {
    System.getProperties.getProperty("os.name") match {
      case "Windows 10" => System.setProperty("hadoop.home.dir", "C:\\Users\\marku\\.hadoop_home")
    }

    SparkSession.builder()
      .appName("Streaming Counter")
      .master("local[2]")
      .getOrCreate()
  }


  private def buildSparkSession = {

  }
}
