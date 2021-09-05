package tecs.compiler

import tecs.compiler.Keyword.*
import tecs.compiler.TokenType.*

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

    private fun matchesSymbol(symbol: String): Boolean =
        t.tokenType == SYMBOL && t.symbol == symbol

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

    private fun consumeSymbol(symbol: String) {
        if (t.tokenType != SYMBOL || t.symbol != symbol) {
            throw IllegalStateException("Expected symbol $symbol")
        }
        append("<symbol>$symbol</symbol>")
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
        while (matchesSymbol(",")) {
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
        if (matchesType()) {
            consumeType()
            consumeIdentifier() // varName
        }
        while (matchesSymbol(",")) {
            consumeSymbol(",")
            consumeType()
            consumeIdentifier() // varName
        }
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
        while (matchesSymbol(",")) {
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
        if (matchesSymbol("[")) {
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
        compileExpression()
        consumeSymbol(";")
        dedent()
        append("</doStatement>")
    }

    // 'return' expression? ';'
    private fun compileReturn() {
        append("<returnStatement>")
        indent()
        consumeKeyword(RETURN)
        if (!matchesSymbol(";")) {
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

        // TODO (op term)*

        dedent()
        append("</expression>")
    }

    // integerConstant | stringConstant | keywordConstant | varName |
    // TODO
    private fun compileTerm() {
        append("<term>")
        indent()

        if (t.tokenType == INT_CONST) {
            consumeIntConst()
        } else if (t.tokenType == STRING_CONST) {
            consumeStringConst()
        } else if (matchesKeywords(TRUE, FALSE, NULL, THIS)) {
            consumeKeyword()
        } else if (matchesIdentifier()) {
            consumeIdentifier()
        } else {
            TODO()
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

    private fun compileExpressionList(): Int {
        return 0
    }
}