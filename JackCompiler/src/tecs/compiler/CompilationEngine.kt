package tecs.compiler

import tecs.compiler.Keyword.*
import tecs.compiler.TokenType.*

private val operatorMap = mapOf(
    "+" to "add",
    "-" to "sub",
    "*" to "call Math.multiply 2",
    "/" to "call Math.divide 2",
    "&" to "and",
    "|" to "or",
    "<" to "lt",
    ">" to "gt",
    "=" to "eq"
)

class CompilationEngine(private val t: JackTokenizer) {
    private lateinit var className: String
    private var subroutineName: String = ""
    private var subroutineKind: Keyword = NULL
    private val output = StringBuilder()
    private var whileCount: Int = 0
    private var ifCount: Int = 0
    private val classTable = SymbolTable()
    private val subroutineTable = SymbolTable()

    fun compile(): String {
        advance()
        compileClass()
        return output.toString()
    }

    private fun compileClass() {
        consumeKeyword(CLASS)
        className = consumeIdentifier()
        consumeSymbol("{")
        while (matchesKeywords(STATIC, FIELD)) {
            compileClassVarDec()
        }
        while (matchesKeywords(CONSTRUCTOR, FUNCTION, METHOD)) {
            compileSubroutineDec()
        }
        consumeSymbol("}")
    }

    private fun matchesKeywords(vararg keywords: Keyword): Boolean =
        t.tokenType == KEYWORD && t.keyword in keywords

    private fun matchesSymbols(vararg symbols: String): Boolean =
        t.tokenType == SYMBOL && t.symbol in symbols

    private fun matchesIdentifier(): Boolean =
        t.tokenType == IDENTIFIER

    private fun append(s: String) {
        output.appendLine(s)
    }

    private fun consumeKeyword(keyword: Keyword? = null): Keyword {
        if (keyword != null) assert(matchesKeywords(keyword))
        val result = t.keyword!!
        advance()
        return result
    }

    private fun consumeIdentifier(): String {
        if (t.tokenType != IDENTIFIER) {
            throw IllegalStateException("Expected identifier")
        }
        val identifier = t.identifier
        advance()
        return identifier
    }

    private fun consumeSymbol(symbol: String? = null): String {
        if (symbol != null) assert(matchesSymbols(symbol)) {
            "Unmatched symbol $symbol"
        }
        val result = t.symbol
        advance()
        return result
    }

    private fun matchesType(): Boolean {
        return matchesKeywords(INT, BOOLEAN, CHAR) || matchesIdentifier()
    }

    private fun consumeType(): String {
        assert(matchesType())
        return if (t.tokenType == KEYWORD) {
            consumeKeyword().name.lowercase()
        } else {
            consumeIdentifier()
        }
    }

    private fun advance() {
        if (t.hasMoreTokens()) t.advance()
    }

    // ('static' | 'field') type varName (',' varName)* ;
    private fun compileClassVarDec() {
        val keyword = consumeKeyword()
        val type = consumeType()
        val firstName = consumeIdentifier()
        classTable.define(firstName, type, VariableKind.valueOf(keyword.name))
        while (matchesSymbols(",")) {
            consumeSymbol(",")
            val nextName = consumeIdentifier()
            classTable.define(nextName, type, VariableKind.valueOf(keyword.name))
        }
        consumeSymbol(";")
    }

    // ('constructor' | 'function' | 'method') ('void' | type) subroutineName
    // '(' parameterList ')' subroutineBody
    private fun compileSubroutineDec() {
        subroutineTable.reset()
        whileCount = 0
        ifCount = 0
        subroutineKind = consumeKeyword()
        if (subroutineKind == METHOD) {
            subroutineTable.define("this", className, VariableKind.ARG)
        }
        if (matchesKeywords(VOID)) {
            consumeKeyword()
        } else {
            consumeType()
        }
        subroutineName = consumeIdentifier()
        consumeSymbol("(")
        compileParameterList()
        consumeSymbol(")")
        compileSubroutineBody()
    }

    // ( type varName (',' type varName)* )?
    private fun compileParameterList() {
        if (matchesType()) {
            val firstType = consumeType()
            val firstName = consumeIdentifier()
            subroutineTable.define(firstName, firstType, VariableKind.ARG)
        }
        while (matchesSymbols(",")) {
            consumeSymbol(",")
            val nextType = consumeType()
            val nextName = consumeIdentifier()
            subroutineTable.define(nextName, nextType, VariableKind.ARG)
        }
    }

