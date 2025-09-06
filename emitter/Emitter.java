package emitter;
import java.io.*;
/**
 * The Emitter class allows us to emit code from Pascal into MIPS.
 * 
 * 
 * @author Anish Jain
 * @version 1.7.24
 */

public class Emitter
{
    private PrintWriter out;
    private int curlabelId;
    /**
     * Creates an Emitter object that generates PASCAL code into MIPS in a 
     * new output file
     * 
     * @param outputFileName the name of the output file that will be generated
     */
    public Emitter(String outputFileName)
    {
        curlabelId = 0;
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prints one line of code to file (with non-labels indented)
     * 
     * @param code the code to emit
     */
    public void emit(String code)
    {
        if (!code.endsWith(":"))
        {
            code = "\t" + code;
        }
        out.println(code);
        //System.out.println(code);
    }

    /**
     * Closes the file, should be called after all calls to emit.
     * 
     */
    public void close()
    {
        out.close();
    }

    /**
     * Makes a newline in MIPS
     * 
     */
    public void nline()
    {
        emit("la $a0 newline");
        emit("li $v0 4");
        emit("syscall  #prints newline");
    }

    /**
     * Push the value in register onto the stack
     * @param r the register to extract the value from
     */
    public void emitPush(String r)
    {
        emit("subu $sp $sp 4");
        emit("sw " + r +  " ($sp)  # Push onto stack");
    }

    /**
     * Pops the value on the top of the stack into register
     * @param r the register to put the value at the top of the stack
     */
    public void emitPop(String r)
    {
        emit("lw " + r + " ($sp)");
        emit("addu $sp $sp 4  # Pop off of stack");
    }

    /**
     * Gives current label ID for if Statement branch to make sure each
     * branch is unique.
     * 
     * @return int current if statement branch ID
     */
    public int nextLabelID()
    {
        return ++curlabelId;
    }
}