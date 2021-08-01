// Start code gen: init SP to 256
@256
D=A
@SP
M=D

////////// File StaticTest

///// push constant 111

// save index
@111
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 333

// save index
@333
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 888

// save index
@888
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// pop static 8

@StaticTest.8
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

///// pop static 3

@StaticTest.3
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

///// pop static 1

@StaticTest.1
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

///// push static 3

@StaticTest.3
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

@StaticTest.1
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

///// push static 8

@StaticTest.8
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

///// add

@SP
AM=M-1
D=M
A=A-1
M=D+M

