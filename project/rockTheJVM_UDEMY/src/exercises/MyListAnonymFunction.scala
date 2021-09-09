package exercises

object AnonymousFunctions_Exercises extends App {

  val listOfIntegers: MyListAnonymFunction[Int] = new ConsAnonymFunction(1, new ConsAnonymFunction(2, new ConsAnonymFunction(3, EmptyAnonymFunction)))
  val cloneListOfIntegers: MyListAnonymFunction[Int] = new ConsAnonymFunction(1, new ConsAnonymFunction(2, new ConsAnonymFunction(3, EmptyAnonymFunction)))
  val listOfStrings: MyListAnonymFunction[String] = new ConsAnonymFunction("Hello", new ConsAnonymFunction("everyone", EmptyAnonymFunction))

  val anotherListOfIntegers: MyListAnonymFunction[Int] = new ConsAnonymFunction(4, new ConsAnonymFunction(5, new ConsAnonymFunction(6, EmptyAnonymFunction)))


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


  println(listOfIntegers.flatMap(new Function1[Int, MyListAnonymFunction[Int]] {
    override def apply(Elem: Int): MyListAnonymFunction[Int] = new ConsAnonymFunction(Elem, new ConsAnonymFunction(Elem + 1, EmptyAnonymFunction))
  }).toString)

 new:
   */
  println(listOfIntegers.flatMap(elem => new ConsAnonymFunction(elem, new ConsAnonymFunction(elem + 1, EmptyAnonymFunction))).toString)
  //not possivle to write lambda because "elem" is being used twice in the implementation, and "_" is used for different parameters!!!
  //[1 2  2 3  3 4]


}


/**
 * The class below, can be compared with MyListAnonymFunction, on this package.
 */

abstract class MyListAnonymFunction[+A] {
  // This represents an immutable List, BECAUSE we are not modifying this instance, BUT YES returning a new List
  // The methods that tells us that above, are "add" and "tail", because they RETURN A NEW LIST. The other ones, are just a simple "select"/"definition" of something

  def head: A

  def tail: MyListAnonymFunction[A]

  def isEmptyAnonymFunction: Boolean

  def add[B >: A](element: B): MyListAnonymFunction[B] //same as the Cats, Dogs and Animal example. See "add" method on part2oop.Generics

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
  def map[B](transformer: A => B): MyListAnonymFunction[B] //same as def map[B](transformer: Function1[A, B]): MyListAnonymFunction[B]

  def flatMap[B](transformer: A => MyListAnonymFunction[B]): MyListAnonymFunction[B]

  //concatenation
  def ++[B >: A](list: MyListAnonymFunction[B]): MyListAnonymFunction[B]

  //since flatMap needs a concatenation we need to define this method

  def filter(predicate: A => Boolean): MyListAnonymFunction[A]

}

/*
Lets see the below declarations:
  val listOfIntegers: MyListAnonymFunction[Int] = EmptyAnonymFunction
  val listOfStrings: MyListAnonymFunction[String] = EmptyAnonymFunction
  "EmptyAnonymFunction" should be a proper value for both types of lists of Int and String
  But how do we do that?

We're going to use "Nothing" below" because ??????
* */
object EmptyAnonymFunction extends MyListAnonymFunction[Nothing] { //"case" makes this object extremely powerful -> "equals" and "hash" are implemented OOTB , and it's serializable
  def head: Nothing = throw new NoSuchElementException //does not make sense for an EmptyAnonymFunction list...

  def tail: MyListAnonymFunction[Nothing] = throw new NoSuchElementException //does not make sense for an EmptyAnonymFunction list...

  def isEmptyAnonymFunction: Boolean = true

  def add[B >: Nothing](element: B): MyListAnonymFunction[B] = new ConsAnonymFunction(element, EmptyAnonymFunction) //when we add an element for the first time, list just have 1 element. So, "element" param is the head, and the tail of the list is EmptyAnonymFunction object

  def printElements: String = ""

