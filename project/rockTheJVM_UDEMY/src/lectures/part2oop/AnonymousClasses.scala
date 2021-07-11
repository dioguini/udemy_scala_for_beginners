package lectures.part2oop

object AnonymousClasses extends App {

  abstract class Animal {
    def eat: Unit
  }

  //code below shows an instantiation of an abstract class, not a typical "instance of a class". Say what!?!?!? Lets dig... In fact we instantiated a real class
  //this is an anonymous class
  val funnyAnimal: Animal = new Animal{
    override def eat: Unit = println("eheheh")
  }
  println(funnyAnimal.getClass)

  /**
    using println(funnyAnimal.getClass) it prints: class lectures.part2oop.AnonymousClasses$$anon$1

   The reason we are instantiating a real class because, behind the scenes for use humans the compiler does:
   1)
    looks at the code
      val funnyAnimal: Animal = new Animal{
      override def eat: Unit = println("eheheh")
      }
   and sees "oh it's a "new Animal with de method eat overriden" so let me create a new class for it

   2)
   creates the anonymous class lectures.part2oop.AnonymousClasses$$anon$1:
    class lectures.part2oop.AnonymousClasses$$anon$1 extends Animal {
    override def eat: Unit = println("eheheh")
  }

   3)
   assigns the class above to "funnyAnimal"
   funnyAnimal = lectures.part2oop.AnonymousClasses$$anon$1

   Basically:
   this:
     val funnyAnimal: Animal = new Animal{
      override def eat: Unit = println("eheheh")
      }

   is equivalent to:
     class lectures.part2oop.AnonymousClasses$$anon$1 extends Animal {
      override def eat: Unit = println("eheheh")
      }
      val funnyAnimal: Animal = new AnonymousClasses$$anon$1
   */


  /**Rules for abstract classes

   Pass in required constructor arguments if needed
   Implement all abstract fields/methods

   Anonymous classes work for traits and classes, whether they're abstract or not

   */


}
}
