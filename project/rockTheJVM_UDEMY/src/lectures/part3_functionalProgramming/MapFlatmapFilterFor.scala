package lectures.part3_functionalProgramming

object MapFlatmapFilterFor extends App {

  //LIST
  println("LIST: ")
  val list = List(1,2,3) //standard list library List implementation, and notice: we are create a List by calling the apply method on the list companion object
  println(list)
  println(list.head)
  println(list.tail)


  //MAP -- it has the same signature we implemented before (according to documentation: map[B](f: (A) => B): List[B)) -- builds a new collection by applying a function to all elements of this list
  println("MAP: ")
  println(list.map(_+ 1))
  println(list.map(_ + " is a number"))

  //Filter
  println("FILTER: ")
  println(list.filter(_%2==0)) //prints all even numbers

  //faltmap
  println("FlatMap: ")
  val toPair = (x: Int) => List(x, x+1) // receives a Int param (x) and for each param it wuill create a list of x,x+1
  //toPiar is basically: a lambda that turns a X (which is an Int) into a list of ints (it's a list of Ints, because it uses "x" and X is an int, and that list is "x", and "x+1"
  println(list.flatMap(toPair))
  println(list.flatMap((x: Int) => List(x, x*8)))

  //Printing all combinations between two lists:
  // outpout: List(a1, a2, a3, a4, b1, ...... d3, d4)
  val numbers = List(1,2,3,4)
  val chars = List('a', 'b', 'c', 'd')
  val colors = List("black",  "white")
  println("Combinations between two lists: ")
  /*
   Explanation:
      Because we want that for each value on numbers we are going to do sometinhg, we can start doing:
        numbers.flatmap
      Then, for each value, lets call "n" we are going to generate a list, and we are going to traverse characters:
        numbers.flatmap(n => chars.map
      And then for every character  (numbers.flatmap(n => chars.map(c => )  we are going to return the string composed from the character and the number:
        numbers.flatmap(n => chars.map(c => "" + c + n))


      In traditional progamming languages, we would do ForLoops
      Above we see the implementation using Functional code, recurring recursion transforming foorloops into functional code

      If we have 2 loops, we would do flatmap, map

      If we have 3 loops we would do flatmap, flatmap, map
   */

  //"iterations" done in Function Code
  val combinations = numbers.flatMap(n => chars.map(c=>""+c+n))
  val combinations2 = numbers.flatMap(n => chars.flatMap(c => colors.map((col => ""+c+n+"_"+col))))
  println(combinations)
  println(combinations2)

  //foreach
  println("FOREACH: ")
  list.foreach(println)


  /*
  Having this
      val combinations2 = numbers.flatMap(n => chars.flatMap(c => colors.map((col => ""+c+n+"_"+col))))
  can be a bit difficult for other people to read, so we can use "for-comprehension" to iterate...
  The full expression below, is rewritten by the compiler to change of maps and flood maps
  BUT for this to be applied, the following function signatures (meaning, they should like code below) :
   map(f: A => B) => MyList[B] --> map needs to receive a function from A to B  and should return a MyList[B]
   filter(p: A => Boolean) => MyList[A] --> filters should receive a predicate  which is from A to Boolean  and should retirn a MyList[A]
   flatMap(f: A => MyList[B]) => MyList[B] --> flatMap should receive a function from A to MyList[B]  and should return a MyList[B]
  */
  val forCombinations = for {
    n <- numbers
    c <- chars
    col <- colors
  } yield ""+c+n+"_"+col
  println(forCombinations)


  //Filter
  /*
  Below we are going to filterOut non even numbers, so we can use
    n <- numbers if n%2 == 0
  that is exactly ythe same as
    numbers.filter(_%2==0)

  and the for-comprehension below is exactly the same as
    val combinations2 = numbers.filter(_%2==0).flatMap(n => chars.flatMap(c => colors.map((col => ""+c+n+"_"+col))))
*/
  val forCombinationsEven = for {
    n <- numbers if n%2 == 0
    c <- chars
    col <- colors
  } yield ""+c+n+"_"+col
  println(forCombinationsEven)

  /*Below we have the same as  the FOREACH above we used*/
  for {
    n <- numbers
  } println(n)

  //syntax overload
  //below we are multiplying by 2 each element of "list"
  val multBy2 = list.map { x =>
    x * 2
  }
  println(multBy2)




  /*EXERCISES
  1. Does MyList support for-comprehensions !? -- MyListMapFlatmapFilter.scala

  2. Implement a small collection named "maybe" of at most one element
    - it will be Maybe[+T] -> meaning it's covariant
    - the only possible subtypes of this, are going to be an empty collection or an element with one element
    - implement map, flatMap, filter
*/

  //


}
