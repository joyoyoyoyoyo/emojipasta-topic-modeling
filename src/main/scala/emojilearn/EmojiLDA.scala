package emojilearn

import org.apache.spark.mllib.clustering.{DistributedLDAModel, LDA}
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import java.time._  // val today = ZonedDateTime.now(ZoneId.of("UTC")).toString


object EmojiLDA {



  def main(args: Array[String]) = {
    val conf = new SparkConf().setMaster("local").setAppName("EmojiLDA")

    val sc = new SparkContext(config = conf)

    val tokenized: RDD[Seq[String]] =
      // corpus.map(_.toLowerCase.split("\\s")).map(_.filter(_.length > 3).filter(_.forall(java.lang.Character.isLetter)))
      sc.textFile(getClass.getResource("/EmojiPasta/emojipastacorpus.txt").toString)
      .map(_.replaceAll("[,.!?:;]","").toLowerCase.trim.split("\\s+")).map(
      _.filter(!_.isEmpty) // .flatMap(_.split("\\s+"))
    )
      // .map(_.replaceAll(
    val documents = transformToDocuments(sc, "/EmojiPasta/emojipastacorpus.txt")
    println("TOKENIZED " + tokenized.count)
    println("DOCUMENTS " + documents.count)
    println("TOKENIZED " + tokenized.count)
    println("DOCUMENTS " + documents.count)
    println("TOKENIZED " + tokenized.count)
    println("DOCUMENTS " + documents.count)
    println(tokenized.count)
    println(tokenized.count)
    println(tokenized.count)
    println(tokenized.count)
    println(tokenized.count)

    val tokenized2: RDD[Seq[String]] =
      // corpus.map(_.toLowerCase.split("\\s")).map(_.filter(_.length > 3).filter(_.forall(java.lang.Character.isLetter)))
      sc.textFile(getClass.getResource("/EmojiPasta/emojipastacorpus.txt").toString)
      .map(_.replaceAll("[,.!?:;]","").toLowerCase.trim.split("\\s+").toSeq)
      .filter(!_.isEmpty) // .flatMap(_.split("\\s+"))
    //   // .map(_.replaceAll(
    // // val documents = transformToDocuments1(sc, "/EmojiPasta/emojipastacorpus.txt")
    println("YO: " + tokenized2.count)
    // println(documents.count)
    // println(documents.count)
    // println(documents.count)
    // println(documents.count)
    // val documents: RDD[Seq[String]]  = sc.textFile(getClass.getResource("/EmojiPasta/emojipastacorpus.txt").toString)
      // .map(_.split("\\s+").toSeq) // .flatMap(_.split("\\s+"))
      // .flatMap(line => line.split("\\s+")) // .flatMap(_.split("\\s+"))
      // .map(_.replaceAll(
      // "[,.!?:;]", "") // Strip punctuation and convert to lowercase
      // .trim
      // .toLowerCase)
      // .filter(!_.isEmpty) // Filter any empty strings
      // .toSeq
      // .reduceByKey(_ + _) // .reduceByKey{ case (x, y) => x + y }
      // .sortBy(_._2, ascending = false).

      // Vectorize words
    val word2vec = new Word2Vec()
    val model = word2vec.fit(tokenized2)
    val synonyms = model.findSynonyms("1", 5)
    for((synonym, cosineSimilarity) <- synonyms) {
      println(s"$synonym $cosineSimilarity")
    }

    //

    // word count vectors (columns: terms [vocabulary], rows [documents])



    // emojiFrequency.saveAsTextFile(s"output/{$today}EmojiLDA.txt")
    // must stop SparkContext or SparkSession, as of version >= 2.0.0
    sc.stop()
  }

  // def transformToDocuments1(sc: SparkContext, filename: String ): RDD[Seq[String]] = {
  //   val documents: RDD[Seq[String]]  = sc.textFile(getClass.getResource("/EmojiPasta/emojipastacorpus.txt").toString)
  //   .flatMap(line => line.split("\\s+")) // .flatMap(_.split("\\s+"))
  //   .map(_.replaceAll(
  //   "[,.!?:;]", "") // Strip punctuation and convert to lowercase
  //   .trim
  //   .toLowerCase)
  //   .filter(!_.isEmpty) // Filter any empty strings
  //   .map(word => (word, 1))
  //   .reduceByKey(_ + _) // .reduceByKey{ case (x, y) => x + y }
  //   .sortBy(_._2, ascending = false)
  //   documents
  // }

    def transformToDocuments(sc: SparkContext, filename: String ): RDD[Seq[String]] = {
      // Load documents (one per line).
      val documents: RDD[Seq[String]]  = sc.textFile(getClass.getResource(filename).toString)
        .map(_.split("\\s+").toSeq)
        .filter(!_.map(
          _.replaceAll(
          "[,.!?:;]", "") // Strip punctuation and convert to lowercase
          .trim
          .toLowerCase).isEmpty
          // .filter(!_.isEmpty) // Filter any empty strings
        )
        documents
    }
}
