package unice.polytech.polystirN.brainfuck.interpreter;

import java.io.FileNotFoundException;

import unice.polytech.polystirN.brainfuck.language.*;

public class InstructionFactory {
    private Operator INCR, DECR, LEFT, RIGHT, IN, OUT, JUMP, BACK;

    /**
     * Constructor for InstructionFactory
     *
     * @throws FileNotFoundException if the inputfile not exist
     */
    public InstructionFactory() throws FileNotFoundException {
        INCR = new Increment();
        DECR = new Decrement();
        LEFT = new Left();
        RIGHT = new Right();
        IN = new In();
        OUT = new Out(null);
        JUMP = new Jump();
        BACK = new Back();
    }

    /**
     * Constructor for InstructionFactory
     *
     * @param inputFile  is the name of the file to replace the input (if null, keyboard by default)
     * @param outputFile is the name of the file to replace the output (if null, console by default)
     * @throws FileNotFoundException if the inputfile not exist
     */
    public InstructionFactory(String inputFile, String outputFile) throws FileNotFoundException {
        this();
        IN = new In(inputFile);
        OUT = new Out(outputFile);

    }

    /**
     * methode getInstruction return operation for the specified instruction
     *
     * @param instruction a string to be translated into an operator
     * @return Operator
     */

    public Operator getInstruction(String instruction) {

        switch (instruction) {
            case "INCR":
            case "+":
            case "#FFFFFF":
                return INCR;
            case "DECR":
            case "-":
            case "#4B0082":
                return DECR;
            case "LEFT":
            case "<":
            case "#9400D3":
                return LEFT;
            case "RIGHT":
            case ">":
            case "#0000FF":
                return RIGHT;
            case "IN":
            case ",":
            case "#FFFF00":
                return IN;
            case "OUT":
            case ".":
            case "#00FF00":
                return OUT;
            case "JUMP":
            case "[":
            case "#FF7F00":
                return JUMP;
            case "BACK":
            case "]":
            case "#FF0000":
                return BACK;
            default:
                return null;
        }

    }


    public int getColor(String instruction) {
        switch (instruction) {
            case "INCR":
            case "+":
                return 0xFFFFFF;
            case "DECR":
            case "-":
                return 0x4B0082;
            case "LEFT":
            case "<":
                return 0x9400D3;
            case "RIGHT":
            case ">":
                return 0x0000FF;
            case "IN":
            case ",":
                return 0xFFFF00;
            case "OUT":
            case ".":
                return 0x00FF00;
            case "JUMP":
            case "[":
                return 0xFF7F00;
            case "BACK":
            case "]":
                return 0xFF0000;
            default:
                return -1;
        }
    }
}
