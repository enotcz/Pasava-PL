package parser.elements.terms;


import exceptions.ParserException;
import javafx.util.Pair;
import parser.elements.factors.IFactor;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Term implements ITerm {
    private IFactor factor;
    private List<Pair<TokenType, IFactor>> factorList;

    public Term(IFactor factor) {
        this.factor = factor;
        factorList = new ArrayList<Pair<TokenType, IFactor>>();
    }

    @Override
    public void addFactor(TokenType sign, IFactor factor) throws ParserException {
        if (sign != TokenType.DIVIDE && sign != TokenType.MULTIPLY){
            throw new ParserException("Token type must be DIVIDE or MULTIPLY, but current TokenType is" + sign);
        }
        factorList.add(new Pair<>(sign, factor));
    }

    @Override
    public double getResult() {
        double result = factor.getValue();
        for (Pair<TokenType, IFactor> pair: factorList){
            switch (pair.getKey()){
                case MULTIPLY:
                    result*= pair.getValue().getValue();
                    break;
                case DIVIDE:
                    result/= pair.getValue().getValue();
                    break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        String str = factor.toString();
        for (Pair<TokenType, IFactor> pair : factorList){
            str += pair.getKey() + " " + pair.getValue();
        }
        return str;
    }
}
