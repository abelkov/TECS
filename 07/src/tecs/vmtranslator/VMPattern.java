package tecs.vmtranslator;

class VMPattern {
	static final String PUSH = "\\s*push\\s+(\\S+)\\s+(\\d+)\\W*";
	static final String POP = "\\s*pop\\s+(\\S+)\\s+(\\d+)\\W*";
	static final String ARITHMETIC = "\\s*(add|sub)\\W*";

	// more to come
	static final String LABEL = "\\s*(add|sub)\\W*";
	static final String GOTO = "\\s*(add|sub)\\W*";
	static final String IF = "\\s*(add|sub)\\W*";
	static final String FUNCTION = "\\s*(add|sub)\\W*";
	static final String RETURN = "\\s*(add|sub)\\W*";
	static final String CALL = "\\s*(add|sub)\\W*";
}
