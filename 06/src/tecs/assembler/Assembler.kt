package tecs.assembler

import java.lang.StringBuilder
import java.util.regex.Pattern

class Assembler(private val asm: String) {
    private val symbolTable = SymbolTable()
    fun assemble(): String {
        firstPass()
        return secondPass()
    }

    private fun firstPass() {
        // update the symbol map with labels
        var lineNumber = 0
        val labelParser = Parser(asm)
        while (labelParser.hasMoreCommands()) {
            labelParser.advance()
            if (labelParser.commandType() == null) {
                continue
            } else if (labelParser.commandType() === Command.L_COMMAND) {
                val symbol = labelParser.symbol
                symbolTable.addEntry(symbol!!, lineNumber)
            } else {
                lineNumber += 1
            }
        }
    }

    private fun secondPass(): String {
        // 2nd pass: turn assembly into machine code
        val output = StringBuilder()
        val codeTable = Code()
        val programParser = Parser(asm)
        var nextRAMAddress = 16
        while (programParser.hasMoreCommands()) {
            programParser.advance()
            if (programParser.commandType() === Command.C_COMMAND) {
                val c = codeTable.comp(programParser.comp!!)
                val d = codeTable.dest(programParser.dest)
                val j = codeTable.jump(programParser.jump)
                output.append("111$c$d$j\n")
            } else if (programParser.commandType() === Command.A_COMMAND) {
                val symbol = programParser.symbol
                var address: Int
                if (Pattern.matches(AsmPattern.NUMBER, symbol)) {
                    address = symbol!!.toInt()
                } else {
                    if (!symbolTable.contains(symbol!!)) {
                        symbolTable.addEntry(symbol, nextRAMAddress)
                        nextRAMAddress += 1
                    }
                    address = symbolTable.getAddress(symbol)
                }
                val binaryAddress = toBinary(address)
                output.append(
                    """
    $binaryAddress
    
    """.trimIndent()
                )
            }
        }
        return output.toString()
    }

    private fun toBinary(address: Int): String {
        return String.format("%16s", Integer.toBinaryString(address)).replace(' ', '0')
    }
}