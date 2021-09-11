package tecs.analyzer

import tecs.analyzer.Keyword.*
import tecs.analyzer.TokenType.*

class CompilationEngine(private val t: JackTokenizer) {
    private val output = StringBuilder()
    private var offset = 0

    fun compile(): String {
        advance()
        compileClass()
        return output.toString()
    }

    private fun compileClass() {
        append("<class>")
        indent()
        consumeKeyword(CLASS)
        consumeIdentifier()
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

    private fun consumeKeyword(keyword: Keyword? = null) {
        if (keyword != null) assert(matchesKeywords(keyword))
        append("<keyword>${t.keyword!!.name.lowercase()}</keyword>")
        advance()
    }

    private fun consumeIdentifier() {
        if (t.tokenType != IDENTIFIER) {
            throw IllegalStateException("Expected identifier")
        }
        append("<identifier>${t.identifier}</identifier>")
        advance()
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

    private fun consumeType() {
        assert(matchesType())
        if (t.tokenType == KEYWORD) {
            consumeKeyword()
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
        consumeKeyword()
        consumeType()
        consumeIdentifier() // varName
        while (matchesSymbols(",")) {
            consumeSymbol(",")
            consumeIdentifier() // varName
        }
        consumeSymbol(";")
        dedent()
        append("</classVarDec>")
    }

    // ('constructor' | 'function' | 'method') ('void' | type) subroutineName
    // '(' parameterList ')' subroutineBody
    private fun compileSubroutineDec() {
        append("<subroutineDec>")
        indent()
        consumeKeyword()
        if (matchesKeywords(VOID)) {
            consumeKeyword()
        } else {
            consumeType()
        }
        consumeIdentifier() // subroutineName
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
            consumeType()
            consumeIdentifier() // varName
        }
        while (matchesSymbols(",")) {
            consumeSymbol(",")
            consumeType()
            consumeIdentifier() // varName
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
        consumeType()
        consumeIdentifier() // varName
        while (matchesSymbols(",")) {
            consumeSymbol(",")
            consumeIdentifier() // varName
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
        consumeIdentifier() // varName
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
        consumeIdentifier()
        if (matchesSymbols("(")) {
            consumeSymbol("(")
            compileExpressionList()
            consumeSymbol(")")
        } else {
            consumeSymbol(".")
            consumeIdentifier()
            consumeSymbol("(")
            compileExpressionList()
            consumeSymbol(")")
        }
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
            consumeIdentifier()
            if (matchesSymbols("[")) {
                consumeSymbol("[")
                compileExpression()
                consumeSymbol("]")
            } else if (matchesSymbols("(")) {
                consumeSymbol("(")
                compileExpressionList()
                consumeSymbol(")")
            } else if (matchesSymbols(".")) {
                consumeSymbol(".")
                consumeIdentifier()
                consumeSymbol("(")
                compileExpressionList()
                consumeSymbol(")")
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
