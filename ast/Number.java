package ast;
import environment.*;
import emitter.Emitter;

/**
 * The Number class is an Expression, used for parsing numbers.
 *
 * @author Anish Jain
 * @version 10.19.23
 */
public class Number extends Expression
{
    private int value;

    /**
     * Constructor for objects of class Number
     * 
     * @param val the value of this number
     */
    public Number(int val)
    {
        value = val;
    }

    /**
     * Evaluates this number; i.e., returns it.
     * 
     * @param e the environment where the calculations are happening
     * @return int the integer number
     */
    public int eval(Environment e)
    {
        return value;
    }

    /**
     * Emits/Compiles this number by putting the value into $v0
     * 
     * @param e the Emitter to emit the code
     */
    @Override
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + value);
    }
}
