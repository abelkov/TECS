package tecs.vmtranslator

import tecs.vmtranslator.CommandType.*

class VMTranslator(private val fileName: String, private val code: String) {
    fun translate(): String {
        val p = Parser(code)
        val c = CodeWriter(fileName)
        while (p.hasMoreCommands()) {
            p.advance()
            when (p.commandType) {
                ARITHMETIC -> c.writeArithmetic(p.arg1)
                PUSH -> c.writePushPop(PUSH, p.arg1, p.arg2)
                POP -> c.writePushPop(POP, p.arg1, p.arg2)
                LABEL -> c.writeLabel(p.arg1)
                IF -> c.writeIf(p.arg1)
                GOTO -> c.writeGoto(p.arg1)
                else -> {
                    // was comment or empty line, ignore
                }
            }
        }

        return c.output.toString()
    }
}
