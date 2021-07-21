package tecs.vmtranslator

import java.lang.StringBuilder

// TODO write translated VM code lines as comments
class CodeWriter {
    var output = StringBuilder()
    private var booleanLabelCount = 0
    private val segmentMap = mapOf(
        "local" to "LCL",
        "argument" to "ARG",
        "this" to "THIS",
        "that" to "THAT",
        "temp" to "5"
        // TODO pointer?
        // TODO static?
    )

    fun writeArithmetic(command: String) {
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
        output.append(code)
    }

    private fun writeUnary(op: String) {
        val code = """
            @SP
            A=M-1
            M=${op}M
            """.trimIndent()
        output.append(code)
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
        output.append(code)
        booleanLabelCount += 1
    }

    fun writePushPop(commandType: CommandType, segment: String, index: Int) {
        val segmentAddress: String? =
            if (segmentMap.containsKey(segment)) segmentMap[segment] else null

        // compute segment + index
        // if segment == "constant", use index only
        output.append(
            """
            // compute value address or literal constant
            @$index
            D=A
            """.trimIndent()
        )
        if (segmentAddress != null) {
            output.append(
                """
                @$segmentAddress
                D=D+M
                """.trimIndent()
            )
        }

        if (commandType == CommandType.C_PUSH) {
            if (segmentAddress != null) {
                output.append(
                    """
                        
                    // fetch value to push
                    A=D
                    D=M

                    """.trimIndent()
                )
            }
            output.append(
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
        } else if (commandType == CommandType.C_POP) {
            output.append(
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
}
