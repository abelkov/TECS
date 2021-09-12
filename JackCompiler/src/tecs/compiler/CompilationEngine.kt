package tecs.compiler

import tecs.compiler.Keyword.*
import tecs.compiler.TokenType.*

class CompilationEngine(private val t: JackTokenizer) {
    private val output = StringBuilder()
    private var offset = 0

    private val classTable = SymbolTable()
    private val subroutineTable = SymbolTable()

    fun compile(): String {
        advance()
        compileClass()
        return output.toString()
    }

    private fun compileClass() {
        append("<class>")
        indent()
        consumeKeyword(CLASS)
        val className = consumeIdentifier()
        appendIdentifier(className, category = "class")
        consumeSymbol("{")
        while (matchesKeywords(STATIC, FIELD)) {
            compileClassVarDec()
        }
        while (matchesKeywords(CONSTRUCTOR, FUNCTION, METHOD)) {
            compileSubroutineDec()
        }
        consumeSymbol("}")
        dedent()
        append("</class>")
    }

    private fun appendIdentifier(name: String, category: String, index: Int? = null, usage: String = "declared") {
        append("""<identifier name="$name" category="$category" index="$index" usage="$usage" />""")
    }

    private fun matchesKeywords(vararg keywords: Keyword): Boolean =
        t.tokenType == KEYWORD && t.keyword in keywords

    private fun matchesSymbols(vararg symbols: String): Boolean =
        t.tokenType == SYMBOL && t.symbol in symbols

    private fun matchesIdentifier(): Boolean =
        t.tokenType == IDENTIFIER

    private fun append(s: String) {
        output.appendLine(" ".repeat(offset) + s)
    }

    private fun indent() {
        offset += 4
    }

    private fun dedent() {
        offset -= 4
    }

    private fun consumeKeyword(keyword: Keyword? = null): Keyword {
        if (keyword != null) assert(matchesKeywords(keyword))
        val result = t.keyword!!
        append("<keyword>${result.name.lowercase()}</keyword>")
        advance()
        return result
    }

    private fun consumeIdentifier(): String {
        if (t.tokenType != IDENTIFIER) {
            throw IllegalStateException("Expected identifier")
        }
        val identifier = t.identifier
        append("<identifier>$identifier</identifier>")
        advance()
        return identifier
    }

    private fun consumeSymbol(symbol: String? = null) {
        if (symbol != null) assert(matchesSymbols(symbol)) {
            "Unmatched symbol $symbol"
        }
        append("<symbol>${t.symbol}</symbol>")
        advance()
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
        append("<classVarDec>")
        indent()
        val keyword = consumeKeyword()
        val type = consumeType()
        val firstName = consumeIdentifier()
        classTable.define(firstName, type, VariableKind.valueOf(keyword.name))
        appendIdentifier(firstName, category = keyword.name.lowercase(), index = classTable.indexOf(firstName))
        while (matchesSymbols(",")) {
            consumeSymbol(",")
            val nextName = consumeIdentifier()
            classTable.define(nextName, type, VariableKind.valueOf(keyword.name))
            appendIdentifier(nextName, category = keyword.name.lowercase(), index = classTable.indexOf(nextName))
        }
        consumeSymbol(";")
        dedent()
        append("</classVarDec>")
    }

    // ('constructor' | 'function' | 'method') ('void' | type) subroutineName
    // '(' parameterList ')' subroutineBody
    private fun compileSubroutineDec() {
        subroutineTable.reset()
        append("<subroutineDec>")
        indent()
        consumeKeyword()
        if (matchesKeywords(VOID)) {
            consumeKeyword()
        } else {
            consumeType()
        }
        val name = consumeIdentifier()
        appendIdentifier(name, category = "subroutine")
        consumeSymbol("(")
        compileParameterList()
        consumeSymbol(")")
        compileSubroutineBody()
        dedent()
        append("</subroutineDec>")
    }

    // ( type varName (',' type varName)* )?
    private fun compileParameterList() {
        append("<parameterList>")
        indent()
        if (matchesType()) {
            val firstType = consumeType()
            val firstName = consumeIdentifier()
            subroutineTable.define(firstName, firstType, VariableKind.ARG)
            appendIdentifier(firstName, category = "arg", index = subroutineTable.indexOf(firstName))
        }
        while (matchesSymbols(",")) {
            consumeSymbol(",")
            val nextType = consumeType()
            val nextName = consumeIdentifier()
            subroutineTable.define(nextName, nextType, VariableKind.ARG)
            appendIdentifier(nextName, category = "arg", index = subroutineTable.indexOf(nextName))
        }
        dedent()
        append("</parameterList>")
    }

    // '{' varDec* statements '}'
    private fun compileSubroutineBody() {
        append("<subroutineBody>")
        indent()
        consumeSymbol("{")
        while (matchesKeywords(VAR)) {
            compileVarDec()
        }
        compileStatements()
        consumeSymbol("}")
        dedent()
        append("</subroutineBody>")
    }

    // 'var' type varName (',' varName)* ;
    private fun compileVarDec() {
        append("<varDec>")
        indent()
        consumeKeyword(VAR)
        val type = consumeType()
        val firstName = consumeIdentifier()
        subroutineTable.define(firstName, type, VariableKind.VAR)
        appendIdentifier(firstName, category = "var", index = subroutineTable.indexOf(firstName))
        while (matchesSymbols(",")) {
            consumeSymbol(",")
            val nextName = consumeIdentifier()
            subroutineTable.define(nextName, type, VariableKind.VAR)
            appendIdentifier(nextName, category = "var", index = subroutineTable.indexOf(nextName))
        }
        consumeSymbol(";")
        dedent()
        append("</varDec>")
    }

