<%
/**************************************************************************
Copyright (c) 2011-2014:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy

See http://www.infn.it and and http://www.consorzio-cometa.it for details on 
the copyright holders.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author <a href="mailto:giuseppe.larocca@ct.infn.it">Giuseppe La Rocca</a>
****************************************************************************/
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.portlet.*"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects/>

<script type="text/javascript">
    $(document).ready(function() {
              
    $('.slideshow').cycle({
	fx: 'fade' // choose your transition type (fade, scrollUp, shuffle, etc)
    });
    
    // Roller
    $('#sonification_footer').rollchildren({
        delay_time         : 3000,
        loop               : true,
        pause_on_mouseover : true,
        roll_up_old_item   : true,
        speed              : 'slow'   
    });
    
    //var $tumblelog = $('#tumblelog');  
    $('#tumblelog').imagesLoaded( function() {
      $tumblelog.masonry({
        columnWidth: 440
      });
    });
});
</script>
                    
<br/>

<fieldset>
<legend>About the project</legend>

<section id="content">

<div id="tumblelog" class="clearfix">
    
  <div class="story col3">    
  <p style="text-align:justify; position: relative;">
  Data Sonification is the representation of data by means of sound signals, so it is the analog of scientific 
  visualization, where we deal with auditory instead of visual images.
  Generally speaking any sonification procedure is a mathematical mapping from a certain data set (numbers, strings, 
  images, ...) to a sound string. <br/>Data sonification is currently used in several fields, for different purposes: science 
  and engineering, education and training, since it provides a quick and effective data analysis and interpretation tool. 
  Although most data analysis techniques are exclusively visual in nature (i.e. are based on the possibility of looking 
  at graphical representations), data presentation and exploration systems could benefit greatly from the addition of 
  sonification capacities.
  </p>    
  <p>&mdash; <i>Domenico VICINANZA, Dante (Delivery of Advanced Network Technology to Europe)</i></p>
  </div>

  <div class="story col3">
  
    <table border="0">
    <tr>
    <td>                
        <a href="http://player.vimeo.com/video/8878045">
                <img align="center" width="280" height="210"
                     src="<%= renderRequest.getContextPath()%>/images/sonification1_video.jpg" 
                     border="0"/>
            </a>
    </td>
    <td> &nbsp;&nbsp; </td>
    <td>        
    <p style="text-align:justify;">
        <font style="position: relative;">
        For the first time ever, a modern dance company has performed to music generated from seismic data, recorded from four 
        volcanoes across three continents. This unique event was facilitated by DANTE, the provider of high speed research and 
        education networks, the two distributed computing projects, Enabling Grids for E-sciencE (<a href="http://www.eu-egee.org/">EGEE</a>) and E-science grid 
        facility for Europe and Latin America (<a href="http://www.eu-eela.eu/">EELA</a>), as well as <a href="http://citydance.net/">CityDance Ensemble</a>, a prestigious company based in Washington, DC.<br/>        
        </font>        
    </p>    
    </td>
    </tr>
    <tr>
        <td colspan="3">
        <font style="position: relative;">
        The dance, titled The Mountain, and choreographed by <a hfre="http://www.jasonignacio.com"> Jason Garcia Ignacio</a>, was part of CityDance Ensemble’s Carbon, a 
        work-in-progress about climate change. Originally presented in sold-out performances on 14 and 15 of March 2009 at the Music 
        Centre, Maryland, USA.
        <br/><br/>
        <a href="http://www.volcanodance.org">
        <img src="http://www.volcanodance.org/Site2/Photos_files/TheMountainBanner1.jpg"
            title=""
            style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
            heigh="400" 
            width="590"/>
        </a>
        </td>
    </tr>    
    </table>    
  </div>                 
             
  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
      <h3>About Data Audification</h3>      
        <p style="text-align:justify;">
        Data audification is the representation of data by sound signals; it can 
        be considered as the acoustic counterpart of data graphic visualization, 
        a mathematical mapping of information from data sets to sounds. In the 
        past twenty years the word audification has acquired a new meaning in 
        the world of computer music, computer science, and auditory display 
        application development. Data audification is currently used in several 
        fields, for different purposes: science and engineering, education and
        training, in most of the cases to provide a quick and effective data 
        analysis and interpretation tool.<br/>        
        Although most data analysis techniques are exclusively visual in nature 
        (i.e. are based on the possibility of looking at graphical representations), 
        data presentation and exploration systems could benefit greatly from the 
        addition of sonification capabilities. [..] <br/>
        For further information about the sonification process, please download the <a href="http://bit.ly/uaDPAJ">White paper (PDF)</a>
        </p>
 </div>
                          
  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
  <h2>MIDI Sonification of Mt. Etna Volcano Seismograms</h2>           
  <br/>
  <object height="81" width="100%" style="padding: 5px; border: 1px solid #ccc; background-color: #eee;">
  <param name="wmode" value="transparent">
  <param name="movie" value="http://player.soundcloud.com/player.swf?url=http%3A%2F%2Fapi.soundcloud.com%2Ftracks%2F29302665&amp;g=1&amp;show_comments=false&amp;auto_play=false&amp;color=000000">             
  </param>
  <embed height="81" src="http://player.soundcloud.com/player.swf?url=http%3A%2F%2Fapi.soundcloud.com%2Ftracks%2F29302665&amp;g=1&amp;show_comments=false&amp;auto_play=false&amp;color=000000" type="application/x-shockwave-flash" width="100%"> 
  </embed> 
  </object>                
  </div>
    
  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
  <table border="0">
  <tr>
  <td>              
    <!--iframe src="http://www.youtube.com/v/8HjRZ9JWnR0?version=3&feature=player_detailpage" 
            style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
            width="280" height="210" frameborder="0" 
            webkitAllowFullScreen mozallowfullscreen allowFullScreen>        
    </iframe-->            
    <a href="http://www.youtube.com/v/8HjRZ9JWnR0">
        <img align="center" width="280" height="210"
             src="<%= renderRequest.getContextPath()%>/images/sonification2_video.jpg" 
             border="0"/>
    </a>   
  </td>
  <td> &nbsp;&nbsp; </td>
  <td>
      <p style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px; text-align:justify;">
          The Sonification application won the best poster and demo awards at the 
          <a href="http://cf2012.egi.eu/">EGI Community Forum, Munich, 2012</a>
          <br/><br/>The demo was about how we can create some music from text 
          (or tweets) using the <a href="http://www.egi.eu/">EGI Infrastructure</a>
      </p>
  </td>
  </tr>
  </table>
  </div>
            
  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
  <h2>3D Image Rendering as Data Representation</h2>
  <table border="0">
  <tr>
    <td> 
    <a href="<%= renderRequest.getContextPath()%>/images/MessageToSpheres1.png">
    <img src="<%= renderRequest.getContextPath()%>/images/MessageToSpheres1.png"
         title="3D rendering of the POVRay source file"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="170" 
         width="137"/>
    </a>
    </td>
    <td>
    <a href="<%= renderRequest.getContextPath()%>/images/MessageToSpheres5.png">
    <img src="<%= renderRequest.getContextPath()%>/images/MessageToSpheres5.png"
         title="3D rendering of the POVRay source file"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="170" 
         width="137"/>
    </a>         
    </td>
    <td>
        <a href="<%= renderRequest.getContextPath()%>/images/MessageToSpheres3.png">
        <img src="<%= renderRequest.getContextPath()%>/images/MessageToSpheres3.png"
         title="3D rendering of the POVRay source file"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="170" 
         width="137"/>
        </a>
    </td>
    <td>
        <a href="<%= renderRequest.getContextPath()%>/images/MessageToSpheres4.png">
        <img src="<%= renderRequest.getContextPath()%>/images/MessageToSpheres4.png"
         title="3D rendering of the POVRay source file"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="170" 
         width="137"/>
        </a>
    </td>
  </tr>
  </table>
  </div>

  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
      <h2><a href="mailto:info@Domenico.Vicinanza@dante.net">
      <img width="100" src="<%= renderRequest.getContextPath()%>/images/contact6.jpg" border="0" title="Get in touch with the project"/></a>Contacts</h2>
      <p style="text-align:justify;">Giuseppe LA ROCCA<i> &mdash; (Responsible for GRID deployment)</i></p>
      <p style="text-align:justify;">Mariapaola SORRENTINO<i> &mdash; (Responsible of the Sonification process)</i></p>
      <p style="text-align:justify;">Domenico VICINANZA <i> &mdash; (Technical Coordinator)</i></p>        
  </div>               
    
  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
        <h2>Sponsors & Credits</h2>
        <table border="0">                        
            <tr>                
            <td>
            <p align="justify">
            <a href="http://www.geant.net">
                <img align="center" width="100" 
                     src="<%= renderRequest.getContextPath()%>/images/geant_logo_rgb_300dpi.png" 
                     border="0" title="The GEANT Project" />
            </a>
            </p>
            </td>
                    
            <!--td>
            <p align="justify">
            <a href="http://www.eumedconnect.net/">
                <img align="center" width="130"                      
                     src="<%= renderRequest.getContextPath()%>/images/EUMEDconnect2_300dpi_RGB.png" 
                     border="0" title="The EUMEDCONNECT Project" />
            </a>
            </p>
            </td-->                        
            
            <td>
            <p align="justify">
            <a href="http://www.redclara.net/">
                <img align="center" width="100"                      
                     src="<%= renderRequest.getContextPath()%>/images/redclara.png" 
                     border="0" title="RedCLARA - Cooperación Latino Americana de Redes Avanzadas" />
            </a>
            </p>            
            </td>            
            
            <td>
            <p align="justify">
            <a href="http://www.dante.net/">
                <img align="center" width="90"                      
                     src="<%= renderRequest.getContextPath()%>/images/dante_2colour_logo.gif" 
                     border="0" title="DANTE" />
            </a>
            </p>
            </td>
            
            <td>
                <p align="justify">
                <a href="http://www.france-grilles.fr/">
                <img align="center" width="130" height="50"
                     src="<%= renderRequest.getContextPath()%>/images/fg_logo.png" 
                     border="0" title="France Grilles. Sharing IT resources and expertise for science."/>
                </a>
                </p>
            </td>
            
            <td>
                <p align="justify">
                <a href="https://www.jyu.fi/">
                <img align="center" width="90" height="90"
                     src="<%= renderRequest.getContextPath()%>/images/midilogo_medium.gif" 
                     border="0" title="The MIDI Toolbox for analyzing and visualizing MIDI files in the Matlab computing environment"/>
                </a>
                </p>
            </td>
            </tr>
            
            <tr>
            <td>
            <p align="justify">
            <a href="http://www.egi.eu/projects/egi-inspire/">
                <img align="center" width="100" 
                     src="<%= renderRequest.getContextPath()%>/images/EGI_Logo_RGB_315x250px.png" 
                 title="EGI - The European Grid Infrastructure" />
            </a>   
            </p>            
            </td>
            
            <td>
            <p align="justify">
            <a href="http://www.infn.it/indexen.php">
                <img align="center" width="100" 
                     src="<%= renderRequest.getContextPath()%>/images/garland_logo.png"  
                     border="0" title="IGI - The Italian Grid Initiatives" />
            </a>   
            </p>
            </td>
            
            <td>
            <p align="justify">
            <a href="https://gilda.ct.infn.it">
                <img align="center" width="120"
                     src="<%= renderRequest.getContextPath()%>/images/gilda.png" 
                     border="0" title="The GILDA Project" />
            </a>
            </p>
            </td>                        
            
            <td>
            <p align="justify">
            <a href="http://www.eumedgrid.eu/">
                <img align="center" width="100"
                     src="http://www.eumedgrid.eu/images/stories/eumedgrid_logo.png" 
                     border="0" title="The EUMEDGRID-Support Project" />
            </a>
            </p>
            </td>
            
            <td>
            <p align="justify">
            <a href="http://www.gisela-grid.eu/">
                <img align="center" width="100"                      
                     src="<%= renderRequest.getContextPath()%>/images/GISELA_Logo_B.gif" 
                     border="0" title="The giSela Project" />
            </a>
            </p>
            </td>
            </tr>
            
            <tr>
            <td>
            <p align="justify">
            <a href="http://www.euindiagrid.eu/">
                <img align="center" width="140"                      
                     src="<%= renderRequest.getContextPath()%>/images/euindia_logo.png" 
                     border="0" title="The EU-India Project" />
            </a>
            </p>
            </td>
                
            <td>
                <p align="justify">
                <a href="http://www.indicate-project.eu/">
                    <img align="center" width="120"                      
                     src="<%= renderRequest.getContextPath()%>/images/Indicate.png" 
                     border="0" title="The Indicate Project" />
                </a>
                </p>
            </td>            
                
            <td>
                <p align="justify">
                <a href="http://citydance.net/">
                <img align="center" width="130" heigth="120"
                     src="<%= renderRequest.getContextPath()%>/images/CityDanceEnsemble_logo.png" 
                     border="0" title="The CityDance Ensemble Inc., Washington" />
                </a>
                </p>
            </td>
                
            <td>
                <p align="justify">
                <a href="http://www.conservatorio.avellino.it">
                <img align="center" width="70" heigth="80"
                     src="<%= renderRequest.getContextPath()%>/images/cimarosa.gif" 
                     border="0" title="Conservatory of Music 'D. Cimarosa' ~ Avellino"/>
                </a>
                </p>
            </td>
            
            <td>
                <p align="justify">
                <a href="http://www.soundcloud.com/">
                <img align="center" width="80" heigth="90"
                     src="<%= renderRequest.getContextPath()%>/images/soundcloud-logo.png" 
                     border="0" title="SoundCloud, the world’s leading social sound platform"/>
                </a>
                </p>
            </td>                               
            </tr>
        </table>   
  </div>
</div>
</section>
</fieldset>
           
<div id="sonification_footer" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
    <div>SONIFICATION portlet ver. 1.3.9</div>
    <div>Istituto Nazionale di Fisica Nucleare (INFN), Italy, Copyright © 2014</div>
    <div>All rights reserved</div>
    <div>This work has been partially supported by
        <a href="http://www.egi.eu/projects/egi-inspire/">
            <img width="35" 
                 border="0"
                 src="<%= renderRequest.getContextPath()%>/images/EGI_Logo_RGB_315x250px.png" 
                 title="EGI - The European Grid Infrastructure" />
        </a>
    </div>
</div>