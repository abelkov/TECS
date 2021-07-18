package tecs.assembler

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files

internal class AssemblerTest {
    @Test
    fun testAssemble() {
        val asmFiles = File("testData").listFiles { dir: File?, name: String -> name.endsWith(".asm") }
        for (file in asmFiles) {
            val asmPath = file.toPath()
            val asmCode = Files.readString(asmPath)
            val assembler = Assembler(asmCode)
            val output = assembler.assemble()
            val parentDirectory = asmPath.parent
            val hackFileName = asmPath.fileName.toString().split("\\.".toRegex()).toTypedArray()[0] + ".hack"
            val hackFilePath = parentDirectory.resolve(hackFileName)
            val hackCode = Files.readString(hackFilePath)
            Assertions.assertEquals(hackCode, output)
        }
    }
}