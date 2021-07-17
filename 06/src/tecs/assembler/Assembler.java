package tecs.assembler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static tecs.assembler.AsmPattern.NUMBER;
import static tecs.assembler.Command.*;

public class Assembler {
	public static void main(String[] args) throws IOException {
		SymbolTable symbolTable = new SymbolTable();
		Code codeTable = new Code();
		Path asmFilePath = Paths.get(args[0]);

		// 1st pass: update the symbol map with labels
		int lineNumber = 0;
		Parser labelParser = new Parser(asmFilePath);
		while (labelParser.hasMoreCommands()) {
			labelParser.advance();
			if (labelParser.commandType() == null) {
				continue;
			} else if (labelParser.commandType() == L_COMMAND) {
				String symbol = labelParser.getSymbol();
				symbolTable.addEntry(symbol, lineNumber);
			} else {
				lineNumber += 1;
			}
		}

		// path of output file
		Path parentDirectory = asmFilePath.getParent();
		String hackFileName = asmFilePath.getFileName().toString().split("\\.")[0] + ".hack";
		Path hackFilePath = parentDirectory.resolve(hackFileName);

		// 2nd pass: turn assembly into machine code
		try (BufferedWriter bw = Files.newBufferedWriter(hackFilePath)) {
			Parser programParser = new Parser(asmFilePath);
			int nextRAMAddress = 16;
			while (programParser.hasMoreCommands()) {
				programParser.advance();
				if (programParser.commandType() == C_COMMAND) {
					String c = codeTable.comp(programParser.getComp());
					String d = codeTable.dest(programParser.getDest());
					String j = codeTable.jump(programParser.getJump());
					bw.write("111" + c + d + j);
					bw.newLine();
				} else if (programParser.commandType() == A_COMMAND) {
					String symbol = programParser.getSymbol();
					int address;
					if (Pattern.matches(NUMBER, symbol)) {
						address = Integer.parseInt(symbol);
					} else {
						if (!symbolTable.contains(symbol)) {
							symbolTable.addEntry(symbol, nextRAMAddress);
							nextRAMAddress += 1;
						}
						address = symbolTable.getAddress(symbol);
					}
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
