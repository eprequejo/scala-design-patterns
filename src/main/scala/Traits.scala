// Traits as Interfaces
trait Alarm {
  def trigger(): String
}

trait Notifier {
  val notificationMsg: String

  def printNotification(): Unit = {
    println(notificationMsg)
  }

  def clear(): Unit
}

// Mixing in traits with variables
class NotifierImpl(val notificationMsg: String) extends Notifier {
  override def clear() = {
    println("clear")
  }
}

// Traits as Classes -> Implement all its methods and have just one constructor parameterless
trait Beeper {
  def beep(times: Int): Unit = {
    1 to times foreach(i => println(s"Beep number: $i"))
  }
}

// Instanciate the trait Beeper and call its method
object BeeperRunner extends App {
  val TIMES = 10

  // trait instanciation
  val beeper = new Beeper {}
  beeper.beep(TIMES)
}

// Extending classes
abstract class Connector {
  def connect()
  def close()
}

trait ConnectorWithHelper extends Connector {
  def findDriver(): Unit = {
    println("find driver called")
  }
}

class PgSqlConnector extends ConnectorWithHelper {
  override def connect(): Unit = println("Connected...")
  override def close(): Unit = println("Closed...")
}

// Extending traits
trait Ping {
  def ping: Unit = println("ping")
}

trait Pong {
  def pong: Unit = println("pong")
}

trait PingPong extends Ping with Pong {
  def pingpong: Unit = {
    ping
    pong
  }
}
object PingPongRunner extends App with PingPong {
  pingpong
}

// Mixin compositions
// Mixin traits in
object MixinRunner extends App with Ping with Pong {
  ping
  pong
}

// Composing simple traits
class Watch(brand: String, initTime: Long) {
  def getTime: Long = System.currentTimeMillis - initTime
}

object WatchUser extends App {

  // Annonymous class
  val expensiveWatch = new Watch("expensive brand", 1000L) with Alarm with Notifier {

    override def trigger: String = "The alarm was triggered"
    override def clear: Unit = println("cleared")
    override val notificationMsg = "Alarm is running"
  }

  // Annonymous class
  val cheapWatch = new Watch("cheap brand", 1000L) with Alarm {

    override def trigger: String = "The alarm was triggered"
  }

  println(expensiveWatch.trigger)
  expensiveWatch.printNotification
  println(s"the time is ${expensiveWatch.getTime}")
  expensiveWatch.clear

  println(cheapWatch.trigger)
  println("Cheap watch can not manually stop de alarm")

}

// Composing complex traits -> Won't compile
// illegal inheritance; superclass Watch
// is not a subclass of the superclass Connector
// of the mixin trait ConnectorWithHelper
object ReallyExpensiveWatchUser extends App {

  //val reallyExpensiveWatch = new Watch("really expensive brand", 1000L) with ConnectorWithHelper {

    //override def connect = println("connected with another connector")
    //override def close = println("closed with another connector")
  //}

  //println("using the really expensive watch")
  //reallyExpensiveWatch.findDriver
  //reallyExpensiveWatch.connect
  //reallyExpensiveWatch.close
}

// Composing with self-types
trait AlarmNotifier {
  // brings all the methods of Notifier to the scope
  // requires that any class mix in AlarmNotifier should mix in Notifier
  this: Notifier =>

  def trigger: String
}

object SelfTypeWatchUser extends App {

  // compilation error -> requires that any class mix in AlarmNotifier should mix in Notifier
  //val watch = new Watch("alarm with notification", 1000L) with AlarmNotifier {
  val watch = new Watch("alarm with notification", 1000L) with AlarmNotifier with Notifier {

    override def trigger = "Alarm triggered"
    override def clear = println("Alarm cleared")
    override val notificationMsg = "The notification"
  }

  println(watch.trigger)
  watch.printNotification
  println(s"the time is ${watch.getTime}")
  watch.clear
}

