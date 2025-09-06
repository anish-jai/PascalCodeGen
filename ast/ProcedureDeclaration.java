package ast;
import environment.*;
import java.util.List;
/**
 * A ProcedureDeclaration represents the declaration of one of Procedure,
 * containing the name, required paramaters, and statements within the body
 * of the function.
 * 
 *
 * @author Anish Jain
 * @version 11.9.23
 */
public class ProcedureDeclaration extends Statement
{
    private String funcName;
    private List<String> params;
    private Statement stat;
    
    /**
     * Constructor for objects of class ProcedureDeclaration
     * 
     * @param fn the function name
     * @param p the list of parameters needed for this procedure
     * @param s the statement/block inside the body of the procedure
     */
    public ProcedureDeclaration(String fn, List<String> p, Statement s) 
    {
        funcName = fn;
        params = p;
        stat = s;
    }
    
    /**
     * Getter for the body statements of the procedure declaration
     * 
     * @return Statement the statement within this ProcedureDeclaration
     */
    public Statement getStatement()
    {
        return stat;
    }
    
    /**
     * Getter for the params of the procedure declaration
     * 
     * @return List the list of parameters of this procedure dec
     */
    public List<String> getParams()
    {
        return params;
    }
    
    /**
     * Executes this procedure declaration by setting/assigning it to the 
     * environment 
     * 
     * @param e the environment this ProcedureDeclaration is in
     */
    @Override
    public void exec(Environment e)
    {
        e.setProcedure(funcName, this);
    }
}
