package Lab4;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ArithmeticExpression {

    static int presmetaj(char c[], int l, int r) {
        if (l == r) {
            // Single digit case
            return Character.getNumericValue(c[l]);
        }

        int balance = 0;
        int index = -1;

        // Find the operator with the lowest priority
        for (int i = r; i >= l; i--) {
            if (c[i] == '(') {
                balance++;
            } else if (c[i] == ')') {
                balance--;
            } else if ((c[i] == '+' || c[i] == '-') && balance == 0) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            // Evaluate the expression recursively
            // Apply the operator
            if (c[index] == '+') {
                return presmetaj(c, l, index - 1) + presmetaj(c, index + 1, r);
            } else {
                return presmetaj(c, l, index - 1) - presmetaj(c, index + 1, r);
            }
        } else {
            // If no operator is found, it means the expression is wrapped in parentheses
            return presmetaj(c, l + 1, r - 1);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String expression = br.readLine();
        char exp[] = expression.toCharArray();

        int rez = presmetaj(exp, 0, exp.length - 1);
        System.out.println(rez);

        br.close();
    }
}
