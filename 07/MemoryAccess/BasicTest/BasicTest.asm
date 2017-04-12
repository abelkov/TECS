
///// push constant 10
@10
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// pop local 0
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-1
M=D
// save segment cell address
@LCL
D=M
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

///// push constant 21
@21
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// push constant 22
@22
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// pop argument 2
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-2
M=D
// save segment cell address
@ARG
D=M
@2
D=D+A

@push-cell-address-2
M=D
// pop stack value to segment
@push-value-2
D=M
@push-cell-address-2
A=M
M=D

///// pop argument 1
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-3
M=D
// save segment cell address
@ARG
D=M
@1
D=D+A

@push-cell-address-3
M=D
// pop stack value to segment
@push-value-3
D=M
@push-cell-address-3
A=M
M=D

///// push constant 36
@36
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// pop this 6
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-4
M=D
// save segment cell address
@THIS
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

///// push constant 42
@42
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// push constant 45
@45
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// pop that 5
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-5
M=D
// save segment cell address
@THAT
D=M
@5
D=D+A

@push-cell-address-5
M=D
// pop stack value to segment
@push-value-5
D=M
@push-cell-address-5
A=M
M=D

///// pop that 2
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-6
M=D
// save segment cell address
@THAT
D=M
@2
D=D+A

@push-cell-address-6
M=D
// pop stack value to segment
@push-value-6
D=M
@push-cell-address-6
A=M
M=D

///// push constant 510
@510
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// pop temp 6
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-7
M=D
// save segment cell address
@5
D=A
@6
D=D+A

@push-cell-address-7
M=D
// pop stack value to segment
@push-value-7
D=M
@push-cell-address-7
A=M
M=D

///// push local 0
@LCL
D=M
@0
A=D+A
D=M

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// push that 5
@THAT
D=M
@5
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

///// push argument 1
@ARG
D=M
@1
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

///// push this 6
@THIS
D=M
@6
A=D+A
D=M

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// push this 6
@THIS
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

///// push temp 6
@5
D=A
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
