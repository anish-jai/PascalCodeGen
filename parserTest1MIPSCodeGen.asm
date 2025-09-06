	#author Anish Jain
	#version 1.7.24
	.data
	newline: .asciiz "\n" #defining a newline variable
	.text
	.globl main
	main: 
	li $v0 6
	subu $sp $sp 4
	sw $v0 ($sp)  # Push onto stack
	li $v0 2
	lw $t0 ($sp)
	addu $sp $sp 4  # Pop off of stack
	mult $v0, $t0
	mflo $v0
	subu $sp $sp 4
	sw $v0 ($sp)  # Push onto stack
	li $v0 3
	lw $t0 ($sp)
	addu $sp $sp 4  # Pop off of stack
	div $t0, $v0
	mflo $v0
	move $a0 $v0    # Move value into $a0 register
	li $v0 1
	syscall
	la $a0 newline
	li $v0 4
	syscall  #prints newline
	li $v0 10
	syscall  #terminate
