package org.falcon.streaming

import twitter4j._
import org.falcon.model.Tweet
import org.falcon.writer.Writer
import org.falcon.util.Util
import java.util.{Date, TimeZone}
import java.text.SimpleDateFormat

/**
 * Project: falcon
 * Package: org.falcon.streaming
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
 class TwitterStreaming() {
  private[this] var _filter: FilterQuery = _
  private[this] var _twitter: TwitterStream = _

  def filter(filter: FilterQuery): Unit = _filter = filter

  def run() = {
    _twitter = new TwitterStreamFactory(Util.twitterConfiguration).getInstance()
    _twitter.addListener(myTwitterStatusListener)
    _twitter.filter(_filter)
  }

  def close() = if (_twitter != null) _twitter.shutdown()

  private def myTwitterStatusListener = new StatusListener {
    def onStatus(status: Status) {
      if(status.getGeoLocation == null) return;

      val id:String        = status.getId.toString
      val username:String  = status.getUser.getScreenName
      val name:String      = status.getUser.getName
      val location:String  = status.getUser.getLocation
      val timezone:String  = status.getUser.getTimeZone
      val createdAt:String = toUTC(status.getCreatedAt)
      val text:String      = status.getText
      val latitude:String  = status.getGeoLocation.getLatitude.toString
      val longitude:String = status.getGeoLocation.getLongitude.toString

      if(Util.isInBoundingBoxes(latitude, longitude)){
        val tweet = new Tweet(id, username, name, location, timezone, createdAt, latitude, longitude, text)
        Writer.write(s"\t${tweet.toXML.toString()}\n")
      }
    }

    def onStallWarning(p1: StallWarning) {}

    def onException(p1: Exception) {}

    def onDeletionNotice(p1: StatusDeletionNotice) {}

    def onScrubGeo(p1: Long, p2: Long) {}

    def onTrackLimitationNotice(p1: Int) {}

    private[this] def toUTC(date: Date): String = {
      val f = new SimpleDateFormat("yyyy-MM-dd HH:mm")
      f.setTimeZone(TimeZone.getTimeZone("UTC"))
      f.format(date)
    }
  }
}
