package tecs.vmtranslator;

class VMPattern {
	static final String PUSH = "\\s*push\\s+(\\w+)\\s+(\\d+)\\W*";
	static final String POP = "\\s*pop\\s+(\\S+)\\s+(\\d+)\\W*";
	static final String ARITHMETIC = "\\s*(add|sub|neg|eq|gt|lt|and|or|not)\\W*";
	static final String LABEL = "\\s*label\\s+(\\w+)\\W*";
	static final String GOTO = "\\s*goto\\s+(\\w+)\\W*";
	static final String IF = "\\s*if-goto\\s+(\\w+)\\W*";
	static final String FUNCTION = "\\s*function\\s+(\\w+)\\s+(\\d+)\\W*";
	static final String CALL = "\\s*call\\s+(\\w+)\\s+(\\d+)\\W*";
	static final String RETURN = "\\s*return\\W*";
}
