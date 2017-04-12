
// push constant 17
@17
D=A
@SP
M=M+1
A=M-1
M=D

// push constant 17
@17
D=A
@SP
M=M+1
A=M-1
M=D

// eq
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@EQ-1
D;JEQ
@SP
A=M-1
M=0
@EQ-1-END
0;JMP
(EQ-1)
@SP
A=M-1
M=-1
(EQ-1-END)

// push constant 17
@17
D=A
@SP
M=M+1
A=M-1
M=D

// push constant 16
@16
D=A
@SP
M=M+1
A=M-1
M=D

// eq
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@EQ-2
D;JEQ
@SP
A=M-1
M=0
@EQ-2-END
0;JMP
(EQ-2)
@SP
A=M-1
M=-1
(EQ-2-END)

// push constant 16
@16
D=A
@SP
M=M+1
A=M-1
M=D

// push constant 17
@17
D=A
@SP
M=M+1
A=M-1
M=D

// eq
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@EQ-3
D;JEQ
@SP
A=M-1
M=0
@EQ-3-END
0;JMP
(EQ-3)
@SP
A=M-1
M=-1
(EQ-3-END)

// push constant 892
@892
D=A
@SP
M=M+1
A=M-1
M=D

// push constant 891
@891
D=A
@SP
M=M+1
A=M-1
M=D

// lt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@LT-4
D;JLT
@SP
A=M-1
M=0
@LT-4-END
0;JMP
(LT-4)
@SP
A=M-1
M=-1
(LT-4-END)

// push constant 891
@891
D=A
@SP
M=M+1
A=M-1
M=D

// push constant 892
@892
D=A
@SP
M=M+1
A=M-1
M=D

// lt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@LT-5
D;JLT
@SP
A=M-1
M=0
@LT-5-END
0;JMP
(LT-5)
@SP
A=M-1
M=-1
(LT-5-END)

// push constant 891
@891
D=A
@SP
M=M+1
A=M-1
M=D

// push constant 891
@891
D=A
@SP
M=M+1
A=M-1
M=D

// lt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@LT-6
D;JLT
@SP
A=M-1
M=0
@LT-6-END
0;JMP
(LT-6)
@SP
A=M-1
M=-1
(LT-6-END)

// push constant 32767
@32767
D=A
@SP
M=M+1
A=M-1
M=D

// push constant 32766
@32766
D=A
@SP
M=M+1
A=M-1
M=D

// gt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@GT-7
D;JGT
@SP
A=M-1
M=0
@GT-7-END
0;JMP
(GT-7)
@SP
A=M-1
M=-1
(GT-7-END)

// push constant 32766
@32766
D=A
@SP
M=M+1
A=M-1
M=D

// push constant 32767
@32767
D=A
@SP
M=M+1
A=M-1
M=D

// gt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@GT-8
D;JGT
@SP
A=M-1
M=0
@GT-8-END
0;JMP
(GT-8)
@SP
A=M-1
M=-1
(GT-8-END)

// push constant 32766
@32766
D=A
@SP
M=M+1
A=M-1
M=D

// push constant 32766
@32766
D=A
@SP
M=M+1
A=M-1
M=D

// gt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@GT-9
D;JGT
@SP
A=M-1
M=0
@GT-9-END
0;JMP
(GT-9)
@SP
A=M-1
M=-1
(GT-9-END)

// push constant 57
@57
D=A
@SP
M=M+1
A=M-1
M=D

// push constant 31
@31
D=A
@SP
M=M+1
A=M-1
M=D

// push constant 53
@53
D=A
@SP
M=M+1
A=M-1
M=D

// add
@SP
A=M
A=A-1
D=M
A=A-1
M=M+D
D=A+1
@SP
M=D

// push constant 112
@112
D=A
@SP
M=M+1
A=M-1
M=D

// sub
@SP
A=M
A=A-1
D=M
A=A-1
M=M-D
D=A+1
@SP
M=D

// neg
@SP
A=M-1
M=-M

// and
@SP
A=M
A=A-1
D=M
A=A-1
M=M&D
D=A+1
@SP
M=D

// push constant 82
@82
D=A
@SP
M=M+1
A=M-1
M=D

// or
@SP
A=M
A=A-1
D=M
A=A-1
M=M|D
D=A+1
@SP
M=D

// not
@SP
A=M-1
M=!M
