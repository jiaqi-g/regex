package core

/**
 * Created by victor on 12/8/14.
 */
abstract class Reg

case class Eps extends Reg
case class Sym(c: Char) extends Reg
case class Alt(r1: Reg, r2: Reg) extends Reg
case class Seq(r1: Reg, r2: Reg) extends Reg
case class Rep(r: Reg) extends Reg
