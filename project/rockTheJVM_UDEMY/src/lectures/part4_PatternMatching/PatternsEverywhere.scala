package lectures.part4_PatternMatching

object PatternsEverywhere extends App {

  // Note 1
  try{
  //code here
  }catch{
    case e : RuntimeException => "runtime"
    case npe: NullPointerException => "npe"
    case _ => "something else"
  }
  //catch are actually matches!

  /*
  try{
  //code here
  }catch (e) {
  e match{
    case e : RuntimeException => "runtime"
    case npe: NullPointerException => "npe"
    case _ => "something else"
  }
  }
   */




  //Note 2
  //generators are also based on pattern matching !
  val list = List(1,2,3,4)
  val onlyEven = for{
    x <- list if x% 2 == 0
  } yield 10 * x

  val tuples = List((1,2),(3,4))
  val filterTuples = for {
    (first, second) <- tuples
  } yield first * second
  //same as above it's a generator!



  //Note 3
  val tuple = (1,2,3)
  val (a,b,c) = tuple //we are assinging multiple values by exploiting the name binding property of pattern matching (a is 1, b is 2, c is 3)
  //multiple value definition based on Pattern Matching
  // --> it's limited to tuples !!!!

  val head :: tail = list
  //this is basicaly "list match and the pattern head/tail
  println(head, tail)


  //Note 4
  //Partial Function

  /*
  Both expressions below are equivalent!
  First is a partial function literal (advance topic)

  But for both lists we are applying the pattern matching FOR EACH ELEMENT
  The output will be something like:
  List (the one, 2 is even, something else, 4 is even)

 */
  val mappedList = list.map{
    case v if v % 2 ==0 => v+ "is even"
    case 1 => "the one"
    case _ => "something else"
  }

  val mappedList2 = list.map {x => x match {
      case v if v % 2 == 0 => v + "is even"
      case 1 => "the one"
      case _ => "something else"
    }
  }




}

