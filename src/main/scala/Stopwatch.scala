package lapse

import scala.concurrent.duration.Duration

trait Stopwatch {
  type Elapsed = () => Duration
  def start(): Elapsed
}

object Stopwatch extends Stopwatch {
  def start(): Elapsed = {
    val off = System.nanoTime()
    () => Duration.fromNanos(System.nanoTime() - off)
  }
}
