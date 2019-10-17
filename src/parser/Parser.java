package parser;

import exceptions.AtributException;
import exceptions.IdentifierException;
import exceptions.ParserException;
import parser.elements.blocks.*;
import parser.elements.conditions.*;
import parser.elements.expressions.*;
import parser.elements.factors.*;
import parser.elements.identifiers.Identifier;
import parser.elements.identifiers.Identifiers;
import parser.elements.procedures.Procedure;
import parser.elements.procedures.Procedures;
import program.IProgram;
import program.Program;
import parser.elements.statements.*;
import parser.elements.terms.*;
import parser.elements.variables.*;
import token.*;

import java.util.ArrayList;
import java.util.List;

import static token.TokenType.*;

public class Parser {

    private Tokens tokens;

    private Identifier programName;
    private Identifiers identifiers;
    private Attributes attributes;
    private Procedures procedures;

    public Parser() {
    }


    public IProgram parse(List<Token> tokens) throws ParserException, IdentifierException, AtributException {
        this.tokens = new Tokens(tokens);
        this.identifiers = new Identifiers();
        this.attributes = new Attributes();
        this.procedures = new Procedures();
        return parseProgram();
    }

    private IProgram parseProgram() throws ParserException, IdentifierException, AtributException {
        tokens.getToken(TokenType.PROGRAM);
        programName = new Identifier(tokens.getToken(TokenType.IDENTIFIER).getTokenString());
        identifiers.add(programName);
        tokens.getToken(TokenType.SEMICOLON);
        while (true) {
            if (tokens.peekToken().getTokenType() == TokenType.VAR) {
                parseVariableBlock(identifiers, attributes);
                continue;
            }
            if (tokens.peekToken().getTokenType() == TokenType.CONST) {
                parseConstBlock(identifiers, attributes);
                continue;
            }
            if (tokens.peekToken().getTokenType() == TokenType.PROCEDURE) {
                parseProcedure(identifiers);
                continue;
            }
            break;
        }

        IBlock programBody = parseStatementBlock(identifiers, attributes);
        tokens.getToken(TokenType.DOT);
        return new Program(programName, identifiers, attributes, procedures, programBody);
    }

    private void parseVariableBlock(Identifiers identifiers, Attributes attributes) throws ParserException, IdentifierException, AtributException {
        tokens.getToken(TokenType.VAR);
        String name = tokens.getToken(TokenType.IDENTIFIER).getTokenString();
        double value = 0;
        if (tokens.peekToken().getTokenType() == TokenType.ASSIGNMENT) {
            tokens.getToken(TokenType.ASSIGNMENT);
            value = Double.parseDouble(tokens.getToken(TokenType.NUMBER).getTokenString());
        }
        Identifier identifier = new Identifier(name);
        identifiers.add(identifier);
        attributes.add(new Variable(identifier, value));
        while (tokens.peekToken().getTokenType() == TokenType.COMMA) {
            tokens.getToken(TokenType.COMMA);
            name = tokens.getToken(TokenType.IDENTIFIER).getTokenString();
            value = 0;
            if (tokens.peekToken().getTokenType() == TokenType.ASSIGNMENT) {
                tokens.getToken(TokenType.ASSIGNMENT);
                value = Double.parseDouble(tokens.getToken(TokenType.NUMBER).getTokenString());
            }
            identifier = new Identifier(name);
            identifiers.add(identifier);
            attributes.add(new Variable(identifier, value));
        }
        tokens.getToken(TokenType.SEMICOLON);
    }

    private void parseConstBlock(Identifiers identifiers, Attributes attributes) throws ParserException, IdentifierException, AtributException {
        tokens.getToken(TokenType.CONST);
        String name = tokens.getToken(TokenType.IDENTIFIER).getTokenString();
        tokens.getToken(TokenType.ASSIGNMENT);
        double value = Double.parseDouble(tokens.getToken(TokenType.NUMBER).getTokenString());
        Identifier identifier = new Identifier(name);
        identifiers.add(identifier);
        attributes.add(new Constant(identifier, value));
        while (tokens.peekToken().getTokenType() != TokenType.SEMICOLON) {
            tokens.getToken(TokenType.COMMA);
            name = tokens.getToken(TokenType.IDENTIFIER).getTokenString();
            tokens.getToken(TokenType.ASSIGNMENT);
            value = Double.parseDouble(tokens.getToken(TokenType.NUMBER).getTokenString());
            identifier = new Identifier(name);
            identifiers.add(identifier);
            attributes.add(new Constant(identifier, value));
        }
        tokens.getToken(TokenType.SEMICOLON);
    }

