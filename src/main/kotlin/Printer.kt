import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import models.BattleField
import models.CubeState

private const val size = FIELD_SIZE

fun BattleField.printToWindow() {
    val terminal = DefaultTerminalFactory().setInitialTerminalSize(TerminalSize(120, 70)).createTerminal()
    for ((z, layer) in (1..size * size step size).withIndex()) {
        repeat(size) { y ->
            val line = StringBuilder()
            repeat(size) { x ->
                when (this[x, y, z]) {
                    CubeState.UNKNOWN -> line.appendFormatted(x, y, z, "-")
                    CubeState.SMALL -> line.appendFormatted(x, y, z, "S")
                    CubeState.MEDIUM -> line.appendFormatted(x, y, z, "M")
                    CubeState.LARGE -> line.appendFormatted(x, y, z, "L")
                    CubeState.BOMB -> line.appendFormatted(x, y, z, "B")
                    CubeState.HELP -> line.appendFormatted(x, y, z, "H")
                    CubeState.HIT -> line.appendFormatted(x, y, z, "x")
                }
            }
            terminal.newTextGraphics().putString(0, y + layer, line.toString())
        }
    }
}

private fun StringBuilder.appendFormatted(x: Int, y: Int, z: Int, symbol: String) = this.append(" $symbol ")

fun BattleField.printToConsole() {
    repeat(size) { z ->
        printHeader(z)
        repeat(size) { y ->
            repeat(size) { x ->
                when (this[x, y, z]) {
                    CubeState.UNKNOWN -> printSymbol(x, y, z, "-")
                    CubeState.SMALL -> printSymbol(x, y, z, "S")
                    CubeState.MEDIUM -> printSymbol(x, y, z, "M")
                    CubeState.LARGE -> printSymbol(x, y, z, "L")
                    CubeState.BOMB -> printSymbol(x, y, z, "B")
                    CubeState.HELP -> printSymbol(x, y, z, "H")
                    CubeState.HIT -> printSymbol(x, y, z, "x")
                }
            }
            println()
        }
    }
}

private fun printSymbol(x: Int, y: Int, z: Int, symbol: String) = print(" $symbol ")

private fun printHeader(z: Int) {
    var layer = z
    println()
    println("Слой №${++layer}")
}
