kill -9  `ps -ef | grep java | grep efamily-portal | awk -F" " '{print $2}'`
 localPath=/opt/efamily/efamily-portal/webapps/ROOT
 serverPath=/opt/efamily/efamily-portal/webapps/ROOT
 cd "$serverPath"/
 mv "$serverPath"/publish/lanhai "$serverPath"/publish/lanhai_`date +"%Y%m%d%H"`
 cp -rf "$serverPath"/lanhai/lanhai/ "$serverPath"/publish/
 cp -fr "$serverPath"/publish/lanhai/* "$serverPath"/
 cd ../../bin
 ./startup.sh
