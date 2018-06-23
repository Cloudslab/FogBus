/* 
	Coded by:
	Shreshth Tuli
*/
	
import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class AnalyzerO2{

	int min;
	int count;
	boolean dip;
	String severity;
	Integer[] data;

	public AnalyzerO2(){

		this.count = 0;
		this.min = 100;
		this.dip = false;

	}

	public void analyze(){

		if(this.data.length != 1){

			for(int j = 0; j < this.data.length; j++){
				
				if(this.data[j] <= 88 && !this.dip)
				{
					
					this.count++;
					this.dip = true;
				
				}

				else if(this.data[j] > 88 && this.dip)
				{
					
					this.dip = false;
				
				}

				if(this.min > this.data[j])
				{
					
					this.min = this.data[j];
				
				}

			}

			if(this.count < 5)
			{
				
				this.severity = "None";
			
			}

			else if(this.count < 15)
			{
				
				this.severity = "Mild";
			
			}

			else if(this.count < 30)
			{
				
				this.severity = "Moderate";
			
			}

			else
			{
				
				this.severity = "Highly Severe";
			
			}

		}

	}

}