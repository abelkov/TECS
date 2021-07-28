package tecs.vmtranslator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files

class VMTranslatorTest {

    @Test
    fun testTranslate() {
        File("testData").listFiles { _, name -> name.endsWith(".vm") }!!.forEach { file ->
            // if (file.name != "FibonacciSeries.vm") return@forEach

            val vmPath = file.toPath()
            val testData = vmPath.parent
            val baseName = vmPath.fileName.toString().split(".")[0]

            val asmPath = testData.resolve("$baseName.asm")
            val tstPath = testData.resolve("$baseName.tst")
            val outPath = testData.resolve("$baseName.out")
            val cmpPath = testData.resolve("$baseName.cmp")

            val vmCode = Files.readString(vmPath)
            val asmCode = VMTranslator(baseName, vmCode).translate()
            Files.writeString(asmPath, asmCode)

            val command = "../tools/CPUEmulator.sh ${tstPath.toAbsolutePath()}"
            val cpuEmulator = Runtime.getRuntime().exec(command)
            cpuEmulator.waitFor()

            assertEquals(Files.readString(cmpPath), Files.readString(outPath), "$baseName failed")
        }
    }
}