package lapse

import scala.concurrent.duration.{ Duration, FiniteDuration }

/** Tracks the passage of time */
trait Stopwatched {
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
      val (res, lap) = tupled(f)
      l(lap)
      res
    }

  /**
   * @param f function to measure
   * @return two element tuple of (result, duration)
   */
  def tupled[T]
    (f: => T)
    (implicit clock: Clock): (T, FiniteDuration) = {
      val lap = start()
      (f, lap())
    }
}

object Stopwatch extends Stopwatched {
  def start()(implicit clock: Clock): Elapsed = {
    val since = clock.read
    () => Duration.fromNanos(clock.read - since)
  }
}
