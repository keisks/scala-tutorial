object WordSegmentation {
  def main(args: Array[String]): Unit = {
    import scala.io._
    import scala.collection.mutable
    import java.io.PrintWriter
    
    val arglist = args.toList
    val modelPath = arglist(0)
    val inputPath = arglist(1)

    // open a file
    val modelFile = Source.fromFile(modelPath, "UTF-8")
    val inputFile = Source.fromFile(inputPath, "UTF-8")

    // create map w
    var model = mutable.Map[String, Double]() //weightMap

    // load model
    for (line <- modelFile.getLines()){
      val word_prob = line.stripLineEnd split '\t'
      val word = word_prob(0)
      val prob = word_prob(1).toDouble

      model.update(word, prob)
    }

    // viterbi algorithm
    for (line <- inputFile.getLines()){

      // Forward step
      val sentence = line.stripLineEnd

      val best_score = mutable.ListBuffer[Double](0.0)
      val best_edge = mutable.ListBuffer((-1, 0))

      for (word_end <- 1 to sentence.length){
        best_score += 99999.0d
        best_edge += ((word_end-1, word_end))

        for (word_begin <- 0 to word_end-1) {
          val string = line.substring(word_begin, word_end)

          if (model.contains(string) || string.length == 1){
            val probability = model(string)
            val tmp_score = best_score(word_begin) - (math.log(probability)/math.log(2))

            if (tmp_score < best_score(word_end)) {
              best_score(word_end) = tmp_score
              best_edge(word_end) = ((word_begin, word_end))
            }
          }
          //println(word_begin)
          //println(word_end)
          //println(best_score)
          //println(best_edge)
        }
      }
      
      // Backward step



     




    }


    
  }

}

