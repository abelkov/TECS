package tecs.vmtranslator

import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    // assume a file path for now
    val sourcePath = Paths.get(args[0])
    val parentDirectory = sourcePath.parent
    val baseName = sourcePath.fileName.toString().split(".")[0]

    val code = Files.readString(sourcePath)
    val translator = VMTranslator(baseName, code)
    val output = translator.translate()

    val asmFilePath = parentDirectory.resolve("$baseName.asm")
    Files.writeString(asmFilePath, output)
}
