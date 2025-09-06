package ast;
import environment.*;
import emitter.Emitter;

/**
 * The BinOp class is an Expression useful for calculating the value 
 * of two expressions with an operator between them.
 *
 * @author Anish Jain
 * @version 10.19.23
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Constructor for objects of class BinOp
     * 
     * @param oper the operator between the two expressions
     * @param e1 the expression to the left of the operator
     * @param e2 the expression to the right of the operator
     */
    public BinOp(String oper, Expression e1, Expression e2)
    {
        op = oper;
        exp1 = e1;
        exp2 = e2;
    }

    /**
     * Evaluates this BinOp, returning the value of the operation after
     * evaluating each expression individually. 
     * 
     * Operators include +, -, *, %, and /.
     * 
     * @param envir the environment where the calculations/evaluation
     * is occurring
     * 
     * @return int the integer value of the evaluated BinOp
     */
    public int eval(Environment envir)
    {
        if(op.equals("+"))
        {
            return exp1.eval(envir) + exp2.eval(envir);
        }
        else if(op.equals("-"))
        {
            return exp1.eval(envir) - exp2.eval(envir);
        }
        else if(op.equals("*"))
        {
            return exp1.eval(envir) * exp2.eval(envir);
        }
        else if(op.equals("%"))
        {
            return exp1.eval(envir) % exp2.eval(envir);
        }
        else if(op.equals("/"))
        {
            return exp1.eval(envir) / exp2.eval(envir);
        }
        else
        {
            return -1/0;
        }
    }

    /**
     * Compiles a binary op. First compiles left side of expression by 
     * pushing it onto the stack, then computes right side. Then carries out
     * the operation and stores value in $t0 register.
     * 
     * @param e the Emitter that writes the code
     */
    @Override
    public void compile(Emitter e)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");

        if(op.equals("+"))
        {
            e.emit("addu $v0 $v0 $t0");
        }
        else if(op.equals("-"))
        {
            e.emit("subu $v0 $v0 $t0");
        }
        else
        {
            if(op.equals("*"))
            {
                e.emit("mult $v0, $t0");
            }
            else
            {
                e.emit("div $t0, $v0");
            }
            e.emit("mflo $v0");
        }
    }
}