    // '{' varDec* statements '}'
    private fun compileSubroutineBody() {
        consumeSymbol("{")
        while (matchesKeywords(VAR)) {
            compileVarDec()
        }
        append("function $className.$subroutineName ${subroutineTable.varCount(VariableKind.VAR)}")
        when (subroutineKind) {
            METHOD -> {
                append("push argument 0")
                append("pop pointer 0")
            }
            CONSTRUCTOR -> {
                append("push constant ${classTable.varCount(VariableKind.FIELD)}")
                append("call Memory.alloc 1")
                append("pop pointer 0")
            }
            else -> {
            }
        }
        compileStatements()
        consumeSymbol("}")
    }

    // 'var' type varName (',' varName)* ;
    private fun compileVarDec() {
        consumeKeyword(VAR)
        val type = consumeType()
        val firstName = consumeIdentifier()
        subroutineTable.define(firstName, type, VariableKind.VAR)
        while (matchesSymbols(",")) {
            consumeSymbol(",")
            val nextName = consumeIdentifier()
            subroutineTable.define(nextName, type, VariableKind.VAR)
        }
        consumeSymbol(";")
    }

    // statement*
    private fun compileStatements() {
        while (matchesKeywords(LET, IF, WHILE, DO, RETURN)) {
            if (matchesKeywords(LET)) {
                compileLet()
            } else if (matchesKeywords(IF)) {
                compileIf()
            } else if (matchesKeywords(WHILE)) {
                compileWhile()
            } else if (matchesKeywords(DO)) {
                compileDo()
            } else if (matchesKeywords(RETURN)) {
                compileReturn()
            }
        }
    }

    // 'let' varName ( '[' expression ']' )? '=' expression ';'
    private fun compileLet() {
        consumeKeyword(LET)
        val name = consumeIdentifier()
        val map = if (subroutineTable.contains(name)) subroutineTable else classTable
        if (matchesSymbols("[")) {
            consumeSymbol("[")
            compileExpression()
            append("push ${map.kindOf(name).toSegmentName()} ${map.indexOf(name)}")
            append("add")
            consumeSymbol("]")
            consumeSymbol("=")
            compileExpression()
            append("pop temp 0")
            append("pop pointer 1")
            append("push temp 0")
            append("pop that 0")
            consumeSymbol(";")
        } else {
            consumeSymbol("=")
            compileExpression()
            consumeSymbol(";")
            append("pop ${map.kindOf(name).toSegmentName()} ${map.indexOf(name)}")
        }
    }

    // 'if' '(' expression ')' '{' statements '}' ( 'else' '{' statements '}' )?
    private fun compileIf() {
        val count = ifCount
        ifCount++
        consumeKeyword(IF)
        consumeSymbol("(")
        compileExpression()
        append("if-goto IF_TRUE$count")
        append("goto IF_FALSE$count")
        append("label IF_TRUE$count")
        consumeSymbol(")")
        consumeSymbol("{")
        compileStatements()
        consumeSymbol("}")
        if (matchesKeywords(ELSE)) {
            append("goto IF_END$count")
            append("label IF_FALSE$count")
            consumeKeyword(ELSE)
            consumeSymbol("{")
            compileStatements()
            consumeSymbol("}")
            append("label IF_END$count")
        } else {
            append("label IF_FALSE$count")
        }
    }

    // 'while' '(' expression ')' '{' statements '}'
    private fun compileWhile() {
        val count = whileCount
        whileCount++
        consumeKeyword(WHILE)
        consumeSymbol("(")
        append("label WHILE_EXP$count")
        compileExpression()
        append("not")
        append("if-goto WHILE_END$count")
        consumeSymbol(")")
        consumeSymbol("{")
        compileStatements()
        append("goto WHILE_EXP$count")
        append("label WHILE_END$count")
        consumeSymbol("}")
    }

    // 'do' subroutineCall ';'
    private fun compileDo() {
        consumeKeyword(DO)
        compileExpression()
        append("pop temp 0")
        consumeSymbol(";")
    }

