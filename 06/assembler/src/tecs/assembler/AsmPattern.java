package tecs.assembler;

class AsmPattern {
	static final String AT = "\\s*@(\\S+).*";
	static final String NUMBER = "^\\d+.*";
	static final String DEST = "\\s*([DMA]{1,3})=([DMA\\-+&\\|!01]{1,3}).*";
	static final String JUMP = "\\s*(\\S+);([A-Z]{3}).*";
	static final String LABEL = "\\s*\\((\\S+)\\).*";
}
