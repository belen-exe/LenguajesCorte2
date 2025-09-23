import java.util.HashMap;
import java.util.Map;

public class EvalVisitorDer extends CalcDerBaseVisitor<Double> {
    Map<String, Double> memory = new HashMap<>();

    @Override
    public Double visitAssign(CalcDerParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        double value = visit(ctx.expr());
        memory.put(id, value);
        return value;
    }

    @Override
    public Double visitPrintExpr(CalcDerParser.PrintExprContext ctx) {
        Double value = visit(ctx.expr());
        System.out.println(value);
        return 0.0;
    }

    @Override
    public Double visitInt(CalcDerParser.IntContext ctx) {
        return Double.valueOf(ctx.INT().getText());
    }

    @Override
    public Double visitId(CalcDerParser.IdContext ctx) {
        String id = ctx.ID().getText();
        return memory.getOrDefault(id, 0.0);
    }

    @Override
    public Double visitParens(CalcDerParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Double visitAddSubRight(CalcDerParser.AddSubRightContext ctx) {
        double left = visit(ctx.term());
        if (ctx.OPSUMA() != null) {
            double right = visit(ctx.expr());
            String op = ctx.OPSUMA().getText();
            if (op.equals("+")) return left + right;
            else return left - right;
        }
        return left;
    }

    @Override
    public Double visitMulDiv(CalcDerParser.MulDivContext ctx) {
        double result = visit(ctx.factor(0));
        for (int i = 1; i < ctx.factor().size(); i++) {
            double rhs = visit(ctx.factor(i));
            String op = ctx.OPMUL(i - 1).getText();
            if (op.equals("*")) result *= rhs;
            else result /= rhs;
        }
        return result;
    }
}

