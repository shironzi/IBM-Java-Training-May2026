package ibm.training;
import java.io.*;

public class Main {

	public static void main(String[] args) {
		try(
				BufferedReader br = new BufferedReader(new FileReader("src/ibm/training/student.csv"));
				BufferedWriter bw = new BufferedWriter(new FileWriter("src/ibm/training/student.json"))) {
			
			String line;
			boolean isFirst = true;
			
			br.readLine();
			bw.write("[\n");
			
			while((line = br.readLine()) != null) {
				String[] data = line.split(",");
				
				if(!isFirst) {
					bw.write(",\n");
				}
				
				isFirst = false;
				
				bw.write("{\n");
				bw.write("\"id\": \"" + data[0] + "\",\n");
				bw.write("\"name\": \"" + data[1] + "\",\n");
				bw.write("\"course\": \"" + data[2] + "\"\n");
				bw.write("}");
			}
			
			bw.write("\n]");
			
			br.close();
			bw.close();
		}catch (IOException e) {
			System.out.print("Error: " + e);
		}
	}

}
