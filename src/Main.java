import frontend.AST.*;
import frontend.Parser;
import runtime.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static runtime.Environment.createGlobalEnv;
import static runtime.Interpreter.evaluate;

public class Main {
    public static void main(String[] args) {
        String filename = "src/test.txt";
        run(filename);
    }

    public static void run(String filename) {
        Parser parser = new Parser();
        Environment env = createGlobalEnv();
        // Create Default Global Environment

        try {
            String input = Files.readString(Paths.get(filename));
            Program program = parser.produceAST(input);
            evaluate(program, env);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void repl() {
        Parser parser = new Parser();
        Environment env = createGlobalEnv();

        System.out.println("\nRepl v0.1");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            // Check for no user input or exit keyword
            if (input == null || input.contains("exit")) {
                System.exit(0);
            }

            Program program = parser.produceAST(input);
            System.out.println(program); // Print the AST node
            evaluate(program, env);
        }
    }
}
