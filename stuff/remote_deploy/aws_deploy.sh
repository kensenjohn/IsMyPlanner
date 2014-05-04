#!/bin/sh
#

if cp /tmp/ROOT.war /tmp/ROOT1.war
then

        cp /tmp/ROOT1.war /tmp/ROOT.war

        service tomcat stop
        echo "Stoppping Tomcat - Complete!!"

        sudo rm -rf /usr/local/tomcat/webapps/*
        echo "Cleaned up old contents of tomcat"

        if sudo mv /tmp/ROOT.war /usr/local/tomcat/webapps/
        then
                echo "Copy ROOT.war to tomcat webapps - Complete!!"
        else
                echo "ERRO ERROR ERROR Root.war could not be moved to webapps"
                exit 1
        fi

        sudo rm /tmp/ROOT1.war

        service tomcat start
        echo "Started tomcat again"

else
        echo "ROOT.war does not exist"
        exit 1
fi
