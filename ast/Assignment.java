package ast;
import environment.*;
import emitter.Emitter;

/**
 * The Assignment class is a Statement that assigns a variable to an
 * evaluated Expression.
 *
 * @author Anish Jain
 * @version 10.19.23
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;

    /**
     * Constructor for objects of class Assignment
     * 
     * @param v the variable name that is being assigned
     * @param e the expression that will be evaluated, and assigned to 
     * the variable
     */
    public Assignment(String v, Expression e)
    {
        var = v;
        exp = e;
    }

    /**
     * Executes the assignment by evaluting the expression and assigning
     * it to a variable with the provided name.
     * 
     * @param env the environment where the variables are being assigned
     */
    @Override
    public void exec(Environment env)
    {
        env.setVariable(var, exp.eval(env));
    }

    /**
     * Compiles assignment by loading variable into a temp register, then 
     * saves the value in $v0 to that variable
     * 
     * @param e the emitter that writes the code
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("la $t2 var" + var);
        e.emit("sw $v0, ($t2) #moves var" + var + " to register $t2");
    }

}
