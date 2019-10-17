package parser.elements.statements;

import exceptions.AtributException;
import exceptions.ParserException;
import parser.elements.blocks.IBlock;
import parser.elements.conditions.ICondition;

public class WhileStatement implements IStatement {
    private ICondition condition;
    private IBlock block;

    public WhileStatement(ICondition condition, IBlock block) {
        this.condition = condition;
        this.block = block;
    }

    @Override
    public void execute() throws ParserException, AtributException {
        while (condition.getResult()){
            block.execute();
        }
    }

    @Override
    public String toString() {
        return "WHILE (" + condition.toString() + ") \n {" + block.toString() + "}\n";
    }
}
