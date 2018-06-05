import Modules.{AlarmUser, TickUser}

// Unifying modules and objects
object Modules {
  // Interface
  trait Tick {

    trait Ticker {
      def count: Int

      def tick: Unit
    }

    def ticker: Ticker
  }

  // Module -> Implementation
  trait TickUser extends Tick {

    class TickUserImpl extends Ticker {
      var curr = 0

      override def count: Int = curr

      override def tick: Unit = curr = curr + 1
    }

    // singleton that will carry the implementation
    object ticker extends TickUserImpl // this name is the same as the method in Tick
  }

  // Interface
  trait Alarm {

    trait Alarmer {
      def trigger: Unit
    }
    def alarm: Alarmer
  }

  trait AlarmUser extends Alarm with Tick {
    class AlarmUserImpl extends Alarmer {
      override def trigger: Unit =
        if (ticker.count % 10 == 0) println(s"alarm triggered at ${ticker.count}")
    }

    object alarm extends AlarmUserImpl
  }
}

object ModuleDemo extends App with AlarmUser with TickUser {
  println("running the ticker should trigger the alarm every 10 times")
  (1 to 100).foreach{ case i =>
    ticker.tick
    alarm.trigger
  }
}