package tecs.vmtranslator;

import java.nio.file.Path;
import java.nio.file.Paths;

public class VMTranslator {
	public static void main(String[] args) {
		// assume a file path for now
		Path sourcePath = Paths.get(args[0]);

		Path parentDirectory = sourcePath.getParent();
		String asmFileName = sourcePath.getFileName().toString().split("\\.")[0] + ".asm";

		// out.asm
		Path asmFilePath = parentDirectory.resolve(asmFileName);

		
	}
}
