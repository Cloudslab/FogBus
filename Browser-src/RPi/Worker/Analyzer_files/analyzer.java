/* 
	Coded by:
	Shreshth Tuli
*/
	
import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;
import java.util.Arrays;

public class Analyzer{

	public static void main(String[] args) throws Exception {

		int i;
		int j;
		FileReader fileReader = new FileReader("data.txt");
		FileWriter resultfile = new FileWriter("result.txt");
		FileWriter datafile;
		BufferedWriter resultwriter;
		BufferedWriter datawriter;
		BufferedReader bufferedReader;
		String line = "empty";
		String line2 = "empty";
		String line3 = "empty";
		BufferedWriter writer;
		String[] datastring1;
		String[] datastring2;
		AnalyzerO2 a1 = new AnalyzerO2();
		AnalyzerBPM a2 = new AnalyzerBPM();

		// Wait for analysis false

		bufferedReader = new BufferedReader(fileReader);

		while(true){

			fileReader = new FileReader("data.txt");
			bufferedReader = new BufferedReader(fileReader);
			line = bufferedReader.readLine();
			System.out.println(line);

			try{

				if(line.equals("Analysis Done = false")){
					break;
				}

			}
			catch(Exception e){}

			Thread.sleep(500);
		}

		line2 = bufferedReader.readLine();
		line3 = bufferedReader.readLine();

		System.out.println(line);
		System.out.println(line2);
		System.out.println(line3);

		bufferedReader.close();

		// parse data

		datastring1 = line2.split(",");
		datastring2 = line3.split(",");

		a1.data = new Integer[datastring1.length];
		a2.data = new Integer[datastring2.length];

		i=0;
		j=0;

		try{

			for(String str:datastring1){
    			a1.data[i]=Integer.parseInt(str);
    			i++;
			}

			for(String str:datastring2){
    			a2.data[j]=Integer.parseInt(str);
    			j++;
			}

		}

		catch(Exception e){}

		// Analyze data

		System.out.println("Number of SpO2 data values : " + a1.data.length);
		System.out.println("Number of Pulse rate values : " + a2.data.length);

		a1.analyze();
		a2.analyze();

		if(a1.data.length != 1)
		{

			// Write results to file
			resultfile = new FileWriter("result.txt");
			resultwriter = new BufferedWriter(resultfile);
			datafile = new FileWriter("data.txt");
			datawriter = new BufferedWriter(datafile);
		

			resultwriter.write("For 1 hour of Sleep Apnea Data :");
			resultwriter.newLine();

			resultwriter.write("AHI (Apnea-hypopnea index) = " + a1.count);
			resultwriter.newLine();

	    	resultwriter.write("Minimum Oxygen Level = " + a1.min);
			resultwriter.newLine();

			resultwriter.write("Disease Severity : " + a1.severity);
			resultwriter.newLine();

			resultwriter.write("Minimum Heart Rate : " + a2.min);
			resultwriter.newLine();

			resultwriter.write("Maximum Heart Rate : " + a2.max);
			resultwriter.newLine();

			resultwriter.write("Average Heart Rate : " + a2.average);
			resultwriter.newLine();

			resultwriter.write("Diagnosis : " + a2.result);

	    	datawriter.write("Analysis Done = true");
			datawriter.newLine();    		

			datawriter.write(line2.substring(1,line2.length()));
			datawriter.newLine();    	

			datawriter.write(line3.substring(1,line2.length()));
			datawriter.newLine();    

			resultwriter.close();
			datawriter.close();

			System.out.println("Data Analysis Done!");
		
		}

		else
		{
			
			System.out.println("Empty Data");
			Thread.sleep(500);
		
			}



	}

	
		
}
