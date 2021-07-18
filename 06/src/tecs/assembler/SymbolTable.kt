package tecs.assembler

import java.util.HashMap

internal class SymbolTable {
    private val table = HashMap<String, Int>()
    fun addEntry(symbol: String, address: Int) {
        table[symbol] = address
    }

    operator fun contains(symbol: String): Boolean {
        return table.containsKey(symbol)
    }

    fun getAddress(symbol: String): Int {
        return table[symbol]!!
    }

    init {
        val symbols = arrayOf(
            "SP", "LCL", "ARG", "THIS", "THAT",
            "R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10",
            "R11", "R12", "R13", "R14", "R15", "SCREEN", "KBD"
        )
        val addresses = intArrayOf(
            0, 1, 2, 3, 4,
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16384, 24576
        )
        for (i in symbols.indices) {
            table[symbols[i]] = addresses[i]
        }
    }
}