# lapse

Measures the fleeting passage of time.

Much like [guava](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/base/Stopwatch.html) and [twitter.util](https://github.com/twitter/util/blob/master/util-core/src/main/scala/com/twitter/util/Stopwatch.scala) Stopwatches but without the baggage that comes along with both.

A `Stopwatch` provides a flexible means of tracking the elapsed period of time from some starting point represented as `scala.concurrent.duration.Durations` which do a good job of representing both a value and unit of time as a unified type. 

A `Stopwatch` requires an implicit `lapse.Clock` to obtain the current time. A `lapse.Clock` defines one method, `read` which should return the current time in nanoseconds according to it's implementation. The default is a `lapse.Clock.SystemClock` which reads time as `System.nanoTime()`

The `lapse.Stopwatch.start()` method captures the current time and returns a function that, when applied, returns a `scala.concurrent.duration.Duration` relative to that starting point. This is useful with you want to capture lapsed time across multiple operations.

```scala
val elapsed = lapse.StopWatch.start()
bippy()
println(elapsed())
boop()
println(elapsed())
```

The `lapse.Stopwatch.log` method takes two arguments. A log function which takes the lapsed Duration and a function to execute. This is useful with you want to log the lapsed time an operation took as a side effect of an expression.

```scala
val result = Stopwatch.log(println) {
  bippy()
}
```

If you can also curry `log` in a way that let's you reuse logging. Below is a contrived example
of capturing a histogram of durations.

```scala
import scala.concurrent.duration.Duration
import scala.collection.mutable.ListBuffer

class Histogram {
  private val population = ListBuffer.empty[Duration]
  def add(d: Duration) = population += d
  def apply() = population.groupBy(identity).map {
    case (d, pop) => (d, pop.size)
  }
}

val histo = new Histogram()
val laplog = lapse.Stopwatch.log[Int](histo.add)_

(1 to 100).foreach(i => laplog(bippy(i)))

histo().foreach(println)
```


Doug Tangren (softprops) 2013-2014
