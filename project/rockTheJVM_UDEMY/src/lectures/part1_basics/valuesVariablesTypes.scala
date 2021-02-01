package lectures.part1_basics

object valuesVariablesTypes extends App {

  val myVal: Int = 10
  println(myVal)
  //myVal = 12
  /**
   It is not possible not re-assign values to a val already declared -> vals are immutable (constants in java).
   In Functional programming, we do not work with variables but vals instead. it is possible to create a new val and assign to it a previous value from another val:
      val myVal: Int = 10
      val myNewVal = myVal*2
   HOWEVER we also have variables, and this ones can change their values. Can be re-assigned with new values
   Usually used on Side Effects - to see what programs are doing (seen forward). Re-assigning a variable is a side effect annnddd... Side Effects are Expressions that return Unit. A while loop is also a side-effect

   Basically:
   In Functional Programming we compute "things" so, the essence is always to return a "final" value, that is why everything is a new object. We are basically returning objects every time
   The line above also explains why we usually do not use Loops, because they do not compute nothig, just return a side effect.
*/
    var aVariable: Int = 45
    var anotherVariable = "string"
  var myOtherVar = aVariable*2

val x = 43 //types in vals are option, because the compiller infers it. we usually do not mention the type
  // the same as ->   val x: Int = 42


  val aString = "this is a String"
  // we can use ";" but usually not used. mostly used if we write the code in one line -> discouraged!!!!

  /**
   * Types in Sala
   * -boolean -> true/false --> val aBoolean = true ; val anotherBoolean: Boolean = false
   * -chars -> 'a' --> val aChar = 'a' ; val anotherChar: Char = 'b'
   * -ints -> 1 / 2 /19092 --> val aVal = 10; anotherVal: Int = 23
   * -short -> same as Int but shorter
   * -long -> ame as Int but longer
   * -float -> decimal numbers (NOT DOUBLES!) --> val aFloat = 2.0f ; val anotherFloat: Float = 5.21f
   * -doubles -> decimal numbers --> val aDouble = 3.14 ; val anotherDouble: Double = 43.54
   */



}
