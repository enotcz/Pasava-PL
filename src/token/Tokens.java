package token;

import exceptions.ParserException;
import token.Token;
import token.TokenType;

import java.util.List;

public class Tokens {
    private List<Token> tokenList;
    private int currentTokenIndex;

    public Tokens(List<Token> tokenList) {
        this.tokenList = tokenList;
        currentTokenIndex = 0;
    }


    public Token getToken(TokenType... types) throws ParserException {
        if (currentTokenIndex >= tokenList.size()){
            throw new IndexOutOfBoundsException();
        }
        if (types.length==0){
            return tokenList.get(currentTokenIndex++);
        }
        for (TokenType type : types){
            if (type == tokenList.get(currentTokenIndex).getTokenType()){
                return tokenList.get(currentTokenIndex++);
            }
        }
        String error = "Missing one of next statements: ";
        for (TokenType type : types){
            error+=type + " ";
        }
        error+= " but current token is " + tokenList.get(currentTokenIndex).getTokenType() + "-> " + tokenList.get(currentTokenIndex).getTokenString();
        error+="\nIn Line " + tokenList.get(currentTokenIndex).getLineNumber() + " -> ";
        for (Token token : tokenList){
            if (token.getLineNumber() == tokenList.get(currentTokenIndex).getLineNumber()){
                error+=token.getTokenString() + " ";
            }
        }

        throw new ParserException(error);
    }

    public Token peekToken(){
        if (currentTokenIndex >= tokenList.size()){
            throw new IndexOutOfBoundsException();
        }
        return tokenList.get(currentTokenIndex);
    }

    @Override
    public String toString() {
        String str = "";
        for(Token token : tokenList){
            str += token.getTokenType() + " --> " + token.getTokenString() + "\n";
        }
        return str;
    }
}
