<html>
<head><title>HealthKeeper - Test</title></head>
<body>

<h1>HealthKeeper - Testing Page</h1>

<?php
ini_set('max_execution_time', 400);
session_start();

echo "Testing on Max Load<br/><br/>";

$global_time_start = microtime(true);
$file = fopen("latency.txt", "a");

while(true){
	
	sleep(1);

	$time_start = microtime(true);
	$result = file_get_contents("http://localhost/HealthKeeper/RPi/Master/session.php/?data1=78,89,90,89,78,90,89,78,67,79,98,78,67,78,89&data2=67,69,78,89,78,89,78,67,67,78,89,90,89,78,89&analyze=analyze");
	echo $result;	
	$time_stop = microtime(true);

	$latency = $time_stop - $time_start;
	fwrite($file, $latency.PHP_EOL);
	
	$time = $time_stop - $global_time_start;
	echo "Latency = ".$latency."<br/>";
	echo "Time = ".$time."<br/>";
	
	if($time>=300){
		break;	
	}
}


fclose($file);


?>

</body>
</html>