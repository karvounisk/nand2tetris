// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

// a = 4000
// for 4000 <= a < 6000
// if (keyboard > 0) screen = 0 else screen = 1


(START)
    @SCREEN
    D=A
    @i
    M=D
(LOOP)
    // if (i=6000) goto START
    @i
    D=M
    @KBD
    D=A-D
    @START
    D;JEQ
    // if (KBD > 0) goto BLACK
    @KBD
    D=M
    @BLACK
    D;JGT
    // else draw white
    @i
    A=M
    M=0
    // i = i + 1
    @i
    M=M+1
    @LOOP
    0;JMP
(BLACK)
    @i
    A=M
    M=-1
    // i = i + 1
    @i
    M=M+1
    @LOOP
    0;JMP
