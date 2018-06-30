<html>
<head><title>HealthKeeper - Worker</title></head>
<body>

<?php
if(isset($_GET['data1'])){
	
	// Verify publickey
	$file = fopen("config.txt", "r");
	$masterIP = fgets($file);
	$masterIP = preg_replace('/\s+/', '', $masterIP);
	fclose($file);
	$array = explode(":", $masterIP);
	$masterIP = $array[1];
	
	$file = fopen("http://".$masterIP."/HealthKeeper/RPi/Master/publicKey.txt", "r");
	$publickey = fgets($file);
	fclose($file);

	if(isset($_GET['publickey']) && preg_replace('/\s+/', '', $publickey) != rawurldecode(preg_replace('/\s+/', '', $_GET['publickey']))){
		exit("Unknown Source. Discarding Block");	
	}	
	
	// Write Block Data to file
	$file = fopen("block.txt", "w+");
	fwrite($file, "New Data".PHP_EOL);
	$i = 1;
	while(true) {
		if(isset($_GET['data'.$i])){
			$content = $_GET['data'.$i];
			fwrite($file,$content.PHP_EOL);
		}
		else{
			break;		
		}		
		$i += 1;
	}
	fwrite($file, $_GET['hash'].PHP_EOL);
	fwrite($file, $_GET['prevhash'].PHP_EOL);
	fwrite($file, $_GET['salt'].PHP_EOL);
	fwrite($file, rawurldecode($_GET['publickey']).PHP_EOL);
	fwrite($file, rawurldecode($_GET['signature']).PHP_EOL);
	fclose($file);

	// Wait for Block addition 
	$file = fopen("block.txt", "r");
	$k = fgets($file);
	while(!preg_match("/Block Added/", $k)){
		fclose($file);
		$file = fopen("block.txt", "r");
		$k = fgets($file);
		usleep(500000);
	}
	fclose($file);	
	
	// Read errors and display
	$result = fopen("error.txt", "r");
	while (!feof($result)) {
   	$content = fgets($result); 
   	echo $content."<br/>";
	}
}

?>

</body>
</html>

