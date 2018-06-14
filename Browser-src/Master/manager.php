<html>
<head><title>HealthKeeper - Manager (Master)</title>
</head>
<body>

<?php

// Remove all worker nodes
if(isset($_POST['remove'])){
	file_put_contents("config.txt", "EnableMaster".PHP_EOL);
	echo "All Workers removed<br/>";
}
{
	// Read IPs from config.txt
	$file = fopen("config.txt", "r");
	$content = "";
	$line = fgets($file);
	while(($line = fgets($file)) !== false){
		$content=$content.$line;	
	}
	fclose($file);
	
	// Alter first line of config.txt as per Enable master set or not
	if(isset($_POST['enable'])){
		file_put_contents("config.txt", "EnableMaster".PHP_EOL.$content);
	}
	else{
		file_put_contents("config.txt", "DisableMaster".PHP_EOL.$content);
	}
	
	// If new IP added, add to config.txt
	if(isset($_POST['ip']) && $_POST['ip']!=""){
	$file = fopen("config.txt", "a");
	$k = $_POST['ip']."\n";
	echo "Worker IP added : ".$_POST['ip']."<br/>";
	fwrite($file, $k);
	fclose($file);	
	
	}
	{
	// Display IPs already set
	echo "Set Worker IPs here <br/>";
	$file = fopen("config.txt", "r");
	$line = fgets($file);
	while(($line = fgets($file)) !== false){
		echo "Worker IP : ".$line."<br/>";	
	}
	fclose($file);
	echo "<br/>"."Add Worker IP<br/>";
	echo "
	<form id='ipinfo' method='post'>
	<input type='checkbox' name='enable' value='Yes' checked />Enable Master as Worker <br/>
	<input type='text' name='ip' id='ip'  maxlength=\"500\" /> <br/>
	<input type='submit' name='add' value='Add Worker' /> <br/><br/>
	<input type='submit' name='remove' value='Remove all workers' />
	</form>
	";
	}
}

?>


</body>
</html>
