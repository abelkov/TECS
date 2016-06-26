package tecs.assembler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;

import static tecs.assembler.Command.*;

public class Assembler {
	public static void main(String[] args) throws IOException {
		//SymbolTable symbolTable = new SymbolTable();
		Code codeTable = new Code();
		Path asmFilePath = Paths.get(args[0]);

		// 1st pass: update the symbol map with labels
//		LabelParser labelParser = new LabelParser(asmFilePath, symbolTable);
//		labelParser.parse();

		// path of output file
		Path parentDirectory = asmFilePath.getParent();
		String hackFileName = asmFilePath.getFileName().toString().split("\\.")[0] + ".hack";
		Path hackFilePath = parentDirectory.resolve(hackFileName);

		// 2nd pass: turn assembly into machine code
		try (BufferedWriter bw = Files.newBufferedWriter(hackFilePath)) {
			Parser parser = new Parser(asmFilePath);
			while (parser.hasMoreCommands()) {
				parser.advance();
				if (parser.commandType() == C_COMMAND) {
					String c = codeTable.comp(parser.comp());
					String d = codeTable.dest(parser.dest());
					String j = codeTable.jump(parser.jump());
					bw.write("111" + c + d + j);
					bw.newLine();
				} else if (parser.commandType() == A_COMMAND) {
					String address = parser.symbol();
					bw.write(address);
					bw.newLine();
				}
			}
		}
	}
}
