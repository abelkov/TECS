////////// File Sys

///// function Sys.init 0

(Sys.init)

///// call Sys.main 0

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
@5
D=D-A
@ARG
M=D

// LCL = SP
@SP
D=M
@LCL
M=D

// goto <f>
@Sys.main
0;JMP

// (<returnAddress>)
(Sys.init$ret.0)

///// pop temp 1

// save index
@1
D=A

@5
D=D+A

// save destination address into temporary variable
@R13
M=D

// decrement SP
@SP
M=M-1

// fetch value to pop
A=M
D=M

// store value at destination address
@R13
A=M
M=D

///// label LOOP

(Sys.Sys.init$LOOP)

///// goto LOOP

// goto
@Sys.Sys.init$LOOP
0;JMP

///// function Sys.main 0

(Sys.main)

///// push constant 123

// save index
@123
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// call Sys.add12 1

// push <returnAddress>
@Sys.main$ret.0
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
@Sys.add12
0;JMP

// (<returnAddress>)
(Sys.main$ret.0)

///// pop temp 0

// save index
@0
D=A

@5
D=D+A

// save destination address into temporary variable
@R13
M=D

// decrement SP
@SP
M=M-1

// fetch value to pop
A=M
D=M

// store value at destination address
@R13
A=M
M=D

///// push constant 246

// save index
@246
D=A

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

///// function Sys.add12 3

(Sys.add12)

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

// push 0
@SP
A=M
M=0
@SP
M=M+1

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

///// push constant 12

// save index
@12
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

