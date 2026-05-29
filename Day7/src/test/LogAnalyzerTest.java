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
    }
    
    private void fileCreator(String filename, String content) {
    	try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
	        bw.write(content);
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
    }

    @Test
    void success_ShouldReturnAnalysisComplete() throws IOException {

        // Create sample log file
        String filename = "server.log";

        String logContent =
                "[2024-05-10 09:00:00] INFO: Server started\n" +
                "[2024-05-10 09:05:00] WARN: High memory usage\n" +
                "[2024-05-10 09:10:00] ERROR: Database connection failed";

        fileCreator(filename, logContent);

        String[] args = {filename};

        // Execute
        LogAnalyzer.main(args);

        // Verify console output
        String expectedConsole =
                "Analysis complete. Summary written to summary.txt"
                        + System.lineSeparator();

        assertEquals(expectedConsole, outputStream.toString());

        // Verify summary file content
        File summaryFile = new File("src/ibm/training/summary.txt");

        Assertions.assertTrue(summaryFile.exists());

        BufferedReader br = new BufferedReader(new FileReader(summaryFile));

        StringBuilder actualContent = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            actualContent.append(line)
                         .append(System.lineSeparator());
        }

        br.close();

        String expectedSummary =
                "Log Summary Report" + System.lineSeparator() +
                "------------------" + System.lineSeparator() +
                "Total Entries: 3" + System.lineSeparator() +
                "INFO: 1" + System.lineSeparator() +
                "WARN: 1" + System.lineSeparator() +
                "ERROR: 1" + System.lineSeparator() +
                System.lineSeparator() +
                "Error Messages:" + System.lineSeparator() +
                "- Database connection failed" + System.lineSeparator() +
                System.lineSeparator() +
                "Earliest Timestamp: 2024-05-10T09:00" + System.lineSeparator() +
                "Latest Timestamp: 2024-05-10T09:10" + System.lineSeparator();

        assertEquals(expectedSummary, actualContent.toString());
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
}
