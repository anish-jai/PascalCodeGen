package scanner;

import java.io.*;
/**
 * Tester for the Scanner class.
 * 
 * @author  Anish Jain
 * @author Ms. Datar
 *
 * @version 9/5/23
 */
public class ScannerTester 
{
    /**
     * Main method for testing Scanner class.
     *
     * @param args arguments from the command line
     */
    public static void main(String [ ] args) throws FileNotFoundException, ScanErrorException
    {
        String lexeme = "";
        System.out.println("\n \n \n");
        InputStream instream = new FileInputStream("/Users/anishjain/Downloads/ScannerTest.txt");
        //instream = new FileInputStream("/Users/anishjain/Downloads/ScannerTestAdvanced.txt");
        Scanner lex = new Scanner(instream);
        while (lex.hasNext())
        {
            System.out.println(lex.nextToken());
        }
    }
}
