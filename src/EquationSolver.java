
import java.util.StringTokenizer;

public class  EquationSolver
{

    public static double solve( String equation )
	{
        return evaluatePostfix(parseInfixToPostfix(equation));


	}

	private static double evaluateOperation( char operand, double operator1, double operator2 )
	{
		double result;

		switch ( operand )
		{
            case '^':
                result = Math.pow(operator2, operator1);
                break;
			case '+':
				result = operator1 + operator2;
				break;
			case '*':
				result = operator1 * operator2;
				break;
            default:
                throw new IllegalArgumentException(String.format("You passed in a bad operand: %d", operand));
		}
		return result;
	}

	public static double evaluatePostfix( DSAQueue<Object> postfixExpression )
	{

		DSAStack<Double> resultStack = new DSAStack<Double>();
		while(!postfixExpression.isEmpty())
		{
            if(postfixExpression.peek() instanceof Double)
            {
                resultStack.push((Double) postfixExpression.dequeue());
            }
            else if( postfixExpression.peek() instanceof Character)
            {
               resultStack.push(evaluateOperation((Character) postfixExpression.dequeue(),  resultStack.pop(),  resultStack.pop()));

            }


		}
        return resultStack.pop().doubleValue();
	} 

	private static int presedenceOf(char operator)
	{
		int presedence;

		switch(operator)
		{
            case '+':
            case '-':
				presedence = 1;
				break;
            case '*':
            case '/':
				presedence = 2;
				break;
			case '^':
				presedence = 3;
				break;
            case '(':
                presedence = 0;
                break;
			default:
				throw new java.lang.IllegalArgumentException("Illegal Operator");

		}

		return presedence;
	}

	private static DSAQueue<Object> parseInfixToPostfix(String equation)
	{
        equation = equation.replaceAll(" +", "");
		DSAQueue<Object> postfixQueue = new DSAQueue<Object>();

		DSAStack<Character> operatorStack = new DSAStack<Character>();

		StringTokenizer strTok = new StringTokenizer(equation, " ( ) + * ^ / ", true);

        String token;

        //Strip out the spaces


		while(strTok.hasMoreTokens())
		{
		    token = strTok.nextToken();

            switch(token.charAt(0))
            {
                case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
                    postfixQueue.enqueue((Object)(Double.parseDouble(token)));
                    break;

                //Number is negative
                case '-':
                    postfixQueue.enqueue((Object)(Double.parseDouble(token)));
                    //uses the && operator to short circuit if the operator stack is empty
                    while(!operatorStack.isEmpty() && presedenceOf('+') <= presedenceOf(operatorStack.top()))
                    {
                        postfixQueue.enqueue((Object) operatorStack.pop());
                    }
                    //operatorStack.push('+');
                    break;

                case '/':
                    //Raise the operator to the -1 power to gain it's reciprocal
                    double devisor = Math.pow(Double.parseDouble(strTok.nextToken()), -1);
                    postfixQueue.enqueue((Object)(devisor));

                    while(!operatorStack.isEmpty() && presedenceOf('*') <= presedenceOf(operatorStack.top()))
                    {
                        postfixQueue.enqueue((Object) operatorStack.pop());
                    }
                    operatorStack.push('*');
                    break;

                case '^':
                    while(!operatorStack.isEmpty() && presedenceOf('^') <= presedenceOf(operatorStack.top()))
                    {
                        postfixQueue.enqueue((Object) operatorStack.pop());
                    }
                    operatorStack.push('^');
                    break;
                case '+':
                    while(!operatorStack.isEmpty() && presedenceOf('+') <= presedenceOf(operatorStack.top()))
                    {
                        postfixQueue.enqueue((Object) operatorStack.pop());
                    }
                    operatorStack.push('+');
                    break;

                case '*':
                    while(!operatorStack.isEmpty() && presedenceOf('*') <= presedenceOf(operatorStack.top()))
                    {
                        postfixQueue.enqueue((Object) operatorStack.pop());
                    }
                    operatorStack.push('*');
                    break;

                case '(':
                    operatorStack.push('(');
                    break;

                case ')':
                    while(operatorStack.top() != '(')
                    {
                        postfixQueue.enqueue((Object)operatorStack.pop());
                    }
                    operatorStack.pop();


            }

		}
        while(!operatorStack.isEmpty())
        {
            postfixQueue.enqueue((Object) operatorStack.pop());
        }

        return postfixQueue;
	}

}
