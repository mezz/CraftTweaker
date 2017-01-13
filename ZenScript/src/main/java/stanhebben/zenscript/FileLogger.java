/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript;


import java.io.*;
import java.util.regex.Pattern;


/**
 * @author Stan Hebben
 */
public class FileLogger {
	
	private final Writer writer;
	private final PrintWriter printWriter;
	
	public static FileLogger INSTANCE = new FileLogger(new File("generated.log"));
	
	public FileLogger(File output) {
		try {
			writer = new OutputStreamWriter(new FileOutputStream(output), "utf-8");
			printWriter = new PrintWriter(writer);
		} catch(UnsupportedEncodingException ex) {
			throw new RuntimeException("What the heck?");
		} catch(FileNotFoundException ex) {
			throw new RuntimeException("Could not open log file " + output);
		}
	}
	
	public void logCommand(String message) {
		try {
			writer.write(stripMessage(message) + "\n");
			writer.flush();
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void logInfo(String message) {
		try {
			writer.write(stripMessage(message) + "\n");
			writer.flush();
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void logWarning(String message) {
		try {
			writer.write( stripMessage(message) + "\n");
			writer.flush();
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void logError(String message) {
		logError(message, null);
	}
	
	public void logError(String message, Throwable exception) {
		try {
			writer.write(stripMessage(message) + "\n");
			if(exception != null) {
				exception.printStackTrace(printWriter);
			}
			writer.flush();
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private static final Pattern FORMATTING_CODE_PATTERN = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");
	
	/**
	 * Returns a copy of the given string, with formatting codes stripped away.
	 */
	public String stripMessage(String message) {
		return message == null ? null : FORMATTING_CODE_PATTERN.matcher(message).replaceAll("");
	}
}
