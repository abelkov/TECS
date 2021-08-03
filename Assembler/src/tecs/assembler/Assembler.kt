package tecs.assembler

class Assembler(private val asm: String) {
    private val symbolTable = SymbolTable()

    fun assemble(): String {
        declareLabelSymbols()
        return generateHackCode()
    }

    private fun declareLabelSymbols() {
        var lineNumber = 0
        val parser = Parser(asm)
        while (parser.hasMoreCommands()) {
            parser.advance()
            when (parser.instructionType) {
                InstructionType.LABEL -> {
                    symbolTable.addEntry(parser.address, lineNumber)
                }
                InstructionType.ADDRESS, InstructionType.COMPUTE -> {
                    lineNumber += 1
                }
            }
        }
    }

    private fun generateHackCode(): String {
        val result = StringBuilder()
        val parser = Parser(asm)
        var nextRAMAddress = 16
        while (parser.hasMoreCommands()) {
            parser.advance()
            when (parser.instructionType) {
                InstructionType.COMPUTE -> {
                    val compBits = COMP_TABLE[parser.comp]
                    val destBits = DEST_TABLE[parser.dest]
                    val jumpBits = JUMP_TABLE[parser.jump]
                    result.appendLine("111$compBits$destBits$jumpBits")
                }
                InstructionType.ADDRESS -> {
                    val symbolicOrNumericAddress = parser.address
                    var numericAddress: Int
                    if (symbolicOrNumericAddress.matches(AsmPattern.NUMBER)) {
                        numericAddress = symbolicOrNumericAddress.toInt()
                    } else {
                        if (!symbolTable.contains(symbolicOrNumericAddress)) {
                            symbolTable.addEntry(symbolicOrNumericAddress, nextRAMAddress)
                            nextRAMAddress += 1
                        }
                        numericAddress = symbolTable.getAddress(symbolicOrNumericAddress)
                    }
                    result.appendLine(numericAddress.toHackBinaryString())
                }
                else -> {
                    // labels are ignored on the second pass
                }
            }
        }

        return result.toString()
    }

    private fun Int.toHackBinaryString(): String {
        return String.format("%16s", Integer.toBinaryString(this)).replace(' ', '0')
    }
}
