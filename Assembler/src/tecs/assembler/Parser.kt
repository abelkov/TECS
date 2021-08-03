package tecs.assembler

import java.util.*

class Parser(asm: String) {
    private val input = Scanner(asm)

    var instructionType: InstructionType? = null
        private set
    var dest: String = ""
        private set
    var comp: String = ""
        private set
    var jump: String = ""
        private set
    var address: String = ""
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
            currentLine.matches(AsmPattern.AT) -> {
                instructionType = InstructionType.ADDRESS
                val matchResult = AsmPattern.AT.find(currentLine)!!
                address = matchResult.groupValues[1]
            }
            currentLine.matches(AsmPattern.COMPUTE) -> {
                instructionType = InstructionType.COMPUTE
                val matchResult = AsmPattern.COMPUTE.find(currentLine)!!
                dest = matchResult.groupValues[1]
                comp = matchResult.groupValues[2]
                jump = matchResult.groupValues[3]
            }
            currentLine.matches(AsmPattern.LABEL) -> {
                instructionType = InstructionType.LABEL
                val matchResult = AsmPattern.LABEL.find(currentLine)!!
                address = matchResult.groupValues[1]
            }
            else -> {
                instructionType = null
            }
        }
    }
}
