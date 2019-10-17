package lexer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import exceptions.AnalyzerException;
import token.Token;
import token.TokenType;

public class Lexer {
    private Map<TokenType,String> tokensPatterns;

    private int currentLine;
    private int currentPosition;
    private String source;


    public Lexer() {
        tokensPatterns = new Hashtable<>();
        tokensPatterns.put(TokenType.PROGRAM, "\\b(program)\\b.*");
        tokensPatterns.put(TokenType.BEGIN, "\\b(begin)\\b.*");
        tokensPatterns.put(TokenType.CONST, "\\b(const)\\b.*");
        tokensPatterns.put(TokenType.END, "\\b(end)\\b.*");
        tokensPatterns.put(TokenType.PRINT, "\\b(print)\\b.*");
        tokensPatterns.put(TokenType.PRINTLN, "\\b(println)\\b.*");
        tokensPatterns.put(TokenType.STRING, "(\"[^\"]*\").*");
        tokensPatterns.put(TokenType.OPEN_BRACKET, "(\\().*");
        tokensPatterns.put(TokenType.CLOSE_BRACKET, "(\\)).*");
        tokensPatterns.put(TokenType.SEMICOLON, "(;).*");
        tokensPatterns.put(TokenType.COLON, "(:).*");
        tokensPatterns.put(TokenType.COMMA, "(,).*");
        tokensPatterns.put(TokenType.OPEN_BLOCK, "(\\{).*");
        tokensPatterns.put(TokenType.CLOSE_BLOCK, "(\\}).*");
        tokensPatterns.put(TokenType.NUMBER, "\\b(\\d+(\\.\\d+)?)\\b.*");
        tokensPatterns.put(TokenType.IF, "\\b(if)\\b.*");
        tokensPatterns.put(TokenType.ELSE, "\\b(else)\\b.*");
        tokensPatterns.put(TokenType.WHILE, "\\b(while)\\b.*");
        tokensPatterns.put(TokenType.VAR, "\\b(var)\\b.*");
        tokensPatterns.put(TokenType.DOT, "(\\.).*");
        tokensPatterns.put(TokenType.PLUS, "(\\+{1}).*");
        tokensPatterns.put(TokenType.MINUS, "(\\-{1}).*");
        tokensPatterns.put(TokenType.MULTIPLY, "(\\*).*");
        tokensPatterns.put(TokenType.DIVIDE, "(/).*");
        tokensPatterns.put(TokenType.EQUAL, "(==).*");
        tokensPatterns.put(TokenType.ASSIGNMENT, "(:=).*");
        tokensPatterns.put(TokenType.NOTEQUAL, "(\\!=).*");
        tokensPatterns.put(TokenType.GREATER, "(>).*");
        tokensPatterns.put(TokenType.LESS, "(<).*");
        tokensPatterns.put(TokenType.PROCEDURE, "\\b(procedure)\\b.*");
        tokensPatterns.put(TokenType.RUN, "\\b(run)\\b.*");
        tokensPatterns.put(TokenType.PARAM, "\\b(param)\\b.*");
        tokensPatterns.put(TokenType.OUT, "\\b(out)\\b.*");
        tokensPatterns.put(TokenType.IDENTIFIER, "\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");
    }

    public List<Token> getTokens(String source) throws AnalyzerException {
        currentPosition = 0;
        currentLine = 0;
        this.source = source;
        List<Token> tokens = new ArrayList<>();
        while (currentPosition < source.length()) {
            removeWhitespaces();
            Token token = getNextToken();
            tokens.add(token);
        }
        return tokens;
    }

    private Token getNextToken() throws AnalyzerException {
        for (TokenType tokenType : TokenType.values()) {
            Pattern pattern = Pattern.compile(".{" + currentPosition + "}" + tokensPatterns.get(tokenType),
                    Pattern.DOTALL);
            Matcher matcher = pattern.matcher(source);
            if (matcher.matches()) {
                String token = matcher.group(1);
                currentPosition+= token.length();
                return new Token( tokenType, token, currentLine);
            }
        }
        System.out.println(source.substring(0,currentPosition));
        throw new AnalyzerException("Unknown token type at: " + currentPosition + ", line: " + currentLine);
    }

    private void removeWhitespaces(){
        while (source.length() > currentPosition &&
                (source.charAt(currentPosition) == ' '
                        || source.charAt(currentPosition) == '\n'
                        || source.charAt(currentPosition) == '\t')){
            if (source.charAt(currentPosition) == '\n'){
                currentLine++;
            }
            currentPosition++;
        }
    }
}