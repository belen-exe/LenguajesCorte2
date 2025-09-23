import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Calc {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Uso: java CalcMain [Izq|Der|Pre]");
            return;
        }

        String mode = args[0]; // "Izq", "Der" o "Pre"
        String fileName = args[1]; // archivo con expresiones

        CharStream input = CharStreams.fromFileName(fileName);

        switch (mode) {
            case "Izq": {
                CalcIzqLexer lexer = new CalcIzqLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                CalcIzqParser parser = new CalcIzqParser(tokens);
                ParseTree tree = parser.prog();
                EvalVisitorIzq eval = new EvalVisitorIzq();
                eval.visit(tree);
                break;
            }
            case "Der": {
                CalcDerLexer lexer = new CalcDerLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                CalcDerParser parser = new CalcDerParser(tokens);
                ParseTree tree = parser.prog();
                EvalVisitorDer eval = new EvalVisitorDer();
                eval.visit(tree);
                break;
            }
            case "Pre": {
                CalcPreLexer lexer = new CalcPreLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                CalcPreParser parser = new CalcPreParser(tokens);
                ParseTree tree = parser.prog();
                EvalVisitorPre eval = new EvalVisitorPre();
                eval.visit(tree);
                break;
            }
            default:
                System.err.println("No vale");
        }
    }
}

