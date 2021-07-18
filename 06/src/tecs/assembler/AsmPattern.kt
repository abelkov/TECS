package tecs.assembler

object AsmPattern {
    // @100 or @varname
    val AT = """\s*@(\S+).*""".toRegex()

    // 100
    val NUMBER = """^\d+.*""".toRegex()

    // D=A or D=A;JMP or A;JMP (COMP part not optional)
    val COMPUTE = """\s*([DMA]{1,3})?\s*=?\s*([DMA\-+&|!01]{1,3})\s*;?\s*([A-Z]{3})?.*""".toRegex()

    // (LABELNAME)
    val LABEL = """\s*\((\S+)\).*""".toRegex()
}