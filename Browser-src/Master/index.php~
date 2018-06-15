<html>
<head><title>HealthKeeper - Login</title></head>
<body>

<!-- Title -->
<h1>HealthKeeper - Login Page</h1>

<!-- Login Form -->
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

// Database settings
$host = "localhost";
$user = "root";
$pass = "";
$db = "users";

// Connection to database
$dbConnected = mysqli_connect($host, $user, $pass, $db);
$dbSelected = mysqli_select_db($dbConnected, "users");

$dbSuccess = true;

// Show Database connection on screen
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

// Check login credentials
if(isset($_POST['username']) && isset($_POST['password']) && $dbSuccess){
	$username = $_POST['username'];
	$password = $_POST['password'];

	$sql = "select * from registrations where username='".$username."' AND
	password='".$password."' limit 1";

	$result = mysqli_query($dbConnected,$sql);

	if(mysqli_num_rows($result)==1){
		// Go to session page if login successful 
		echo "Login Successful!";
		header('Location: home.php/?username='.$_POST['username']);  
	}
	else{
		// Show incorrect credentials otherwise
		echo "Username or password incorrect!";
		exit();
	}
}




?>


</body>
</html>