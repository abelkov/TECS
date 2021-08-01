// Start code gen: init SP to 256
@256
D=A
@SP
M=D

////////// File StackTest

///// push constant 17

// save index
@17
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 17

// save index
@17
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// eq

@SP
M=M-1
AM=M-1
D=M
A=A+1
// arg1 - arg2
D=D-M
@BOOLEANTRUE0
D;JEQ
@SP
A=M
// 0 == false
M=0
@BOOLEANEND0
0;JMP
(BOOLEANTRUE0)
@SP
A=M
// -1 == true
M=-1
(BOOLEANEND0)
@SP
M=M+1

///// push constant 17

// save index
@17
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 16

// save index
@16
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// eq

@SP
M=M-1
AM=M-1
D=M
A=A+1
// arg1 - arg2
D=D-M
@BOOLEANTRUE1
D;JEQ
@SP
A=M
// 0 == false
M=0
@BOOLEANEND1
0;JMP
(BOOLEANTRUE1)
@SP
A=M
// -1 == true
M=-1
(BOOLEANEND1)
@SP
M=M+1

///// push constant 16

// save index
@16
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 17

// save index
@17
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// eq

@SP
M=M-1
AM=M-1
D=M
A=A+1
// arg1 - arg2
D=D-M
@BOOLEANTRUE2
D;JEQ
@SP
A=M
// 0 == false
M=0
@BOOLEANEND2
0;JMP
(BOOLEANTRUE2)
@SP
A=M
// -1 == true
M=-1
(BOOLEANEND2)
@SP
M=M+1

///// push constant 892

// save index
@892
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 891

// save index
@891
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// lt

@SP
M=M-1
AM=M-1
D=M
A=A+1
// arg1 - arg2
D=D-M
@BOOLEANTRUE3
D;JLT
@SP
A=M
// 0 == false
M=0
@BOOLEANEND3
0;JMP
(BOOLEANTRUE3)
@SP
A=M
// -1 == true
M=-1
(BOOLEANEND3)
@SP
M=M+1

///// push constant 891

// save index
@891
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 892

// save index
@892
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// lt

@SP
M=M-1
AM=M-1
D=M
A=A+1
// arg1 - arg2
D=D-M
@BOOLEANTRUE4
D;JLT
@SP
A=M
// 0 == false
M=0
@BOOLEANEND4
0;JMP
(BOOLEANTRUE4)
@SP
A=M
// -1 == true
M=-1
(BOOLEANEND4)
@SP
M=M+1

///// push constant 891

// save index
@891
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 891

// save index
@891
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// lt

@SP
M=M-1
AM=M-1
D=M
A=A+1
// arg1 - arg2
D=D-M
@BOOLEANTRUE5
D;JLT
@SP
A=M
// 0 == false
M=0
@BOOLEANEND5
0;JMP
(BOOLEANTRUE5)
@SP
A=M
// -1 == true
M=-1
(BOOLEANEND5)
@SP
M=M+1

///// push constant 32767

// save index
@32767
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 32766

// save index
@32766
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// gt

@SP
M=M-1
AM=M-1
D=M
A=A+1
// arg1 - arg2
D=D-M
@BOOLEANTRUE6
D;JGT
@SP
A=M
// 0 == false
M=0
@BOOLEANEND6
0;JMP
(BOOLEANTRUE6)
@SP
A=M
// -1 == true
M=-1
(BOOLEANEND6)
@SP
M=M+1

///// push constant 32766

// save index
@32766
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 32767

// save index
@32767
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// gt

@SP
M=M-1
AM=M-1
D=M
A=A+1
// arg1 - arg2
D=D-M
@BOOLEANTRUE7
D;JGT
@SP
A=M
// 0 == false
M=0
@BOOLEANEND7
0;JMP
(BOOLEANTRUE7)
@SP
A=M
// -1 == true
M=-1
(BOOLEANEND7)
@SP
M=M+1

///// push constant 32766

// save index
@32766
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 32766

// save index
@32766
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// gt

@SP
M=M-1
AM=M-1
D=M
A=A+1
// arg1 - arg2
D=D-M
@BOOLEANTRUE8
D;JGT
@SP
A=M
// 0 == false
M=0
@BOOLEANEND8
0;JMP
(BOOLEANTRUE8)
@SP
A=M
// -1 == true
M=-1
(BOOLEANEND8)
@SP
M=M+1

///// push constant 57

// save index
@57
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 31

// save index
@31
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 53

// save index
@53
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// add

@SP
AM=M-1
D=M
A=A-1
M=D+M

///// push constant 112

// save index
@112
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// sub

@SP
AM=M-1
D=M
A=A-1
M=M-D

///// neg

@SP
A=M-1
M=-M

///// and

@SP
AM=M-1
D=M
A=A-1
M=D&M

///// push constant 82

// save index
@82
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// or

@SP
AM=M-1
D=M
A=A-1
M=D|M

///// not

@SP
A=M-1
M=!M

