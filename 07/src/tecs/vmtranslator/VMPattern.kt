package tecs.vmtranslator

object VMPattern {
    // push local 0
    val PUSH = """\s*push\s+(\w+)\s+(\d+)\W*""".toRegex()

    // pop constant 10
    val POP = """\s*pop\s+(\S+)\s+(\d+)\W*""".toRegex()

    // add
    val ARITHMETIC = """\s*(add|sub|neg|eq|gt|lt|and|or|not)\W*""".toRegex()

    // label foo
    val LABEL = """\s*label\s+(\w+)\W*""".toRegex()

    // goto foo
    val GOTO = """\s*goto\s+(\w+)\W*""".toRegex()

    // if-goto foo
    val IF = """\s*if-goto\s+(\w+)\W*""".toRegex()

    // TODO
    val FUNCTION = """\s*function\s+(\w+)\s+(\d+)\W*""".toRegex()

    // TODO
    val CALL = """\s*call\s+(\w+)\s+(\d+)\W*""".toRegex()

    // return
    val RETURN = """\s*return\W*""".toRegex()
}
