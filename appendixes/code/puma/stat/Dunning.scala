package org.puma.stat

/**
 * Project: puma
 * Package: org.puma.stat
 *
 * Author: Daniel Gayo-Avello
 * Date: 02/2014
 */
object Dunning {

  def rootLogLikelihoodRatio(a: Long, b: Long, c: Long, d: Long): Double = {
    val E1 = c * ((a + b) / (c + d).toDouble)
    val E2 = d * ((a + b) / (c + d).toDouble)

    val E3 = if(a == 0) 1/E1 else a/E1
    val E4 = if(b == 0) 1/E2 else b/E2
    val result = Math.sqrt(2 * (a * Math.log(E3) + b * Math.log(E4)))

    if ((a / c.toDouble) < (b / d.toDouble)) -result else result
  }

  def normalizedRootLogLikelihoodRatio (a: Long, b: Long, c: Long, d: Long): Double = {
    val min    = rootLogLikelihoodRatio(0,d,c,d)
    val max    = rootLogLikelihoodRatio(c,0,c,d)

    val a_norm = a / c.toDouble
    val b_norm = b / d.toDouble
    val llr    = rootLogLikelihoodRatio(a,b,c,d)

    if (llr > 0) llr / (max * a_norm) else -llr / (min * b_norm)
  }

}
