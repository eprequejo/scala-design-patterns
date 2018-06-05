import org.scalatest._

// Testing using a class
class DoubleMultiplierIdentityTest extends FlatSpec with Matchers {

  class DoubleMultiplierIdentityClass extends DoubleMultiplierIdentity

  val instance = new DoubleMultiplierIdentityClass
  "identity" should "return 2 * 1" in {
    instance.identity should be (2)
  }

}

// Testing mixing into a test class
class TraitATest extends FlatSpec with Matchers with A {
  "hello" should "greet properly" in {
    hello should be ("I'm a trait A")
  }

  "pass" should "return the right string with the number" in {
    pass(10) should be ("Trait A says: you passed 10")
  }

  it should "be correct also for negative values" in {
    pass(-10) should be ("Trait A says: you passed -10")
  }
}

// Testing mixing into the test cases
class TraitACaseScopeTest extends FlatSpec with Matchers {
  "hello" should "greet properly" in new A {
    hello should be ("I'm a trait A")
  }
}
