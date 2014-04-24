package lapse

import scala.concurrent.duration.Duration

/** Tracks the passage of time */
trait Stopwatch {
  type Elapsed = () => Duration
  /** @return a function that, when applied, returns a duration from the start point */
  def start()(implicit clock: Clock): Elapsed

  /**
   * @param l log function
   * @param f function to measure
   * @return result of applying f
   */
  def log[T](
    l: Duration => Unit)(f: => T)(implicit clock: Clock): T = {
    val lap = start()
    val res = f
    l(lap())
    res
  } 
}

object Stopwatch extends Stopwatch {
  def start()(implicit clock: Clock): Elapsed = {
    val off = clock.read
    () => Duration.fromNanos(clock.read - off)
  }
}
