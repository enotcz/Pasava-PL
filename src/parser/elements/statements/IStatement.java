package parser.elements.statements;

import exceptions.AtributException;
import exceptions.ParserException;

public interface IStatement {
    void execute() throws ParserException, AtributException;
}
