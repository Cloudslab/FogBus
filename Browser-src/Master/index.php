<html>
<head><title>HealthKeeper - Login</title></head>
<body>


<h1>HealthKeeper - Login Page</h1>
<form id='login' action='index.php' method='post' accept-charset='UTF-8'>
<fieldset >
<legend>Login</legend>
<label for='username' >Username:</label>
<br>
<input type='text' name='username' id='username'  maxlength="50" />
<br>
<label for='password' >Password:</label>
<br>
<input type='password' name='password' id='password' maxlength="50" />
<br><br>
<input type='submit' name='Submit' value='Submit' />
</fieldset>
</form>

<?php

session_start();

$host = "localhost";
$user = "root";
$pass = "";
$db = "users";

$dbConnected = mysqli_connect($host, $user, $pass, $db);
$dbSelected = mysqli_select_db($dbConnected, "users");

$dbSuccess = true;

if($dbConnected){
	echo "MySQL Connection OK<br />";
	if($dbSelected){
		echo "DB Connection OK<br />";
	}
	else{
		echo "DB Connection FAIL<br />";
		$dbSuccess = false;
	}
}
else{
	echo "MySQL Connection FAIL<br />"; 
	$dbSuccess = false;
}

if(isset($_POST['username']) && isset($_POST['password']) && $dbSuccess){
	$username = $_POST['username'];
	$password = $_POST['password'];

	$sql = "select * from registrations where username='".$username."' AND
	password='".$password."' limit 1";

	$result = mysqli_query($dbConnected,$sql);

	if(mysqli_num_rows($result)==1){
		echo "Login Successful!";
		header('Location: session.php/?username='.$_POST['username']);  
	}
	else{
		echo "Username or password incorrect!";
		exit();
	}
}




?>


</body>
</html>