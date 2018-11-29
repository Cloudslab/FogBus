<?php
// Coded by:
// Shreshth Tuli

// Display CPU load

//Linux:
$load = sys_getloadavg();
echo $load[0];

//Windows:
//exec('wmic cpu get LoadPercentage', $p);
//$load = trim($p[1], '"');
//echo $load/100;

?>

