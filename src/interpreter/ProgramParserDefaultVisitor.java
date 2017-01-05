/* Generated By:JavaCC: Do not edit this line. ProgramParserDefaultVisitor.java Version 6.0_1 */
package interpreter;

public class ProgramParserDefaultVisitor implements ProgramParserVisitor{
  public Object defaultVisit(SimpleNode node, Object data){
    node.childrenAccept(this, data);
    return data;
  }
  public Object visit(SimpleNode node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTProgram node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTprogname node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTinput node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASToutput node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTBlock node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTMacro node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTAssign node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTWhile node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTIf node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTSwitch node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTCase node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTExprList node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTDefaultCase node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTEqual node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTnil node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTCons node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTatom node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTHd node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTTl node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTnum node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTbool node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTvar node, Object data){
    return defaultVisit(node, data);
  }
}
/* JavaCC - OriginalChecksum=19b9bd7c8b5b07b41bafa24abdc106ba (do not edit this line) */
