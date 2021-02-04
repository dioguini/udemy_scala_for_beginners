package lectures.part1_basics

import scala.annotation.tailrec

object DefaultArgs extends App {

  @tailrec
  def trecFactorial(number: Int, accumulator: Int =1): Int ={ //defining the value here, we do not need to specify a value when calling trecFactorial() function -> trecFactorial(90)
    if(number <= 1) accumulator
    else
      trecFactorial(number-1, number*accumulator)
  }
  //trecFactorial(90,4) -> "4" will override the value specified on the trecFactorial() definition
  println(trecFactorial(90))


  def personCharacteristics(name: String, age: Int, hairColor: String, eyesColor: String): Unit = println("This is just an example")

  /**
    The function above,  personCharacteristics() can be called on the different ways.
   We just need to specify the paramaters when calling the function. If we do not specify the parameters, they will be called by order. See example3
   */

  personCharacteristics("Diogo", 28, "blonde","green")
  personCharacteristics(hairColor="blonde", name="Diogo", eyesColor="green",age=28)


  //example3
  def personCharacteristics_2(name: String, age: Int, hairColor: String ="Blonde", eyesColor: String): Unit = println("This is just an example")
  personCharacteristics_2(hairColor="blonde", name="Diogo", eyesColor="green",age=28) //OK!
  //personCharacteristics_2("Diogo", "green", 28, "blonde") -> gives an error, because 2nd parameter is an Int and we put a String and the 3rd one is a String and we are putting an int
}
