object TrainPerceptron {
    import scala.io._
    import scala.collection.mutable

  def update_weights(w: mutable.Map[String, Float], phi: mutable.Map[String, Float], y: Int): mutable.Map[String, Float] = {
    phi.foreach{ e => 
      val name = e._1
      val value = e._2
      w.update(name, value*y)
    }
    w
  }

  def predict_one(w: mutable.Map[String, Float], phi: mutable.Map[String, Float]): Int = {
    var score = 0.0f
    var return_val = 0
    phi.foreach{ e => 
      val name = e._1
      val value = e._2
      if (w.contains(name)){
        score += value * w(name)
      }
      if (score >= 0){
        return_val = 1
      } else {
        return_val = -1
      }
    }
    return_val
  }


  def create_features(x: String): mutable.Map[String, Float] = {
    val featureMap = mutable.Map[String, Float]()

    // unigram feature
    val words = x.stripLineEnd split ' '
    for (word <- words){
      if(featureMap.contains(word)){
        featureMap.update(word, featureMap(word)+1.0f)
      } else {
        featureMap += word -> 1.0f
      }
    }

    // another feature comes here

    featureMap
  }


  def main(args: Array[String]): Unit = {
    
    // get filePath
    val arglist = args.toList
    val filePath = arglist(0)

    // open a file
    val source = Source.fromFile(filePath, "UTF-8")

    // create map w
    var w = mutable.Map[String, Float]() //weightMap

    // for I iteration
    for (i <- 0 to 5){

      // foreach labeled pair x, y in the data
      for(line <- source.getLines()){
        val label_words = line.stripLineEnd split '\t'
        val label = label_words(0).toInt
        //val words = label_words(1).stripLineEnd split ' '
        val words = label_words(1)

        val phi = create_features(words)
        val y_prime = predict_one(w, phi)
          

        //if y_prime != y:
          //update_weights(w, phi, y)
        if(y_prime != label){
          //println(w)
          w = update_weights(w, phi, label)
          //println(w)
        }
      }
    }

    // save w as a model file
  }

}

