package lectures.part2oop

object Generics extends App {


  /**
   Basic for this topic:
   **Variance**
   If B extends A, should List[B] extend List[A] ?
   yes - covariant -> trait List[A+]
   no - invariant (default) -> trait List[A]
   no - contravariant (no way!!) -> trait List[-A]

   **Bounded types**
    solve the problem for defining generic methods that leads to an annoying variance problem
   */



  //
  // class myList[A] { //A donates a Generic type
  class myList[+A] { //lists should be covariants
    //use the type A

    /*
    method below does not work, even myList is covariant and generic
    def add (element: A): myList[A] = {//implementation here}

    Error: Covariant type A occurs in contravariant position in type A of value element
    -> meaning: this is basically a "technical version" of:
            question: since "animalList" is a covariant list of "Animal" (like declared above), can we add any animal to it?

            so, will  animalList.add(new Dog) work?
            "animalList" is of type "Animal" so it makes sense to add a Dog (or any other animal) to it.... BUT in fact "animalList" is of "Cat" type and will pollute the list of animal correct method implementation should be:
    
    -> to define a covariant list we need to be able to answer the following question:
    if I have a list of animal (for example, a list of cats) what if I add a new dog to it?
      ->if we add a dog into a list of cats... it basically turns into a list of animals
   */
   //so the method should be:
    /**
     If to a list of A, I put in a B (which is a super type of A), then this list will turn into a myList of B
     ->Cats and Dogs example:
        "A" is a list of cats
        "B" is a Animal
     */
   def add[B >: A] (element: B): myList[B] = ??? 


  }

  val listOfIntegers = new myList[Int] //Type "Int", will replace type "A" for this instance list of integers
  val listOfStrings = new myList[String] //same as above, but for Strings

  class myKeyValClass[key, value] {

  }

  val listOfPairs = new myKeyValClass[Int, String]

  //traits can also be declared with abstract types
  trait myTrait[A]


  //generic methods
  /*
  Below we have:
    object myList, which is a companion for the class "myList" defined above, has a generic method called "empty" which takes in a type parameter "A" and returns a "myList" of "A", that can be used on the implementation
  * */
  object myList{
    def empty[A]: myList[A] = { //given a type parameter "A" on "empty" method, it constructs an empty list parameterized with that type
      ???
    }
  }

  val emptyListOfIntegers = myList.empty[Int]


  /**
   VARIANCE PROBLEM
   */

  class Animal{

  }
  class Cat extends Animal{

  }
  class Dog extends Animal{

  }

  /*
  BIG QUESTION NOW
    if cat extends animal, does a list of cat also extend the list of animal?
    -> 1) Yes . with covariance
    -> 2) No. they are both different things - Invariance -> means we cannot substitute that with a new invariant list of anything else else.
    -> 3) No way! Contravariance
  */

  //1 - covariance
  class covariantList[+A]{ //+A means that this is a covariance list
  }
  val animal: Animal = new Cat
  val animalList: covariantList[Animal] = new covariantList[Cat] //basically we are replacing a list of animal with  a list of cats which makes sense, BECAUSE CATS ARE ANIMALS

  /*
  question: since "animalList" is a covariant list of "Animal" (like declared above), can we add any animal to it?
  so, will  animalList.add(new Dog) work?
  "animalList" is of type "Animal" so it makes sense to add a Dog (or any other animal) to it.... BUT in fact "animalList" is of "Cat" type and will pollute the list of animal
    ->Answer: we will need to return a list of Animals (super type for both)       
      def add[B >: A] (element: B): myList[B] = ???                                 */

  //2 - invariance
  class invariantList[A] {
  }
  //val invariantAnimalList: invariantList[Animal] = new invariantList[Cat]  //this does not make sense
  val invariantAnimalList: invariantList[Animal] = new invariantList[Animal] //this is ok!


  //3 - contravariance
  class contraVariantList[-A]{
  }
  val contravariantList: contraVariantList[Cat] = new contraVariantList[Animal]
  /*
  basically we are replacing a list of cats with  a list of animals, which DOES NOT MAKE SENSE, even "cats" are a subtype of "animals", the relationship is exactly the opposite as we see in 1)
  but now look at the example below....
   */

  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]
  //this makes sense now. because a trainer to "cat" is a trainer of "animal". we can train a cat with a trainer of animal




  /**
   * BOUNDED TYPES - solve a variance problem (variance problem: annoying problem when we ant to write covariance collections)
   allows us to use our generic classes only for certain types that are either a subclass of a different type or a super class of a different type
   */

  //below a "Upper bounded type"
  class Cage[A <: Animal] (animal: A) //Class Cage only accepts type parameters "A" which are subtypes of animal, that receives a parameter "animal" of type "A"
  val cage = new Cage(new Dog) //it works perfectly

  class Car
  //val newCage =  new Cage(new Car) //it just won't work! -> generic type needs proper bounded type

  //below a "Lower Bounded Type"
  class Cage1[A >: Animal] (animal: A) //Class Cage1 only accepts type parameters "A" which are super type of animal, that receives a parameter "animal" of type "A"

}
