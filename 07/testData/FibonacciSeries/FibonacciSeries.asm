////////// File FibonacciSeries

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

///// pop pointer 1

// save index
@1
D=A

@3
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

///// pop that 0

// save index
@0
D=A

@THAT
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

///// pop that 1

// save index
@1
D=A

@THAT
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

///// label MAIN_LOOP_START

(FibonacciSeries.null$MAIN_LOOP_START)

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

///// if-goto COMPUTE_ELEMENT // if num_of_elements > 0, goto COMPUTE_ELEMENT

// pop topmost value
@SP
AM=M-1
D=M

// jump if not zero
@FibonacciSeries.null$COMPUTE_ELEMENT // if num_of_elements > 0, goto COMPUTE_ELEMENT
D;JNE

///// goto END_PROGRAM        // otherwise, goto END_PROGRAM

// goto
@FibonacciSeries.null$END_PROGRAM        // otherwise, goto END_PROGRAM
0;JMP

///// label COMPUTE_ELEMENT

(FibonacciSeries.null$COMPUTE_ELEMENT)

///// push that 0

// save index
@0
D=A

@THAT
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

///// push that 1

// save index
@1
D=A

@THAT
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

///// pop that 2

// save index
@2
D=A

@THAT
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

///// push pointer 1

// save index
@1
D=A

@3
D=D+A

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

///// add

@SP
AM=M-1
D=M
A=A-1
M=D+M

///// pop pointer 1

// save index
@1
D=A

@3
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

///// goto MAIN_LOOP_START

// goto
@FibonacciSeries.null$MAIN_LOOP_START
0;JMP

///// label END_PROGRAM

(FibonacciSeries.null$END_PROGRAM)

