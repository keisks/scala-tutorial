object TestPerceptron {
  import scala.io._
  import scala.collection.mutable
  import java.io.PrintWriter

  def predict_one(w: mutable.Map[String, Float], phi: mutable.Map[String, Float]): Int = {
    var score = 0.0f
    var return_val = 0
    phi.foreach{ e => 
      val name = e._1
      val value = e._2
      if (w.contains(name)){
        score += value * w(name)
      }
    }
    //println(score)
    if (score >= 0){
      return_val = 1
    } else {
      return_val = -1
    }
    return_val
  }

  def create_features(x: String): mutable.Map[String, Float] = {
    val featureMap = mutable.Map[String, Float]()

    // unigram feature
    val words = x.stripLineEnd split ' '
    for (word <- words){
      val feature_name = "UNI:" + word
      if(featureMap.contains(feature_name)){
        featureMap.update(feature_name, featureMap(feature_name)+1.0f)
      } else {
        featureMap += feature_name -> 1.0f
      }
    }

    // another feature comes here

    featureMap
  }


  def predict_all(modelFilePath:String, inputFilePath:String) {
    // open a file
    val modelFile = Source.fromFile(modelFilePath, "UTF-8")
    val inputFile = Source.fromFile(inputFilePath, "UTF-8")

    // load a map w
    var w = mutable.Map[String, Float]() //weightMap

    for(line <- modelFile.getLines()){
      val name_weight = line.stripLineEnd split '\t'
      val name = name_weight(0)
      val weight = name_weight(1).toFloat

      w.update(name, weight)
    }

    // predict for each input

    for(line <- inputFile.getLines()){
      val phi = create_features(line)
      val y = predict_one(w, phi)
      println(y)
    }
  }


  def main(args: Array[String]): Unit = {
    
    // get filePath
    val arglist = args.toList
    val Path = arglist(0)

    predict_all(arglist(0), arglist(1))
  }
}
