using System;
using System.IO;
using System.Collections.Generic;
using System.Text;
using System.Security.Cryptography;
using System.Linq;
using System.Globalization;
using System.Threading;
using System.Numerics;
using Aneka;
using Aneka.Threading;
using Aneka.Entity;

namespace AnekaHealthKeeper
{

    public class Block
    {
        public List<int> data = new List<int>();
        public string hash;
        public string prevHash;
        public int index;
        public int salt;
        public string timestamp;

        public Block(int index, List<int> data, string previousHash = "")
        {
            this.index = index;
            this.salt = 0;
            this.prevHash = previousHash;
            this.data = data;
            this.timestamp = DateTime.Now.ToString("yyyyMMddHHmmssfff");
            this.hash = this.CalculateHash();
        }
        public string CalculateHash()
        {
            string calculatedhash = SHA256_hash(this.salt + this.prevHash + this.timestamp + this.index + string.Join(";", data.Select(x => x.ToString()).ToArray()));
            return calculatedhash;
        }
        public void Mine(int difficulty)
        {
            while (this.hash.Substring(0, difficulty) != new String('0', difficulty))
            {
                this.salt++;
                this.hash = this.CalculateHash();
            }
        }
        // Create a hash string from stirng
        static string SHA256_hash(string value)
        {
            var sb = new StringBuilder();
            using (SHA256 hash = SHA256Managed.Create())
            {
                var enc = Encoding.UTF8;
                var result = hash.ComputeHash(enc.GetBytes(value));
                foreach (var b in result)
                    sb.Append(b.ToString("x2"));
            }
            return sb.ToString();
        }
    }
    public class Blockchain
    {
        List<Block> chain;
        public int i = 1;
        private int difficulty = 2;
        public Blockchain()
        {
            this.chain = new List<Block>();
            this.chain.Add(CreateGenesisBlock());
        }
        Block CreateGenesisBlock()
        {
            return new Block(0, new List<int>(), "");
        }
        public Block GetLatestBlock()
        {
            return this.chain.Last();
        }
        public void AddBlock(List<int> data)
        {
            Console.WriteLine("Adding Block at index : " + i);
            Block newBlock = new Block(i, data, this.GetLatestBlock().hash);
            newBlock.Mine(difficulty);
            this.chain.Add(newBlock);
            i += 1;
        }
        public void ValidateChain()
        {
            for (var i = 1; i < this.chain.Count; i++)
            {
                var currentBlock = this.chain[i];
                var previousBlock = this.chain[i - 1];

                Console.WriteLine("Data at index " + currentBlock.index + " : " + string.Join(";", currentBlock.data.Select(x => x.ToString()).ToArray()));

                // Check if the current block hash is consistent with the hash calculated
                if (currentBlock.hash != currentBlock.CalculateHash())
                {
                    throw new Exception("Chain is not valid! Current hash is incorrect!");
                }
                // Check if the Previous hash match the hash of previous block
                if (!currentBlock.prevHash.Equals(previousBlock.hash))
                {
                    throw new Exception("Chain is not valid! PreviousHash isn't pointing to the previous block's hash!");
                }
                // Check if hash string has initial zeroes
                if (currentBlock.hash.Substring(0, difficulty) != new String('0', difficulty))
                {
                    throw new Exception("Chain is not valid! Hash does not show proof-of-work!");
                }
            }
        }
    }
    [Serializable]
    public class HelloWorld
    {
        public string result = "None";
        public string savedhash = "initialhash";
        public bool checkhash = true;
        public bool success;
        public List<int> input = new List<int>();
        public int count = 0;
        public int min = 100;
        public bool dip = false;
        public int len;
        public string check = "Function not executed";
        public HelloWorld(List<int> input, string signature, RSAParameters publicKey, string oldhash, string newhash)
        {
            result = "HelloWorld";
            success = VerifyData(string.Join(";", input.Select(x => x.ToString()).ToArray()), signature, publicKey);
            if(this.savedhash != oldhash && this.savedhash != "initialhash")
            {
                Console.WriteLine("Saved Hash : " + this.savedhash);
                Console.WriteLine("Old Hash : " + oldhash);
                Console.WriteLine("New Hash : " + newhash);
                this.checkhash = false;
                Console.WriteLine("Checkhash : " + this.checkhash);
            }
            else
            {
                this.checkhash = true;
            }
            if (!success)
            {
                throw new Exception("Data Breach! Signature verification failure!");
            }
            this.savedhash = newhash;
            this.input = input;
            len = this.input.Count();
            this.check = "Function Executed!";
            Console.WriteLine("Entered analysis function");
            count = 0;
            foreach (var val in this.input)
            {
                Console.WriteLine("Value in input : " + val);
                if (val <= 88 && !dip)
                {
                    count++;
                }
                else if (val > 88 && dip)
                {
                    dip = false;
                }
                if (min > val)
                {
                    min = val;
                }
            }

        }
        public static bool VerifyData(string originalMessage, string signedMessage, RSAParameters publicKey)
        {
            bool success = false;
            using (var rsa = new RSACryptoServiceProvider())
            {
                ASCIIEncoding byteConverter = new ASCIIEncoding();
                byte[] bytesToVerify = byteConverter.GetBytes(originalMessage);
                byte[] signedBytes = Convert.FromBase64String(signedMessage);
                try
                {
                    rsa.ImportParameters(publicKey);
                    success = rsa.VerifyData(bytesToVerify, CryptoConfig.MapNameToOID("SHA512"), signedBytes);
                }
                catch (CryptographicException e)
                {
                    Console.WriteLine(e.Message);
                }
                finally
                {
                    rsa.PersistKeyInCsp = false;
                }
            }
            return success;
        }
        public void PrintHello()
        {
            // Main PrintHello Function
        }
    }
    class Program
    {
        static string signature;
        static string oldhash;
        static string newhash;
        static string path = @"C:\xampp\htdocs\HealthKeeper\Aneka\data.txt";
        static List<string> list;
        static List<int> intlist;
        static int totalcount;
        static int minima;
        static List<int> allData = new List<int>();
        static string sev;
        static void Main(string[] args)
        {
            RSACryptoServiceProvider RSA = new RSACryptoServiceProvider();
            RSAParameters RSAPublicKeyInfo = RSA.ExportParameters(false);
            RSAParameters RSAPrivateKeyInfo = RSA.ExportParameters(true);
            AnekaApplication<AnekaThread, ThreadManager> app = null;
            Console.WriteLine("Initialized Master");
            try
            {
                // Initialize Blockchain
                var myBlockChain = new Blockchain();
                Console.WriteLine("Hash : " + myBlockChain.GetLatestBlock().hash);

                while (true)
                {
                    // Start Aneka
                    Logger.Start();
                    Configuration conf =
                    Configuration.GetConfiguration(@"C:\xampp\htdocs\HealthKeeper\Aneka\conf.xml");

                    // Analyze data.txt
                    while (Analyze() == false)
                    {
                        System.Threading.Thread.Sleep(500);
                        continue;
                    }
                    Console.WriteLine("Data Parsed");

                    // Start Aneks application
                    app = new AnekaApplication<AnekaThread, ThreadManager>(conf);
                    
                    // Parse data.txt
                    List<int>[] partitions = new List<int>[2];
                    int maxSize = (int)Math.Ceiling(list.Count / (double)2);
                    int k = 0;
                    for (int i = 0; i < 2; i++)
                    {
                        partitions[i] = new List<int>();
                        for (int j = k; j < k + maxSize; j++)
                        {
                            if (j >= list.Count)
                                break;
                            partitions[i].Add(intlist[j]);
                        }
                        k += maxSize;
                    }

                    oldhash = myBlockChain.GetLatestBlock().hash;

                    // Add data partitions to blockchain (assuming no exception in signature validation below)
                    myBlockChain.AddBlock(partitions[0]);
                    Console.WriteLine("Hash value : " + myBlockChain.GetLatestBlock().hash);
                    myBlockChain.AddBlock(partitions[1]);
                    Console.WriteLine("Hash value : " + myBlockChain.GetLatestBlock().hash);

                    newhash = myBlockChain.GetLatestBlock().hash;

                    // Signature makes sure the transaction is legit (as private key in invisible)
                    // Initialize 2 HelloWorld objects for two data halves
                    signature = SignData(string.Join(";", partitions[0].Select(x => x.ToString()).ToArray()), RSAPrivateKeyInfo);
                    HelloWorld hw = new HelloWorld(partitions[0], signature, RSAPublicKeyInfo, oldhash, newhash);
                    signature = SignData(string.Join(";", partitions[1].Select(x => x.ToString()).ToArray()), RSAPrivateKeyInfo);
                    HelloWorld hw2 = new HelloWorld(partitions[1], signature, RSAPublicKeyInfo, oldhash, newhash);

                    // Declare Aneka thread array
                    AnekaThread[] th = new AnekaThread[2];
                    Console.WriteLine("Aneka thread array declared");
                    
                    // Initialize Aneka threads with PrintHello method
                    th[0] = new AnekaThread(hw.PrintHello, app);
                    th[1] = new AnekaThread(hw2.PrintHello, app);

                    // Start threads
                    th[0].Start();
                    th[1].Start();

                    Console.WriteLine("Data sent for analysis");

                    // Wait for first thread to finish
                    th[0].Join();
                    hw = (HelloWorld)th[0].Target;
                    foreach (var val in partitions[0])
                    {
                        Console.WriteLine("Value in partitions[0] : " + val);
                    }

                    Console.WriteLine("Check : " + hw.check);
                    Console.WriteLine("Value : {0} , NodeId:{1},SubmissionTime:{2},Completion Time{3}", hw.result, th[0].NodeId, th[0].SubmissionTime, th[0].CompletionTime);
                    Console.WriteLine("Minimum : {0}", hw.min);
                    Console.WriteLine("Count : {0}", hw.count);

                    // Wait for second thread to finish
                    th[1].Join();
                    hw2 = (HelloWorld)th[1].Target;
                    foreach (var val in partitions[1])
                    {
                        Console.WriteLine("Value in partitions[1] : " + val);
                    }

                    Console.WriteLine("Check : " + hw2.check);
                    Console.WriteLine("Value : {0} , NodeId:{1},SubmissionTime:{2},Completion Time{3}", hw2.result, th[1].NodeId, th[1].SubmissionTime, th[1].CompletionTime);
                    Console.WriteLine("Minimum : {0}", hw2.min);
                    Console.WriteLine("Count : {0}", hw2.count);

                    // Verify data not tampered
                    if(hw.checkhash == false && hw2.checkhash == false)
                    {
                        throw new Exception("Data tampered in Master!");
                    }
                    Console.WriteLine("Checked for data tamper. No data has been modified.");
                    
                    // Validate blockchain
                    myBlockChain.ValidateChain();
                    Console.WriteLine("Blockchain Validation checked!");

                    // Compile all results
                    allData = allData.Concat(partitions[0]).Concat(partitions[1]).ToList();

                    // Publish results in result.txt
                    totalcount = hw.count + hw2.count;
                    if (hw.min < hw2.min)
                        minima = hw.min;
                    else
                        minima = hw2.min;
                    if (totalcount < 5)
                        sev = "None";
                    else if (totalcount < 15)
                        sev = "Mild";
                    else if (totalcount < 30)
                        sev = "Moderate";
                    else
                        sev = "Highly severe";

                    Console.WriteLine("Result : " + totalcount + ", " + minima);

                    int line_to_edit = 1; // Warning: 1-based indexing!
                    string sourceFile = path;
                    string destinationFile = @"C:\xampp\htdocs\HealthKeeper\Aneka\result.txt";

                    // Read the appropriate line from the file.
                    string lineToWrite = null;
                    using (StreamReader reader = new StreamReader(sourceFile))
                    {
                        for (int i = 1; i <= line_to_edit; ++i)
                            lineToWrite = reader.ReadLine();
                    }

                    if (lineToWrite == null)
                        throw new InvalidDataException("Line does not exist in " + sourceFile);

                    // Read the old file.
                    string[] lines = new string[4];
                    string[] linestemp = File.ReadAllLines(sourceFile);

                    lines[0] = "For 1 hour of Sleep Apnea Data :";
                    lines[1] = "AHI (Apnea-hypopnea index) = " + totalcount;
                    lines[2] = "Minimum Oxygen Level = " + minima;
                    lines[3] = "Disease severity = " + sev;
                    System.IO.File.WriteAllLines(destinationFile, lines);
                    linestemp[0] = "Analysis Done = true";
                    System.IO.File.WriteAllLines(sourceFile, linestemp);

                }

                
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
                Console.WriteLine(e.StackTrace);
            }
            finally
            {
                app.StopExecution();
                Logger.Stop();
            }
        }

