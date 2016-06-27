package tecs.assembler;

class AsmPattern {
    static final String AT = "@(\\S+)";
    static final String AT_NUMBER = "@(\\d+)";
    static final String AT_SYMBOL ="\\s*@([a-zA-Z_.$:]+[0-9a-zA-Z_.$:]*)\\s*";
    static final String DEST = "([DMA])=([DMA\\-+&\\|!01]{1,3})";
    static final String JUMP = "\\s*(.*);([a-zA-Z]*).*";
    static final String LABEL = "^\\s*\\((.*)\\)\\s*";
    static final String EMPTY = "^\\s*$";
}
