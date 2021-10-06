package lectures.part3_functionalProgramming

import scala.util.Random

object Sequences extends App {

  /*
  Sequences

  are a general interface for data strucutres that
      have a well defined order
      can be indexed

     Supports various operations:
        For indexing and iterating - apply, iteration, length, revers
        For concatenation, appending prepending
        grouping, sorting, zipping, seraching. slicing


  * */

  println("SEQUENCES: ")
  val aSeq = Seq(4,7,1,2,8) //we are using "apply" method, do not forget
  println(aSeq) //prints "List(1, 2, 3, 4)" -why do we have "list"?! Well, "Seq" companion object actually has an "apply" factory method that can constructs subclasses of sequence. but we can see, that declared tap of "aSeq" is "Seq[Int]"
  println(aSeq.reverse)
  println(aSeq(2))
  println(aSeq ++ Seq(5,6,3))
  println(aSeq ++ Seq(5,6,3).sorted)
  println((aSeq.sorted ++ Seq(5,6,3)))
  println((aSeq ++ Seq(5,6,3)).sorted)
  println(aSeq.sorted)


  //Ranges - are also sequences
  println("RANGES: ")
  val aRange: Seq[Int] = 1 to 10 // 1 and 10 are INCLUSIVE
  val aRange2: Seq[Int] = 1 until 10 // 1 and 10 are EXCLUSIVE
  aRange.foreach(println)
  aRange2.foreach(println)

  (1 to 10).foreach(x=> println("Hello number - " + x))




  /*
  Lists
    Are immutable linked list
        head, tail, isEmpty methods are fast! (     O(1) -> this means "amortised constatine"   )
        length, reverse are also suported, but are slowerd than the above ones (   O(n) -> linear time   )



  Are Sealed, and have two subtypes:
      object Nil (empty list)
      class ::
  */

  println("LISTS: ")
  val aList = List(1,2,3)
  val prepended = 42 :: aList //"::" is the syntatic suggr for 42.apply(aList) (???)
  println(prepended)
  val prepended2 = 42 +: aList // +: infix operator between 42 and aList and does prepending
  println(prepended2)
  val prepended3 = aList :+ 90 // +:  infix operator between 42 and aList and does append
  println(prepended3)

  val apples5 = List.fill(5)("apple") //"fill" is a curried function that takes in a number and a value, and a constructs a list with X times that number that value
  println(apples5)

  println(aList.mkString("-")) //concats all values on the list, and adds a separator between all values







  /*
  ARRAYS
  arrays are sequences. how= with an implicit conversion, like we can see below, line 107

Equivalent of simple Java Arrays
--> can be manually constructed with predefined lengths
-->can be mutated (updated in place)
-->are interoperable with Java's native arrays
-->indexing is fast

so, but where's the sequence part ?!?!?!?!
lets see below

   */


  val numbers = Array(1,2,3,4)
  val threeIntElems = Array.ofDim[Int](3) //creating an array with 3 slots only for Int values
  println(threeIntElems)
  println(threeIntElems.foreach(println)) //prints "0" - the default value

  val threeStringElems = Array.ofDim[String](3) //creating an array with 3 slots only for Int values
  println(threeStringElems)
  println(threeStringElems.foreach(println))//prints "null" - the default value

  //mutation
  numbers(2) = 0 //updating position 2 to put a 0 . SyntaxSUgar for "numbers.update(2,0) - rarely used but commonly used for multiple collections,  but this is a syntax sugar that is very similar to appl
  println(numbers.mkString("//"))

  //arrays are sequences! lets see
  val numbersSeq: Seq[Int] = numbers //implicit conversion
  println(numbersSeq) //prints "WrappedArray(1, 2, 0, 4)" --> special kind of sequence which wraps over an array, so the equal sign is called "implicit conversion"


  /*
  VECTORS
  are the default implementation for immutable sequences
  effectively constant indexed read/write  (    O(log32(n))   )
  fast element addition (append/prepend)
  implemented as fixed-branched trie (branch factor 32, so it hold 32 elements at any one level)
  good performance for lager sizes

  basically, they offer the same functionality as other collections

   */

  val vector: Vector[Int] = Vector(1,2,3)
  println(vector)

  //the perf test -> Vector vs List
  //goal is to get the average write time for a collection. To do so, we will write a value to a Vector or Collection a number of times (randomNumber), sumUp the time required and divide by the number of runs

  val maxRuns = 1000
  val maxCapacity = 1000000
  def getWriteTime(collection: Seq[Int]): Double = {
    val randomNumber = new Random
    //we are going to do "something" for an amount of times
    val times = for{ //times is basically a collection of all the measurements done of the function below (that takes the current system time, inserts a value in a random position and calculates the time to do that
      itr <- 1 to  maxRuns //for every iteration (itr), that goes from 1 to "maxRuns" we are going to (see th yield part)
    } yield { //we are going to do (steps below)
      val currentTime = System.nanoTime() //get current time of the system
      collection.updated(randomNumber.nextInt(maxCapacity),randomNumber.nextInt())//operation -> we are going to the collection and update a random position until "maxCapacity" (randomNumber.nextInt(maxCapacity)) and update with a random value randomNumber.nextInt()
      System.nanoTime() - currentTime
    }

    times.sum/maxRuns //average time it takes for the collection to be updated at a random index with a random value
  }

  //instantiating two collections: a list and a vector
  val numbersList = (1 to maxCapacity).toList
  val numbersVector = (1 to maxCapacity).toVector

  println("Vector vs List")
  /*
Advantages for Lists:
-saves the reference to the tail - so updating the first element is extremely efficient. THE OPPOSITE when trying to update a value in the middle of it (slow performance)
  */
  println(getWriteTime(numbersList))
  /*
Advantages for Vectors:
-needs to traverse the all 32 branch trie and then replace that entire chunk (so the advantage here is that the depth of the tree is small)
-needs to replace an entire 32-element chunk
  */
  println(getWriteTime(numbersVector))

  /*
  9870636.0 - list
  9516.0 - vector
  as we were predicting, inserting a value on a random position in a vector takes much less tme than a list.
   */


}
