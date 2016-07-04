package me.list;

import java.util.regex.*;

public class FourOperations {
    private static char[] operation = {'-', '+', '*', '/', ')', '('};

    public static boolean isOperation(char ch) {
        for (int i = 0; i < operation.length; i++) {
            if ( ch == operation[i] ) return true;
        }
        return false;
    }

    public static String getStackNum(MyStack<Character> digit) {
        StringBuilder number = new StringBuilder();
        while ( !digit.isEmpty() ) {
            number.append( digit.pop() );
        }

        return number.reverse().toString();        
    }

    public static void getStackOp(
        MyStack<Operation> operationStack, 
        MyArrayList<String> revPolish) {

        String opInStack;
        char topOp;
        while ( !operationStack.isEmpty() ) {
            topOp = operationStack.pop().value();
            if ( topOp == '(' ) break;
            opInStack =  new Character( topOp ).toString();
            revPolish.add( opInStack );
        }      
    }

    public static MyArrayList<String> reversePolishList(String exp) {
        char[] charExp = exp.toCharArray();

        MyStack<Operation> operationStack = new MyStack<Operation>();
        MyStack<Character> digitTmp = new MyStack<Character>();
        MyArrayList<String> revPolish = new MyArrayList<String>();

        char current;
        Operation op;
        for (int i = 0; i < charExp.length; i++) {
            current = charExp[i];
            
            if (current == ' ') continue;

            if ( Character.isDigit( current ) ) {
                digitTmp.push(current);
            } else if ( isOperation( current ) ) {
                // builder the number from char first
                if ( !digitTmp.isEmpty() )
                    revPolish.add( getStackNum( digitTmp ) );

                // deal with the non-digit char
                op = new Operation( current );

                if ( !operationStack.isEmpty() ) {
                    if ( op.value() == '(') {
                        operationStack.push( op );
                    } else if ( op.value() == ')' ) {
                        getStackOp( operationStack, revPolish);
                    } else if ( operationStack.top().prior() <= op.prior() ) {
                        operationStack.push( op );
                    } else {
                        getStackOp( operationStack, revPolish);
                        operationStack.push( op );                            
                    }
                } else {
                    operationStack.push( op );
                }
            }
        }

        revPolish.add( getStackNum( digitTmp ) );
        getStackOp( operationStack, revPolish);

        return revPolish;
    }

    public static String reversePolishString(String exp) {
        MyArrayList<String> revPolish = reversePolishList( exp );
        StringBuilder strRevPolish = new StringBuilder();

        java.util.Iterator<String> it = revPolish.iterator();
        while( it.hasNext() ) {
            strRevPolish.append( it.next() ).append(" ");
        }

        return strRevPolish.toString();
    }

    public static int calculate(String exp) throws Exception {
        MyArrayList<String> revPolish = reversePolishList( exp );

        MyStack<Integer> number = new MyStack<Integer>();

        Pattern isInteger = Pattern.compile("\\d+");

        Matcher intMatcher;
        String current;
        int operandOne, operandTwo, result;

        java.util.Iterator<String> it = revPolish.iterator();
        while( it.hasNext() ) {
            current = it.next();
            intMatcher = isInteger.matcher( current );
            if ( intMatcher.matches() ) {
                number.push( Integer.parseInt( current ) );
            } else {
                operandTwo = number.pop();
                operandOne = number.pop();

                if ( current.equals("+") ) {
                    result = operandOne + operandTwo;
                } else if ( current.equals("-") ) {
                    result = operandOne - operandTwo;
                } else if ( current.equals("*") ) {
                    result = operandOne * operandTwo;
                } else if ( current.equals("/") ) {
                    result = operandOne / operandTwo;
                } else {
                    throw new Exception("Only basic 4 arithmetic operations for integers are supported");
                }

                number.push( result );
            }
        }

        return number.pop();
    }

    private static class Operation {
        private char operation;
        private int prior;

        public Operation(char operation) {
            this.operation = operation;
            if (operation == '+' || operation == '-') {
                prior = 0;
            } else if (operation == '*' || operation == '/') {
                prior = 1;
            }
        }

        public char value() {
            return operation;
        }

        public int prior() {
            return prior;
        }
    }
}