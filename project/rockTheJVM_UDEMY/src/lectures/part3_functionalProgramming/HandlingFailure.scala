package lectures.part3_functionalProgramming

import scala.util.{Failure, Random, Success, Try}

object HandlingFailure extends App {

  /*
  At this moment we already know about try/catch finnaly expressions
  But using too much of them, is that it makes the code too hard to follow and it's really possible to chain mmultiple try/catch exceptions or operations that are prone to failure
  and if we don't do them at all, we might end up, with exceptions being blow in your face


 Scala has Try -> wrapper for a computation that might fail or not

 Try:
  avoid runtime crashes due to uncaught exceptions
  avoid an endless amount of try-catches

  When designing a method to return (someType) ut may throw an exception, return Try[someType] instead !
   */


  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("SUPER FAILURE"))

  println(aSuccess)
  println(aFailure)

  /**
   * Success and Failure aren't always needed. We can use try instead
   */

  def unsafeMethod(): String = throw new RuntimeException("NO STRING FOR You!")

  val potentialFailure = Try(unsafeMethod())

  println(potentialFailure)
  //our app didn't crash, although we called unsafeMethod(), because Try to care the exception and wrap it up in a failure if it got caught. Notice we used an apply method!


  //syntax sugar
  val anotherPotentialFailure = Try {
    //code
  }


  //utilities
  println(potentialFailure.isSuccess) //-> we are testing the boolean value for potentialFailure

  //orElse
  def backupMethod(): String = "A valid result"

  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod())) //useful for unsafe APIs
  println(fallbackTry)

  //when designing an API. if we know that our code might throw an exception, we should wrap the code in a Try, instead.
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)

  def betterBackupMethod(): Try[String] = Success("A valid result")

  val betterFallback = betterUnsafeMethod() orElse betterBackupMethod()

  //map, flatMap, filter
  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x + 10)))
  println(aSuccess.filter(_ > 10))


  /*
  Exercise
  */


  /*
  In a small scenario, we will try to obtain a connection from a server and given that connection we will try to obtain an HTML page from the server and given the page we will try to print it to the console

  val hostname = "localhost"
  val port = "8080"
  def renderHTML(page: String) = println(page)

  class Connection{
    def get(url: String): String ={
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>...</html>" //random.nextBoolean we can say that this means "if this random decides to generate a value"
      else throw new RuntimeException("Connection interrupted")
    }
  }

  object HttpService{
    val random =new Random(System.nanoTime())
    def getConnection(host: String, port: String): Connection =
      if (random.nextBoolean()) new Connection
      else throw new  RuntimeException("Someoneelse took the port")
  }
*/


  /*
  Purpose of the exercise: try to print the page (<html>...</html>) IF the html page is obtained from that connection
   */


  println("Exercise:")
  val hostname = "localhost"
  val port = "8080"

  def renderHTML(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>...</html>" //random.nextBoolean we can say that this means "if this random decides to generate a value"
      else throw new RuntimeException("Connection interrupted")
    }

    def getSafe(url: String): Try[String] = Try(get(url)) //putting this (get(url)) in a try we are containing exceptions that might be thrown along the way
  }

  object HttpService {
    val random = new Random(System.nanoTime())

    def getConnection(host: String, port: String): Connection =
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Someoneelse took the port")

    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port)) //putting this (getConnection(host, port)) in a try we are containing exceptions that might be thrown along the way

  }

  val possibleConnection = HttpService.getSafeConnection(hostname, port) //a possibleConnection means "might be a success might be a failure"
  val possibleHTML = possibleConnection.flatMap(connection => connection.getSafe("/home")) //"connection" might be or not be there, we will try to "getSafe" the url
  //"possibleHTML" is a Try of string, which might be a String (our hTML) or might be an exception

  possibleHTML.foreach(renderHTML)

  /* lines above mean
  -We will get a connection and not throwing an exception
  If we get a connection, we might get an HTML
  If we get an HTML we will print to the console


  * */

  //shorthand version (chained)
  HttpService.getSafeConnection(hostname, port)
    .flatMap(connection => connection.getSafe("/home"))
    .foreach(renderHTML)

  //for.comprehension
  for {
    connection <- HttpService.getSafeConnection(hostname, port)
    html <- connection.getSafe("/home")
  } renderHTML(html)

  /*
  now with try/catch

  try{
    connection = HttpService(hostname, port)
    try{
      page = connection.get("/home")
      renderHTML(page)
    }catch(someException)
  }catch(someException)

*/


}
