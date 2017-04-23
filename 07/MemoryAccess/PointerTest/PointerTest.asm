
///// push constant 3030
@3030
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// pop pointer 0
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-1
M=D
// save segment cell address
@3
D=A
@0
D=D+A

@push-cell-address-1
M=D
// pop stack value to segment
@push-value-1
D=M
@push-cell-address-1
A=M
M=D

///// push constant 3040
@3040
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// pop pointer 1
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-2
M=D
// save segment cell address
@3
D=A
@1
D=D+A

@push-cell-address-2
M=D
// pop stack value to segment
@push-value-2
D=M
@push-cell-address-2
A=M
M=D

///// push constant 32
@32
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// pop this 2
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-3
M=D
// save segment cell address
@THIS
D=M
@2
D=D+A

@push-cell-address-3
M=D
// pop stack value to segment
@push-value-3
D=M
@push-cell-address-3
A=M
M=D

///// push constant 46
@46
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// pop that 6
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-4
M=D
// save segment cell address
@THAT
D=M
@6
D=D+A

@push-cell-address-4
M=D
// pop stack value to segment
@push-value-4
D=M
@push-cell-address-4
A=M
M=D

///// push pointer 0
@3
D=A
@0
A=D+A
D=M

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// push pointer 1
@3
D=A
@1
A=D+A
D=M

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// add
@SP
A=M
A=A-1
D=M
A=A-1
M=M+D
D=A+1
@SP
M=D

///// push this 2
@THIS
D=M
@2
A=D+A
D=M

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// sub
@SP
A=M
A=A-1
D=M
A=A-1
M=M-D
D=A+1
@SP
M=D

///// push that 6
@THAT
D=M
@6
A=D+A
D=M

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// add
@SP
A=M
A=A-1
D=M
A=A-1
M=M+D
D=A+1
@SP
M=D
