#!/bin/bash
export LFC_CATALOG_TYPE=lfc
export LFC_HOST=lfcserver.cnaf.infn.it
export LCG_GFAL_INFOSYS=egee-bdii.cnaf.infn.it:2170
export VO_NAME=gridit

echo "- Running MCRInstaller on "`hostname -f`;echo
echo "- Installing MCRInstaller in the working directory...This operation may take few minutes, please wait..."

cp -r /opt/exp_soft/${VO_NAME}/MCR_MatlabR2011b/* $PWD >VIDEO.txt 2>VIDEO.txt

chmod +x install
chmod +x run_midi.sh
chmod +x midi

# Renaming the MIDI file
mv *.mid MIDI.mid

./install -mode silent -destinationFolder ${PWD}>>VIDEO.txt
echo "- Starting MidiToolBox..."
./run_midi.sh v716>>VIDEO.txt

cat <<EOF >> COPYRIGHT.txt
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

