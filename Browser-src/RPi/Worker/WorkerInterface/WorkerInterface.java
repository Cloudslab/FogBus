/* 
	Coded by:
	Shreshth Tuli
*/
	
import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;
import java.util.Arrays;

public class WorkerInterface{

	public static void main(String[] args) throws Exception{

		int i;
		int j;
		FileReader fileReader = new FileReader("block.txt");
		FileWriter fileWriter;
		BufferedReader bufferedReader;
		BufferedWriter bufferedWriter;
		String line = "empty";
		String line2;
		String line3;
		String data;
		String hash;
		String prevHash;
		int salt;
		String publickey;
		String signature;
		String savedpublickey = "empty";
		String savedsignature = "empty";
		String saveddata = "empty";
		Boolean unknownsrc;
		String msg;
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
				fileReader = new FileReader("block.txt");
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
			data = line2 + ":" + line3;

			// Parse other block details
			hash = bufferedReader.readLine();
			System.out.println("Hash : " + hash);
			prevHash = bufferedReader.readLine();
			System.out.println("Previous Hash : " + prevHash);
			salt = Integer.parseInt(bufferedReader.readLine());
			System.out.println("Salt : " + salt);
			publickey = bufferedReader.readLine();
			System.out.println("Public Key : " + publickey);
			signature = bufferedReader.readLine();
			System.out.println("Signature : " + signature);

			bufferedReader.close();

			// DDoS check
			unknownsrc = saveddata.equals(data) && savedsignature.equals(signature) && savedpublickey.equals(publickey);

			fileWriter = new FileWriter("error.txt");
			bufferedWriter = new BufferedWriter(fileWriter);

			// Check Source
			if(!StringUtil.Verify(data, publickey, signature)){
				msg = "Data Breach! Signature Verification failure. Discarding block";
				System.out.println(msg);
				bufferedWriter.write(msg);
				bufferedWriter.newLine();
			}
			else if(unknownsrc){
				msg = "Unknown Source! Block repetition. Discarding block";
				System.out.println(msg);
				bufferedWriter.write(msg);
				bufferedWriter.newLine();
			}
			else{
				// Add block to blockchain
				chain.AddBlock(data);

				// Update saved values
				savedpublickey = publickey;
				savedsignature = signature;
				saveddata = data;

				// Validate blockchain
				if(!chain.GetLatestBlock().hash.equals(hash.replaceAll("\\s+",""))){
					msg = "Data Tamper! Failed to verify Latest hash";
					System.out.println(msg);
					System.out.println("Chain Hash : " + chain.GetLatestBlock().hash);
					bufferedWriter.write(msg);
					bufferedWriter.newLine();
				}
				if(salt!=chain.GetLatestBlock().salt){
					msg = "Data Tamper! Failed to verify Proof-of-Work";
					System.out.println(msg);
					System.out.println("Chain Salt : " + chain.GetLatestBlock().salt);
					bufferedWriter.write(msg);
					bufferedWriter.newLine();
				}
				if(!prevHash.equals(chain.GetLatestBlock().prevHash)){
					msg = "Data Tamper! Failed to verify Previous Hash";
					System.out.println(msg);
					bufferedWriter.write(msg);
					bufferedWriter.newLine();
				}
				try{
					chain.ValidateChain();
				}
				catch(Exception e){
					System.out.println(e.getMessage());
					bufferedWriter.write(e.getMessage());
					bufferedWriter.newLine();
				}

			}

			bufferedWriter.close();

			fileWriter = new FileWriter("block.txt");
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write("Block Added");
			bufferedWriter.newLine();
			bufferedWriter.close();

		}


		
	}
}