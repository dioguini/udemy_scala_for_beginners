package lectures.part3_functionalProgramming

object TuplesAndMaps extends App {

  //Tuples --> finite ordered "lists". Group "things" together
  //can group 22 elements of different types, because they are used in conjunction with FunctionTypes (that go from 0 to 22)
  //index starts at 1 !


  val aTuple = new Tuple2(2, "Hello Scala!!") // Tuple2[Int, String] = (Int, String) --> the "()" are the syntactic sugar for Tuple2[Int, String]
  //val aTuple = Tuple2(2, "Hello Scala!!") // --> "new" was omitted because "Tuplew" has an apply method
  //val aTuple = (2, "Hello Scala!!")

  //What can we do with a  tuple!?
  println(aTuple._1)
  println(aTuple.copy(_2 = "goodbye java")) //copy method works as in the same style as case classes

  print(aTuple.swap) //"Hello Scala!!", 2


  //MAPS
  //associate keys(which index maps) with values(the values for each key)
  //the way we specify a map is by using Tuples or Pairings
  //are immutable

  val aMap: Map[String, Int] = Map() //also has an apply method
  val phoneBook = Map(("Jim", 555), ("Daniel", 769))
  val phoneBook_2 = Map("Jim" -> 555, "Daniel" -> 769) // "Daniel" -> 769 is a syntatic sugar for ("Daniel", 769)

  //basic map operations
  println(phoneBook.contains("Jim"))
  println(phoneBook("Jim"))
  val phoneBookDefaults = Map(("Jim", 555), ("Daniel", 769)).withDefaultValue(-1) //we also could throw an exception
  println(phoneBookDefaults("Marry"))

  val newPairing = "Marry" -> 678
  val aNewPhoneBook = phoneBook + newPairing //because maps are immutable, this will creates a new map
  println(aNewPhoneBook)


  //functionals on maps
  //map, flatMap, filter --> takes a "pairing", so applies for key and value

  println(phoneBook.map(pair => pair._1.toLowerCase -> pair._2)) //phoneBook.map work with a tuple of 2 values, and for every pairing, we can have "pair._1.toLowerCase (tht converts the first element of "phonebook" to lower), and returns the valuefor each key, for the second pair (->pair._2)


  //filterKeys
  println(phoneBook.filterKeys(x => x.startsWith("J")))
  println(phoneBook.filterKeys(_.startsWith("J"))) //same as above

  //mapValues
  println(phoneBook.mapValues(number => number * 10)) //this applies ontle for values, and  apppend a 0 for each value
  println(phoneBook.mapValues(number => "0245-" + number))
  println(phoneBook.mapValues(number => number * 51))


  //conversoin to other collections
  println(phoneBook.toList) //all pairings converts to a each list
  println(List(("Daniel", 555)).toMap) //converts a list to a map
  val names = List("Diogo", "Jenn", "Bob", "Anthony", "Marta", "Jim")
  println(names.groupBy(name => name.charAt(0))) //whoever obtains the same CharAt(0) is going to be grupped under the same list --> Jenn and Jim, for example, both have "J" at CharAt(0)


  /*
  EXERCISES
  1 - What would happen if we had two original entries "Jim" -> 550 and "JIM" -> 900 (goal for maps is to map keys and values)
      overload happens, so we need to be careful when mapping values
  2 - Overly simplified social network based on maps
      Person = String
        -- ad a person to the network
        -- remove a person
        -- friend (mutual)
        -- unfriend
        --number of friednds of a person
        - person with most firends
        - if there is a social between two people (direct or not)
  */


  // EXERCISE 1
  println("Exercise1")
  val phonebook_ex = Map("Jim" -> 555, "JIM" -> 900) //prints Map(Jim -> 555, JIM -> 900) --> OK, all good!
  println((phonebook_ex))
  val phonebook_ex2 = Map("Jim" -> 555, "Jim" -> 900) // prints Map(Jim -> 900) --> does not make sense, it OVERLOADS all values, we are loosing data
  println((phonebook_ex2))

  //EXERCISE 2
  println("Exercise2")

  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    //using Set we are avoinding duplicated values
    //if we used List insterad set, we had to deal with duplicates

