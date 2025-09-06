package parser;
import scanner.Scanner;
import scanner.ScanErrorException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import ast.Program;
import environment.*;
import emitter.Emitter;

/**
 * The MIPSCompiler class compiles programs to MIPS assembly code.
 *
 * @author Anish Jain
 * @version 09.04.25
 */
public class MIPSCompiler
{
    /**
     * Compiles a program to MIPS assembly
     * 
     * @param args command line arguments - optional filename (without extension)
     */
    public static void main (String [ ] args) throws ScanErrorException, FileNotFoundException
    {
        String filename = "parserTest9"; // default
        
        if (args.length > 0) {
            filename = args[0];
        }
        
        InputStream instream = new FileInputStream(filename + ".txt");
        Scanner s = new Scanner(instream);
        Parser p = new Parser(s);
        
        (p.parseProgram()).compile(filename + "MIPSCodeGen.asm");
        System.out.println("Compiled " + filename + ".txt to " + filename + "MIPSCodeGen.asm");
    }
}