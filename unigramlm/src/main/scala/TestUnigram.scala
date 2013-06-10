object TestUnigram {
  def main(args: Array[String]): Unit = {
    import scala.io._
    import scala.collection.mutable.Map

    // Edit here 
    val lambda_1 = 0.95d
    val V = 1000000

    // Constant Value
    val lambda_unk = 1 - lambda_1
    var W = 0
    var H = 0.0d
    var unk = 0

    // Read Model file
    val arglist = args.toList
    val trainFilePath = arglist(0)

    val sourceTrain = Source.fromFile(trainFilePath, "UTF-8")
    val probabilities: Map[String, Float] = scala.collection.mutable.Map()

    for(line <- sourceTrain.getLines()){
      val wordProbPair = line.stripLineEnd split '\t'
      probabilities.update(wordProbPair(0), wordProbPair(1).stripLineEnd.toFloat)
    }

    // Evaluation and print the result
    // Read Test file
    val testFilePath = arglist(1)
    val sourceTest = Source.fromFile(testFilePath, "UTF-8")

    for(line <- sourceTest.getLines()){
      var words = line.stripLineEnd split ' '
      words = words :+ "</s>"
      
      for(w <- words){
        W += 1
        var P = (lambda_unk/V).toDouble

        if (probabilities.contains(w)){
          P += lambda_1 * probabilities(w)

        } else {
          unk += 1
        }
      H += -(scala.math.log(P)/scala.math.log(2))
      }
    }

    println("Entropy:  " + H/W)
    println("Coverage: " + ((W-unk)/W.toDouble))
   
  }

}

