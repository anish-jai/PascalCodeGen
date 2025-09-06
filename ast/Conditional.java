package ast;
import environment.*;
import emitter.*;

/**
 * The Conditional class is an Expression that is useful for evaluating
 * boolean operations.
 * 
 * 
 * @author Anish Jain
 * @version 10.19.23
 */
public class Conditional extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Constructor for objects of class Conditional
     * 
     * @param oper the relative operator 
     * @param e1 the expression to the left of the operator
     * @param e2 the expression to the right of the operator
     */
    public Conditional(String oper, Expression e1, Expression e2)
    {
        op = oper;
        exp1 = e1;
        exp2 = e2;
    }

    /**
     * Evaluates this conditional, by comparing the two expressions based
     * upon what the operator is. 
     * Potential operators include: ==, <>, >, >=, <, <=
     * 
     * @param e the environment in which the evaluation happens
     * 
     * @return int either 0 or 1, representing false and true respectively
     */
    @Override
    public int eval(Environment e)
    {
        int leftVal = exp1.eval(e);
        int rightVal = exp2.eval(e);
        int ret = 0;
        if((op.equals("==") && leftVal == rightVal) || (op.equals("<>") && leftVal != rightVal)
            || (op.equals(">") && leftVal > rightVal) || (op.equals("<") && leftVal < rightVal)
            || (op.equals(">=") && leftVal >= rightVal) || (op.equals("<=") && leftVal <= rightVal))
        {
            ret = 1;
        }
        return ret;
    }

    /**
     *  Compiles a conditional by evaluating the first expression,
     * pushing it onto the stack, evaluating the second, then storing the first in a temp
     * 
     * 
     * @param e Emitter that generates code
     * @param tlabel the target label to jump if the expression is false
     */
    public void compile(Emitter e, String tlabel)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t1");
        if (op.equals("="))
        {
            e.emit("bne $t1, $v0, " + tlabel + "  #Equal to conditional");
        }
        else if (op.equals(">"))
        {
            e.emit("ble $t1, $v0, " + tlabel + "  #Greater than conditional");
        }
        else if (op.equals("<"))
        {
            e.emit("bge $t1, $v0, " + tlabel + "  #Less than conditional");
        }
        else if (op.equals("<>"))
        {
            e.emit("beq $t1, $v0, " + tlabel + "  #Not equal to conditional");
        }
        else if (op.equals(">="))
        {
            e.emit("blt $t1, $v0, " + tlabel + "  #Greater than equal to conditional");
        }
        else if (op.equals("<="))
        {
            e.emit("bgt $t1, $v0, " + tlabel + "  #Less than equal to conditional");
        }
        else
        {
            throw new IllegalArgumentException("Operator not recognized.");
        }
    }
}
