package exercises


object Generics extends App {
  val listOfIntegers: MyCovarianceList[Int] = new ConsCovariance(1, new ConsCovariance(2, new ConsCovariance(3, EmptyCovariance)))
  val listOfStrings: MyCovarianceList[String] = new ConsCovariance("Hello", new ConsCovariance("everyone", EmptyCovariance))
  println(listOfIntegers.toString)
  println(listOfStrings.toString)



}


/**
 * The class below, can be compared with MyCovarianceList, on this package.
 */

abstract class MyCovarianceList [+A] {
  // This represents an immutable List, BECAUSE we are not modifying this instance, BUT YES returning a new List
  // The methods that tells us that above, are "add" and "tail", because they RETURN A NEW LIST. The other ones, are just a simple "select"/"definition" of something

  def head: A

  def tail: MyCovarianceList[A]

  def isEmptyCovariance: Boolean

  def add[B >: A](element: B): MyCovarianceList[B] //same as the Cats, Dogs and Animal example. See "add" method on part2oop.Generics

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
  val listOfIntegers: MyCovarianceList[Int] = EmptyCovariance
  val listOfStrings: MyCovarianceList[String] = EmptyCovariance
  "EmptyCovariance" should be a proper value for both types of lists of Int and String
  But how do we do that?

We're going to use "Nothing" below" because ??????
* */
object EmptyCovariance extends MyCovarianceList[Nothing] {
  def head: Nothing = throw new NoSuchElementException //does not make sense for an EmptyCovariance list...

  def tail: MyCovarianceList[Nothing] = throw new NoSuchElementException //does not make sense for an EmptyCovariance list...

  def isEmptyCovariance: Boolean = true

  def add[B >: Nothing](element: B): MyCovarianceList[B] = new ConsCovariance(element, EmptyCovariance) //when we add an element for the first time, list just have 1 element. So, "element" param is the head, and the tail of the list is EmptyCovariance object

  def printElements: String = ""

}

class ConsCovariance[+A](h: A, t: MyCovarianceList[A]) extends MyCovarianceList[A] { //"ConsCovariance" must be Covariant, accordingly MyCovarianceList
  def head: A = h

  def tail: MyCovarianceList[A] = t

  def isEmptyCovariance: Boolean = false

  def add[B >: A](element: B): MyCovarianceList[B] = new ConsCovariance(element, this) //the element is the one added, and the tail is the list itself ("this")

  def printElements: String = {
    if (t.isEmptyCovariance) "" + h
    else {
      h + " " + t.printElements
    }
  }

}



