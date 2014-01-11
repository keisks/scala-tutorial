object LearnLda {
  import scala.io._
  import scala.collection.mutable
  import java.io.PrintWriter
  import scala.util.Random

  val N = 20     // You can edit # of topics.
  val I = 100   // Number of Iterations
  val B = 50    // Burn-in period
  val alpha = 0.01
  val beta = 0.01

  // make vectors 
  val xvector: mutable.ListBuffer[List[String]] = mutable.ListBuffer()
  val yvector: mutable.ListBuffer[mutable.ListBuffer[Int]]= mutable.ListBuffer()

  // make counts 
  val xcounts: mutable.Map[String, Int] = mutable.Map().withDefaultValue(0)
  val ycounts: mutable.Map[String, Int] = mutable.Map().withDefaultValue(0)


  def sample(probs: mutable.ListBuffer[Double]): Int = {
    val cumulatives = probs.scanLeft(0.0)(_ + _).tail
    val cumul_normal = cumulatives.map(_ / cumulatives.last)

    val seed = Random.nextFloat()
    for ((cn, s) <- cumul_normal.zipWithIndex) {
      if (seed < cn) {
        return s
      }
    }
    throw new Exception("sampling probability is incorrect")
  }

  def addCounts(word: String, topic: Int, docID: Int, amount: Int): Unit = {
    val t = topic.toString
    val d = docID.toString
    val wt = word + '_' + t
    val td = t + '_' + d

    xcounts.update(t, xcounts(t) + amount)
    xcounts.update(wt, xcounts(wt) + amount)

    ycounts.update(d, ycounts(d) + amount)
    ycounts.update(td, ycounts(td) + amount)

    // bug check
    if (xcounts(t) < 0){
      throw new Exception("xcounts(topic) incorrect")
    }
    if (xcounts(wt) < 0){
      throw new Exception("xcounts(topic_word) incorrect")
    }
    if (ycounts(d) < 0){
      throw new Exception("ycounts(docID) incorrect")
    }
    if (ycounts(td) < 0){
      throw new Exception("ycounts(docID_topic) incorrect")
    }
  }

  def gibbsSampling(): Unit = {
    for (itera <- 0 to I) {
      var ll = 0.0
      for ((xvec, di) <- xvector.zipWithIndex) {
        for ((word, j) <- xvec.zipWithIndex){
          val w = word
          val t = yvector(di)(j)
          addCounts(w, t, di, -1)

          val prob: mutable.ListBuffer[Double] = mutable.ListBuffer()
          for (n <- 0 to N-1) {
            val d = di.toString
            val wt = w + '_' + n.toString
            val td = n.toString + '_' + d

            val pr_z_di = (ycounts(td)+beta) / (ycounts(d) + beta*(xvector.length))  
            val pr_w_z =  (xcounts(wt)+alpha) / (xcounts(n.toString) + alpha*N)

            prob += (pr_z_di * pr_w_z)
          }
          //println(prob)

          val new_t = sample(prob)
          addCounts(w, new_t, di, 1)
          yvector(di)(j) = new_t

          ll += math.log(prob(new_t))
          
        }
      }
      println(ll)
    }
  }

  def main(args: Array[String]): Unit = {
    //get a filePath
    val arglist = args.toList
    val filePath = arglist(0)
   
    //open the file
    val source = Source.fromFile(filePath, "UTF-8")

    // Initialization
    for(line <- source.getLines()) {
      val docid = xvector.length
      val words = line.stripLineEnd.split(' ').toList
      val topics = mutable.ListBuffer[Int]()

      for(w <- words){
        val z = Random.nextInt(N)
        topics += z
        addCounts(w, z, docid, 1)
      }
      xvector += words
      yvector += topics
    }
    
    // (collapsed) Gibbs Sampling
    gibbsSampling()

    // 
    ////val tw_distribution: mutable.ListBuffer[mutable.ListBuffer[Tuple(String, Double)]] = mutable.ListBuffer()
    //for (wt_counts <- xcounts){
    //  //println(wt_counts)
    //  val (k, v) = wt_counts
    //  var w = " "
    //  var t = " "
    //  var w_prob = 0.0
    //  if (k.contains("_")) {
    //    w = k.split('_')(0)
    //    t = k.split('_')(1)
    //    }
    //}
      
    println(ycounts)
    println(xcounts)
  }
} 
