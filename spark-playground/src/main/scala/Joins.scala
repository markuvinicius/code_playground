package com.markuvinicius

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

object Joins{

  val guitarsPathData = "src/main/resources/data/guitars"
  val guitarPlayersPathData = "src/main/resources/data/guitarPlayers"
  val bandsPathData = "src/main/resources/data/bands"

  val spark = setupSparkSession()

  private def setupSparkSession() = {
    System.getProperties.getProperty("os.name") match {
      case "Windows 10" => System.setProperty("hadoop.home.dir", "C:\\Users\\marku\\.hadoop_home")
    }

    SparkSession.builder()
      .appName("Streaming Counter")
      .master("local[2]")
      .getOrCreate()
  }

  def readDataFrame(path:String) = {
    spark.read
      .format("json")
      .option("inferSchema",true)
      .load(path)
  }

  def main(args: Array[String]): Unit = {
    val spark = setupSparkSession()

    val guitarsDF = readDataFrame(guitarsPathData)
    val bandsDF = readDataFrame(bandsPathData)
    val guitarPlayersDF = readDataFrame(guitarPlayersPathData)

    val joinCondition = guitarPlayersDF.col("band") === bandsDF.col("id")

    val guitaristsWithBandsDF = guitarPlayersDF.join(bandsDF, joinCondition, "inner")


    guitaristsWithBandsDF.show()




  }

}


