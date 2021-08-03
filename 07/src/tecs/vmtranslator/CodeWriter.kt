package tecs.vmtranslator

import java.lang.StringBuilder

class CodeWriter(generateInit: Boolean) {
    val output = StringBuilder()
    var fileName: String = ""
        set(value) {
            field = value
            output.appendLine("////////// File $value\n")
        }
    private var functionName: String = "null"
    private var returnAddressIndex = 0
    private var booleanLabelIndex = 0
    private val segmentMap = mapOf(
        "local" to "LCL",
        "argument" to "ARG",
        "this" to "THIS",
        "that" to "THAT",
        "pointer" to "3",
        "temp" to "5"
    )

    init {
        if (generateInit) {
            output.appendLine(
                """
                // Start code gen: init SP to 256 and call Sys.init
                @256
                D=A
                @SP
                M=D
    
                @Sys.init
                0;JMP
    
                """.trimIndent()
            )
        }
    }

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
        // TODO is the `if` really necessary?
        val command = if (op == "-") "M-D" else "D${op}M"
        output.appendLine(
            """
            @SP
            AM=M-1
            D=M
            A=A-1
            M=$command
            
            """.trimIndent()
        )
    }

    private fun writeUnary(op: String) {
        output.appendLine(
            """
            @SP
            A=M-1
            M=${op}M
            
            """.trimIndent()
        )
    }

    private fun writeBoolean(command: String) {
        // TODO prepend file name to label?
        val trueLabel = "BOOLEANTRUE$booleanLabelIndex"
        val endLabel = "BOOLEANEND$booleanLabelIndex"
        booleanLabelIndex += 1
        output.appendLine(
            """
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
        )
    }

    fun writePushPop(commandType: CommandType, segment: String, index: Int) {
        val segmentAddress: String =
            if (segmentMap.containsKey(segment)) {
                segmentMap[segment]!!
            } else {
                "$fileName.$index" // for statics
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
        output.appendLine("($functionName$$label)\n")
    }

    fun writeIf(label: String) {
        output.appendLine(
            """
            ///// if-goto $label

            // pop topmost value
            @SP
            AM=M-1
            D=M
            
            // jump if not zero
            @$functionName$$label
            D;JNE
            
            """.trimIndent()
        )
    }

    fun writeGoto(label: String) {
        output.appendLine(
            """
            ///// goto $label
            @$functionName$$label
            0;JMP
            
            """.trimIndent()
        )
    }

    fun writeFunction(functionName: String, nArgs: Int) {
        this.functionName = functionName
        returnAddressIndex = 0 // reset index on entering a new function
        output.appendLine("///// function $functionName $nArgs\n")
        output.appendLine("($functionName)\n")
        repeat(nArgs) {
            output.appendLine(
                """
                // push 0
                @SP
                A=M
                M=0
                @SP
                M=M+1

                """.trimIndent()
            )
        }
    }

    fun writeCall(fqnFunctionName: String, nArgs: Int) {
        val returnAddress = "$functionName\$ret.$returnAddressIndex"
        returnAddressIndex++
        output.appendLine(
            """
            ///// call $fqnFunctionName $nArgs
                
            // push <returnAddress>
            @$returnAddress
            D=A
            @SP
            A=M
            M=D
            @SP
            M=M+1
            
            // push LCL
            @LCL
            D=M
            @SP
            A=M
            M=D
            @SP
            M=M+1
            
            // push ARG
            @ARG
            D=M
            @SP
            A=M
            M=D
            @SP
            M=M+1
            
            // push THIS
            @THIS
            D=M
            @SP
            A=M
            M=D
            @SP
            M=M+1
            
            // push THAT
            @THAT
            D=M
            @SP
            A=M
            M=D
            @SP
            M=M+1
            
            // ARG = SP-5-nArgs
            @SP
            D=M
            @${5 + nArgs}
            D=D-A
            @ARG
            M=D
            
            // LCL = SP
            @SP
            D=M
            @LCL
            M=D
            
            // goto <f>
            @$fqnFunctionName
            0;JMP
            
            // (<returnAddress>)
            ($returnAddress)

            """.trimIndent()
        )
    }

    fun writeReturn() {
        output.appendLine(
            """
            ///// return
            
            // frame = LCL
            @LCL
            D=M
            @R13
            M=D
            
            // retAddr = *(frame-5)
            @5
            D=D-A
            A=D
            D=M
            @R14
            M=D
            
            // *ARG = pop()
            @SP
            M=M-1
            D=M
            A=D
            D=M
            @ARG
            A=M
            M=D
            
            // SP = ARG+1
            @ARG
            D=M+1
            @SP
            M=D
            
            // THAT = *(frame-1)
            @R13
            D=M
            A=D-1
            D=M
            @THAT
            M=D
            
            // THIS = *(frame-2)
            @R13
            D=M
            @2
            A=D-A
            D=M
            @THIS
            M=D
                        
            // ARG = *(frame-3)
            @R13
            D=M
            @3
            A=D-A
            D=M
            @ARG
            M=D
            
            // LCL = *(frame-4)
            @R13
            D=M
            @4
            A=D-A
            D=M
            @LCL
            M=D
            
            // goto retAddr
            @R14
            D=M
            A=D
            0;JMP

            """.trimIndent()
        )
    }
}

private fun String.isFixed(): Boolean = this in listOf("pointer", "temp")
private fun String.isDirect(): Boolean = this in listOf("local", "argument", "this", "that")
