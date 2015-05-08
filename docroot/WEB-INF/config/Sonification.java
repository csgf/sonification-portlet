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

import javax.sound.sampled.*;

import java.io.*;

import java.nio.channels.*;
import java.nio.*;

import java.util.*; //splitter
import java.util.Properties;

import jargs.gnu.CmdLineParser;
import org.apache.log4j.Logger;

public class Sonification
{
  private static Logger log = Logger.getLogger(Sonification.class);

  private static Boolean verbose = false;
  private static String FILENAME = "";
  private static String BYTES_SAMPLE = "";
  private static String SAMPLING = "";
  private static String RESAMPLE_FREQ = "";
  private static String SCALE_FACTOR = "";
  private static String DATA_TYPE = "";

  AudioFormat audioFormat;
  AudioInputStream audioInputStream;
  SourceDataLine sourceDataLine;

  float sampleRate = 22050.0F;

  int sampleSizeInBits = 16;
  int channels = 1;

  boolean signed = true;
  boolean bigEndian = true;

  double duration=60;
  //Allaowabe any duration in seconds
   
  //A buffer to hold two seconds monaural and one
  // second stereo data at 16000 samp/sec for
  // 16-bit samples
  byte audioData[] = new byte[(int)(sampleRate*duration*2)];

  public Sonification(Properties properties)
  {
    	//constructor
    	new SynGen(properties).getSyntheticData(audioData, properties);

    	playAndStoreData();
  } //end constructor
  
  private void playAndStoreData() 
  {
    
	try {
      		InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);

		audioFormat = new AudioFormat(sampleRate, 
	  		    	  sampleSizeInBits, 
				  channels, 
				  signed, 
				  bigEndian);

      		audioInputStream = new AudioInputStream(byteArrayInputStream, 
		 		       audioFormat, 
				       audioData.length/audioFormat.getFrameSize());
                                   
        	try {
		          AudioSystem.write(audioInputStream,
			                    AudioFileFormat.Type.AIFF,
			                    new File("Sonification"+".aiff"));

        	} catch (Exception e) { e.printStackTrace(); System.exit(0); } //end catch
    	} catch (Exception e) { e.printStackTrace(); System.exit(0); } //end catch
  } //end playAndStoreData



public class SynGen
{
	ByteBuffer byteBuffer;
	ShortBuffer shortBuffer;
	int byteLength;
	String name = "";

	SynGen(Properties properties)
	{
	     String name = properties.getProperty("FILENAME");
	}
 
  	void getSyntheticData(byte[] synDataBuffer, Properties properties)
	{
		byteBuffer = ByteBuffer.wrap(synDataBuffer);
		shortBuffer = byteBuffer.asShortBuffer();
    
    		byteLength = synDataBuffer.length;
	    	synthesize(properties);
	} //end getSyntheticData method

