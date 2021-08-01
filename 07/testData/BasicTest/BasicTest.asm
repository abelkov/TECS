// Start code gen: init SP to 256
@256
D=A
@SP
M=D

////////// File BasicTest

///// push constant 10

// save index
@10
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

///// push constant 21

// save index
@21
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 22

// save index
@22
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// pop argument 2

// save index
@2
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

///// pop argument 1

// save index
@1
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

///// push constant 36

// save index
@36
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// pop this 6

// save index
@6
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

///// push constant 42

// save index
@42
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// push constant 45

// save index
@45
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// pop that 5

// save index
@5
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

///// push constant 510

// save index
@510
D=A

// push
@SP
A=M
M=D

// increment SP
@SP
M=M+1

///// pop temp 6

// save index
@6
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

///// push that 5

// save index
@5
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

///// push this 6

// save index
@6
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

///// push this 6

// save index
@6
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

///// add

@SP
AM=M-1
D=M
A=A-1
M=D+M

///// sub

@SP
AM=M-1
D=M
A=A-1
M=M-D

///// push temp 6

// save index
@6
D=A

@5
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

