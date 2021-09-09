package exercises


abstract class Maybe[+T] { //receives a Type parameter (T) which is covariant "+T" and ist extended by 2 case subtypes

  /**
  Yes, having a collection just one value... Meh, it does not make sense
  HOWEVER, this class has a purpose!
  Since the philosophy of "maybe" class here, is to denote the possible abscence of a value
   and this is extremely important
   */


  def map[B](f: T => B): Maybe[B] //type parameter, B, and a functin that receives a T and returns a B, and it will return MAybe a T (similar to MyList)
  def flatMap[B](f: T => Maybe[B]): Maybe[B] //type parameter B, and then takes a transformer from T to Maybe[B] and it will return a Maybe[B)
  def filter(p: T => Boolean): Maybe[T] //receives a predicate from T to BOolean, and returns Maybe[T]


}


case object MaybeNot extends Maybe[Nothing] { //similar to "MyList" we have an empty "collection"
  def map[B](f: Nothing => B): Maybe[B] = MaybeNot //empty.map("something_some_function") will return an empty "list"
  def flatMap[B](f: Nothing => Maybe[B]): Maybe[B] = MaybeNot
  def filter(p: Nothing => Boolean): Maybe[Nothing] = MaybeNot // emptyCollection.filter with some predicate, it will return an empty collection
}

case class Just[T](value: T) extends Maybe[T] { //similar to "MyList" we have a full "collection" BUT NOW WITH JUST ONE VALUE, (value: T)
  def map[B](f: T => B):Maybe[B] = Just(f(value)) //if we have a collection with only one element, then we will still return a collection with one element and the value will be the result of apply "f" to the value that we currently have
  def flatMap[B](f: T => Maybe[B]): Maybe[B] = f(value)  //since we just have one value, we just need to apply the function the value. and not need to do anything else (like getting the tail of the list, or something else)
  def filter(p: T => Boolean): Maybe[T] =
    if (p(value)) this //if "p" applied to the value, we will return the same object
    else MaybeNot //if "p" applied to value is false, then we will return nothing, so "Maybenot"

}


object MaybeTester extends App {
  val just3 = Just(3)
  println(just3)

  println(just3.map(_*2))
  println(just3.flatMap(x => Just(x % 2 == 0))) //we are creating a new "just" object, with a boolean inside, so this is going to be a Just false
  print(just3.filter(x=> x % 2 == 0)) //will return MaybeNot, because just through that filter and the predicate is not going to pass for the value three. SO from a JUST object, we are going to end up with a MAYBENOT object

}