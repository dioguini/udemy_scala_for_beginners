package exercises


object Generics extends App {
  val listOfIntegers: MyList_Covariance[Int] = new Cons_Covariance(1, new Cons_Covariance(2, new Cons_Covariance(3, Empty_Covariance)))
  val listOfStrings: MyList_Covariance[String] = new Cons_Covariance("Hello", new Cons_Covariance("everyone", Empty_Covariance))
  println(listOfIntegers.toString)
  println(listOfStrings.toString)



}


/**
 * The class below, can be compared with MyList_Covariance, on this package.
 */

abstract class MyList_Covariance [+A] {
  // This represents an immutable List, BECAUSE we are not modifying this instance, BUT YES returning a new List
  // The methods that tells us that above, are "add" and "tail", because they RETURN A NEW LIST. The other ones, are just a simple "select"/"definition" of something

  def head: A

  def tail: MyList_Covariance[A]

  def isEmpty_Covariance: Boolean

  def add[B >: A](element: B): MyList_Covariance[B] //same as the Cats, Dogs and Animal example. See "add" method on part2oop.Generics

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
  val listOfIntegers: MyList_Covariance[Int] = Empty_Covariance
  val listOfStrings: MyList_Covariance[String] = Empty_Covariance
  "Empty_Covariance" should be a proper value for both types of lists of Int and String
  But how do we do that?

We're going to use "Nothing" below" because ??????
* */
object Empty_Covariance extends MyList_Covariance[Nothing] {
  def head: Nothing = throw new NoSuchElementException //does not make sense for an Empty_Covariance list...

  def tail: MyList_Covariance[Nothing] = throw new NoSuchElementException //does not make sense for an Empty_Covariance list...

  def isEmpty_Covariance: Boolean = true

  def add[B >: Nothing](element: B): MyList_Covariance[B] = new Cons_Covariance(element, Empty_Covariance) //when we add an element for the first time, list just have 1 element. So, "element" param is the head, and the tail of the list is Empty_Covariance object

  def printElements: String = ""

}

class Cons_Covariance[+A](h: A, t: MyList_Covariance[A]) extends MyList_Covariance[A] { //"Cons_Covariance" must be Covariant, accordingly MyList_Covariance
  def head: A = h

  def tail: MyList_Covariance[A] = t

  def isEmpty_Covariance: Boolean = false

  def add[B >: A](element: B): MyList_Covariance[B] = new Cons_Covariance(element, this) //the element is the one added, and the tail is the list itself ("this")

  def printElements: String = {
    if (t.isEmpty_Covariance) "" + h
    else {
      h + " " + t.printElements
    }
  }

}



