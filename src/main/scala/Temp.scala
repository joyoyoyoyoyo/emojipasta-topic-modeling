import net.ruippeixotog.scalascraper.browser.JsoupBrowser

object Temp extends App {
  val browser = JsoupBrowser()
  val doc = browser.parseFile("src/main/scala/example.html")
  val doc2 = browser.get("http://example.com")

  val redditDoc = browser.get("http://reddit.com")
  println(redditDoc)
}
