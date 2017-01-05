package genetic;

import datatype.Data;
import programtodata.Program;
import inputparser.ParseException;
import interpreter.InterpreterException;

class Individual {

    private Program programAsProgram;
    private Data programAsData;
    private String programAsString;
    private Boolean newProgram;
    public int num;

    Individual(String programAsString) {
        setProgram(programAsString);
    }

    void setProgram(String newProgramString) {
        this.newProgram = true;
        try {
            this.programAsProgram = programtodata.ProgramToData.run(newProgramString);
            this.programAsData = inputparser.InputParser.run(getPrintOfProgram());
            this.programAsString = datatoprogram.DataToProgram.run(programAsData.dataToString());
        } catch (programtodata.ParseException | ParseException | InterpreterException | datatoprogram.ParseException e) {
            e.printStackTrace();
        }
    }

    void setProgramWithData(String newProgramData) {
        this.newProgram = true;
        try {
            this.programAsData = inputparser.InputParser.run(newProgramData);
            this.programAsString = datatoprogram.DataToProgram.run(programAsData.dataToString());
        } catch (ParseException | InterpreterException | datatoprogram.ParseException e) {
            e.printStackTrace();
        }
    }

    String getProgramAsString() {
        return programAsString;
    }

    private String getPrintOfProgram() {
        String print = programAsProgram.print;
        print = print.replace(">", "");
        print = print.replace("<", "");
        //print = print.substring(4, print.length()-5);
        return print;
    }

    Data getProgramAsData() {
        return programAsData;
    }

    void setNewProgram(Boolean value) {
        this.newProgram = value;
    }

    Boolean isNew() {
        return this.newProgram;
    }
}
