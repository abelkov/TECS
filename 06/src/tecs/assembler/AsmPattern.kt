package tecs.assembler

object AsmPattern {
    // @100 or @varname
    const val AT = "\\s*@(\\S+).*"

    // 100
    const val NUMBER = "^\\d+.*"

    // D=A or D=A;JMP or A;JMP (COMP part not optional)
    const val COMP = "\\s*([DMA]{1,3})?\\s*=?\\s*([DMA\\-+&\\|!01]{1,3})\\s*;?\\s*([A-Z]{3})?.*"

    // (LABELNAME)
    const val LABEL = "\\s*\\((\\S+)\\).*"
}