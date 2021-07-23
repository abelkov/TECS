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
        val line = input.nextLine()
        when {
            line.matches(VMPattern.ARITHMETIC) -> {
                this.commandType = CommandType.ARITHMETIC
                val matchResult = VMPattern.ARITHMETIC.find(line)!!
                arg1 = matchResult.groupValues[1]
            }
            line.matches(VMPattern.PUSH) -> {
                this.commandType = CommandType.PUSH
                val matchResult = VMPattern.PUSH.find(line)!!
                arg1 = matchResult.groupValues[1]
                arg2 = matchResult.groupValues[2].toInt()
            }
            line.matches(VMPattern.POP) -> {
                this.commandType = CommandType.POP
                val matchResult = VMPattern.POP.find(line)!!
                arg1 = matchResult.groupValues[1]
                arg2 = matchResult.groupValues[2].toInt()
            }
            line.matches(VMPattern.LABEL) -> {
                this.commandType = CommandType.LABEL
                val matchResult = VMPattern.LABEL.find(line)!!
                arg1 = matchResult.groupValues[1]
            }
            line.matches(VMPattern.GOTO) -> {
                this.commandType = CommandType.GOTO
                val matchResult = VMPattern.GOTO.find(line)!!
                arg1 = matchResult.groupValues[1]
            }
            line.matches(VMPattern.IF) -> {
                this.commandType = CommandType.IF
                val matchResult = VMPattern.IF.find(line)!!
                arg1 = matchResult.groupValues[1]
            }
            line.matches(VMPattern.FUNCTION) -> {
                this.commandType = CommandType.FUNCTION
                val matchResult = VMPattern.FUNCTION.find(line)!!
                arg1 = matchResult.groupValues[1]
                arg2 = matchResult.groupValues[2].toInt()
            }
            line.matches(VMPattern.CALL) -> {
                this.commandType = CommandType.CALL
                val matchResult = VMPattern.CALL.find(line)!!
                arg1 = matchResult.groupValues[1]
                arg2 = matchResult.groupValues[2].toInt()
            }
            line.matches(VMPattern.RETURN) -> {
                this.commandType = CommandType.RETURN
            }
            else -> {
                this.commandType = null
            }
        }
    }
}
