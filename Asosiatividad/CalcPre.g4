grammar CalcPre;

prog: stat* EOF ;
stat: expr NEWLINE                                   # printExpr
    | ID '=' expr NEWLINE                            # assign
    | NEWLINE                                        # blank
    ;

expr: mulLevel ;

mulLevel
    : sumLevel (OPMUL sumLevel)*   // * agrupa sumar como operadores de *
    ;

sumLevel
    : atom (OPSUMA atom)*           // + bmayor precedencia
    ;

atom
    : INT
    | ID
    | '(' expr ')'
    ;

OPSUMA : [+\-] ;
OPMUL  : [*/] ;
ID     : [a-zA-Z_][a-zA-Z_0-9]* ;
INT    : [0-9]+ ;
NEWLINE: [\r\n]+ ;
WS     : [ \t]+ -> skip ;

