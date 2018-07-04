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

Enter Data for analysis:
<!-- Submit for analysis -->
<form id='data1' method='get'>
<input type='text' name='data1' id='data1'  maxlength="500" />
<input type='submit' name='analyze' value='analyze' />
</form>

<?php

// If data entered, show the data values stored in data files
$datafile = fopen("data.txt", "w");
$data = "New Data".PHP_EOL;
$i = 1;
while(true){
	if(isset($_GET['data'.$i])){
		$file = fopen("data".$i.".txt", "a");
		$content = $_GET['data'.$i];
		echo "Data".$i." Values Stored : ";
		echo $content.PHP_EOL;
		if(0==filesize("data".$i.".txt")){
			fwrite($file,$content);
		}else{
			fwrite($file,",".$content);
		}
		fclose($file);
		
		$data .= $content.PHP_EOL;
	}
	else{
		break;	
	}
	$i = $i + 1;
}

if(isset($_GET['data1'])){
	fwrite($datafile, $data);
}
?>



<?php

// If Analyze is clicked
if(isset($_GET['analyze'])){

	// Wait for Blockchain update
	$file = fopen("data.txt", "r");
	$k = fgets($file);
	while(!preg_match("/Block Added/", $k)){
		fclose($file);
		$file = fopen("data.txt", "r");
		$k = fgets($file);
		usleep(500000);
	}
	fclose($file);

	$content = array();
	$i = 1;
	while(true) {
		if(isset($_GET['data'.$i])){
			array_push($content, $_GET['data'.$i]);
		}
		else{
			break;		
		}		
		$i = $i + 1;
	}
	
	// Parse config.txt for IPs 
	$file = fopen("config.txt", "r");
	$line = fgets($file);
	$choiceArray = explode(" ", $line);
		
	// Initialize choices
	// true if work given to them, else false
	$toMaster = true;	
	if($choiceArray[0] == "DisableMaster"){
		$toMaster = false;	
	}
	$toAneka = true;	
	if($choiceArray[1] == "DisableAneka"){
		$toAneka = false;	
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
		// If any load < 80% then toMaser and toAneka = false
	  	if($my_var <= 0.8){
	  		$toMaster = false;
	  		$toAneka = false;
	  	}
	}
	
	$result = "";
	
	if($toMaster && $toAneka){
		if(rand(0, 1)==0){
			$toMaster = false;		
		}	
	}
	
	// Form get request
	// Add data
	$getRequest = '';
	$i = 1;
	foreach($content as $data){
		$getRequest .= 'data'.$i.'='.$data.'&';
		$i += 1;
	}
	$getRequest = substr($getRequest, 0, strlen($getRequest)-1);
	
	// Add hash, prevhash, salt, public Key and sigature from file
	$file = fopen("data.txt", "r");
	$k = fgets($file); // Block added
	$k = fgets($file); // Data1
	$k = fgets($file); // Data2
	$k = fgets($file); // Hash
	$k = preg_replace('/\s+/', '', $k);
	$getRequestblock = $getRequest.'&hash='.urlencode($k);
	$k = fgets($file); // PrevHash
	$k = preg_replace('/\s+/', '', $k);
	$getRequestblock .= '&prevhash='.urlencode($k);
	$k = fgets($file); // Salt
	$k = preg_replace('/\s+/', '', $k);
	$getRequestblock .= '&salt='.urlencode($k);
	$k = fgets($file); // Public Key
	$k = preg_replace('/\s+/', '', $k);
	$getRequestblock .= '&publickey='.urlencode($k);
	$k = fgets($file); // Signature
	$k = preg_replace('/\s+/', '', $k);
	$getRequestblock .= '&signature='.urlencode($k);
	
	// $getRequest is one with data only, $getRequest2block has data and block details
	// $getRequest if for worker.php, $getRequestBlock is for blockchain.php
	
	// Send new data to be added to blockchain
	for($i=0; $i<count($ips); $i++) {
		$ipworker = $ips[$i];
		$ipworker = preg_replace('/\s+/', '', $ipworker);
		$block =	@file_get_contents('http://'.$ipworker.'/HealthKeeper/blockchain.php/?'.$getRequestblock); 
		if(strpos($block, 'Tamper') !== false || strpos($block, 'block') !== false){
			//echo "<br/>Error at Worker IP : ".$ipworker."<br/>";
			//echo $block.PHP_EOL;
		}	
	}	
	
	// Debug : Blockchain data sent to master
	//$block =	@file_get_contents('http://localhost/HealthKeeper/RPi/Worker/blockchain.php/?'.$getRequestblock); 
	//echo "Error : ".$block;
	
		
	if(!$toMaster && !$toAneka){
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
		// Send data for analysis
		echo "<br/><br/>Work sent to Worker ".($minindex+1)." with IP address : ".$ipworker."<br/><br/>";	
		// Get result and store in $result variable
		$result = file_get_contents('http://'.$ipworker.'/HealthKeeper/worker.php/?'.$getRequest);
	}
	elseif($toMaster) {
		// Work done by master
		$minindex = 0;
		$ipworker = "localhost";	
		echo "<br/><br/>Work Done by Master<br/><br/>";
		$result = file_get_contents('http://'.$ipworker.'/HealthKeeper/RPi/Worker/worker.php/?'.$getRequest.'&analyze=true');
	}
	else{
		// Work done to Aneka
		$ipworker = "localhost";
		echo "<br/><br/>Work Done by Aneka<br/><br/>";
		$result = file_get_contents('http://'.$ipworker.'/HealthKeeper/Aneka/workerAneka.php/?'.$getRequest);
	}
	
	echo $result;

	// Graph 
	$i = 1;
	$totalData = array();
	while(true){
		if(!isset($_GET['data'.$i])){
			break;		
		}
		$file1 = fopen("data".$i.".txt", "r");
		$result = fgets($file1);
		$allArray = explode(",", $result);
		$dataPoints = array();
		foreach ($allArray as $value) {
   		array_push($dataPoints, array("y" => (int)$value, "label" => "-"));
		}
		fclose($file1);
		array_push($totalData, $dataPoints);	
		$i+=1;
	}


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
		dataPoints: <?php echo json_encode(array_pop($totalData), JSON_NUMERIC_CHECK); ?>},
		{
	   markerType: "none", 
		type: "line",
		dataPoints: <?php echo json_encode(array_pop($totalData), JSON_NUMERIC_CHECK); ?>
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
	$i = 1;
	while(true){
		if(!isset($_GET['data'.$i])){
			break;		
		}
		file_put_contents("data".$i.".txt", "");
		$i+=1;
	}
	
	echo "All Data removed<br/>";
}
?>


</body>
</html>