        static bool Analyze()
        {
            try
            {
                Console.WriteLine("Checking data for pending Analysis");
                string readText = File.ReadAllText(path);
                var result = readText.Split("\n\r".ToCharArray(), StringSplitOptions.RemoveEmptyEntries);
                if (result[0] == "Analysis Done = false")
                {
                    Console.WriteLine("DEBUG: " + result[0]);
                }
                else if (result[0] == "Analysis Done = true")
                {
                    Console.WriteLine("DEBUG: " + result[0]);
                    return false;
                }
                list = (result[1]).Split(',').ToList<string>();
                intlist = list.ConvertAll(s => Int32.Parse(s));
                return true;
            }
            catch
            {
                Console.WriteLine("Exception!");
                return false;
            }

        }
        public static string SignData(string message, RSAParameters privateKey)
        {
            ASCIIEncoding byteConverter = new ASCIIEncoding();
            byte[] signedBytes;
            using (var rsa = new RSACryptoServiceProvider())
            {
                // Write the message to a byte array using ASCII as the encoding.
                byte[] originalData = byteConverter.GetBytes(message);
                try
                {
                    // Import the private key used for signing the message
                    rsa.ImportParameters(privateKey);
                    // Sign the data, using SHA512 as the hashing algorithm 
                    signedBytes = rsa.SignData(originalData, CryptoConfig.MapNameToOID("SHA512"));
                }
                catch (CryptographicException e)
                {
                    Console.WriteLine(e.Message);
                    return null;
                }
                finally
                {
                    // Set the keycontainer to be cleared when rsa is garbage collected.
                    rsa.PersistKeyInCsp = false;
                }
            }
            // Convert the byte array back to a string message
            return Convert.ToBase64String(signedBytes);
        }
    }
}