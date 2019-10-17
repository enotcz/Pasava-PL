package parser.elements.blocks;

import exceptions.AtributException;
import exceptions.ParserException;
import parser.elements.statements.IStatement;

import java.util.ArrayList;
import java.util.List;

public class StatementBlock implements IBlock {
    List<IStatement> statementList;

    public StatementBlock(List<IStatement> statementList) {
        this.statementList = statementList;
    }

    @Override
    public String toString() {
        String str = "";
        for(IStatement statement : statementList){
            str += statement.toString();
        }
        return str;
    }

    @Override
    public void execute() throws ParserException, AtributException {
        for (IStatement statement : statementList){
            statement.execute();
        }
    }
}
