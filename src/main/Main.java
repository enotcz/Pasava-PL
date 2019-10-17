package main;

import exceptions.AnalyzerException;
import exceptions.AtributException;
import exceptions.IdentifierException;
import exceptions.ParserException;
import lexer.Lexer;
import parser.Parser;
import program.IProgram;
import token.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws AnalyzerException {
        try {
            StringBuffer source = new StringBuffer();
            Scanner sc = new Scanner(new File("testProgram.txt"));
            while (sc.hasNextLine()) {
                source.append(sc.nextLine() + "\n");
            }
            source.deleteCharAt(source.length() - 1);
            sc.close();
            Lexer l = new Lexer();
            List<Token> list = l.getTokens(source.toString());
            Parser parser = new Parser();
            IProgram program = parser.parse(list);
            program.runProgram();
        } catch (ParserException | FileNotFoundException | IdentifierException | AtributException e) {
            e.printStackTrace();
        }

    }
}
