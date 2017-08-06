package spideremoji

import scala.io.{Codec, Source}

/**
  * Created by marblecake on 7/19/17.
  */
object Spider {

  def main(args: Array[String]) = {
    val blockingURL = "http://reddit.com/r/emojipasta"
    // TODO: Adjust for blocking
    val accessibleURL = "https://en.wikipedia.org/wiki/Cat"
    // val response = scala.io.Source.fromURL(url)
    val input = new java.net.URL(accessibleURL).openStream()
    val generator =  Source.fromInputStream(input)(Codec.UTF8).getLines()
    System.out.println(generator.mkString)
  }
}
