object TrainBigram {

  def main(args: Array[String]): Unit = {
    import scala.io._
    import scala.collection.mutable
    import java.io.PrintWriter

    //create map counts, context_counts
    val countMap: mutable.Map[String, Int] = mutable.Map()
    val contextMap: mutable.Map[String, Int] = mutable.Map()

    //get filePath
    val arglist = args.toList
    val filePath = arglist(0)
             
    //open a file
    val source = Source.fromFile(filePath, "UTF-8")

    for(line <- source.getLines()) {
      val words_org = line.stripLineEnd split ' '
      val words = "<s>" +: words_org :+ "</s>"

      for(i <- 1 until words.length-1) {
        val counts = words(i-1) + ' ' + words(i)
        val context_counts = words(i-1)

        if (countMap.contains(counts)){
          countMap.update(counts, countMap(counts)+1)
        } else {
          countMap += counts -> 1
        }

        if (contextMap.contains(context_counts)){
          contextMap.update(context_counts, contextMap(context_counts)+1)
        } else {
          contextMap += context_counts -> 1
        }

        if (countMap.contains(words(i))){
          countMap.update(words(i), countMap(words(i))+1)
        } else {
          countMap += words(i) -> 1
        }

        if (contextMap.contains("")){
          contextMap.update("", contextMap("")+1)
        } else {
          contextMap += "" -> 1
        }
      }
    }
    
    //open the model_file for writing
    val probModel = new PrintWriter("bigram_prob.model")

    // get sorted key list
    val sortedKeys = countMap.keys.toList.sorted

    sortedKeys.foreach {ngram => 
      val word_array = ngram split ' '
      val context_array = ngram split ' ' dropRight 1
      val context = context_array.mkString("")

      val probability = countMap(ngram).toFloat/contextMap(context).toFloat
      //println(f"$ngram\t$probability%.6f")   
      probModel.println(f"$ngram\t$probability%.6f")   
    }
    probModel.close()
    source.close()
  }
}
