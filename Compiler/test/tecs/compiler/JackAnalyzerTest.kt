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
    fun test() {
        File("testData").listFiles { it, _ -> it.isDirectory }!!.forEach { testDir: File ->
            // if (testDir.name != "ArrayTest") return@forEach

            val testPath = testDir.toPath()

            testDir.listFiles { _, name -> name.endsWith(".jack") }!!.forEach loop@{ jackFile ->
                // if (jackFile.name != "SquareGame.jack") return@loop

                val code = Files.readString(jackFile.toPath())
                val baseName = jackFile.name.removeSuffix(".jack")

                val tokenizerPath = testPath.resolve("${baseName}T.xml")
                val tokenizerExpected = Files.readString(tokenizerPath)
                val tokenizer = JackTokenizer(code)
                checkTokenizer("${testDir.name}/$baseName", tokenizer, tokenizerExpected)

                // val parserPath = testPath.resolve("$baseName.xml")
                // val parserExpected = Files.readString(parserPath)

                // translator.translate(jackFile.name.removeSuffix(".vm"), jackCode)
            }


            // val asmPath = testPath.resolve("${test.name}.asm")
            // val tstPath = testPath.resolve("${test.name}.tst")
            // val outPath = testPath.resolve("${test.name}.out")
            // val cmpPath = testPath.resolve("${test.name}.cmp")
            //
            // val generateInit = test.name in listOf("FibonacciElement", "StaticsTest")
            // val translator = VMTranslator(generateInit = generateInit)
            //
            // test.listFiles { _, name -> name.endsWith(".vm") }!!.forEach { vmFile ->
            //     val vmCode = Files.readString(vmFile.toPath())
            //     translator.translate(vmFile.name.removeSuffix(".vm"), vmCode)
            // }
            //
            // Files.writeString(asmPath, translator.output)
            // val command = "../tools/CPUEmulator.sh ${tstPath.toAbsolutePath()}"
            // val cpuEmulator = Runtime.getRuntime().exec(command)
            // cpuEmulator.waitFor()
            //
            // assertEquals(Files.readString(cmpPath), Files.readString(outPath), "${test.name} failed")
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
