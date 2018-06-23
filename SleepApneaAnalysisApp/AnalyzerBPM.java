/* 
	Coded by:
	Shreshth Tuli
*/
	
import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class AnalyzerBPM{

	int min;
	int max;
	int average;
	int sum;
	String result;
	Integer[] data;

	public AnalyzerBPM(){

		this.min = 200;
		this.max = 0;
		this.average = 0;
		this.sum = 0;

	}

	public void analyze(){

		if(this.data.length != 1){

			for(int j = 0; j < this.data.length; j++){

				try{

					this.sum = this.sum + this.data[j];

					if(this.min > this.data[j])
					{
						
						this.min = this.data[j];
					
					}

					if(this.max < this.data[j])
					{
						
						this.max = this.data[j];
					
					}
				
				}

				catch(Exception e){}

			}

			this.average = this.sum / this.data.length;

			if(this.average > 100)
			{

				this.result = "High Probability of Tachycardia";

			}
			else if(this.average < 60)
			{

				this.result = "High Probability of Bradycardia";

			}
			else
			{

				this.result = "Normal ECG";

			}

		}
	}

}