    network + (person -> Set()) // this is how we add a new pairing. The purpose is to add a new pairing between person and a empty list "(person...)" parenthesis are used to not confuse the compiler
  }

  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    /*
      We can't simply remove thhe key person from the map BECAUSE this person MIGHT have friendships. So if we just remove a person it means that the friends of that person will have some dangling friendships to an inexistent person
      So, we need to fist remove those friendships and then we can remove the person as a key
     */

    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] = { //"friends" = friends of the person we want to remove. We are basically going to the friendslist and remove them from the network (this is what this function does)
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriending(networkAcc, person, friends.head)) //removeAux is being recursively called
      /*
        We basically take the first element from firiends --> friends.head
        We do an unfriend between person and that friend (unfriending(networkAcc, person, friends.head))
        We call removeAux for the remaining friends (removeAux((friends.tail)
      */
    }

    val unfriended = removeAux(network(person), network) //this basically creates an "unfriended cleaned map"
    /*
    person friends -->  network(person)
    networkAcc (a parameter from removeAux) will start with "network"

     */
    unfriended - person
    //we can finally remove person safely
  }

  def friend(network: Map[String, Set[String]], personA: String, personB: String): Map[String, Set[String]] = {
    //getting both friendlist for personA and B
    //correct way to do was with try/catch blocks!!!!
    val firendsOfA = network(personA)
    val firendsOfB = network(personB)


    network + (personA -> (firendsOfA + personB)) + (personB -> (firendsOfB + personA))
  }


  def unfriending(network: Map[String, Set[String]], personA: String, personB: String): Map[String, Set[String]] = {
    //getting both friendlist for personA and B
    //correct way to do was with try/catch blocks!!!!
    val firendsOfA = network(personA)
    val firendsOfB = network(personB)


    network + (personA -> (firendsOfA - personB)) + (personB -> (firendsOfB - personA)) //similar idea to "friend" function, but now we want to unfriend .   network + always create a new Pairing, do not forget this.
  }


  println("Exercise2")
  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Diogo"), "Marta")
  println(network)
  println(friend(network, "Diogo", "Marta"))
  println(unfriending(friend(network, "Diogo", "Marta"), "Diogo", "Marta"))
  println(remove(friend(network, "Diogo", "Marta"), "Diogo"))
  println(add(empty, "Diogo"))
  println(friend(network, "Diogo", "Marta"))


  /*
  Network with 3 people: Bob, Mary and Jim
  Bob, Mary - friends
  Bob, Jim - friends
  Jim, Mary - NOT friends
  */
  val people = add(add(add(add(empty, "Bob"), "Jim"), "Mary"), "Taylor")
  val bobJim = (friend(people, "Bob", "Jim"))
  val testNet = (friend(bobJim, "Bob", "Mary"))


  println(testNet)

  def nFriends(network: Map[String, Set[String]], person: String): Int = {
    if (!network.contains(person)) 0
    else network(person).size
  }


  println(nFriends(testNet, "Bob"))

  def mostFriends(network: Map[String, Set[String]]): String = {
    network.maxBy(pair => pair._2.size)._1
    /*
    maxBy -> receives a lambda from pairing to a value, and value needs to be comparable (on this case, it is because it's an Int
    pair._2 --> list of friends for every pairing
    pair._2.size --> return the pairing with the maximum value given by the lambda (pair => pair._2.size)
    ._1 --> is the key (or the first element) from that pairing (the name of the person) , and that's why we are returning a String for this function
    */
  }

  println(mostFriends(testNet))


  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int = {
    network.filterKeys(k => network(k).isEmpty).size //isEmpty is better than size==0
    //network.filterKeys(k => network(k).size==0).size

    /*
    For a network, we are going to filter each value that have a size = 0, and this returns a Map
    And for that map we apply ".size" that returns an int
    --> so we are basically getting the size of a map that have a list of people with 0 friends

    IDE understands that "filter.size" can be replaced with "count"

    network.filter(pair => pair._2.isEmpty).size //instead of "filterKeys" we are use "filter" that will filter "pairs", and for each pair we use "_2", which is the list of friends
    IDE will suggest to change to
      network.count(pair => pair._2.isEmpty).size
    even shorter now:
      network.count(_._2.isEmpty)

    */
  }

  println(nPeopleWithNoFriends(testNet))


  def socialConnection(network: Map[String, Set[String]], personA: String, personB: String): Boolean = {
    //bfs = Breadth First Search
    //Can we fing a "target" in "DiscoveredPeople" having considered "consideredPeople" already ?
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true  //if the person we picked was already considered, we just need to return true, we found that person
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail) //if consideredPeople contains the person we picked, we just need to call the function bfs again, with the tail of the list of people
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
        // consideredPeople + person --> person is added to consideredPeople, because we've looked at it and it's not equal to our target
        // discoveredPeople.tail ++ network(person) --> ++network(person) means we are appending all this person's direct friends, so we're going to say network(person) -- meanng, network applied to person
      }
    }

    /*
    personB is the target, and we start with an empty Set (because we haven't considered anything yet, and discoveredPeople is going to be "personA" direct friends PLUS personA itself, bevause it migth be the case that we might call social connection between personA and personA
    */
    bfs(personB, Set(), network(personA) + personA)
  }

  println(socialConnection(testNet, "Bob", "Jim"))
  println(socialConnection(testNet, "Mary", "Jim")) //also returns true because they have a friend in common?
  println(socialConnection(testNet, "Bob", "Taylor"))


}


