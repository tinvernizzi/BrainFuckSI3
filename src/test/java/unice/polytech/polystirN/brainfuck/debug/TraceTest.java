package unice.polytech.polystirN.brainfuck.debug;

import org.junit.Test;
import unice.polytech.polystirN.brainfuck.interpreter.Interpreter;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests class for the Trace
 *
 * @author Joël CANCELA VAZ and Pierre RAINERO
 * @author Tanguy INVERNIZZI and Aghiles DZIRI
 */
public class TraceTest {
	private Interpreter intrptr;
	private Trace trace;
	private BufferedReader br1;

	@Test
	public void writeSameTraceWithAnytypeOfFile() throws Exception {
		BufferedReader br2;

		try {
			trace = new Trace();
			intrptr = new Interpreter(getClass().getResource("/L3/usual/little_traceLONG.bf").getFile(), trace);
			intrptr.interpretFile();

			trace = new Trace();
			intrptr = new Interpreter(getClass().getResource("/L3/usual/little_traceIMAGE.bmp").getFile(), trace);
			intrptr.interpretFile();

			trace = new Trace();
			intrptr = new Interpreter(getClass().getResource("/L3/usual/little_traceSHORT.bf").getFile(), trace);
			intrptr.interpretFile();


			String sCurrentLineF1;
			boolean isSameContent = true;

			br1 = new BufferedReader(new FileReader("./target/test-classes/L3/usual/little_traceLONG.log"));
			br2 = new BufferedReader(new FileReader("./target/test-classes/L3/usual/little_traceSHORT.log"));

			while ((sCurrentLineF1 = br1.readLine()) != null) {
				if (!sCurrentLineF1.equals(br2.readLine()))
					isSameContent = false;
			}

			assertTrue(isSameContent);

			br2 = new BufferedReader(new FileReader("./target/test-classes/L3/usual/little_traceIMAGE.log"));

			while ((sCurrentLineF1 = br1.readLine()) != null) {
				if (!sCurrentLineF1.equals(br2.readLine()))
					isSameContent = false;
			}

			assertTrue(isSameContent);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void writeACorrectTraceNoError() throws Exception {
		String expected = "1 : +pointer : 0pointer after : 0[1]----------------------------2 : +pointer : 0pointer after : 0[2]----------------------------3 : -pointer : 0pointer after : 0[1]----------------------------4 : .pointer : 0pointer after : 0[1]----------------------------5 : [pointer : 0pointer after : 0[1]----------------------------6 : -pointer : 0pointer after : 0[0]----------------------------7 : ]pointer : 0pointer after : 0[0]----------------------------8 : >pointer : 0pointer after : 1[0, 0]----------------------------9 : <pointer : 1pointer after : 0[0, 0]----------------------------";
		String result = "";
		String sCurrentLineF;
		trace = new Trace();

		intrptr = new Interpreter(getClass().getResource("/L3/usual/little_traceSHORT.bf").getFile(), trace);
		intrptr.interpretFile();

		br1 = new BufferedReader(new FileReader("./target/test-classes/L3/usual/little_traceSHORT.log"));
		while ((sCurrentLineF = br1.readLine()) != null) {
			result += sCurrentLineF;
		}

		assertEquals(expected, result);
	}

	@Test
	public void writeACorrectTracWithError() throws Exception {
		trace = new Trace();
		String expected = "1 : +pointer : 0pointer after : 0[1]----------------------------2 : -pointer : 0pointer after : 0[0]----------------------------3 : [pointer : 0";
		String result = "";
		String sCurrentLineF = "";
		boolean firstLine = true;

		try {
			intrptr = new Interpreter(getClass().getResource("/L3/usual/traceError.bf").getFile(), trace);
			intrptr.interpretFile();
		} catch (Exception e) { //Code du main :
			expected += "unice.polytech.polystirN.brainfuck.exceptions.BadLoopException: Loop without end : Missing BACK operator";
			if (trace != null && trace.isOpen()) {
				e.printStackTrace(trace.getPrintWriter());
				trace.closePrint();
				trace.close();
			}
		}

		br1 = new BufferedReader(new FileReader("./target/test-classes/L3/usual/traceError.log"));
		while (sCurrentLineF != null) {
			if (firstLine)
				firstLine = false;
			else {
				if (sCurrentLineF.charAt(1) != '\t' && sCurrentLineF.charAt(1) != 'a' && sCurrentLineF.charAt(2) != 't')
					result += sCurrentLineF;
			}
			sCurrentLineF = br1.readLine();
		}

		assertEquals(expected, result);

	}

	@Test
	public void writeACorrectTracWithoutGoingInTheJump() throws Exception {
		String expected = "1 : +pointer : 0pointer after : 0[1]----------------------------2 : -pointer : 0pointer after : 0[0]----------------------------3 : >pointer : 0pointer after : 1[0, 0]----------------------------4 : [pointer : 1pointer after : 1[0, 0]----------------------------5 : ]pointer : 1pointer after : 1[0, 0]----------------------------6 : +pointer : 1pointer after : 1[0, 1]----------------------------7 : -pointer : 1pointer after : 1[0, 0]----------------------------";
		String result = "";

		trace = new Trace();
		intrptr = new Interpreter(getClass().getResource("/L3/usual/trace_notGoing_Into_Jump.bf").getFile(), trace);
		intrptr.interpretFile();

		String sCurrentLineF;

		br1 = new BufferedReader(new FileReader("./target/test-classes/L3/usual/trace_notGoing_Into_Jump.log"));

		while ((sCurrentLineF = br1.readLine()) != null) {
			result += sCurrentLineF;
		}

		assertEquals(expected, result);
	}
}
