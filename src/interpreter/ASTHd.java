/* Generated By:JJTree: Do not edit this line. ASTHd.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package interpreter;

public
class ASTHd extends SimpleNode {
  public ASTHd(int id) {
    super(id);
  }

  public ASTHd(ProgramParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ProgramParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=34e84d22fac1be7174115cc606ff0197 (do not edit this line) */
