#!/bin/bash
sudo ./lamp.sh
sudo service apache2 restart
sudo mkdir /var/www/html/HealthKeeper/
sudo chmod -R 777 /var/www/html/HealthKeeper/
sudo cp ../../Browser-src/Worker/* /var/www/html/HealthKeeper/
sudo chmod 777 /var/www/html/HeathKeeper/*
cd /var/www/html/HealthKeeper/
javac ./analyzer.java
echo ".................................."
echo "Successfully Installed Raspi-Fog"
echo "Note the worker IP address :"
hostname -I
echo "Press Enter to run"
read
chromium-browser localhost/HealthKeeper/manager.php &
java analyzer

