package ast;
import environment.*;
import emitter.Emitter;

/**
 * The If class is a Statement that is useful for executing simple 
 * "If...Then" statements.
 *
 * @author Anish Jain
 * @version 10.19.23
 */
public class If extends Statement
{
    private Conditional cond;
    private Statement stat;

    /**
     * Constructor for objects of class If
     * 
     * @param c the Conditional that will determine whether the If is executed
     * @param s the statement to be executed if the conditional is deemed true
     */
    public If(Conditional c, Statement s)
    {
        cond = c;
        stat = s;
    }

    /**
     * Executes the If statement by checking if the conditional c is true.
     * If true, executes the following "then" statement, otherwise does
     * nothing.
     * 
     * 
     * @param e the environment where the statements are being executed
     */
    @Override
    public void exec(Environment e)
    {
        int tf = cond.eval(e);
        if(tf == 1 && stat != null)
        {
            stat.exec(e);
        }
    }

    /**
     * Compiles if by compiling conditional. Compiles statement if
     * cond is true, otherwise jumps to a unique label.
     * 
     * 
     * @param e The emitter writing the code
     */
    public void compile(Emitter e)
    {
        int id = e.nextLabelID();
        cond.compile(e, "endif" + id);
        stat.compile(e);
        e.emit("endif" + id + ":  #jump for the if");
    }

}
