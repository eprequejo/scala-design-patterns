import ADTs._

// Unifying ADTs and class hierarchies
object ADTs {

  // Sum ADTs
  sealed abstract trait Month
  case object January extends Month
  case object February extends Month
  case object March extends Month
  case object April extends Month
  case object May extends Month
  case object June extends Month
  case object July extends Month
  case object August extends Month
  case object September extends Month
  case object October extends Month
  case object November extends Month
  case object December extends Month

  // Product ADTs
  sealed case class RGB(r: Int, g: Int, b: Int)

  // Hybrid ADTs
  case class Point(x: Double, y: Double)

  sealed abstract trait Shape
  case class Circle(centre: Point, r: Double) extends Shape
  case class Rectangle(topLeft: Point, h: Double, w: Double) extends Shape

  // Pattern matching with values -> with Sum ADTs
  object Month {
    def toInt(m: Month): Int = m match {
      case January => 1
      case February => 2
      case March => 3
      case April => 4
      case May => 5
      case June => 6
      case July => 7
      case August => 8
      case September => 9
      case October => 10
      case November => 11
      case December => 12
    }
  }

  // Pattern matching with product and hybrid ADTs
  object Shape {
    def area(s: Shape): Double = s match {
      case Circle(Point(x, y), r) => Math.PI * Math.pow(r, 2)
      case Rectangle(_, h, w) => h * w
    }
  }
}

object MonthDemo extends App {
  val m: Month = February
  println(s"current month is: $m and its number is: ${Month.toInt(m)}")
}

object RGBDemo extends App {
  val m = RGB(255, 0, 255)
  println(s"magenta in RGB is: $m")
}

object ShapeDemo extends App {
  val circle = Circle(Point(1, 2), 2.5)
  val rec = Rectangle(Point(6, 7), 5, 6)
  println(s"circle area: ${Shape.area(circle)}")
  println(s"rectagle area: ${Shape.area(rec)}")

}
