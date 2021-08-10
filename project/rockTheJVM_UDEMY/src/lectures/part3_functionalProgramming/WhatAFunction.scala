package lectures.part3_functionalProgramming

object WhatAFunction extends App {

  /*
  PURPOSE OF FUNCTIONAL PROGRAMMING
    use and work functions as first class elements -> meaning, that we want to work with functions like we work with clean values
    problem -> OOP (everything is an instance of a class...)
      -> JVM deals with instances of classes, nothing else
      the only way we can simulate Function Programming is to use classes and instances of those classes
  */


  //below, we can see "doubler" is an instance of "MyFunction" and acts like a "new instance of a class MyFunction"
  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  //doubler which is an instance of function like class, can be called as a function!
  println(doubler(2)) //we are seeing here a BIG ADVANTAGE of Scala when compared with java: we are calling "doubler"  like it were a function! so, "doubler" acts like a function!!!


  /*
  we don't need to always define functions like the one above... BECAUSE scala already has them! it supports until 22 params inputs
   */

  val stringToIntConverter = new Function[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("2") + 4)
  println(stringToIntConverter("4") * 85)
  println(stringToIntConverter("4") * stringToIntConverter("4") - 85)


  val adder = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }
  println(adder(1, 4))

  /*
  val adder: Function2[Int, Int, Int] = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1+v2
  }

  Below we have the sintactic sugar for Function2 -> Function2(Int, Int)
  val adder: Function2(Int, Int) => Int = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1+v2
  }
  */


  //So on Function Programming, we have Function Types !! ---> Function[A, B, R] === (A,B) => R

  //ALL SCALA FUNCTIONS ARE OBJECTS (instances of classes deriving from the pre-defined functions)


  /* EXERCISES
  1.  Function takes 2 strings and concatenates them
  2.  transform current MyPredicate and MyTransformer into Function Types
  3. define function which takes an int and returns another function which takes an int and returns an int
  */

  //1.  Function takes 2 strings and concatenates them
  def concatenator: (String, String) => String = new Function2[String, String, String] {
    override def apply(a: String, b: String): String = a + b
  }

  println(concatenator("string1_", "string2_"))


  //2.  transform current MyPredicate and MyTransformer into Function Types
  // --> check exercies.MyList_Function.scala

  //3. define function which takes an int and returns another function which takes an int and returns an int --> using high order functions (receives a function returns a function)
  //Function1[Int, Function1[Int, Int]]

  val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int): Function1[Int, Int] = new Function1[Int, Int] { //x is visisble for functions defined below
      override def apply(y: Int): Int = x + y
    }
  }


  //tenho sempre que fazer dest amaneira!?
  val adder3 = superAdder(3) //adder3 is a new function, and returns and Int
  println(adder3(4))

  println(superAdder(3)(4)) //curried function --> property that can be called with multiple params list -- faz mais sentido esta maneira que a de cima

}


//basic trait. receives an element of type "A" and returns an element of type B
trait MyFunction[A, B] {
  def apply(element: A): B
}