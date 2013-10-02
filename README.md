# lapse

Measures the fleeting passage time.

Much like [guava](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/base/Stopwatch.html) and [twitter.util](https://github.com/twitter/util/blob/master/util-core/src/main/scala/com/twitter/util/Stopwatch.scala) Stopwatches without the baggage that comes along with both.

A `Stopwatch` provides a flexible means of tracking the elapsed period of time from some starting point. `scala.concurrent.duration.Duration` does a good job at representing that time but lacks a convenient interface that supports the usecase of tracking time. This library provides a single `lapse.Stopwatch.start()` function which captures the begining of a duration expsoing a function that, when applied, returns a `scala.concurrent.duration.Duration` relative to that starting point. 

This is a more flexible interface than a method that takes a block as an arguement, meastures the time it takes to execute that block, logs the time as a side effect, and returns the value of that block. A Stopwatch's exported function is able to capture a context of a precise starting point which can be passed around as an argument to other functions. The application of the function returning `scala.concurrent.duration.Duration` is more flexible than returning a numerif type without a context of the numeric values unit information.


Doug Tangren (softprops) 2013
