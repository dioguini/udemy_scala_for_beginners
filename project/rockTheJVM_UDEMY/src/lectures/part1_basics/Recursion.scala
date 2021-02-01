package lectures.part1_basics

import scala.annotation.tailrec


object Recursion extends App {

  /**
   * How a Recursive function works on the compiler in Runtime:
   * JVM uses a call stack to keep partial results, so that it can get back to computing the desired result
   * --> this means that each call of the recursive function uses a stack Frame -> so JVM keeps all the calls on its internal memory stack, which has a lmited memory - PROBLEM ALERT !!!!!
   * --> the factorial of 5000 will raise a StackOverFlowError -> when the recursive of depth is too big.  WAHT TO DO !?!?!?!??! See below
   *
   */

  def factorial(number: Int): Int = {
    if (number <= 0) 1
    else {
      println("Computing the factorial of " + number + " - First need a factorial of " + (number - 1))
      val result = number * factorial(number - 1)
      println("Computed Factorial of " + number + " -> " + result)
      result
    }
  }

  println("Factorial: " + factorial(4))


  /**
   * Dealing with "Heavy" recursive functions
   */


  /**
   * Wait... What the hell is the function bellow!?!?!?!?
   * Lets breake it down:
   * anotherFactorial(10) [1*2+3+4+5+6+7+8+9+10]
   *
   * anotherFactorial(10) = factHelper(10,1)  --> is 10 less or equal than 1? No, ok go to the else branch:
   * number is now 9, and so we have:
   * factHelper(9, 10*1) --> is 9 less or equal than 1? No, ok go to the else branch:
   *
   * number is now 8, and so we have:
   * factHelper(8, 8*9*10*1) --> is 8 less or equal than 1? No, ok go to the else branch:
   *
   * number is now 7, accumulator is 7*8*9*10*1 and so we have:
   * factHelper(7, 7*8*9*10*1) --> is 7 less or equal than 1? No, ok go to the else branch:
   *
   * number is now 6, accumulator is 6*7*8*9*10*1 and so we have:
   * factHelper(6, 6*7*8*9*10*1) --> is 6 less or equal than 1? No, ok go to the else branch:
   *
   * number is now 5, accumulator is 5*6*7*8*9*10*1 and so we have:
   * factHelper(5, 5*6*7*8*9*10*1) --> is 5 less or equal than 1? No, ok go to the else branch:
   *
   * number is now 4, accumulator is 4*5*6*7*8*9*10*1 and so we have:
   * factHelper(4, 4*5*6*7*8*9*10*1) --> is 4 less or equal than 1? No, ok go to the else branch:
   *
   * number is now 3, accumulator is 3*4*5*6*7*8*9*10*1 and so we have:
   * factHelper(3, 3*4*5*6*7*8*9*10*1) --> is 3 less or equal than 1? No, ok go to the else branch:
   *
   * number is now 2, accumulator is 2*3*4*5*6*7*8*9*10*1 and so we have:
   * factHelper(2, 2*3*4*5*6*7*8*9*10*1) --> is 2 less or equal than 1? No, ok go to the else branch:
   *
   * number is now 1, accumulator is 1*2*3*4*5*6*7*8*9*10*1 and so we have:
   * factHelper(1, 1*2*3*4*5*6*7*8*9*10*1) --> is 1 less or equal than 1? Yes!! so return the accumulator 1*2*3*4*5*6*7*8*9*10*1, which is the factorial of 10!
   *
   *
   * This will not crash!! why!?!? Because uses TAIL RECURSION -> basicamente é adiado o momento de utilizar espaço na memória da JVM. No código acime necessitamos sempre de colocar resultados temporários na stack para os consultar. Aqui não. Temos um resultado que não é colocado na stack e logo a seguir temos um novo resultado para o val que tinha a expressão, ou seja, é como se estivessemos a adiar colocar o valor na stack até à ultima instância. Nessa última instância retornamos o valor da multiplicação.
   * In anotherFactorial(), factHelper() is the last expression  of the code path which allows Scala to preserve the same stack frame and not use additional stack frames for recursive, as on the implementation above, where Scala needed a recrusive call stack frame for each recursive call so that it computes the intermediate result so that it can then multiply it with  a number and then PASS IT back from the stack
   * But here Scala does not need it!  Scala does need to save intermidiate results to be used later so when we evaluate this rescursive call, the current stack frame, for example factHelper(10) is replaced with the factHelper() of something else, without extra stack memory
   *
   * KEY TO USE TAIL RECURSION
   * -> USE RECURSIVE CALL AS THE LAST EXPRESSION, on each code path that it occurs
   * we if are no sure if the function is being see as Tail Recursion we can add the @tailrecursion. Do not worry: if it is not tail recursive, the compiler throws an error. Try on the factorial above definitio.
   *
   * Basically....
   * WHEN WE NEED LOOPS, USE TAIL RECURSION! :)
   *
   * Any function can be converted into a Tail Recursion function (use the accumulator strategy)
   *
   */
  def anotherFactorial(number: Int): BigInt = {
    @tailrec
    def factHelper(x: Int, accumulator: BigInt): BigInt = //-> auxiliary function!
      if (x <= 1) accumulator
      else
        factHelper(x - 1, x * accumulator)

    factHelper(x = number, accumulator = 1) //don't forget -> this is the first line to run
  }

  println(anotherFactorial(5000))

  @tailrec
  def concatenateTRec(times: Int, text: String, accumulator: String): String = {
    if (times <= 0)
      accumulator
    else
      concatenateTRec(text = text, times = times - 1, accumulator = text + accumulator)
  }

  println("Concatenate TRec: ", concatenateTRec(text = "hey", times = 3, accumulator = ""))

  def isPrime(number: Int): Boolean = {
    @tailrec
    def isPrime_TREC(times: Int, isStillPrime: Boolean): Boolean = {
      if (!isStillPrime) false
      if (times <= 1) true
      else
        isPrime_TREC(times - 1, number % times != 0 && isStillPrime)


    }

    isPrime_TREC(number / 2, isStillPrime=true)
  }

  println("Prime TRec: ", isPrime(123))


  def fibonacci(number: Int): Int = {
    /**
     * 3 params. Number and 2 accumulators. TWO? WHY? Because fibonnaci uses Fibonnaci twice: fibonacci-1 AND fibonacci-2 IN THE SAME CODE path
     * i - the number we want to comput
     * acc1 - accumulator 1 --> last computed value (fibonacci -1)
     * acc2 - accumulator 2 --> previous fibonacci calculated (fibonacci -2)
     * --> However many recursive calls we have on the same code path, THAT'S HOW MANY ACCUMULATORS WE NEED TO HAVE in the TRec function version we need to implement
     */
    @tailrec
    def fibonacciTRec(i: Int, acc1: Int, acc2: Int): Int = {
      if (i >= number) acc1
      else fibonacciTRec(i + 1, acc1 + acc2, acc1)
    }

    if (number <= 2)
      1
    else
      fibonacciTRec(2, 1, 1) //2 - starting index for fibonacciTRec() and 1,1 are the 2 first fibonacci numbers
  }

  println("Fibonacci TRec: " + fibonacci(8)) //1,2,3,5,8,13, (21 - result)

}
