package exercises

object MyListMapFlatmapFilter extends App {

  val listOfIntegers: MyListMapFlatmapFilter[Int] = new ConsMapFlatmapFilter(1, new ConsMapFlatmapFilter(2, new ConsMapFlatmapFilter(3, EmptyMapFlatmapFilter)))
  val cloneListOfIntegers: MyListMapFlatmapFilter[Int] = new ConsMapFlatmapFilter(1, new ConsMapFlatmapFilter(2, new ConsMapFlatmapFilter(3, EmptyMapFlatmapFilter)))
  //val listOfStrings: MyListMapFlatmapFilter[String] = new ConsMapFlatmapFilter("Hello", new ConsMapFlatmapFilter("everyone", EmptyMapFlatmapFilter))




  val listOfStrings: MyListMapFlatmapFilter[String] = new ConsMapFlatmapFilter("Hello", new ConsMapFlatmapFilter("everyone", new ConsMapFlatmapFilter("here", EmptyMapFlatmapFilter)))

  val anotherListOfIntegers: MyListMapFlatmapFilter[Int] = new ConsMapFlatmapFilter(4, new ConsMapFlatmapFilter(5, new ConsMapFlatmapFilter(6, EmptyMapFlatmapFilter)))


  //case classes verifications
  /**
   * returns true, because we are using case classes.
   * if not used, we would need to define a recursive "equals" method, which have been a great headache because we would need to compare all the elements recursively
   */
  //println(listOfIntegers == cloneListOfIntegers)

  println("Original values for list: " + listOfIntegers)
  /*
  old:
  println("Values for transform: " + listOfIntegers.map(new Function1[Int, Int] {
    override def apply(elem: Int): Int = elem * 2
  }).toString)

  new:
  */
  println("Values for transform: " + listOfIntegers.map(elem => elem * 2).toString)
  println("Values for transform: " + listOfIntegers.map(_ * 2).toString) //same as above


  /*
  old:

    println("Values for test: " + listOfIntegers.filter(new Function1[Int, Boolean] {
      override def apply(Elem: Int): Boolean = Elem % 2 == 0
    }).toString)

  new:
   */
  println("Values for test: " + listOfIntegers.filter(elem => elem % 2 == 0).toString)
  println("Values for test: " + listOfIntegers.filter(_ % 2 == 0).toString) // same as above


  println((listOfIntegers ++ anotherListOfIntegers).toString)
  /*
  old:


  println(listOfIntegers.flatMap(new Function1[Int, MyListMapFlatmapFilter[Int]] {
    override def apply(Elem: Int): MyListMapFlatmapFilter[Int] = new ConsMapFlatmapFilter(Elem, new ConsMapFlatmapFilter(Elem + 1, EmptyMapFlatmapFilter))
  }).toString)

 new:
   */
  println(listOfIntegers.flatMap(elem => new ConsMapFlatmapFilter(elem, new ConsMapFlatmapFilter(elem + 1, EmptyMapFlatmapFilter))).toString)
  //not possivle to write lambda because "elem" is being used twice in the implementation, and "_" is used for different parameters!!!
  //[1 2  2 3  3 4]


  //HOFS Exercises
  println("HOFS Exercises:")
  println("foreach method:")
  listOfIntegers.foreach(println)
  println("sort method:")
  println(listOfIntegers.sort((x,y) => y - x)) // listOfIntegers.sort((x,y) => y - x) -->> this means we are using listOfIntegers but we are inverting the order of the elements
  println("sort zipWith:")
  /*
  Line below does not work, because:
    "_" are extremely contextual, and Scala doesnt know which "_" means what, so we will need to pass in the type params
  */
  //println(anotherListOfIntegers.zipWith(listOfIntegers, _ + "-" + _ ))
  println(anotherListOfIntegers.zipWith[String, String](listOfStrings, _ + "-" + _ ))
  println("sort fold:")
  println(listOfIntegers.fold(0)(_+_))


  println("FOR COMPREHENSIONS EXERCISES")

  //FOR COMPREHENSIONS EXERCISES
  /*
  val combinations = for{
    n <- listOfIntegers
    string <- listOfStrings
  }yield n + " " + string

  println(combinations)
  */



