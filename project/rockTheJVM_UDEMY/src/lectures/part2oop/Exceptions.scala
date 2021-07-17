package lectures.part2oop

object Exceptions extends App {

  /**
   THROWING EXCEPTIONS
   */
  val x: String = null
  //println(x.length) // this crashes because throws a null pointer exception


  //we can intentionally throw an exception:
  //throw new NullPointerException //this runs "normally", no problem

  //LIKE EVERYTHING ELSE IN SCALA, throwing an exception is an expression

  /*
  Below we have:
  if we print "myExcep" it will return nothing, why?
  so, exceptions are actually instances of classes, that's why we use "new", and "NullPointerException" is a class
  so basically below, we are just instantiating a "NullPointerException" class and throwing an instance of it
   */
  //val myExcep: String = throw new NullPointerException

  /*
  throwable classes extend the Throwable class, so the property of a class to be thrown is restricted by that class, extending or deriving from the throwable type
   "Exception" and "Error" are the major Throwable subtypes
   Exceptions -> something that went wrong with the PROGRAM
   Errors -> something that went wrong with the SYSTEM
  */




  /**
  CATCHING EXCEPTIONS
   */

  def getInt(withExceptions: Boolean): Int = {
    if (withExceptions) throw new RuntimeException("No Int for you")
    else 10
  }

    try{
      getInt(true)
    } catch{
      case e: RuntimeException => println("caught a runtime exception - 1st") //this line will be executed if "withExceptions" is true. And there's a pattern matching here, in this case for RuntimeException (case e: RuntimeException =>)
      //case e: NullPointerException => println("caught a null exception") //if line above is commented, "No Int for you" will be printed, and a RuntimeException will be raised BY THE JVM
    }finally {
      //code that will get executed NO MATTER WHAT
      //does not influence the return type of the expression (see below)
      //use finally only for side effects (logins, for example)
      println("reached finally statement - 1st")
    }


  /*
  A try/catch/finally has a value, and can be printed
  */

  //"potencialFail" is anyVal here, because the try block tries to return a int BUT the catch is Unit (an exception)
  val potentialFail = try{
    getInt(true)
  } catch{
    case e: RuntimeException => println("caught a null exception - 2nd") //if line above is commented, "No Int for you" will be printed, and a RuntimeException will be raised BY THE JVM
  }finally {
    //code that will get executed NO MATTER WHAT
    println("reached finally statement - 2nd")
  }

  //"potencialFail2" is Int here, because the try block tries to return a int AND the catch is also Int
  val potentialFail2 = try{
    getInt(true)
  } catch{
    case e: RuntimeException => 3
  }finally {
    println("reached finally statement - 3d")
  }

  //"potencialFail2" is Int here, because the try block tries to return a int AND the catch is also Int
  val potentialFail3 = try{
    getInt(false)
  } catch{
    case e: RuntimeException => 3
  }finally {
    println("reached finally statement - 4th - not needed this finally statement, in this case")
  }

/*
  println("potentialFail -> " + potentialFail)
  println("potentialFail2 -> " + potentialFail2)
  println("potentialFail3 -> " + potentialFail3)

*/


  /**
   DEFINING OUR OWN EXCEPTIONS
   */

  class myException extends Exception{
    val exception = new myException
  }

  //we can throw the exception above, with
  //throw exception


/*
Exercise - Crashing with OutOfMemory
 */


  /*
Exercise - Crashing with StackOverflowError
 */

  /*
Exercise - PocketCalculator
  class that receives 2 numbers and:
  adds
  subtracts
  multiply
  divide

  throw
    overflowexception if add exceeds Int.MAX_VALUE
    underflowexception if subtracts exceeds Int.MIN_VALUE
    MathCalculationException for division by 0

 */


  //this helps crashing the JVM with an out of memory
  //val array = Array.ofDim(Int.MaxValue)

  //stackoverflow error (exceeds the stack limit)
  //def infinite: Int = 1+infinite
  //val noLimit = infinite

  object PocketCalculator{ //declaring it as an object will prevent to to instantiate "PocketCalculator" everytime we call its methods
    def add(x: Int, y: Int): Unit ={
      val result = x + y
      /*
      // if (result > Int.MaxValue) //false all the time, because we are comparing it to the MAX VALUE allowed. same for "result < min value"
      if we add "10" to the max value allowed, a negative value will be returned, and this logic can be used below
      */

      if(x>0 && y> 0 && result <0) throw new OverflowException
      else if(x<0 && y< 0 && result >0) throw new UnderflowException
      else result


    }

    def subtratc(x: Int, y: Int): Unit ={
      val result = x-y
      if(x > 0 && y <0 && result<0) throw new OverflowException
      else if(x < 0 && y >0 && result>0) throw new UnderflowException
    }
    def multily(x: Int, y: Int): Unit ={
      val result = x*y
      if(x>0 && y>0 && result <0) throw new OverflowException
      else if(x<0 && y<0 && result <0) throw new OverflowException
      else if(x>0 && y<0 && result >0)throw new UnderflowException
      else if(x<0 && y>0 && result >0)throw new UnderflowException
      else result

    }
    def divide(x: Int, y: Int): Unit ={
      if(y==0) throw new MathCalculationException
      else x/y

    }

  }

  class OverflowException extends RuntimeException{}
  class UnderflowException extends RuntimeException{}
  class MathCalculationException extends RuntimeException("Division by 0 not allowed"){}




  println(PocketCalculator.divide(2,0))

}
