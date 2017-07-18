import org.apache.spark.{SparkConf, SparkContext}

object EmojiClusterAnalysis {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("EmojiClusterAnalysis")
    val sc = new SparkContext(conf)
  }
}
