package lectures.part2oop

object MethodNotations extends App {

  val me = new Person(name="Diogo", favoriteMovie = "Inception")
  println(me.likes(movie = "Inception"))
  println(me likes "Inception") //equivalent as above. INFIX NOTATION (operator notation - syntactic sugar) -> only works with methods with ONE PARAMETER
  //Syntatic sugar -> nicer ways of writing code that are equivalent to more complex or more cumbersome ways of writhing code. Basically we can use the natural language





    class Person (val name: String, favoriteMovie: String ){
      def likes(movie: String): Boolean = {
        movie == favoriteMovie
      }

      def hangOutWith(person: Person): String = { //used for infix notation example
        s"${this.name} is hanging out with ${person.name}"
      }

      def +(person: Person): String = { //valid method!!! "+"
        s"${this.name} is hanging out with ${person.name}"
      }

      def unary_! : String = { //used for prefix notation example
      s"$name, what the heck!?"
      } // needs to be " : " otherwise compiler will interpret as "unary_!:" and it's not corret

      def isAlive(): Boolean = { //used for postfix notation example
        true

      }

      def apply(): String = { // used for apply example. "()" are very important
        s"Hi my name is $name and I love $favoriteMovie"

      }


    }


  /**
   * Operators in scala
   */

  val marta = new Person(name="Marta", favoriteMovie = "Minions" )
  print(me hangOutWith marta)  //the same print(me hangOutWith(marta)) AND THE SAME AS  print(me.hangOutWith(marta))

  //hangOutWith acts like an operator between two Things returning a 3rd thing
  print(me + marta) // hey! "+" do not forget "+" is a valid name for a method
  print(me.+(marta)) //same as above


  /**
   * ALL OPERATORS ARE METHODS!!!!
   */

  println(1+2)
  println(1.+(2)) //1. -> "1" is a number and so we can accessing its methods. To do so, just do the typical "." 1. -> all methods for "1"

  //Akka actors have ! and ?


  //Syntatic sugar examples:

  //infix (above on the top)

  //prefix notation
  val x = -1 //equivalent with 1.unaru_-
  val y = 1.unary_-

  // "unary_" prefix only works with "-" , "+" , "~" and "!"
  println(!me)
  println(me.unary_!) //the same as above


  //postfix
  //function that NOT RECEIVE ANY PARAMETERS have the property they can be used in a postfix notation
  println(me.isAlive()) //commonly used because when reading the code, we can infer potential ambiguities
  println(me isAlive()) //postfix notation


  //apply
  println(me.apply())
  println(me()) // -> acts like if "me" was a function, BECAUSE we have defined the "apply()" method above. When we use that syntax, compiler will look for the "apply" definition of the particular class


}
