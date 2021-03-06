#!/bin/bash
#####################################################################
# Script        : Data2Midi.sh (bash)                               #
# Release       : 2.2                                               #
# Last Update   : September 05, 2012                                #
# ================================================================  #
# Author        : Domenico Vicinanza (domenico.vicinanza@dante.org) #
# Organization  : DANTE                                             #
# Address       : City House, 126-130 Hills Rd                      #
#                 Cambridge - CB2 1PQ                               #
# Phone         : (+44) 1223 371300                                 #
# ================================================================  #
# Author        : Giuseppe La Rocca (giuseppe.larocca@ct.infn.it)   #
# Organization  : Physics Institute for Nuclear Research            #
#               : INFN - Univ. of Catania                           #
# Address       : Via S. Sofia, 64                                  #
#               : 95123 Catania (CT) - ITALY                        #
# Phone         : (+39) 095.378.55.19                               #
#####################################################################

INPUT_FILE=`basename $1`
MIDDLEWARE=$2
EnableMIDI=$3
TIME=$4

export CLASSPATH=.:*

echo;echo "- Running on "`hostname -f`

if [ "X${MIDDLEWARE}" = "Xglite" ] ; then
	export VO_NAME=$(voms-proxy-info -vo)
	export VO_VARNAME=$(echo $VO_NAME | sed s/"\."/"_"/g | sed s/"-"/"_"/g | awk '{ print toupper($1) }')
	export VO_SWPATH_NAME="VO_"$VO_VARNAME"_SW_DIR"
	export VO_SWPATH_CONTENT=$(echo $VO_SWPATH_NAME | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
	#export CLASSPATH=.:jmusic.jar
	export INPUT_FILE=`basename $1`

	echo;echo "- The selected middleware is "$2
        echo;echo "[ Settings for the gLite infrastructure ]"
	echo "VO_NAME           : "$VO_NAME
	echo "VO_VARNAME        : "$VO_VARNAME
	echo "VO_SWPATH_NAME    : "$VO_SWPATH_NAME
	echo "VO_SWPATH_CONTENT : "$VO_SWPATH_CONTENT
	echo "CLASSPATH         : "$CLASSPATH
	echo "INPUT_FILE        : "$1
	echo

	echo;echo "Checking ${VO_SWPATH_CONTENT}"
        tree -L 1 ${VO_SWPATH_CONTENT}

	# Sourcing env. variabiles (only for IN2P3 sites)
	./usr/local/shared/bin/java6_env.sh

	echo;echo "- Starting compilation at ";date
	if [ ! -z $JAVA_HOME ] ; then
		echo $JAVA_HOME/bin/javac TunguScore.java
		$JAVA_HOME/bin/javac TunguScore.java
	else
		echo /usr/java/latest/bin/javac TunguScore.java
		/usr/java/latest/bin/javac TunguScore.java
	fi
fi

if [ "X${MIDDLEWARE}" = "Xwsgram" ] ; then
	echo;echo "- The selected middleware is "$2
	JAVA_HOME=/usr/local/GARUDA/jdk1.6.0_03
	#export CLASSPATH=.:jmusic.jar
	echo;echo "- Starting compilation at ";date
	echo $JAVA_HOME/bin/javac TunguScore.java
	$JAVA_HOME/bin/javac TunguScore.java
fi

# Renaming the input file
cp $INPUT_FILE tungu_tot.dat

echo;echo "- Starting the sonification at ";date
if [ ! -z $JAVA_HOME ] ; then
	echo $JAVA_HOME/bin/java TunguScore -f $INPUT_FILE -t $TIME
	$JAVA_HOME/bin/java TunguScore -f $INPUT_FILE -t $TIME
else
	echo /usr/java/latest/bin/java TunguScore -f $INPUT_FILE -t $TIME
	/usr/java/latest/bin/java TunguScore -f $INPUT_FILE -t $TIME
fi

# Renaming the MIDI file
cp TunguScore.mid $INPUT_FILE.mid

if [ $? -eq 0 ] ; then
        echo -n "- Finish at ";date
        echo; echo "- Checking for results..."
        ls -al TunguScore.mid
        if [ $? -eq 0 ] ; then
                echo "MIDI file successfully created!"
        else
                echo "Problems occured during the creation of the MIDI file, please check log files"
        fi
fi

# Start analysis of the midi file with the MidiToolBox framework
if [[ "X${MIDDLEWARE}" = "Xglite" && "X${EnableMIDI}" = "XEnableMIDI" ]] ; then
        echo;echo "- Installing MCRInstaller on the working directory..."
        echo "- This operation may take few minutes, please wait..."
        
        rm -f activate.ini 2>/dev/null
        rm -f installer_input.txt 2>/dev/null
        rm -f install_guide.pdf 2>/dev/null
        rm -f readme.txt 2>/dev/null
        rm -f install 2>/dev/null
        rm -rf archives 2>/dev/null
        rm -rf bin 2>/dev/null
        rm -rf help 2>/dev/null
        rm -rf java 2>/dev/null
        rm -rf sys 2>/dev/null
        rm -rf v716 2>/dev/null

        cp -r ${VO_SWPATH_CONTENT}/MCR_MatlabR2011b/* $PWD

        chmod +x install
        chmod +x run_midi.sh
        chmod +x midi

        # Renaming the MIDI file
        cp $INPUT_FILE.mid MIDI.mid

        ./install -mode silent -destinationFolder ${PWD}
        echo;echo "- Starting MidiToolBox..."
        ./run_midi.sh v716
        
        ls -al figure.zip
        if [ $? -eq 0 ] ; then
                echo;echo "MIDI analysis completed!"
        else
                echo;echo "Problems occured during the MIDI analysis. Please check log files"
        fi
fi

cat <<EOF >> output.README
#
# README - Sonification
# Sonification Algorithm - DataSet2Midi
# 
# Giuseppe LA ROCCA, INFN Catania
# <mailto:giuseppe.larocca@ct.infn.it>
#

This application allows Science Gateway users to implements the sonification of data 
by means of sound signals. 
Generally speaking any sonification procedure is a mathematical mapping from a certain 
data set (numbers, strings, images, ...) to a sound string.

If the job has been successfully executed, the following files will be produced:
~ Text2Midi.out:        the standard output file;
~ Text2Midi.err:        the standard error file;
~ TunguScore.mid:	the audio file produced during the sonification process;

- About the MIDI Toolbox
The MIDI Toolbox is a compilation of functions for analyzing and visualizing MIDI files in the Matlab computing environment. 
Besides simple manipulation and filtering functions, the toolbox contains cognitively inspired analytic techniques that are 
suitable for context-dependent musical analysis that deal with such topics as melodic contour, similarity, key-finding, 
meter-finding and segmentation.

- Citation
Eerola, T. & Toiviainen, P. (2004) 
MIDI Toolbox: MATLAB Tools for Music Research 
University of Jyväskylä: Kopijyvä, Jyväskylä, Finland 
Available at http://www.jyu.fi/hum/laitokset/musiikki/en/research/coe/materials/miditoolbox/
EOF

