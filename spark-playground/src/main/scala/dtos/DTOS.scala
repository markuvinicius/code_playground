package com.markuvinicius
package dtos

object DTOS {

  case class StatusMessage(id:String,
                           timeStamp:String,
                           user_name:String,
                           message:String)
}
