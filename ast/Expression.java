package ast;
import environment.*;
import emitter.Emitter;

/**
 * The Expression abstract class is a framework for all expressions
 * and calculations.
 *
 * @author Anish Jain
 * @version 10.19.23
 */
public abstract class Expression
{
    /**
     * Every object that is an Expression must be able to be calculated 
     * in some form; provides a basis for evaluation.
     * 
     * @param env the environment where all the variables and their values 
     * are stored
     * 
     * @return int the integer value of the evaluated expression
     */
    public abstract int eval(Environment env);
    
    /**
     * Will be overrided by each AST component, this serves as a reminder
     * if one has not been.
     * 
     * @param e the emitter that will compile this Expression
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}
