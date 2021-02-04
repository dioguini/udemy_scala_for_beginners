package lectures.part1_basics

object StringOps extends App {

  val myString: String = "Hello, I am learning Scala"

  println(myString.charAt(2)) // Indexes start on 0 !
  println(myString.substring(7,11))
  println(myString.split(" ").toList) // -> Using "toList" because the output of Split() is an array, and so to print an array we just need to use toList() function
  println(myString.startsWith("Hellos")) // true/false
  println(myString.replace(" ", "-"))
  println(myString.toLowerCase())
  println(myString.length())

  val aNumberString: String = "34"
  val aNumber = aNumberString.toInt


  /**
   * Concat
   * Prepend +:
   * Append :*
   */

  println('a'+:aNumberString:+'z')
  println(aNumberString.reverse)
  println(aNumberString.take(2)) // takes the first 2 chars

  /**
   * S-interpolators
   */

  val name:String = "Diogo"
  val age:Int = 45

  val greeting: String = s"Hello I am $name and I am $age years old" // -> there is an "s" rigth before. This allows us to use the values of the values name and age INSIDE the entire string (we call this "expand the value") Otherwise, we need to use the "+" to concat. If we do not use the "s" before, the string will have literally "$name" and "$age" text

  //s-interpolator can evaluate complex expressions
  val anotherGreeting: String = s"Hello I am $name and I am ${age +1} years old" // note the {} . we can use code we want inside it

  /**
   * F interpolattors
   */

  val speed: Double = 1.2f
  val myth: String = f"$name can ride his bike at $speed%2.2f" //note the 2.2f -> it's the printf format . 2 chars total minimum and 2 decimals precision
  println(myth)

val x: Double = 1.1f
  //val str:String = f"$x%3d" // this throws an error %3d is expecting an Int, but we are trying to pass an float.



  /**
   * RAW Interpolator
    works like the s-interpolator, but it can prints characters literally
   */
  println(raw"This is a \n newline") // \n will be printed. ->> Backslashes WILL NOT BE ESCAPED
  val escaped: String = "This is a \n newline" //-> a new line will be printed
  println(raw"This is a \n newline $name") // injected variables DO GET ESCAPED. no issues!



}
