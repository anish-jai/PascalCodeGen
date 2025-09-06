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
 * The ParserTester class tests the Parser class using one a provided input file.
 *
 * @author Anish Jain
 * @version 10.19.23
 */
public class ParserTester
{
    /**
     * Constructor for objects of class ParserTester
     * 
     * @param args command line arguments
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
        Environment e = new Environment(null);
        
        (p.parseProgram()).exec(e);
    }
}
