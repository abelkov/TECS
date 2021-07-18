package tecs.assembler

import java.util.HashMap

internal class Code {
    private val destTable = HashMap<String?, String>()
    private val jumpTable = HashMap<String?, String>()
    private val compTable = HashMap<String, String>()
    fun dest(mnemonic: String?): String? {
        return destTable[mnemonic]
    }

    fun jump(mnemonic: String?): String? {
        return jumpTable[mnemonic]
    }

    fun comp(mnemonic: String): String? {
        return compTable[mnemonic]
    }

    init {
        val destMnemonic = arrayOf(null, "M", "D", "MD", "A", "AM", "AD", "AMD")
        val destBinary = arrayOf("000", "001", "010", "011", "100", "101", "110", "111")
        for (i in destMnemonic.indices) {
            destTable[destMnemonic[i]] = destBinary[i]
        }
        val jumpMnemonic = arrayOf(null, "JGT", "JEQ", "JGE", "JLT", "JNE", "JLE", "JMP")
        val jumpBinary = arrayOf("000", "001", "010", "011", "100", "101", "110", "111")
        for (i in jumpMnemonic.indices) {
            jumpTable[jumpMnemonic[i]] = jumpBinary[i]
        }
        val compMnemonic = arrayOf(
            "0", "1", "-1", "D", "A", "!D", "!A", "-D", "-A",
            "D+1", "A+1", "D-1", "A-1", "D+A", "D-A", "A-D", "D&A", "D|A",
            "M", "!M", "-M", "M+1", "M-1", "D+M", "D-M", "M-D", "D&M", "D|M"
        )
        val compBinary = arrayOf(
            "0101010", "0111111", "0111010", "0001100", "0110000", "0001101", "0110001", "0001111",
            "0110011", "0011111", "0110111", "0001110", "0110010", "0000010", "0010011", "0000111",
            "0000000", "0010101", "1110000", "1110001", "1110011", "1110111", "1110010", "1000010",
            "1010011", "1000111", "1000000", "1010101"
        )
        for (i in compMnemonic.indices) {
            compTable[compMnemonic[i]] = compBinary[i]
        }
    }
}