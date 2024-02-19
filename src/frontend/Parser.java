package frontend;

import frontend.Lexer.Token;
import frontend.Lexer.TokenType;

import frontend.AST.NodeType;
import frontend.AST.Stmt;
import frontend.AST.Program;
import frontend.AST.Expr;
import frontend.AST.BinaryExpr;
import frontend.AST.Identifier;
import frontend.AST.NumericLiteral;
import frontend.AST.NullLiteral;


import java.util.ArrayList;
import java.util.List;

import static frontend.Lexer.tokenize;

public class Parser {
    private List<Token> tokens = new ArrayList<>();

    private boolean notEof() {
        return !tokens.isEmpty() && tokens.get(0).getType() != TokenType.EOF;
    }

    private Token at() {
        return tokens.get(0);
    }

    private Token eat() {
        return tokens.remove(0);
    }

    private Token expect(TokenType type, Object err) {
        Token prev = eat();
        if (prev == null || prev.getType() != type) {
            System.err.println("Parser Error:\n" + err + " - Expecting: " + type);
            System.exit(1);
        }
        return prev;
    }

    public Program produceAST(String sourceCode) {
        this.tokens = tokenize(sourceCode);

        Program program = new Program(new ArrayList<>());

        while (notEof()) {
            program.getBody().add(parseStmt());
        }

        return program;
    }

    private Stmt parseStmt() {
        // Skip to parseExpr
        return parseExpr();
    }

    private Expr parseExpr() {
        return parseAdditiveExpr();
    }

    private Expr parseAdditiveExpr() {
        Expr left = parseMultiplicativeExpr();

        while (at().getValue().equals("+") || at().getValue().equals("-")) {
            String operator = eat().getValue();
            Expr right = parseMultiplicativeExpr();
            left = new BinaryExpr(left, right, operator);
        }
        return left;
    }

    private Expr parseMultiplicativeExpr() {
        Expr left = parsePrimaryExpr();

        while (at().getValue().equals("*") || at().getValue().equals("/") || at().getValue().equals("%")) {
            String operator = eat().getValue();
            Expr right = parsePrimaryExpr();
            left = new BinaryExpr(left, right, operator);
        }
        return left;
    }

    private Expr parsePrimaryExpr() {
        TokenType tk = at().getType();

        switch (tk) {
            case Identifier:
                return new Identifier(eat().getValue());
            case Null:
                eat(); // Advance past null keyword
                return new NullLiteral();
            case Number:
                return new NumericLiteral(Double.parseDouble(eat().getValue()));
            case OpenParen:
                eat(); // Eat opening parenthesis
                Expr value = parseExpr();
                expect(TokenType.CloseParen, "Unexpected Token found inside parenthesized expression. Expected closing parenthesis.");
                return value;
            default:
                System.err.println("Unexpected Token found during parsing: " + at());
                System.exit(1);
                return null; // Unreachable, added to satisfy compiler
        }
    }
}
