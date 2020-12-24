package routing

import akka.actor.{Actor, ActorLogging, Props, Terminated}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.LogEntry
import akka.http.scaladsl.server.{Route, RouteResult}
import routing.HttpRoutingAkka.{Person, UserAdded}
import spray.json._
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._
import java.util.UUID


trait PersonJsonProtocol extends DefaultJsonProtocol {
  implicit val personFormat = jsonFormat2(Person)
  implicit val userAddedFormat = jsonFormat1(UserAdded)
}

object HttpRoutingAkka extends PersonJsonProtocol with SprayJsonSupport {

  class SimpleLoggingActor extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info(message.toString)
    }
  }


    case class Person(name: String, id: Int)

    case class UserAdded(secretCode: String)

   implicit val system = ActorSystem(Behaviors.empty, "AkkaHttpJson")
  val config = ConfigFactory.load().getConfig("mySpecialConfig")

  println(s"  ${config.getString("akka.greet")}")


    val route: Route =
      concat(
        get {
          path("api" / "user" / IntNumber) { id =>

            extractLog{
              log=>{
            val startTime: Long = System.currentTimeMillis()
            log.info(s"start time $startTime")

            extractUri { uri =>
              validate(uri.path.toString.size < 15, s"incorrect path:${uri.path.toString}") {

                val endTime: Long = System.currentTimeMillis()

                log.info(s"end time $endTime")

                val time = endTime - startTime
                log.info(s"time taken $time")
                complete {
                  (
                    "Welcome to Akka ",
                    s"This is a get request from $id ",
                  )
                }
              }
            }
          }
            }
          }
          },
        post {
          path("api" / "user") {
            entity(as[Person]) { person =>
              extractLog {
                log => {
                  val startTime: Long = System.currentTimeMillis()
                  log.info(s"start time $startTime")

                  validate(person.id.toString.size > 0 && person.id.toString.size < 5, "Id entered is wrong") {
                    val endTime: Long = System.currentTimeMillis()
                    log.info(s"end time $endTime")

                    val time = endTime - startTime
                    log.info(s"time taken $time")
                    complete {
                      (
                        UserAdded(UUID.randomUUID().toString)
                        )
                    }


                  }

                }
              }
            }
          }
        }
      )

    def main(args: Array[String]): Unit = {
      Http().newServerAt("localhost", 8081).bind(route)

    }


  }
