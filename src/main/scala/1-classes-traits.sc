/**
  * Classes, Traits, and Inheritance
  *
  */

/**
  * Scala classes are relatively simple and don't define a special method for constructors.
  */

// the parens after the class name is the constructor
// args can have defaults
class Dog(
  name: String,
  breed: String = "",
  isLoyal: Boolean = false,
  val smelliness: String = "extreme",
  var rabidness: Int = 0,
  private val height: Int = 3
) {
  // instance methods have access to state
  // parens can be omitted when declaring methods without args
  def speak: String = s"Hello my name is $name my size is $size and I am ${if (isLoyal) "" else "not"} loyal"

  // an example of how a constructor param can be used to initialize state
  // the `match` keyword is a feature called pattern matching- extremely high powered if/else
  val size: String = breed match {
    case "Burmese Mountain Dog" | "St. Bernard" => "big"
    case _ => "unknown"
  }
}

val odie = new Dog("Odie")
// this constructor param is private- no getter
//odie.name
// explicitly set to private, not accessible
//odie.height
// getter created
odie.smelliness
// still a val though! cannot be reassigned
//odie.smelliness = "mild"
// behaves like a var, reassignment allowed
odie.rabidness = 2


// parens can be omitted when calling methods without args
// args with default params can be omitted
odie.speak // == odie.speak()


// scala supports named args- very handy for ambiguous boolean arguments
val bigDog = new Dog("Beethoven", "St. Bernard", isLoyal = true)
bigDog.speak

/**
  * Traits can't be instantiated, they are used to share common behavior.
  */
trait Obedient {
  def sit: String = "Ok, I did it. Treat now?"
  def shake: String = "Seriously though. Treats or what?"
}

class SmartDog(name: String, breed: String = "", isLoyal: Boolean = false)
  extends Dog(name, breed, isLoyal)
  with Obedient

val smartDog = new SmartDog("Lassie")
smartDog.sit
smartDog.shake

/**
  * Classes can also extend other classes. Typical inheritance pattern.
  */
class RobotDog(name: String, breed: String = "", isLoyal: Boolean = false)
  extends Dog(name, breed, isLoyal) {

  // possible to override inherited methods
  override def speak: String = "Prepare for world domination, inferior squishy human."
}

val robotDog = new RobotDog("Destructo-Bot 9000")
robotDog.speak