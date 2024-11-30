#!/bin/bash


ps -ef | grep server.php | grep -v grep | awk '{print $2}' | xargs kill
nohup php server.php > nohup.log &

now=$(date)
echo "script restarted at: $now" >> meysam.txt
