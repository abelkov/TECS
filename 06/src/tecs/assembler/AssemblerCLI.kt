package tecs.assembler

import kotlin.jvm.JvmStatic
import java.nio.file.Files
import java.nio.file.Paths

object AssemblerCLI {
    @JvmStatic
    fun main(args: Array<String>) {
        val content = Files.readString(Paths.get(args[0]))
        val assembler = Assembler(content)
        val output = assembler.assemble()
        val asmFilePath = Paths.get(args[0])
        val parentDirectory = asmFilePath.parent
        val hackFileName = asmFilePath.fileName.toString().split("\\.".toRegex()).toTypedArray()[0] + ".hack"
        val hackFilePath = parentDirectory.resolve(hackFileName)
        Files.writeString(hackFilePath, output)
    }
}