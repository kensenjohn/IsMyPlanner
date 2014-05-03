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
if unzip $war_file_work_area/$zip_file_name -d  $war_file_work_area
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
