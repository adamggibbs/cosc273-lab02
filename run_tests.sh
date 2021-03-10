#!/bin/bash
echo 'Compiling...'
javac *.java
echo 'Done compiling.'

for RUN in 6 7 8 9 
do
	echo "Starting run $RUN"
	java ShortcutTester >> "./logs/log$RUN.txt"
	echo "Finished running run $RUN"
done

