package lapse

/** Exposes a means of obtaining the current time */
trait Clock {
  /** @return the current time in nanos */
  def read: Long
}

object Clock {
  implicit val SystemClock: Clock = new Clock {
    def read = System.nanoTime()  
  }
  /** @return a clock whose reading doesn't change */
  def const(reading: Long): Clock = new Clock {
    val read = reading
  }
}
