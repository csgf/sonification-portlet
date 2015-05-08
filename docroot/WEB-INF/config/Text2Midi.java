/*
 *  *************************************************************************
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

import java.io.*;
import java.util.Properties;
import java.util.Random;
import java.lang.Math;

//MIDI Controller features
import javax.sound.midi.*;
import javax.swing.*;

import jargs.gnu.CmdLineParser;
import org.apache.log4j.Logger;

public class Text2Midi implements JMC {

  private static Logger log = Logger.getLogger(Text2Midi.class);

  private static Boolean verbose = false;
  private static String FILENAME = "";
  private static String MUSICALSCALE = "";
  private static String SPEED = "";
  private static String RANGE_MIN = "";
  private static String RANGE_MAX = "";
        
  public Text2Midi(Properties properties) 
  {
	   String aLine = "";
	   String bLine = "";
	   double value=0;
	   //double value0=0;
	   //double max=value;
	   //double min=value;
	   //double slope=0;
	   int intvalue=(int)value;
	   int n_of_notes=0;
	   //double nextvalue=value;
	   String tot_message="";
	   String language_selector="";
   
   	   //////////////////////////////////
	   // Reading ASCII text from file //
   	   //////////////////////////////////

	   String filename = properties.getProperty("FILENAME");
   	   try { 
		//calculate scale factor and normalization
	       int cnt=0;
	       FileInputStream fin =  new FileInputStream(filename);
	       BufferedReader myInput = new BufferedReader (new InputStreamReader(fin));     
	       while ((aLine = myInput.readLine()) != null)
	          tot_message=tot_message+removeSpaces(aLine);       
	    } catch( Exception e ) { e.printStackTrace(); }

	   int max_number_of_notes=tot_message.length(); 

	   log.info("\n\n...[ Database acquired ]...");
	   log.info("Message = " + tot_message);
           log.info("String length = " + tot_message.length() + "\n");
   
           //////////////////////////////////
           // MUSICAL SETTINGS
           // SET HERE THE MUSICAL SCALE 
           // 0 = dodecafonic
           // 1 = diatonic
           // 2 = blues
           // 3 = pentatonic
           // 4 = whole tones
           //////////////////////////////////
           //int musicalscale = 1;
           int musicalscale = Integer.parseInt(properties.getProperty("MUSICALSCALE"));
	   if (musicalscale==0) log.info ("Selected the following Musical Scale => [ DODECAFONIC ] ");
	   if (musicalscale==1) log.info ("Selected the following Musical Scale => [ DIATONIC ] ");
	   if (musicalscale==2) log.info ("Selected the following Musical Scale => [ BLUES ] ");
	   if (musicalscale==3) log.info ("Selected the following Musical Scale => [ PENTATONIC ] ");
	   if (musicalscale==4) log.info ("Selected the following Musical Scale => [ WHOLE TONES ] ");

           //////////////////////////////////
           // SET HERE THE SPEED
           // 1 = fast
           // 2 = slow
           //int speed = 2;
           int speed = Integer.parseInt(properties.getProperty("SPEED"));
	   if (speed==1) log.info ("Selected the following Speed => [ FAST ] ");
	   else log.info ("Selected the following Speed => [ SLOW ] ");
           /////////////////////////////////

           //min=1;
           int min = Integer.parseInt(properties.getProperty("RANGE_MIN"));
           //max=255;
           int max = Integer.parseInt(properties.getProperty("RANGE_MAX"));
          
           n_of_notes=tot_message.length(); 
           int[] pitches = new int[n_of_notes];
           Note[] notes = new Note[n_of_notes];
           int[] values = new int[n_of_notes];

           //convert the message lowecase
           //tot_message=tot_message.toLowerCase();

   	   try { 
		//calculate scale factor and normalization
	        int cnt=0;
        	int sw=-1;
          
	        while (cnt<tot_message.length()) 
		{ 
	            char letter=tot_message.charAt(cnt);
             	    value=(int)letter;
	            value=(int)value*2;
        	    values[cnt]=(int)letter;
	            log.info("char="+letter);
        	    int midiPitch=0;
	            int randInt;

         	    intvalue=(int)letter;
	            if (musicalscale==0) {
            	    // DODECAFONIC //
	            midiPitch=(int)((value-(max+min)/2)*(65-0)/(max-min))+65;
           	    }
	 
                    else if (musicalscale==1) {
	            // DIATONIC //
        	    switch (intvalue % 7) {
	                 case 0: midiPitch=(int) 60; 
                         break;
        	         case 1: midiPitch=(int) 62;
                         break;
	                 case 2: midiPitch=(int) 64;
                         break;
        	         case 3: midiPitch=(int) 65;
                         break;
	                 case 4: midiPitch=(int) 67;
                         break;
        	         case 5: midiPitch=(int) 69;
                         break;
                	 case 6: midiPitch=(int) 71;
                         break;
	                 default: midiPitch=(int) 0;
        	    }

	            log.info("midiPitch: " + midiPitch);
	            //randInt = (int) (Math.random() * 2) + 1; 
        	    //System.out.println("random: "+randInt);
	            //midiPitch=(int)(midiPitch+(int)12*(randInt-2));
           	    } 
	
		    else if (musicalscale==2) {
	            // BLUES //
               	    switch (intvalue % 6) {
	                 case 0: midiPitch=(int) 60; 
                         break;
        	         case 1: midiPitch=(int) 63;
                         break;
                	 case 2: midiPitch=(int) 65;
                         break;
	                 case 3: midiPitch=(int) 66;
                         break;
        	         case 4: midiPitch=(int) 67;
                         break;
                	 case 5: midiPitch=(int) 70;
                         break;
	                 default: midiPitch=(int) 0;
        	    }

	            log.info("midiPitch: " + midiPitch);
        	    randInt = (int) (Math.random() * 2) + 1; 
	            //System.out.println("random: "+randInt);
        	    midiPitch=(int)(midiPitch+(int)12*(randInt-2));

	            //sw=sw*(-1);
         	   //midiPitch=(int)(midiPitch+12*sw);
           	   } 

		   else if (musicalscale==3) {
	           // PENTATONIC //
               	   switch (intvalue % 5) {
	                 case 0: midiPitch=(int) 61; 
                         break;
        	         case 1: midiPitch=(int) 63;
                         break;
                	 case 2: midiPitch=(int) 66;
                         break;
	                 case 3: midiPitch=(int) 68;
                         break;
        	         case 4: midiPitch=(int) 78;
                         break;
                	 default: midiPitch=(int) 0;
               	   }

               	   log.info("midiPitch: " + midiPitch);
                   randInt = (int) (Math.random() * 2) + 1; 
                   //System.out.println("random: "+randInt);
                   midiPitch=(int)(midiPitch+(int)12*(randInt-2));
           	   } 

		   else if (musicalscale==4) {
	           // WHOLE TONE //
               	   switch (intvalue % 6) {
	                 case 0: midiPitch=(int) 60; 
                         break;
        	         case 1: midiPitch=(int) 62;
                         break;
                	 case 2: midiPitch=(int) 64;
                         break;
	                 case 3: midiPitch=(int) 66;
                         break;
        	         case 4: midiPitch=(int) 68;
                         break;
                	 case 5: midiPitch=(int) 70;
                         break;
	                 default: midiPitch=(int) 0;
               	   }

        	   log.info("midiPitch: " + midiPitch);
	           randInt = (int) (Math.random() * 2) + 1; 
	           //System.out.println("random: "+randInt);
	           midiPitch=(int)(midiPitch+(int)12*(randInt-2));
           	   } 
            
            	   log.info("midiPitch = " + midiPitch);
	           if (midiPitch<0) midiPitch=0;
        	   if (midiPitch>127) midiPitch=0;
	           pitches[cnt]=midiPitch;

        	   cnt++;
          } //end while
      } catch( Exception e ) { e.printStackTrace(); } //end catch

      ////////////////////////
      // Creating MIDI file //
      ////////////////////////

      log.info("\n\n...[ Creating MIDI file ]...");
      log.info("Note processed:" + n_of_notes);
          for (int i = 0; i < notes.length; i++) {
           // notes[i] = new Note(pitches[i], DEMI_SEMI_QUAVER * 4 * speed);
           if ((values[i]==65)  ||  (values[i]==69)  || (values[i]==73)  || (values[i]==79)  || 
               (values[i]==85)  ||  (values[i]==89)  || (values[i]==97)  || (values[i]==101) || 
               (values[i]==105) ||  (values[i]==111) || (values[i]==117) || (values[i]==121))               
                notes[i] = new Note(pitches[i], DEMI_SEMI_QUAVER * 4 * speed);
           else 
		notes[i] = new Note(pitches[i], DEMI_SEMI_QUAVER * 2 * speed);
          }

         // A phrase is made up of notes
         Phrase phrase = new Phrase(notes);
         Part pianoPart = new Part("Piano", PIANO);
         //Part pianoPart = new Part("Flute", FLUTE);
         // A part is made up of phrases 
         pianoPart.add(phrase);
         // A score is made up of part(s)
         int tempo = 120;
         Score Text2Midi = new Score("Score from wyp2005 messages sonification. (c) D. Vicinanza", tempo, pianoPart);
         // In key of C: 0 flat
         Text2Midi.setKeySignature(0);
         // In 4/4 time
         Text2Midi.setNumerator(4);
         Text2Midi.setDenominator(4);
         // Display score in standard musical notation
         //View.notate(Text2Midi);
         // Write out score to MIDI file
         Write.midi(Text2Midi, "messages.mid");
  }
       
  static String removeSpaces(String withSpaces) {
        
	java.util.StringTokenizer t = new java.util.StringTokenizer(withSpaces, " ");
        StringBuffer result = new StringBuffer("");
        
	while (t.hasMoreTokens()) 
            result.append(t.nextToken());
        
	return result.toString();
  }   
    
  int dodecafonic(double value, double max, double min) {

       int midiPitch;
       midiPitch=(int)((value-(max+min)/2)*(65-0)/(max-min))+65;

       return midiPitch;
  }    
    
  private byte[] ConvertStringToByte(String str) {

	int i;
	byte[] byteStr = new byte[str.length()];
		
	str.getBytes(0,str.length(),byteStr,0);
		
	return byteStr;	
  }

  private static void printUsage() {

	System.err.println(
        "Usage: Text2Midi [options]\n" +
        "\nAvailable [options] are:\n" +
        "[ -v,--verbose ]\n" +
        "[ {-f,--filename} the filename with the ASCII file to be processed ]\n" +
        "[ {-m,--musicalscale} the music scale to be used (0, 'Dodecatonic'; 1, 'Diatonic'; 2, 'Blues'; 3, 'Pentatonic'; 4, 'Whole Tones')]\n" +
        "[ {-s,--speed} the speed to be used (1, 'fast'; 2, 'slow']\n" + 
        "[ {-a,--rangemin} the range min ]\n" + 
        "[ {-z,--rangemax} the range max ]\n"
        );
  }
	

  public static void main(String[] args) 
  {
        CmdLineParser parser                = new CmdLineParser();
	CmdLineParser.Option o_filename     = parser.addStringOption ('f', "filename");
	CmdLineParser.Option o_musicalscale = parser.addIntegerOption  ('m', "musicalscale");
	CmdLineParser.Option o_speed        = parser.addIntegerOption  ('s', "speed");
	CmdLineParser.Option o_rangemin     = parser.addIntegerOption  ('a', "rangemin");
	CmdLineParser.Option o_rangemax     = parser.addIntegerOption  ('z', "rangemax");
	CmdLineParser.Option o_verbose      = parser.addBooleanOption ('v', "verbose");

	try {
	      if ((args == null) || (args.length < 10) || (args.length > 11)) {
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
	else FILENAME="message2.txt";

	MUSICALSCALE = (String) parser.getOptionValue(o_musicalscale).toString();
        log.info("Musical Scale \t = " + MUSICALSCALE);

	SPEED = (String) parser.getOptionValue(o_speed).toString();
        log.info("Music Speed \t\t = " + SPEED);

	RANGE_MIN = (String) parser.getOptionValue(o_rangemin).toString();
        log.info("Min Range \t\t = " + RANGE_MIN);

	RANGE_MAX = (String) parser.getOptionValue(o_rangemax).toString();
        log.info("Max Range \t\t = " + RANGE_MAX);

	Properties properties = new Properties();
        properties.setProperty("FILENAME", FILENAME);
        properties.setProperty("MUSICALSCALE", MUSICALSCALE);
        properties.setProperty("SPEED", SPEED);
        properties.setProperty("RANGE_MIN", RANGE_MIN);
        properties.setProperty("RANGE_MAX", RANGE_MAX);
        new Text2Midi(properties);
  }
} //end of global class
