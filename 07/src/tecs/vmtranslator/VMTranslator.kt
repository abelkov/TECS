package tecs.vmtranslator

class VMTranslator(private val fileName: String, private val code: String) {
    fun translate(): String {
        val p = Parser(code)
        val c = CodeWriter(fileName)
        while (p.hasMoreCommands()) {
            p.advance()
            when (p.commandType) {
                CommandType.ARITHMETIC -> {
                    c.writeArithmetic(p.arg1)
                }
                CommandType.PUSH -> {
                    c.writePushPop(CommandType.PUSH, p.arg1, p.arg2)
                }
                CommandType.POP -> {
                    c.writePushPop(CommandType.POP, p.arg1, p.arg2)
                }
                else -> {
                    // was comment or empty line
                }
            }
        }

        return c.output.toString()
    }
}
