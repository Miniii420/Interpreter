import frontend.AST.Program;
import frontend.Lexer;
import frontend.Parser;
import runtime.Interpreter;
import runtime.Values.RuntimeVal;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        /*
        List<Lexer.Token> tokens = Lexer.tokenize("42 * (2 + 3)");
        if (tokens != null) {
            for (Lexer.Token token : tokens) {
                System.out.println(token.value + " : " + token.type);
            }
        }
        System.out.println("\n");
        /*/

        /*
        Program pro = parser.produceAST("42 * (2 + 3)");
        System.out.println(pro);
        System.out.println("\n");
        /*/

        /*
        RuntimeVal rv = Interpreter.evaluate(pro);
        System.out.println(rv);
        System.out.println("\n");
        /*/

        /*
        System.out.println("\nRepl v0.1");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            // Check for no user input or exit keyword
            if (input == null || input.contains("exit")) {
                System.exit(0);
            }

            Program program = parser.produceAST(input);
            RuntimeVal result = Interpreter.evaluate(program);
            System.out.println(result);
        }
        */
    }
}
