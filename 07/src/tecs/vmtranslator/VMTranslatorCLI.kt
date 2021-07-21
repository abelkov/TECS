package tecs.vmtranslator

import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    // assume a file path for now
    val sourcePath = Paths.get(args[0])
    val code = Files.readString(sourcePath)
    val t = VMTranslator(code)
    val output = t.translate()

    val parentDirectory = sourcePath.parent
    val asmFileName = sourcePath.fileName.toString().split("\\.".toRegex()).toTypedArray()[0] + ".asm"
    // out.asm
    val asmFilePath = parentDirectory.resolve(asmFileName)
    Files.writeString(asmFilePath, output)
}
