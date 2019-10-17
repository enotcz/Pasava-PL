package program;

import exceptions.AnalyzerException;
import exceptions.AtributException;
import exceptions.IdentifierException;
import exceptions.ParserException;
import lexer.Lexer;
import parser.Parser;
import parser.elements.blocks.IBlock;
import parser.elements.identifiers.Identifier;
import parser.elements.identifiers.Identifiers;
import parser.elements.procedures.Procedures;
import parser.elements.variables.Attributes;
import token.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Program implements IProgram {
    private Identifier programName;
    private Identifiers identifiers;
    private Attributes attributes;
    private Procedures procedures;
    private IBlock programBody;

    public Program(Identifier programName, Identifiers identifiers, Attributes attributes, Procedures procedures, IBlock programBody) {
        this.programName = programName;
        this.identifiers = identifiers;
        this.attributes = attributes;
        this.procedures = procedures;
        this.programBody = programBody;
    }

    @Override
    public void runProgram() throws AtributException, ParserException {
        System.out.println("Run " + programName.getName());
        System.out.println("---------------");
        programBody.execute();
    }
}
