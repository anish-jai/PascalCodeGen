package ast;
import environment.*;
import java.util.List;

/**
 * The ProcedureCall class calls upon the associated procedure and 
 * evaluates it
 *
 * @author Anish Jain
 * @version 11.9.23
 */
public class ProcedureCall extends Expression
{
    String funcName;
    List<Expression> expressions;
    /**
     * Constructor for objects of class ProcedureCall
     * 
     * @param n the name of the procedure that is being called
     * @param e the expressions/parameters for this procedure
     */
    public ProcedureCall(String n, List<Expression> e) 
    {
        funcName = n;
        expressions = e;
    }

    /**
     * Evaluates this procedure by accessing the declaration from the parent
     * environment. Then, executes the statement/s within the function
     * in order to return the final value of the procedure.
     * 
     * @param e the environment for this procedure call
     * @return int the final evaluated value of the procedure
     */
    @Override
    public int eval(Environment e)
    {
        Environment par = e;
        if(e.getParent()!=null)
        {
            par = e.getParent();
        }
        Environment env = new Environment(par);
        ProcedureDeclaration funcDec = e.getProcedure(funcName);
        List<String> params = funcDec.getParams();
        if (expressions.size() != params.size())
        {
            throw new IllegalArgumentException("Expected " + params.size() + "and instead got " );
        }
        for (int i = 0; i < params.size(); i++)
        {
            env.declareVariable(params.get(i), expressions.get(i).eval(e));
        }
        env.declareVariable(funcName, 0);
        funcDec.getStatement().exec(env);
        return env.getVariable(funcName);
    }
}
