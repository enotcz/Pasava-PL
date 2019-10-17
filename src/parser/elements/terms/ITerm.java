package parser.elements.terms;

import exceptions.ParserException;
import parser.elements.factors.IFactor;
import token.TokenType;

public interface ITerm {
    void addFactor(TokenType sign, IFactor factor) throws ParserException;
    double getResult();
}
