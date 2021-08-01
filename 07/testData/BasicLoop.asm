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

///// pop local 0

// save index
@0
D=A

@LCL
D=D+M

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

///// label LOOP_START

(BasicLoop.null$LOOP_START)

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

///// add

@SP
AM=M-1
D=M
A=A-1
M=D+M

///// pop local 0

// save index
@0
D=A

@LCL
D=D+M

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

///// pop argument 0

// save index
@0
D=A

@ARG
D=D+M

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

///// if-goto LOOP_START // If counter > 0, goto LOOP_START

// pop topmost value
@SP
AM=M-1
D=M

// jump if not zero
@BasicLoop.null$LOOP_START // If counter > 0, goto LOOP_START
D;JNE

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

