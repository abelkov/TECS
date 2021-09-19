package tecs.compiler

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files

class JackCompilerTest {

    @Test
    fun testCompiler() {
        File("testData").listFiles { it, _ -> it.isDirectory }!!.forEach { testDir: File ->
            // if (testDir.name != "ComplexArrays") return@forEach
            val testPath = testDir.toPath()

            testDir.listFiles { _, name -> name.endsWith(".jack") }!!.forEach loop@{ jackFile ->
                // if (jackFile.name != "Main.jack") return@loop

                val code = Files.readString(jackFile.toPath())
                val baseName = jackFile.name.removeSuffix(".jack")
                val qualifiedName = "${testDir.name}/$baseName"
                val cmpPath = testPath.resolve("$baseName.cmp")
                val engine = CompilationEngine(JackTokenizer(code))
                assertEquals(Files.readString(cmpPath), engine.compile(), "Compile '$qualifiedName' failed")
            }
        }
    }
}
