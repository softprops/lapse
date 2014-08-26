# lapse

Measures the fleeting passage of time.

Much like [guava](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/base/Stopwatch.html) and [twitter.util](https://github.com/twitter/util/blob/master/util-core/src/main/scala/com/twitter/util/Stopwatch.scala) Stopwatches, but without the everything else that comes along with both.

A `Stopwatch` defines interfaces for tracking the elapsed passage of time in terms of [FiniteDurations](http://www.scala-lang.org/api/current/index.html#scala.concurrent.duration.FiniteDuration), a type representing amounts of times and their units. 

A `Stopwatch` requires an implicit `lapse.Clock` to obtain the current time. A `lapse.Clock` defines one method, `read` which should return the current time in nanoseconds according to it's implementation. The default `Clock` reads time as `System.nanoTime()`.

## usage

### start

The `lapse.Stopwatch#start()` interface captures the current time and returns a function that, when applied, returns a `Duration`, relative to that starting point. This is useful if you want to capture lapsed time across multiple operations.

```scala
val elapsed = lapse.Stopwatch.start()
bippy()
println(elapsed())
boop()
println(elapsed())
```

### log

The `lapse.Stopwatch#log(logger)(fn)` interface takes two arguments: a function which takes the lapsed Duration and a function to execute. This is useful if you want to log the lapsed time an operation took as a side effect of an expression.

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

The `lapse.Stopwatch#tupled` interface returns a two element tuple of (result, elapsedDuration)

```scala
val (result, elapsed) = lapse.Stopwatch.tupled(bippy())
```

Doug Tangren (softprops) 2013-2014
