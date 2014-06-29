package org.falcon.main

import org.falcon.streaming.Collector
import org.falcon.util.Util

/**
 * Project: falcon
 * Package: org.falcon.main
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
object Main {
  def main(args: Array[String]): Unit = {
    CLIParser.parse(args, Configuration()) map { config => run(config) }
  }

  def run(config: Configuration) = {
    println("========================================")
    println("                 falcon                 ")
    println("========================================")
    println()

    Util.loadConfiguration(config)
    val collector = new Collector()
    collector.collect
  }
}
