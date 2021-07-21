package tecs.vmtranslator

class VMTranslator(private val code: String) {
    fun translate(): String {
        val p = Parser(code)
        val c = CodeWriter()
        while (p.hasMoreCommands()) {
            p.advance()
            when (p.commandType) {
                CommandType.C_ARITHMETIC -> {
                    c.writeArithmetic(p.arg1)
                }
                CommandType.C_PUSH -> {
                    c.writePushPop(CommandType.C_PUSH, p.arg1, p.arg2)
                }
                CommandType.C_POP -> {
                    c.writePushPop(CommandType.C_POP, p.arg1, p.arg2)
                }
                else -> {
                    TODO()
                }
            }
        }

        return c.output.toString()
    }
}
