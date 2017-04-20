/**
  * Objects, Case Classes, and Pattern Matching
  *
  */

/**
  * Objects hold a single instance of a class. They're often combined with the magic
  * "apply" method to create factories. Singletons, collections of static methods.
  */
// when a class and an object have the same name, the object is called a "companion object"
object Bar {
  def apply(foo: String): Bar = new Bar(foo)
}
class Bar(foo: String) {
  override def toString = s"Foo is $foo"
}

// notice we don't need new Bar(...). the companion object apply method takes care of this for us.
Bar("bam!")

object DogFactory {}
// not allowed- can't instantiate objects
//new DogFactory()

/**
  * Pattern matching is if/else branching on steroids.
  *
  */
val amount = 2
val pluralized = amount match {
  case 0 => "like, none"
  case 1 => "just the one"
  case 2 => "couple"
    // here the underscore is a catch all default case
  case _ => "hecka"
}

val betterPluralized = amount match {
  // these are arbitrary names bound to amount that could be used but aren't here. _ would be fine
  case some if amount < 5 => "some"
  case _ if amount >= 5 => "hecka"
}

// Any is the root of the scala class hierarchy.
val mysteryBox: Any = "hey there"
val mysteryBoxContents = mysteryBox match {
  // same deal here- i and s are optional as they aren't actually used
  case i: Int => "I'm an Int!"
  case s: String => "I'm a String!"
  case _ => "Truly a mystery!"
}

// the compiler will try and warn you if you use pattern matching without accounting for all the cases it can figure
// out

/**
  * Scala provides a neat construct called case classes that bundle up some common class operations for ease of use.
  *
  */
abstract class Robot {}

case class BloodthirstyRobot(thirstForHumanBlood: Int) extends Robot

case class FriendlyRobot(name: String) extends Robot


val r1 = BloodthirstyRobot(9000)
val r2 = FriendlyRobot("Rosie")

// they're immutable
// compiler says no! Error: reassignment to val
//r1.thirstForHumanBlood = 0


// they're decomposable through pattern matching
def assessThreat(robot: Robot): String = robot match {
  case BloodthirstyRobot(thirstForHumanBlood) if thirstForHumanBlood > 10 => "Run for your life!"
  case BloodthirstyRobot(thirstForHumanBlood) if thirstForHumanBlood <= 10 => "Proceed with caution."
  // underscore means we don't care about the attrs here
  case FriendlyRobot(_) => "Harmless! Probably...."
}

// compared by structural equality, not reference
r2 == FriendlyRobot("Rosie")

// comes with a built in 'copy' method
val r3 = r1.copy(thirstForHumanBlood = 10)
// the original instance is unchanged... immutable!
r1 != r3