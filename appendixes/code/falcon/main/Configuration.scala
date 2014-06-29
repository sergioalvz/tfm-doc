package org.falcon.main

import java.io.File

case class Configuration(language:String = "es", keywords:File = null, boundingBoxes:File = null, timeMeasure:String = "SECONDS",
    timeToCollect:Int = 10, output:String = "falcon_collection.xml", coordinatesMandatory:Boolean = false, credentials:File = null) {}

object CLIParser {
  private[this] val parser = new scopt.OptionParser[Configuration]("Falcon") {
    head("Falcon", "1.0")
    opt[String]('l', "language")
      .required() action { (x, c) => c.copy(language = x) } text("Specifies the language, in ISO 639-1 format, for the tweets to collect.")
    opt[File]('k', "keywords")
      .required() action { (x, c) => c.copy(keywords = x) } text("Specifies the file for the keywords.")
    opt[String]('t', "time-in")
      .required() action { (x, c) => c.copy(timeMeasure = x) } text("The time measure for collecting tweets (SECONDS, MINUTES, HOURS, DAYS).")
    opt[Int]('n', "timestamp")
      .required() action { (x, c) => c.copy(timeToCollect = x) } text("Units of time for collecting tweets.")
    opt[String]('o', "output")
      .required() action { (x, c) => c.copy(output = x) } text("The output filename where store the collection results.")
    opt[File]('c', "credentials")
      .required() action { (x, c) => c.copy(credentials = x) } text("Properties file with the Twitter credentials")
    opt[File]('b', "bounding-boxes")
      .required() action { (x, c) => c.copy(boundingBoxes = x) } text ("Specifies the file which contains the bounding boxes.")
  }

  def parse(args:Array[String], conf:Configuration):Option[Configuration] = this.parser.parse(args, conf)
}
