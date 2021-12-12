CREATE USER 'nexusbrain-user'@'%' IDENTIFIED BY 'dev';
GRANT ALL PRIVILEGES ON nexusbrain.* TO 'nexusbrain-user'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;