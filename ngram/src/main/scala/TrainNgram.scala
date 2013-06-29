object TrainNgram {

  val N = 3

  def main(args: Array[String]): Unit = {
    import scala.io._
    import scala.collection.mutable
    import java.io.PrintWriter

    //create map counts, context_counts, unique count for Witten-Bell smoothing
    val countMap: mutable.Map[String, Int] = mutable.Map()
    val contextMap: mutable.Map[String, Int] = mutable.Map()
    val uniqueMap: mutable.Map[String, Int] = mutable.Map()

    //get filePath
    val arglist = args.toList
    val filePath = arglist(0)
             
    //open a file
    val source = Source.fromFile(filePath, "UTF-8")

    for(line <- source.getLines()) {
      val words_org = line.stripLineEnd split ' '

      // add BOS and EOS
      val words = "<s>" +: words_org :+ "</s>"

      // see the words between [j to i]
      for(i <- 1 until words.length-1) {
        for (j <- 0 to i) {     // j should not exceed i

          // check if N is greater than i-j
          // to limit the words exactly [j to i] rather [0 to i]
          if (N > i-j) {
            val r = List(N, i-j+1).min // get minimum length of Ngram
            println(i.toString + " " + j.toString)
            val ngramlist = words.take(i+1).takeRight(r)

            val counts = ngramlist.mkString(" ")
            var context_counts = ""

            // get context_counts except unigram
            if (ngramlist.length >= 2) {
              context_counts = ngramlist.init.mkString(" ")
            }
            
            println(counts)
            println(context_counts)

            // Add countMap
            if (countMap.contains(counts)) {
              countMap.update(counts, countMap(counts)+1)
            } else {
              countMap += counts -> 1

              // Add uniqueMap
              if (uniqueMap.contains(context_counts)) {
                uniqueMap.update(context_counts, uniqueMap(context_counts)+1)
              } else {
                uniqueMap += context_counts -> 1
              }
            }

            // Add contextMap
            if (contextMap.contains(context_counts)){
              contextMap.update(context_counts, contextMap(context_counts)+1)
            } else {
              contextMap += context_counts -> 1
            }
          }
        }
      }
    }

    //open the model_file for writing
    val probModel = new PrintWriter("ngram_prob.model")
    val lambdaModel = new PrintWriter("ngram_lambda.model")

    // get sorted key list
    val sortedKeys = countMap.keys.toList.sorted

    sortedKeys.foreach {ngram => 
      //for probModel
      val word_array = ngram split ' '
      val context_array = ngram split ' ' dropRight 1
      val context = context_array.mkString("")

      val probability = countMap(ngram).toFloat/contextMap(context).toFloat
      //println(f"$ngram\t$probability%.6f")   
      probModel.println(f"$ngram\t$probability%.6f")   

      //for lambdaModel
      val lambda = 1 - ( uniqueMap(ngram).toFloat / (uniqueMap(ngram).toFloat + countMap(ngram).toFloat))
      lambdaModel.println(f"$ngram\t$lambda%.6f")
    }

    probModel.close()
    lambdaModel.close()
    source.close()
  }
}

