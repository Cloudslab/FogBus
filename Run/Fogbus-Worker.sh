#!/bin/bash
cd /var/www/html/HealthKeeper/
echo "Note the worker IP address :"
hostname -I
echo "Press Enter to run"
read
chromium-browser localhost/HealthKeeper/manager.php --no-sandbox &
java -jar WorkerInterface.jar
