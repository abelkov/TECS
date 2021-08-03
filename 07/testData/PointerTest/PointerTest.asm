////////// File PointerTest

///// push constant 3030

// save index
@3030
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// pop pointer 0

// save index
@0
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

///// push constant 3040

// save index
@3040
D=A

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

///// push constant 32

// save index
@32
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// pop this 2

// save index
@2
D=A

@THIS
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

///// push constant 46

// save index
@46
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// pop that 6

// save index
@6
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

///// push pointer 0

// save index
@0
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

///// add

@SP
AM=M-1
D=M
A=A-1
M=D+M

///// push this 2

// save index
@2
D=A

@THIS
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

///// push that 6

// save index
@6
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

