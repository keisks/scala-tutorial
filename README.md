# Scala ~~Tutorial~~ self-study for NLP

This is a scala  ~~Tutorial~~ self-study for Natural Language Processing (NLP).

The practice is based on the NLP Programming Tutorial at <http://www.phontron.com/teaching.php?lang=en>.  
I am grateful to Graham Neubig for his great tutorial at Nara Institute of Science and Technology (NAIST).

N.B All the programs are written and tested on Mac. This repository is work-in-progress and continually-revised. 

For those who are interested in, here I give a step-by-step instruction.  
For a great introduction of Scala can be found at <http://www.scala-lang.org/node/1305>.

(The step 1 and 2 are needed for the first time only.)

1. For character encoding for Scala, the following settings are necessary (only for Mac users?).

        (1) add ``export JAVA_OPTS="-Dfile.encoding=UTF-8"`` in your (bash, zsh, etc) rc file.
        (2) create ~/.sbtconfig and write ``SBT_OPTS=-Dfile.encoding=UTF-8``.

2. Download the repository and tutorial dataset. 

  ``git clone https://github.com/keisks/scala-tutorial.git``  
  ``wget http://www.phontron.com/data/nlp-programming-data.zip``  
  ``unzip nlp-programming-data.zip``  
  ``rm nlp-programming-data.zip``  

3. Make project by makesbt.sh

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

4. You create your scala code at src/main/scala/

5. (optional) Compile your code by ``sbt compile``.

6. (optional) Write test at src/test/scala/, according to your test framework.

7. Run your code by either  
        a. ``scala src/main/scala/yourcode.scala (arg1 arg2 ...) Scala`` (if you don't compile your code at step 4.)  
        b. ``sbt run`` or ``sbt 'run arg1 arg2 ... '``

