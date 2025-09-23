import java.util.HashMap;
import java.util.Map;

public class EvalVisitorIzq extends CalcIzqBaseVisitor<Double> {
    Map<String, Double> memory = new HashMap<>();

    @Override
    public Double visitAssign(CalcIzqParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        double value = visit(ctx.expr());
        memory.put(id, value);
        return value;
    }

    @Override
    public Double visitPrintExpr(CalcIzqParser.PrintExprContext ctx) {
        Double value = visit(ctx.expr());
        System.out.println(value);
        return 0.0;
    }

    @Override
    public Double visitInt(CalcIzqParser.IntContext ctx) {
        return Double.valueOf(ctx.INT().getText());
    }

    @Override
    public Double visitId(CalcIzqParser.IdContext ctx) {
        String id = ctx.ID().getText();
        return memory.getOrDefault(id, 0.0);
    }

    @Override
    public Double visitParens(CalcIzqParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Double visitMulDiv(CalcIzqParser.MulDivContext ctx) {
        double left = visit(ctx.expr(0));
        double right = visit(ctx.expr(1));
        String op = ctx.getChild(1).getText();
        if (op.equals("*")) return left * right;
        return left / right;
    }

    @Override
    public Double visitAddSub(CalcIzqParser.AddSubContext ctx) {
        double left = visit(ctx.expr(0));
        double right = visit(ctx.expr(1));
        String op = ctx.getChild(1).getText();
        if (op.equals("+")) return left + right;
        return left - right;
    }
}

