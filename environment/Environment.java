package environment;
import java.util.*;
import ast.*;
/**
 * The Environment class' primary purpose is to create, access, and remember
 * the values of the variables and procedures that have been defined. 
 *
 * @author Anish Jain
 * @version 11.9.23
 */
public class Environment
{
    private Map<String, Integer> vars;
    private Map<String, ProcedureDeclaration> funcs;
    Environment parent;
    /**
     * Constructor for objects of class Environment
     * 
     * @param p the parent environment; otherwise, null if this is the global
     *        enivronment
     */
    public Environment(Environment p)
    {
        vars = new HashMap<String, Integer>();
        funcs = new HashMap<String, ProcedureDeclaration>();
        parent = p;
    }

    /**
     * Sets a variable's value, replacing the existing value in either this 
     * environment or the parent environment. 
     * 
     * Otherwise, creates a new variable in this environment's HashMap.
     * 
     * 
     * @param variable the name of the variable being added/updated
     * @param value the new integer value of the variable 
     * 
     */
    public void setVariable(String variable, int value)
    {
        if(vars.containsKey(variable))
        {
            vars.put(variable, value);
        }
        else if(parent != null && parent.getAllVariables().containsKey(variable))
        {
            vars.put(variable, value);
        }
        else
        {
            vars.put(variable, value);
        }
    }

    /**
     * Returns the value that is associated with the inputted variable name 
     * or checks the parent environment for the variable. 
     * 
     * 
     * @param variable the name of the variable to get from the HashMap
     * @return int the corresponding value of the variable
     * @throw IllegalArgumentException if the variable doesn't exist in either 
     *        environment
     */
    public int getVariable(String variable) throws IllegalArgumentException
    {
        if (vars.containsKey(variable))
        {
            return vars.get(variable);
        }
        else if (parent != null)
        {
            return parent.getVariable(variable);
        }
        else
        {
            throw new IllegalArgumentException("Variable with name: " + 
                variable + " does not exist.");
        }
    }

    /**
     * Defines/assigns a procedure in the global environment.
     * 
     * @param name the name of the procedure
     * @param dec the procedure declaration with its params and statements 
     * 
     */
    public void setProcedure(String name, ProcedureDeclaration dec)
    {
        if (parent != null)
        {
            parent.setProcedure(name, dec);
        }
        else
        {
            funcs.put(name, dec);
        }
    }

    /**
     * Retrieves the procedure with the given name.
     * 
     * @param name the name of the procedure to be retrieved
     * @return ProcedureDeclaration the procedure associated with the given name
     */
    public ProcedureDeclaration getProcedure(String name)
    {
        if(parent == null && funcs.containsKey(name))
        {
            if(funcs.containsKey(name))
            {
                return funcs.get(name);
            }
        }
        else
        {
            return parent.getProcedure(name);
        }
        throw new IllegalArgumentException("Procedure with name: " + name + " does not exist.");
    }

    /**
     * Tells whether or not this environment or parent environment contains
     * the variable that is requested. 
     * 
     * @param variable the name of the variable to search for
     * @return boolean true, if this or the parent environment contains the variable,
     *                 otherwise, false
     */
    private boolean hasVariable(String variable)
    {
        return (parent != null && parent.hasVariable(variable)) || vars.containsKey(variable);
    }

    /**
     * Returns this environment's parent, or this environment if it
     * doesn't have a parent
     * 
     * @return Environment the parent environment
     */
    public Environment getParent()
    {
        if(parent == null)
        {
            return this;
        }
        return parent.getParent();
    }

    /**
     * Declares a variable in this environment
     * 
     * @param variable the variable name to assign the value to
     * @param value the value of the variable
     */
    public void declareVariable(String variable, int value)
    {
        vars.put(variable, value);
    }

    /**
     * Getter method for all the defined variables in this environment 
     * 
     * 
     * @return Map containing all the variables/values in this Environment
     *                             
     */
    public Map<String, Integer> getAllVariables()
    {
        return vars;
    }
}