  	void synthesize(Properties properties)
	{
		channels = 1;//Java allows 1 or 2
		//int bytesPerSamp = 2;
		int bytesPerSamp = Integer.parseInt(properties.getProperty("BYTES_SAMPLE"));
		sampleRate = 22050.0F;

		// Allowable 8000,11025,16000,22050,44100
		int sampLength = byteLength/bytesPerSamp;
		double bufferForSpectrum[];
		bufferForSpectrum = new double [sampLength];
          
		//BEGIN Sonification PARAMETERS
		// =============================
		//String filename="tungu_tot.dat";
		String filename = properties.getProperty("FILENAME");
		//double originalSamplingFreq=100; //DO NOT MODIFY 
                	                         //Sonification of geophysical data sampled at 100 Hz
		
		double originalSamplingFreq = Double.parseDouble(properties.getProperty("SAMPLING"));

		//double resampleFreq=10;
		double resampleFreq = Double.parseDouble(properties.getProperty("RESAMPLE_FREQ"));
		//double scalefactor=2;
		double scalefactor = Double.parseDouble(properties.getProperty("SCALE_FACTOR"));
		double holdfactor=(sampleRate/originalSamplingFreq)/resampleFreq;
		// 0 --> one line ASCII file, data separated by <space>
		// 1 --> more lines ASCII file, data separated by <return>	
		//int datatype=1;
		int datatype = Integer.parseInt(properties.getProperty("DATA_TYPE"));
		// =============================
    		//END Sonification PARAMETERS
        
		String aLine = "";
		String bLine = "";
		double value=0;
		double value0=0;

		double max=value;
		double min=value;
		double slope=0;
		double nextvalue=value;
		String [] values = null;    
        
		int nsamplesHold=(int)holdfactor;
        
		if (datatype==1)
		{
			try { 
				  //calculate scale factor and normalization
			          int cnt=0;
			          FileInputStream fin =  new FileInputStream(filename);
			          BufferedReader myInput = new BufferedReader (new InputStreamReader(fin));     
			          aLine=aLine = myInput.readLine();          
			          try {
			             value = Double.valueOf(aLine.trim()).doubleValue();
			             //log.info("value = " + value);
			          } catch (NumberFormatException nfe) {
			              log.error("NumberFormatException: " + nfe.getMessage());
			          }

		          max=value;
		          min=value;

		          while (((aLine = myInput.readLine()) != null) && (cnt<sampLength))
			  {
		            // Convert aLine into a double value
		            try {
			             value = Double.valueOf(aLine.trim()).doubleValue();
			             //log.info("value = " + value);
		            } catch (NumberFormatException nfe) { 
				log.error("NumberFormatException: " + nfe.getMessage()); 
			    }
	
		            if (value>max) max=value;
		            if (value<min) min=value;
		            cnt++;
		          } //end while
		        } catch( Exception e ) { e.printStackTrace(); }//end catch

		        log.info("Max value = " + max);
		        log.info("Min value = " + min);

		        try {
			          int cnt=0;
			          FileInputStream fin =  new FileInputStream(filename);
			          BufferedReader myInput = new BufferedReader (new InputStreamReader(fin));     
			          aLine = myInput.readLine();

			          // first computation out of loop
			          try {
			             value = Double.valueOf(aLine.trim()).doubleValue();
			             //log.info("first in buffer = " + value);
			             value=value-(double)(max+min)/(double)2;
			             value=(double)value/(double)(max-min);
			             value=scalefactor*value;
			             //log.info("first in buffer (after scaling) = " + value);
			           } catch (NumberFormatException nfe) {
			              log.info("NumberFormatException: " + nfe.getMessage());
			           }

			          shortBuffer.put((short)(sampleRate*value));
			          bufferForSpectrum[cnt]=(double)(sampleRate*value);
			          cnt++;
			          bLine = myInput.readLine();
			
			          // second computation out of loop
			          try {
			             nextvalue = Double.valueOf(bLine.trim()).doubleValue();
			             //log.info("second value in buffer = " + nextvalue);
			             nextvalue=nextvalue-(double)(max+min)/(double)2;
			             nextvalue=(double)nextvalue/(double)(max-min);
			             nextvalue=scalefactor*nextvalue;
			             //log.info("second in buffer (after scaling) = " + nextvalue);
			          } catch (NumberFormatException nfe) {
			              log.info("NumberFormatException: " + nfe.getMessage());
			          }
	
			          slope=(nextvalue-value)/holdfactor;
			          //log.info("**nextvalue = " + nextvalue);
			          //log.info("**value     = " + value);
			          //log.info("**holdfacto = " + holdfactor);
			          //log.info("**slope     = " + slope);
          
          			  double step=1;
			          value0=value;

			          while ((aLine !=null) && (cnt<sampLength))
				  {
			            if (cnt<=nsamplesHold){
			              //log.info("Slope: "+slope+ " ");
			              value=value0+slope*step;
			              //log.info("Step: "+step+ " ");
			              //log.info("scaled value in buffer = " + value);
			              shortBuffer.put((short)(sampleRate*value));
			              bufferForSpectrum[cnt]=(double)(sampleRate*value);
			              cnt++;
			              step++;
			            } else {
			              nsamplesHold=nsamplesHold+(int)holdfactor;
			              //reset step
			              step=1;
			              aLine = myInput.readLine();
			              // Convert aLine into a double value
			              try {
				               //set new value0 as last value of the previous slope
				               value0=value;
				               //get next value
				               nextvalue = Double.valueOf(aLine.trim()).doubleValue();
				               log.info("-> Got next value in buffer = " + nextvalue);
				               nextvalue=nextvalue-(double)(max+min)/(double)2;
				               nextvalue=(double)nextvalue/(double)(max-min);
				               nextvalue=scalefactor*nextvalue;
				               //calculate new slope
				               slope=(nextvalue-value)/holdfactor;              
			              } catch (NumberFormatException nfe) {
			                log.info("NumberFormatException: " + nfe.getMessage());
			              }
            			    }  
          			  }
            
        		    } catch( Exception e ) { e.printStackTrace(); } //end catch
        
     		}  else // if (datatype==1)
     		{
     
		      //data example: 12 0 -3 3 2 0 3 12 40 6 54 3
        
        		try { 
				//calculate scale factor and normalization
			        int cnt=0;
			        FileInputStream fin =  new FileInputStream(filename);
			        BufferedReader myInput = new BufferedReader (new InputStreamReader(fin));     
			        aLine=aLine = myInput.readLine(); 
		          	values=aLine.split(" ");   
	
		                try {
			             value = Double.valueOf(values[0]).doubleValue();
			             //log.info("value = " + value);
			        } catch (NumberFormatException nfe) {
			              log.info("NumberFormatException: " + nfe.getMessage());
	 	           	}

		          max=value;
		          min=value;
		          cnt++;

		          while ((cnt<values.length) && (cnt<sampLength))
			  {
		            // Convert aLine into a double value
		            try {
		             value = Double.valueOf(values[cnt]).doubleValue();
		             //log.info("value = " + value);
		            } catch (NumberFormatException nfe) {
		              log.info("NumberFormatException: " + nfe.getMessage());
		            }

		            if (value>max) max=value;
		            else if (value<min) min=value;
            	
			  cnt++;
          		 } //end while

        		} catch( Exception e ) { e.printStackTrace(); } //end catch

		        log.info("Max value = " + max);
		        log.info("Min value = " + min);

		        try {
          			int cnt=0;
			        // first computation out of loop
         
				try {
			             value =Double.valueOf(values[0]).doubleValue();
			             //log.info("first in buffer = " + value);
			             value=value-(double)(max+min)/(double)2;
		        	     value=(double)value/(double)(max-min);
			             value=scalefactor*value;
			             //log.info("first in buffer (after scaling) = " + value);
            			} catch (NumberFormatException nfe) {
			              log.info("NumberFormatException: " + nfe.getMessage());
            			}

			        shortBuffer.put((short)(sampleRate*value));
			        bufferForSpectrum[cnt]=(double)(sampleRate*value);
        			cnt++;
			        // second computation out of loop
        
				try {
			             nextvalue = Double.valueOf(values[cnt]).doubleValue();
		        	     //log.info("second value in buffer = " + nextvalue);
	        		     nextvalue=nextvalue-(double)(max+min)/(double)2;
		        	     nextvalue=(double)nextvalue/(double)(max-min);
	        		     nextvalue=scalefactor*nextvalue;
		        	     //log.info("second in buffer (after scaling) = " + nextvalue);
	        		} catch (NumberFormatException nfe) {
		         	     log.info("NumberFormatException: " + nfe.getMessage());
			        }

	        		slope=(nextvalue-value)/holdfactor;
		         	//log.info("**nextvalue = " + nextvalue);
			        //log.info("**value     = " + value);
		        	//log.info("**holdfacto = " + holdfactor);
			        //log.info("**slope     = " + slope);
          
		          	double step=1;
			        value0=value;

			        while ((cnt<values.length-1) && (cnt<sampLength))
				{
			            if (cnt<=nsamplesHold){
		        	      //log.info("Slope: "+slope+ " ");
			              value=value0+slope*step;
		        	      //log.info("Step: "+step+ " ");
			              //log.info("scaled value in buffer = " + value);
		        	      shortBuffer.put((short)(sampleRate*value));
			              bufferForSpectrum[cnt]=(double)(sampleRate*value);
		        	      cnt++;
			              step++;
			            } else {
		        	      nsamplesHold=nsamplesHold+(int)holdfactor;
			              //reset step
		        	      step=1;
			              value=Double.valueOf(values[cnt]).doubleValue();
		        	      // Convert aLine into a double value
			              try {
		        	       //set new value0 as last value of the previous slope
			               value0=value;
		        	       //get next value
			               nextvalue = Double.valueOf(values[cnt+1]).doubleValue();
		        	       //log.info("-> Got next value in buffer = " + nextvalue);
			               nextvalue=nextvalue-(double)(max+min)/(double)2;
			     	       nextvalue=(double)nextvalue/(double)(max-min);
				       nextvalue=scalefactor*nextvalue;
		        	       //calculate new slope
			               slope=(nextvalue-value)/holdfactor;              
		              	      } catch (NumberFormatException nfe) {
			                log.info("NumberFormatException: " + nfe.getMessage());
		              	      }
             			      //log.info("Not yet implemented"); 
		            	    }  
                		}
            
			    } catch( Exception e ) { e.printStackTrace(); }//end catch
       		} //end of if (datavalue==1)
        

    	//  Writing Sound datafile as ASCII 
    	try {
	      String outputFileName = "Sound.dat";
	      FileWriter outputFileReader  = new FileWriter(outputFileName);   
	      PrintWriter    outputStream  = new PrintWriter(outputFileReader);

	      for (int a=0; a<sampLength; a++) {
        	  //log.info(bufferForSpectrum[a] + "\t");
	          outputStream.print(a);
        	  outputStream.print("\t");
	          outputStream.println(bufferForSpectrum[a]); 
      	      }
	} catch (IOException e) {
            //log.info("IOException:");
            log.info("IOException:");
            e.printStackTrace();
    	}

    /** 
    //DFT computation
    int dataLen = sampLength;
    int outputLen = dataLen;
    int spectrumPts = outputLen;
    //Create arrays for the data and
    // the results.
    double[] output =
                 new double[outputLen];
    double[] spectrum =
               new double[spectrumPts];
    Dft.dft(duration,bufferForSpectrum, spectrumPts, spectrum);     
    // Writing DFT output on disk 
    try{
      String outputFileName = "Spectrum.dat";
      FileWriter outputFileReader  = new FileWriter(outputFileName);   
      PrintWriter    outputStream  = new PrintWriter(outputFileReader);
      for (int a=0; a<sampLength; a++){
          //System.out.print(spectrum[a] + "\t");
          outputStream.print(a);
          outputStream.print("\t");
          outputStream.println(spectrum[a]); 
      }
    } catch (IOException e) {

            System.out.println("IOException:");
            e.printStackTrace();
      }

      */
  }//end method Synthesize

}//end SynGen class
//=============================================//



