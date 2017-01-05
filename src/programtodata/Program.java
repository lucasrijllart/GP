package programtodata;

public class Program { //[progname, varnum1, "S", varnum2]
  public String print;

  public Program(Var varnum1,
          Block block,
          Var varnum2) {
    this.print = "[\u005cn>" + varnum1.varnum + ",\u005cn" + block.print + ",\u005cn" + varnum2.varnum + "\u005cn<]";
  }
}
