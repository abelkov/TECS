package tecs.assembler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static tecs.assembler.Command.A_COMMAND;
import static tecs.assembler.Command.C_COMMAND;

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
					String c = codeTable.comp(parser.getComp());
					String d = codeTable.dest(parser.getDest());
					String j = codeTable.jump(parser.getJump());
					bw.write("111" + c + d + j);
					bw.newLine();
				} else if (parser.commandType() == A_COMMAND) {
					String symbol = parser.getSymbol();
					int address = Integer.parseInt(symbol);
					String binaryAddress = toBinary(address);
					bw.write(binaryAddress);
					bw.newLine();
				}
			}
		}
	}

	private static String toBinary(int address) {
		return String.format("%16s", Integer.toBinaryString(address)).replace(' ', '0');
	}
}
