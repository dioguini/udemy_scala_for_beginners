package exercises

object MyList_Function extends App {

  val listOfIntegers: MyOFPExerciseList[Int] = new ConsOFPExercise(1, new ConsOFPExercise(2, new ConsOFPExercise(3, EmptyOFPExercise)))
  val cloneListOfIntegers: MyOFPExerciseList[Int] = new ConsOFPExercise(1, new ConsOFPExercise(2, new ConsOFPExercise(3, EmptyOFPExercise)))
  val listOfStrings: MyOFPExerciseList[String] = new ConsOFPExercise("Hello", new ConsOFPExercise("everyone", EmptyOFPExercise))

  val anotherListOfIntegers: MyOFPExerciseList[Int] = new ConsOFPExercise(4, new ConsOFPExercise(5, new ConsOFPExercise(6, EmptyOFPExercise)))


  //case classes verifications
  /**
   * returns true, because we are using case classes.
   * if not used, we would need to define a recursive "equals" method, which have been a great headache because we would need to compare all the elements recursively
   */
  //println(listOfIntegers == cloneListOfIntegers)

  println("Original values for list: " + listOfIntegers)
  println("Values for transform: " + listOfIntegers.map(new MyTransformerFPExercise[Int, Int] {
    override def transform(elem: Int): Int = elem * 2
  }).toString)

  /*
  this is an anonymous class

  new MyTransformerFPExercise[Int, Int] {
    override def transform(elem: Int): Int = elem * 2
  }
  * */

  println("Values for test: " + listOfIntegers.filter(new MyPredicateFPExercise[Int] {
    override def test(Elem: Int): Boolean = Elem % 2 == 0
  }).toString)


  println((listOfIntegers ++ anotherListOfIntegers).toString)
  println(listOfIntegers.flatMap(new MyTransformerFPExercise[Int, MyOFPExerciseList[Int]]{
    override def transform(Elem: Int): MyOFPExerciseList[Int] = new ConsOFPExercise(Elem, new ConsOFPExercise(Elem+1, EmptyOFPExercise))
  }).toString)
  //[1 2  2 3  3 4]



}


/**
 * The class below, can be compared with MyOFPExerciseList, on this package.
 */

abstract class MyOFPExerciseList[+A] {
  // This represents an immutable List, BECAUSE we are not modifying this instance, BUT YES returning a new List
  // The methods that tells us that above, are "add" and "tail", because they RETURN A NEW LIST. The other ones, are just a simple "select"/"definition" of something

  def head: A

  def tail: MyOFPExerciseList[A]

  def isEmptyOFPExercise: Boolean

  def add[B >: A](element: B): MyOFPExerciseList[B] //same as the Cats, Dogs and Animal example. See "add" method on part2oop.Generics

  def printElements: String

  /*
  polymorphic call
  -> toString method calls "printElements"
  -> when we call later "toString" method, the correct implementation of "printElements" will be called, that why this is a polymorphic call
   */
  override def toString: String = {
    "[" + printElements + "]"
  }


  //Exercise3
  def map[B](transformer: MyTransformerFPExercise[A, B]): MyOFPExerciseList[B]

  def flatMap[B](transformer: MyTransformerFPExercise[A, MyOFPExerciseList[B]]): MyOFPExerciseList[B]

  //concatenation
  def ++[B >: A](list: MyOFPExerciseList[B]): MyOFPExerciseList[B]

  //since flatMap needs a concatenation we need to define this method

  def filter(predicate: MyPredicateFPExercise[A]): MyOFPExerciseList[A]

}

/*
Lets see the below declarations:
  val listOfIntegers: MyOFPExerciseList[Int] = EmptyOFPExercise
  val listOfStrings: MyOFPExerciseList[String] = EmptyOFPExercise
  "EmptyOFPExercise" should be a proper value for both types of lists of Int and String
  But how do we do that?

We're going to use "Nothing" below" because ??????
* */
object EmptyOFPExercise extends MyOFPExerciseList[Nothing] { //"case" makes this object extremely powerful -> "equals" and "hash" are implemented OOTB , and it's serializable
  def head: Nothing = throw new NoSuchElementException //does not make sense for an EmptyOFPExercise list...

  def tail: MyOFPExerciseList[Nothing] = throw new NoSuchElementException //does not make sense for an EmptyOFPExercise list...

  def isEmptyOFPExercise: Boolean = true

  def add[B >: Nothing](element: B): MyOFPExerciseList[B] = new ConsOFPExercise(element, EmptyOFPExercise) //when we add an element for the first time, list just have 1 element. So, "element" param is the head, and the tail of the list is EmptyOFPExercise object

  def printElements: String = ""


  //Exercise3
  def map[B](transformer: MyTransformerFPExercise[Nothing, B]): MyOFPExerciseList[B] = EmptyOFPExercise

  def filter(predicate: MyPredicateFPExercise[Nothing]): MyOFPExerciseList[Nothing] = EmptyOFPExercise


