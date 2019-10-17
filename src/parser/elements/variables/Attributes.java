package parser.elements.variables;

import exceptions.AtributException;

import java.util.ArrayList;
import java.util.List;

public class Attributes {
    private List<Attribute> attributeList;


    public Attributes() {
        attributeList = new ArrayList<>();
    }

    public Attributes(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

    public boolean contains(Attribute attribute){
        for (Attribute att : attributeList){
            if (att.getName().equals(attribute.getName())){
                return true;
            }
        }
        return false;
    }

    public void add(Attribute attribute) throws AtributException {
        if (contains(attribute)){
            throw new AtributException("Attribute " + attribute.getName() + " exists");
        }
        attributeList.add(attribute);
    }

    public void setAttributesValues(Attributes attributes) throws AtributException {
        if (attributeList.size() != attributes.attributeList.size()){
            throw new AtributException("Wrong attributes count");
        }
        for (int i = 0; i < attributeList.size(); i++) {
            attributeList.get(i).setValue(attributes.attributeList.get(i).getValue());
        }
    }

    public Attribute get(String name) throws AtributException {
        for (Attribute attribute : attributeList){
            if (attribute.getName().equals(name)){
                return attribute;
            }
        }
        throw new AtributException("Attribute " + name + " doesn't exist");
    }

    public int getSize(){
        return attributeList.size();
    }
}