public static class Dft
{
  public static void dft(double duration, double[] data, int dataLen, double[] spectrum)
  {
    //Set the frequency increment to
    // the reciprocal of the data
    // length.  This is convenience
    // only, and is not a requirement
    // of the DFT algorithm.
    double delF = duration/dataLen;
  
    //Outer loop iterates on frequency
    // values.
    for(int i=0; i < dataLen;i++){
      double freq = i*delF;
      double real = 0;
      double imag = 0;
      //Inner loop iterates on time-
      // series points.
      for(int j=0; j < dataLen; j++){
        real += data[j]*Math.cos(
                     2*Math.PI*freq*j);
        imag += data[j]*Math.sin(
                     2*Math.PI*freq*j);
        spectrum[i] = Math.sqrt(
                real*real + imag*imag);
      }//end inner loop
    }//end outer loop
  }//end dft
}//end Dft01

private static void printUsage() {

        System.err.println(
        "Usage: Sonification [options]\n" +
        "\nAvailable [options] are:\n" +
        "[ -v,--verbose ]\n" +
        "[ {-f,--filename} the filename with the ASCII file to be processed ]\n" +
        "[ {-b,--bytes} the bytes per sample to be used (default 2) ]\n" + 
        "[ {-o,--sampling} the original sampling frequency to be used (default 100 Hz) ]\n" +
        "[ {-r,--resample} the resampling frequency to be used (default 10) ]\n" +
        "[ {-s,--scale} the scale factor to be used (default 2) ]\n" +
        "[ {-d,--datatype} the data type to be used. Possible values: 0,1 (default 1) ]\n"
        );
}