  def ++[B >: Nothing](list: MyOFPExerciseList[B]): MyOFPExerciseList[B] = list //anything concatenated with empty ,is that thing
  def flatMap[B](transformer: MyTransformerFPExercise[Nothing, MyOFPExerciseList[B]]): MyOFPExerciseList[B] = EmptyOFPExercise


}

class ConsOFPExercise[+A](h: A, t: MyOFPExerciseList[A]) extends MyOFPExerciseList[A] { //"ConsOFPExercise" must be Covariant, accordingly MyOFPExerciseList
  def head: A = h

  def tail: MyOFPExerciseList[A] = t

  def isEmptyOFPExercise: Boolean = false

  def add[B >: A](element: B): MyOFPExerciseList[B] = new ConsOFPExercise(element, this) //the element is the one added, and the tail is the list itself ("this")

  def printElements: String = {
    if (t.isEmptyOFPExercise) "" + h
    else {
      h + " " + t.printElements
    }
  }


  //Exercise3
  def map[B](transformer: MyTransformerFPExercise[A, B]): MyOFPExerciseList[B] = {
    //the output is a Cons, where the head is going to be transformed by the transformer
    //new ConsOFPExercise(transformer.transform(h), ... ) -> head of the result
    //new ConsOFPExercise(.. , t.map(transformer)) -> recursive call
    new ConsOFPExercise(transformer transform (h), t.map(transformer))
  }

  /*
  How map works
  lets say we have list[1,2,3]
  and we are going to do list[1,2,3].map(n*2)
  = new ConsOFPExercise(2, [2,3].map(n*2)) -> head of list is "1", multiply by 2 AND the tail is [2,3).map(n*2)
  = new ConsOFPExercise(2, new ConsOFPExercise(4, [3].map(n*2))) -> head of list is "2", multiply by 2 AND the tail is [3,3).map(n*2)
  = new ConsOFPExercise(2,4, new ConsOFPExercise(6, EmptyOFPExercise.map(n*2)))
  = new ConsOFPExercise(2, new ConsOFPExercise(4, new ConsOFPExercise(6, EmptyOFPExercise))))

  * */


  def filter(predicate: MyPredicateFPExercise[A]): MyOFPExerciseList[A] = {
    //testing if head satisfies the predicate (if predicate.head passes the test)
    //if passes the test, head, "h" will be included in the result, so must return "new Cons(h)" with the predicate so the tail needs to be filtered and the to be passed in as the result. so we have   new Cons(h,t.filter(predicate))
    //basically if head passes the predicate, it will be included in the result, so the result will be certainly a "cons with the head". Te tail will be filtered with a predicate,so we don't know what that will return (might be an empty list, but we will just delegate to the recursive call with filter
    if (predicate.test(h)) new ConsOFPExercise(h, t.filter(predicate))
    //if the head does not pass, can not be returned, only the predicate
    else t.filter(predicate)
  }

  /*
  How filter works
  original list is [1,2,3] and we want only the predicates, so the values that rest of division of value by 2 is 0
  tests always start on the head, so:
  ->[1,2,3].filter(n%2 == 0)
  "1" is not predicate
  let's evaluate the tail [2,3]
  ->[2,3].filter(n%2 == 0)
  2 is predicate
  return new ConsOFPExercise(2, [3].filter(n%2 == 0))
  new ConsOFPExercise(2, EmptyOFPExercise.filter(n%2 == 0))
  = new ConsOFPExercise(2, EmptyOFPExercise)
  * */


  /*
  [1,2] ++ [3,4,5]
  = new  ConsOFPExercise(1, [2] ++ [3,4,5])
  = new  ConsOFPExercise(1, new ConsOFPExercise(2, Empty ++[3,4,5]))
  = new  ConsOFPExercise(1, new ConsOFPExercise(2, new  ConsOFPExercise(3, new  ConsOFPExercise(4, =ew ConsOFPExercise(5)))))
   */

  def ++[B >: A](list: MyOFPExerciseList[B]): MyOFPExerciseList[B] = new ConsOFPExercise(h, t ++ list)


  def flatMap[B](transformer: MyTransformerFPExercise[A, MyOFPExerciseList[B]]): MyOFPExerciseList[B] = {
    {
      transformer.transform(h) ++ t.flatMap(transformer)
    }
    /*
   How it works
   we have list[1,2], the goal is to do:
   [1,2,].flatMap(n => n, n+1)
   = [1,2] ++ [2].flatMap(n => [n, nº1])
   = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])           --> [2,3] is the result of (1+1) and (2+1) and now we want to concatenate both lists
   = [1,2,2,3]
     */
  }

}



//Both traits below are not needed anymore, since like we can see, they are basically functions

trait MyPredicateFPExercise[-T] {
  def test(Elem: T): Boolean
}


//Exercise 2
trait MyTransformerFPExercise[-A, B] { //if A is not contravariant, def map wont work
  def transform(Elem: A): B
}







