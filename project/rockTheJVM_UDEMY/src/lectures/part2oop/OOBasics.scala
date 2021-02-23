package lectures.part2oop

object OOBasics extends App {

val person = new Person("Diogo", 27)
  println(person.age)
  person.greeting("zÉ")

}


class Person (name: String, val age: Int) {
//class Person (name: String, val age: Int=0) { --> We are setting the age is 0 by default which means we will not need to define an auxiliary constructor to define age as 0. So the constructor on line 29 does not make sense anymore when  (def this (name: String) = this(name, 0) )
  // on the class body we can have everything
  // everytime we instantiate the class THE WHOLE CODE ON ITS BODY WILL BE EXECUTED. So, if we have println() they will appear then.

  val z = 2 //z is a field!!! above, we can access it, by: person.z

  //method, not a function
  def greeting2(name: String): Unit = println(s"$name says hey $name") //prints Diogo says hey Diogo
  def greeting(name: String): Unit = println(s"${this.name} says hey $name") //this prints Diogo says hey <name>. When we have "same params name", we use "this" to refer to the argument of the class.
  def greeting3(nameOfOtherPerson: String): Unit = println(s"${this.name} says hey $nameOfOtherPerson") //this does the same as above.

  //overloading -> defining METHODS with the SAME NAME BUT WITH DIFFERENT SIGNATURE (different number of params, or we can have also the same number of params BUT with different types)
  //same name, different return IS NOT overloading. it just does not work
  def greeting(): Unit = println(s"hey I am $name")

  //we can also overload constructors...
  def this (name: String) = this(name, 0) // -> we are defining this constructor just to ommit the age parameter. HUMMMM.... seems does not make sense! Se note below about Defining auxiliary constructors...
 // Frustration with auxiliary constructors: the implementation of a secondary constructor HAS TO BE another constructor and nothing else

  def this() = this("Jonh Doe")
  //this is the only implementation that an auxiliary constructor can have

  /**
   * Defining auxiliary constructors is quite impractical because they are only used in practice for default parameters
   * The solution is to supply a default parameter to the actual class definition... Se above the Person definition
   * 
   */

  


}// constructor
//in JAVA we have specific methods that are constructors and we can have multiple constructors with the same class name (the difference between them is the arguments to construct a new object)
//In SCALA we define the PARAMETER on the class definition
/*
Class PARAMETERS <> Class MEMBER
Class parameter -> are not fields! we use them just on the constructor of the class. not possible to access them like in java.
Class member -> these are the fields. we access them like we do in java.

So:
class Person (name: String, age: Int)
val person = new Person("Diogo", 27)
  println(person.age) // not possible!
      
But a class parameter can be converted into a field. How? just add the "val" before on the class definition
class Person (name: String, val age: Int)

person.age is now possible
*/