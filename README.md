# lapse

Measures the fleeting passage of time.

Much like [guava](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/base/Stopwatch.html) and [twitter.util](https://github.com/twitter/util/blob/master/util-core/src/main/scala/com/twitter/util/Stopwatch.scala) Stopwatches, but without the everything else that comes along with both.

A `Stopwatch` defines interfaces for tracking the elapsed passage of time in terms of [FiniteDurations](http://www.scala-lang.org/api/current/index.html#scala.concurrent.duration.FiniteDuration), a type representing a quanity of time and its unit of measurement.

A `Stopwatch` requires an implicit `lapse.Clock` to obtain the current time. A `lapse.Clock` defines one method, `read` which should return the current time in nanoseconds according to it's implementation. The default `Clock` reads time as `System.nanoTime()`.

## usage

### start

The `lapse.Stopwatch#start()` interface captures the current time and exports a function that, when applied, returns a `FiniteDuration`, relative to that starting point. This is useful if you want to capture lapsed time across multiple operations.

```scala
val elapsed = lapse.Stopwatch.start()
bippy()
println(elapsed())
boop()
println(elapsed())
```

### log

The `lapse.Stopwatch#log(logger)(fn)` interface takes two arguments: 1) a logging function which the elapsed Duration is handed to after a timing and 2) a function to execute. This is useful if you want to log the lapsed time an operation took as a side effect of an expression.

```scala
val result = Stopwatch.log(println) {
  bippy()
}
```

You can curry `log(...)` in a way that let's you reuse logging. Below is a contrived example
of capturing a histogram of durations.

```scala
import scala.concurrent.duration.FiniteDuration
import scala.collection.mutable.ListBuffer

class Histogram {
  private val population = ListBuffer.empty[FiniteDuration]
  def add(d: FiniteDuration) = population += d
  def apply() = population.groupBy(identity).map {
    case (d, pop) => (d, pop.size)
  }
}

val histo = new Histogram()
val laplog = lapse.Stopwatch.log[Int](histo.add) _

(1 to 100).foreach(i => laplog(bippy(i)))

histo().foreach(println)
```

### tupled

The `lapse.Stopwatch#tupled` interface returns a two element tuple of (result, elapsedDuration). This interface is useful for side effect free
tracking of lapsed time and the value of the expression being timed.

```scala
val (result, elapsed) = lapse.Stopwatch.tupled(bippy())
```

Doug Tangren (softprops) 2013-2014
