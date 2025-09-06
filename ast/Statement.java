package ast;
import environment.*;
import emitter.Emitter;
/**
 * The Statement abstract class is a framework for all code that executes
 * statements.
 *
 * @author Anish Jain
 * @version 10.19.23
 */
public abstract class Statement
{
    /**
     * Executes this statement; every object that is a Statement must 
     * be executed in some form
     * 
     * @param env the environment in which the executions happen
     */
    public abstract void exec(Environment env);
    
    /**
     * Will be overrided by each AST component, this serves as a reminder
     * if one has not been.
     * 
     * @param e the emitter that will compile this Statement
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}
