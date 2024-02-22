package frontend;

import frontend.AST.*;
import frontend.Lexer.Token;
import frontend.Lexer.TokenType;


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
        switch (at().getType()) {
            case Let:
            case Const:
                return parseVarDeclaration();
            default:
                return parseExpr();
        }
    }

    private Stmt parseVarDeclaration() {
        boolean isConstant = eat().getType() == TokenType.Const;
        String identifier = expect(TokenType.Identifier, "Expected Identifier name following Let or const Keyword.").getValue();

        if (at().getType() == TokenType.Semicolon) {
            eat();
            if (isConstant) {
                throw new RuntimeException("Must assign value to constant expression. No value provided.");
            }
            return new VarDeclaration(false, identifier, null);
        }

        expect(TokenType.Equals, "Expected equals token following identifier in var declaration.");
        Expr value = parseExpr();
        VarDeclaration declaration = new VarDeclaration(isConstant, identifier, value);

        expect(TokenType.Semicolon, "Variable Declaration statement must end with semicolon.");
        return declaration;
    }

    private Expr parseExpr() {
        return parseAssignmentExpr();
    }

    private Expr parseAssignmentExpr() {
        Expr left = parseAdditiveExpr();

        if (at().getType() == TokenType.Equals) {
            eat();
            Expr value = parseAssignmentExpr();
            return new AssignmentExpr(left, value);
        }

        return left;
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
