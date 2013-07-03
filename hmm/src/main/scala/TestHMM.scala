object TestHMM {

  /////////////////////
  // Constant values //
  /////////////////////

  // for emission probability smoothing
  val lambda = 0.95 
  val N = 1000000
  // N.B. You may apply lambda values obtained by Witten-Bell Smoothing etc.

  /////////////////////

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

    // create map for transition, emission, and possible_tag
    var transMap = mutable.Map[String, Double]()
    var emitMap = mutable.Map[String, Double]()
    var possibleTag = mutable.Map[String, Int]()

    // load model
    for (line <- modelFile.getLines()){
      val tmp = line.stripLineEnd split '\t'
      val transEmit = tmp(0)
      val contextWord = tmp(1)
      val probability = tmp(2).toDouble

      val contextWordList = contextWord split " "
      val tag = contextWordList(0)

      possibleTag += tag -> 1

      if (transEmit == "T"){
        transMap += contextWord -> probability
      } else {
        emitMap += contextWord -> probability
      }
    }

    // viterbi algorithm
    for (line <- inputFile.getLines()){

      // Forward step
      val sentence = line.stripLineEnd split " "
      val len = sentence.length
      val bestScoreMap = mutable.Map[String, Double]()
      val bestEdgeMap = mutable.Map[String, String]()

      bestScoreMap += "0 <s>" -> 0
      bestEdgeMap += "0 <s>" -> "NONE"

      for (i <- 0 until len) {
        for (prev <- possibleTag.keys) {
          for (next <- possibleTag.keys) {
            val i_tag = i.toString + " " + prev
            val next_i_tag = (i+1).toString + " " + next
            val transition = prev + " " + next

            if ( bestScoreMap.contains(i_tag) && transMap.contains(transition) ) {
              //println(transMap(prev+" "+next))
              //println(emitMap(next+" "+sentence(i)))
              val transScore = -(math.log(transMap(prev+" "+next).toDouble)/math.log(2))
              //println(next + " " + sentence(i))

              // for emitScore, we need smoothing for unknown words

              var emitProbability = 0.0d
              if (emitMap.contains(next+" "+sentence(i))) {
                emitProbability = lambda*(emitMap(next+" "+sentence(i)).toDouble) + (1-lambda)/N
              } else {
                emitProbability = (1-lambda)/N
              }
              val emitScore = -(math.log(emitProbability)/math.log(2))

              // add up score
              val score = bestScoreMap(i_tag) + transScore + emitScore

              if (! bestScoreMap.contains(next_i_tag)) {
                bestScoreMap += next_i_tag -> score
                bestEdgeMap += next_i_tag -> i_tag
              } else {
                if (bestScoreMap(next_i_tag) > score) {
                    bestScoreMap.update(next_i_tag, score)
                    bestEdgeMap.update(next_i_tag, i_tag)
                }
              }
            }
          }
        }
      }
      for (prev <- possibleTag.keys) {
        val last_tag = len.toString + " </s>"
        val prev_tag = (len-1).toString + " " + prev
        val last_trans = prev + " </s>"

        if (bestScoreMap.contains(last_tag) && transMap.contains(last_trans)) {
          val lastTransScore = -(math.log(transMap(last_trans).toDouble)/math.log(2))
          val last_score = bestScoreMap(prev_tag) + lastTransScore

          if (! bestScoreMap.contains(last_tag)) {
            bestScoreMap += last_tag -> last_score
            bestEdgeMap += last_tag -> prev_tag
          } else {
            if (bestScoreMap(last_tag) > last_score) {
              bestScoreMap.update(last_tag, last_score)
              bestEdgeMap.update(last_tag, prev_tag)
            }
          }
        }
      }
      
      /*
      // Backward step
      val words = mutable.ListBuffer.empty[String]
      var next_edge = best_edge(best_edge.length-1)

      while (next_edge._1 != -1){
        val word = line.substring(next_edge._1, next_edge._2)
        words += word
        next_edge = best_edge(next_edge._1)
      }
      println(words.reverse.mkString(" "))
      */

    }
    
  }

}