    private void parseProcedure(Identifiers identifiers) throws ParserException, IdentifierException, AtributException {
        tokens.getToken(PROCEDURE);
        String nameOfProcedure = tokens.getToken(IDENTIFIER).getTokenString();
        Identifier procedureIdentifier = new Identifier(nameOfProcedure);
        identifiers.add(procedureIdentifier);
        tokens.getToken(OPEN_BRACKET);
        Identifiers procedureIdentifiers = new Identifiers();
        Attributes procedureParams = new Attributes();
        Attributes procedureAttributes = new Attributes();
        while (tokens.peekToken().getTokenType() == PARAM) {
            tokens.getToken(PARAM);
            Identifier paramIdentifier = new Identifier(tokens.getToken(IDENTIFIER).getTokenString());
            procedureIdentifiers.add(paramIdentifier);
            Attribute attribute = new Variable(paramIdentifier);
            procedureParams.add(attribute);
            procedureAttributes.add(attribute);
            if (tokens.peekToken().getTokenType() == CLOSE_BRACKET) break;
            tokens.getToken(COMMA);
        }
        tokens.getToken(CLOSE_BRACKET);
        if (tokens.peekToken().getTokenType() == VAR) {
            parseVariableBlock(procedureIdentifiers, procedureAttributes);
        }

        IBlock block = parseStatementBlock(procedureIdentifiers, procedureAttributes);
        procedures.add(new Procedure(procedureIdentifier, procedureParams, block));
        tokens.getToken(SEMICOLON);
    }

    private IBlock parseStatementBlock(Identifiers identifiers, Attributes attributes) throws ParserException, AtributException {
        List<IStatement> statements = new ArrayList<>();
        tokens.getToken(BEGIN);
        statements.add(parseStatement(identifiers, attributes));
        while (tokens.peekToken().getTokenType() != END) {
            IStatement statement = parseStatement(identifiers, attributes);
            if (statement != null)
                statements.add(statement);
        }
        tokens.getToken(END);
        return new StatementBlock(statements);
    }

    private IStatement parseStatement(Identifiers identifiers, Attributes attributes) throws ParserException, AtributException {
        IStatement statement = null;
        switch (tokens.peekToken().getTokenType()) {
            case IDENTIFIER:
                    statement = parseSetStatement(identifiers,attributes);
                break;
            case IF:
                statement = parseIfStatement(identifiers,attributes);
                break;
            case WHILE:
                statement = parseWhileStatement(identifiers, attributes);
                break;
            case BEGIN:
                statement = new BlockStatement(parseStatementBlock(identifiers, attributes));
                tokens.getToken(TokenType.SEMICOLON);
                break;
            case PRINT:
            case PRINTLN:
                statement = new BlockStatement(parsePrintBlock(identifiers, attributes));
                break;
            case RUN:
                statement = parseProcedureStatement(identifiers, attributes);
                break;
            default:
                tokens.getToken(IDENTIFIER, IF, WHILE, BEGIN, PRINT, RUN);
        }
        return statement;
    }

    private IStatement parseSetStatement(Identifiers identifiers, Attributes attributes) throws ParserException, AtributException {
        String name = tokens.getToken(TokenType.IDENTIFIER).getTokenString();
        Attribute attribute = attributes.get(name);
        tokens.getToken(TokenType.ASSIGNMENT);
        IExpression expression = parseExpression(identifiers, attributes);
        tokens.getToken(TokenType.SEMICOLON);
        return new SetStatement(attribute, expression);
    }

