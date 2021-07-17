package lectures.part2oop

import lectures.part2oop.NovelWriteExample.Writer

import playground.{Cinderella => Princess, PrinceCharming}

import java.util.Date
import java.sql.{Date => SQLDate}


object PackagingAndImports extends App {

  val writer = new Writer("Daniel", "RockTeheJVM",2019) //requires to import lectures.part2oop.NovelWriteExample.Writer

  val write2 = new lectures.part2oop.NovelWriteExample.Writer("Diogo", "vida", 2020) //same as line above, but import not used

  //packages are in hierarchy
  //matching folder structure

  /**
  Package Object (ONLY ONE PER PACKAGE!) - scala specific

  specific code organizing structure
  we can now write classes, traits, objects, and we can only a ccess vallures or methods, constants from them)
   -> to solve the problem above, we sometimes we may want to write methods or constants outside of basically everything else
    --> no names required, because we only can use one per package

   */
  //using package object part2oop
  sayHlloToEveryone
  println(SPEED_OF_LIGHT)


  /**
   IMPORTS
   * */
  //val princess = new Cinderella
  val prince = new PrinceCharming
  //to import both classes above, import will be like import playground.{Cinderella, PrinceCharming}
  // but we could use just import playground.*

  /*
  Import changed from
        import playground.{Cinderella, PrinceCharming}
    to
        import playground.{Cinderella => Princess, PrinceCharming}

  import playground.{Cinderella => Princess} when using a single class

this is useful if for some reason we need to import more than one class with the same name from different packages, and this aliasing will solve naming conflict problems
   */

  val princess2 = new Princess


  val date = new Date
  val sqlDate = new java.sql.Date(2021,17,7)
  //line above is the same as below, because we are using aliasing when import java.sql.Data
  val sqlDate2 = new SQLDate(2021,7,18)

  //default imports -> automatically imported without any intentional import from our side
  //java.lang - string, Object, Exception
  //scala - Int, Nothing, Function
  //scala.Predef - println

}
