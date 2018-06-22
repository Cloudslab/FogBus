<html>
<head><title>HealthKeeper - Worker</title></head>
<body>

<?php
if(isset($_GET['data1'])){
	
	// Write Data to file
	$file = fopen("data.txt", "w+");
	fwrite($file, "Analysis Done = false".PHP_EOL);
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
	fclose($file);

	// Execute Analysis code
	exec("java -jar analyzer.jar");

	// Wait for analysis done
	$file = fopen("data.txt", "r");
	$k = fgets($file);
	while(!preg_match("/Analysis Done = true/", $k)){
		fclose($file);
		$file = fopen("data.txt", "r");
		$k = fgets($file);
		usleep(500000);
	}
	fclose($file);	
	
	// Read results and display
	$result = fopen("result.txt", "r");
	while (!feof($result)) {
   	$content = fgets($result); 
   	echo $content."<br/>";
	}
}

?>

</body>
</html>

