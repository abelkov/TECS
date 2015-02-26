// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.

(LOOP)
  @KBD
  D=M

  @i
  M=0

  @BLACK
  D;JGT

  @WHITE
  0;JMP

(BLACK)
  @i
  D=M

  @SCREEN
  A=A+D
  M=-1

  @i
  MD=M+1

  @8191
  D=D-A

  @LOOP
  D;JGT

  @BLACK
  0;JMP

(WHITE)
  @i
  D=M

  @SCREEN
  A=A+D
  M=0

  @i
  MD=M+1

  @8191
  D=D-A

  @LOOP
  D;JGT

  @WHITE
  0;JMP