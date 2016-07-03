package tecs.vmtranslator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static tecs.vmtranslator.Command.C_PUSH;

class CodeWriter {
	private BufferedWriter bw;
	private String n = System.getProperty("line.separator");

	CodeWriter(Path outputPath) throws IOException {
		setFilePath(outputPath);
	}

	void setFilePath(Path outputPath) throws IOException {
		if (bw != null) {
			bw.close();
		}
		bw = Files.newBufferedWriter(outputPath);
	}

	void writeArithmetic(String command) throws IOException {
		switch (command) {
			case "add":
				writeBinary("+");
				break;
			case "sub":
				writeBinary("-");
				break;
			case "neg":
				writeUnary("-");
				break;
			case "eq":
				writeBoolean("EQ");
				break;
			case "gt":
				writeBoolean("GT");
				break;
			case "lt":
				writeBoolean("LT");
				break;
			case "and":
				writeBinary("&");
				break;
			case "or":
				writeBinary("|");
				break;
			case "not":
				writeUnary("!");
				break;
		}
	}

	private void writeBinary(String op) throws IOException {
		String command;
		if (op.equals("-")) {
			command = "M-D";
		} else {
			command = "D" + op + "M";
		}
		String code =
				"@SP" + n +
				"AM=M-1" + n +
				"D=M" + n +
				"A=A-1" + n +
				"M=" + command + n;
		bw.write(code);
	}

	private void writeUnary(String op) throws IOException {
		String code =
				"@SP" + n +
				"A=M-1" + n +
				"M=" + op + "M" + n;
		bw.write(code);
	}

	private void writeBoolean(String command) throws IOException {
		String code =
				"@SP" + n +
				"M=M-1" + n +
				"AM=M-1" + n +
				"D=M" + n +
				"A=A+1" + n +
				"D=D-M" + n + // arg1 - arg2
				"@BOOLEANTRUE" + n +
				"D;J" + command + n +
				"@SP" + n +
				"A=M" + n +
				"M=0" + n + // false
				"@BOOLEANEND" + n +
				"0;JMP" + n +
				"(BOOLEANTRUE)" + n +
				"@SP" + n +
				"A=M" + n +
				"M=-1" + n + // true
				"(BOOLEANEND)" + n +
				"@SP" + n +
				"M=M+1" + n;
		bw.write(code);
	}

	void writePushPop(Command command, String segment, int index) throws IOException {
		if (command == C_PUSH) {
			// assume constants only
			String code =
					"@" + index + n +
					"D=A" + n +
					"@SP" + n +
					"A=M" + n +
					"M=D" + n +
					"@SP" + n +
					"M=M+1" + n;
			bw.write(code);
		}
	}

	void close() throws IOException {
		bw.close();
	}
}