  println(for{
    n <- listOfIntegers
    string <- listOfStrings
  }yield n + " " + string)

}


/**
 * The class below, can be compared with MyListMapFlatmapFilter, on this package.
 */

abstract class MyListMapFlatmapFilter[+A] {
  // This represents an immutable List, BECAUSE we are not modifying this instance, BUT YES returning a new List
  // The methods that tells us that above, are "add" and "tail", because they RETURN A NEW LIST. The other ones, are just a simple "select"/"definition" of something

  def head: A

  def tail: MyListMapFlatmapFilter[A]

  def isEmptyMapFlatmapFilter: Boolean

  def add[B >: A](element: B): MyListMapFlatmapFilter[B] //same as the Cats, Dogs and Animal example. See "add" method on part2oop.Generics

  def printElements: String

  /*
  polymorphic call
  -> toString method calls "printElements"
  -> when we call later "toString" method, the correct implementation of "printElements" will be called, that why this is a polymorphic call
   */
  override def toString: String = {
    "[" + printElements + "]"
  }


  //Higher order functions --> either receive functions as parameters OR return other functions as result
  def map[B](transformer: A => B): MyListMapFlatmapFilter[B] //same as def map[B](transformer: Function1[A, B]): MyListMapFlatmapFilter[B]

  def flatMap[B](transformer: A => MyListMapFlatmapFilter[B]): MyListMapFlatmapFilter[B]

  def filter(predicate: A => Boolean): MyListMapFlatmapFilter[A]


  //concatenation
  def ++[B >: A](list: MyListMapFlatmapFilter[B]): MyListMapFlatmapFilter[B]

  //since flatMap needs a concatenation we need to define this method


  //HOFS
  //ex.1
  def foreach(f: A => Unit): Unit

  //ex,2
  def sort(compare: (A, A) => Int): MyListMapFlatmapFilter[A]

  //ex3 - attention! This function needs to raise expection if both lists are have different lengths
  def zipWith[B,C](list: MyListMapFlatmapFilter[B], zip:(A,B) => C) : MyListMapFlatmapFilter[C] //zipWith[B,C] -->> B and C are type parameters

  //ex4 - fold method is important when we want  to calapse all data into a single value. Often called as "reduce", and "fold" is one of the formats that reduce can have
  def fold[B](start: B)(operator: (B, A) => B ): B //parameter list: "start" and "operator"



}

/*
Lets see the below declarations:
  val listOfIntegers: MyListMapFlatmapFilter[Int] = EmptyMapFlatmapFilter
  val listOfStrings: MyListMapFlatmapFilter[String] = EmptyMapFlatmapFilter
  "EmptyMapFlatmapFilter" should be a proper value for both types of lists of Int and String
  But how do we do that?

We're going to use "Nothing" below" because ??????
* */
object EmptyMapFlatmapFilter extends MyListMapFlatmapFilter[Nothing] { //"case" makes this object extremely powerful -> "equals" and "hash" are implemented OOTB , and it's serializable
  def head: Nothing = throw new NoSuchElementException //does not make sense for an EmptyMapFlatmapFilter list...

  def tail: MyListMapFlatmapFilter[Nothing] = throw new NoSuchElementException //does not make sense for an EmptyMapFlatmapFilter list...

  def isEmptyMapFlatmapFilter: Boolean = true

  def add[B >: Nothing](element: B): MyListMapFlatmapFilter[B] = new ConsMapFlatmapFilter(element, EmptyMapFlatmapFilter) //when we add an element for the first time, list just have 1 element. So, "element" param is the head, and the tail of the list is EmptyMapFlatmapFilter object

  def printElements: String = ""

  //Exercise3
  def map[B](transformer: Nothing => B): MyListMapFlatmapFilter[B] = EmptyMapFlatmapFilter

  def filter(predicate: Nothing => Boolean): MyListMapFlatmapFilter[Nothing] = EmptyMapFlatmapFilter

  def ++[B >: Nothing](list: MyListMapFlatmapFilter[B]): MyListMapFlatmapFilter[B] = list //anything concatenated with empty ,is that thing
  def flatMap[B](transformer: Nothing => MyListMapFlatmapFilter[B]): MyListMapFlatmapFilter[B] = EmptyMapFlatmapFilter


