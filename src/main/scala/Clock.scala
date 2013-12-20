package lapse

/** Exposes a means of obtaining the current time */
trait Clock {
  /** @return the current time in millis */
  def read: Long
}

object Clock {
  implicit val SystemClock: Clock = new Clock {
    def read = System.nanoTime()  
  }
}
