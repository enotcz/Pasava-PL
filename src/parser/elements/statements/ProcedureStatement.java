package parser.elements.statements;

import exceptions.AtributException;
import exceptions.ParserException;
import parser.elements.procedures.Procedure;
import parser.elements.variables.Attributes;

public class ProcedureStatement implements IStatement {
    private Procedure procedure;
    private Attributes params;

    public ProcedureStatement(Procedure procedure, Attributes params) {
        this.procedure = procedure;
        this.params = params;
    }

    @Override
    public void execute() throws ParserException, AtributException {
        procedure.setParams(params);
        procedure.execute();
        params.setAttributesValues(procedure.getParams());
    }
}
