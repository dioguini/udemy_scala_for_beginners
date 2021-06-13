package lectures.part2oop


object Objects extends App {
  //in scala an Object is a dedicatd concept, which is the apposite to Java, where an object is an instance of a class
  //SCALA DOES NOT HAVE CLASS LEVEL FUNCTIONALITY, scala does not know th concept of "static" --> Scala has "Objects"
  //So, in Scala to ue Class Level Definition, we use Objects
  /*
  Scala objects:
  are in their own class
  are the only instance
  singleton pattern in one line
  */

  /*
    Code below does EXACTLY the same as the JAVA code below on this class. There are some differences between both approaches

    Objects can have values, vars and methods.

   */
  object Person { //Type and Instance definitions
    //"static"/"class" level
    val _EYES = 2
    def ableToFly: Boolean = {
    false
    }
  }

  println(Person._EYES)
  println(Person.ableToFly)

  val mary = Person //instance definition only (Person type, already defined above)
  val john = Person
  println(mary == john) //returns true, because they are the same type. Basically, they are both a singleton object (Object Person), so they mean that there is a single instance that can be referred to with the name person


  val mary_1 = new Person //instance definition only (Person type, already defined above)
  val john_1 = new Person
  println(mary_1 == john_1) //returns FALSE --> because they are different instances! here we are creating different instance of "Person". Above we were creating two equal objects

  class Person {
    //instance level functionality
  }



  //Factory Methods
  object Persona {

    //factory method -> purpose is to build a new Persona given some parameters
    def apply(mother: Persona, father: Persona) = new Persona("newPersona")
  }

  class Persona (val name: String) {
  }
  val diogo = new Persona ("diogo")
  val marta = new Persona ("marta")


  val bobby = Persona(diogo, marta) //look, the code is very similar to a constructor   (Persona.apply(diogo, marta) also works )

  //Scala applications -> Scala object with a VERY PARTICULAR METHOD, main,  that receives an Array as an input
  /*
   def main(args: Array[String]): Unit = {/*code goes here*/} <-- exact definition
      why this definition? -> Scala applications turn into JVM applications whose entry points HAVE TO BE public static void (Unit in Scala) main with an array of string as parameter
  that is ahy we use  "extends App {"  because App already has that main method
   */




}


/**
 * Differences Scala/Java
   SCALA
      We use an Object as a Singleton Instance (when we define the object "person" we are defining its type, but also its instance
      uses Companions --> We write objects and classes with the same name in the same file/scope --> separation of instance level functionality and "static"/"class" level
        On this case class Person and object Person are companions because thei are on the same scope and have the same name
         which means that all the code that will ever going to access will be accessed from some kind of instance either a regular instance (class person) or the Singleton instance (object Person) --> making Scala a truly OOP Language!!
          -->The code written on a class can be accessed from an instance
          -->The code written on a object can be accessed by a Singleton object

  Scala companions:
 can access each other's private members
 */





/**



--JAVA--

public class JavaPlayground {

    public static void main (String args[]){
        System.out.println(Person.N_EYES); --->>>> CLASS LEVEL FUNCTIONALITY -> access a value directly from a class instead creating an instance of a class (new Person P = new Persion() ... p.N_EYES; )
    }

}


class Person {
  public static final int N_EYES = 2;
}


 */
