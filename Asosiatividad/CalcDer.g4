grammar CalcDer;

prog:   stat* EOF ;

stat:   expr NEWLINE                # printExpr
    |   ID '=' expr NEWLINE         # assign
    |   NEWLINE                     # blank
    ;

expr
    : term (OPSUMA expr)?           # AddSubRight
    ;

term
    : factor (OPMUL factor)*        # MulDiv
    ;

factor
    : INT                           # Int
    | ID                            # Id
    | '(' expr ')'                  # Parens
    ;

OPSUMA : [+\-] ;
OPMUL  : [*/] ;
ID     : [a-zA-Z_][a-zA-Z_0-9]* ;
INT    : [0-9]+ ;
NEWLINE: [\r\n]+ ;
WS     : [ \t]+ -> skip ;

