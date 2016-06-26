package tecs.assembler;

class AsmPattern {
    static final String at = "\\s*@(.+)\\s*";
    static final String atNumber = "\\s*@(\\d+)\\s*";
    static final String atSymbol ="\\s*@([a-zA-Z_.$:]+[0-9a-zA-Z_.$:]*)\\s*";
    static final String dest = "\\s*([D|M|A]*)=([D|M|A|\\-|+|&|\\||!|0|1]*).*";
    static final String jump = "\\s*(.*);([a-zA-Z]*).*";
    static final String label = "^\\s*\\((.*)\\)\\s*";
    static final String empty = "^\\s*$";
}