public static void main(String args[])
{
	CmdLineParser parser                    = new CmdLineParser();
        CmdLineParser.Option o_verbose          = parser.addBooleanOption ('v', "verbose");
        CmdLineParser.Option o_filename         = parser.addStringOption ('f', "filename");
        CmdLineParser.Option o_bytesPerSample   = parser.addStringOption ('b', "bytesPerSample");
        CmdLineParser.Option o_originalSampling = parser.addStringOption ('o', "originalSampling");
        CmdLineParser.Option o_resampleFreq     = parser.addStringOption ('r', "resampleFreq");
        CmdLineParser.Option o_scale            = parser.addStringOption ('s', "scale");
        CmdLineParser.Option o_datatype         = parser.addStringOption ('d', "datatype");

	try {
		if ((args.length < 13) || (args == null) || (args.length >= 14)) {
                	printUsage();
                        System.exit(2);
                } else parser.parse(args);
         } catch ( CmdLineParser.OptionException e ) {
         	printUsage();
                System.exit(2);
         } catch (Exception exc){ System.out.println (exc.toString()); }

	 verbose = (Boolean) parser.getOptionValue(o_verbose, Boolean.FALSE);
         if (verbose) log.setLevel(org.apache.log4j.Level.INFO);
         log.info("Verbose level \t\t   = " + verbose);

         FILENAME = (String) parser.getOptionValue(o_filename);
         if(FILENAME != null && FILENAME.length() > 0)
         log.info("ASCII File \t\t   = " + FILENAME);
         else FILENAME="message2.txt";

         BYTES_SAMPLE = (String) parser.getOptionValue(o_bytesPerSample);
         if(BYTES_SAMPLE != null && BYTES_SAMPLE.length() > 0)
         log.info("Bytes Per Sample \t\t   = " + BYTES_SAMPLE);

	 SAMPLING = (String) parser.getOptionValue(o_originalSampling);
         if(SAMPLING != null && SAMPLING.length() > 0)
         log.info("Original Sampling Frequency  = " + SAMPLING + " Hz.");

	 RESAMPLE_FREQ = (String) parser.getOptionValue(o_resampleFreq);
         if(RESAMPLE_FREQ != null && RESAMPLE_FREQ.length() > 0)
         log.info("Resample Frequency \t   = " + RESAMPLE_FREQ + " Hz.");

	 SCALE_FACTOR = (String) parser.getOptionValue(o_scale);
         if(SCALE_FACTOR != null && SCALE_FACTOR.length() > 0)
         log.info("Scale Factor \t\t   = " + SCALE_FACTOR);

	 DATA_TYPE = (String) parser.getOptionValue(o_datatype);
         if(DATA_TYPE != null && DATA_TYPE.length() > 0)
         log.info("Data Type \t\t   = " + DATA_TYPE);

         Properties properties = new Properties();
         properties.setProperty("FILENAME", FILENAME);
         properties.setProperty("BYTES_SAMPLE", BYTES_SAMPLE);
         properties.setProperty("SAMPLING", SAMPLING);
         properties.setProperty("RESAMPLE_FREQ", RESAMPLE_FREQ);
         properties.setProperty("SCALE_FACTOR", SCALE_FACTOR);
         properties.setProperty("DATA_TYPE", DATA_TYPE);
	 new Sonification(properties);
} //end main

}//end outer class Sonification.java
