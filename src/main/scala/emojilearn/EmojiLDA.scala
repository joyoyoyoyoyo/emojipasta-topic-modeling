package emojilearn

import org.apache.spark.mllib.clustering.{DistributedLDAModel, LDA}
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import java.time._  // val today = ZonedDateTime.now(ZoneId.of("UTC")).toString


object EmojiLDA {



  def LDA(args: Array[String]) = {
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
    val model = word2vec.fit(tokenized)
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

    def main(args: Array[String]) = {
        import org.apache.spark.mllib.clustering.LDA
        import org.apache.spark.mllib.linalg.{Vector, Vectors}
        import scala.collection.mutable

        val conf = new SparkConf().setMaster("local").setAppName("EmojiLDA")
        val sc = new SparkContext(config = conf)

        val stopWordsInput = sc.textFile(getClass.getResource("/stopwords.txt").toString)
        val stopWords = stopWordsInput.flatMap(x => x.split(",")).map(_.trim)
        val broadcastStopWords = sc.broadcast(stopWords.collect.toSet)

        val tokenized: RDD[Seq[String]] =
          // corpus.map(_.toLowerCase.split("\\s")).map(_.filter(_.length > 3).filter(_.forall(java.lang.Character.isLetter)))
          sc.textFile(getClass.getResource("/EmojiPasta/emojipastacorpus.txt").toString)
          .map(_.replaceAll("[,.!?:;]","").toLowerCase.trim.split("\\s+").filter(!broadcastStopWords.value.contains(_)))
          .map(
          _.filter(!_.isEmpty) // .flatMap(_.split("\\s+"))
        )
        // word.filter(!word.isEmpty && !broadcastStopWords.value.contains(word)) // .flatMap(_.split("\\s+"))


        val termCounts = tokenized.flatMap(_.map(_ -> 1L)).reduceByKey(_ + _).collect().sortBy(-_._2)
        val numStopwords = 0
        val vocabArray: Array[String] = termCounts.takeRight(termCounts.size - numStopwords).map(_._1)
        val vocab: Map[String, Int] = vocabArray.zipWithIndex.toMap

        // Convert documents into term count vectors
        val documents: RDD[(Long, Vector)] =
          tokenized.zipWithIndex.map { case (tokens, id) =>
            val counts = new mutable.HashMap[Int, Double]()
            tokens.foreach { term =>
              if (vocab.contains(term)) {
                val idx = vocab(term)
                counts(idx) = counts.getOrElse(idx, 0.0) + 1.0
              }
            }
            (id, Vectors.sparse(vocab.size, counts.toSeq))
          }

          // Set LDA parameters
          val numTopics = 10
          val lda = new LDA().setK(numTopics).setMaxIterations(100)

          val ldaModel = lda.run(documents)
          val avgLogLikelihood = ldaModel.asInstanceOf[DistributedLDAModel].logLikelihood / documents.count()


          // PrintWriter
          import java.io._
          val pw = new PrintWriter(new File("Results.txt" ))
          // FileWriter
          // val file = new File(canonicalFilename)
          // val bw = new BufferedWriter(new FileWriter(file))
          // bw.write(text)
          // bw.close()

          /**
           * Print results.
           */
          // Print training time
          pw.println(s"Finished training LDA model.  Summary:")
          pw.println(s"==========")

          // Print the topics, showing the top-weighted terms for each topic.
          val topicIndices = ldaModel.describeTopics(maxTermsPerTopic = 10)
          val topics = topicIndices.map { case (terms, termWeights) =>
            terms.map(vocabArray(_)).zip(termWeights)
          }
          pw.println(s"$numTopics topics:")
          topics.zipWithIndex.foreach { case (topic, i) =>
            pw.println(s"TOPIC $i")
            topic.foreach { case (term, weight) => pw.println(s"$term\t$weight") }
            pw.println(s"==========")
          }
          pw.close

          // // Save and load model.
          // ldaModel.save(sc, "target/org/apache/spark/LatentDirichletAllocationExample/LDAModel")
          // val sameModel = DistributedLDAModel.load(sc,"target/org/apache/spark/LatentDirichletAllocationExample/LDAModel")

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



        //

        // word count vectors (columns: terms [vocabulary], rows [documents])



        // emojiFrequency.saveAsTextFile(s"output/{$today}EmojiLDA.txt")
        // must stop SparkContext or SparkSession, as of version >= 2.0.0
        sc.stop()

    }
}