  //HOFS
  def foreach(f: Nothing => Unit): Unit = () //remember: Unit is "()"
  def sort(compare: (Nothing, Nothing) => Int) = EmptyMapFlatmapFilter
  def zipWith[B,C](list: MyListMapFlatmapFilter[B], zip: (Nothing, B) => C): MyListMapFlatmapFilter[C] = { //very wide used function on DataScience
    if (!list.isEmptyMapFlatmapFilter) throw new RuntimeException("Lists do not have the same length")
    else EmptyMapFlatmapFilter //empty with empty, with return an empty list
  }

  def fold[B](start: B)(operator: (B, Nothing) => B): B = start //because it's an empty list, we don't really get to apply the operator to anything, so we'll just return the start value

}

class ConsMapFlatmapFilter[+A](h: A, t: MyListMapFlatmapFilter[A]) extends MyListMapFlatmapFilter[A] { //"ConsMapFlatmapFilter" must be Covariant, accordingly MyListMapFlatmapFilter
  def head: A = h

  def tail: MyListMapFlatmapFilter[A] = t

  def isEmptyMapFlatmapFilter: Boolean = false

  def add[B >: A](element: B): MyListMapFlatmapFilter[B] = new ConsMapFlatmapFilter(element, this) //the element is the one added, and the tail is the list itself ("this")

  def printElements: String = {
    if (t.isEmptyMapFlatmapFilter) "" + h
    else {
      h + " " + t.printElements
    }
  }


  //Exercise3
  def map[B](transformer: A => B): MyListMapFlatmapFilter[B] = {
    //the output is a Cons, where the head is going to be transformed by the transformer
    //new ConsMapFlatmapFilter(transformer.transform(h), ... ) -> head of the result
    //new ConsMapFlatmapFilter(.. , t.map(transformer)) -> recursive call
    new ConsMapFlatmapFilter(transformer(h), t.map(transformer))
  }

  /*
  How map works
  lets say we have list[1,2,3]
  and we are going to do list[1,2,3].map(n*2)
  = new ConsMapFlatmapFilter(2, [2,3].map(n*2)) -> head of list is "1", multiply by 2 AND the tail is [2,3).map(n*2)
  = new ConsMapFlatmapFilter(2, new ConsMapFlatmapFilter(4, [3].map(n*2))) -> head of list is "2", multiply by 2 AND the tail is [3,3).map(n*2)
  = new ConsMapFlatmapFilter(2,4, new ConsMapFlatmapFilter(6, EmptyMapFlatmapFilter.map(n*2)))
  = new ConsMapFlatmapFilter(2, new ConsMapFlatmapFilter(4, new ConsMapFlatmapFilter(6, EmptyMapFlatmapFilter))))

  * */


  def filter(predicate: A => Boolean): MyListMapFlatmapFilter[A] = {
    //testing if head satisfies the predicate (if predicate.head passes the test)
    //if passes the test, head, "h" will be included in the result, so must return "new Cons(h)" with the predicate so the tail needs to be filtered and the to be passed in as the result. so we have   new Cons(h,t.filter(predicate))
    //basically if head passes the predicate, it will be included in the result, so the result will be certainly a "cons with the head". Te tail will be filtered with a predicate,so we don't know what that will return (might be an empty list, but we will just delegate to the recursive call with filter
    if (predicate(h)) new ConsMapFlatmapFilter(h, t.filter(predicate))
    //if the head does not pass, can not be returned, only the predicate
    else t.filter(predicate)
  }

  /*
  How filter works
  original list is [1,2,3] and we want only the predicates, so the values that rest of division of value by 2 is 0
  tests always start on the head, so:
  ->[1,2,3].filter(n%2 == 0)
  "1" is not predicate
  let's evaluate the tail [2,3]
  ->[2,3].filter(n%2 == 0)
  2 is predicate
  return new ConsMapFlatmapFilter(2, [3].filter(n%2 == 0))
  new ConsMapFlatmapFilter(2, EmptyMapFlatmapFilter.filter(n%2 == 0))
  = new ConsMapFlatmapFilter(2, EmptyMapFlatmapFilter)
  * */


