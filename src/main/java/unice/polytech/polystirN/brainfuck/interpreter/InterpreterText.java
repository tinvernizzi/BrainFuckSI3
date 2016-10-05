package unice.polytech.polystirN.brainfuck.interpreter;

import java.io.BufferedReader;
import java.io.FileReader;

import unice.polytech.polystirN.brainfuck.exceptions.IncorrectFileTypeException;
import unice.polytech.polystirN.brainfuck.exceptions.SyntaxErrorException;

public class InterpreterText extends Interpreter {
	/**
    * 	buffer is the BufferedReader used to read files
	**/
    private BufferedReader buffer;

	public InterpreterText(String filename) throws Exception {
		super();
        if (!filename.matches("(.*).bf")) {
            throw new IncorrectFileTypeException(filename + " must have .bf extension");
        }
        buffer = new BufferedReader(new FileReader(filename));
	}
	
	/**
	 * 
	 */
	@Override
	public boolean executeFile() throws Exception {
    String keyword;
    while ((keyword = buffer.readLine()) != null) {
        if (getOperatorsKeywords().get(keyword.trim()) == null) {
            throw new SyntaxErrorException("Invalid keyword operator");
        }
        getOperatorsKeywords().get(keyword.trim()).doOperation(this);
    }
    return false;
	}

	@Override
	public boolean rewriteFile() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getInstructions() {
		return buffer;
	}
	
}
