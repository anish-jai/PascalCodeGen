package ast;
import java.util.List;
import environment.*;
import emitter.Emitter;

/**
 * A Block is a group of multiple statements.
 *
 * @author Anish Jain
 * @version 10.25.23
 */
public class Block extends Statement
{
    private List<Statement> stmts;

    /**
     * Constructor for objects of class Block
     * 
     * @param statements the set of statements that make up this Block
     */
    public Block(List<Statement> statements)
    {
        stmts = statements;
    }

    /**
     * Executes all the statements in this block by looping through 
     * the list of statements.
     * 
     * @param env the environment where the Block will be executed
     */
    @Override
    public void exec(Environment env)
    {
        for (int i = 0; i < stmts.size(); i++)
        {
            Statement s = stmts.get(i);
            s.exec(env);
        }
    }

    /**
     * Compiles a block by individually compiling the statements within it
     * 
     * @param e Emitter that writes the code
     */
    @Override
    public void compile(Emitter e)
    {
        for (int i = 0; i < stmts.size(); i++)
        {
            Statement s = stmts.get(i);
            s.compile(e);
        }
    }
}
