package lectures.part4_PatternMatching

import exercises._

object AllThePatterns extends App {

  // 1 - Constants
  val x: Any = "Scala"
  val constant = x match {
    case 1 => "a number"
    case "Scala" => "THE Scala"
    case true => "THE Truth"
    case AllThePatterns => "a singleton object" //singleton object
  }

  // 2 - Match anything
  // 2.1 - wildcards
  val matchAnything = x match {
    case _ =>
  }

  // 2.2 Variable
  val matchAVariable = x match {
    case something => s"I've found $something" //"something" matches any value, but the advantage is to use the variable on the return
    //when we match a variable, we match aything and we bind that to a name
  }

  // 3 - Tuples
  val aTuple = (1, 2)
  val matchATuple = aTuple match {
    case (1, 1) => //tuple of literals (1,1)
    case (something, 2) => s"I've found $something" //tuples composed of nested patterns. "something" will be extracted out of tuple if the rest of the pattern matches, and can be used on the return
  }

  val nestedTuple = (1, (2, 3))
  val matchANestedTuple = nestedTuple match {
    case (_, (2, v)) =>
  }
  //Pattern Matching can be nested

  // 4 - case classes (most poowerful of pattern matching) - usually called as constructor pattern
  val aList: MyList_Covariance[Int] = new Cons_Covariance(0, Cons_Covariance(2, Empty_Covariance))
  val matchAList = aList match {
    case Empty_Covariance =>
    case Cons_Covariance(head, tail) => //head and tail are the "variables" of this pattern matching, head is "1" and tail is "Cons(2, Empty)"
    case Cons_Covariance(head, Cons_Covariance(subhead, subtail)) => //head is 1, subhead is "2" and subtail is "Empty"
    // PatternMatching can be nested with Case Classes
  }

  // 5 List patterns
  val aStandardList = List(1, 2, 3, 42)
  val aStandardListMatching = aStandardList match {
    case List(1, _, _, _) => //extractor (advanced) - List itself is not a case class, the list constructor extractor (List(1,_,_,_) ) exists in the scala standard library
    case List(1, _*) => //list of arbitrary length (advanced)
    case 1 :: List(_) => //infix pattern
    case List(1, 2, 3) :+ 42 => //infix pattern
  }

  // 6 - type specifiers - this is how we force pattern matching to conform to certain types
  val unknown: Any = 2
  val unknownMAtch = unknown match {
    case list: List[Int] => //explicit type specifier. This is the type specifier (: List[Int]) , so a List of Ints
    case _ =>
  }

  // 7 - name binding
  val nameBindingMatch = aList match {
    case nonEmptyList@Cons_Covariance(_, _) => // "nonEmptyList" names a pattern (which is the "name binding" and makes us use the name later OR here on the return expresison, like we did with variables earlier. Name binding is much powerful because we can name entire patterns
    //name binding works inside nested patterns
    case Cons_Covariance(1, rest@Cons_Covariance(2, _)) => //"rest" @ is inside a nested pattern
  }

  // 8 - multi patterns
  val multipattern = aList match {
    case Empty_Covariance | Cons_Covariance(0, _) => //compound pattern (multi-pattern). it is possible to chain multiple patterns with "|" -> useful when we want to return the same expression to multiple patterns
  }


  // 9 - if guards
  val secondElementSpecial = aList match {
    case Cons_Covariance(_, Cons_Covariance(specialElement, _)) if specialElement % 2 == 0 => // only if specialElement % 2 == 0 pattern will be evaluated

  }


  //Exercise
  println("Exercise")
  val numbers = List(1, 2, 3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of strings"
    case listOfNumbers: List[Int] => "a list of numbers"
    case _ => ""
  }
  //Question for exercise. What happens if we try to print "numbersMatch" ?
  println(numbersMatch)
  //JVM trick question

  /*
  >> answer is "a list of strings"
  WHY !?!?!?!
-> This is not a Scala problem.
Basically JAVA was designed to backwards compatibility, so basically a JAVA9 app can run a JAVA1 program written many years ago
BUT the problem is: at the beginning JAVA did not have generic parameters (where introduced just in JAVA5)

So this is the problem.
But JVM runs both JAVA apps. How?
Well, basically JAVA5 compiler erased all generic ytpes after type checking, which makes the JVM absolutely oblivious to generic types

Because Scala runs on a JVM, Scala suffers the same fault
So after type checking, our pattern match expressions look like this, because type parameters are erased during compiling

val numbersMatch = numbers match {
    case listOfStrings: List => "a list of strings"
    case listOfNumbers: List => "a list of numbers"
    case _ => ""
  }

  So now everything makes sense:
  numbers is a List, and the first case is also a list (case listOfStrings: List)


This is known as "Type Erasure"

In fact, IntelliJ is smart enough and warns about this:
fruitless type test: a value of type List[Int] cannot also be a List[String](but still might match its erasure)

   */

}
