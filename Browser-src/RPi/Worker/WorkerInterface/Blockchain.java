/* 
	Coded by:
	Shreshth Tuli
*/
	
import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;
import java.util.Arrays;
import java.lang.Object;

public class Blockchain{

	public List<Block> chain;
	private int i = 1;
	private int difficulty = 2;

	public Blockchain(){
		this.chain = new ArrayList<Block>();
		this.chain.add(CreateGenesisBlock());
	}

	private Block CreateGenesisBlock(){
		return new Block(0, "", "");
	}

	public Block GetLatestBlock(){
		return this.chain.get(this.chain.size() - 1);
	}

	public void AddBlock(String data){
		System.out.println("Adding Block at index : " + i);
		Block newBlock = new Block(i, data, this.GetLatestBlock().hash);
		newBlock.Mine(difficulty);
		System.out.println("Hash : " + newBlock.hash);
		System.out.println("Proof-of-work = " + newBlock.salt);
		this.chain.add(newBlock);
		i++;
	}

	public void ValidateChain() throws Exception{
		for(int i = 1; i < this.chain.size(); i++){
			Block currentBlock = this.chain.get(i);
			Block prevBlock = this.chain.get(i-1);

			System.out.println("Data at index " + i + " : " + currentBlock.data);

            // Check if the current block hash is consistent with the hash calculated
            if (!currentBlock.hash.equals(currentBlock.CalculateHash()))
            {
                throw new Exception("Chain is not valid! Current hash is incorrect!");
            }
            // Check if the Previous hash match the hash of previous block
            if (!currentBlock.prevHash.equals(prevBlock.hash))
            {
                throw new Exception("Chain is not valid! PreviousHash isn't pointing to the previous block's hash!");
            }
            // Check if hash string has initial zeroes
            String str = "0";
            str = repeat(difficulty, str);
            if (!currentBlock.hash.substring(0, difficulty).equals(str))
            {
                throw new Exception("Chain is not valid! Hash does not show proof-of-work!");
            }
        }
	}

	public static String repeat(int count, String with) {
    	return new String(new char[count]).replace("\0", with);
	}
	



}


