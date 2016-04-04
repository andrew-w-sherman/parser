Andrew Sherman's BPL Parser!!! <asherman@oberlin.edu>

README ====================================

Hello! I hope grading is going well! Here is my readme. I included a simple java makefile in my handin (it just runs javac and removes class files) so you can use make to compile, and then use java bpl.Bpl <filename> to run (both from the root). If you're feeling fancy, you can use ./gbc <filename> to run instead. (GNU bob compiler) And of course since it uses stdout you can use > to redirect to a file.

THE TYPE-CHECKER ==========================

Not too much to tell about it, continues with my somewhat cumbersome but more correct-feeling (to me) implementation. Every expression is assigned a type on the second pass but the recursive function also returns the type assigned to the child it was called on. I didn't have as much time to test as I might have liked but it seems like everything is working as it should be.

I implemented isLNode in the parser so I didn't have to do any checking for that in my typechecker (which is why they come up as parser errors). The way I handle pointers between FNodes and Variable nodes is still a little hacky, but it seems to work fine.

The debug printing is a little ugly but I made some little improvements so it's more readable, I name expressions differently if they're assignments, for instance.



OLDER STUFF (ABOUT THE PARSER) ============================================
===========================================================================
===========================================================================





THE TREE ==================================

The tree should be relatively self-explanatory, it is a preorder print that indents based on the depth of the current item. Nexts, where applicable, print as if they were the next child. This would be confusing were it not for the fact that each thing with a child that can have nexts only can have one child. (It should be obvious which these are, and if not I've commented my grammar below with #uses next on the nodes that do).

ABOUT THE GRAMMAR =========================
I made relatively few changes to the grammar, but there are two of note. Rather than EXPRESSION -> VAR = EXPRESSION | COMP EXP, I have COMP_EXP -> COMP_EXP = EXPRESSION | COMP_EXP. Not only does this make it possible to parse, but it also gives proper prescedence between relops and assignments. The only issue is the possibility of a non-assignable left-hand side, but we simply use a recursive function isLNode to check if it's assignable after the fact. In an effort to make the tree structure less silly, I actually grab the var from inside the comp exp and set that as the expression's child instead, though this requires a hack removing the pointer from the F node and sticking it onto the var. Should I save this for the type checker instead? How come we can have a pointer symbol in F and also in var?

The other change I made was smaller, and that is to check if parameters and declarations are void within the parser, as otherwise the parser will be slightly messed up by the first parameter being void, so the errors will be parser for the first or typechecker for any other. That's no good, so I make them all parser errors.

ENOUGH YAPPING, HERE'S THE GRAMMAR ========

PROGRAM -> DECLARATION_LIST
DECLARATION_LIST -> DECLARATION_LIST DECLARATION | DECLARATION #uses next
DECLARATION -> VAR_DEC | FUN_DEC
VAR_DEC -> TYPE_SPECIFIER < id> ; | TYPE_SPECIFIER *<id> ; |  TYPE_SPECIFIER <id>[ <num>  ] ; make sure they aren't void
TYPE_SPECIFIER -> int | void | string
FUN_DEC -> TYPE_SPECIFIER <id> ( PARAMS ) COMPOUND_STMT
PARAMS -> void | PARAM_LIST
PARAM_LIST -> PARAM_LIST , PARAM |  PARAM   #uses next
PARAM ->  TYPE_SPECIFIER  <id> |  TYPE_SPECIFIER * < id >  |TYPE_SPECIFIER <id>[   ] # make sure they aren't void
COMPOUND_STATEMENT -> { LOCAL_DECS STATEMENT_LIST }
LOCAL_DECS -> LOCAL_DECS VAR_DEC | <empty> #uses next
STATEMENT_LIST -> STATEMENT_LIST STATEMENT | <empty> #uses next
STATEMENT -> EXPRESSION_STMT | COMPOUND_STATEMENT | IF_STMT | WHILE_STMT | RETURN_STMT | WRITE_STMT
EXPRESSION_STMT -> EXPRESSION;
IF_STMT -> if ( EXPRESSION ) STATEMENT | if ( EXPRESSION ) STATEMENT else STATEMENT
WHILE_STMT -> while ( EXPRESSION ) statement
RETURN_STMT -> return ; |  return EXPRESSION ;
WRITE_STMT -> write ( EXRESSION ) ; |  writeln (   ) ;
EXPRESSION -> COMP_EXP = EXPRESSION | COMP_EXP          #check isLNode
VAR -> * <id> | <id> [expression] | <id>
COMP_EXP -> E RELOP E | E
RELOP -> <= | < | == | != | > | >=
E -> E ADDOP T | T
ADDOP -> + | -
T -> T MULOP F | F
MULOP -> * | / | %
F -> -F | &FACTOR | *FACTOR | FACTOR
FACTOR -> ( EXPRESSION ) | read() | FUN_CALL | LIT | VAR
FUN_CALL -> <id> ( ARGS )
ARGS -> ARG_LIST | <empty>
ARG_LIST -> ARG_LIST , EXPRESSION | EXPRESSION #uses next
LIT -> <string> | <int>
