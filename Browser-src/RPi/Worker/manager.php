<!-- 
	Coded By:
	Shreshth Tuli
-->

<html>
<head><title>HealthKeeper - Manager</title>
</head>
<body>

<?php

if(isset($_POST['ip'])){
	// Set Master IP
	$file = fopen("config.txt", "w+");
	$k = "Master IP : ".$_POST['ip'];
	echo "Master IP Set to : ".$_POST['ip'].PHP_EOL;
	fwrite($file, $k);
	fclose($file);	
	
}
else{
	echo "Set Master IP here<br/>".PHP_EOL;
	$file = fopen("config.txt", "r");
	$k = fgets($file);
	echo $k.PHP_EOL;
	// Form to input Master IP
	echo "
	<form id='ip' method='post'>
	<input type='text' name='ip' id='ip'  maxlength=\"500\" />
	</form>
	";
	echo 	"<form id='Change IP' method='post'>
	<input type='submit' name='Change IP' value='Change IP' />
	</form>";
	fclose($file);

}

// Syncronize Jar file from Master Node
if(isset($_GET['sync'])){
	$file = fopen("config.txt", "r");
	$masterIP = fgets($file);
	$masterIP = preg_replace('/\s+/', '', $masterIP);
	fclose($file);
	$array = explode(":", $masterIP);
	$masterIP = $array[1];
	shell_exec("sudo rm -rf *.jar");
	shell_exec("wget -O analyzer.jar http://".$masterIP."/HealthKeeper/RPi/Worker/analyzer.jar");
	shell_exec("sudo chmod 777 *");
}

// Set Master IP by Get request
if(isset($_GET['setIP'])){
	$file = fopen("config.txt", "w+");
	$k = "Master IP : ".$_GET['setIP'];
	echo "Master IP Set to : ".$_GET['setIP'].PHP_EOL;
	fwrite($file, $k);
	fclose($file);	
}
?>


</body>
</html>
