package parser;
import scanner.Scanner;
import scanner.ScanErrorException;
import java.util.Map;
import java.util.HashMap;
import ast.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The Parser classes utilizes a context-free grammar to parse through
 * the tokenized version of the provided code given by the scanner.
 * 
 * This updated version of Parser uses the AST package to create nodes
 * for AST classes.
 *
 * @author Anish Jain
 * @author Anu Datar
 * 
 * @version 10.19.23
 */
public class Parser
{
    private Scanner scanner;
    private String curToken;
    /**
     * Constructor for objects of class Parser
     * 
     * @param scan the Scanner that will scan through and return Tokens for
     * the parser
     * 
     */
    public Parser(Scanner scan) throws ScanErrorException
    {
        scanner = scan;
        curToken = scanner.nextToken();
    }

    /**
     * Eats the current token if it matches what is expected
     * 
     * @param expected the expected String to be eaten
     */
    private void eat(String expected) throws ScanErrorException
    {
        if(expected.equals(curToken))
        {
            curToken = scanner.nextToken();
        }
        else
        {
            throw new IllegalArgumentException("Expected " + 
                expected + "but instead found " + curToken);
        }
    }

    /**
     * Parses the number that is in the current token.
     * 
     * @precondition: current token is an integer
     * @postcondition: number token has been eaten
     * 
     * @return Expresion expression with the number
     */
    private Expression parseNumber() throws ScanErrorException
    {
        int n = Integer.parseInt(curToken);
        eat(curToken);
        return new ast.Number(n);
    }

    /**
     * Reads/stores all procedure declarations and then calls upon
     * parseStatement and assigns this to a program object to store and 
     * execute the code. Program is the highest level object in the grammar.
     * 
     * 
     * 
     * @return Program the program object containing all declared functions
     *         and the following block/statement of code 
     */
    public Program parseProgram() throws ScanErrorException
    {
        List<String> varList = new ArrayList<String>();
        while(curToken.equals("VAR"))
        {
            eat("VAR");
            varList.add(curToken);
            eat(curToken);
            while (!curToken.equals(";"))
            {
                eat(",");
                varList.add(curToken);
                eat(curToken);
            }
            eat(";");
        }
        List <ProcedureDeclaration> decFuncs = new ArrayList<ProcedureDeclaration>();
        while (curToken.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String c = curToken;
            eat(curToken);
            eat("(");
            List<String> params = new ArrayList<String>();
            while (!curToken.equals(")"))
            {
                params.add(curToken);
                eat(curToken);
                if (curToken.equals(","))
                {
                    eat(",");
                }
            }
            eat(")");
            eat(";");
            Statement stat = parseStatement();
            decFuncs.add(new ProcedureDeclaration(c, params, stat));
        }
        Statement nextStat = parseStatement();
        return new Program(decFuncs, nextStat, varList);
    }

    /**
     * Parses current statement, by checking if the currentToken is "BEGIN",
     * "WRITELN", "IF", or "WHILE". Otherwise, assumes the statement is a 
     * variable definition. Calls upon parseExpression for values.
     * 
     * @return Statement the Statement that has been parsed
     */
    public Statement parseStatement() throws ScanErrorException
    {
        if(curToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if(curToken.equals("BEGIN"))
        {
            eat("BEGIN");
            List<Statement> stats = new ArrayList<Statement>();
            while(!curToken.equals("END"))
            {
                stats.add(parseStatement());
            }
            eat("END");
            eat(";");
            return new Block(stats);
        }
        else if(curToken.equals("IF"))
        {
            eat("IF");
            Expression exp = parseExpression();
            String oper = curToken;
            eat(curToken);
            Conditional condo = new Conditional(oper, exp, parseExpression());
            eat("THEN");
            return new If(condo, parseStatement());
        }
        else if(curToken.equals("WHILE"))
        {
            eat("WHILE");
            Expression exp = parseExpression();
            String oper = curToken;
            eat(curToken);
            Conditional condo = new Conditional(oper, exp, parseExpression());
            eat("DO");
            return new While(condo, parseStatement());
        }
        else
        {
            String varName = curToken;
            eat(curToken);
            //Variable v1 = new Variable(varName);
            eat(":=");
            Expression e = parseExpression();
            eat(";");
            return new Assignment(varName, e);
        }
    }

    /**
     * Recursively parses one factor, accounting for negative signs and 
     * paranthesis. Also, checks for variable definitions/names, using a HashMap
     * to replace the name with their integer value.
     * 
     * Checks for Procedure calls as well. If procedure is present, evaluate
     * each parameter as an individual expression, then returns new 
     * ProcedureCall object with corresponding name/parameters.
     * 
     * @return an Expression representing the value of this Factor
     */
    private Expression parseFactor() throws ScanErrorException
    {
        if(curToken.equals("-"))
        {
            eat("-");
            return new BinOp("*", new ast.Number(-1), parseFactor());
        }
        else if(curToken.equals("("))
        {
            eat("(");
            Expression a = parseExpression();
            eat(")");
            return a;
        }
        else if((curToken.substring(0, 1).compareTo("0") >= 0) && 
                (curToken.substring(0, 1).compareTo("9") <= 0))
        {
            return parseNumber();
        }
        else
        {
            String nme = curToken;
            eat(curToken);
            if(curToken.equals("("))
            {
                eat("(");
                List<Expression> pars = new ArrayList<Expression>();
                while(!curToken.equals(")"))
                {
                    pars.add(parseExpression());
                    if(curToken.equals(","))
                    {
                        eat(",");
                    }
                }
                eat(")");
                return new ProcedureCall(nme, pars);
            }
            else
            {
                Variable a = new Variable(nme);
                return a;
            }
        }
    }

    /**
     * Parses through current term, dealing with higher precedence operations. 
     * Calls upon parseFactor once multiplication and division has been accounted for.
     * 
     * 
     * @return an Expression representing the value of this term
     */
    private Expression parseTerm() throws ScanErrorException
    {
        Expression curTerm = parseFactor();
        while (curToken.equals("*") || curToken.equals("/") ) 
        {
            if (curToken.equals("*"))
            {
                eat("*");
                curTerm = new BinOp("*", curTerm, parseFactor());
            }
            else if (curToken.equals("/"))
            {
                eat("/");
                curTerm = new BinOp("/", curTerm, parseFactor());
            }
        }
        return curTerm;
    }   

    /**
     * Parses through an expression, accounting for addition and subtraction.
     * Also calls upon parseTerm.
     * 
     * @return an Expression representing the value of this expression
     */
    private Expression parseExpression() throws ScanErrorException
    {
        Expression curExpression = parseTerm();
        while(curToken.equals("-") || curToken.equals("+"))
        {
            if(curToken.equals("+"))
            {
                eat("+");
                curExpression = new BinOp("+", curExpression, parseTerm());
            }
            else if(curToken.equals("-"))
            {
                eat("-");
                curExpression = new BinOp("-", curExpression, parseTerm());
            }
        }
        return curExpression;
    }
}
