package core

/**
 * author: victor on 12/8/14.
 */

class Eval {
  def accept(r: Reg, s: String): Boolean = r match {
    case Eps() => s == ""
    case Sym(c) => (s.length == 1) && (s.charAt(0) == c)
    case Alt(p, q) => accept(p, s) || accept(q, s)
    // case Seq(p, q) => {}
    // case Rep(r) => {}
  }
}

object Eval {
  //var r: Eval = null

  def main (args: Array[String]) {
    //println(3)
    val r = new Eval
    println(r.accept(Alt(Sym('a'), Sym('b')), "b"))
    println("test")
  }
}