  /*
  [1,2] ++ [3,4,5]
  = new  ConsMapFlatmapFilter(1, [2] ++ [3,4,5])
  = new  ConsMapFlatmapFilter(1, new ConsMapFlatmapFilter(2, Empty ++[3,4,5]))
  = new  ConsMapFlatmapFilter(1, new ConsMapFlatmapFilter(2, new  ConsMapFlatmapFilter(3, new  ConsMapFlatmapFilter(4, =ew ConsMapFlatmapFilter(5)))))
   */

  def ++[B >: A](list: MyListMapFlatmapFilter[B]): MyListMapFlatmapFilter[B] = new ConsMapFlatmapFilter(h, t ++ list)


  def flatMap[B](transformer: A => MyListMapFlatmapFilter[B]): MyListMapFlatmapFilter[B] = {
    transformer(h) ++ t.flatMap(transformer)
  }

  /*
 How it works
 we have list[1,2], the goal is to do:
 [1,2,].flatMap(n => n, n+1)
 = [1,2] ++ [2].flatMap(n => [n, nº1])
 = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])           --> [2,3] is the result of (1+1) and (2+1) and now we want to concatenate both lists
 = [1,2,2,3]
   */


  //HOFS
  def foreach(f: A => Unit): Unit = {
    f(h)
    t.foreach(f)

  }

  def sort(compare: (A, A) => Int): MyListMapFlatmapFilter[A] = {
    //insert is an auxiliary function
    //insert() does a sorted insertion of a value inside an already sorted list
    def insert(x: A, sortedList: MyListMapFlatmapFilter[A]): MyListMapFlatmapFilter[A] = { //with "sortedList: MyListMapFlatmapFilter[A]" we are assuming hat list is already sorted
      if (sortedList.isEmptyMapFlatmapFilter ) new ConsMapFlatmapFilter (x, EmptyMapFlatmapFilter)
      else if (compare (x, sortedList.head) <= 0) new ConsMapFlatmapFilter (x, sortedList) //x is the samllest value on the result, so we want ot insert it on the beginning of the list - we are always comparing with the first element of a list because that list is already sorted!
      else new ConsMapFlatmapFilter (sortedList.head, insert (x, sortedList.tail) ) //this means the first element of the list is the samllest one, we get everything after it, and we call then the function to do the tests again until we find a place to insert "x" on the list. This is not tail recursion!
    }
    val sortedTail = t.sort (compare)
    insert (h, sortedTail)
  }


  def zipWith[B,C](list: MyListMapFlatmapFilter[B], zip: (A, B) => C): MyListMapFlatmapFilter[C] = {
    if (list.isEmptyMapFlatmapFilter) throw new RuntimeException("Lists do not have the samesss length")
    else new ConsMapFlatmapFilter(zip(h, list.head), t.zipWith(list.tail, zip))
    //zip(h, list.head -> first element will be zip to the "head" of the list, and to the new list

  }


  def fold[B](start: B)(operator: (B, A) => B ): B = {
    t.fold(operator(start, h))(operator)
    /*
    Because it+s non-empty list, we have to compose that value with the HEAD value
      --> fold(operator(start, h)
    * */

    /*
    How it wokrs:
    We have [1,2,3] list

    if we do:
    list.fold(0)(+) we start on the fisrt position (because we called the function with "0" ) . "+" is to "add numbers"
    now, next iteration:
    we have the list [2,3]
    and we do now list.fold(1)(+)

    now we have another tail, now it's a list with just "3" -> [3]
    3.fold the operator is the start value is the operator between "1" and and head ("3)

    then we have [].fold(6)(+)
    and now we have "empty.fold" with the start vlue and the operator, that just returns the start value which is 6.
   */

  }

}

/*
//Both traits below are not needed anymore, since like we can see, they are basically functions

trait MyPredicateFPExercise[-T] {
  def test(Elem: T): Boolean
}


//Exercise 2
trait MyTransformerFPExercise[-A, B] { //if A is not contravariant, def map wont work
  def transform(Elem: A): B
}
*/
