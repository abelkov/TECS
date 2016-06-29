package tecs.assembler;

class AsmPattern {
		// @100 or @varname
	static final String AT = "\\s*@(\\S+).*";

	// 100
	static final String NUMBER = "^\\d+.*";

	// D=A or D=A;JMP or A;JMP (COMP part not optional)
	static final String COMP = "\\s*([DMA]{1,3})?\\s*=?\\s*([DMA\\-+&\\|!01]{1,3})\\s*;?\\s*([A-Z]{3})?.*";

	// (LABELNAME)
	static final String LABEL = "\\s*\\((\\S+)\\).*";
}