  //Exercise3
  def map[B](transformer: Nothing => B): MyListAnonymFunction[B] = EmptyAnonymFunction

  def filter(predicate: Nothing => Boolean): MyListAnonymFunction[Nothing] = EmptyAnonymFunction

  def ++[B >: Nothing](list: MyListAnonymFunction[B]): MyListAnonymFunction[B] = list //anything concatenated with empty ,is that thing
  def flatMap[B](transformer: Nothing => MyListAnonymFunction[B]): MyListAnonymFunction[B] = EmptyAnonymFunction


}

class ConsAnonymFunction[+A](h: A, t: MyListAnonymFunction[A]) extends MyListAnonymFunction[A] { //"ConsAnonymFunction" must be Covariant, accordingly MyListAnonymFunction
  def head: A = h

  def tail: MyListAnonymFunction[A] = t

  def isEmptyAnonymFunction: Boolean = false

  def add[B >: A](element: B): MyListAnonymFunction[B] = new ConsAnonymFunction(element, this) //the element is the one added, and the tail is the list itself ("this")

  def printElements: String = {
    if (t.isEmptyAnonymFunction) "" + h
    else {
      h + " " + t.printElements
    }
  }


  //Exercise3
  def map[B](transformer: A => B): MyListAnonymFunction[B] = {
    //the output is a Cons, where the head is going to be transformed by the transformer
    //new ConsAnonymFunction(transformer.transform(h), ... ) -> head of the result
    //new ConsAnonymFunction(.. , t.map(transformer)) -> recursive call
    new ConsAnonymFunction(transformer(h), t.map(transformer))
  }

  /*
  How map works
  lets say we have list[1,2,3]
  and we are going to do list[1,2,3].map(n*2)
  = new ConsAnonymFunction(2, [2,3].map(n*2)) -> head of list is "1", multiply by 2 AND the tail is [2,3).map(n*2)
  = new ConsAnonymFunction(2, new ConsAnonymFunction(4, [3].map(n*2))) -> head of list is "2", multiply by 2 AND the tail is [3,3).map(n*2)
  = new ConsAnonymFunction(2,4, new ConsAnonymFunction(6, EmptyAnonymFunction.map(n*2)))
  = new ConsAnonymFunction(2, new ConsAnonymFunction(4, new ConsAnonymFunction(6, EmptyAnonymFunction))))

  * */


  def filter(predicate: A => Boolean): MyListAnonymFunction[A] = {
    //testing if head satisfies the predicate (if predicate.head passes the test)
    //if passes the test, head, "h" will be included in the result, so must return "new Cons(h)" with the predicate so the tail needs to be filtered and the to be passed in as the result. so we have   new Cons(h,t.filter(predicate))
    //basically if head passes the predicate, it will be included in the result, so the result will be certainly a "cons with the head". Te tail will be filtered with a predicate,so we don't know what that will return (might be an empty list, but we will just delegate to the recursive call with filter
    if (predicate(h)) new ConsAnonymFunction(h, t.filter(predicate))
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
  return new ConsAnonymFunction(2, [3].filter(n%2 == 0))
  new ConsAnonymFunction(2, EmptyAnonymFunction.filter(n%2 == 0))
  = new ConsAnonymFunction(2, EmptyAnonymFunction)
  * */


  /*
  [1,2] ++ [3,4,5]
  = new  ConsAnonymFunction(1, [2] ++ [3,4,5])
  = new  ConsAnonymFunction(1, new ConsAnonymFunction(2, Empty ++[3,4,5]))
  = new  ConsAnonymFunction(1, new ConsAnonymFunction(2, new  ConsAnonymFunction(3, new  ConsAnonymFunction(4, =ew ConsAnonymFunction(5)))))
   */

  def ++[B >: A](list: MyListAnonymFunction[B]): MyListAnonymFunction[B] = new ConsAnonymFunction(h, t ++ list)


  def flatMap[B](transformer: A => MyListAnonymFunction[B]): MyListAnonymFunction[B] = {
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