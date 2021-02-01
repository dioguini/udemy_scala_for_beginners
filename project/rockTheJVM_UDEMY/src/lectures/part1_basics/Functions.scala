package lectures.part1_basics

object Functions extends App {

  //not necessary to use {} if the function only has "1 line"
  def aFcuntion (param_1_name: String, param_2_name: String): String =
    param_1_name + "" + param_2_name


  //expressions CAN BE BLOCKS
  def aFcuntion2 (param_1_name: String, param_2_name: Int): String = {
    param_1_name + "" + param_2_name
  }

  println(aFcuntion2("hey number ",2))


  //two ways to print a function without parameters (parameterless)
  def aParameterLessFunction(): Int = 42
  println(aParameterLessFunction())
  println(aParameterLessFunction)



  //the replace for Loops -> A RECURSIVE FUNCTION
  def aRepeatedFunction(aString: String, n: Int): String = {
  if (n==1) aString
  else aString + aRepeatedFunction(aString, n-1)
  }
  println(aRepeatedFunction("hello ",3)) // -> prints hello hello hello


  //the compiler can infer the return of a function too, so no need to specify the return type. BUT it does not work for recursive functions. AS best practice, specify it! :)
  //expressions CAN BE BLOCKS
  def aFcuntion3 (param_1_name: String, param_2_name: Int) = {
    param_1_name + "" + param_2_name
  }


  //it is possible to define Unit as return type of a function -> we define a function as executing only side effects
  //we can use this functions when we do not want to compute anythig. we just want for example to write to a doc, or print something
  def aFunctionWithSideEffects(aString: String): Unit = println(aString)


  //using a code block to define a function
  def aBigFunction (n: Int): Int = {
  def aSmallerFunction(a: Int, b: Int): Int = {
  a+b
  }
    aSmallerFunction(n, n-1)

  }

//working with recursvie functions

  def aGreetingFunction (name: String, age: Int): String = {
    "Hey there! I'm " + name + "and I'm  " + age + " years old"
  }
    println(aGreetingFunction("Diogo",13))


  def aGreetingFunction_2 (name: String, age: Int): Unit = {
    println("Hey there! I'm " + name + " and I'm  " + age + " years old")
    println(s"Hey there! I'm $name and I'm $age years old") //another way
  }
  println(aGreetingFunction_2("zé", 34))


  def factorial(number: Int): Int = {
    if(number<=0) 1
    else
     number* factorial(number-1)
  }
  println("Factorial: "+factorial(4))

  /**
   * f(1) = 1
   * f(2) = 1
   * f(n) = f(n-1) + f(n-2)
   *  1 1 2 3 5 8 13 21 34
   *
   */
  def fibonacci (number: Int): Int =
    if(number <= 2) 1
    else fibonacci(number-1) + fibonacci(number-2)

  println("Fibonacci: "+fibonacci(7))

  def isPrime(n: Int): Boolean = {      //we will need an axuiliary function -> isPrimeUntil ||| isPrime will just call isPrimeUntil with n/2 with half of n
    def isPrimeUntil(t: Int): Boolean = { //is "n" prime until "t" that is, does "n" have any divisors until the number "T" ?
      if (t <= 1) true
      else n%t != 0 && isPrimeUntil(t-1)
    }
    isPrimeUntil(n / 2)
  }

  println("Is prime? "+isPrime(7))
  println("Is prime? "+isPrime(8))
}
