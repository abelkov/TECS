package tecs.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AssemblerCLI {
	public static void main(String[] args) throws IOException {
		String content = Files.readString(Paths.get(args[0]));
		var assembler = new Assembler(content);
		String output = assembler.assemble();
		Path asmFilePath = Paths.get(args[0]);
		Path parentDirectory = asmFilePath.getParent();
		String hackFileName = asmFilePath.getFileName().toString().split("\\.")[0] + ".hack";
		Path hackFilePath = parentDirectory.resolve(hackFileName);
		Files.writeString(hackFilePath, output);
	}
}
