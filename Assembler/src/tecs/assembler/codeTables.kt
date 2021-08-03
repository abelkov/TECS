package tecs.assembler

val COMP_TABLE = mapOf(
    "0" to "0101010",
    "1" to "0111111",
    "-1" to "0111010",
    "D" to "0001100",
    "A" to "0110000",
    "!D" to "0001101",
    "!A" to "0110001",
    "-D" to "0001111",
    "-A" to "0110011",
    "D+1" to "0011111",
    "A+1" to "0110111",
    "D-1" to "0001110",
    "A-1" to "0110010",
    "D+A" to "0000010",
    "D-A" to "0010011",
    "A-D" to "0000111",
    "D&A" to "0000000",
    "D|A" to "0010101",
    "M" to "1110000",
    "!M" to "1110001",
    "-M" to "1110011",
    "M+1" to "1110111",
    "M-1" to "1110010",
    "D+M" to "1000010",
    "D-M" to "1010011",
    "M-D" to "1000111",
    "D&M" to "1000000",
    "D|M" to "1010101"
)

val DEST_TABLE = mapOf(
    "" to "000",
    "M" to "001",
    "D" to "010",
    "MD" to "011",
    "A" to "100",
    "AM" to "101",
    "AD" to "110",
    "AMD" to "111"
)

val JUMP_TABLE = mapOf(
    "" to "000",
    "JGT" to "001",
    "JEQ" to "010",
    "JGE" to "011",
    "JLT" to "100",
    "JNE" to "101",
    "JLE" to "110",
    "JMP" to "111"
)