    private IStatement parseIfStatement(Identifiers identifiers, Attributes attributes) throws ParserException, AtributException {
        tokens.getToken(TokenType.IF);
        tokens.getToken(TokenType.OPEN_BRACKET);
        ICondition ifCondition = parseCondition(identifiers, attributes);
        tokens.getToken(TokenType.CLOSE_BRACKET);
        IBlock trueBlock = parseStatementBlock(identifiers, attributes);
        tokens.getToken(SEMICOLON);
        IBlock falseBlock = null;
        if (tokens.peekToken().getTokenType() == TokenType.ELSE) {
            tokens.getToken(TokenType.ELSE);
            falseBlock = parseStatementBlock(identifiers, attributes);
            tokens.getToken(SEMICOLON);
        }
        return new IfStatement(ifCondition, trueBlock, falseBlock);
    }

    private IStatement parseWhileStatement(Identifiers identifiers, Attributes attributes) throws ParserException, AtributException {
        tokens.getToken(TokenType.WHILE);
        tokens.getToken(TokenType.OPEN_BRACKET);
        ICondition whileCondition = parseCondition(identifiers, attributes);
        tokens.getToken(TokenType.CLOSE_BRACKET);
        IBlock whileBlock = parseStatementBlock(identifiers, attributes);
        tokens.getToken(SEMICOLON);
        return new WhileStatement(whileCondition, whileBlock);
    }

    private IStatement parseProcedureStatement(Identifiers identifiers, Attributes attributes) throws ParserException, AtributException {
        tokens.getToken(RUN);
        String procedureIdentifier = tokens.getToken(IDENTIFIER).getTokenString();
        tokens.getToken(OPEN_BRACKET);
        Attributes procedureParams = new Attributes();
        while (tokens.peekToken().getTokenType() == IDENTIFIER || tokens.peekToken().getTokenType() == MINUS
                || tokens.peekToken().getTokenType() == NUMBER || tokens.peekToken().getTokenType() == OUT) {
            double value = 0;
            switch (tokens.peekToken().getTokenType()){
                case NUMBER:
                    value = Double.parseDouble(tokens.getToken(NUMBER).getTokenString());
                    procedureParams.add(new Parametr(new Identifier("$_$" + procedureParams.getSize()), value));
                    break;
                case MINUS:
                    tokens.getToken(MINUS);
                    value = Double.parseDouble(tokens.getToken(NUMBER).getTokenString());
                    procedureParams.add(new Parametr(new Identifier("$_$" + procedureParams.getSize()), -value));
                    break;
                case IDENTIFIER:
                    value = attributes.get(tokens.getToken(IDENTIFIER).getTokenString()).getValue();
                    procedureParams.add(new Parametr(new Identifier("$_$" + procedureParams.getSize()), value));
                    break;
                case OUT:
                    tokens.getToken(OUT);
                    procedureParams.add(attributes.get(tokens.getToken(IDENTIFIER).getTokenString()));
                    break;
            }

            if (tokens.peekToken().getTokenType() == CLOSE_BRACKET) break;
            tokens.getToken(COMMA);
        }
        tokens.getToken(CLOSE_BRACKET);
        tokens.getToken(SEMICOLON);
        return new ProcedureStatement(procedures.get(procedureIdentifier), procedureParams);
    }

    private ICondition parseCondition(Identifiers identifiers, Attributes attributes) throws ParserException, AtributException {
        IExpression leftExpression = parseExpression(identifiers, attributes);
        TokenType type = tokens.getToken(TokenType.EQUAL, TokenType.GREATER, TokenType.LESS, TokenType.NOTEQUAL).getTokenType();
        IExpression rightExpression = parseExpression(identifiers, attributes);
        switch (type) {
            case EQUAL:
                return new EqualCondition(leftExpression, rightExpression);
            case GREATER:
                return new GreaterCondition(leftExpression, rightExpression);
            case LESS:
                return new LessCondition(leftExpression, rightExpression);
            case NOTEQUAL:
                return new NotEqualCondition(leftExpression, rightExpression);
        }
        throw new ParserException();
    }

