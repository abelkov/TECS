package tecs.assembler

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files

class AssemblerTest {
    @Test
    fun testAssemble() {
        File("testData").listFiles { _, name -> name.endsWith(".asm") }!!.forEach { file ->
            val asmPath = file.toPath()
            val asmCode = Files.readString(asmPath)
            val assembler = Assembler(asmCode)
            val actualHackCode = assembler.assemble()

            val parentDirectory = asmPath.parent
            val hackFileName = asmPath.fileName.toString().split(".")[0] + ".hack"
            val hackFilePath = parentDirectory.resolve(hackFileName)
            val expectedHackCode = Files.readString(hackFilePath)
            assertEquals(expectedHackCode, actualHackCode)
        }
    }
}
