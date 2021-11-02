package lectures.part4_PatternMatching

object BracelessSyntax {

  // This started to be used in Scala3

  def main(args: Array[String]): Unit = {

  }



  // if - Expressions

  //scala style
  val anIfExpression = if(2>3) "bigger" else "smaller"

  //java style
  val anIfExpression_v2 = {
    if (2>3) {
      "bigger"
    }
    else {
      "smaller"
    }
  }

  //compact
  val anIfExpressions_v3 =
    if (2>3) "bigger"
    else "smaller"


  // scala 3 -> Introduced "then" keyword
  //need correct identation in order to remove "{" and "}"
  val anIfExpressions_v4 =
    if 2 > 3 then
      "bigger" //NEEDS TO HAVE a higher identation than the if part
    else
      "smaller"
  val anIfExpressions_v5 =
    if 2 > 3 then
      //since the code below is correctly indented, expressions will be executed as a code block. The value for this code block is the value is its LAST expression. Variables' values will be restricted to this scope
      val result = "bigger"
      result
    else
      val result = "smaller"
      result

  //scala 3 one-liner (smae as scala)
  val anIfExpression = if(2>3) "bigger" else "smaller"





  // for comprehensions
  val aForComprehension = for{
    n <- List(1,2,3)
    s <- List("black", "white")
  } yield s"$n$s"

  //scala 3
  val aForComprehension_v2 =
    for
      n <- List(1,2,3)
      s <- List("black", "white")
    yield "s$n$s"


  //pattern matching
  val meaningOfLife = 42
  val aPatternMatch = meaningOfLife match {
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "something else"
  }

  //scala 3
  val aPatternMatch_v2 =
    meaningOfLife match
      case 1 => "the one"
      case 2 => "double or nothing"
      case _ => "something else"

  //aslso scala 3
  val aPatternMatch_v3 = meaningOfLife match // below, all cases are one identation of this live, which makes sense. The code block and the above are both correct, since they are following the "identation rule"
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "something else"



  //defining methods

  def computeMeaningOfLife(arg: Int): Int = {
    val partialResult = 50



    partialResult+2
  }
  //final result will be value of partialResult +2. The entire balcnk space is ignored but it's part of the code block, since it's in the {}


  def computeMeaningOfLife_2(arg: Int): Int =
    val partialResult = 50



    partialResult+2
  //final code block is here. black space will be ignored and as we can see indentation level is followed accordingly first line


  //it is possible to define  classes, objects, traits, data types and enums

  //class
  //normal declaration
  class Animal{
    def eat: Unit =
      println("I'm eating")
  }

  class Animal2: // ":" means that the compiler is expecting the body of the class. So it basically starts and indentation region
    def eat: Unit =
      println("I'm eating")
    end eat

    def grow (): Unit =
      println("I'm growing")

  end Animal //ends an indentation regions previously declared with ":". Useful when we have thousands of lines and we don't already know where it starts -> Used for if, match, for, classes (anonymous, normal, case classes), traits, enums, objects

//anonymous classes
//normal declaration
  val aSpecialAnimal = new Animal{
    override def eat: Unit = println("I'm special")
  }


  val aSpecialAnimal = new Animal:
    override def eat: Unit = println("I'm special")



  //indentation -> strictly larger indentation
  //don't mix spaces with tabs, use tabs or spaces




}
