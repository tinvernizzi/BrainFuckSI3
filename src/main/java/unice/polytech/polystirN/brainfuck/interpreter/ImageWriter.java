package unice.polytech.polystirN.brainfuck.interpreter;

import unice.polytech.polystirN.brainfuck.language.Macro;
import unice.polytech.polystirN.brainfuck.language.MacroWithParam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Class used to read translate text into images
 *
 * @author Joël CANCELA VAZ and Pierre RAINERO
 * @author Tanguy INVERNIZZI and Aghiles DZIRI
 */
public class ImageWriter {
	private final int pixelSize = 3;//pixels squares' width and height
	private BufferedImage img;//Buffer used to write the image
	private TextReader buffer;//Buffer used to read the program
	private String filename;//Filename of the program
	private InstructionFactory factory;

	/**
	 * ImageWriter constructor
	 *
	 * @param filename is the filename of the program to translate
	 * @throws Exception if the filename is incorrect
	 */
	public ImageWriter(String filename) throws Exception {
		this.filename = filename;
		factory = new InstructionFactory();
		buffer = new TextReader(filename, factory);
	}

	/**
	 * Translate the program read by the buffer and writes the corresponding image
	 *
	 * @throws Exception if the program is bad formed or if the image writing went wrong
	 */
	public void translate() throws Exception {
		int instructionsNumber = 0;
		String ins = " ";
		while (buffer.hasNext()) {
			ins = buffer.next();
			if (!ins.equals("\n") && !ins.equals(" ") && !ins.equals("\r")) {
				if (factory.getInstruction(ins.trim()) != null && factory.getInstruction(ins.trim()).getClass().equals(Macro.class)) {
					instructionsNumber += ((Macro) factory.getInstruction(ins)).getNumberOfinstruction();
				} else if (factory.getInstruction(ins.trim()) != null && factory.getInstruction(ins.trim()).getClass().equals(MacroWithParam.class)) {
					instructionsNumber += ((MacroWithParam) factory.getInstruction(ins)).getNumberOfinstruction();
				} else
					instructionsNumber++;
			}

		}
		factory = new InstructionFactory();
		buffer = new TextReader(filename, factory);
		int pictureWidthSquares = 1;
		while ((pictureWidthSquares * pictureWidthSquares) < (instructionsNumber)) {
			pictureWidthSquares++;
		}
		int pictureWidth = pictureWidthSquares * pixelSize;
		img = new BufferedImage(pictureWidth, pictureWidth, TYPE_INT_RGB);
		int x = 0;
		int y = 0;
		while (buffer.hasNext()) {
			String s = buffer.next();
			if (!s.equals("\n") && !s.equals(" ") && !s.equals("\r") && !s.equals("\t")) {
				int col;
				if (factory.getInstruction(s.trim()) != null && factory.getInstruction(s.trim()).getClass().equals(Macro.class)) {
					Macro macros = (Macro) factory.getInstruction(s.trim());
					for (int i = 0; i < macros.getInstructions().size(); i++) {
						col = factory.getColor(macros.getInstructions().get(i).toString());
						fillPixelSquare(x, y, col);
						if (x + pixelSize == pictureWidth) {
							x = 0;
							y += pixelSize;
						} else {
							x += pixelSize;
						}
					}
				} else if (factory.getInstruction(s.trim()) != null && factory.getInstruction(s.trim()).getClass().equals(MacroWithParam.class)) {
					MacroWithParam macros = (MacroWithParam) factory.getInstruction(s.trim());
					for (int j = 0; j < macros.getParam(); j++)
						for (int i = 0; i < macros.getInstructions().size(); i++) {
							col = factory.getColor(macros.getInstructions().get(i).toString());
							fillPixelSquare(x, y, col);
							if (x + pixelSize == pictureWidth) {
								x = 0;
								y += pixelSize;
							} else {
								x += pixelSize;
							}
						}
				} else {
					col = factory.getColor(s);
					fillPixelSquare(x, y, col);
					if (x + pixelSize == pictureWidth) {
						x = 0;
						y += pixelSize;
					} else {
						x += pixelSize;
					}
				}
			}
		}

		DataOutputStream dout = new DataOutputStream(System.out);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "bmp", baos);
		byte[] bytes = baos.toByteArray();
		dout.write(bytes);
	}

	/**
	 * Fills pixels squares with the same color
	 *
	 * @param x     is the x-coordinate where the fill starts
	 * @param y     is the y-coordinate where the fill starts
	 * @param color is the color to fill
	 */
	private void fillPixelSquare(int x, int y, int color) {
		for (int yAxis = y; yAxis < y + (pixelSize); yAxis++) {
			for (int xAxis = x; xAxis < x + (pixelSize); xAxis++) {
				img.setRGB(xAxis, yAxis, color);
			}
		}
	}

	public void translateBmp() throws Exception {
		img = ImageIO.read(new File(filename));
		DataOutputStream dout = new DataOutputStream(System.out);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "bmp", baos);
		byte[] bytes = baos.toByteArray();
		dout.write(bytes);
	}
}
