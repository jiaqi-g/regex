/**
 * Created by victor on 12/8/14.
 */

class Complex(real: Double, imaginary: Double) {
  val re = real
  val im = imaginary
}

class Run extends App {
  println(new Complex(3,2).re)
}
