Run by sbt as follows.  
(Of course, you can run without compiling by ``scala XYZ.scala ARGS Scala``


``sbt 'run ../nlp-programming/test/02-train-input.txt'``

        Multiple main classes detected, select one to run:
        
        [1] TrainBigram
        [2] TestBigram
        
        Enter number: 1

You may get bigram_prob.model as follows:

        <s> a 1.000000  
        a 0.333333  
        a b 1.000000  
        b 0.333333  
        b c 0.500000  
        b d 0.500000  
        c 0.166667  
        d 0.166667  

Now, you can train on real data.

``sbt 'run ../nlp-programming/data/wiki-en-train.word'``

For test, you can run as follows:

``sbt 'run bigram.model ../nlp-programming/data/wiki-en-test.word'``

        Multiple main classes detected, select one to run:
        
        [1] TrainBigram
        [2] TestBigram
        
        Enter number: 2
        
        (The result depends on the parameter lambda_1 and lambda_2 in TestBigrm.)  
        Entropy: 12.282323235904924

