package parser.elements.identifiers;

import exceptions.IdentifierException;
import exceptions.ParserException;

import java.util.ArrayList;
import java.util.List;

public class Identifiers {
    private List<Identifier> identifiers;

    public Identifiers(){
        this.identifiers = new ArrayList<>();
    }
    public Identifiers(List<Identifier> identifiers){
        this.identifiers = identifiers;
    }

    public Identifier get(String name) throws IdentifierException {
        for (Identifier identifier : identifiers){
            if (identifier.getName().equals(name)){
                return identifier;
            }
        }
        throw new IdentifierException("Identifier " + name + " doesn't exist");
    }


    public boolean contains(String name){
        for (Identifier identifier : identifiers){
            if (identifier.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void add(Identifier identifier) throws IdentifierException {
        if (contains(identifier.getName())){
            throw new IdentifierException();
        }
        identifiers.add(identifier);
    }
}
