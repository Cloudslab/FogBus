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

#upgrade php
upgrade_php(){

    if [ ! -d ${php_location} ]; then
        log "Error" "PHP looks like not installed, please check it and try again."
        exit 1
    fi

    local tram=$( free -m | awk '/Mem/ {print $2}' )
    local swap=$( free -m | awk '/Swap/ {print $2}' )
    local ramsum=$( expr $tram + $swap )
    [ ${ramsum} -lt 600 ] && disable_fileinfo="--disable-fileinfo" || disable_fileinfo=""

    local phpConfig=${php_location}/bin/php-config
    local php_version=`get_php_version "${phpConfig}"`
    local php_extension_dir=`get_php_extension_dir "${phpConfig}"`
    local installed_php=`${php_location}/bin/php -r 'echo PHP_VERSION;' 2>/dev/null`

    if [ "${php_version}" == "5.6" ]; then
        latest_php=$(curl -s http://php.net/downloads.php | awk '/Changelog/{print $2}' | grep '5.6')
    elif [ "${php_version}" == "7.0" ]; then
        latest_php=$(curl -s http://php.net/downloads.php | awk '/Changelog/{print $2}' | grep '7.0')
    elif [ "${php_version}" == "7.1" ]; then
        latest_php=$(curl -s http://php.net/downloads.php | awk '/Changelog/{print $2}' | grep '7.1')
    elif [ "${php_version}" == "7.2" ]; then
        latest_php=$(curl -s http://php.net/downloads.php | awk '/Changelog/{print $2}' | grep '7.2')
    fi

    echo -e "Latest version of PHP: \033[41;37m ${latest_php} \033[0m"
    echo -e "Installed version of PHP: \033[41;37m ${installed_php} \033[0m"
    echo
    echo "Do you want to upgrade PHP ? (y/n)"
    read -p "(Default: n):" upgrade_php
    if [ -z ${upgrade_php} ]; then
        upgrade_php="n"
    fi
    echo "---------------------------"
    echo "You choose = ${upgrade_php}"
    echo "---------------------------"
    echo
    echo "Press any key to start...or Press Ctrl+C to cancel"
    char=`get_char`

    if [[ "${upgrade_php}" = "y" || "${upgrade_php}" = "Y" ]]; then

        log "Info" "PHP upgrade start..."
        if [[ -d ${php_location}.bak && -d ${php_location} ]]; then
            rm -rf ${php_location}.bak
        fi
        mv ${php_location} ${php_location}.bak

        if [ ! -d ${cur_dir}/software ]; then
            mkdir -p ${cur_dir}/software
        fi
        cd ${cur_dir}/software

        if [ ! -s php-${latest_php}.tar.gz ]; then
            latest_php_link="http://php.net/distributions/php-${latest_php}.tar.gz"
            backup_php_link="${download_root_url}/php-${latest_php}.tar.gz"
            untar ${latest_php_link} ${backup_php_link}
        else
            tar -zxf php-${latest_php}.tar.gz
            cd php-${latest_php}/
        fi

        if [ -d ${mariadb_location} ] || [ -d ${mysql_location} ] || [ -d ${percona_location} ]; then
            if [[ "${php_version}" == "7.0" || "${php_version}" == "7.1" || "${php_version}" == "7.2" ]]; then
                with_mysql="--enable-mysqlnd --with-mysqli=mysqlnd --with-mysql-sock=/tmp/mysql.sock --with-pdo-mysql=mysqlnd"
            else
                with_mysql="--enable-mysqlnd --with-mysql=mysqlnd --with-mysqli=mysqlnd --with-mysql-sock=/tmp/mysql.sock --with-pdo-mysql=mysqlnd"
            fi
        else
            with_mysql=""
        fi

        if [[ "${php_version}" == "7.0" || "${php_version}" == "7.1"  || "${php_version}" == "7.2" ]]; then
            with_gd="--with-gd --with-webp-dir --with-jpeg-dir --with-png-dir --with-xpm-dir --with-freetype-dir"
        elif [[ "${php_version}" == "5.6" ]]; then
            with_gd="--with-gd --with-vpx-dir --with-jpeg-dir --with-png-dir --with-xpm-dir --with-freetype-dir"
        fi

        if [[ "${php_version}" == "7.2" ]]; then
            other_options="--enable-zend-test"
        else
            other_options="--with-mcrypt --enable-gd-native-ttf"
        fi

        if check_sys packageManager yum; then
            with_imap="--with-imap=${depends_prefix}/imap"
        elif check_sys packageManager apt; then
            with_imap="--with-imap --with-kerberos"
        fi

        is_64bit && with_libdir="--with-libdir=lib64" || with_libdir=""

        php_configure_args="--prefix=${php_location} \
        --with-apxs2=${apache_location}/bin/apxs \
        --with-config-file-path=${php_location}/etc \
        --with-config-file-scan-dir=${php_location}/php.d \
        --with-pcre-dir=${depends_prefix}/pcre \
        ${with_imap} \
        --with-imap-ssl \
        --with-libxml-dir \
        --with-openssl \
        --with-snmp \
        ${with_libdir} \
        ${with_mysql} \
        ${with_gd} \
        --with-zlib \
        --with-bz2 \
        --with-curl=/usr \
        --with-gettext \
        --with-gmp \
        --with-mhash \
        --with-icu-dir=/usr \
        --with-ldap \
        --with-ldap-sasl \
        --with-libmbfl \
        --with-onig \
        --with-unixODBC \
        --with-pspell=/usr \
        --with-enchant=/usr \
        --with-readline \
        --with-tidy=/usr \
        --with-xmlrpc \
        --with-xsl \
        --without-pear \
        ${other_options} \
        --enable-bcmath \
        --enable-calendar \
        --enable-dba \
        --enable-exif \
        --enable-ftp \
        --enable-gd-jis-conv \
        --enable-intl \
        --enable-mbstring \
        --enable-pcntl \
        --enable-shmop \
        --enable-soap \
        --enable-sockets \
        --enable-wddx \
        --enable-zip \
        ${disable_fileinfo}"

        error_detect "./configure ${php_configure_args}"
        error_detect "parallel_make ZEND_EXTRA_LIBS='-liconv'"
        error_detect "make install"

        mkdir -p ${php_location}/{etc,php.d}
        cp -pf ${php_location}.bak/etc/php.ini ${php_location}/etc/php.ini
        cp -pn ${php_location}.bak/lib/php/extensions/no-debug-zts-*/* ${php_extension_dir}/
        if [ `ls ${php_location}.bak/php.d/ | wc -l` -gt 0 ]; then
            cp -pf ${php_location}.bak/php.d/* ${php_location}/php.d/
        fi
        log "Info" "Clear up start..."
        cd ${cur_dir}/software
        rm -rf php-${latest_php}/
        rm -f php-${latest_php}.tar.gz
        log "Info" "Clear up completed..."

        /etc/init.d/httpd restart

        log "Info" "PHP upgrade completed..."
    else
        echo
        log "Info" "PHP upgrade cancelled, nothing to do..."
        echo
    fi

}
