package exercises


object Generics_Exercise extends App {
  val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val listOfStrings: MyList[String] = new Cons("Hello", new Cons("everyone", Empty))
  println(listOfIntegers.toString)
  println(listOfStrings.toString)



}


/**
 * The class belw, can be compared with MyList, on this package.
 */

abstract class MyList[+A] {
  // This represents an immutable List, BECAUSE we are not modifying this instance, BUT YES returning a new List
  // The methods that tells us that above, are "add" and "tail", because they RETURN A NEW LIST. The other ones, are just a simple "select"/"definition" of something

  def head: A

  def tail: MyList[A]

  def isEmpty: Boolean

  def add[B >: A](element: B): MyList[B] //same as the Cats, Dogs and Animal example. See "add" method on part2oop.Generics

  def printElements: String

  /*
  polymorphic call
  -> toString method calls "printElements"
  -> when we call later "toString" method, the correct implementation of "printElements" will be called, that why this is a polymorphic call
   */
  override def toString: String = {
    "[" + printElements + "]"
  }


}

/*
Lets see the below declarations:
  val listOfIntegers: MyList[Int] = Empty
  val listOfStrings: MyList[String] = Empty
  "Empty" should be a proper value for both types of lists of Int and String
  But how do we do that?

We're going to use "Nothing" below" because ??????
* */
object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException //does not make sense for an Empty list...

  def tail: MyList[Nothing] = throw new NoSuchElementException //does not make sense for an Empty list...

  def isEmpty: Boolean = true

  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty) //when we add an element for the first time, list just have 1 element. So, "element" param is the head, and the tail of the list is Empty object

  def printElements: String = ""

}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A] { //"Cons" must be Covariant, accordingly MyList
  def head: A = h

  def tail: MyList[A] = t

  def isEmpty: Boolean = false

  def add[B >: A](element: B): MyList[B] = new Cons(element, this) //the element is the one added, and the tail is the list itself ("this")

  def printElements: String = {
    if (t.isEmpty) "" + h
    else {
      h + " " + t.printElements
    }
  }

}



