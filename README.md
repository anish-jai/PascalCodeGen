# Pascal Language Compiler and Interpreter

A Java-based compiler and interpreter for a Pascal-like programming language that supports compilation to MIPS assembly code.

## Features

- **Interpreter Mode**: Execute programs directly with immediate output
- **MIPS Compiler**: Generate MIPS assembly code for hardware execution
- Supports variables, arithmetic operations, control structures (IF/THEN, WHILE), and procedure calls

## Compilation

Compile all source files:
```bash
javac parser/*.java ast/*.java scanner/*.java environment/*.java emitter/*.java
```

## Usage

### Running the Interpreter
```bash
java parser.ParserTester [filename]
```
- Executes the program and displays output immediately
- Default file: `parserTest9.txt` (if no filename provided)
- Input files should have `.txt` extension (omit extension in command)

### Running the MIPS Compiler
```bash
java parser.MIPSCompiler [filename] 
```
- Compiles program to MIPS assembly code
- Generates `[filename]MIPSCodeGen.asm` output file
- Default file: `parserTest9.txt` (if no filename provided)

## How It Works

### 1. Scanner Phase
- Tokenizes input source code into lexical tokens
- Handles keywords (`VAR`, `BEGIN`, `END`, `IF`, `WHILE`, etc.), operators, identifiers, and numbers

### 2. Parser Phase  
- Uses recursive descent parsing to build an Abstract Syntax Tree (AST)
- Implements context-free grammar rules for the Pascal-like language
- Creates nodes for statements, expressions, and declarations

### 3. Execution/Compilation Phase
**Interpreter Mode:**
- Traverses AST and executes nodes directly using visitor pattern
- Maintains runtime environment for variable storage and scope management

**MIPS Compiler Mode:**
- Generates MIPS assembly instructions by traversing AST
- Uses stack-based expression evaluation
- Produces optimized assembly code with proper register allocation

## Language Syntax

```pascal
VAR x, y;
BEGIN
    x := 10;
    y := x + 5;
    WRITELN(y);
    IF x > 5 THEN WRITELN(x);
    WHILE x > 0 DO
    BEGIN
        WRITELN(x);
        x := x - 1;
    END;
END;
.
```

## Example Usage

```bash
# Compile all files
javac parser/*.java ast/*.java scanner/*.java environment/*.java emitter/*.java

# Run interpreter
java parser.ParserTester parserTest1

# Generate MIPS code
java parser.MIPSCompiler parserTest1
```
