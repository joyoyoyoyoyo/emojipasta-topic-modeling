package emojilearn

import org.apache.spark.mllib.clustering.{DistributedLDAModel, LDA}
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import java.time._  // val today = ZonedDateTime.now(ZoneId.of("UTC")).toString

object EmojiLDA {

    def LDA(sc: SparkContext)(loadFile: String) = {
        import scala.collection.mutable


        val stopWordsInput = sc.textFile(getClass.getResource("/stopwords.txt").toString)
        val stopWords = stopWordsInput.flatMap(x => x.split(",")).map(_.trim)
        val broadcastStopWords = sc.broadcast(stopWords.collect.toSet)

        val tokenized: RDD[Seq[String]] = sc.textFile(loadFile)
          .map(_.replaceAll("[,.!?:;]","").toLowerCase.trim.split("\\s+").filter(!broadcastStopWords.value.contains(_)))
          .map(
          _.filter(!_.isEmpty)
        )


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




          /**
           * Print results.
           */
          import java.io._
          val pw = new PrintWriter(new File("Results.txt" ))

          pw.println(s"Finished training LDA model.  Summary:")
          pw.println(s"==========")

          // Print the topics, showing the top-weighted terms for each topic.
          val topicIndices = ldaModel.describeTopics(maxTermsPerTopic = 10)
          val topics = topicIndices.map { case (terms, termWeights) =>
            terms.map(vocabArray(_)).zip(termWeights)
          }
          pw.println(s"$numTopics topics:")
          topics.zipWithIndex.foreach { case (topic, i) =>
            pw.println(s"TOPIC ${i + 1}")
            topic.foreach { case (term, weight) => pw.println(s"$term\t$weight") }
            pw.println(s"==========")
          }
          pw.close

    }
}
