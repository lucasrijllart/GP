/* Generated By:JJTree: Do not edit this line. ASTIf.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package interpreter;

public
class ASTIf extends SimpleNode {
  public ASTIf(int id) {
    super(id);
  }

  public ASTIf(ProgramParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ProgramParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4ce99cdc4f698ceaa4219ad7e526b3a2 (do not edit this line) */
