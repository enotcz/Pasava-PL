package parser.elements.blocks;

import parser.elements.expressions.Expression;
import parser.elements.expressions.IExpression;

import java.util.List;

public class PrintBlock implements IBlock {
    private List<Object> list;
    private boolean printLn;

    public PrintBlock(List<Object> list, boolean printLn) {
        this.list = list;
        this.printLn = printLn;
    }

    @Override
    public String toString() {
        String str = "PRINT(";
        for(Object o : list){
            str += o.toString() + " ";
        }
        str+=")\n";
        return str;
    }

    @Override
    public void execute() {
        String result="";
        for (Object object : list){
            if (object instanceof IExpression){
                result += ((IExpression)object).getResult();
            }
            if (object instanceof String){
                result += object;
            }
        }
        result = result.replaceAll("\"", "");
        if (printLn){
            System.out.println(result);
        } else{
            System.out.print(result);
        }

    }


}
