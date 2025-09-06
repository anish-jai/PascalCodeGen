	#author Anish Jain
	#version 1.7.24
	.data
	newline: .asciiz "\n" #defining a newline variable
	varx: .word 0 #initializing to 0
	vary: .word 0 #initializing to 0
	varcount: .word 0 #initializing to 0
	.text
	.globl main
	main: 
	li $v0 2
	la $t2 varx
	sw $v0, ($t2) #moves varx to register $t2
	la $t1 varx
	lw $v0 ($t1)
	subu $sp $sp 4
	sw $v0 ($sp)  # Push onto stack
	li $v0 1
	lw $t0 ($sp)
	addu $sp $sp 4  # Pop off of stack
	addu $v0 $v0 $t0
	la $t2 vary
	sw $v0, ($t2) #moves vary to register $t2
	la $t1 varx
	lw $v0 ($t1)
	subu $sp $sp 4
	sw $v0 ($sp)  # Push onto stack
	la $t1 vary
	lw $v0 ($t1)
	lw $t0 ($sp)
	addu $sp $sp 4  # Pop off of stack
	addu $v0 $v0 $t0
	la $t2 varx
	sw $v0, ($t2) #moves varx to register $t2
	la $t1 varx
	lw $v0 ($t1)
	subu $sp $sp 4
	sw $v0 ($sp)  # Push onto stack
	la $t1 vary
	lw $v0 ($t1)
	lw $t0 ($sp)
	addu $sp $sp 4  # Pop off of stack
	mult $v0, $t0
	mflo $v0
	move $a0 $v0    # Move value into $a0 register
	li $v0 1
	syscall
	la $a0 newline
	li $v0 4
	syscall  #prints newline
	la $t1 varx
	lw $v0 ($t1)
	subu $sp $sp 4
	sw $v0 ($sp)  # Push onto stack
	la $t1 vary
	lw $v0 ($t1)
	lw $t1 ($sp)
	addu $sp $sp 4  # Pop off of stack
	ble $t1, $v0, endif1  #Greater than conditional
	la $t1 varx
	lw $v0 ($t1)
	move $a0 $v0    # Move value into $a0 register
	li $v0 1
	syscall
	la $a0 newline
	li $v0 4
	syscall  #prints newline
	la $t1 vary
	lw $v0 ($t1)
	move $a0 $v0    # Move value into $a0 register
	li $v0 1
	syscall
	la $a0 newline
	li $v0 4
	syscall  #prints newline
	endif1:  #jump for the if
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp)  # Push onto stack
	li $v0 14
	lw $t1 ($sp)
	addu $sp $sp 4  # Pop off of stack
	bne $t1, $v0, endif2  #Equal to conditional
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp)  # Push onto stack
	li $v0 14
	lw $t1 ($sp)
	addu $sp $sp 4  # Pop off of stack
	beq $t1, $v0, endif3  #Not equal to conditional
	li $v0 3
	move $a0 $v0    # Move value into $a0 register
	li $v0 1
	syscall
	la $a0 newline
	li $v0 4
	syscall  #prints newline
	endif3:  #jump for the if
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp)  # Push onto stack
	li $v0 14
	lw $t1 ($sp)
	addu $sp $sp 4  # Pop off of stack
	bgt $t1, $v0, endif4  #Less than equal to conditional
	li $v0 4
	move $a0 $v0    # Move value into $a0 register
	li $v0 1
	syscall
	la $a0 newline
	li $v0 4
	syscall  #prints newline
	endif4:  #jump for the if
	endif2:  #jump for the if
	li $v0 15
	subu $sp $sp 4
	sw $v0 ($sp)  # Push onto stack
	li $v0 14
	lw $t1 ($sp)
	addu $sp $sp 4  # Pop off of stack
	ble $t1, $v0, endif5  #Greater than conditional
	li $v0 5
	move $a0 $v0    # Move value into $a0 register
	li $v0 1
	syscall
	la $a0 newline
	li $v0 4
	syscall  #prints newline
	endif5:  #jump for the if
	li $v0 1
	la $t2 varcount
	sw $v0, ($t2) #moves varcount to register $t2
	while6:     #jump for while6
	la $t1 varcount
	lw $v0 ($t1)
	subu $sp $sp 4
	sw $v0 ($sp)  # Push onto stack
	li $v0 15
	lw $t1 ($sp)
	addu $sp $sp 4  # Pop off of stack
	bgt $t1, $v0, endwhile6  #Less than equal to conditional
	la $t1 varcount
	lw $v0 ($t1)
	move $a0 $v0    # Move value into $a0 register
	li $v0 1
	syscall
	la $a0 newline
	li $v0 4
	syscall  #prints newline
	la $t1 varcount
	lw $v0 ($t1)
	subu $sp $sp 4
	sw $v0 ($sp)  # Push onto stack
	li $v0 1
	lw $t0 ($sp)
	addu $sp $sp 4  # Pop off of stack
	addu $v0 $v0 $t0
	la $t2 varcount
	sw $v0, ($t2) #moves varcount to register $t2
	j while6   #return to top of while loop
	endwhile6:   #end of while loop, move forward
	li $v0 10
	syscall  #terminate
