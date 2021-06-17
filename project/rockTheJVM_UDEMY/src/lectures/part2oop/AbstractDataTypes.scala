package lectures.part2oop

object AbstractDataTypes extends App {

  //abstract --> when methods, fields or classes are left in blank/unimplemented (subclasses can do that by us)
  //absract classes can not be instantiated (obviously, because the are not implemented)

  abstract class Animal{
    val creatureType: String
    def eat:Unit



  }


  class Dog extends Animal {
    val creatureType: String = "Canine" //"override" can be used or not
    def eat: Unit = println("crunch crunch")
  }


  /*
  TRAITS
  like abstract classes also have abstract fields
  What's the difference?
    TRAITS CAN BE inherited along classes
  */
trait Carnivore {
    def eat(animal: Animal): Unit
  }

trait ColdBlooded
  class Crocodile extends Animal with Carnivore with ColdBlooded { //can have multiple traits here and must implement EVERYTHING which is inside "Animal" and "Carnivore"
    override val creatureType: String = "croc"
    def eat: Unit = println("yumiii")

  def eat(animal: Animal): Unit =println(s"I'm a $creatureType and I'm eating ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)

  /*
  TRAITS vs ABSTRACT CLASSES
  -Both can have both abstract/non abstract members
  -Traits DO NOT HAVE CONSTRUCTOR PARAMETERS
    -> so the code bellow isn't possible
    trait Carnivore (name: String) {
    ...
    }
  -Multiple traits may be inherited by the same class
  -Only one class can be inherited

  -Tratis -> represent behaviours (describe what "something" DOES )
  -Abstract Class - type of "thing"

  */
}
