import java.util.HashMap;
import java.util.Map;

public class EvalVisitorPre extends CalcPreBaseVisitor<Double> {
    Map<String, Double> memory = new HashMap<>();

    @Override
    public Double visitAssign(CalcPreParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        double value = visit(ctx.expr());
        memory.put(id, value);
        return value;
    }

    @Override
    public Double visitPrintExpr(CalcPreParser.PrintExprContext ctx) {
        Double value = visit(ctx.expr());
        System.out.println(value);
        return 0.0;
    }

    // expr: mulLevel ;
    @Override
    public Double visitExpr(CalcPreParser.ExprContext ctx) {
        return visit(ctx.mulLevel());
    }

    // mulLevel: sumLevel (OPMUL sumLevel)* ;
    @Override
    public Double visitMulLevel(CalcPreParser.MulLevelContext ctx) {
        double result = visit(ctx.sumLevel(0));
        for (int i = 1; i < ctx.sumLevel().size(); i++) {
            double rhs = visit(ctx.sumLevel(i));
            String op = ctx.OPMUL(i - 1).getText();
            if (op.equals("*")) result *= rhs;
            else result /= rhs;
        }
        return result;
    }

    // sumLevel: atom (OPSUMA atom)* ;
    @Override
    public Double visitSumLevel(CalcPreParser.SumLevelContext ctx) {
        double result = visit(ctx.atom(0));
        for (int i = 1; i < ctx.atom().size(); i++) {
            double rhs = visit(ctx.atom(i));
            String op = ctx.OPSUMA(i - 1).getText();
            if (op.equals("+")) result += rhs;
            else result -= rhs;
        }
        return result;
    }

    // atom: INT | ID | '(' expr ')' ;
    @Override
    public Double visitAtom(CalcPreParser.AtomContext ctx) {
        if (ctx.INT() != null) {
            return Double.valueOf(ctx.INT().getText());
        }
        if (ctx.ID() != null) {
            return memory.getOrDefault(ctx.ID().getText(), 0.0);
        }
        return visit(ctx.expr()); // paréntesis
    }
}

