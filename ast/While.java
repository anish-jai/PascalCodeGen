package ast;
import environment.*;
import emitter.Emitter;

/**
 * The While class is a Statement useful for evaluating simple while loop
 * constructs.
 *
 * @author Anish Jain
 * @version 10.19.23
 */
public class While extends Statement
{
    private Conditional cond;
    private Statement stat;

    /**
     * Constructor for objects of class While
     * 
     * @param c the conditional that will go inside the while loop,
     * determining how many times it loops
     * 
     * @param s the statement that will be executed inside the body of the
     * while loop
     *
     */
    public While(Conditional c, Statement s)
    {
        cond = c;
        stat = s;
    }

    /**
     * Executes the while loop by executing the statement inside until
     * the conditional is false.
     * 
     * 
     * @param e the environment where the while loop is being executed
     */
    @Override
    public void exec(Environment e)
    {
        int tf = cond.eval(e);
        while(tf == 1)
        {
            if(stat != null)
            {
                stat.exec(e);
            }
            tf = cond.eval(e);
        }
    }

    /**
     * Compiles while loop using a a unique label name. As with any while loop,
     * compiles condition, compiles body, then reevaluates condition. Repeats 
     * until condition isn't met.
     * 
     * @param e the Emitter that emits the code
     */
    public void compile(Emitter e)
    {
        int id = e.nextLabelID();
        e.emit("while" + id + ":     #jump for while" + id);
        cond.compile(e, "endwhile" + id);
        stat.compile(e);
        e.emit("j while" + id + "   #return to top of while loop");
        e.emit("endwhile" + id + ":   #end of while loop, move forward");
    }
}
