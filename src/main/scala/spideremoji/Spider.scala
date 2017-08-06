package spideremoji

import io.reactors.Reactor

/**
  * Created by marblecake on 7/19/17.
  */
object Spider {

  def main(args: Array[String]) = {
    val url = "http://www.reddit.com/r/emojipasta/"
    val response = scala.io.Source.fromURL(url)
    System.out.println(response)
  }
}
