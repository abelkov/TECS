package tecs.assembler

import java.util.*
import java.util.regex.Pattern

internal class Parser(asm: String?) {
    private val input: Scanner
    private var currentLine: String? = null
    private var currentCommand: Command? = null
    var dest: String? = null
        private set
    var comp: String? = null
        private set
    var jump: String? = null
        private set
    var symbol: String? = null
        private set

    fun commandType(): Command? {
        return currentCommand
    }

    fun hasMoreCommands(): Boolean {
        if (input.hasNextLine()) {
            return true
        }
        input.close()
        return false
    }

    fun advance() {
        currentLine = input.nextLine()
        if (Pattern.matches(AsmPattern.AT, currentLine)) {
            currentCommand = Command.A_COMMAND
            parseAt()
        } else if (Pattern.matches(AsmPattern.COMP, currentLine)) {
            currentCommand = Command.C_COMMAND
            parseComp()
        } else if (Pattern.matches(AsmPattern.LABEL, currentLine)) {
            currentCommand = Command.L_COMMAND
            parseLabel()
        } else {
            currentCommand = null
        }
    }

    private fun parseAt() {
        val m = Pattern.compile(AsmPattern.AT).matcher(currentLine)
        m.matches()
        symbol = m.group(1)
    }

    private fun parseComp() {
        val m = Pattern.compile(AsmPattern.COMP).matcher(currentLine)
        m.matches()
        dest = m.group(1)
        comp = m.group(2)
        jump = m.group(3)
    }

    private fun parseLabel() {
        val m = Pattern.compile(AsmPattern.LABEL).matcher(currentLine)
        m.matches()
        symbol = m.group(1)
    }

    init {
        input = Scanner(asm)
    }
}