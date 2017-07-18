import org.apache.spark.{SparkConf, SparkContext}

object EmojiClusterAnalysis {

  def main(args: Array[String]): Unit = {

    sparkInit("local", "EmojiClusterAnalysis", emojiClusterAnalysis)

  }

  def emojiClusterAnalysis(): Unit = {
    ???
  }


  private def sparkInit(clusterUrl: String = "local", name: String,
    callback: () => Unit) = {
      // creates a standalone application that runs on one thread on the local machine
      val conf = new SparkConf().setMaster("local").setAppName(name)
      val sc = new SparkContext(config = conf)

      callback()

      // must stop SparkContext or SparkSession, as of version >= 2.0.0
      sc.stop()
  }
}
