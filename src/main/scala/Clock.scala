package lapse

/** Exposes a means of obtaining the current time */
abstract class Clock {
  /** @return the current time in nanos */
  def read: Long
}

object Clock {
  implicit val system: Clock = Clock(() => System.nanoTime)

  def apply(now: () => Long): Clock = new Clock {
    def read = now()
  }
}
