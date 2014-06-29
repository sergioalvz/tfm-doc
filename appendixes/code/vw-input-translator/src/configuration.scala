import java.io.File

case class Configuration(inout:Boolean = false, decimals:Int = -1, file:File = null) {}

object CLIParser {
  private[this] val parser = new scopt.OptionParser[Configuration]("vw-input-translator") {
    head("vw-input-translator", "1.0")
    opt[Unit]("inout") action { (x, c) => c.copy(inout = true) } text("Flag for creating an input file for binary classification")
    opt[Int]('d', "decimals") action { (x, c) => c.copy(decimals = x) } validate { x =>
      if (x > 0) success else failure("Option --decimals must be > 0") } text("Creates an input file for multi-class classification." +
        " Each sample will have the selected number of decimals on latitude and longitude coordinates")
    arg[File]("<file>") unbounded() action { (x, c) => c.copy(file = x) } text("Source file")
    checkConfig { c => if (c.inout && c.decimals > -1) failure("You cannot use --inout and --decimals at the same time") else success }
    checkConfig { c => if (!c.inout && c.decimals == -1) failure("You have to provide the --inout OR --decimals parameter") else success }
  }

  def parse(args:Array[String], conf:Configuration):Option[Configuration] = this.parser.parse(args, conf)
}
