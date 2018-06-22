/* 
	Coded by:
	Shreshth Tuli
*/
	
import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class analyzer{

	public static void main(String[] args) throws Exception {

		int i;
		FileReader fileReader = new FileReader("data.txt");
		FileWriter resultfile = new FileWriter("result.txt");
		FileWriter datafile;
		BufferedWriter resultwriter;
		BufferedWriter datawriter;
		BufferedReader bufferedReader;
		String line = "empty";
		String line2 = "empty";
		int count;
		int min;
		boolean dip;
		BufferedWriter writer;
		String[] datastring;
		Integer[] data;
		String severity;

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
		System.out.println(line);
		System.out.println(line2);
		bufferedReader.close();

		// parse data
		datastring = line2.split(",");
		data = new Integer[datastring.length];
		i=0;
		try{
			for(String str:datastring){
    			data[i]=Integer.parseInt(str);
    			i++;
			}
		}
		catch(Exception e){}

		// Analyze data
		count = 0;
		min = 100;
		dip = false;
		System.out.println("Number of data values :"+data.length);
		if(data.length != 1){
			for(int j = 0; j < data.length; j++){
				if(data[j] <= 88 && !dip){
					count++;
					dip = true;
				}
				else if(data[j] > 88 && dip){
					dip = false;
				}
				if(min > data[j]){
					min = data[j];
				}
			}

			if(count < 5){
				severity = "None";
			}
			else if(count < 15){
				severity = "Mild";
			}
			else if(count < 30){
				severity = "Moderate";
			}
			else{
				severity = "Highly Severe";
			}



			// Write results to file
			resultfile = new FileWriter("result.txt");
			resultwriter = new BufferedWriter(resultfile);
			datafile = new FileWriter("data.txt");
			datawriter = new BufferedWriter(datafile);
		

			resultwriter.write("For 1 hour of Sleep Apnea Data :");
			resultwriter.newLine();
			resultwriter.write("AHI (Apnea-hypopnea index) = "+count);
			resultwriter.newLine();
	    	resultwriter.write("Minimum Oxygen Level = "+min);
			resultwriter.newLine();
			resultwriter.write("Disease Severity : "+severity);

	    	datawriter.write("Analysis Done = true");
			datawriter.newLine();    		
			datawriter.write(line2.substring(1,line2.length())+"\n");
			resultwriter.close();
			datawriter.close();

			System.out.println("Data Analysis Done!");
		}
		else{
			System.out.println("Empty Data");
			Thread.sleep(500);
		}



	}

	
		
}
