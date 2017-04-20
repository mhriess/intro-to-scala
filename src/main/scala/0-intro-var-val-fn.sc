/**
  * Intro - Philosophy, Variables, Values, and Functions
  *
  */


/** Philosophy
  *
  * Scala is an object oriented programming language. Every value is an object and every operation is a method call. It
  * supports inheritance, singletons, and mixins.
  *
  * Scala is also a functional programming language. Functions are first class objects and the standard library comes
  * with many powerful immutable data structures. Immutability is generally preferred.
  *
  */

/**
  * Variables
  *
  * Scala supports variables typical of many programming languages.
  *
  */
var age: Int = 1
age = 2

// types aren't always required for declarations- the compiler can figure out many simple cases, but it's good practice to
// include types anyways, especially when a type may not be obvious.
var name = "Bob"

/**
  * Values
  *
  * Values are like variables except they are immutable. Once they are bound, they cannot be changed.
  *
  * Immutability can lead to more intuitive, less error prone code because state changes in a more predictable fashion,
  * making code easier to reason about.
  */
val height = 5
// compiler says no! Error: reassignment to val
// height = 6

/**
  * Functions
  *
  * Functions are first class objects in Scala. They can be generated at runtime, passed as arguments, and returned from
  * functions.
  */
// simple function
def addOne(a: Int): Int = a + 1
addOne(1)

// anonymous function - notice it can be saved as a val
val addOneAnon = (a: Int) => a + 1
addOneAnon(1)

// anonymous functions are often used as arguments to common "higher order" functions (functions that take functions
// as arguments. these are all equivalent:
List(1, 2, 3).map(addOne)
List(1, 2, 3).map((x: Int) => x + 1)
List(1, 2, 3).map(x => x + 1)
List(1, 2, 3).map(_ + 1)

// functions are objects
class AddOne extends Function1[Int, Int] {
  def apply(a: Int): Int = a + 1
}

val fancyAddOne = new AddOne()
fancyAddOne(1)

// functions can be partially applied
val add = (x: Int, y: Int) => x + y
// underscore means different things in different places, here it means - "i'll get back to you on this arg"
// val fancierAddOne: Function1[Int, Int] => Int = add(1, _)
val fancierAddOne: Int => Int = add(1, _)
fancierAddOne(1)

// functions can be curried- this is big in FP world. notice the double parens for args.
def multiply(x: Int)(y: Int): Int = x * y
val double = multiply(2) _
double(3)



