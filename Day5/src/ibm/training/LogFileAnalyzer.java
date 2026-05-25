package ibm.training;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LogFileAnalyzer {

	public static void main(String[] args) {
		HashMap<String, Integer> levels = new HashMap<>();
		
		levels.put("WARN", 0);
		levels.put("INFO", 0);
		levels.put("ERROR", 0);
		
		LocalDateTime earliest = null;
		LocalDateTime latest = null;
		
		List<String> messages = new ArrayList<>();
		List<String> malformedLines = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader("src/ibm/training/server.log"))){
			String line;
			
			while((line = br.readLine()) != null) {
				if(line.charAt(0) == '[' && line.contains("]")) {
					
					int endBracket = line.indexOf("]");
					String time = line.substring(1, endBracket);
					int messageStart;
					
					if(line.indexOf("ERROR") >= 0 && line.charAt(line.indexOf("ERROR") + 5) == ':') {
						levels.put("ERROR", levels.get("ERROR") + 1);
						messageStart = line.indexOf("ERROR") + 7;
					}else if(line.indexOf("WARN") >= 0 && line.charAt(line.indexOf("WARN") + 4) == ':') {
						levels.put("WARN", levels.get("WARN") + 1);
						messageStart = line.indexOf("WARN") + 6;
					}else if(line.indexOf("INFO") >= 0 && line.charAt(line.indexOf("INFO") + 4) == ':') {
						levels.put("INFO", levels.get("INFO") + 1);
						messageStart = line.indexOf("INFO") + 6;
					}else {
						throw new MalformedLogEntryException(line);
					}
					
					messages.add(line.substring(messageStart));
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					LocalDateTime timestamp = LocalDateTime.parse(time, formatter);
					
					if (earliest == null || timestamp.isBefore(earliest)) {
					    earliest = timestamp;
					}

					if (latest == null || timestamp.isAfter(latest)) {
					    latest = timestamp;
					}
				}else {
					throw new MalformedLogEntryException(line);
				}
			}
		}catch(FileNotFoundException e) {
			System.out.println("File not found.");
		}catch(IOException e) {
			System.out.println("Error: " + e.getMessage());
		}catch(MalformedLogEntryException e) {
			malformedLines.add(e.getMessage());
		}
		
		try ( BufferedWriter bw = new BufferedWriter(new FileWriter("src/ibm/training/summary.txt"))){
			bw.write("Log Summary Report \n----------------------\n");
			
			int totalEntries = levels.get("INFO") + levels.get("WARN") + levels.get("ERROR");
			bw.write("Total Entries: " + totalEntries +"\n");
			bw.write("INFO: " + levels.get("INFO") + "\n");
			bw.write("WARN: " + levels.get("WARN") + "\n");
			bw.write("ERROR: " +  levels.get("ERROR") + "\n\n");
			
			bw.write("Error Messages:\n");
			for(String message: messages) {
				bw.write(" - " + message + "\n");
			}
			
			bw.write("\nEarliest Timestamp: " + earliest + "\n");
			bw.write("Latest Timestamp: " + latest + "\n");
		}catch(IOException e) {
			System.out.println("Error: " + e);
		}
	}
}
