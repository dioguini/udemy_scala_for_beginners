package exercises

object MyList_Function extends App {

  val listOfIntegers: MyList_FP[Int] = new Cons_FP(1, new Cons_FP(2, new Cons_FP(3, Empty_FP)))
  val cloneListOfIntegers: MyList_FP[Int] = new Cons_FP(1, new Cons_FP(2, new Cons_FP(3, Empty_FP)))
  val listOfStrings: MyList_FP[String] = new Cons_FP("Hello", new Cons_FP("everyone", Empty_FP))

  val anotherListOfIntegers: MyList_FP[Int] = new Cons_FP(4, new Cons_FP(5, new Cons_FP(6, Empty_FP)))


  //case classes verifications
  /**
   * returns true, because we are using case classes.
   * if not used, we would need to define a recursive "equals" method, which have been a great headache because we would need to compare all the elements recursively
   */
  //println(listOfIntegers == cloneListOfIntegers)

  println("Original values for list: " + listOfIntegers)
  println("Values for transform: " + listOfIntegers.map(new Function1[Int, Int] {
    override def apply(elem: Int): Int = elem * 2
  }).toString)

  /*
  this is an anonymous class

  new MyTransformerFPExercise[Int, Int] {
    override def transform(elem: Int): Int = elem * 2
  }
  * */

  println("Values for test: " + listOfIntegers.filter(new Function1[Int, Boolean] {
    override def apply(Elem: Int): Boolean = Elem % 2 == 0
  }).toString)


  println((listOfIntegers ++ anotherListOfIntegers).toString)
  println(listOfIntegers.flatMap(new Function1[Int, MyList_FP[Int]] {
    override def apply(Elem: Int): MyList_FP[Int] = new Cons_FP(Elem, new Cons_FP(Elem + 1, Empty_FP))
  }).toString)
  //[1 2  2 3  3 4]


}


/**
 * The class below, can be compared with MyList_FP, on this package.
 */

abstract class MyList_FP[+A] {
  // This represents an immutable List, BECAUSE we are not modifying this instance, BUT YES returning a new List
  // The methods that tells us that above, are "add" and "tail", because they RETURN A NEW LIST. The other ones, are just a simple "select"/"definition" of something

  def head: A

  def tail: MyList_FP[A]

  def isEmpty_FP: Boolean

  def add[B >: A](element: B): MyList_FP[B] //same as the Cats, Dogs and Animal example. See "add" method on part2oop.Generics

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
  def map[B](transformer: A => B): MyList_FP[B] //same as def map[B](transformer: Function1[A, B]): MyList_FP[B]

  def flatMap[B](transformer: A => MyList_FP[B]) : MyList_FP[B]

  //concatenation
  def ++[B >: A](list: MyList_FP[B]): MyList_FP[B]

  //since flatMap needs a concatenation we need to define this method

  def filter(predicate: A => Boolean): MyList_FP[A]

}

/*
Lets see the below declarations:
  val listOfIntegers: MyList_FP[Int] = Empty_FP
  val listOfStrings: MyList_FP[String] = Empty_FP
  "Empty_FP" should be a proper value for both types of lists of Int and String
  But how do we do that?

We're going to use "Nothing" below" because ??????
* */
object Empty_FP extends MyList_FP[Nothing] { //"case" makes this object extremely powerful -> "equals" and "hash" are implemented OOTB , and it's serializable
  def head: Nothing = throw new NoSuchElementException //does not make sense for an Empty_FP list...

  def tail: MyList_FP[Nothing] = throw new NoSuchElementException //does not make sense for an Empty_FP list...

  def isEmpty_FP: Boolean = true

  def add[B >: Nothing](element: B): MyList_FP[B] = new Cons_FP(element, Empty_FP) //when we add an element for the first time, list just have 1 element. So, "element" param is the head, and the tail of the list is Empty_FP object

  def printElements: String = ""

  //Exercise3
  def map[B](transformer: Nothing => B): MyList_FP[B] = Empty_FP

  def filter(predicate: Nothing => Boolean): MyList_FP[Nothing] = Empty_FP

  def ++[B >: Nothing](list: MyList_FP[B]): MyList_FP[B] = list //anything concatenated with empty ,is that thing
  def flatMap[B](transformer: Nothing => MyList_FP[B]): MyList_FP[B] = Empty_FP


}

class Cons_FP[+A](h: A, t: MyList_FP[A]) extends MyList_FP[A] { //"Cons_FP" must be Covariant, accordingly MyList_FP
  def head: A = h

  def tail: MyList_FP[A] = t

  def isEmpty_FP: Boolean = false

  def add[B >: A](element: B): MyList_FP[B] = new Cons_FP(element, this) //the element is the one added, and the tail is the list itself ("this")

  def printElements: String = {
    if (t.isEmpty_FP) "" + h
    else {
      h + " " + t.printElements
    }
  }


  //Exercise3
  def map[B](transformer: A => B): MyList_FP[B] = {
    //the output is a Cons, where the head is going to be transformed by the transformer
    //new Cons_FP(transformer.transform(h), ... ) -> head of the result
    //new Cons_FP(.. , t.map(transformer)) -> recursive call
    new Cons_FP(transformer(h), t.map(transformer))
  }

  /*
  How map works
  lets say we have list[1,2,3]
  and we are going to do list[1,2,3].map(n*2)
  = new Cons_FP(2, [2,3].map(n*2)) -> head of list is "1", multiply by 2 AND the tail is [2,3).map(n*2)
  = new Cons_FP(2, new Cons_FP(4, [3].map(n*2))) -> head of list is "2", multiply by 2 AND the tail is [3,3).map(n*2)
  = new Cons_FP(2,4, new Cons_FP(6, Empty_FP.map(n*2)))
  = new Cons_FP(2, new Cons_FP(4, new Cons_FP(6, Empty_FP))))

  * */


  def filter(predicate: A => Boolean): MyList_FP[A] = {
    //testing if head satisfies the predicate (if predicate.head passes the test)
    //if passes the test, head, "h" will be included in the result, so must return "new Cons(h)" with the predicate so the tail needs to be filtered and the to be passed in as the result. so we have   new Cons(h,t.filter(predicate))
    //basically if head passes the predicate, it will be included in the result, so the result will be certainly a "cons with the head". Te tail will be filtered with a predicate,so we don't know what that will return (might be an empty list, but we will just delegate to the recursive call with filter
    if (predicate(h)) new Cons_FP(h, t.filter(predicate))
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
  return new Cons_FP(2, [3].filter(n%2 == 0))
  new Cons_FP(2, Empty_FP.filter(n%2 == 0))
  = new Cons_FP(2, Empty_FP)
  * */


  /*
  [1,2] ++ [3,4,5]
  = new  Cons_FP(1, [2] ++ [3,4,5])
  = new  Cons_FP(1, new Cons_FP(2, Empty ++[3,4,5]))
  = new  Cons_FP(1, new Cons_FP(2, new  Cons_FP(3, new  Cons_FP(4, =ew Cons_FP(5)))))
   */

  def ++[B >: A](list: MyList_FP[B]): MyList_FP[B] = new Cons_FP(h, t ++ list)


  def flatMap[B](transformer: A => MyList_FP[B]): MyList_FP[B] = {
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