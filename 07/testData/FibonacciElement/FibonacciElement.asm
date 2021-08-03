// Start code gen: init SP to 256 and call Sys.init
@256
D=A
@SP
M=D

@Sys.init
0;JMP

////////// File Main

///// function Main.fibonacci 0

(Main.fibonacci)

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

///// push constant 2

// save index
@2
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
@BOOLEANTRUE0
D;JLT
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

///// if-goto IF_TRUE

// pop topmost value
@SP
AM=M-1
D=M

// jump if not zero
@Main.fibonacci$IF_TRUE
D;JNE

///// goto IF_FALSE
@Main.fibonacci$IF_FALSE
0;JMP

///// label IF_TRUE

(Main.fibonacci$IF_TRUE)

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

///// label IF_FALSE

(Main.fibonacci$IF_FALSE)

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

///// push constant 2

// save index
@2
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

///// call Main.fibonacci 1
    
// push <returnAddress>
@Main.fibonacci$ret.0
D=A
@SP
A=M
M=D
@SP
M=M+1

// push LCL
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1

// push ARG
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1

// push THIS
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1

// push THAT
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1

// ARG = SP-5-nArgs
@SP
D=M
@6
D=D-A
@ARG
M=D

// LCL = SP
@SP
D=M
@LCL
M=D

// goto <f>
@Main.fibonacci
0;JMP

// (<returnAddress>)
(Main.fibonacci$ret.0)

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

///// push constant 1

// save index
@1
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

///// call Main.fibonacci 1
    
// push <returnAddress>
@Main.fibonacci$ret.1
D=A
@SP
A=M
M=D
@SP
M=M+1

// push LCL
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1

// push ARG
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1

// push THIS
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1

// push THAT
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1

// ARG = SP-5-nArgs
@SP
D=M
@6
D=D-A
@ARG
M=D

// LCL = SP
@SP
D=M
@LCL
M=D

// goto <f>
@Main.fibonacci
0;JMP

// (<returnAddress>)
(Main.fibonacci$ret.1)

///// add

@SP
AM=M-1
D=M
A=A-1
M=M+D

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

////////// File Sys

///// function Sys.init 0

(Sys.init)

///// push constant 4

// save index
@4
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// call Main.fibonacci 1
    
// push <returnAddress>
@Sys.init$ret.0
D=A
@SP
A=M
M=D
@SP
M=M+1

// push LCL
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1

// push ARG
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1

// push THIS
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1

// push THAT
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1

// ARG = SP-5-nArgs
@SP
D=M
@6
D=D-A
@ARG
M=D

// LCL = SP
@SP
D=M
@LCL
M=D

// goto <f>
@Main.fibonacci
0;JMP

// (<returnAddress>)
(Sys.init$ret.0)

///// label WHILE

(Sys.init$WHILE)

///// goto WHILE
@Sys.init$WHILE
0;JMP

