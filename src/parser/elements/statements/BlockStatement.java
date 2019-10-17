package parser.elements.statements;

import exceptions.AtributException;
import exceptions.ParserException;
import parser.elements.blocks.IBlock;

public class BlockStatement implements IStatement {
    private IBlock block;

    public BlockStatement(IBlock block) {
        this.block = block;
    }

    @Override
    public void execute() throws ParserException, AtributException {
        block.execute();
    }

    @Override
    public String toString() {
        return block.toString();
    }
}
