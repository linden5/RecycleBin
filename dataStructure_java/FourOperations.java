import java.util.regexp.*;

public class FourOperations {
    private static Pattern lowPrior = new Pattern("\+|\-");;
    private static Pattern highPrior = new Pattern("\*|\/|\)");;

    public static void reversePolish(String exp) {
        String[] splited = exp.split("\s+");
        MyArrayList revPolish = new MyArrayList();

        Pattern digit = new Pattern("\d+");
        Pattern operation = new Pattern("\+|\-|\*|\/|\(|\)");
        Matcher digitMatcher;

        MyStack<Operation> operationStack = new MyStack<Operation>();

        String trimed;
        for (int i = 0; i < splited.length; i++) {
            trimed = splited[i].trim();
            digitMatcher = digit.matcher( trimed );
            if ( digitMatcher.matches() )
                revPolish.add( trimed );
        }
    }

    public static void calculate(String exp) {

    }

    private class Operation {
        private String operation;
        private int prior;

        public Operation(String operation, int prior) {
            this.operation = operation;
            this.prior = prior;
        }
    }
}