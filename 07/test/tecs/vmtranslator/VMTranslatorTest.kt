package tecs.vmtranslator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files

class VMTranslatorTest {

    @Test
    fun testTranslate() {
        File("testData").listFiles { it, _ -> it.isDirectory }!!.forEach { test: File ->
            // if (test.name != "StaticsTest") return@forEach

            val testPath = test.toPath()
            val asmPath = testPath.resolve("${test.name}.asm")
            val tstPath = testPath.resolve("${test.name}.tst")
            val outPath = testPath.resolve("${test.name}.out")
            val cmpPath = testPath.resolve("${test.name}.cmp")

            val generateInit = test.name in listOf("FibonacciElement", "StaticsTest")
            val translator = VMTranslator(generateInit = generateInit)

            test.listFiles { _, name -> name.endsWith(".vm") }!!.forEach { vmFile ->
                val vmCode = Files.readString(vmFile.toPath())
                translator.translate(vmFile.name.removeSuffix(".vm"), vmCode)
            }

            Files.writeString(asmPath, translator.output)
            val command = "../tools/CPUEmulator.sh ${tstPath.toAbsolutePath()}"
            val cpuEmulator = Runtime.getRuntime().exec(command)
            cpuEmulator.waitFor()

            assertEquals(Files.readString(cmpPath), Files.readString(outPath), "${test.name} failed")
        }
    }
}
