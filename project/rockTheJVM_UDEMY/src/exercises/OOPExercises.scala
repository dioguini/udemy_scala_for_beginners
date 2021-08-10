package exercises

object OOPExercises extends App {

  val listOfIntegers: MyList_OOP[Int] = new Cons_OOP(1, new Cons_OOP(2, new Cons_OOP(3, Empty_OOP)))
  val cloneListOfIntegers: MyList_OOP[Int] = new Cons_OOP(1, new Cons_OOP(2, new Cons_OOP(3, Empty_OOP)))
  val listOfStrings: MyList_OOP[String] = new Cons_OOP("Hello", new Cons_OOP("everyone", Empty_OOP))

  val anotherListOfIntegers: MyList_OOP[Int] = new Cons_OOP(4, new Cons_OOP(5, new Cons_OOP(6, Empty_OOP)))


  //case classes verifications
  /**
   * returns true, because we are using case classes.
   * if not used, we would need to define a recursive "equals" method, which have been a great headache because we would need to compare all the elements recursively
   */
  //println(listOfIntegers == cloneListOfIntegers)

  /*
  Exercises:
    1,
    Generic trait MyPredicate[-T] (contravariant type) , returns Boolean
    -> have a small method to test whether a value of type T passes a condition
    so every subclass of "MYPredicative[T]" will actually have a different implementation of that little method which tests whether the "T" passes that condition

    class EvenPredication extends MyPredicate[Int]
    ->the method will take an Int and will return wether that Int is even or not



    2,
    Generic Trait MyTransformer[-A,B] (contravariant type on A)
    -> have a small method ("transform") to convert a parameter of type "A" into a value of type "B"
    so every subclass of "MyTransformer" will have a different implementation of that method

    class StringToIntTransformer extends MyTransformer(String, Int)
    -> the method "transform" will just return the desired type

    3,
    Implement function on MyList
      -map(transformer) returns MyList of a different type
      -filter(predicate) returns Mylist
      -flatMap(transformer)from a to MyList[B] and returns MyList[B]

      if we have the list [1,2,3]
        -> for map, if the condition is double all the elements, the output will be the list[2,4,6]
        -> for filter, if we want to return when a number is even, the output should be list[2]
        -> for flatMap, if we want a list like [n, n+1] the output should be [2,3,4] AND at the same type, the element, so it's like a concatenation of all sublists [1,2], [2,3], [3,4]
          little pseudocode: [1,2,3].flatMap[n => n, n+1] = [1,2,2,3,3,4]



     for exercises 1 and 2 instructor said "just accept" the contravariant types, otherwise code won't compile and the reason is a rabbit hole
  * */

  println("Original values for list: " + listOfIntegers)
  println("Values for transform: " + listOfIntegers.map(new MyTransformer[Int, Int] {
    override def transform(elem: Int): Int = elem * 2
  }).toString)

  /*
  this is an anonymous class

  new MyTransformer[Int, Int] {
    override def transform(elem: Int): Int = elem * 2
  }
  * */

  println("Values for test: " + listOfIntegers.filter(new MyPredicate[Int] {
    override def test(Elem: Int): Boolean = Elem % 2 == 0
  }).toString)


  println((listOfIntegers ++ anotherListOfIntegers).toString)
  println(listOfIntegers.flatMap(new MyTransformer[Int, MyList_OOP[Int]]{
    override def transform(Elem: Int): MyList_OOP[Int] = new Cons_OOP(Elem, new Cons_OOP(Elem+1, Empty_OOP))
  }).toString)
  //[1 2  2 3  3 4]



}


/**
 * The class below, can be compared with MyList_OOP, on this package.
 */

abstract class MyList_OOP[+A] {
  // This represents an immutable List, BECAUSE we are not modifying this instance, BUT YES returning a new List
  // The methods that tells us that above, are "add" and "tail", because they RETURN A NEW LIST. The other ones, are just a simple "select"/"definition" of something

  def head: A

  def tail: MyList_OOP[A]

  def isEmpty_OOP: Boolean

  def add[B >: A](element: B): MyList_OOP[B] //same as the Cats, Dogs and Animal example. See "add" method on part2oop.Generics

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
  def map[B](transformer: MyTransformer[A, B]): MyList_OOP[B]

  def flatMap[B](transformer: MyTransformer[A, MyList_OOP[B]]): MyList_OOP[B]

  //concatenation
  def ++[B >: A](list: MyList_OOP[B]): MyList_OOP[B]

  //since flatMap needs a concatenation we need to define this method

  def filter(predicate: MyPredicate[A]): MyList_OOP[A]

}

