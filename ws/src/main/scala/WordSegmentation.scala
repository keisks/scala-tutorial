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
    var model = mutable.Map[String, Float]() //weightMap

    // load model
    for (line <- modelFile.getLines()){
      val word_prob = line.stripLineEnd split '\t'
      val word = word_prob(0)
      val prob = word_prob(1).toDouble

      model.update(word, prob)
    }

    // 
    // viterbi algorithm
    for (line <- inputFile.getLines()){

      // Forward step
      val sentence = line.stripLineEnd

      val best_score = mutable.ListBuffer[Double]()
      val best_edge = mutable.ListBuffer[Int]()

      for (word_end <- 1 until sentence.length){
        best_score += 99999.0d

        for (word_begin <- 0 to word_end-1) {
          string = line.substring(word_begin, word_end)

          if model.contains(string) 



        }


      }


     




    }


    
  }

}

