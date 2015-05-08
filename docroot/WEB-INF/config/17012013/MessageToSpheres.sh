#!/bin/sh
#####################################################################
# Script        : sonification.sh (bash)                            #
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

tar zxf $PWD/inputs.tar.gz

echo;echo "- Running on "`hostname -f`

if [ "X${MIDDLEWARE}" = "Xglite" ] ; then
	export VO_NAME=$(voms-proxy-info -vo)
	export VO_VARNAME=$(echo $VO_NAME | sed s/"\."/"_"/g | sed s/"-"/"_"/g | awk '{ print toupper($1) }')
	export VO_SWPATH_NAME="VO_"$VO_VARNAME"_SW_DIR"
	export VO_SWPATH_CONTENT=$(echo $VO_SWPATH_NAME | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')
	export CLASSPATH=.:jmusic.jar

	echo;echo "- The selected middleware is "$2
        echo;echo "[ Settings for the gLite infrastructure ]"
        echo "VO_NAME           : "$VO_NAME
        echo "VO_VARNAME        : "$VO_VARNAME
        echo "VO_SWPATH_NAME    : "$VO_SWPATH_NAME
        echo "VO_SWPATH_CONTENT : "$VO_SWPATH_CONTENT
        echo "CLASSPATH         : "$CLASSPATH
        echo "INPUT_FILE        : "$INPUT_FILE

	# Sourcing env. variabiles (only for IN2P3 sites)
	./usr/local/shared/bin/java6_env.sh

	echo;echo "- Checking for POV-Ray software..."
        echo tree -L 3 $VO_SWPATH_CONTENT/POV-Ray-3.6
        tree -L 3 $VO_SWPATH_CONTENT/POv-Ray-3.6

	# Configuring the povray.ini file according to the selected grid Infrastructure
	echo; echo "- Configuring the povray.ini file for the current infrastructure.."
	_r1="${VO_SWPATH_CONTENT//\//\\/}"
	sed -e "s/PATH_TO_CHANGE/$_r1/g" ./povray-template.ini > povray.ini
fi

if [ "X${MIDDLEWARE}" = "Xwsgram" ] ; then
	echo;echo "- The selected middleware is "$2
	POVRAY_INSTALL_DIR="/usr/local/GARUDA/apps/povray-3.6"
	JAVA_HOME=/usr/local/GARUDA/jdk1.6.0_03
	export CLASSPATH=.:jmusic.jar
        echo;echo "[ Settings for the GARUDA infrastructure ]"
        echo "POVRAY_INSTALL_DIR           : "$POVRAY_INSTALL_DIR
	
	echo;echo "- Checking for POV-Ray software..."
        echo tree -L 3 $POVRAY_INSTALL_DIR
        tree -L 3 $POVRAY_INSTALL_DIR
	
	# Configuring the povray.ini file according to the selected grid Infrastructure
	echo; echo "- Configuring the povray.ini file for the current infrastructure.."
	_r1="${POVRAY_INSTALL_DIR//\//\\/}"
	sed -e "s/PATH_TO_CHANGE/$_r1/g" ./povray-template-garuda.ini > povray.ini
fi

echo;echo "- Starting compilation at ";date
if [ ! -z $JAVA_HOME ] ; then
	$JAVA_HOME/bin/javac MessageToSpheres.java
else
	javac MessageToSpheres.java
fi

echo "- Message to parse is the following :"
cat $INPUT_FILE

#
# Start 3D-images rendering with POV-RAY-3.6
#
# Creation of the MessageToSpheresX.png files
echo;echo "- Creation of MessageToSpheres*.png"
if [ "X${MIDDLEWARE}" = "Xglite" ] ; then
	${VO_SWPATH_CONTENT}/POV-Ray-3.6/bin/povray ./MessageToSpheres1.ini
	${VO_SWPATH_CONTENT}/POV-Ray-3.6/bin/povray ./MessageToSpheres2.ini
	${VO_SWPATH_CONTENT}/POV-Ray-3.6/bin/povray ./MessageToSpheres3.ini
	${VO_SWPATH_CONTENT}/POV-Ray-3.6/bin/povray ./MessageToSpheres4.ini
	${VO_SWPATH_CONTENT}/POV-Ray-3.6/bin/povray ./MessageToSpheres5.ini
fi

if [ "X${MIDDLEWARE}" = "Xwsgram" ] ; then
	${POVRAY_INSTALL_DIR}/bin/povray ./MessageToSpheres1.ini
	${POVRAY_INSTALL_DIR}/bin/povray ./MessageToSpheres2.ini
	${POVRAY_INSTALL_DIR}/bin/povray ./MessageToSpheres3.ini
	${POVRAY_INSTALL_DIR}/bin/povray ./MessageToSpheres4.ini
	${POVRAY_INSTALL_DIR}/bin/povray ./MessageToSpheres5.ini
fi

echo -n "..Finish at ";date
echo; echo "- Checking for results..."

ls -al MessageToSpheres*.png
        if [ $? -eq 0 ] ; then
                echo "- PNG file successfully created!"
        else
                echo "- Problems occured during the creation of the PNG files, please check log files"
        fi

cat <<EOF >> output.README
#
# README - Sonification
# Sonification Algorithm - ASCIItoSphere
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
~ *.png:    		a three-dimensional graphics produced with POVRay (www.povray.org).
EOF
