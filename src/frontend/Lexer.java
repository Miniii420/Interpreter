package frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    public enum TokenType {
        Number,
        Identifier,
        Let,
        Const,
        Fn,
        BinaryOperator,
        Equals, // =
        Comma, // ,
        Dot, // .
        Colon, // :
        Semicolon, // ;
        OpenParen, // (
        CloseParen, // )
        OpenBrace, // {
        CloseBrace, // }
        OpenBracket, // [
        CloseBracket, // ]
        EOF, // End of File
    }

    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();

    static {
        KEYWORDS.put("let", TokenType.Let);
        KEYWORDS.put("const", TokenType.Const);
        KEYWORDS.put("fn" ,TokenType.Fn);
    }

    public static class Token {
        public String value;
        public TokenType type;

        public Token(String value, TokenType type) {
            this.value = value;
            this.type = type;
        }

        public TokenType getType() {
            return type;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Token{" +
                    "value='" + value + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    public static Token token(String value, TokenType type) {
        return new Token(value, type);
    }

    public static boolean isAlpha(char c) {
        return Character.isLetter(c);
    }

    public static boolean isInt(char c) {
        return Character.isDigit(c);
    }

    public static boolean isSkippable(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    public static List<Token> tokenize(String sourceCode) {
        List<Token> tokens = new ArrayList<>();
        char[] src = sourceCode.toCharArray();

        for (int i = 0; i < src.length; i++) {
            char currentChar = src[i];
            switch (currentChar) {
                case '(':
                    tokens.add(token(String.valueOf(currentChar), TokenType.OpenParen));
                    break;
                case ')':
                    tokens.add(token(String.valueOf(currentChar), TokenType.CloseParen));
                    break;
                case '{':
                    tokens.add(token(String.valueOf(currentChar), TokenType.OpenBrace));
                    break;
                case '}':
                    tokens.add(token(String.valueOf(currentChar), TokenType.CloseBrace));
                    break;
                case '[':
                    tokens.add(token(String.valueOf(currentChar), TokenType.OpenBracket));
                    break;
                case ']':
                    tokens.add(token(String.valueOf(currentChar), TokenType.CloseBracket));
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '%':
                    tokens.add(token(String.valueOf(currentChar), TokenType.BinaryOperator));
                    break;
                case '=':
                    tokens.add(token(String.valueOf(currentChar), TokenType.Equals));
                    break;
                case ';':
                    tokens.add(token(String.valueOf(currentChar), TokenType.Semicolon));
                    break;
                case ':':
                    tokens.add(token(String.valueOf(currentChar), TokenType.Colon));
                    break;
                case ',':
                    tokens.add(token(String.valueOf(currentChar), TokenType.Comma));
                    break;
                case '.':
                    tokens.add(token(String.valueOf(currentChar), TokenType.Dot));
                    break;
                default:
                    if (isInt(currentChar)) {
                        StringBuilder num = new StringBuilder();
                        while (i < src.length && isInt(src[i])) {
                            num.append(src[i++]);
                        }
                        tokens.add(token(num.toString(), TokenType.Number));
                        i--; // decrement i to account for the extra increment in the while loop
                    } else if (isAlpha(currentChar)) {
                        StringBuilder ident = new StringBuilder();
                        while (i < src.length && isAlpha(src[i])) {
                            ident.append(src[i++]);
                        }
                        // check for reserved keywords
                        String identifier = ident.toString();
                        TokenType reserved = KEYWORDS.get(identifier);
                        if (reserved != null) {
                            tokens.add(token(identifier, reserved));
                        } else {
                            tokens.add(token(identifier, TokenType.Identifier));
                        }
                        i--; // decrement i to account for the extra increment in the while loop
                    } else if (isSkippable(currentChar)) {
                        // SKIP THE CURRENT CHARACTER
                    } else {
                        System.out.println("Unrecognized character found in source: " + currentChar);
                        return null; // Or throw an exception
                    }
            }
        }
        tokens.add(token("EndOfFile", TokenType.EOF));
        return tokens;
    }



}