// Clashing traits
trait FormalGreeting {
  def hello: String
  //def getTime: String
}
trait InformalGreeting {
  def hello: String
  //def getTime: Int
}

class Greeter extends FormalGreeting with InformalGreeting {
  override def hello = "good morning sir/madam"
  // compilation error not allowed
  //override def getTime = 1
}

object GreeterRunner extends App {

  val greeter = new Greeter
  println(greeter.hello)
}

trait A {
  def hello = "I'm a trait A"
  def pass(a: Int): String = s"Trait A says: you passed $a"
  //def value(a: Int): Int = a
}
trait B {
  def hello = "I'm a trait B"
  //def value(a: Int): String = a.toString
}
object Clashing extends App with A with B {

  override def hello = super[A].hello
  // compilation error -> not allow
  //override def value(a: Int) = super[A].value(a)

  println(hello)
  //println(value(1))
}
// solution
trait C {
  def value(a: Int): Int = a
}
trait D {
  def value(a: Int): String = a.toString
}
object SolutionClashing extends App {
  val c = new C {}
  val d = new D {}

  println(c.value(10))
  println(d.value(10))
}

// Multiple inheritance
// The diamon problem
trait DiamonA {
  def hello = "Hello from A"
}
trait DiamonB extends DiamonA {
  override def hello = "Hello from B"
}
trait DiamonC extends DiamonA {
  override def hello = "Hello from C"
}
trait DiamonD extends DiamonB with DiamonC { }
trait DiamonD2 extends DiamonC with DiamonB { }

object DiamonRunner extends DiamonD with App {
  println(hello) // from C -> linearization
}
object DiamonRunner2 extends DiamonD2 with App {
  println(hello) // from B -> linearization
}

// Linearization
class Animal extends AnyRef // Animal -> AnyRef -> Any
class Dog extends Animal // Dog -> Animal -> AnyRef -> Any

// Override -> Super call
class MultiplierIdentity {
  def identity = 1
}
trait DoubleMultiplierIdentity extends MultiplierIdentity {
  override def identity = 2 * super.identity
}
trait TripleMultiplierIdentity extends MultiplierIdentity {
  override def identity = 3 * super.identity
}
// first double then triple
// 1 implementation
class ModifiedIdentity1 extends DoubleMultiplierIdentity with TripleMultiplierIdentity
// 2 implementation
class ModifiedIdentity2 extends DoubleMultiplierIdentity with TripleMultiplierIdentity {
  override def identity = super[DoubleMultiplierIdentity].identity
}
// 3 implementation
class ModifiedIdentity3 extends DoubleMultiplierIdentity with TripleMultiplierIdentity {
  override def identity = super[TripleMultiplierIdentity].identity
}

// first triple then double
// 1 implementation
class ModifiedIdentity4 extends TripleMultiplierIdentity with DoubleMultiplierIdentity
// 2 implementation
class ModifiedIdentity5 extends TripleMultiplierIdentity with DoubleMultiplierIdentity {
  override def identity = super[DoubleMultiplierIdentity].identity
}
// 3 implementation
class ModifiedIdentity6 extends TripleMultiplierIdentity with DoubleMultiplierIdentity {
  override def identity = super[TripleMultiplierIdentity].identity
}

object ModifiedIdentityUser extends App {

  val instance1 = new ModifiedIdentity1
  val instance2 = new ModifiedIdentity2
  val instance3 = new ModifiedIdentity3
  val instance4 = new ModifiedIdentity4
  val instance5 = new ModifiedIdentity5
  val instance6 = new ModifiedIdentity6

  println(s"Result 1: ${instance1.identity}") // 6
  println(s"Result 2: ${instance2.identity}") // 2
  println(s"Result 3: ${instance3.identity}") // 6
  println(s"Result 4: ${instance4.identity}") // 6
  println(s"Result 5: ${instance5.identity}") // 6
  println(s"Result 6: ${instance6.identity}") // 3

}
