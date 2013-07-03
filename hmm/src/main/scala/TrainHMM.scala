object TrainHMM {
  def main(args: Array[String]): Unit = {
    import scala.io._
    import scala.collection.mutable
    import java.io.PrintWriter

    // get filePath
    val arglist = args.toList
    val filePath = arglist(0)

    // open a file
    val source = Source.fromFile(filePath, "UTF-8")
    
    // create maps: emit, transition, context
    val emitMap: mutable.Map[String, Int] = mutable.Map()
    val transitionMap: mutable.Map[String, Int] = mutable.Map()
    val contextMap: mutable.Map[String, Int] = mutable.Map()

    // count up emission, transition, and context
    for (line <- source.getLines()) {
      val wordtags = line.stripLineEnd split ' '
      var previous = "<s>"

      if (contextMap.contains(previous)) {
        contextMap.update(previous, contextMap(previous)+1)
      } else {
        contextMap += previous ->1
      }

      wordtags.foreach { wt =>
        val wordtag = wt split '_'
        val word = wordtag(0)
        val tag = wordtag(1)
        val transition = previous + ' ' + tag
        val emit = tag + ' ' + word

        // count the transition
        if (transitionMap.contains(transition)) {
          transitionMap.update(transition, transitionMap(transition)+1)
        } else {
          transitionMap += transition -> 1
        }

        // count the context
        if (contextMap.contains(tag)) {
          contextMap.update(tag, contextMap(tag)+1)
        } else {
          contextMap += tag -> 1
        }

        // count the emission
        if (emitMap.contains(emit)) {
          emitMap.update(emit, emitMap(emit)+1)
        } else {
          emitMap += emit -> 1
        }

        previous = tag
      }

      // add the last transition
      val after = previous + " </s>"

      if (transitionMap.contains(after)) {
        transitionMap.update(after, transitionMap(after))
      } else {
        transitionMap += after -> 1
      }
    }

    // open the model_file for writing
    val outFile = new PrintWriter("hmm.model")

    val sortedTransitionKeys = transitionMap.keys.toList.sorted
    val sortedEmitKeys= emitMap.keys.toList.sorted

    // print the transition probabilities
    sortedTransitionKeys.foreach { trans =>
      val prevNext = trans split ' '
      val prev = prevNext(0)
      val probability = transitionMap(trans).toFloat / contextMap(prev).toFloat
      outFile.println(f"T\t$trans\t$probability%.6f")
    }

    // print the emission probabilities
    sortedEmitKeys.foreach { emission =>
      val tagWord = emission split ' '
      val pos = tagWord(0)
      val probability = emitMap(emission).toFloat / contextMap(pos).toFloat
      outFile.println(f"E\t$emission\t$probability%.6f")
    }
    source.close()
    outFile.close()
  }
}
