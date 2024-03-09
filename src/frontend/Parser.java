package frontend;

import frontend.AST.*;
import frontend.Lexer.Token;
import frontend.Lexer.TokenType;
import static frontend.Lexer.tokenize;

import java.util.ArrayList;
import java.util.List;



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
            case Fn:
                return parseFnDeclaration();
            default:
                return parseExpr();
        }
    }

    private Stmt parseFnDeclaration() {
        eat();
        Token nameToken = expect(TokenType.Identifier, "unexpected Functionname following fn keyword");
        String name = nameToken.getValue();

        List<Expr> args = parseArgs();

        List<String> params = new ArrayList<>();
        for (Expr arg : args) {
            if (arg.getKind() != NodeType.Identifier){
                System.out.println(arg);
                throw new RuntimeException("Inside function declaration expected parameters to be of type string.");
            }
            params.add(((Identifier) arg).getValue());
        }

        expect(TokenType.OpenBrace, "Expected Open brace following Parameters / Expected function body following declaration");

        List<Stmt> body = new ArrayList<>();
        while (at().getType() != TokenType.EOF && at().getType() != TokenType.CloseBrace) {
            body.add(parseStmt());
        }

        expect(TokenType.CloseBrace, "Closing Brace following Function body");

        return new FunctionDeclaration(params, name, body);
    }

    private Expr parseObjectExpr() {
        if (at().getType() != TokenType.OpenBrace) {
            return parseAdditiveExpr();
        }

        eat();
        List<Property> properties = new ArrayList<>();

        while (notEof() && at().getType() != TokenType.CloseBrace) {
            Token keyToken = expect(TokenType.Identifier, "Object Literal Key expected.");
            String key = keyToken.getValue();

            // Allows shorthand key: pair -> { key, }
            if (at().getType() == TokenType.Comma) {
                eat();
                properties.add(new Property(key, null));
                continue;
            }
            // Allows shorthand key: pair -> { key }
            else if (at().getType() == TokenType.CloseBrace) {
                properties.add(new Property(key, null));
                continue;
            }

            // { key : val }
            expect(TokenType.Colon, "Missing Colon following Identifier in ObjectExpr");
            Expr value = parseExpr();

            properties.add(new Property(key, value));
            if (at().getType() != TokenType.CloseBrace) {
                expect(TokenType.Comma, "Expected Comma or Closing Bracket following Property");
            }
        }

        expect(TokenType.CloseBrace, "Object Literal missing Closing brace.");
        return new ObjectLiteral(properties);
    }


    private Expr parseMemberExpr() {
        Expr object = parsePrimaryExpr();

        while (at().getType() == TokenType.Dot || at().getType() == TokenType.OpenBracket) {
            Token operator = eat();
            Expr property;
            boolean computed;

            if (operator.getType() == TokenType.Dot) {
                computed = false;
                property = parsePrimaryExpr();

                if (property.getKind() != NodeType.Identifier) {
                    throw new RuntimeException("Cannot use dot operator without right hand side being an identifier");
                }
            } else {
                computed = true;
                property = parseExpr();
                expect(TokenType.CloseBracket, "Missing closing bracket in computed value");
            }

            object = new MemberExpr(object, property, computed);
        }

        return object;
    }
    private Expr parseCallMemberExpr() {
        Expr member = parseMemberExpr();

        if (at().getType() == TokenType.OpenParen) {
            return parseCallExpr(member);
        }

        return member;
    }

    private Expr parseCallExpr(Expr caller) {
        CallExpr callExpr = new CallExpr(parseArgs(), caller);

        if (at().getType() == TokenType.OpenParen) {
            callExpr = (CallExpr) parseCallExpr(caller);
        }

        return callExpr;
    }

    private List<Expr> parseArgumentsList() {
        List<Expr> args = new ArrayList<>();
        args.add(parseAssignmentExpr());

        while (at().getType() == TokenType.Comma && eat() != null) {
            args.add(parseAssignmentExpr());
        }

        return args;
    }
    private List<Expr> parseArgs() {
        expect(TokenType.OpenParen, "Expected Open Paren");

        List<Expr> args;
        if (at().getType() == TokenType.CloseParen) {
            args = new ArrayList<>();
        } else {
            args = parseArgumentsList();
        }

        expect(TokenType.CloseParen, "Missing Closing Paren inside arguments list");

        return args;
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
        Expr left = parseObjectExpr();

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
        Expr left = parseCallMemberExpr();

        while (at().getValue().equals("*") || at().getValue().equals("/") || at().getValue().equals("%")) {
            String operator = eat().getValue();
            Expr right = parseCallMemberExpr();
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
