import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.functions.unix_timestamp

import emojilearn.EmojiLDA

import java.time._

object EmojiClusterAnalysis {

  def main(args: Array[String]): Unit = {

    // sparkInit("local", "EmojiClusterAnalysis", emojiClusterAnalysis _)
    sparkInit("local", "EmojiLDA", EmojiLDA.LDA _)

  }

  def emojiClusterAnalysis(sc: SparkContext)(loadFile: String): Unit = {
    val emojiFrequency = sc.textFile(loadFile)
      .flatMap(line => line.split("\\s+")) // .flatMap(_.split("\\s+"))
      .map(_.replaceAll(
      "[,.!?:;]", "") // Strip punctuation and convert to lowercase
      .trim
      .toLowerCase)
      .filter(!_.isEmpty) // Filter any empty strings
      .map(word => (word, 1))
      .reduceByKey(_ + _) // .reduceByKey{ case (x, y) => x + y }
      .sortBy(_._2, ascending = false)

    val today = ZonedDateTime.now(ZoneId.of("UTC")).toString

    // emojiFrequency.saveAsTextFile(s"output/{$today}EmojiFrequency.txt")
    // emojiFrequency.saveAsTextFile(s"transformed-output/{$today}EmojiFrequency.txt")

  }


  private def sparkInit(clusterUrl: String = "local", name: String,
    callback: org.apache.spark.SparkContext => (String => Unit)) = {
      // creates a standalone application that runs on one thread on the local machine
      val conf = new SparkConf().setMaster("local").setAppName(name)
      val sc = new SparkContext(config = conf)

      // callback(sc)(Thread.currentThread().getContextClassLoader().getResource("/EmojiPasta/emojipastacorpus.txt").toString)
      callback(sc)(getClass.getResource("/EmojiPasta/emojipastacorpus.txt").toString)

      // must stop SparkContext or SparkSession, as of version >= 2.0.0
      sc.stop()
  }
}
