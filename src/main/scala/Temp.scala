import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._


object Temp extends App {
  val browser = JsoupBrowser()
  val subreddit = browser.get("http://reddit.com/r/emojipasta")
  // Extract the <title> elements inside <p>
  val submissionsTitles = subreddit >> elementList("p.title")
  val unparsedSubmissions = subreddit >> elementList("div#siteTable.sitetable.linklisting")

  // From each submission title, extract all the text inside their <a> elements
  val submissionsTitlesText = submissionsTitles.map(_ >> allText("a"))

  println("process terminated")
}
