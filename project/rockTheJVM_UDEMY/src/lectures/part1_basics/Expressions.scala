package lectures.part1_basics

object Expressions extends App {
  /**
   * Expressions
   * -have a type
   * -are evaluated to be a value (avaliadas para ter um valor)
   *
   * Compiller also can infer the type of an expression
   */
  val myExpression = 1 + 2 // this is an expression
  val myNewExpression: Int = 2 - 1 // this is an expression too. as seen, we can have the type (Int in this case) delcared or not

  println(1 == myExpression) //this is an expression too. returns false.
  println(1 == myNewExpression) //this is an expression too. returns true.
  println(!(1 == myExpression)) //this is an expression too. returns true. ! -> negation

  var aVariable = 2
  aVariable += 3
  /**
   * also possible:
   * -=
   * *=
   * /=
   * -->> ONLY AVAILABE FOR VARIABLES !!! because this will change their values
   */

  /**
   * Statements (instructions) VS Expressions
   * Statements (instructions) -> something we tell the computer to do
   *
   * Expressions -> something that as a value and/or a type. Everything in Scala is an expression. Definitions of package, vals are not expressions. But a re-assigng variable are expressions
   *
   *
   * */

  val aCondition = true
  val aConditionedValue = if(aCondition) 5 else true // this is an expression
  // above this is an expression because it gives back a value. we are computing a value. In java we do something like If (blabla) DO x else Y . we are not doing the same here
  println(if(aCondition) 5 else true) // also possible

  /**
   * We do not usually Loops in Scala -> we use Recursion instead -> see Functions.scala
   * We do not use Loops in Functional Programming because they do not compute anything meaningful just return side effects
   * However it is possible to iterate!! :)
   */

  val aWeirdvalue = (aVariable = 3) //Unit -> does not return anything meaningful. The value of Unit is literally () , which is the unique value this type can hold
// Do not forget: Re-assigning a variable is a side effect annnddd... Side Effects are Expressions that return Unit
  println("qui")
  println(aWeirdvalue)


  /**
   * CODE BLOCKS
   *  Kind of expressions because they have special properties
   *  defined between {}
   *  -> are expressions
   *  -> the VALUE OF A BLOCK, IS THE VALUE OF ITS LAST EXPRESSION
   *  -> not possible to access variables/values outside the code block
   *  -> a code block can be used to define a function - see Functions.scala (allow a definition of auxiliary functions)
   */

  //definition
  val aCodeBlock = {
    val y = 20
    val z = y+1
    println("This will be printed. Do not forget: this is a println() side effect ")
    val valueToCompare = 20
    if (z>valueToCompare) s"Z is greather than $valueToCompare" else s"Z is lesser than $valueToCompare" //this is not the value of aCodeBlock
    if (z>2) "Z>Y" else "Z<Y"


  }

  println(s"The value of codeblock is: "+aCodeBlock)

  // val anotherValue = z+1 -> not possible.

  //do not forget: the type is infered by the compiller. will be true for the code block below
 val someValue = {
    2<3
  }
  println(someValue)
}
