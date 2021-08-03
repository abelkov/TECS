package tecs.vmtranslator

object VMPattern {
    // push local 0
    val PUSH = """\s*push\s+(\S+)\s+(\d+)\s*(//.*)?""".toRegex()

    // pop constant 10
    val POP = """\s*pop\s+(\S+)\s+(\d+)\s*(//.*)?""".toRegex()

    // add
    val ARITHMETIC = """\s*(add|sub|neg|eq|gt|lt|and|or|not)\s*(//.*)?""".toRegex()

    // label foo
    val LABEL = """\s*label\s+(\S+)\s*(//.*)?""".toRegex()

    // goto foo
    val GOTO = """\s*goto\s+(\S+)\s*(//.*)?""".toRegex()

    // if-goto foo
    val IF = """\s*if-goto\s+(\S+)\s*(//.*)?""".toRegex()

    // function functionName nLocals
    val FUNCTION = """\s*function\s+(\S+)\s+(\d+)\s*(//.*)?""".toRegex()

    // call functionName nArgs
    val CALL = """\s*call\s+(\S+)\s+(\d+)\s*(//.*)?""".toRegex()

    // return
    val RETURN = """\s*return\s*(//.*)?""".toRegex()
}
