/* Generated By:JJTree: Do not edit this line. ASTvar.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package interpreter;

public
class ASTvar extends SimpleNode {
  public ASTvar(int id) {
    super(id);
  }

  public ASTvar(ProgramParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ProgramParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=fc58c3e45203aca0a350e81fd6feefec (do not edit this line) */
