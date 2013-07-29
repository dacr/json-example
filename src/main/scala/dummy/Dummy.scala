/*
 * Copyright 2013 David Crosson
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dummy

import java.util.Date

trait Currency {
  val symbol:String
  val name:String
}

// unfortunately Objects is not supported by json4s (or not yet)

case class Dollar() extends Currency {
  val symbol="$"
  val name="dollar"
}

case class Euro() extends Currency {
  val symbol="�"
  val name="euro"
}

trait Place

case class Address(
    address:String,
    town:String
    ) extends Place

case class GpsCoords(
    latitude:Double,
    north:Boolean,
    longitude:Double,
    east:Boolean
    ) extends Place

case class Receipt(
    amount:Double,
    currency:Currency,
    when:Date,
    where:Place,
    what:String,
    keywords:Set[String]
    )

case class ReceiptsArchive(
    receipts:List[Receipt],
    description:Option[String]=None
    )

object Dummy {
  import org.json4s._
  import org.json4s.native.Serialization
  import org.json4s.native.Serialization.{ read, write, writePretty }

  def today()=new Date()

  def main(args:Array[String]) {
    //implicit val formats = Serialization.formats(NoTypeHints)
    implicit val formats = Serialization.formats(
        ShortTypeHints(
            List(
                classOf[Euro],
                classOf[Dollar],
                classOf[Address],
                classOf[GpsCoords]
                )
            )
        )

    val receipts = ReceiptsArchive(
        Receipt(15, Euro(), today(), Address("XIII","Paris"), "meal", Set("food", "4work"))::
        Receipt(1, Euro(), today(), Address("I","Paris"), "bread", Set("food"))::Nil,
        Some("2013 archives")
        )
    
    val json = write(receipts)
    
    println(writePretty(receipts))
    
    val decoded = read[ReceiptsArchive](json)
    
    println(decoded)
    
    assert(json == write(decoded))
  }
}
