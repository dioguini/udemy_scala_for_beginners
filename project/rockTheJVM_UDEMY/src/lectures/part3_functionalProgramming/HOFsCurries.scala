package lectures.part3_functionalProgramming

object HOFsCurries extends App {

  /*
  The function below has two params:
  ->Int
  ->a function String, (Int => Boolean)) => Int
  and returns a function(Int => Int)

  The function beloe is a HOF (high order function because has a function as parameter
  --> map, filter and flatMap in MyList are other HOFs
  */
  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = null

  /*
  function that applies a function n times over a value x
  nTimes(f,n.x)
  f - function
  n - number of applications of f function
  x - subject of the application of the function

  ntimes(f,3,x) -> 3 times the application of function f applied over x --> f(f(f(x)))  -> ntimes(f, 2, f(x)) = f(f(f(x)))
  so basically it's "n-1" applied of f(x)
  nTimes(f,n,x) = nTimes(f, n-1, f(x)) --> we are applting a recursion here
  */

  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))
  }

  //a basic incremental function
  val plusOne = (x: Int) => x + 1
  println(nTimes(plusOne, 10, 1)) //we call plusOne, on the value 1, 10 times --> we are incrementing 1 ten times

  /*
  a better way to do the same as above...
we can say that the function nTimes takes a function, and takes a number and returns another function, which is basically the application of fanction nTimes where where can use on any value we want

The difference is:
instead of applying function f a certain number of times of "x", we save that for later in te sense that we can say:
  nTimesBetter(f,n) = x => f(f(f...(x)))
    -> so instead of actually applying F a certain number of times to X, we RETURN a LAMBDA that we can then use later as many times we want to various values
      -> increament10 = nTimesBetter(plusOne, 10) = x => plusOne(plusOne(plusOne(plusOne(plusOne(plusOne(plusOne(plusOne(plusOne(plusOne(x))))))))))
          later we can have the following:
         val y = increment10(1) --> increment10 is begin used as if it were a function!

  */

  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) = {
    if (n <= 0)
      (x: Int) => x //we are returning a function that receives a Int and returns that value
    else
      (x: Int) => nTimesBetter(f, n - 1)(f(x)) //the return of nTimesBetter is function, and that function will be applied to f(x)
  }

  val plus10 = nTimesBetter(plusOne, 10) //because nTimesBetter returns a function, "plus10" is a function
  println(plus10(1)) //"plus10" is actually "nTimesBetter" applied to "plusOne" and 10, which is a function that applies plusOne 10 times to whatever the value we pass, in this case 1


  /**
   * Curried Functions - very useful when we want to define the helper functions that we want to use later on various values. Can have as many parameter list as we want
   */


  val superAdder: Int => (Int => Int) = (x: Int) => (y: Int) => x + y //superAdder receives an Int and returns a function
  val add3 = superAdder(3) // this is basically a lambda! -> y => 3+y
  println(add3(10))
  println(superAdder(3)(10))

  //Scala supports functions with multiple parameter lists!
  def curriedFOrmatter(c: String)(x: Double): String = c.format(x) //acts like a curried function because we can then later craete some functions (like standardFormat and preciseFormat) that apply that curried functions with fewer parameter lists ("4.22f" for example) and then we can use them with whatever value we want later (Math.PI, for example)

  val standardFormat: (Double => String) = curriedFOrmatter("%2.2f") //we can see here we are using curriedFormater on standardFormat. CurriedFormat uses 2 params, and that's why we are specifing the unique parameter for standardFormat, otherwise, if we not use params specification, we would need to use all the needed param for the curried function
  val preciseFormat: (Double => String) = curriedFOrmatter("%10.8f")

  println(standardFormat(Math.PI))
  println(preciseFormat(Math.PI))


  /**
   * EXERCISES
   * 1. Expand MyList
   * - foreach method -> for each element A of Unit tye, it will apply this function to each element
   * Ex: [1,2,3].foreach(x => println(x))
   *
   * - sort function (compares two functions). Definition: sort((A,B) => Int) = Int , where the result is negative if A<B, and positive otherwise, and return a MyList
   * Ex: [1,2,3].sort(x,y) => y-x => [3,2,1]
   *
   * - zipWith (list, (A,A) => B ) => B MyList[B]
   * Ex: [1,2,3].zipWith([4,5,6], x*y) => [1*4, 2*5, 3*6] => [4,10,18]
   *
   * -fold (a curried function) that receives two param lists(a start value, and a function), and the return will be a value
   * Ex: [1,2,3].fold(0)(x+y) = 6 -->> the start position is 0, so we are going to sum value on position 0, and the next one, that is 3, and the sum the nexet elements. Final reuslt will be 6
   *
   *
   * 2. Defining methods to turn functions into curried and uncurried versions
   * Ex: toCurry(f: (Int, Int) => Int => (Int => Int => Int)
   * fromCurry(f: (Int => Int => Int)) => (Int, Int) => Int
   *
   *
   * 3. compose(f,g) => f(g)(x)   -->>> which is basically compose(f,g) => x => f(g)(x)   (this is function composition)
   * --> compose with f and g, will return a lambda that applies f,g,x
   * --> so, compose(f,g) will return a lambda wich for every value of x, it applies g firts and then f (this is function composition)
   *
   * andThen applying first g then f, so:
   * compose(f,g) => x => g(f)(x)
   */


  //ex2
  def toCurry(f: (Int, Int) => Int): (Int => Int => Int) = // we can say that "based on  (Int => Int => Int)  we are retunring a Curried version
    x => y => f(x, y) //the implemention is: a lambda taking a value X which returns a lambda taking a value Y and the result is going to be "f of x and y" -> f(x,)

  def fomCurry(f: (Int => Int => Int)): (Int, Int) => Int = //the return is a function with two arguments (Int, Int) => Int
    (x, y) => f(x)(y) //the implemention is "a lambda with two arguments -> (x,y) <- and the implementation is a function applied to x applied to y

  //ex3 - compose() and andThen() are two util functions - they exist on the documention
  def compose[A, B, T](f: A => B, g: T => A): T => B =
    x => f(g(x)) //the return is "g of x"
  /*
  g of T return A
  f of A is of type B
  and the return will be T all the way to A
  */

  def andThen[A, B, C](f: A => B, g: B => C): A => C = //we can read this "Int => Int" as "int to int"
    x => g(f(x))
    /*
        f of A will return a B
        G of B will return a C
        final function is A all the way to C
     */


def supperAdder2: (Int => Int => Int) = toCurry(_+_)
  def add4 = supperAdder2(4)
  println(add4(17)) //this will add 4 to 17 --> reason is: add4(17) is actually supperAdder2(4) with 17, which is in toCurry=_+_ >> toCurry = 4+17


  val simpleAdder = fomCurry(superAdder) //this is a lambda that takes 2 params
  println(simpleAdder(4,17))

  val add2 = (x: Int) => x+2
  val times3 = (x: Int) => x*3

  val composed = compose(add2, times3) //times3 will be called first and then add2 will be called --> f(g(x))
  val ordered = andThen (add2, times3) //functions will be called in ordered


  println(composed(4)) //14 because (4*3) +2
  println(ordered(4)) //18 because (4+2) *3
}
