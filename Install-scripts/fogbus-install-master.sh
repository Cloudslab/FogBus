#!/bin/bash
apt-get update
apt-get upgrade -y
apt-get install oracle-java8-jdk -y
apt-get install ant git vim -y
apt-get install apache2 -y
echo "ServerName 127.0.0.1" >> /etc/apache2/apache2.conf
apache2ctl configtest
apt-get install php libapache2-mod-php php-mcrypt php-mysql -y
service apache2 restart
apt-get install mysql-server -y
mysql -u root -praspberry -e "CREATE DATABASE data;GRANT ALL PRIVILEGES ON data.* TO 'root'@'localhost' IDENTIFIED BY 'raspberry';FLUSH PRIVILEGES;"
service apache2 restart
sudo mkdir /var/www/html/HealthKeeper/
sudo chmod -R 777 /var/www/html/HealthKeeper/
sudo chmod -R 777 /var/www/html/HealthKeeper/RPi/
sudo cp -a ./FogBus/Browser-src/* /var/www/html/HealthKeeper/
sudo chmod 777 /var/www/html/HealthKeeper/RPi/
sudo chmod 777 /var/www/html/HealthKeeper/RPi/Master/*
sudo chmod 777 /var/www/html/HealthKeeper/RPi/Worker/*
echo ".................................."
echo "Successfully Installed Raspi-Fog"
echo "Note the Master IP address :"
hostname -I
echo "Configure Database as described in End-User Tutorial then press Enter to run"
read
chromium-browser localhost/HealthKeeper/RPi/Master/ --no-sandbox &
cd /var/www/html/HealthKeeper/RPi/Master/
java -jar MasterInterface.jar


