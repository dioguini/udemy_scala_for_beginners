package lectures.p4_PatternMatching

import scala.util.Random

object PatternMatching extends App {


  val random = new Random()
  val x = random.nextInt(10) //up to 10

  val description = x match {
    case 1 => "the one"
    case 2 => " double or nothing"
    case 3 => " third time is the charm"
    case _ => "something else" // -> "_" is a wildcard, and matches ANYTHING
  }

  println(x)
  println(description)
  /*
  what we have above, is called Pattern Matching, and tries to match a value against multiple patterns (that is written with case).
  Works kind of "switch" statement, but is much powerful!

  -> Cases are matched in order
  -> When no cases match (absence of "case _" ) an "MatchError" is raised
  -> What is the type of the Pattern Matching expression? Unified tyoe of all the tyoes in all the cases

    val description = x match {
    case 1 => "the one"
    case 2 => " double or nothing"
    case 3 => " third time is the charm"
    case _ => "something else" // -> "_" is a wildcard, and matches ANYTHING
    }
    ---> "description" is String type -> all returns are String

    val description = x match {
    case 1 => "the one"
    case 2 => " double or nothing"
    case 3 => " third time is the charm"
    case _ => 42
    }
    ---> "description" of type "Any"
        Compiler tries to unify String and Int and so the result is "Any", because String and Int are completely unrelated
        So the compiler will try to unify the types for the developer and returns the lowest common ancestor of all the types returned by all the cases
  -> Pattern Matching works really will with case case classes
   */




  /*1 - Decompose values (case classes)

  Can decompose value (specially used in conjunction with case classes)
  Case classes have the ability to be deconstructed or extracted in pattern matching
  -> we basically can do a pattern matching against a Case Class and extrtact the values out of an object (an instance of a case class)

  Case classes are very powerful for Pattern Matching, because it is possible to use the extractor pattern out of the box


  */
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(n,a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I don't know who I am"
  }
  /* if "bob" is a person, this pattern match expression, is able to deconstruct bob into its constituint part and so is able to extract name (n) and age(a) out of bob, even though the pattern of expression doesn't know them beforehand*/

  println("1 - decomposing classes")
  println(greeting)



  // 2 - Gards

  case class Person2(name: String, age: Int)
  val bob2 = Person("Bob", 17)

  val greeting2 = bob2 match {
    case Person(n,a) if a < 21=> s"Hi, my name is $n and I am $a years old and I can't drink in US" //there's an implicit "and" before if
    case Person(n,a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I don't know who I am"
  }
  println("2 - gards")
  println(greeting2)

  //3 - PatternMatched on Sealed Hierarchies (this is used to a compiler warning)
  //
  sealed class Animal
  case class Dog(breed: String) extends Animal()
  case class Parrot(greeting: String) extends Animal

  val animal:Animal = Dog("Boxer")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed")
      /*
      Dog(someBreed) -> is an extractor pattern for a case class
      If we leave the code as is, when compiling, we will get a warning saying the match may not be exhaustive (we are not covering Parrot case)
        the reason is because Animal class is sealed

        So, Sealed Classes can help covering all cases in any app

       */
  }




  //4 - Match everything
  val isEven = x match {
    case n if n%2 == 0 => true
    case _ => false
  }
  /*
  OVERKIL !!
  Even we can use pattern matching, it does not mean we should use it every time!
  */
  val isEvenNormal = x%2 == 0 //this expression already returns a true/false

  /*
  EXERCISES
  */

  trait Expression
  case class Number(n: Int) extends Expression
  case class Sum(val1: Expression, val2: Expression) extends Expression
  case class Prod(val1: Expression, val2: Expression) extends Expression

  /*
  Exercise 1
  Using pattern matching, create a function that takes an Expression and returns Human Readable formate of it

  Example:
    Sum(Number(2), Number(3)) , should return 2+3
    Sum(Number(2), Number(3)), should return 2+3
    Sum(Number(2), Number(3), Number(4)) , should return 2+3+4
    Prod(Sum(Number(2), Number(1)), Number(3)) , should return (2+1)*3
    Sum(Prod(Number(2), Number(1)), NUmber(3)) , should return 2*1+3
   */

  def show(expr: Expression): String = expr match {
    case Number(n) => s"$n"
    case Sum(val1, val2) => show(val1) + " + " + show(val2)
    case Prod(val1, val2) => {
      def maybeShowParentheses(expr: Expression) = expr match{
        case Prod(_, _) => show(expr)
        case Number(_) => show(expr)
        case _ => "(" + show(expr) + ")"
      }
      maybeShowParentheses(val1) + " * " + maybeShowParentheses(val2)
    }
  }
  println(show(Sum(Number(2), Number(3))))
  println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
  println(show(Prod(Sum(Number(2), Number(1)), Number(3))))
  println(show(Sum(Prod(Number(2), Number(1)), Number(3))))
  println(show(Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(4)))))



}
