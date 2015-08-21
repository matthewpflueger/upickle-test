package upickletest

import utest._
import autowire._
import upickle.default._

import scala.concurrent.ExecutionContext.Implicits.global

case class BooleanWrapper(bool: Boolean = false)
case class OptionBoolean(optBool: Option[Boolean] = Some(false))

trait BooleanApi {
  def show(bw: BooleanWrapper) = bw.toString
  def optionShow(optBw: OptionBoolean) = optBw.toString
}


object BooleanServer extends autowire.Server[String, Reader, Writer] {
  def write[Result: Writer](r: Result) = upickle.default.write(r)
  def read[Result: Reader](p: String) = upickle.default.read[Result](p)

  def routes = BooleanServer.route[BooleanApi](new BooleanApi {})
}

object BooleanClient extends autowire.Client[String, Reader, Writer] {
  def write[Result: Writer](r: Result) = upickle.default.write(r)
  def read[Result: Reader](p: String) = upickle.default.read[Result](p)

  override def doCall(req: Request) = {
    println(req)
    BooleanServer.routes.apply(req)
  }
}

object BooleanWrapperTest extends TestSuite {
  val tests = TestSuite {
    "invalid input when using default" - {
      BooleanClient[BooleanApi].show(BooleanWrapper()).call().foreach(println)
    }
    "invalid input even when explicitly provided value is same as default" - {
      BooleanClient[BooleanApi].show(BooleanWrapper(false)).call().foreach(println)
    }
    "valid input if provided value is not the same as default" - {
      BooleanClient[BooleanApi].show(BooleanWrapper(true)).call().foreach(println)
    }
    "invalid input when using wrapped default" - {
      BooleanClient[BooleanApi].optionShow(OptionBoolean()).call().foreach(println)
    }
    "invalid input even when explicitly provided wrapped value is same as default" - {
      BooleanClient[BooleanApi].optionShow(OptionBoolean(Some(false))).call().foreach(println)
    }
    "valid input if provided wrapped value is not the same as default" - {
      BooleanClient[BooleanApi].optionShow(OptionBoolean(Some(true))).call().foreach(println)
    }
  }

}
