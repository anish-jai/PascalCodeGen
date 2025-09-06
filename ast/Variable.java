package ast;
import environment.*;
import emitter.Emitter;

/**
 * The Variable class is an Expression useful for creating/evaluating 
 * variables with integer values.
 *
 * @author Anish Jain
 * @version 10.19.23
 */
public class Variable extends Expression
{
    private String name;

    /**
     * Constructor for objects of class Variable
     * 
     * @param varName the name of this variable
     */
    public Variable(String varName)
    {
        name = varName;
    }

    /**
     * Evaluates this variable by returning its value.
     * 
     * @param e the environment where the variables and their values are 
     * stored
     * 
     * @return int the integer value corresponding with this variable's
     * name
     */
    public int eval(Environment e)
    {
        return e.getVariable(name);
    }

    /**
     * Compiles a variable, loads it into a temp register then 
     * loads its value into $v0
     * 
     * @param e The emitter that writes the code
     */
    @Override
    public void compile(Emitter e)
    {
        e.emit("la $t1 var" + name);
        e.emit("lw $v0 ($t1)");
    }
}
