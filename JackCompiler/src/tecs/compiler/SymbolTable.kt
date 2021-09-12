package tecs.compiler

import tecs.compiler.VariableKind.*

private data class VariableInfo(val name: String, val type: String, val kind: VariableKind, val index: Int)

class SymbolTable {
    private val symbolMap = mutableMapOf<String, VariableInfo>()
    private val indexMap = mutableMapOf(
        STATIC to 0,
        FIELD to 0,
        ARG to 0,
        VAR to 0,
    )

    fun reset() {
        symbolMap.clear()
        indexMap[STATIC] = 0
        indexMap[FIELD] = 0
        indexMap[ARG] = 0
        indexMap[VAR] = 0
    }

    fun contains(name: String): Boolean =
        symbolMap.contains(name)

    fun define(name: String, type: String, kind: VariableKind) {
        val index = indexMap[kind]!!
        indexMap[kind] = index + 1
        symbolMap[name] = VariableInfo(name, type, kind, index)
    }

    fun varCount(kind: VariableKind): Int =
        indexMap[kind]!!

    fun kindOf(name: String): VariableKind =
        symbolMap[name]?.kind ?: NONE

    fun typeOf(name: String): String =
        symbolMap[name]!!.type

    fun indexOf(name: String): Int =
        symbolMap[name]!!.index
}
