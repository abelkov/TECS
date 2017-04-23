
///// push constant 111
@111
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// push constant 333
@333
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// push constant 888
@888
D=A

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// pop static 8
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-1
M=D
// save segment cell address
@vm.8
D=A

@push-cell-address-1
M=D
// pop stack value to segment
@push-value-1
D=M
@push-cell-address-1
A=M
M=D

///// pop static 3
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-2
M=D
// save segment cell address
@vm.3
D=A

@push-cell-address-2
M=D
// pop stack value to segment
@push-value-2
D=M
@push-cell-address-2
A=M
M=D

///// pop static 1
// save stack value and decrement SP
@SP
AM=M-1
D=M
@push-value-3
M=D
// save segment cell address
@vm.1
D=A

@push-cell-address-3
M=D
// pop stack value to segment
@push-value-3
D=M
@push-cell-address-3
A=M
M=D

///// push static 3
@vm.3
D=M

// push value to stack
@SP
M=M+1
A=M-1
M=D

///// push static 1
@vm.1
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

///// push static 8
@vm.8
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
