package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;

import org.junit.jupiter.api.*;

import ibm.training.LogAnalyzer;

class LogAnalyzerTest {
	
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to a ByteArrayOutputStream
        System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    public void restoreSystemOut() {
        // Restore the original System.out after each test
        System.setOut(originalOut);
        
        File summary = new File("src/ibm/training/summary.txt");
        if (summary.exists()) {
            summary.delete();
        }
    }
    
    private void fileCreator(String filename, String content) {
    	try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
	        bw.write(content);
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
    }

	@Test
	void success_ShouldReturnAnalysisComplete() {
		String[] args = {"src/ibm/training/server.log"};
		LogAnalyzer.main(args);
		
		String expectedOutput = "Analysis complete. Summary written to summary.txt" + System.lineSeparator();
		assertEquals(expectedOutput, outputStream.toString());
	}
	
	@Test
	void fileNotFoundError() {
	    String[] args = {"server123.log"};

	    LogAnalyzer.main(args);

	    String expectedOutput =
	            "Log file not found." + System.lineSeparator();

	    assertEquals(expectedOutput, outputStream.toString());
	}
	
	@Test
	void shouldPrintMalformedLineMessage() {
		String filename = "server.log";
	    fileCreator(filename, "2024-05-10 09:04:18 INFO: Shutdown completed successfully");

	    LogAnalyzer.main(new String[] {filename});

	    String expectedOutput =
	            "Skipping malformed line: 2024-05-10 09:04:18 INFO: Shutdown completed successfully"
	                    + System.lineSeparator()
	                    + "Analysis complete. Summary written to summary.txt"
	                    + System.lineSeparator();

	    assertEquals(expectedOutput, outputStream.toString());
	}
	
	@Test
	void shouldPrintMalformedLineMessageForLevel() {
		String filename = "server.log";
	    fileCreator(filename, "[2024-05-10 09:04:18] INFOS: Shutdown completed successfully");

	    LogAnalyzer.main(new String[] {filename});

	    String expectedOutput =
	            "Skipping malformed line: [2024-05-10 09:04:18] INFOS: Shutdown completed successfully"
	                    + System.lineSeparator()
	                    + "Analysis complete. Summary written to summary.txt"
	                    + System.lineSeparator();

	    assertEquals(expectedOutput, outputStream.toString());
	}

	@Test
	void shouldPrintMissingMessage() {
		String filename = "server.log";
	    fileCreator(filename, "[2024-05-10 09:04:18] INFO:");

	    LogAnalyzer.main(new String[] {filename});


	    String expectedOutput =
	            "Skipping malformed line: [2024-05-10 09:04:18] INFO:"
	                    + System.lineSeparator() 
	                    + "Analysis complete. Summary written to summary.txt" 
	                    + System.lineSeparator();

	    assertEquals(expectedOutput, outputStream.toString());
	}
	
//	@Test
//	void ShouldReturnInvalidLogLevel() {
//	    String fileName = "server.log";
//
//	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
//	        bw.write("[2024-05-10 09:04:18] INFOS: Shutdown completed successfully");
//	    } catch (Exception e) {
//	        System.out.println("Error: " + e.getMessage());
//	    }
//
//	    String[] args = {fileName};
//
//	    LogAnalyzer.main(args);
//
//	    String expectedOutput =
//	            "Skipping malformed line: [2024-05-10 09:04:18] INFOS: Shutdown completed successfully"
//	                    + System.lineSeparator() 
//	                    + "Analysis complete. Summary written to summary.txt" 
//	                    + System.lineSeparator();
//
//	    assertEquals(expectedOutput, outputStream.toString());
//	}

}
