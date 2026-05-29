package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ibm.training.LogAnalyzer;

class LogAnalyzerTest {

	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to a ByteArrayOutputStream
    	outputStream.reset();
        System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    void restoreSystemOut() throws IOException {
        // Restore the original System.out after each test
        System.setOut(originalOut);
        
        // Deleting the summary if not existing
        Files.deleteIfExists(Path.of("src/ibm/training/resources/summary.txt"));
    }
    
    /**
     * should_ReturnEqual_IfSummaryOutputIsEquivalent
     * Verifies that the generated summary file content and console output
     * match the expected values for a valid log file input.
     */
    @Test
    void exec001() throws IOException {	
		String expectedFile = java.nio.file.Files.readString(Path.of("src/test/resources/exec001/summary.txt"));
		
		LogAnalyzer.main(new String[] {"src/test/resources/exec001/server.log"});
		String expectedOutput = "Analysis complete. Summary written to summary.txt" + System.lineSeparator();
		
		String actualFile = Files.readString(Path.of("src/ibm/training/resources/summary.txt"));
		
		assertEquals(expectedFile, actualFile);
		assertEquals(expectedOutput, outputStream.toString());
    }
    
    /**
     * should_DisplayFileNotFoundMessage_IfLogFileDoesNotExist
     * Verifies that the application prints an appropriate error message when the specified log file cannot be found.
     */
    @Test
    void exec002() throws IOException {	
		LogAnalyzer.main(new String[] {"src/test/resources/exec002/server.log"});
		String expectedOutput = "Log file not found." + System.lineSeparator();
		
		assertEquals(expectedOutput, outputStream.toString());
    }
    
    /**
     * should_SkipMalformedLine_IfTimestampFormatIsInvalid
     * Verifies that malformed log entries without the expected timestamp format are skipped during analysis.
     */
    @Test
    void exec003() throws IOException {	
    	String expectedFile = java.nio.file.Files.readString(Path.of("src/test/resources/exec003/summary.txt"));
    	
		LogAnalyzer.main(new String[] {"src/test/resources/exec003/server.log"});
		String expectedOutput = "Skipping malformed line: 2024-05-10 09:00:24 ERROR: Failed to connect to external API" 
				+ System.lineSeparator() 
				+ "Analysis complete. Summary written to summary.txt"
				+ System.lineSeparator();
		
		String actualFile = Files.readString(Path.of("src/ibm/training/resources/summary.txt"));
		
		assertEquals(expectedFile, actualFile);
		assertEquals(expectedOutput, outputStream.toString());
    }
    
    /*
     * should_SkipMalformedLine_IfLogLevelIsInvalid
     * Verifies that log entries containing unsupported log levels are skipped during processing.
     */
    @Test
    void exec004() throws IOException {	
    	String expectedFile = java.nio.file.Files.readString(Path.of("src/test/resources/exec004/summary.txt"));
    	
		LogAnalyzer.main(new String[] {"src/test/resources/exec004/server.log"});
		String expectedOutput = "Skipping malformed line: [2024-05-10 09:00:24] RUN: Failed to connect to external API" 
				+ System.lineSeparator() 
				+ "Analysis complete. Summary written to summary.txt"
				+ System.lineSeparator();
		
		String actualFile = Files.readString(Path.of("src/ibm/training/resources/summary.txt"));
		
		assertEquals(expectedFile, actualFile);
		assertEquals(expectedOutput, outputStream.toString());
    }
    
    /*
     * should_SkipMalformedLine_IfLogMessageIsMissing
     * Verifies that log entries missing the message content
     * after the log level are skipped during analysis.
     */
    @Test
    void exec005() throws IOException {	
    	String expectedFile = java.nio.file.Files.readString(Path.of("src/test/resources/exec005/summary.txt"));
    	
		LogAnalyzer.main(new String[] {"src/test/resources/exec005/server.log"});
		String expectedOutput = "Skipping malformed line: [2024-05-10 09:00:24] ERROR:" 
				+ System.lineSeparator() 
				+ "Analysis complete. Summary written to summary.txt"
				+ System.lineSeparator();
		
		String actualFile = Files.readString(Path.of("src/ibm/training/resources/summary.txt"));
		
		assertEquals(expectedFile, actualFile);
		assertEquals(expectedOutput, outputStream.toString());
    }
    
    /**
     * should_DisplayErrorReadingFileMessage_IfLogFileIsLocked
     * Verifies that the application handles IOException
     * when the input log file is locked during reading.
     */
    @Test
    void exec006() throws IOException {

        Path logPath = Path.of("src/test/resources/exec006/server.log");

        try (
            RandomAccessFile raf = new RandomAccessFile(logPath.toFile(), "rw");
            FileChannel channel = raf.getChannel();
            FileLock lock = channel.lock()
        ) {

            LogAnalyzer.main(new String[] {logPath.toString()});

            String expectedOutput =
                    "Error reading file." + System.lineSeparator();

            assertEquals(expectedOutput, outputStream.toString());
        }
    }
    
    /**
     * should_DisplayErrorWritingSummaryMessage_IfSummaryFileIsLocked
     * Verifies that the application handles IOException
     * when the summary output file is locked during writing.
     */
    @Test
    void exec007() throws IOException {

        Path summaryPath =
                Path.of("src/ibm/training/resources/summary.txt");

        Files.createDirectories(summaryPath.getParent());

        if (!Files.exists(summaryPath)) {
            Files.createFile(summaryPath);
        }

        try (
            RandomAccessFile raf =
                    new RandomAccessFile(summaryPath.toFile(), "rw");

            FileChannel channel = raf.getChannel();

            FileLock lock = channel.lock()
        ) {

            LogAnalyzer.main(
                new String[] {"src/test/resources/exec007/server.log"}
            );

            String expectedOutput =
                    "Error writing summary file."
                    + System.lineSeparator()
                    + "Analysis complete. Summary written to summary.txt"
                    + System.lineSeparator();

            assertEquals(expectedOutput, outputStream.toString());
        }
    }

}
