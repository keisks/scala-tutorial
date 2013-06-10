Run by sbt as follows.  
(Of course, you can run without compiling by ``scala XYZ.scala ARGS Scala``


``sbt 'run ../nlp-programming/test/01-train-input.txt'``

        Multiple main classes detected, select one to run:
        
        [1] TrainUnigram
        [2] TestUnigram
        
        Enter number: 1

``sbt 'run unigram.model ../nlp-programming/test/01-test-input.txt'``

        Multiple main classes detected, select one to run:
        
        [1] TrainUnigram
        [2] TestUnigram
        
        Enter number: 2
        
        Entropy:  6.709899494272102
        Coverage: 0.8

