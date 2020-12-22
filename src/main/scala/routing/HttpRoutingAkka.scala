package routing

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import routing.HttpRoutingAkka.{Person, UserAdded}
import spray.json._

import java.util.UUID

trait PersonJsonProtocol extends DefaultJsonProtocol {
  /*
    The `jsonFormat2` method is applicable for Product types (which include case classes) with 2 members.
    If you have case classes with more than 2 fields, you'd use the appropriate `jsonFormatX` method.
  */
  implicit val personFormat = jsonFormat2(Person)
  implicit val userAddedFormat = jsonFormat1(UserAdded)
}


object HttpRoutingAkka extends PersonJsonProtocol with SprayJsonSupport {
  case class Person(name: String, id: Int)
  case class UserAdded(secretCode: String)

  implicit val system = ActorSystem(Behaviors.empty, "AkkaHttpJson")

  val route: Route =
    concat(
      get {
        path("api" / "user" / LongNumber) {id=>
          complete("This is a get request " + id)
        }
      },
      post {
        path("api" / "user") {
          entity(as[Person]) { person =>
            complete(UserAdded(UUID.randomUUID().toString))
          }
        }
      }
    )


  def main(args: Array[String]): Unit = {
    Http().newServerAt("localhost", 8081).bind(route)
  }
}

