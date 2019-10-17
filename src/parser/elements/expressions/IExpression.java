package parser.elements.expressions;

import exceptions.ParserException;
import parser.elements.terms.ITerm;
import token.TokenType;

public interface IExpression {
    double getResult();
    void addTerm(TokenType sign, ITerm term) throws ParserException;
}
