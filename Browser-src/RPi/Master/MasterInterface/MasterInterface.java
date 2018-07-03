/* 
	Coded by:
	Shreshth Tuli
*/
	
import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;
import java.util.Arrays;

public class MasterInterface{

	public static void main(String[] args) throws Exception{

		int i;
		int j;
		FileReader fileReader = new FileReader("data.txt");
		FileWriter fileWriter;
		BufferedReader bufferedReader;
		BufferedWriter bufferedWriter;
		String line = "empty";
		String line2;
		String line3;
		String data;
		String[] array;
		String publickey;
		String signature;
		Blockchain chain = new Blockchain();

		// Initialize with Genesis Block
		System.out.println("Initialized Master");
		System.out.println("Genesis Hash : " + chain.GetLatestBlock().hash);

		while(true){

			// Update Last hash
			fileWriter = new FileWriter("lastHash.txt");
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(chain.GetLatestBlock().hash);
			bufferedWriter.newLine();
			bufferedWriter.close();

			// Wait for new data
			bufferedReader = new BufferedReader(fileReader);

			while(true){
				fileReader = new FileReader("data.txt");
				bufferedReader = new BufferedReader(fileReader);
				line = bufferedReader.readLine();
				System.out.println(line);
				try{
					if(line.equals("New Data")){
						break;
					}
				}
				catch(Exception e){}
				Thread.sleep(500);
				bufferedReader.close();
			}

			// Concatenate all data with ':' as delimiter
			line2 = bufferedReader.readLine();
			System.out.println("Data1 : " + line2);
			line3 = bufferedReader.readLine();
			System.out.println("Data2 : " + line3);
			bufferedReader.close();
			data = line2 + ":" + line3;

			// Add block to blockchain
			chain.AddBlock(data);
			chain.ValidateChain();

			// Get Public Key and Signature for data
			array = StringUtil.Sign(data).split(" ");
			publickey = array[0];
			signature = array[1];
			System.out.println("Public Key : " + publickey);
			System.out.println("Signature : " + signature);
			System.out.println("Signature Valid : " + StringUtil.Verify(data, publickey, signature));

			fileWriter = new FileWriter("publicKey.txt");
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(publickey);
			bufferedWriter.newLine();

			bufferedWriter.close();

			fileWriter = new FileWriter("data.txt");
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write("Block Added");
			bufferedWriter.newLine();
			bufferedWriter.write(line2); // data1
			bufferedWriter.newLine();
			bufferedWriter.write(line3); // data2
			bufferedWriter.newLine();
			bufferedWriter.write(chain.GetLatestBlock().hash); // Latest Hash
			bufferedWriter.newLine();
			bufferedWriter.write(chain.GetLatestBlock().prevHash); // Previous Hash
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(chain.GetLatestBlock().salt)); // Proof-of-Work
			bufferedWriter.newLine();
			bufferedWriter.write(publickey);
			bufferedWriter.newLine();
			bufferedWriter.write(signature);
			bufferedWriter.newLine();

			bufferedWriter.close();

		}

		
	}
}