/*
Lets see the below declarations:
  val listOfIntegers: MyList_OOP[Int] = Empty_OOP
  val listOfStrings: MyList_OOP[String] = Empty_OOP
  "Empty_OOP" should be a proper value for both types of lists of Int and String
  But how do we do that?

We're going to use "Nothing" below" because ??????
* */
object Empty_OOP extends MyList_OOP[Nothing] { //"case" makes this object extremely powerful -> "equals" and "hash" are implemented OOTB , and it's serializable
  def head: Nothing = throw new NoSuchElementException //does not make sense for an Empty_OOP list...

  def tail: MyList_OOP[Nothing] = throw new NoSuchElementException //does not make sense for an Empty_OOP list...

  def isEmpty_OOP: Boolean = true

  def add[B >: Nothing](element: B): MyList_OOP[B] = new Cons_OOP(element, Empty_OOP) //when we add an element for the first time, list just have 1 element. So, "element" param is the head, and the tail of the list is Empty_OOP object

  def printElements: String = ""


  //Exercise3
  def map[B](transformer: MyTransformer[Nothing, B]): MyList_OOP[B] = Empty_OOP

  def filter(predicate: MyPredicate[Nothing]): MyList_OOP[Nothing] = Empty_OOP


  def ++[B >: Nothing](list: MyList_OOP[B]): MyList_OOP[B] = list //anything concatenated with empty ,is that thing
  def flatMap[B](transformer: MyTransformer[Nothing, MyList_OOP[B]]): MyList_OOP[B] = Empty_OOP


}

class Cons_OOP[+A](h: A, t: MyList_OOP[A]) extends MyList_OOP[A] { //"Cons_OOP" must be Covariant, accordingly MyList_OOP
  def head: A = h

  def tail: MyList_OOP[A] = t

  def isEmpty_OOP: Boolean = false

  def add[B >: A](element: B): MyList_OOP[B] = new Cons_OOP(element, this) //the element is the one added, and the tail is the list itself ("this")

  def printElements: String = {
    if (t.isEmpty_OOP) "" + h
    else {
      h + " " + t.printElements
    }
  }


  //Exercise3
  def map[B](transformer: MyTransformer[A, B]): MyList_OOP[B] = {
    //the output is a Cons, where the head is going to be transformed by the transformer
    //new Cons_OOP(transformer.transform(h), ... ) -> head of the result
    //new Cons_OOP(.. , t.map(transformer)) -> recursive call
    new Cons_OOP(transformer transform (h), t.map(transformer))
  }

  /*
  How map works
  lets say we have list[1,2,3]
  and we are going to do list[1,2,3].map(n*2)
  = new Cons_OOP(2, [2,3].map(n*2)) -> head of list is "1", multiply by 2 AND the tail is [2,3).map(n*2)
  = new Cons_OOP(2, new Cons_OOP(4, [3].map(n*2))) -> head of list is "2", multiply by 2 AND the tail is [3,3).map(n*2)
  = new Cons_OOP(2,4, new Cons_OOP(6, Empty_OOP.map(n*2)))
  = new Cons_OOP(2, new Cons_OOP(4, new Cons_OOP(6, Empty_OOP))))

  * */


  def filter(predicate: MyPredicate[A]): MyList_OOP[A] = {
    //testing if head satisfies the predicate (if predicate.head passes the test)
    //if passes the test, head, "h" will be included in the result, so must return "new Cons(h)" with the predicate so the tail needs to be filtered and the to be passed in as the result. so we have   new Cons(h,t.filter(predicate))
    //basically if head passes the predicate, it will be included in the result, so the result will be certainly a "cons with the head". Te tail will be filtered with a predicate,so we don't know what that will return (might be an empty list, but we will just delegate to the recursive call with filter
    if (predicate.test(h)) new Cons_OOP(h, t.filter(predicate))
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
  return new Cons_OOP(2, [3].filter(n%2 == 0))
  new Cons_OOP(2, Empty_OOP.filter(n%2 == 0))
  = new Cons_OOP(2, Empty_OOP)
  * */


  /*
  [1,2] ++ [3,4,5]
  = new  Cons_OOP(1, [2] ++ [3,4,5])
  = new  Cons_OOP(1, new Cons_OOP(2, Empty ++[3,4,5]))
  = new  Cons_OOP(1, new Cons_OOP(2, new  Cons_OOP(3, new  Cons_OOP(4, =ew Cons_OOP(5)))))
   */

  def ++[B >: A](list: MyList_OOP[B]): MyList_OOP[B] = new Cons_OOP(h, t ++ list)


  def flatMap[B](transformer: MyTransformer[A, MyList_OOP[B]]): MyList_OOP[B] = {
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

//Exercise 1
trait MyPredicate[-T] {
  def test(Elem: T): Boolean
}


//Exercise 2
trait MyTransformer[-A, B] { //if A is not contravariant, def map wont work
  def transform(Elem: A): B
}






