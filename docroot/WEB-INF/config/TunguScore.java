/*
 * *************************************************************************
 *  Copyright (c) 2000-2013:
 *  DANTE (Delivery of Advanced Network Technology to Europe), Cambridge, UK
 *  Istituto Nazionale di Fisica Nucleare (INFN), Italy
 *  Conservatory of Music of Avellino, Italy
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  @author <a href="mailto:domenico.vicinanza@dante.net">Domenico Vicinanza</a>
 *  @author <a href="mailto:mariapaola.sorrentino@gmail.com">Mariapaola Sorrentino</a>
 *  @author <a href="mailto:giuseppe.larocca@ct.infn.it">Giuseppe La Rocca</a>
 *   ***************************************************************************
 *    
 */

import jm.JMC;
import jm.music.data.*;
import jm.music.tools.*;
import jm.util.*;

import java.util.Properties;
import java.io.*;

import jargs.gnu.CmdLineParser;
import org.apache.log4j.Logger;

//MIDI Controller features
import javax.sound.midi.*;
import javax.swing.*;

public class TunguScore implements JMC {

   private static Logger log = Logger.getLogger(TunguScore.class);

   private static Boolean verbose = false;
   private static String FILENAME = "";
   private static String TEMPO = "";

   int getMaxNotes (String filename)
   {
        int max_number_of_notes = 0;
        String aLine = "";

	try {
	        FileInputStream fin =  new FileInputStream(filename);
        	BufferedReader myInput = new BufferedReader (new InputStreamReader(fin));  

	        while ((aLine = myInput.readLine()) != null)  max_number_of_notes++;
	} catch( Exception e ) { e.printStackTrace(); }//end catch

        return max_number_of_notes;
   }
        
   public TunguScore(Properties properties) 
   {
   	String aLine = "";
	String bLine = "";
	double value=0;
	double value0=0;
	double max=value;
	double min=value;
	double slope=0;
	int n_of_notes=0;

	//////////////////////////////////
	// Reading ASCII text from file //
	//////////////////////////////////	
        String filename = properties.getProperty("FILENAME");

	//int max_number_of_notes=400;
	int max_number_of_notes = getMaxNotes(filename);
	double nextvalue=value;

	try { 
        	//calculate scale factor and normalization          
	        FileInputStream fin =  new FileInputStream(filename);
        	BufferedReader myInput = new BufferedReader (new InputStreamReader(fin));     
	        aLine=aLine = myInput.readLine();          
        	try {
	             value = Double.valueOf(aLine.trim()).doubleValue();
	        } catch (NumberFormatException nfe) {
	              log.info("NumberFormatException: " + nfe.getMessage());
            	}
          
	        max=value;
         	min=value;

	        while (((aLine = myInput.readLine()) != null)  && (n_of_notes<max_number_of_notes))
		{
	            // Convert aLine into a double value
        	    try {
		             value = Double.valueOf(aLine.trim()).doubleValue();
	            } catch (NumberFormatException nfe) {
	              log.info("NumberFormatException: " + nfe.getMessage());
            	    }

            	    if (value>max) max=value;
            	    else if (value<min) min=value;
            
            	    n_of_notes++;
          	} //end while
    	} catch( Exception e ) { e.printStackTrace(); }//end catch

        log.info("Max value = " + max);
        log.info("Min value = " + min);

        int[] pitches = new int[n_of_notes+1];
        Note[] notes = new Note[n_of_notes+1];

   	try { 
	  	//calculate scale factor and normalization
	        int cnt=0;
        	FileInputStream fin =  new FileInputStream(filename);
	        BufferedReader myInput = new BufferedReader (new InputStreamReader(fin));

	        aLine=aLine = myInput.readLine();
	        try {
        	     value = Double.valueOf(aLine.trim()).doubleValue();
        	} catch (NumberFormatException nfe) {
	              log.info("NumberFormatException: " + nfe.getMessage());
            	}

          	while (((aLine = myInput.readLine()) != null) && (cnt<max_number_of_notes))
		{
	            // Convert aLine into a double value
        	    try {
		             value = Double.valueOf(aLine.trim()).doubleValue();
		             log.info("value = " + value);
	            } catch (NumberFormatException nfe) {
		      	log.info("NumberFormatException: " + nfe.getMessage());
	            }
            
            	    int midiPitch=(int)((value-(max+min)/2)*(65-0)/(max-min))+65;
            	    log.info("midiPitch = "+midiPitch);
            
		    if (midiPitch<0) midiPitch=0;
	            if (midiPitch>127) midiPitch=127;
        	    pitches[cnt]=midiPitch;

	            cnt++;
          	} //end while
      	} catch( Exception e ) { e.printStackTrace(); } //end catch

        log.info("Creating MIDI...");
        log.info("Note processed:" + max_number_of_notes);

        for (int i = 0; i < notes.length; i++)
             notes[i] = new Note(pitches[i], DEMI_SEMI_QUAVER * 2);

         // A phrase is made up of notes
         Phrase phrase = new Phrase(notes);
         Part pianoPart = new Part("Piano", PIANO);
         //Part pianoPart = new Part("Flute", FLUTE);
         // A part is made up of phrases 
         pianoPart.add(phrase);
         // A score is made up of part(s)
         //int tempo = 120;
         int tempo = Integer.parseInt(properties.getProperty("TEMPO"));
         Score TunguScore = new Score("Score from Tungurauha seismograph.data", tempo, pianoPart);
         // In key of C: 0 flat
         TunguScore.setKeySignature(0);
         // In 4/4 time
         TunguScore.setNumerator(4);
         TunguScore.setDenominator(4);
         // Display score in standard musical notation
         ////View.notate(TunguScore);
         // Write out score to MIDI file
         Write.midi(TunguScore, "TunguScore.mid");
         
/**         
    //MIDI OUT (Using Plumstone)
    try {
      // See all the midi devices on the system
      MidiDevice.Info[] info = MidiSystem.getMidiDeviceInfo();

      // Display all the available devices
      for (int i=0; i<info.length; i++) {
        System.out.println ("Device Info [" + i + "]=" + info [i]);
      } // for
      
      MidiDevice.Info outputDeviceInfo = 
        (MidiDevice.Info) JOptionPane.showInputDialog (null,
                                                       "Select OUTPUT MIDI Device",
                                                       "PlumStone Deployment Tester",
                                                       JOptionPane.QUESTION_MESSAGE,
                                                        null,
                                                       info,
                                                       null);
        
      // Test output to some midi device
      System.out.println ("Testing Midi Output...");
      MidiDevice outputPort = MidiSystem.getMidiDevice (outputDeviceInfo);
      outputPort.open ();
      Receiver r = outputPort.getReceiver ();
      System.out.println ("Output Port = " + outputPort);
      System.out.println ("Receiver = " + r);

      // Create short message structure
      ShortMessage note = new ShortMessage ();
      Thread.sleep (1000L);

      // Play the notes of the scale
      // i should start from 0, here it has been set "int i=1" to correctly calculate
      // pitches[i]-pitches[i-1] in the Thread.sleep () function call
      for (int i=0; i<pitches.length; i++) {
        note.setMessage (ShortMessage.NOTE_ON, 1, pitches [i], 127);
        r.send (note, -1L);
        Thread.sleep (75L);
        //Thread.sleep (50*(int)Math.abs(pitches[i]-pitches[i-1]));
        note.setMessage (ShortMessage.NOTE_OFF, 1, pitches [i], 127);
        r.send (note, -1L);
        Thread.sleep (50L);
      } // for
    }
    catch (Exception e) {
      // Oops! Should never get here
      System.out.println ("Exception in PlumStone Deployment Tester main ()");
      System.out.println (e);
      System.exit (0);
    }
 */         
   }

