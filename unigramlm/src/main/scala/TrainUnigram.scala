object TrainUnigram {
  def main(args: Array[String]): Unit = {
    import scala.io._
    import scala.collection.mutable.Map
    import java.io.PrintWriter

    // create a map
    val countMap: Map[String, Int] = scala.collection.mutable.Map()

    // create a variable
    var totalCount = 0.0f

    // get filePath
    val arglist = args.toList
    val filePath = arglist(0)
    
    //open a file
    val source = Source.fromFile(filePath, "UTF-8")

    try {
      for(line <- source.getLines()) {
        val words_org = line.stripLineEnd split ' '

        val words = words_org :+ "</s>"

        
        for(w <- words) {
          if(countMap.contains(w)) {
            countMap.update(w, countMap(w)+1)
            totalCount += 1
          } else {
            countMap += w -> 1
            totalCount += 1
          }
        }
      }

      // get sorted key list
      val sortedKeys = countMap.keys.toList.sorted

      //open the modelFile for writing
      val outFile = new PrintWriter("unigram.model")

      sortedKeys.foreach { key =>
        val probability = countMap(key) / totalCount
        //println(f"$key\t$probability%.6f")
        outFile.println(f"$key\t$probability%.6f")
      }

      outFile.close()

    } finally {
      // close file
      source.close()
    }
  }
}
