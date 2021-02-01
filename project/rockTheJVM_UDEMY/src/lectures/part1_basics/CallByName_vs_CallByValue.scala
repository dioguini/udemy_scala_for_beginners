package lectures.part1_basics

object CallByName_vs_CallByValue extends App {

  def calledByValue(number: Long): Unit = {
    println("By value, "+number)
    println("By value, "+number)

  }


  def calledByName(number: => Long): Unit = { // => tells the compiler the parameter will be CALLED by NAME
    println("By name, "+number)
    println("By name, "+number)

  }

  calledByValue(System.nanoTime())
  calledByName(System.nanoTime())

  /**
   * Soooooo what's the difference?!?!?!?
    Here's the output:
    By value, 270157711891849
    By value, 270157711891849
    By name, 270157810203641
    By name, 270157810230404

   By VALUE -> two results with the SAME value
   By NAME -> two results with the DIFFERENT value

   --> HUM!?!?!?
 the "=>" makes all the difference!
   Let' see:

   *** By VALUE ***
      The EXACT value is calculated BEFORE the function evaluates
      same value used everywhere.
        So:
          compiller calculates the value of System.nanoTime()
          compiller evaluates the function
          compiller prints twice the value computed (270157711891849)

          Basically is:

          def calledByValue(number: Long) = {
            println("By value, "+number)
            println("By value, "+number)

           }
        calledByValue(270157711891849L)


   *** By NAME ***
      The expression is evaluated EVERYTIME in the funcion definition, that's why we have two different timestamps on the function above
      --> useful in lazy streams / things that might fail
      Basically is:
        def calledByName(number: => Long) = { // => tells the compiler the parameter will be CALLED by NAME
          println("By name, " + System.nanoTime())
          println("By name, " + System.nanoTime())
        }
        calledByName(System.nanoTime())

   */


  def infinite(): Int = 1 + infinite()
  def printFirst(param1: Int, param2: => Int): Unit = println(param1)

  //printFirst(infinite(), 34) // error -> StackOverflowError

  /**
   This wont fail because we're using a BY NAME parameter (param2: =>Int) , and so it dealys the evaluation of the expression passed (infinite() ) until it's used, so since the param2 is not used, infinite() is never actually evaluated which is why our app doesn't fail
   */
  printFirst(34, infinite())

}
