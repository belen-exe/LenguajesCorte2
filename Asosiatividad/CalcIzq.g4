grammar CalcIzq;

prog:   stat* EOF ;

stat:   expr NEWLINE                # printExpr
    |   ID '=' expr NEWLINE         # assign
    |   NEWLINE                     # blanco
    ;

expr
    : expr OPSUMA expr              # AddSub    // left-recursive: produces left-assoc for +/-
    | expr OPMUL expr               # MulDiv    // left-recursive: produces left-assoc for */ 
    | INT                           # Int
    | ID                            # Id
    | '(' expr ')'                  # Parens
    ;

OPSUMA : [+\-] ;
OPMUL  : [*/] ;
ID     : [a-zA-Z_][a-zA-Z_0-9]* ;
INT    : [0-9]+ ;
NEWLINE: [\r\n]+ ;
WS     : [ \t]+ -> skip ;

