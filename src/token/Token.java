package token;



public class Token {
    private TokenType tokenType;
    private String tokenString;
    private int lineNumber;

    public Token(TokenType tokenType, String tokenString, int lineNumber) {
        this.tokenType = tokenType;
        this.tokenString = tokenString;
        this.lineNumber = lineNumber;
    }

    public String getTokenString() {
        return tokenString;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return tokenString;
    }
}