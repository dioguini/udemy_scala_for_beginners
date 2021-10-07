package lectures.part3_functionalProgramming

import scala.util.Random

object Options extends App {

  /*
    We need some kind of data type that could encapsulate the possible absence of a value
    Options are kind of scala "collections"
    WITH OPTIONS WE ALWAYS WANT TO AVOID TO DO NULL CHECKS

    sealed bastrac class Option[A+]
      case class Some[+A](x: A) extends Option[A] --> "Some" wraps a concrete value
      case object None extends Option[Nothing] --> "None" is a singleton for absent values

     Option is used in maps and lists, for example, when we try to get an empty value

     Options are very useful when using APIS

     So basically with options, we can
      avoid runtime crashes due to NullPointerExceptions
      avoid an endless way amount of "null verifications"

IF WE ARE DESIGNING A METHOD THAT RETURNS SOME TYPE (Int, for example) BUT MAY RETURN NULL
  we should return an Option[Int] instead (instead of null)

  */

  val myFirstOption: Option[Int] = Some(3)
  val noOption: Option[Int] = None

  println(myFirstOption)

  def unsafeMethod(): String = null
  val wrongResult = Some(unsafeMethod()) // THIS IS WRONG because we can end by getting Some(null), which breaks the goal of Option. Some should ALWAYS get a valid objet inside
  val result = Option(unsafeMethod()) //this is correct. Why: the "apply" method from the companion object "Option" would take care to build a some or none depending on whether "unsafeMethod()" is null or not. No "Null checks are needed"
  println(result)

  //chained methods (the way we would use Options, would be in chained methods)
      //Chained methods are class methods that return the instance of the object so you can call another method on the same object.
  def backupMethod(): String = "A valid result"
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))
  //we try to get a result from unsafeMethod, and in case it returns null, we can use "orElse" to retrieve result from another method, like "backupMethod"

  //Design unsafe APIs
  def betterUnsafeMethod(): Option[String] = None
  def betterBackUpMethod(): Option[String] = Some("A valid result")
  val betterChainedResult = betterUnsafeMethod() orElse betterBackUpMethod()

  //functions on Options
  println("functios on Options")
  println(myFirstOption.isEmpty)
  println(myFirstOption.get) //UNSAFE because it's like trying to access a no pointer if the option is not getting the value out of an empty option will throw a NullPonterExpection --> breaks the Option idea

  //map, flatmap, filter
  println("map, flatmap, filter")
  println(myFirstOption.map(_*2))
  println(myFirstOption.filter(x=> x >10)) //using filter, we can turn an option with  a value INTO an option of no values, if the predicate doesn't match. This should print "None" when x<=10
  println(myFirstOption.flatMap(x => Option(x*10)))

  //for-comprehensions
  val config: Map[String, String] = Map(
    "host" -> "172.45.56.1",
    "port" -> "80"
  )

  class Connection{
    def connect = "Connected" //connect to some server

  }

  object Connection { //companion object for Connection
    val randomValue = new Random(System.nanoTime())

    //this is a safe APi that returns an Option[Connection] given a host and port
    def apply(host: String, port: String): Option[Connection] =
      if(randomValue.nextBoolean()) Some(new Connection)
      else None


    //Trouble with this,is that this config, which is Map[String, String], contains some values that were fetched from elsewhere (like a configuration file or from another connection or from somewhere else) and we can not guarantee that "host" and "port" are filled on the map

  }

  /*
  Challenge:
  someone writes the config above (that as seen, can be or not filled
  someone different create a connection, and is assuming everything on config is ok, but we need to prevent null values
    at the end, call "connect" method, that will print just "Connected"
   */
  println("Connection exercise: (to test, run multiple times. Sometimes connection will be made, sometimes not)")

  val host = config.get("host") //Option of string
  val port = config.get("port") //Option of string

  //now we need to be able to obtain the values contained within these options and them into the apply method, BUT THE ONLY WAY to do that would be either to try out to get the value out of the Options, which is UNSAFE, and so the alternative is to use functionals!
  /*
  if(h != null)
    if(p != null)
      return connection.apply(h,p)
  else
    return null
  */
  val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h,p))) //the type of this connection is going to be Option[Connection]. Remember: when doing " host.flatMap(h =>" we are saying we are retrieving the value for "host" and that value can be there or not

  /*
  if( c!= null)
    return c.connect
  else
    return null
   */
  val connectionStatus = connection.map(c => c.connect) //connection can be there or not

  /*
  if (connectionStatus == null)
    println(none)
  else
    print(Some(connectionStatus.get))
   */
  println(connectionStatus)
  /*
  if (status != null)
    println(status)
   */
  connectionStatus.foreach(println)

  println("Using chained calls")
  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port))
      .map(connection => connection.connect))
      .foreach(println)

  println("Using for comprehensions")
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host,port)
  } yield connection.connect
forConnectionStatus.foreach(println)

  /*
  Code above can be read as:
    "given a host which is config.get("host")"
    "given a port which is config.get("port")"
    "given a connection which is Connection(host,port)"
    --> so assuming that host, port and connection are not null then
    "give me a connection.connect", otherwise give me none

    if we look, "forConnectionStatus" is Option[String]



   */
}
