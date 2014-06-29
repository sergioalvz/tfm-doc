package org.falcon.streaming

import org.falcon.writer.Writer
import org.falcon.util.Util
import org.falcon.streaming.filter.FilterFactory

/**
 * Project: falcon
 * Package: org.falcon.streaming
 *
 * Author: Sergio Álvarez
 * Date: 02/2014
 */
class Collector {
  def collect() = {
    val twitterStreaming = new TwitterStreaming
    twitterStreaming.filter(FilterFactory.createFilterQuery)
    try{
      Writer.open(Util.fileName)
      Writer.write("<tweets>\n")
      twitterStreaming.run()

      val top = System.currentTimeMillis() + Util.timeToCollect
      while(System.currentTimeMillis() <= top) {}

      twitterStreaming.close()
      Writer.write("</tweets>")
      Writer.close()
    } finally {
      Writer.close()
      twitterStreaming.close()
    }

  }
}
