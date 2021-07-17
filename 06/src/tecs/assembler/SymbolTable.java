package tecs.assembler;

import java.util.HashMap;

class SymbolTable {
	private HashMap<String, Integer> table = new HashMap<>();

	SymbolTable() {
		String[] symbols = {
			"SP", "LCL", "ARG", "THIS", "THAT",
			"R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10",
			"R11", "R12", "R13", "R14", "R15", "SCREEN", "KBD"
		};
		int[] addresses = {
			0, 1, 2, 3, 4,
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
			11, 12, 13, 14, 15, 16384, 24576
		};
		for (int i = 0; i < symbols.length; i++) {
			table.put(symbols[i], addresses[i]);
		}
	}

	void addEntry(String symbol, int address) {
		table.put(symbol, address);
	}

	boolean contains(String symbol) {
		return table.containsKey(symbol);
	}

	int getAddress(String symbol) {
		return table.get(symbol);
	}
}
