import java.io._
import scala.io.Source
import scala.collection.mutable.ListBuffer
import java.security.MessageDigest

object Main {
  def main(args: Array[String]): Unit = {
    CLIParser.parse(args, Configuration()) map { config =>
      val translator = new VWTranslator(config)
      Source.fromFile(config.file).getLines.foreach(line => translator.translate(line))
      translator.saveDictionary

      println("Number of classes extracted: " + translator.numberOfClasses)
    }
  }
}

object Writer {
  def write(line: String, file: String):Unit = {
    val out = new File(file)
    if(!out.exists) out.createNewFile

    val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out, true)))
    try {
      writer.write(line)
    } finally {
      writer.flush()
      writer.close()
    }
  }
}

class VWTranslator(config: Configuration) {
  private[this] val dictionary = new ListBuffer[(String, Int)]()

  def translate(raw: String): Unit = {
    val columns       = raw.split("\t")
    val classLabel    = getClass(columns)
    val formattedLine = s"$classLabel |Tweet ${columns(3)}\n"

    Writer.write(formattedLine, outputFileName)
  }

  def numberOfClasses: Int = dictionary.size

  def saveDictionary: Unit = {
    val fileName = s"${outputFileName}_dictionary"
    dictionary.foreach { case (k, c) => Writer.write(s"$c\t$k\n", fileName) }
  }

  private[this] def outputFileName: String = {
    var file = config.file
    if(this.config.inout) s"${file}_vw_inout" else s"${file}_vw_${config.decimals}_decimals"
  }

  private[this] def getClass(columns: Array[String]): Int =
    if(config.inout) getBinaryClass(columns(0).toDouble) else getMultiClassLabel(columns(1), columns(2))

  private[this] def getBinaryClass(score: Double): Int = if(score < 0.0) 0 else 1

  private[this] def getMultiClassLabel(lat:String, lng:String): Int = {
    val trunkedLat = trunkCoordinate(lat)
    val trunkedLng = trunkCoordinate(lng)

    val key = trunkedLat + "," + trunkedLng

    if(dictionary.isEmpty) {
      dictionary += key -> 1
      1
    } else{
      val storedClassLabel = getClassLabelInDictionary(key)
      if(storedClassLabel.isEmpty) {
        val last = dictionary.last._2
        dictionary += key -> (last + 1)
        last + 1
      }else {
        storedClassLabel.get._2
      }
    }
  }

  private[this] def getClassLabelInDictionary(key: String): Option[(String, Int)] = dictionary.find(t => t._1 == key)

  private[this] def trunkCoordinate(coordinate: String): String = {
    val parts = coordinate.split("\\.")
    parts(0) + "." + parts(1).slice(0, config.decimals)
  }
}
