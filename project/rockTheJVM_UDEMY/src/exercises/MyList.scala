package exercises


object testList extends App {
  val list1 = new Cons(1, Empty)
  println(list1.head)

  val list2 = new Cons(1, new Cons(2, new Cons(3, Empty)))
  println(list2.tail.head) //prints 2. list(1,2,3), and we want to print the head of the tail. So, if tail is (2,3), the head of it will be 2

  println(list2.add(4).head) //list(4,1,2,3

  println(list2.isEmpty)

  println(list2.toString)

}




abstract class MyList {
  // This represents an immutable List, BECAUSE we are not modifying this instance, BUT YES returning a new List
  // The methods that tells us that above, are "add" and "tail", because they RETURN A NEW LIST. The other ones, are just a simple "select"/"definition" of something

  def head: Int

  def tail: MyList

  def isEmpty: Boolean

  def add(element: Int): MyList

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

  object Empty extends MyList {
    def head: Int = throw new NoSuchElementException //does not make sense for an Empty list...

    def tail: MyList = throw new NoSuchElementException //does not make sense for an Empty list...

    def isEmpty: Boolean = true

    def add(element: Int): MyList = new Cons(element, Empty) //when we add an element for the first time, list just have 1 element. So, "element" param is the head, and the tail of the list is Empty object

    def printElements: String = ""

  }

  class Cons(h: Int, t: MyList) extends MyList {
    def head: Int = h

    def tail: MyList = t

    def isEmpty: Boolean = false

    def add(element: Int): MyList = new Cons(element, this) //the element is the one added, and the tail is the list itself ("this")

    def printElements: String = {
      if (t.isEmpty) "" + h
      else {
        h + " " + t.printElements
      }
    }

  }



