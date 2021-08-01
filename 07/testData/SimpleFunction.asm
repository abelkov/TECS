///// function SimpleFunction.test 2

(SimpleFunction.SimpleFunction.test)
// push 0
@SP
A=M
M=0
@SP
M=M+1

// push 0
@SP
A=M
M=0
@SP
M=M+1

///// push local 0

// save index
@0
D=A

@LCL
D=D+M

// fetch value to push
A=D
D=M

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push local 1

// save index
@1
D=A

@LCL
D=D+M

// fetch value to push
A=D
D=M

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

///// not

@SP
A=M-1
M=!M

///// push argument 0

// save index
@0
D=A

@ARG
D=D+M

// fetch value to push
A=D
D=M

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

///// push argument 1

// save index
@1
D=A

@ARG
D=D+M

// fetch value to push
A=D
D=M

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

///// return

// frame = LCL
@LCL
D=M
@R13
M=D

// retAddr = *(frame-5)
@5
D=D-A
A=D
D=M
@R14
M=D

// *ARG = pop()
@SP
M=M-1
D=M
A=D
D=M
@ARG
A=M
M=D

// SP = ARG+1
@ARG
D=M+1
@SP
M=D

// THAT = *(frame-1)
@R13
D=M
A=D-1
D=M
@THAT
M=D

// THIS = *(frame-2)
@R13
D=M
@2
A=D-A
D=M
@THIS
M=D
            
// ARG = *(frame-3)
@R13
D=M
@3
A=D-A
D=M
@ARG
M=D

// LCL = *(frame-4)
@R13
D=M
@4
A=D-A
D=M
@LCL
M=D

// goto retAddr
@R14
D=M
A=D
0;JMP

