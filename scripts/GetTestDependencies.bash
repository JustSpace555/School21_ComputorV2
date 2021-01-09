#!/bin/bash
DEP='Dependencies'
JUNIT_FOLDER="$DEP/junit"

if ! [ -d $DEP ]
then
	mkdir $DEP
fi

if ! [ -d $JUNIT_FOLDER ]
then

	if [[ "$OSTYPE" == "linux-gnu"* ]];
	then
		wget --content-disposition https://search.maven.org/remotecontent?filepath=junit/junit/4.13/junit-4.13.jar
		wget --content-disposition https://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar

	elif [[ "$OSTYPE" == "darwin"* ]];
	then
		curl https://search.maven.org/remotecontent?filepath=junit/junit/4.13/junit-4.13.jar --output junit-4.13.jar
		curl https://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar --output hamcrest-core-1.3.jar

	else
		echo ERROR: "
			Unknown OS type. Please download appropriate with your OS junit library from https://search.maven.org/remotecontent?filepath=junit/junit/4.13/
			and hamcrest-core from https://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/
			Then place them into $JUNIT_FOLDER
		"
		exit 1
	fi

	mkdir $JUNIT_FOLDER
	mv *junit* $JUNIT_FOLDER/junit.jar
	mv *hamcrest-core* $JUNIT_FOLDER/hamcrest-core.jar
fi