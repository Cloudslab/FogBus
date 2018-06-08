<html>
<head><title>HealthKeeper - Worker</title></head>
<body>

<?php
if(isset($_GET['data'])){
	
	// Write Data to file
	$content = $_GET['data'];
	$file = fopen("data.txt", "w+");
	fwrite($file, "Analysis Done = false".PHP_EOL);
	fwrite($file,$content.PHP_EOL);
	fclose($file);

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
	$file1 = fopen("result.txt", "r");
	$result = fgets($file1);
	$array = explode(",", $result);
	$count = (int)$array[0];
	$min = (int)$array[1];
	echo "For 1 hour of sleep data<br />";
	echo "AHI (Apnea-hypopnea index) = ".$count."<br />";
	echo "Minimum Oxygen Level reached : ".$min."<br />";
	$sev = "";
	if($count < 5){
		$sev = "None";
	}
	elseif($count < 15){
		$sev = "mild";
	}
	elseif($count < 30){
		$sev = "moderate";	
	}
	else{
		$sev = "Highly severe!";	
	}
	
	echo "Disease severity : ".$sev;	
		
}

?>

</body>
</html>

