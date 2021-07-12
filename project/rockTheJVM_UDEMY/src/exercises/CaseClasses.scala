package exercises

object CaseClasses extends App {

  val listOfIntegers: MyCaseClassList[Int] = new ConsCaseClass(1, new ConsCaseClass(2, new ConsCaseClass(3, EmptyCaseClass)))
  val cloneListOfIntegers : MyCaseClassList[Int] = new ConsCaseClass(1, new ConsCaseClass(2, new ConsCaseClass(3, EmptyCaseClass)))
  val listOfStrings: MyCaseClassList[String] = new ConsCaseClass("Hello", new ConsCaseClass("everyone", EmptyCaseClass))


  //case classes verifications
  /**
  returns true, because we are using case classes.
   if not used, we would need to define a recursive "equals" method, which have been a great headache because we would need to compare all the elements recursively
   */
  println(listOfIntegers == cloneListOfIntegers)




}


/**
 * The class below, can be compared with MyCaseClassList, on this package.
 */

abstract class MyCaseClassList [+A] {
  // This represents an immutable List, BECAUSE we are not modifying this instance, BUT YES returning a new List
  // The methods that tells us that above, are "add" and "tail", because they RETURN A NEW LIST. The other ones, are just a simple "select"/"definition" of something

  def head: A

  def tail: MyCaseClassList[A]

  def isEmptyCaseClass: Boolean

  def add[B >: A](element: B): MyCaseClassList[B] //same as the Cats, Dogs and Animal example. See "add" method on part2oop.Generics

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
  val listOfIntegers: MyCaseClassList[Int] = EmptyCaseClass
  val listOfStrings: MyCaseClassList[String] = EmptyCaseClass
  "EmptyCaseClass" should be a proper value for both types of lists of Int and String
  But how do we do that?

We're going to use "Nothing" below" because ??????
* */
case object EmptyCaseClass extends MyCaseClassList[Nothing] { //"case" makes this object extremely powerful -> "equals" and "hash" are implemented OOTB , and it's serializable
  def head: Nothing = throw new NoSuchElementException //does not make sense for an EmptyCaseClass list...

  def tail: MyCaseClassList[Nothing] = throw new NoSuchElementException //does not make sense for an EmptyCaseClass list...

  def isEmptyCaseClass: Boolean = true

  def add[B >: Nothing](element: B): MyCaseClassList[B] = new ConsCaseClass(element, EmptyCaseClass) //when we add an element for the first time, list just have 1 element. So, "element" param is the head, and the tail of the list is EmptyCaseClass object

  def printElements: String = ""

}

case class ConsCaseClass[+A](h: A, t: MyCaseClassList[A]) extends MyCaseClassList[A] { //"ConsCaseClass" must be Covariant, accordingly MyCaseClassList
  def head: A = h

  def tail: MyCaseClassList[A] = t

  def isEmptyCaseClass: Boolean = false

  def add[B >: A](element: B): MyCaseClassList[B] = new ConsCaseClass(element, this) //the element is the one added, and the tail is the list itself ("this")

  def printElements: String = {
    if (t.isEmptyCaseClass) "" + h
    else {
      h + " " + t.printElements
    }
  }

}



