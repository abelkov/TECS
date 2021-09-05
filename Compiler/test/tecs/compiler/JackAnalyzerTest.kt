package tecs.compiler

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tecs.compiler.TokenType.*
import java.io.File
import java.nio.file.Files

private val escapedSymbols = mapOf(
    "<" to "&lt;",
    "&" to "&amp;",
    ">" to "&gt;",
    "\"" to "&quot;"
)

class JackAnalyzerTest {

    @Test
    fun testTokenizer() {
        File("testData").listFiles { it, _ -> it.isDirectory }!!.forEach { testDir: File ->
            if (testDir.name != "Simple") return@forEach

            val testPath = testDir.toPath()

            testDir.listFiles { _, name -> name.endsWith(".jack") }!!.forEach loop@{ jackFile ->
                // if (jackFile.name != "SquareGame.jack") return@loop

                val code = Files.readString(jackFile.toPath())
                val baseName = jackFile.name.removeSuffix(".jack")
                val qualifiedName = "${testDir.name}/$baseName"

                val tokenizerPath = testPath.resolve("${baseName}T.xml")
                val tokenizerExpected = Files.readString(tokenizerPath)
                checkTokenizer(qualifiedName, JackTokenizer(code), tokenizerExpected)
            }
        }
    }

    @Test
    fun testParser() {
        File("testData").listFiles { it, _ -> it.isDirectory }!!.forEach { testDir: File ->
            if (testDir.name != "ExpressionlessSquare") return@forEach

            val testPath = testDir.toPath()

            testDir.listFiles { _, name -> name.endsWith(".jack") }!!.forEach loop@{ jackFile ->
                if (jackFile.name != "Main.jack") return@loop

                val code = Files.readString(jackFile.toPath())
                val baseName = jackFile.name.removeSuffix(".jack")
                val qualifiedName = "${testDir.name}/$baseName"

                val parserPath = testPath.resolve("$baseName.xml")
                val parserExpected = Files.readString(parserPath)

                val engine = CompilationEngine(JackTokenizer(code))
                assertEquals(parserExpected, engine.compile(), "parser for '$qualifiedName' failed")
            }
        }
    }

    private fun checkTokenizer(testName: String, t: JackTokenizer, expected: String) {
        var actual = "<tokens>\n"
        while (t.hasMoreTokens()) {
            t.advance()
            when (t.tokenType) {
                KEYWORD -> {
                    actual += "    <keyword>${t.keyword!!.name.lowercase()}</keyword>\n"
                }
                SYMBOL -> {
                    val symbol = if (t.symbol in escapedSymbols) escapedSymbols[t.symbol] else t.symbol
                    actual += "    <symbol>$symbol</symbol>\n"
                }
                IDENTIFIER -> {
                    actual += "    <identifier>${t.identifier}</identifier>\n"
                }
                INT_CONST -> {
                    actual += "    <integerConstant>${t.intVal}</integerConstant>\n"
                }
                STRING_CONST -> {
                    actual += "    <stringConstant>${t.stringVal}</stringConstant>\n"
                }
                else -> {
                    // was comment or empty line, ignore
                }
            }
        }
        actual += "</tokens>"
        assertEquals(expected, actual, "tokenizer for '$testName' failed")
    }
}
