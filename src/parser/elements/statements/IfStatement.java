package parser.elements.statements;

import exceptions.AtributException;
import exceptions.ParserException;
import parser.elements.blocks.IBlock;
import parser.elements.conditions.ICondition;

public class IfStatement implements IStatement {
    private ICondition condition;
    private IBlock statement;
    private IBlock elseStatement;

    public IfStatement(ICondition condition, IBlock statement) {
        this.condition = condition;
        this.statement = statement;
        this.elseStatement = null;
    }

    public IfStatement(ICondition condition, IBlock statement, IBlock elseStatement) {
        this.condition = condition;
        this.statement = statement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() throws ParserException, AtributException {
        if (condition.getResult()){
            statement.execute();
        }else {
            if (elseStatement != null){
                elseStatement.execute();
            }
        }
    }

    @Override
    public String toString() {
        return "IF(" + condition.toString() + ") {\n    " + statement.toString() + "    }\n    ELSE {\n" + (elseStatement != null ? elseStatement.toString() : "") + "  }\n";
    }
}
