object TestBigram {
  def main(args: Array[String]): Unit = {
    import scala.io._
    import scala.collection.mutable.Map

    // Edit here 
    val lambda_1 = 0.95d
    val lambda_2 = 0.95d
    val V = 1000000

    // Constant Value
    //val lambda_unk = 1 - lambda_1
    var W = 0
    var H = 0.0d

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
      words = "<s>" +: words :+ "</s>"
      
      for(i <- 1 to words.length-1){
        var P1 = 0.0d
        var P2 = 0.0d
        if (probabilities.contains(words(i-1) + ' ' + words(i))) {
          if (probabilities.contains(words(i))) {
            P1 = lambda_1 * probabilities(words(i)) + ((1-lambda_1)/V).toDouble
            P2 = lambda_1 * probabilities(words(i-1) + ' ' + words(i)) + (1-lambda_2) * P1
          } else {
            P1 = (1-lambda_1)/V.toDouble
            P2 = (1-lambda_2) * P1
          }
        } else {
          if (probabilities.contains(words(i))) {
            P1 = lambda_1 * probabilities(words(i)) + ((1-lambda_1)/V).toDouble
            P2 = (1-lambda_2) * P1
          } else {
            P1 = (1-lambda_1)/V.toDouble
            P2 = (1-lambda_2) * P1
          }
        }
        W += 1
        H += -(scala.math.log(P2)/scala.math.log(2))
      }
    }
    println("Entropy: " + H/W)
  }
}