   private static void printUsage() {

        System.err.println(
        "Usage: TunguScore [options]\n" +
        "\nAvailable [options] are:\n" +
        "[ -v,--verbose ]\n" +
        "[ {-f,--filename} the filename with the ASCII file to be processed ]\n" +
        "[ {-t,--time} the time (default value 120)]\n"
        );
  }

   public static void main(String[] args) 
   {
	CmdLineParser parser                = new CmdLineParser();
	CmdLineParser.Option o_verbose      = parser.addBooleanOption ('v', "verbose");
	CmdLineParser.Option o_filename     = parser.addStringOption ('f', "filename");
        CmdLineParser.Option o_tempo 	    = parser.addIntegerOption  ('t', "tempo");

	try {
		if ((args == null) || (args.length < 3) || (args.length > 6)) {
	        	printUsage();
			System.exit(2);
		} else parser.parse(args);
	} catch ( CmdLineParser.OptionException e ) {
        	//log.error(e.getMessage());
	        printUsage();
	        System.exit(2);
	} catch (Exception exc){ System.out.println (exc.toString()); }

	verbose = (Boolean) parser.getOptionValue(o_verbose, Boolean.FALSE);
        if (verbose) log.setLevel(org.apache.log4j.Level.INFO);
	log.info("Verbose level \t = " + verbose);

	FILENAME = (String) parser.getOptionValue(o_filename);
        if(FILENAME != null && FILENAME.length() > 0)
		log.info("ASCII File \t\t = " + FILENAME);
        else FILENAME="tungu_tot.dat";

	TEMPO = (String) parser.getOptionValue(o_tempo).toString();
        if(TEMPO != null && TEMPO.length() > 0)
		log.info("TEMPO \t\t = " + TEMPO);

	Properties properties = new Properties();
        properties.setProperty("FILENAME", FILENAME);
        properties.setProperty("TEMPO", TEMPO);
        new TunguScore(properties);
   }
}
