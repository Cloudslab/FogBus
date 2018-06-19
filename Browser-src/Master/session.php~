<!-- 
	Coded By:
	Shreshth Tuli
-->

<html>
<head><title>HealthKeeper - Login</title></head>
<body>

<h1>HealthKeeper - Session Page</h1>

<?php

session_start();

// Welcome user
if(isset($_GET['username'])){
	$username = $_GET['username'];
	echo "<h2>Hello ".htmlspecialchars($username)."</h2> ";
}
else{
	echo "<h2>Hello</h2> ";
}

?>

Enter data for analysis:
<!-- Submit for analysis -->
<form id='data' method='get'>
<input type='text' name='data' id='data'  maxlength="500" />
<input type='submit' name='analyze' value='analyze' />
</form>

<?php

// If data entered, show the data values stored in data.txt
if(isset($_GET['data'])){

	$file = fopen("data.txt", "a");
	$content = $_GET['data'];
	echo "Data Values Stored : ";
	echo $content;
	if(0==filesize("data.txt")){
		fwrite($file,$content);
	}else{
		fwrite($file,",".$content);
	}
	fclose($file);
}

?>



<?php

// If Analyze is clicked
if(isset($_GET['analyze'])){

	$content = $_GET['data'];
	
	// Parse config.txt for IPs 
	$file = fopen("config.txt", "r");
	$line = fgets($file);
	
	// Initialize toMaster
	// true if work given to master, else false
	$toMaster = true;	
	if($line == "DisableMaster"){
		$toMaster = false;	
	}
	$ips = array();
	while(($line = fgets($file)) !== false){
	  array_push($ips, $line);
	}
	
	// Initialize loads array to store loads of workers
	$loads = array();
	// For each IP, get load from load.php
	foreach($ips as $ip){
		$ip = preg_replace('/\s+/', '', $ip);
		$dataFromExternalServer = @file_get_contents("http://".$ip."/HealthKeeper/load.php");
		if($dataFromExternalServer != FALSE){
			$dataFromExternalServer = preg_replace('/\s+/', '', $dataFromExternalServer);	
			$my_var = 0.0 + $dataFromExternalServer;
			echo "<br/>Woker load with IP ".$ip.": ".$my_var;
		} else{
			$my_var = 100;
			echo "<br/>Woker with IP ".$ip.": compromised - Error \"Could not connect to fog node\"";
		}
		array_push($loads, $my_var);	
		// If any load < 80% then toMaser = false
	  	if($my_var <= 0.8){
	  		$toMaster = false;
	  	}
	}
	
	
	$result = "";
	
	if(!$toMaster){
		// Work given to worker with least load
		$min = 100;
		$minindex = 0;
		foreach($loads as $load){
			if ($min > $load){
				$min = $load;			
			}		
		}
		foreach($loads as $load ){
			if($min == $load){
				break;			
			}		
			$minindex = $minindex+1;
		}
		$ipworker = $ips[$minindex];
		$ipworker = preg_replace('/\s+/', '', $ipworker);
		// Send data
		echo "<br/><br/>Work sent to Worker ".($minindex+1)." with IP address : ".$ipworker."<br/><br/>";	
		// Get result and store in $result variable
		$result = file_get_contents('http://'.$ipworker.'/HealthKeeper/worker.php/?data='.$_GET['data']);
	}
	else {
		// Work done by master
		$minindex = 0;
		$ipworker = "localhost";	
		echo "<br/><br/>Work Done by Master<br/><br/>";
		$result = file_get_contents('http://'.$ipworker.'/HealthKeeper/RPi/Worker/worker.php/?data='.$_GET['data']);
	}
	
	echo $result;

	// Graph 
	$file1 = fopen("data.txt", "r");
	$result = fgets($file1);
	$allArray = explode(",", $result);
	$dataPoints = array();
	$criticalPoints = array();
	foreach ($allArray as $value) {
    array_push($dataPoints, array("y" => (int)$value, "label" => "-"));
    array_push($criticalPoints, array("y" => 88, "label" => "-"));
	}


	fclose($file1);

}

?>
<script>
window.onload = function () {
 
var chart = new CanvasJS.Chart("chartContainer", {
	title: {
		text: "Sleep Apnea Graph"
	},
	axisY: {
		title: "Oxygen Level"
	},
	data: [{
		markerType: "none",
		type: "line",
		dataPoints: <?php echo json_encode($dataPoints, JSON_NUMERIC_CHECK); ?>},
		{
	   markerType: "none", 
		type: "line",
		dataPoints: <?php echo json_encode($criticalPoints, JSON_NUMERIC_CHECK); ?>
	}]
});
chart.render();
 
}
</script>
<div id="chartContainer" style="height: 370px; width: 100%;"></div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>

<form id='data' method='post'>
<input type='submit' name='reset' value='Reset All Data' />
</form>
<?php

// Reset all data
if(isset($_POST['reset'])){
	file_put_contents("data.txt", "");
	echo "All Data removed<br/>";
}
?>


</body>
</html>