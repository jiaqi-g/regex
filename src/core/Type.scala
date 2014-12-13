package core

/**
 * Regex Core class
 */
abstract class Regex {

  def empty: Boolean
  def _shift(c: Char, mark: Boolean): Boolean

  // a component is marked if and only if already "fully" matched
  var marked = false

  def Regex() {}

  def reset(): Unit = {
    marked = false
  }

  def shift(c: Char, mark: Boolean): Boolean = {
    val marked = _shift(c, mark)
    this.marked = marked
    marked
  }

  def _match(re: Regex, s: Array[Char]): Boolean = {
    if (s.length == 0) {
      return re.empty
    }

    // shift a mark in reading the first char
    var result = re.shift(s(0), true)

    // shift internal marks reading later chars
    for (i <- 1 until s.length) {
      result = re.shift(s(i), false)
    }

    re.reset()
    result
  }
}

/**
 * Re for character representation
 */
case class Sym(char: Char) extends Regex {

  def empty = false

  def _shift(c: Char, mark: Boolean) = mark && (c == char)
}

/**
 * Re for empty string representation
 */
case class Eps() extends Regex {

  def empty = true

  def _shift(c: Char, mark: Boolean) = false
}

abstract class Binary(left: Regex, right: Regex) extends Regex {
  override def reset(): Unit = {
    left.reset()
    right.reset()
    super.reset()
  }
}

/**
 * Re for alternative representation
 */
case class Alt(left: Regex, right: Regex) extends Binary(left, right) {

  def empty = left.empty || right.empty

  def _shift(c: Char, mark: Boolean) = {
    val marked_left  = left.shift(c, mark)
    val marked_right = right.shift(c, mark)

    // prevent short-cut
    marked_left || marked_right
  }
}


/**
 * Re for sequence representation
 */
case class Seq(left: Regex, right: Regex) extends Binary(left, right) {

  def empty = left.empty && right.empty

  def _shift(c: Char, mark: Boolean) = {
    val old_marked_left = left.marked

    val marked_left = left.shift(c, mark)
    val marked_right = right.shift(c, old_marked_left || (mark && left.empty))

    (marked_left && right.empty) || marked_right
  }
}

/**
 * Re for repetition representation
 */
case class Rep(re: Regex) extends Regex {

  def empty = true

  def _shift(c: Char, mark: Boolean) = re.shift(c, mark || marked)

  override def reset() {
    re.reset()
    super.reset()
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    println(new Sym('a').empty)
  }
}
