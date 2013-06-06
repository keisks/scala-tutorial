object Count_Freq {
  def main(args: Array[String]): Unit = {
    import scala.io._
    import scala.collection.mutable.Map

    // get filePath
    val arglist = args.toList
    val filePath = arglist(0)

    // open a file
    val source = Source.fromFile(filePath, "UTF-8")

    // create a map
    val countMap: Map[String, Int] = scala.collection.mutable.Map()

    try{

      // read each line and split line into words
      for(line <- source.getLines()){
        val words = line.stripLineEnd split ' '
        //words.foreach(println)

        for(w <- words){
          if (countMap.contains(w)){
            countMap.update(w, countMap(w)+1)
            
          } else {
            countMap += w -> 1
          }

        }
      }

      // get sorted key list
      val sortedKeys = countMap.keys.toList.sorted

      // print according to the sorted key list
      sortedKeys.foreach { key =>
        println(key + '\t' + countMap(key))
      }

    } finally {
      // close file
      source.close()
    }
  }
}
