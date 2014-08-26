package lapse;

import scala.Function0;
import scala.concurrent.duration.FiniteDuration;

public class JavaTest {
  public static void main(String[] args) {
    Function0<FiniteDuration> elapsed = Stopwatch.start(Clock.system());
    System.out.println(elapsed.apply());
  }
}
