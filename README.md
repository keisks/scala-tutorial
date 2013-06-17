# Scala ~~Tutorial~~ self-study for NLP

This is a scala  ~~Tutorial~~ self-study for Natural Language Processing (NLP).

The practice is based on the NLP Programming Tutorial (http://www.phontron.com/teaching.php).  
I am grateful to Graham Neubig for his great tutorial at Nara Institute of Science and Technology (NAIST).

N.B This repository is work-in-progress and continually-revised.

For those who are interested in, here I give a step-by-step instruction.  
For a great introduction of Scala can be found at <http://www.scala-lang.org/node/1305>


1. Download the repository and tutorial dataset. (This step is necessary for the first time only.)

  ``git clone https://github.com/keisks/scala-tutorial.git``  
  ``wget http://www.phontron.com/data/nlp-programming-data.zip``  
  ``unzip nlp-programming-data.zip``  
  ``rm nlp-programming-data.zip``  

2. Make project by makesbt.sh

  e.g.
  ``sh makesbt.sh Intro``

  This will give you directories and files as follows:

        $ Tree -L2 Intro
        Intro/
        ├── README.md
        ├── build.sbt
        ├── project
        │   ├── Build.scala
        │   └── target
        ├── src
        │   ├── main
        │   └── test
        └── target
            ├── resolution-cache
            ├── scala-2.10
            └── streams

3. You create your scala code at src/main/scala/

4. (optional) Compile your code by ``sbt compile``.

5. (optional) Write test at src/test/scala/, according to your test framework.

6. Run your code by either  
        a. ``scala src/main/scala/yourcode.scala (arg1 arg2 ...) Scala`` (if you don't compile your code at step 4.)  
        b. ``sbt run`` or ``sbt 'run arg1 arg2 ... '``


