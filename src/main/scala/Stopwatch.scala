package lapse

import scala.concurrent.duration.{ Duration, FiniteDuration }

/** Tracks the passage of time */
trait Stopwatch {
  type Elapsed = () => FiniteDuration

  /** @return a function that, when applied, returns a duration from the start point */
  def start()(implicit clock: Clock): Elapsed

  /**
   * @param l log function
   * @param f function to measure
   * @return result of applying f
   */
  def log[T]
   (l: FiniteDuration => Unit)
   (f: => T)
   (implicit clock: Clock): T = {
    val lap = start()
    val res = f
    l(lap())
    res
  } 
}

object Stopwatch extends Stopwatch {
  def start()(implicit clock: Clock): Elapsed = {
    val since = clock.read
    () => Duration.fromNanos(clock.read - since)
  }
}
