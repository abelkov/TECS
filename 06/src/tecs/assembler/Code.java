package tecs.assembler;

import java.util.HashMap;

class Code {
	private final HashMap<String, String> destTable = new HashMap<>();
	private final HashMap<String, String> jumpTable = new HashMap<>();
	private final HashMap<String, String> compTable = new HashMap<>();

	Code() {
		String[] destMnemonic = {null, "M", "D", "MD", "A", "AM", "AD", "AMD"};
		String[] destBinary = {"000", "001", "010", "011", "100", "101", "110", "111"};
		for (int i = 0; i < destMnemonic.length; i++) {
			destTable.put(destMnemonic[i], destBinary[i]);
		}

		String[] jumpMnemonic = {null, "JGT", "JEQ", "JGE", "JLT", "JNE", "JLE", "JMP"};
		String[] jumpBinary = {"000", "001", "010", "011", "100", "101", "110", "111"};
		for (int i = 0; i < jumpMnemonic.length; i++) {
			jumpTable.put(jumpMnemonic[i], jumpBinary[i]);
		}

		String[] compMnemonic = {
				"0", "1", "-1", "D", "A", "!D", "!A", "-D", "-A",
				"D+1", "A+1", "D-1", "A-1", "D+A", "D-A", "A-D", "D&A", "D|A",
				"M", "!M", "-M", "M+1", "M-1", "D+M", "D-M", "M-D", "D&M", "D|M"
		};
		String[] compBinary = {
				"0101010", "0111111", "0111010", "0001100", "0110000", "0001101", "0110001", "0001111",
				"0110011", "0011111", "0110111", "0001110", "0110010", "0000010", "0010011", "0000111",
				"0000000", "0010101", "1110000", "1110001", "1110011", "1110111", "1110010", "1000010",
				"1010011", "1000111", "1000000", "1010101"
		};
		for (int i = 0; i < compMnemonic.length; i++) {
			compTable.put(compMnemonic[i], compBinary[i]);
		}
	}

	String dest(String mnemonic) {
		return destTable.get(mnemonic);
	}

	String jump(String mnemonic) {
		return jumpTable.get(mnemonic);
	}

	String comp(String mnemonic) {
		return compTable.get(mnemonic);
	}
}