    private IBlock parsePrintBlock(Identifiers identifiers, Attributes attributes) throws ParserException, AtributException {
        List<Object> objectList = new ArrayList<>();
        boolean printLn = false;
        Token token = tokens.getToken(PRINT,PRINTLN);
        if (token.getTokenType() == PRINTLN) {
            printLn = true;
        }
        tokens.getToken(OPEN_BRACKET);
        token = tokens.peekToken();
        switch (token.getTokenType()) {
            case PLUS:
            case MINUS:
            case NUMBER:
            case IDENTIFIER:
                objectList.add(parseExpression(identifiers, attributes));
                break;
            case STRING:
                tokens.getToken(TokenType.STRING);
                objectList.add(token.getTokenString().replaceAll("\"", ""));
                break;
            default:
                throw new ParserException();
        }
        while (tokens.peekToken().getTokenType() == TokenType.COLON) {
            tokens.getToken(TokenType.COLON);
            token = tokens.peekToken();
            switch (token.getTokenType()) {
                case PLUS:
                case MINUS:
                case NUMBER:
                case IDENTIFIER:
                    objectList.add(parseExpression(identifiers, attributes));
                    break;
                case STRING:
                    tokens.getToken(TokenType.STRING);
                    objectList.add(token.getTokenString());
                    break;
                default:
                    throw new ParserException();
            }
        }
        tokens.getToken(CLOSE_BRACKET);
        tokens.getToken(SEMICOLON);
        return new PrintBlock(objectList, printLn);
    }


    private IExpression parseExpression(Identifiers identifiers, Attributes attributes) throws ParserException, AtributException {
        IExpression expression = null;
        switch (tokens.peekToken().getTokenType()) {
            case MINUS:
                tokens.getToken(MINUS);
                expression = new Expression(MINUS, parseTerm(identifiers, attributes));
                break;
            case PLUS:
                tokens.getToken(PLUS);
                expression = new Expression(PLUS, parseTerm(identifiers, attributes));
                break;
            default:
                expression = new Expression(PLUS, parseTerm(identifiers, attributes));
                break;
        }
        while (tokens.peekToken().getTokenType() == MINUS || tokens.peekToken().getTokenType() == PLUS) {
            switch (tokens.getToken(TokenType.PLUS, MINUS).getTokenType()) {
                case MINUS:
                    expression.addTerm(MINUS, parseTerm(identifiers, attributes));
                    break;
                case PLUS:
                    expression.addTerm(TokenType.PLUS, parseTerm(identifiers, attributes));
                    break;
            }
        }
        return expression;
    }

    private ITerm parseTerm(Identifiers identifiers, Attributes attributes) throws ParserException, AtributException {
        ITerm term = new Term(parseFactor(identifiers, attributes));
        while (tokens.peekToken().getTokenType() == TokenType.MULTIPLY || tokens.peekToken().getTokenType() == TokenType.DIVIDE) {
            switch (tokens.getToken(TokenType.MULTIPLY, TokenType.DIVIDE).getTokenType()) {
                case MULTIPLY:
                    term.addFactor(MULTIPLY, parseFactor(identifiers, attributes));
                    break;
                case DIVIDE:
                    term.addFactor(DIVIDE, parseFactor(identifiers, attributes));
                    break;
            }
        }
        return term;
    }

    private IFactor parseFactor(Identifiers identifiers, Attributes attributes) throws ParserException, AtributException {
        IFactor factor = null;
        Token token = tokens.getToken(TokenType.IDENTIFIER, TokenType.NUMBER, TokenType.OPEN_BRACKET);
        switch (token.getTokenType()) {
            case OPEN_BRACKET:
                factor = new ExpressionFactor(parseExpression(identifiers, attributes));
                tokens.getToken(TokenType.CLOSE_BRACKET);
                break;
            case IDENTIFIER:
                factor = new IdentifierFactor(attributes.get(token.getTokenString()));
                break;
            case NUMBER:
                factor = new NumberFactor(Double.parseDouble(token.getTokenString()));
                break;
        }
        return factor;
    }
}
