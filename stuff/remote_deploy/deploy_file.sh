#!/bin/sh
#
. /home/kensen/War_folder/app.properties

echo "#################################################################"
echo "##########  Start Deployment $(date) ###########"
echo "#################################################################"
echo "War File - Name : $war_file_name"
echo "War File - Location: $war_file_location" 
echo "Reading Property file complete"


error_exit()
{
        echo "$1" 1>&2
        exit 1

}

### Cleanup War Work Are
rm -rf $war_file_work_area/*
ls -ltr $war_file_work_area
echo "Clean up of War Work Area - Complete!!"

### Copying War file to workspace
if cp $war_file_location/$war_file_name $war_file_work_area
then

        ls -ltr $war_file_work_area
        echo "copy war file to workspace - Complete!!"
else
        error_exit "Cannot copy War file $war_file_location/$war_file_name to $war_file_work_area  Aborting."
fi

### Rename War file to Zip
mv $war_file_work_area/$war_file_name  $war_file_work_area/$zip_file_name
ls -ltr $war_file_work_area
echo "Rename of War file to $zip_file_name - Complete!!"

### Unzip the File
if unzip -q $war_file_work_area/$zip_file_name -d  $war_file_work_area
then
        echo "Unzip file $war_file_work_area/$zip_file_name - Complete!!"
else
        error_exit "Failed to unzip $war_file_work_area/$zip_file_name"
fi

### Creating Backup of Zip File
mv $war_file_work_area/$zip_file_name ./$zip_file_name"_bkp"
echo "Backup of $zip_file_name - Complete!!"


### Delete lib jar (3rd party Jars)
rm -rf $war_file_work_area/WEB-INF/lib/*

### Creating zip file
cd $war_file_work_area/
zip -rq $zip_file_name *
cd ..
echo "Creating new zip file - Complete!!"

###  Rename zip file to war
mv $war_file_work_area/$zip_file_name $war_file_work_area/$war_file_name
echo "Rename zip file to $war_file_name - Complete!!"

### COPY WAR file to Amazon AWS
sudo scp -i $aws_pem_file_location/$aws_pem_file $war_file_work_area/$war_file_name  $aws_name@$aws_ip_address:$aws_tmp_location
echo "COPY WAR to Amazon - Complete!!"

### Execute deployment script in AWS
sudo ssh -i $aws_pem_file_location/$aws_pem_file $aws_name@$aws_ip_address 'bash -s' < aws_deploy.sh
echo "Execute deployment script in AWS - Complete!!"

