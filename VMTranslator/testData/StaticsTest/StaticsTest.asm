// Start code gen: init SP to 256 and call Sys.init
@256
D=A
@SP
M=D

@Sys.init
0;JMP

////////// File Class1

///// function Class1.set 0

(Class1.set)

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

///// pop static 0

@Class1.0
D=A

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

///// pop static 1

@Class1.1
D=A

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

///// push constant 0

// save index
@0
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

///// function Class1.get 0

(Class1.get)

///// push static 0

@Class1.0
D=A

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

///// push static 1

@Class1.1
D=A

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

////////// File Sys

///// function Sys.init 0

(Sys.init)

///// push constant 6

// save index
@6
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

///// call Class1.set 2
    
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
@7
D=D-A
@ARG
M=D

// LCL = SP
@SP
D=M
@LCL
M=D

// goto <f>
@Class1.set
0;JMP

// (<returnAddress>)
(Sys.init$ret.0)

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

///// push constant 23

// save index
@23
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 15

// save index
@15
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// call Class2.set 2
    
// push <returnAddress>
@Sys.init$ret.1
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
@7
D=D-A
@ARG
M=D

// LCL = SP
@SP
D=M
@LCL
M=D

// goto <f>
@Class2.set
0;JMP

// (<returnAddress>)
(Sys.init$ret.1)

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

///// call Class1.get 0
    
// push <returnAddress>
@Sys.init$ret.2
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
@Class1.get
0;JMP

// (<returnAddress>)
(Sys.init$ret.2)

///// call Class2.get 0
    
// push <returnAddress>
@Sys.init$ret.3
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
@Class2.get
0;JMP

// (<returnAddress>)
(Sys.init$ret.3)

///// label WHILE

(Sys.init$WHILE)

///// goto WHILE
@Sys.init$WHILE
0;JMP

////////// File Class2

///// function Class2.set 0

(Class2.set)

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

///// pop static 0

@Class2.0
D=A

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

///// pop static 1

@Class2.1
D=A

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

///// push constant 0

// save index
@0
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

///// function Class2.get 0

(Class2.get)

///// push static 0

@Class2.0
D=A

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

///// push static 1

@Class2.1
D=A

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

