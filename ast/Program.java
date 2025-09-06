package ast;
import java.util.List;
import environment.Environment;
import emitter.Emitter;
/**
 * The Program class represents the highest level object in the grammar, and
 * encompasses the entire code for evaluation.
 *
 * @author Anish Jain
 * @version 11/9/23
 */
public class Program
{
    private List<ProcedureDeclaration> functions;
    private List<String> varNames;
    private Statement stat;

    /**
     * Constructor for objects of class Program
     * 
     * @param f the procedures/functions in this Program
     * @param s the Statement/block of Statements to be evaluated  
     * @param vnames all the variable names in this program
     */
    public Program(List<ProcedureDeclaration> f, Statement s, List<String> vnames)
    {
        functions = f;
        stat = s;
        varNames = vnames;
    }

    /**
     * Executes program by executing each procedure declaration and the
     * following statement/block of statements.
     * 
     * @param e the environment in which the Program is executed 
     */
    public void exec(Environment e)
    {
        for (int i = 0; i < functions.size(); i++)
        {
            ProcedureDeclaration funcDec = functions.get(i);
            funcDec.exec(e);
        }
        stat.exec(e);
    }

    /**
     * Emits full Pascal program into MIPS. Defines newline variable, then
     * initializes all declared variables at the top to 0. Finally, writes
     * .text and .main sections, compiles main body of code, and terminates. 
     * 
     * @param filename the filename where output code is stored
     */
    public void compile(String filename)
    {
        Emitter e = new Emitter(filename);
        e.emit("#author Anish Jain");
        e.emit("#version 1.7.24");
        e.emit(".data");
        e.emit("newline: .asciiz \"\\n\" #defining a newline variable");
        for (int i = 0; i < varNames.size(); i++)
        {
            String str = varNames.get(i);
            e.emit("var" + str + ": .word " + 0 + " #initializing to 0");
        }
        e.emit(".text");
        e.emit(".globl main");
        e.emit("main: ");
        stat.compile(e);
        e.emit("li $v0 10");
        e.emit("syscall  #terminate");
    }
}
