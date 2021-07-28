package tecs.vmtranslator

import java.lang.StringBuilder

class CodeWriter(var fileName: String) {
    var functionName: String = "null"
    var output = StringBuilder()
    private var booleanLabelCount = 0
    private val segmentMap = mapOf(
        "local" to "LCL",
        "argument" to "ARG",
        "this" to "THIS",
        "that" to "THAT",
        "pointer" to "3",
        "temp" to "5"
    )

    fun writeArithmetic(command: String) {
        output.appendLine("///// $command\n")
        when (command) {
            "add" -> writeBinary("+")
            "sub" -> writeBinary("-")
            "neg" -> writeUnary("-")
            "eq" -> writeBoolean("EQ")
            "gt" -> writeBoolean("GT")
            "lt" -> writeBoolean("LT")
            "and" -> writeBinary("&")
            "or" -> writeBinary("|")
            "not" -> writeUnary("!")
        }
    }

    private fun writeBinary(op: String) {
        val command = if (op == "-") "M-D" else "D${op}M"
        val code = """
            @SP
            AM=M-1
            D=M
            A=A-1
            M=$command
            
            """.trimIndent()
        output.appendLine(code)
    }

    private fun writeUnary(op: String) {
        val code = """
            @SP
            A=M-1
            M=${op}M
            """.trimIndent()
        output.appendLine(code)
    }

    private fun writeBoolean(command: String) {
        val trueLabel = "BOOLEANTRUE$booleanLabelCount"
        val endLabel = "BOOLEANEND$booleanLabelCount"
        val code = """
            @SP
            M=M-1
            AM=M-1
            D=M
            A=A+1
            // arg1 - arg2
            D=D-M
            @$trueLabel
            D;J$command
            @SP
            A=M
            // 0 == false
            M=0
            @$endLabel
            0;JMP
            ($trueLabel)
            @SP
            A=M
            // -1 == true
            M=-1
            ($endLabel)
            @SP
            M=M+1
            
            """.trimIndent()
        output.appendLine(code)
        booleanLabelCount += 1
    }

    fun writePushPop(commandType: CommandType, segment: String, index: Int) {
        val segmentAddress: String =
            if (segmentMap.containsKey(segment)) {
                segmentMap[segment]!!
            } else {
                "$fileName.$index"
            }

        output.appendLine("///// ${commandType.toString().lowercase()} $segment $index\n")

        if (segment != "static") {
            output.appendLine(
                """
                // save index
                @$index
                D=A
                
                """.trimIndent()
            )
        }

        if (segment != "constant") {
            output.appendLine("@$segmentAddress")
            when {
                segment.isDirect() -> output.appendLine("D=D+M\n")
                segment.isFixed() -> output.appendLine("D=D+A\n")
                segment == "static" -> output.appendLine("D=A\n")
            }
        }

        if (commandType == CommandType.PUSH) {
            if (segment != "constant") {
                output.appendLine(
                    """
                    // fetch value to push
                    A=D
                    D=M

                    """.trimIndent()
                )
            }
            output.appendLine(
                """
                // push
                @SP
                A=M
                M=D
                
                // increment SP
                @SP
                M=M+1

                """.trimIndent()
            )
        } else if (commandType == CommandType.POP) {
            output.appendLine(
                """
                // save destination address into temporary variable
                @R13
                M=D
                
                // decrement SP
                @SP
                M=M-1
                
                // fetch value to pop
                A=M
                D=M
                
                // store value at destination address
                @R13
                A=M
                M=D
                
                """.trimIndent()
            )
        }
    }

    fun writeLabel(label: String) {
        output.appendLine("///// label $label\n")
        output.appendLine("($fileName.$functionName$$label)\n")
    }

    fun writeIf(label: String) {
        output.appendLine("///// if-goto $label\n")
        output.appendLine(
            """
            // pop topmost value
            @SP
            AM=M-1
            D=M
            
            // jump if not zero
            @$fileName.$functionName$$label
            D;JNE
            
            """.trimIndent()
        )
    }

    fun writeGoto(label: String) {
        output.appendLine("///// goto $label\n")
        output.appendLine(
            """
            // goto
            @$fileName.$functionName${"$"}$label
            0;JMP
            
            """.trimIndent()
        )
    }
}

private fun String.isFixed(): Boolean = this in listOf("pointer", "temp")
private fun String.isDirect(): Boolean = this in listOf("local", "argument", "this", "that")
