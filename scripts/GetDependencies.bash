#!/bin/bash

KOTLIN='kotlinc'
JAVA='java'
DEP='Dependencies'

if ! [ -d $DEP ]
then
	mkdir $DEP
fi

if ! [ -d $DEP/$KOTLIN ]
then
	echo "Downloading kotlin compiler...
	"

	if [[ "$OSTYPE" == "linux-gnu"* ]];
	then
		wget https://github.com/JetBrains/kotlin/releases/download/v1.4.10/kotlin-compiler-1.4.10.zip

	elif [[ "$OSTYPE" == "darwin"* ]];
	then
		curl -L https://github.com/JetBrains/kotlin/releases/download/v1.4.10/kotlin-compiler-1.4.10.zip --output kotlin-compiler-1.4.10.zip
	else
		echo ERROR: "
			Unknown OS type. Please download appropriate with your OS kotlinc program from https://github.com/JetBrains/kotlin/releases/tag/v1.4.10.
			Then unpack it, place into $DEP folder and rename folder to $KOTLIN
		"
		exit 1
	fi

	echo ""
	echo ""
	echo "Unarchiving file..."

	unzip kotlin-compiler-1.4.10.zip
	mv $KOTLIN $DEP/$KOTLIN
	rm kotlin-compiler-1.4.10.zip
fi