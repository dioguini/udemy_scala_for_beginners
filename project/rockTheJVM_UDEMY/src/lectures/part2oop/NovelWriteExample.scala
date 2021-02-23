package lectures.part2oop

object NovelWriteExample extends App{

  /**
   * Novel and Writer
  --Novel
  ---args
        name
        year of release
        author
  ---methods:
        authorAge (age of the autor at the year of the release)
        isWWrittenBy(Author)
        copy (new year of release) return a new instance of the novel

  ---Writer
  ---args
        first name
        surname
        year
  ---methods
        fullanme - return name+surname


  --Counter Class
  ---receives
      an int value
   ---methods:
        show the current count
        method to increment/decrement the counter by 1 -> return a new Counter
        overload incrementer/decrement methods to receive an amount -> returns a new Counter
   */


  val myAuthor = new Writer (firstName="Jose", surname= "Saramago", yearOfBirth = 1922)
  val myImpostor = new Writer (firstName="Diogo", surname= "Fernandes", yearOfBirth = 1922)
  val myNovel = new Novel(name="Memorial do Convento",yearRelease=2016 , author=myAuthor)
  println(myNovel.authorAge())

  println(myNovel.isWrittenBy(myAuthor))
  println(myNovel.isWrittenBy(myImpostor)) //returns false due to isWrittenBy implementation

  val counterStart = 0
  val myCounter = new Counter(counterStart)
  myCounter.increment().print
  myCounter.increment().increment().increment().print // não esquecer que os métodos retornam sempre um novo objeto, logo esta linha é possível de fazer. podíamos estar aqui eternamente a fazer esta linha porque é possível.
  myCounter.increment(10).print




  class Novel (name: String, yearRelease: Int, author: Writer){
    def authorAge (): Int = {
      yearRelease - author.yearOfBirth
    }

    def isWrittenBy (author: Writer): Boolean = {
      author == this.author
    }

    def createNewCopy(newYearRelease: Int): Novel ={
      new Novel(name, newYearRelease, author)
    }

  }

  class Writer (firstName: String, surname: String, val yearOfBirth: Int){

    def fullName: String ={
      //firstName + " " + surname
      s"$firstName $surname"
    }

  }


  class Counter (val count: Int) {
    def increment (): Counter = {
      println("Incrementing: ")
      new Counter(count+1)
      /**
       * IMMUTABILITY !!!!!!
          - we are creating a new object WITHOUT modifying the current one -> we are creating a new Counter without modifying the current one already declared
        it's an extension to the idea of immutability to "val" primitive type. Here it's for classes and objects
       --> This basically says that instances are FIXED. They can not be modified INSIDE.
            Whenever we need to modify the contents of an instance WE NEED TO RETURN A NEW INSTANCE
       */
    }
    def decrement(): Counter = {
      println("Decrementing: ")
      new Counter(count-1)
    }

    //overloads
    /**
    What does this method mean?
     Remember we always want to AVOID loops! we always want recursion instead!
     Do not forget the logic of the accumulator. it's the same here. we receive a value, and then we do the logic and print the final result at the end
     */
    def increment(n: Int): Counter = {
      if (n<=0)
        this //returns the instance itself. No need to do anything
      else
        increment.increment(n-1) //does all the logic (creation of an object and goes to the next one and so on). because we have the print method, we can print on the console "Incrementing" or "decrementing"

    }

    def decrement(n: Int): Counter = {

      if (n <= 0)
        this //returns the instance itself. No need to do anything
      else
        increment.increment(n - 1)
    }

    def print = println(count)

  }


  }

  /**
  *
  class Counter (number: Int) {
    def currentCount = number
  }

  Equivalent class to the above declared:
     class Counter (val number: Int) {
    }

  --> Why: on the first one "number" is a parameter and we need to define a method (currentCount) to return the value of the "number" parameter -> basically a "getter" method
  We can simplify by just defining "number" as a val. And then we can just access it normally.

   */
