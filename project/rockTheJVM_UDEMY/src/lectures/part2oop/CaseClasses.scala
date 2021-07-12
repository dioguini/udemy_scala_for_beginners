package lectures.part2oop

object CaseClasses extends App {

  /*
  PROBLEM WE WANT TO SOLVE:
    often, for lightweight data structures we need to reimplement all sorts of boilerplate, like companion objects, standard methods for serializing, hashCode, toString, equals...
   Solution:
   Case classes are an exceptionally useful shorthand for defining a class and the companion object and a lot of sensible defaults in one go.
   They are perfect for creating this kind of lightweight data, holding cases with really minimum of hassle
  */


  case class Person(name: String, age: Int)

  //class parameters are fields
  val jim = new Person("Jim", 34)
  println(jim.name) //not possible if "Person" wasn't a cse class

  //sensible toString method
  //not "toString" needed. Just print the object. See below
  println(jim.toString) //not possible if "Person" wasn't a case class. It would print something like "lectures.part2oop.Person@5594a1b5"
  println(jim) //same as above, toString not needed

  //equals and hasCode implemented OOTB
  val jim2 = new Person("Jim", 34)
  println(jim == jim2) //returns true.
  //Above if "case" wasn't used on "Person", "jim" and "jim2" would be 2 different instances of Person (like they are if "case" is used) BUT "==" would return false, because it compares two two different instances of class "Person" but "equals" wasn't implemented so it would return false. Using "case" "equals" method is already defined and so it returns true.

  //case classes have handy copy methods (copy() creates a new instance of the case class)
  val jim3 = jim.copy(age=23)//It's the same as creating person with the same params used for "jim", except for "age".
  val jim4 = jim.copy()//It's the same as creating person with the same params used for "jim".

  println((jim3, jim4))


  //case classes have companion objects
  val thePerson = Person
  val mary = Person("Mary", 23) //uses the "apply" method (which makes the companion object callable like a function) from the "Person" class. So, it does the same thing as the constructor, so in practice we don't really use the keyword "new" when instantiating a case class


  //case classes are serializable -> when working on distributed systems, it is possible to send instances of this classes through the network and in between jvms
  //mostly used in AKKA framework

  //case classes have extractor patterns -> can be used in Pattern Matching


  //there is a "case object" too ...
  //they have the same properties as case classes, EXCEPT they don't get companion objects, because they are their own companion objects

  case object UnitedKingdom {
  def name: String = "The UK of GB and NorIrela nd"
  }

}
