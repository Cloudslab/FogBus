/* 
	Coded by:
	Shreshth Tuli
*/
	
import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;
import java.util.Arrays;
import java.util.List;
import java.text.SimpleDateFormat;

public class Block{

	public String data;
	public String hash;
	public String prevHash;
	public int index;
	public int salt;
	public String timestamp;

	public Block(int index, String data, String prevHash){
		this.index = index;
		this.data = data;
		this.prevHash = prevHash;
		this.salt = 0;
		this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		this.hash = this.CalculateHash();
	}

	public String CalculateHash(){
		String calculatedHash = StringUtil.applySha256(this.salt + this.prevHash + this.index + this.data);
		return calculatedHash;
	}

	public void Mine(int difficulty){
		while(!this.hash.substring(0, difficulty).equals(StringUtil.repeat(difficulty, "0"))){
			this.salt++;
			this.hash = this.CalculateHash();
		}
	}

}