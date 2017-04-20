/**
  * Data Structures and Functional Combinators
  *
  */

/**
  * Arrays
  *   -preserve order
  *   -can contain duplicates
  *   -mutable
  */
val a = Array(1,2,3,4,5)
a(1) == 2
a(1) = 3
a(1) == 3

/**
  * Lists
  *   -preserve order
  *   -can contain duplicates
  *   -immutable
  */
val l = List(1,2,3,4,5)
l(0) == 1
// not allowed!
// l(0) = 2

/**
  * Sets
  *   -do not preserve order
  *   -cannot contain duplicates
  *   -immutable
  */
val s = Set(1,1,2,3,4,4,4)

/**
  * Tuple
  *   -grab bag
  */
val t = (1, "bananas", List(1,2,3))
t._1 == 1

// can be pattern matched against
t match {
  case (a: Int, _, _) => println(s"found an int $a")
}

/**
  * Maps
  */
val m = Map("a" -> 1, "b" -> 2)
// why does this return an "Option[Int]"? wtf is Option?
val v: Option[Int] = m.get("a")

/**
  * Option - never "null" again
  *
  * Option is a -container- that may or may not hold something.
  * It has two subtypes, Some[T] and None, for each case.
  */
// still satisfies Option[Int] but the type is a None (None <: Option[T])
val v1: Option[Int] = m.get("c")

// not allowed! Option[Int] has no method "+"
// how do we get at our trapped value??
//v + 1

// danger! if v is a None, this throws an exception. you should never use this.
val dangerousV: Int = v.get

// returns the contents of v if it's a Some, otherwise the default value
val lessDangerousV: Int = v.getOrElse(0)

// pattern matching as well obvi
val explicitV: Int = v match {
  case Some(n) => n
  case None => 0
}

/**
  * Functional Combinators
  *
  * Functions that perform some kind of standardized transformation on a data structure. The standard library in Scala
  * relies heavily on them to elegantly chain operations together.
  */

// imagine a function that wraps a simple SQL query to find a user by id. how can we elegantly accommodate the fact
// that the record we're looking for may not exist? In Java, you could return null or throw an exception. In Scala, a
// more appropriate return type might be an Option[User]...
case class User(name: String, profileId: Int)

def findById(id: Int): Option[User] = id match {
  // stub out our DB
  case 1 => Option(User("Rick", 1))
  case 2 => Option(User("Morty", 2))
  // can instantiate a Some either Some() or Option(), conventionally Option() is usually used
  case 3 => Some(User("Summer", 3))
  // usually the compiler is smart enough to figure out the underlying type of a None- it infers it here from the return
  // type of findById, but there are cases when the compiler needs help figuring out the typing, which can be fulfilled
  // using Option.empty[SomeType]
  case _ => None
}

val user1 = findById(1)
val user2 = findById(2)
val user3 = findById(4)

// how do we get a user's name? each of these may be a Some or a None
// the argument to map (a function) is only evaluated if user1 is a Some[User]
val user1Name = user1.map(user => user.name).getOrElse("")

// how do we get all of these users' names?
List(user1, user2, user3)
  // combinator- returns a new list from a List[Option[T]] of List[T] removing any Nones
  .flatten
  // combinator- returns a new list by applying the argument to map over each element in the list
  .map(_.name) // same as (user => user.name)

// what if I have sequential operations that each return an Option?
case class Profile(age: Int)

def findProfileById(id: Int): Option[Profile] = id match {
  case 1 => Option(Profile(age = 65))
  case 2 => Option(Profile(age = 13))
  case _ => None
}

// find a user's age given their user id...

// first try... we end up with nested Options... probably not a good situation, hard to get at our desired value
val try1: Option[Option[Int]] = findById(1).map(user => findProfileById(user.profileId).map(profile => profile.age))
// flatMap is another functional combinator that can be used to prevent this kind of nesting
val try2: Option[Int] = findById(1).flatMap(user => findProfileById(user.profileId).map(profile => profile.age))
// this pattern of chained flatMapping is so common that scala provides syntactic sugar over it as "for comprehensions"
val try3: Option[Int] = for {
  user <- findById(1)
  profile <- findProfileById(user.profileId)
} yield profile.age // snazzy! can also evaluate a block here:
// yield { // perform some computation }