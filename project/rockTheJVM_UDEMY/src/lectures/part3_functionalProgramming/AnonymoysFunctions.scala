package lectures.part3_functionalProgramming

object AnonymoysFunctions extends App {

  //below, a "Object Oriented way of defining an anonymous function and instantiating it on the spot
  val doublerObject = new Function1[Int, Int] {
    override def apply(x: Int) = x * 2
  }

  //but we can do it better
  //below a sintatic sugar fot he function above.
  //below we have an anonymous function, or a LAMBDA, which is a mathematical represation of an anonymous function ( we equate an anomyous function wth a LAMBDA)
  val doubler = (x: Int) => x * 2

  //multiple params in a lambda
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  //no params
  val doSomething = () => 3 //this function does nothing than returning "3" . the type of ths function is " () => Int "


  //WE MUST USE () when calling lambdas. When calling methods it's not necessary to use them, but they are required in lambda functions
  println(doSomething) // we are calling the function itself, so it will print a wrid number
  println(doSomething()) //this will prnint the value on the function

  // {}  with lambdas
  val strToInt = { (str: String) =>
    str.toInt
  }


  // MOAR syntactic sugar
  //val niceIncrementer: Int => Int = (x: Int) => x+1 is equivalent to
  val niceIncrementer: Int => Int = _ + 1

  // val niceAdder: (Int, Int) => Int = (a, b) => a+b is equivalent to
  val niceAdder: (Int, Int) => Int = _ + _ // each "_" is a DIFFERENT PARAMETER -> extremely useful in practice when we want to change a higher order functino calls


  /*
  Exercises
1. go to MyList and replace all FunctionX xalls with Lambdas  -> refer to AnonymousFunctions_Exercises
2.rewrite the "special" adder as an anonymous function

  * */


  //Exercise2

  val superAdd = (x: Int) => (y: Int) => x+y
  println(superAdd(3)(4))

}
