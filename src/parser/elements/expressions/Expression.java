package parser.elements.expressions;

import exceptions.ParserException;
import javafx.util.Pair;
import parser.elements.terms.ITerm;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Expression implements IExpression {
    private List<Pair<TokenType,ITerm>> termList;

    public Expression(TokenType sign, ITerm term) throws ParserException {
        termList = new ArrayList<>();
        addTerm(sign,term);
    }

    @Override
    public double getResult() {
        double result = 0;
        for (Pair<TokenType,ITerm> pair : termList){
            if (pair.getKey() == TokenType.PLUS){
                result += pair.getValue().getResult();
            }else{
                result -= pair.getValue().getResult();
            }
        }
        return result;
    }

    @Override
    public String toString() {
        String str = "";
        for (Pair<TokenType,ITerm> pair : termList){
            str += pair.getKey() + " "+ pair.getValue().toString();
        }
        return str;
    }

    @Override
    public void addTerm(TokenType sign, ITerm term) throws ParserException {
        if (sign != TokenType.PLUS && sign != TokenType.MINUS){
            throw new ParserException("Token type must be PLUS or MINUS, but current TokenType is" + sign);
        }
        termList.add(new Pair<>(sign,term));
    }
}
