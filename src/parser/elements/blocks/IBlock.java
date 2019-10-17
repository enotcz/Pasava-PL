package parser.elements.blocks;

import exceptions.AtributException;
import exceptions.ParserException;
import parser.elements.statements.IStatement;

public interface IBlock {
    void execute() throws ParserException, AtributException;
}
