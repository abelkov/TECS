////////// File SimpleAdd

///// push constant 7

// save index
@7
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 8

// save index
@8
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
M=M+D

