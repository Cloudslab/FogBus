# Copyright (C) 2014 - 2018, Teddysun <i@teddysun.com>
# 
# This file is part of the LAMP script.
#
# LAMP is a powerful bash script for the installation of 
# Apache + PHP + MySQL/MariaDB/Percona and so on.
# You can install Apache + PHP + MySQL/MariaDB/Percona in an very easy way.
# Just need to input numbers to choose what you want to install before installation.
# And all things will be done in a few minutes.
#
# Website:  https://lamp.sh
# Github:   https://github.com/teddysun/lamp

load_config(){

#Install location
apache_location=/usr/local/apache
mysql_location=/usr/local/mysql
mariadb_location=/usr/local/mariadb
percona_location=/usr/local/percona
php_location=/usr/local/php
openssl_location=/usr/local/openssl

#Install depends location
depends_prefix=/usr/local

#Web root location
web_root_dir=/data/www/default

#Download root URL
download_root_url="https://dl.lamp.sh/files"

#parallel compile option,1:enable,0:disable
parallel_compile=1

##Software version
#nghttp2
nghttp2_filename="nghttp2-1.32.0"
#openssl
openssl_filename="openssl-1.0.2o"
#apache2.4
apache2_4_filename="httpd-2.4.33"
#mysql5.5
mysql5_5_filename="mysql-5.5.60"
#mysql5.6
mysql5_6_filename="mysql-5.6.40"
#mysql5.7
mysql5_7_filename="mysql-5.7.22"
#mysql8.0
mysql8_0_filename="mysql-8.0.11"
#mariadb5.5
mariadb5_5_filename="mariadb-5.5.60"
#mariadb10.0
mariadb10_0_filename="mariadb-10.0.35"
#mariadb10.1
mariadb10_1_filename="mariadb-10.1.34"
#mariadb10.2
mariadb10_2_filename="mariadb-10.2.15"
#mariadb10.3
mariadb10_3_filename="mariadb-10.3.7"
#percona5.5
percona5_5_filename="Percona-Server-5.5.60-38.12"
#percona5.6
percona5_6_filename="Percona-Server-5.6.40-84.0"
#percona5.7
percona5_7_filename="Percona-Server-5.7.22-22"
#php5.6
php5_6_filename="php-5.6.36"
#php7.0
php7_0_filename="php-7.0.30"
#php7.1
php7_1_filename="php-7.1.18"
#php7.2
php7_2_filename="php-7.2.6"
#phpMyAdmin
phpmyadmin_filename="phpMyAdmin-4.8.1-all-languages"

#apr
apr_filename="apr-1.6.3"
#apr-util
apr_util_filename="apr-util-1.6.1"
#mod_wsgi
mod_wsgi_filename="mod_wsgi-4.6.4"
#mod_jk
mod_jk_filename="tomcat-connectors-1.2.43-src"
set_hint ${mod_jk_filename} "mod_jk-1.2.43"
#mod_security
mod_security_filename="modsecurity-2.9.2"
set_hint ${mod_security_filename} "mod_security-2.9.2"
#mhash
mhash_filename="mhash-0.9.9.9"
#libmcrypt
libmcrypt_filename="libmcrypt-2.5.8"
#mcrypt
mcrypt_filename="mcrypt-2.6.8"
#pcre
pcre_filename="pcre-8.42"
#re2c
re2c_filename='re2c-1.0.2'
#imap
imap_filename='imap-2007f'
#libiconv
libiconv_filename="libiconv-1.15"
#swoole
swoole_filename="swoole-src-2.2.0"
set_hint ${swoole_filename} "php-swoole-2.2.0"
#xcache
xcache_filename="xcache-3.2.0"
#xdebug
xdebug_filename="xdebug-2.6.0"
#ImageMagick
ImageMagick_filename="ImageMagick-7.0.7-38"
php_imagemagick_filename="imagick-3.4.3"
set_hint ${php_imagemagick_filename} "php-${php_imagemagick_filename}"
#GraphicsMagick
GraphicsMagick_filename="GraphicsMagick-1.3.29"
php_graphicsmagick_filename="gmagick-1.1.7RC3"
php_graphicsmagick_filename2="gmagick-2.0.5RC1"
set_hint ${php_graphicsmagick_filename} "php-${php_graphicsmagick_filename}"
set_hint ${php_graphicsmagick_filename2} "php-${php_graphicsmagick_filename2}"
#ionCube
ionCube_filename="ioncube_loaders"
ionCube32_filename="ioncube_loaders_lin_x86"
ionCube64_filename="ioncube_loaders_lin_x86-64"
#libevent
libevent_filename="libevent-2.0.22-stable"
#memcached
memcached_filename="memcached-1.5.8"
#libmemcached
libmemcached_filename="libmemcached-1.0.18"
#php-memcached
php_memcached_filename="memcached-2.2.0"
php_memcached_filename2="memcached-3.0.4"
set_hint ${php_memcached_filename} "php-${php_memcached_filename}"
set_hint ${php_memcached_filename2} "php-${php_memcached_filename2}"
#redis
redis_filename="redis-4.0.9"
#php-redis
php_redis_filename="redis-2.2.8"
php_redis_filename2="redis-4.0.2"
set_hint ${php_redis_filename} "php-${php_redis_filename}"
set_hint ${php_redis_filename2} "php-${php_redis_filename2}"
#php-mongodb
php_mongo_filename="mongodb-1.4.3"
set_hint ${php_mongo_filename} "php-${php_mongo_filename}"
#php-libsodium
libsodium_filename="libsodium-1.0.16"
php_libsodium_filename="libsodium-php-2.0.11"


#software array setting
apache_arr=(
${apache2_4_filename}
do_not_install
)

apache_modules_arr=(
${mod_wsgi_filename}
${mod_security_filename}
${mod_jk_filename}
do_not_install
)

mysql_arr=(
${mysql5_5_filename}
${mysql5_6_filename}
${mysql5_7_filename}
${mysql8_0_filename}
${mariadb5_5_filename}
${mariadb10_0_filename}
${mariadb10_1_filename}
${mariadb10_2_filename}
${mariadb10_3_filename}
${percona5_5_filename}
${percona5_6_filename}
${percona5_7_filename}
do_not_install
)

php_arr=(
${php5_6_filename}
${php7_0_filename}
${php7_1_filename}
${php7_2_filename}
do_not_install
)

phpmyadmin_arr=(
${phpmyadmin_filename}
do_not_install
)

php_modules_arr=(
${ionCube_filename}
${xcache_filename}
${php_imagemagick_filename}
${php_graphicsmagick_filename}
${php_memcached_filename}
${php_redis_filename}
${php_mongo_filename}
${php_libsodium_filename}
${swoole_filename}
${xdebug_filename}
do_not_install
)

}
