package lapse

import scala.concurrent.duration.Duration

/** Exposes a means of creating a start point for a duration */
trait Stopwatch {
  type Elapsed = () => Duration
  /** @return a function that, when applied,
   * returns a duration from the start point
   */
  def start()(implicit clock: Clock): Elapsed
}

object Stopwatch extends Stopwatch {
  def start()(implicit clock: Clock): Elapsed = {
    val off = clock.read
    () => Duration.fromNanos(clock.read - off)
  }
}
