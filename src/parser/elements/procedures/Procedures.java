package parser.elements.procedures;

import exceptions.ParserException;

import java.util.ArrayList;
import java.util.List;

public class Procedures {
    private List<Procedure> procedureList;

    public Procedures() {
        procedureList = new ArrayList<>();
    }

    public boolean contains(String name){
        for (Procedure proc : procedureList){
            if (proc.getIdentifier().getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void add(Procedure procedure) throws ParserException {
        if (contains(procedure.getIdentifier().getName())){
            throw new ParserException("Procedure " + procedure.getIdentifier().getName() + " exists");
        }
        procedureList.add(procedure);
    }

    public Procedure get(String name) throws ParserException {
        for (Procedure proc : procedureList){
            if (proc.getIdentifier().getName().equals(name)){
                return proc;
            }
        }
        throw new ParserException("Procedure " + name + " doesn't exists");
    }


}
