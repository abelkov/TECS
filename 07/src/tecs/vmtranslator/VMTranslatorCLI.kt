package tecs.vmtranslator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static tecs.vmtranslator.Command.*;

public class VMTranslator {
	public static void main(String[] args) throws IOException {
		// assume a file path for now
		Path sourcePath = Paths.get(args[0]);

		Path parentDirectory = sourcePath.getParent();
		String asmFileName = sourcePath.getFileName().toString().split("\\.")[0] + ".asm";

		// out.asm
		Path asmFilePath = parentDirectory.resolve(asmFileName);

		Parser p = new Parser(sourcePath);
		CodeWriter c = new CodeWriter(asmFilePath);
		while (p.hasMoreCommands()) {
			p.advance();
			if (p.commandType() == C_ARITHMETIC) {
				c.writeArithmetic(p.getArg1());
			} else if (p.commandType() == C_PUSH) {
				c.writePushPop(C_PUSH, p.getArg1(), p.getArg2());
			} else if (p.commandType() == C_POP) {
				c.writePushPop(C_POP, p.getArg1(), p.getArg2());
			}
		}
		c.close();
	}
}