    // statement*
    private fun compileStatements() {
        append("<statements>")
        indent()
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
        dedent()
        append("</statements>")
    }

    // 'let' varName ( '[' expression ']' )? '=' expression ';'
    private fun compileLet() {
        append("<letStatement>")
        indent()
        consumeKeyword(LET)
        val name = consumeIdentifier()
        val map = if (subroutineTable.contains(name)) subroutineTable else classTable
        val category = map.kindOf(name).name.lowercase()
        val index = map.indexOf(name)
        appendIdentifier(name, category, index, usage = "used")
        if (matchesSymbols("[")) {
            consumeSymbol("[")
            compileExpression()
            consumeSymbol("]")
        }
        consumeSymbol("=")
        compileExpression()
        consumeSymbol(";")
        dedent()
        append("</letStatement>")
    }

    // 'if' '(' expression ')' '{' statements '}' ( 'else' '{' statements '}' )?
    private fun compileIf() {
        append("<ifStatement>")
        indent()
        consumeKeyword(IF)
        consumeSymbol("(")
        compileExpression()
        consumeSymbol(")")
        consumeSymbol("{")
        compileStatements()
        consumeSymbol("}")
        if (matchesKeywords(ELSE)) {
            consumeKeyword(ELSE)
            consumeSymbol("{")
            compileStatements()
            consumeSymbol("}")
        }
        dedent()
        append("</ifStatement>")
    }

    // 'while' '(' expression ')' '{' statements '}'
    private fun compileWhile() {
        append("<whileStatement>")
        indent()
        consumeKeyword(WHILE)
        consumeSymbol("(")
        compileExpression()
        consumeSymbol(")")
        consumeSymbol("{")
        compileStatements()
        consumeSymbol("}")
        dedent()
        append("</whileStatement>")
    }

    // 'do' subroutineCall ';'
    private fun compileDo() {
        append("<doStatement>")
        indent()
        consumeKeyword(DO)
        val firstName = consumeIdentifier()
        if (matchesSymbols("(")) {
            appendIdentifier(firstName, category = "subroutine", usage = "used")
        } else {
            appendIdentifier(firstName, category = "class", usage = "used")
            consumeSymbol(".")
            val nextName = consumeIdentifier()
            appendIdentifier(nextName, category = "subroutine", usage = "used")
        }
        consumeSymbol("(")
        compileExpressionList()
        consumeSymbol(")")
        consumeSymbol(";")
        dedent()
        append("</doStatement>")
    }

    // 'return' expression? ';'
    private fun compileReturn() {
        append("<returnStatement>")
        indent()
        consumeKeyword(RETURN)
        if (!matchesSymbols(";")) {
            compileExpression()
        }
        consumeSymbol(";")
        dedent()
        append("</returnStatement>")
    }

    // term (op term)*
    private fun compileExpression() {
        append("<expression>")
        indent()
        compileTerm()
        while (matchesSymbols("+", "-", "*", "/", "&amp;", "|", "&lt;", "&gt;", "=")) {
            consumeSymbol()
            compileTerm()
        }
        dedent()
        append("</expression>")
    }

    // integerConstant | stringConstant | keywordConstant | varName |
    // varName '[' expression ']' | '(' expression ')' | unaryOp term | subroutineCall
    private fun compileTerm() {
        append("<term>")
        indent()
        if (t.tokenType == INT_CONST) {
            consumeIntConst()
        } else if (t.tokenType == STRING_CONST) {
            consumeStringConst()
        } else if (matchesKeywords(TRUE, FALSE, NULL, THIS)) {
            consumeKeyword()
        } else if (matchesSymbols("(")) {
            consumeSymbol("(")
            compileExpression()
            consumeSymbol(")")
        } else if (matchesSymbols("-", "~")) {
            consumeSymbol()
            compileTerm()
        } else if (matchesIdentifier()) {
            val name = consumeIdentifier()
            if (matchesSymbols("[")) {
                appendIdentifier(name, category = "subroutine", usage = "used")
                consumeSymbol("[")
                compileExpression()
                consumeSymbol("]")
            } else if (matchesSymbols("(")) {
                appendIdentifier(name, category = "subroutine", usage = "used")
                consumeSymbol("(")
                compileExpressionList()
                consumeSymbol(")")
            } else if (matchesSymbols(".")) {
                appendIdentifier(name, category = "class", usage = "used")
                consumeSymbol(".")
                val nextName = consumeIdentifier()
                appendIdentifier(nextName, category = "subroutine", usage = "used")
                consumeSymbol("(")
                compileExpressionList()
                consumeSymbol(")")
            } else {
                appendIdentifier(name, category = "subroutine", usage = "used")
            }
        } else {
            throw IllegalStateException("Invalid term")
        }
        dedent()
        append("</term>")
    }

    private fun consumeIntConst() {
        assert(t.tokenType == INT_CONST)
        append("<integerConstant>${t.intVal}</integerConstant>")
        advance()
    }

    private fun consumeStringConst() {
        assert(t.tokenType == STRING_CONST)
        append("<stringConstant>${t.stringVal}</stringConstant>")
        advance()
    }

    // ( expression (',' expression)* )?
    private fun compileExpressionList(): Int {
        append("<expressionList>")
        indent()
        var exprCount = 0
        if (matchesSymbols(")")) {
            dedent()
            append("</expressionList>")
            return exprCount
        }
        compileExpression()
        exprCount++
        while (matchesSymbols(",")) {
            consumeSymbol(",")
            compileExpression()
            exprCount++
        }
        dedent()
        append("</expressionList>")
        return exprCount
    }
}
