import org.apache.spark.{SparkConf, SparkContext}

object EmojiClusterAnalysis {

  def main(args: Array[String]): Unit = {

    // creates a standalone application that runs on one thread on the local machine
    val conf = new SparkConf().setMaster("local").setAppName("EmojiClusterAnalysis")
    val sc = new SparkContext(config = conf)
  }
}
