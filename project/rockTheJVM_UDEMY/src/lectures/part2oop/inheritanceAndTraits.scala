package lectures.part2oop

object inheritanceAndTraits extends App {

  //single class inheritance

  class Animal {
    def method1 = println("m_1") //public, by default. available for everyone
    protected def method2 = println("m_2") //available for main and subclases
    private def method3 = println("m_3") //available here only
    def eat = println ("hummmhummmm")
    val creatureType = "wild"


  }

  class Cat extends Animal {
    def crunch = {
      //method1 from Animal, is available
      method1 //possible because it is public
      method2 //available because it's a subclass of Animal
      //method3 is not available because it's private
      println("crunching")
    }
  }


  val cat = new Cat
  cat.crunch //possible because it is public
  cat.method1 //possible because method1 is public
  //cat.method2 not possible, because cat isn't a subclass of Aniaml. It is an instance of Cat
  //cat.method3 not possible because method3 is private






  /*
  CONSTRUCTORS
  */
  class Person(name: String, age: Int){}
  //class Adult(name: String, age: Int, idCard: String) extends Person --> does not work. There is no Person without arguments. JVM always looks for method on the main class first, on this case Person-
  class Adult(name: String, age: Int, idCard: String) extends Person(name, age) // ok!



  class Person_1(name: String, age: Int){
    def this(name: String) = this(name,0) //this is an auxiliary constructor. we are setting 0 to age
  }
  class Adult_1(name: String, age: Int, idCard: String) extends Person_1(name) // ok! a constructor with just "name" on superclass Person exists, so this "calling method" is perfectly ok!





  /*
  OVERRIDING - supplying a different implementation in derived classes
  overloading - supplying multiple methods with different signatures but with the same name IN the same class
 */
  class Dog extends Animal {
    override def eat = println("nhame, nhami") //do not forget, eat a public method
    override val creatureType = "domestic"
  }
  val dog = new Dog
  dog.eat
  println(dog.creatureType)




  //vals and vars can be overwritten in the constructor as well
  class Dog1(override val creatureType: String) extends Animal {
    override def eat = println("nhame, nhami") //do not forget, eat a public method
  }
  val dog1 = new Dog1("K9")
  dog1.eat
  println(dog1.creatureType)

  class Dog2(dogType: String) extends Animal {
    override def eat = println("nhaca, nhaca") //do not forget, eat a public method
    override val creatureType = dogType
  }
  val dog2 = new Dog1("canine")
  dog2.eat
  println(dog2.creatureType)



  /*
  POLYMORPHISM - type substitution
  a method call will always go to the most overriden version whenever possible ->  power of polymorphism
  we can actually use "Animal" type , but inside they can be a derived instance (Dog2)
     --> we can have a collection of "animal" like dog, cat and giraffes. IF we call the "eat" method on all of them, each instance will know the derived implementation of "eat" to call
  */

  val unknownAnimal: Animal = new Dog2("testing")
  unknownAnimal.eat //prints nhaca, nhaca because DOg2 USES THE OVERRIDDEN IMPLEMENTATION of eat. "unknownAnimal" is an Animal, BUT this is an instance of Dog2, so WILL USE Dog2 METHODS INSTEAD


  /*
  SUPER - used when you want to reference a method/fields FROM A PARENT CLASS
  */
  class Dog3(override val creatureType: String) extends Animal {
    override def eat = {
      super.eat
      println("nhame, nhami")
    }
  }
  val dog3 = new Dog1("K9")
  dog3.eat


  /*
  PREVENTING OVERRIDES
  1 - using keyword "FINAL" - on members (vals, vars or methods)
  2 - using keyword "FINAL" - on class - the entire class WONT BE EXTENDED
  3 - seal the class - extend classes IN THIS CLASS, PREVENT extension in OTHER FILE
    --often used when want to be exhaustive in our type hierarchy:
      seal the class "Animal" and only he ones available are in this file (Dog and Cat and/or other ones). We just need to extend them in a normal way.
  */



}
