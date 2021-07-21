package tecs.vmtranslator

import java.util.*

class Parser(code: String) {
    private val input = Scanner(code)

    var arg1: String = ""
        private set
    var arg2: Int = 0
        private set
    var commandType: CommandType? = null
        private set

    fun hasMoreCommands(): Boolean {
        if (input.hasNextLine()) {
            return true
        }
        input.close()
        return false
    }

    fun advance() {
        val currentLine = input.nextLine()
        when {
            currentLine.matches(VMPattern.ARITHMETIC) -> {
                this.commandType = CommandType.C_ARITHMETIC
                val matchResult = VMPattern.ARITHMETIC.find(currentLine)!!
                arg1 = matchResult.groupValues[1]
            }
            currentLine.matches(VMPattern.PUSH) -> {
                this.commandType = CommandType.C_PUSH
                val matchResult = VMPattern.PUSH.find(currentLine)!!
                arg1 = matchResult.groupValues[1]
                arg2 = matchResult.groupValues[2].toInt()
            }
            currentLine.matches(VMPattern.POP) -> {
                this.commandType = CommandType.C_POP
                val matchResult = VMPattern.POP.find(currentLine)!!
                arg1 = matchResult.groupValues[1]
                arg2 = matchResult.groupValues[2].toInt()
            }
            currentLine.matches(VMPattern.LABEL) -> {
                this.commandType = CommandType.C_LABEL
                val matchResult = VMPattern.LABEL.find(currentLine)!!
                arg1 = matchResult.groupValues[1]
            }
            currentLine.matches(VMPattern.GOTO) -> {
                this.commandType = CommandType.C_GOTO
                val matchResult = VMPattern.GOTO.find(currentLine)!!
                arg1 = matchResult.groupValues[1]
            }
            currentLine.matches(VMPattern.IF) -> {
                this.commandType = CommandType.C_IF
                val matchResult = VMPattern.IF.find(currentLine)!!
                arg1 = matchResult.groupValues[1]
            }
            currentLine.matches(VMPattern.FUNCTION) -> {
                this.commandType = CommandType.C_FUNCTION
                val matchResult = VMPattern.FUNCTION.find(currentLine)!!
                arg1 = matchResult.groupValues[1]
                arg2 = matchResult.groupValues[2].toInt()
            }
            currentLine.matches(VMPattern.CALL) -> {
                this.commandType = CommandType.C_CALL
                val matchResult = VMPattern.CALL.find(currentLine)!!
                arg1 = matchResult.groupValues[1]
                arg2 = matchResult.groupValues[2].toInt()
            }
            currentLine.matches(VMPattern.RETURN) -> {
                this.commandType = CommandType.C_RETURN
            }
            else -> {
                this.commandType = null
            }
        }
    }
}
