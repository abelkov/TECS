package tecs.compiler

import tecs.compiler.TokenType.*

private val escapedSymbols = mapOf(
    "<" to "&lt;",
    "&" to "&amp;",
    ">" to "&gt;",
    "\"" to "&quot;"
)

class JackTokenizer(private val code: String) {
    private var current = 0
    private var lineNumber = 1
    private var columnNumber = 1

    var tokenType: TokenType? = null
        private set

    var keyword: Keyword? = null
        private set

    var symbol: String = ""
        private set

    var identifier: String = ""
        private set

    var intVal: Int = 0
        private set

    var stringVal: String = ""
        private set

    fun hasMoreTokens(): Boolean = current < code.length

    fun advance() {
        val c = code[current].toString()
        when {
            c == "/" -> matchComment()

            c in listOf(
                "{", "}", "(", ")", "[", "]", ".", ",", ";",
                "+", "-", "*", "/", "&", "|", "<", ">", "=", "~"
            ) -> {
                matchSymbol(c)
            }

            c.isBlank() -> {
                tokenType = null
                consume()
                if (hasMoreTokens()) advance()
            }

            c == "\"" -> matchString()

            c.isDigit() -> matchInteger()

            c.isAlpha() -> matchIdentifier()

            else -> {
                throwError("unexpected character '$c'")
            }
        }
    }

    private fun matchSymbol(c: String) {
        tokenType = SYMBOL
        symbol = if (c in escapedSymbols) escapedSymbols[c]!! else c
        consume()
    }

    private fun throwError(message: String) {
        throw IllegalStateException("[$lineNumber:$columnNumber] $message")
    }

    private fun matchInteger() {
        var lexeme = code[current].toString()
        consume()
        while (hasMoreTokens() && code[current].toString().isDigit()) {
            lexeme += code[current]
            consume()
        }
        tokenType = INT_CONST
        intVal = lexeme.toInt()
    }

    private fun matchString() {
        consume() // skip over opening "
        var lexeme = ""
        while (hasMoreTokens() && code[current] != '"' && code[current] != '\n') {
            lexeme += code[current]
            consume()
        }
        if (!hasMoreTokens() || code[current] != '"') {
            throwError("unterminated string")
        }
        consume() // skip over closing "
        tokenType = STRING_CONST
        stringVal = lexeme
    }

    private fun matchComment() {
        if (current + 1 >= code.length) return
        when (code[current + 1]) {
            '/' -> {
                current += 2 // skip over '//'
                while (hasMoreTokens() && code[current] != '\n') {
                    consume()
                }
                if (hasMoreTokens()) {
                    advance()
                }
            }
            '*' -> {
                current += 2 // skip over '/*'
                while ((current + 1 < code.length) && !(code[current] == '*' && code[current + 1] == '/')) {
                    consume()
                }
                if (current + 1 >= code.length) {
                    throwError("unterminated multi-line comment")
                }
                current += 2 // skip over '*/'
                if (hasMoreTokens()) {
                    advance()
                }
            }
            else -> matchSymbol(code[current].toString()) // Just a single '/' operator
        }
    }

    // try to match keyword, if not - try to match arbitrary identifier
    private fun matchIdentifier() {
        var lexeme = code[current].toString()
        consume()
        while (hasMoreTokens() && code[current].toString().isAlphanumeric()) {
            lexeme += code[current]
            consume()
        }
        if (lexeme.uppercase() in Keyword.values().map { it.name }) {
            tokenType = KEYWORD
            keyword = Keyword.valueOf(lexeme.uppercase())
        } else {
            tokenType = IDENTIFIER
            identifier = lexeme
        }
    }

    private fun consume() {
        assert(hasMoreTokens())
        if (code[current] == '\n') {
            lineNumber++
            columnNumber = 1
        } else {
            columnNumber++
        }
        current++
    }
}

private fun String.isAlpha(): Boolean = this in "a".."z" || this in "A".."Z" || this == "_"

private fun String.isDigit(): Boolean = this in "0".."9"

private fun String.isAlphanumeric(): Boolean = isAlpha() || isDigit()
