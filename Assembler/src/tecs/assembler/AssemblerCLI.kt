package tecs.assembler

import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    val asmFilePath = Paths.get(args[0])
    val asm = Files.readString(asmFilePath)
    val assembler = Assembler(asm)
    val output = assembler.assemble()
    val parentDirectory = asmFilePath.parent
    val hackFileName = asmFilePath.fileName.toString().split("\\.".toRegex()).toTypedArray()[0] + ".hack"
    val hackFilePath = parentDirectory.resolve(hackFileName)
    Files.writeString(hackFilePath, output)
}
