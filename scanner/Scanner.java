package scanner;

import java.io.*;
import java.util.regex.Pattern;

/**
 * Scanner is a simple scanner for Compilers and Interpreters
 * 
 * @author Anish Jain
 * @author Ms. Datar
 * @version 9/5/23
 *
 */
public class Scanner
{
    private PushbackReader in;
    private char currentChar;
    private boolean eof;
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * 
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new PushbackReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * 
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new PushbackReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /**
     * The getNextChar method moves currentChar to the next one in the file.
     * If there is no next char, sets eof variable to true.
     * 
     */
    private void getNextChar()
    {
        try
        {
            int inval = in.read();
            if(inval == -1)
            {
                eof = true;
            }
            else
            {
                currentChar = (char) inval;
            }
        } catch(IOException exception)
        {
            System.out.println("EXCEPTION CAUGHT at getNextChar()");
            System.exit(0);
        }
    }

    /**
     * The eat method moves the currentChar to the next position, otherwise
     * throws a new ScanErrorException.
     * 
     * @param expected the char that is expected to match the currentChar
     */
    private void eat(char expected) throws ScanErrorException
    {
        if(expected == currentChar)
        {
            getNextChar();
        }
        else
        {
            throw new ScanErrorException("Illegal character: expected " + 
                expected + "but instead found" + currentChar);
        }
    }

    /**
     * The hasNext returns whether or not the file has another character.
     * 
     * @return true if not end of file and not a '.' char; otherwise
     *  return false
     */
    public boolean hasNext()
    {
        return !eof && !(currentChar == '.');
    }

    /**
     * The nextToken method returns the next token, with the token name 
     * and value. Throws ScanErrorException if scan doesn't work properly.
     * 
     * @return String a Token with the token name and associated lexeme.
     */
    public String nextToken() throws ScanErrorException
    {
        while(hasNext() && isWhiteSpace(currentChar))
        {
            eat(currentChar);
        }
        if(!hasNext())
        {
            if(currentChar == '.')
            {
                return ".\nEND";
            }
            return "END";
        }
        if(isComment())
        {
            eat(currentChar);
            scanComment();
            return "";
        }
        else if(isDigit(currentChar))
        {
            return scanNumber();
            //return ("(Number, " + scanNumber() + ")" + "\n");
        }
        else if(isLetter(currentChar))
        {
            return scanIdentifier();
            //return ("(Identifier, " + scanIdentifier() + ")" + "\n");
        }
        else if(isOperator(currentChar))
        {
            return scanOperand();
            //return ("(Operand, " + scanOperand() + ")" + "\n");
        }

        else 
        {
            throw new ScanErrorException("This token is invalid: " + currentChar + "...");
        }
    }    
    
    /**
     * The isDigit method returns whether or not the inputted char is
     * a digit or not.
     * 
     * @param dig the char to be checked
     * 
     * @return true if dig is a digit; otherwise,
     *  return false
     */
    public static boolean isDigit(char dig)
    {
        return (dig - '0' >= 0 && dig - '0' <= 9); 
    }

    /**
     * The isComment method returns whether or not the scanner is currently
     * at a double front slash, indicating a comment.
     * 
     * @return true if there is a comment; otherwise
     *  return false
     */
    public boolean isComment()
    {
        if (currentChar == '/')
        {
            try
            {
                if(hasNext())
                {
                    int a = in.read();
                    if(a!='/')
                    {
                        in.unread(a);
                        return false;
                    }
                    return true;
                }
            } catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
        return false;
    }

    /**
     * The scanComment method eliminates the entire rest of the line of 
     * code starting with a double slash, since the scanner must remove
     * any single-line commments.
     * 
     * @return String all the eliminated chars.
     */
    private String scanComment() throws ScanErrorException
    {
        String ret = "";
        while((currentChar == '.' || hasNext()) && currentChar!='\n' && currentChar!='\r')
        {
            ret += currentChar;
            eat(currentChar);
        }
        return "";
    }

    /**
     * The isLetter method returns whether or not the inputted char is
     * a letter or not.
     * 
     * @param lett the char to be checked
     * 
     * @return true if lett is a letter; otherwise,
     *  return false
     */
    public static boolean isLetter(char lett)
    {
        return (lett >= 'A' && lett <= 'Z') || (lett >= 'a' && lett <= 'z');
    }

    /**
     * The isWhiteSpace method returns whether or not the inputted char is
     * some form of a whitespace or not.
     * 
     * @param whit the char to be checked
     * 
     * @return true if whit is a whitespace; otherwise,
     *  return false
     */
    public static boolean isWhiteSpace(char whit)
    {
        return (whit == '\n' || whit == ' ' || whit == '\t' || whit == '\r');
    }

    /**
     * The isOperator method returns whether or not the inputted char is
     * an operator or not.
     * 
     * @param op the char to be checked
     * 
     * @return true if op is an operator; otherwise,
     *  return false
     */
    public static boolean isOperator(char op)
    {
        return Pattern.matches("[=+\\-*/%();:><,]", op + "");
    }

    /**
     * This method scans in digits as a complete number until a non-digit
     * is detected. Can also throw a ScanErrorException if necessary.
     * 
     * @return String all the digits in the scanned number
     * 
     */
    private String scanNumber() throws ScanErrorException
    {
        String num = "";
        if(!isDigit(currentChar))
        {
            throw new ScanErrorException("Looked for Number found error");
        }
        while(hasNext() && isDigit(currentChar)) 
        {
            num += currentChar;
            eat(currentChar);
        }
        return num;
    }

    /**
     * This method scans in letters/digits as a complete identifier until a
     * different character is detected. Can also throw a 
     * ScanErrorException if necessary.
     * 
     * @return String with all the characters of the scanned identifier
     * 
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String id = "";
        if(!isLetter(currentChar))
        {
            throw new ScanErrorException("Looked for Identifier found error");
        }
        while(hasNext() && (isLetter(currentChar) || isDigit(currentChar)))
        {
            id += currentChar;
            eat(currentChar);
        }
        return id;
    }

    /**
     * This method scans in an operand. Can also throw a ScanErrorException 
     * if necessary.
     * 
     * @return String with the scanned operand
     * 
     */
    private String scanOperand() throws ScanErrorException
    {
        if(!isOperator(currentChar))
        {
            throw new ScanErrorException("Looked for Operator found error");
        }
        
        String chcopy = "";
        chcopy += currentChar;
        eat(currentChar);
        if((((chcopy.equals("<") || chcopy.equals(">") || chcopy.equals(":"))) && 
             (currentChar == '=')) || (chcopy.equals("<") && currentChar == '>'))
        {
            chcopy+=currentChar;
            eat(currentChar);
            return chcopy;
        }
        return chcopy;
    }
}
