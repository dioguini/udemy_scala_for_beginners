package lectures.part3_functionalProgramming

object Collections {

  /*
  Scala offers 2 collection types:
  Mutable - can be updated in place
  Immutable - NEVER change (we always need to instantiate a new collection everytime we need to make a change)



Standard library has type definitions or tpe aliases for immutable collections (both below use the immutable collections by default
package object scala{
type List[+A] = immutable.List[A]

}

 object Predef {
 type Map[A, +B] = immutable.Map[A, B]
 type Set[A] = immutable.Set[A]
 }

  */


  // IMMUTABLE COLLECTIONS

  /**
Imutable collections are found in scala.coolections.immutable package

Transversable - the "mother" of all collections, that reside in the scala collections immutable package
    --> is extended by  the iterable trait, which in turn is extended by the major collections
    --> check ImmutableCollectionsDiagram.png on this package
    --> offers great variety of methods
        maps (map, flatMap, collect
        conversions (toArray, toList, toSeq)
        size info (isEMpty, size, nonEmpty)
        tests (exists, forall)
        folds (foldLeft, foldRight, reduceLeft, reduceRight)
        retrieval (head, find, tail)
        strings ops (mkString)


Transversable
---> Iterable
------> Set - type of collections that do not contain duplicates (hash seets and sortedSets)
------------> HashSet
------------> SortedSet
------> Seq - kind of collections that can be traversed in a set order
------------> IndexedSeq - have the property that it can be quickly accessed
--------------------> Vector
--------------------> Range
--------------------> String
------------> LinearSeq - only guarantee in some form the ordering of elements
--------------------> List
--------------------> Stream
--------------------> Stack
--------------------> Queue
------> Map - make the associations between keys and values
------------> HashMap
------------> SortedMap
   */





  //MUTABLE COLLECTIONS
  /*
  Can be found in scla.collections.mutable package and has a very similar hierechy when compared with Immutable collections, BUT the actual implementations for multiple collections differ slightly


Transversable
---> Iterable
------> Set
------------> HashSet
------------> LinkedHashSet
------> Seq
------------> IndexedSeq
--------------------> StringBuilder
--------------------> ArrayBuffer (also extends from Buffer) we can say extends multiple traits
------------> Buffer
--------------------> ArrayBuffer (also extends from IndexedSeq)  we can say extends multiple traits
--------------------> List BUffer
------------> LinearSeq
--------------------> LinkedList
--------------------> MutableList
------> Map
------------> HashMap
------------> MultiMap



  */



}
