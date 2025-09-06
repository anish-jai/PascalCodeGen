package ast;
import environment.*;
import emitter.Emitter;
/**
 * The Writeln class is a simple print function, where anything in the
 * paranthesis are evaluated and printed.
 *
 * @author Anish Jain
 * @version 10.19.23
 */
public class Writeln extends Statement
{
    private Expression exp;

    /**
     * Constructor for objects of class Writeln
     * 
     * @param e the Expression to be evaluated/printed
     */
    public Writeln(Expression e)
    {
        exp = e;
    }

    /**
     * Executes the Writeln by evaluating and printing the expression.
     * 
     * @param env the environment where the Writeln will be executed
     */
    @Override
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));    
    }
    
    /**
     * Emits/Compiles this Writeln statement
     * 
     * @param e the Emitter to emit the code
     */
    @Override
    public void compile(Emitter e)
    {
        exp.compile(e); 
        e.emit("move $a0 $v0    # Move value into $a0 register");
        e.emit("li $v0 1");
        e.emit("syscall");
        e.nline();
    }
}
