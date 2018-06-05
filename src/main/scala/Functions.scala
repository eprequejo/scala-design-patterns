import Functions.{FunctionLiterals, FunctionObjects}

// Unifying classes and functions -> Functions as classes
object Functions {

  // Function literals -> with syntactic sugar
  class FunctionLiterals {

    val sum = (a: Int, b: Int) => a + b

    def runOp(f: (Int, Int) => Int, a: Int, b: Int) = f(a, b)
  }

  // Function literals -> without syntactic sugar
  class SumFunction extends Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }

  class FunctionObjects {
    val sum = new SumFunction

    def runOp(f: (Int, Int) => Int, a: Int, b: Int): Int = f(a, b)
  }
}

object FunctionLiterals extends App {
  val obj = new FunctionLiterals
  println(s"3 + 9 = ${obj.sum(3, 9)}")
  obj.runOp(obj.sum, 3, 9)
  obj.runOp(Math.max, 3, 9)
}

object FunctionObjects extends App {
  val obj = new FunctionObjects
  println(s"3 + 9 = ${obj.sum(3, 9)}")
}
