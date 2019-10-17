package parser.elements.procedures;

import exceptions.AtributException;
import exceptions.ParserException;
import parser.elements.blocks.IBlock;
import parser.elements.identifiers.Identifier;
import parser.elements.variables.Attributes;

public class Procedure  {
    private Identifier identifier;
    private Attributes params;
    private IBlock statements;

    public Procedure(Identifier identifier, Attributes params, IBlock statements) {
        this.identifier = identifier;
        this.params = params;
        this.statements = statements;
    }

    public Attributes getParams() {
        return params;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setStatements(IBlock statements) {
        this.statements = statements;
    }

    public void execute() throws AtributException, ParserException {
        statements.execute();
    }

    public void setParams(Attributes params) throws AtributException {
        if (this.params.getSize() != params.getSize()){
            throw new AtributException("Wrong params count in procedure: "  + identifier.getName());
        }
        this.params.setAttributesValues(params);
    }
}
