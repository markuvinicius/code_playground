package com.markuvinicius
package schemas

import org.apache.spark.sql.types.{StringType, StructField, StructType}

object Schemas {

  val statusMessageSchema = StructType(Array(
    StructField("id",StringType),
    StructField("timestamp",StringType),
    StructField("user_name",StringType),
    StructField("message",StringType)
  ))

}