    // 'return' expression? ';'
    private fun compileReturn() {
        consumeKeyword(RETURN)
        if (matchesSymbols(";")) {
            append("push constant 0")
        } else {
            compileExpression()
        }
        append("return")
        consumeSymbol(";")
    }

    // term (op term)*
    private fun compileExpression() {
        compileTerm()
        while (matchesSymbols("+", "-", "*", "/", "&", "|", "<", ">", "=")) {
            val symbol = consumeSymbol()
            compileTerm()
            append(operatorMap[symbol]!!)
        }
    }

    // integerConstant | stringConstant | keywordConstant | varName |
    // varName '[' expression ']' | '(' expression ')' | unaryOp term | subroutineCall
    private fun compileTerm() {
        if (matchesSymbols("(")) {
            consumeSymbol("(")
            compileExpression()
            consumeSymbol(")")
        } else if (matchesSymbols("-")) {
            consumeSymbol()
            compileTerm()
            append("neg")
        } else if (matchesSymbols("~")) {
            consumeSymbol()
            compileTerm()
            append("not")
        } else if (t.tokenType == INT_CONST) {
            consumeIntConst()
        } else if (t.tokenType == STRING_CONST) {
            consumeStringConst()
        } else if (matchesKeywords(NULL, FALSE)) {
            consumeKeyword()
            append("push constant 0")
        } else if (matchesKeywords(TRUE)) {
            consumeKeyword()
            append("push constant 0")
            append("not")
        } else if (matchesKeywords(THIS)) {
            consumeKeyword()
            append("push pointer 0")
            if (matchesSymbols(".")) {
                // this.methodName(...)
                consumeSymbol(".")
                val methodName = consumeIdentifier()
                consumeSymbol("(")
                val argCount = compileExpressionList()
                consumeSymbol(")")
                append("call $className.$methodName ${argCount + 1}")
            }
        } else if (matchesIdentifier()) {
            val name = consumeIdentifier()
            val map = when {
                subroutineTable.contains(name) -> subroutineTable
                classTable.contains(name) -> classTable
                else -> null
            }
            if (matchesSymbols("[")) {
                // array access
                consumeSymbol("[")
                compileExpression()
                append("push ${map!!.kindOf(name).toSegmentName()} ${map.indexOf(name)}")
                append("add")
                append("pop pointer 1")
                append("push that 0")
                consumeSymbol("]")
            } else if (matchesSymbols("(")) {
                // method call on implicit 'this': methodName(...)
                append("push pointer 0")
                consumeSymbol("(")
                val argCount = compileExpressionList()
                consumeSymbol(")")
                append("call $className.$name ${argCount + 1}")
            } else if (matchesSymbols(".")) {
                var argCount = 0
                if (map != null) {
                    // it's a method call: varName.methodName(...)
                    append("push ${map.kindOf(name).toSegmentName()} ${map.indexOf(name)}")
                    argCount++
                }
                consumeSymbol(".")
                val nextName = consumeIdentifier()
                consumeSymbol("(")
                argCount += compileExpressionList()
                consumeSymbol(")")
                val className = map?.typeOf(name) ?: name
                append("call $className.$nextName $argCount")
            } else {
                if (map == null) throw IllegalStateException("Undefined variable '$name'")
                append("push ${map.kindOf(name).toSegmentName()} ${map.indexOf(name)}")
            }
        } else {
            throw IllegalStateException("Invalid term")
        }
    }

    private fun consumeIntConst() {
        assert(t.tokenType == INT_CONST)
        append("push constant ${t.intVal}")
        advance()
    }

    private fun consumeStringConst() {
        assert(t.tokenType == STRING_CONST)
        val str = t.stringVal
        advance()
        append("push constant ${str.length}")
        append("call String.new 1")
        for (char in str) {
            append("push constant ${char.code}")
            append("call String.appendChar 2")
        }
    }

    // ( expression (',' expression)* )?
    private fun compileExpressionList(): Int {
        var exprCount = 0
        if (matchesSymbols(")")) {
            return exprCount
        }
        compileExpression()
        exprCount++
        while (matchesSymbols(",")) {
            consumeSymbol(",")
            compileExpression()
            exprCount++
        }
        return exprCount
    }
}
