package org.falcon.streaming.filter

import twitter4j.FilterQuery
import org.falcon.util.Util

/**
 * Project: falcon
 * Package: org.falcon.streaming.filter
 *
 * Author: Sergio Álvarez
 * Date: 02/2014
*/
object FilterFactory {
  def createFilterQuery: FilterQuery = new FilterQuery().language(Util.language).track(Util.keywords)
}
