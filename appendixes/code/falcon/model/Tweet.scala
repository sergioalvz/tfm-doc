package org.falcon.model

/**
 * Project: falcon
 * Package: org.falcon.model
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
  class Tweet(id:String, username: String, name:String, location: String, timezone: String, createdAt:String, latitude: String,
              longitude: String, text: String) {
    def toXML =
      <tweet>
        <id>
          {id}
        </id>
        <username>
          {username}
        </username>
        <name>
          {name}
        </name>
        <location>
          {location}
        </location>
        <timezone>
          {timezone}
        </timezone>
        <createdAt>
          {createdAt}
        </createdAt>
        <latitude>
          {latitude}
        </latitude>
        <longitude>
          {longitude}
        </longitude>
        <text>
          {text}
        </text>
      </tweet>
  }
