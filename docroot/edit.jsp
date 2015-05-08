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
<%@page import="javax.portlet.*"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<%
  //
  // SONIFICATION 1.3.9 portlet preferences for the GirdEngine interaction
  //
  // These parameters are:  
  // o *_sonification_INFRASTRUCTURE  - The Infrastructure Acronym to be enabled
  // o *_sonification_TOPBDII         - The TopBDII hostname for accessing the Infrastructure
  // o *_sonification_WMS             - The WMProxy hostname for accessing the Infrastructure
  // o *_sonification_VONAME          - The VO name
  // o *_sonification_ETOKENSERVER    - The eTokenServer hostname to be contacted for 
  //                                    requesting grid proxies
  // o *_sonification_MYPROXYSERVER   - The MyProxyServer hostname for requesting 
  //                                    long-term grid proxies
  // o *_sonification_PORT            - The port on which the eTokenServer is listening
  // o *_sonification_ROBOTID         - The robotID to generate the grid proxy
  // o *_sonification_ROLE            - The FQAN for the grid proxy (if any)
  // o *_sonification_REWAL           - Enable the creation of a long-term proxy to a 
  //                                    MyProxy Server (default 'yes')
  // o *_sonification_DISABLEVOMS     - Disable the creation of a VOMS proxy (default 'no')
  //
  // o sonification_APPID             - The ApplicationID for the SONIFICATION results
  // o sonification_OUTPUT_PATH       - The path where store results
  // o sonification_SOFTWARE          - The Application Software requested by the application
  // o TRACKING_DB_HOSTNAME           - The Tracking DB server hostname 
  // o TRACKING_DB_USERNAME           - The username credential for login
  // o TRACKING_DB_PASSWORD           - The password for login
%>

<jsp:useBean id="cometa_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="cometa_sonification_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="garuda_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="garuda_sonification_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gridit_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="gridit_sonification_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gilda_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="gilda_sonification_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="eumed_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="eumed_sonification_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gisela_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_sonification_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="gisela_sonification_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_sonification_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_sonification_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_sonification_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_sonification_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_sonification_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_sonification_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_sonification_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="sagrid_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_sonification_WMS" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="sagrid_sonification_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_sonification_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_sonification_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_sonification_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_sonification_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_sonification_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_sonification_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_sonification_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="sonification_ENABLEINFRASTRUCTURE" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="sonification_APPID" class="java.lang.String" scope="request"/>
<jsp:useBean id="sonification_OUTPUT_PATH" class="java.lang.String" scope="request"/>
<jsp:useBean id="sonification_SOFTWARE" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_HOSTNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_USERNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_PASSWORD" class="java.lang.String" scope="request"/>

<jsp:useBean id="SMTP_HOST" class="java.lang.String" scope="request"/>
<jsp:useBean id="SENDER_MAIL" class="java.lang.String" scope="request"/>

<script type="text/javascript">
    
    //var EnabledInfrastructure = "<%= sonification_ENABLEINFRASTRUCTURE %>";    
    //console.log(EnabledInfrastructure);        
            
    $(document).ready(function() { 
                
        var cometa_inputs = 1;        
        // ADDING a new WMS enpoint for the COMETA infrastructure (MAX. 5)
        $('#adding_WMS_cometa').click(function() {        
            ++cometa_inputs;        
            if (cometa_inputs>1 && cometa_inputs<6) {
            var c = $('.cloned_cometa_sonification_WMS:first').clone(true);            
            c.children(':text').attr('name','cometa_sonification_WMS' );
            c.children(':text').attr('id','cometa_sonification_WMS' );
            $('.cloned_cometa_sonification_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the COMETA infrastructure
        $('.btnDel_cometa').click(function() {
        if (cometa_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --cometa_inputs;
            $(this).closest('.cloned_cometa_sonification_WMS').remove();
            $('.btnDel_cometa').attr('disabled',($('.cloned_cometa_sonification_WMS').length < 2));
        }
        });
        
        $('.btnDel_cometa2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --cometa_inputs;
            $(this).closest('.cloned_cached_cometaWMS').remove();
            $('.btnDel_cometa2').attr('disabled',($('.cloned_cached_cometaWMS').length < 2));
        }
        });
        
        var garuda_inputs = 1;        
        // ADDING a new WMS enpoint for the GARUDA infrastructure (MAX. 5)
        $('#adding_WMS_garuda').click(function() {        
            ++garuda_inputs;        
            if (garuda_inputs>1 && garuda_inputs<6) {
            var c = $('.cloned_garuda_sonification_WMS:first').clone(true);            
            c.children(':text').attr('name','garuda_sonification_WMS' );
            c.children(':text').attr('id','garuda_sonification_WMS' );
            $('.cloned_garuda_sonification_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the GARUDA infrastructure
        $('.btnDel_garuda').click(function() {
        if (garuda_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --garuda_inputs;
            $(this).closest('.cloned_garuda_sonification_WMS').remove();
            $('.btnDel_garuda').attr('disabled',($('.cloned_garuda_sonification_WMS').length < 2));
        }
        });
        
        $('.btnDel_garuda2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --garuda_inputs;
            $(this).closest('.cloned_cached_garudaWMS').remove();
            $('.btnDel_garuda2').attr('disabled',($('.cloned_cached_garudaWMS').length < 2));
        }
        });
                
        var gridit_inputs = 1;        
        // ADDING a new WMS enpoint for the GRIDIT infrastructure (MAX. 5)
        $('#adding_WMS_gridit').click(function() {        
            ++gridit_inputs;        
            if (gridit_inputs>1 && gridit_inputs<6) {
            var c = $('.cloned_gridit_sonification_WMS:first').clone(true);            
            c.children(':text').attr('name','gridit_sonification_WMS' );
            c.children(':text').attr('id','gridit_sonification_WMS' );
            $('.cloned_gridit_sonification_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the GRIDIT infrastructure
        $('.btnDel_gridit').click(function() {
        if (gridit_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --gridit_inputs;
            $(this).closest('.cloned_gridit_sonification_WMS').remove();
            $('.btnDel_gridit').attr('disabled',($('.cloned_gridit_sonification_WMS').length < 2));
        }
        });
        
        $('.btnDel_gridit2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --gridit_inputs;
            $(this).closest('.cloned_cached_griditWMS').remove();
            $('.btnDel_gridit2').attr('disabled',($('.cloned_cached_griditWMS').length < 2));
        }
        });
        
        var gilda_inputs = 1;        
        // ADDING a new WMS enpoint for the GILDA infrastructure (MAX. 5)
        $('#adding_WMS_gilda').click(function() {        
            ++gilda_inputs;        
            if (gilda_inputs>1 && gilda_inputs<6) {
            var c = $('.cloned_gilda_sonification_WMS:first').clone(true);            
            c.children(':text').attr('name','gilda_sonification_WMS' );
            c.children(':text').attr('id','gilda_sonification_WMS' );
            $('.cloned_gilda_sonification_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the GILDA infrastructure
        $('.btnDel_gilda').click(function() {
        if (gilda_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --gilda_inputs;
            $(this).closest('.cloned_gilda_sonification_WMS').remove();
            $('.btnDel_gilda').attr('disabled',($('.cloned_gilda_sonification_WMS').length < 2));
        }
        });
                
        $('.btnDel_gilda2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --gilda_inputs;
            $(this).closest('.cloned_cached_gildaWMS').remove();
            $('.btnDel_gilda2').attr('disabled',($('.cloned_cached_gildaWMS').length < 2));
        }
        });
                                
        var eumed_inputs = 1;        
        // ADDING a new WMS enpoint for the EUMED infrastructure (MAX. 5)
        $('#adding_WMS_eumed').click(function() {        
            ++eumed_inputs;        
            if (eumed_inputs>1 && eumed_inputs<6) {
            var c = $('.cloned_eumed_sonification_WMS:first').clone(true);            
            c.children(':text').attr('name','eumed_sonification_WMS' );
            c.children(':text').attr('id','eumed_sonification_WMS' );
            $('.cloned_eumed_sonification_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the EUMED infrastructure
        $('.btnDel_eumed').click(function() {
        if (eumed_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --eumed_inputs;
            $(this).closest('.cloned_eumed_sonification_WMS').remove();
            $('.btnDel_eumed').attr('disabled',($('.cloned_eumed_sonification_WMS').length < 2));
        }
        });
                
        $('.btnDel_eumed2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --eumed_inputs;
            $(this).closest('.cloned_cached_eumedWMS').remove();
            $('.btnDel_eumed2').attr('disabled',($('.cloned_cached_eumedWMS').length < 2));
        }
        });
        
        var gisela_inputs = 1;        
        // ADDING a new WMS enpoint for the GISELA infrastructure (MAX. 5)
        $('#adding_WMS_gisela').click(function() {        
            ++gisela_inputs;        
            if (gisela_inputs>1 && gisela_inputs<6) {
            var c = $('.cloned_gisela_sonification_WMS:first').clone(true);            
            c.children(':text').attr('name','gisela_sonification_WMS' );
            c.children(':text').attr('id','gisela_sonification_WMS' );
            $('.cloned_gisela_sonification_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the GISELA infrastructure
        $('.btnDel_gisela').click(function() {
        if (gisela_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --gisela_inputs;
            $(this).closest('.cloned_gisela_sonification_WMS').remove();
            $('.btnDel_gisela').attr('disabled',($('.cloned_gisela_sonification_WMS').length < 2));
        }
        });
        
        $('.btnDel_gisela2').click(function() {            
            if (confirm('Do you really want to delete the item ?')) {
            --gisela_inputs;
            $(this).closest('.cloned_cached_giselaWMS').remove();
            $('.btnDel_gisela2').attr('disabled',($('.cloned_cached_giselaWMS').length < 2));
        }
        });
        
        var sagrid_inputs = 1;        
        // ADDING a new WMS enpoint for the SAGRID infrastructure (MAX. 5)
        $('#adding_WMS_sagrid').click(function() {        
            ++sagrid_inputs;        
            if (sagrid_inputs>1 && sagrid_inputs<6) {
            var c = $('.cloned_sagrid_sonification_WMS:first').clone(true);            
            c.children(':text').attr('name','sagrid_sonification_WMS' );
            c.children(':text').attr('id','sagrid_sonification_WMS' );
            $('.cloned_sagrid_sonification_WMS:last').after(c);
        }        
        });
        
        // REMOVING a new WMS enpoint for the SAGRID infrastructure
        $('.btnDel_sagrid').click(function() {
        if (sagrid_inputs > 1)
            if (confirm('Do you really want to delete the item ?')) {
            --sagrid_inputs;
            $(this).closest('.cloned_sagrid_sonification_WMS').remove();
            $('.btnDel_sagrid').attr('disabled',($('.cloned_sagrid_sonification_WMS').length < 2));
        }
        });
        
        $('.btnDel_sagrid2').click(function() {
            if (confirm('Do you really want to delete the item ?')) {
            --sagrid_inputs;
            $(this).closest('.cloned_cached_sagridWMS').remove();
            $('.btnDel_sagrid2').attr('disabled',($('.cloned_cached_sagridWMS').length < 2));
        }
        });
        

        // Validate input form
        $('#SonificationEditForm').validate({
            rules: {
                cometa_sonification_INFRASTRUCTURE: {
                    required: true              
                },
                cometa_sonification_VONAME: {
                    required: true              
                },
                cometa_sonification_TOPBDII: {
                    required: true
                },
                cometa_sonification_WMS: {
                    required: true
                },
                cometa_sonification_MYPROXYSERVER: {
                    required: true
                },
                cometa_sonification_ETOKENSERVER: {
                    required: true
                },                
                cometa_sonification_PORT: {
                    required: true
                },
                cometa_sonification_ROBOTID: {
                    required: true
                },
                
                garuda_sonification_INFRASTRUCTURE: {
                    required: true              
                },
                garuda_sonification_VONAME: {
                    required: true              
                },
                garuda_sonification_WMS: {
                    required: true
                },
                garuda_sonification_ETOKENSERVER: {
                    required: true
                },                
                garuda_sonification_PORT: {
                    required: true
                },
                garuda_sonification_ROBOTID: {
                    required: true
                },
                
                
                gridit_sonification_INFRASTRUCTURE: {
                    required: true              
                },
                gridit_sonification_VONAME: {
                    required: true              
                },
                gridit_sonification_TOPBDII: {
                    required: true
                },
                gridit_sonification_WMS: {
                    required: true
                },
                gridit_sonification_MYPROXYSERVER: {
                    required: true
                },
                gridit_sonification_ETOKENSERVER: {
                    required: true
                },                
                gridit_sonification_PORT: {
                    required: true
                },
                gridit_sonification_ROBOTID: {
                    required: true
                },
                
                
                gilda_sonification_INFRASTRUCTURE: {
                    required: true              
                },
                gilda_sonification_VONAME: {
                    required: true              
                },
                gilda_sonification_WMS: {
                    required: true
                },
                gilda_sonification_MYPROXYSERVER: {
                    required: true
                },
                gilda_sonification_ETOKENSERVER: {
                    required: true
                },                
                gilda_sonification_PORT: {
                    required: true
                },
                gilda_sonification_ROBOTID: {
                    required: true
                },
                
                                
                eumed_sonification_INFRASTRUCTURE: {
                    required: true              
                },
                eumed_sonification_VONAME: {
                    required: true              
                },
                eumed_sonification_TOPBDII: {
                    required: true
                },
                eumed_sonification_WMS: {
                    required: true
                },
                eumed_sonification_MYPROXYSERVER: {
                    required: true
                },
                eumed_sonification_ETOKENSERVER: {
                    required: true
                },                
                eumed_sonification_PORT: {
                    required: true
                },
                eumed_sonification_ROBOTID: {
                    required: true
                },
                
                
                gisela_sonification_INFRASTRUCTURE: {
                    required: true              
                },
                gisela_sonification_VONAME: {
                    required: true              
                },
                gisela_sonification_TOPBDII: {
                    required: true
                },
                gisela_sonification_WMS: {
                    required: true
                },
                gisela_sonification_MYPROXYSERVER: {
                    required: true
                },
                gisela_sonification_ETOKENSERVER: {
                    required: true
                },                
                gisela_sonification_PORT: {
                    required: true
                },
                gisela_sonification_ROBOTID: {
                    required: true
                },
                
                
                sagrid_sonification_INFRASTRUCTURE: {
                    required: true              
                },
                sagrid_sonification_VONAME: {
                    required: true              
                },
                sagrid_sonification_TOPBDII: {
                    required: true
                },
                sagrid_sonification_WMS: {
                    required: true
                },
                sagrid_sonification_MYPROXYSERVER: {
                    required: true
                },
                sagrid_sonification_ETOKENSERVER: {
                    required: true
                },                
                sagrid_sonification_PORT: {
                    required: true
                },
                sagrid_sonification_ROBOTID: {
                    required: true
                },
                
                sonification_APPID: {
                    required: true              
                },
                sonification_OUTPUT_PATH: {
                    required: true              
                }
            },
            
            invalidHandler: function(form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors) {
                    $("#error_message").empty();
                    var message = errors == 1
                    ? ' You missed 1 field. It has been highlighted'
                    : ' You missed ' + errors + ' fields. They have been highlighted';                    
                    $('#error_message').append("<img width='30' src='<%=renderRequest.getContextPath()%>/images/Warning.png' border='0'>"+message);
                    $("#error_message").show();
                } else $("#error_message").hide();                
            },
            
            submitHandler: function(form) {
                   form.submit();
            }
        });
        
        $("#SonificationEditForm").bind('submit', function () {            
            // Check if the Sonification OPTIONS are NULL
            if ( !$('#cometa_sonification_RENEWAL').is(':checked') && 
                 !$('#cometa_sonification_DISABLEVOMS').is(':checked') 
             ) $('#cometa_sonification_OPTIONS').val('NULL');
                 
            if ( !$('#garuda_sonification_RENEWAL').is(':checked') && 
                 !$('#garuda_sonification_DISABLEVOMS').is(':checked') 
             ) $('#garuda_sonification_OPTIONS').val('NULL');                 
                 
            if ( !$('#gridit_sonification_RENEWAL').is(':checked') && 
                 !$('#gridit_sonification_DISABLEVOMS').is(':checked') 
             ) $('#gridit_sonification_OPTIONS').val('NULL');
                 
            if ( !$('#gilda_sonification_RENEWAL').is(':checked') && 
                  !$('#gilda_sonification_DISABLEVOMS').is(':checked') 
             ) $('#gilda_sonification_OPTIONS').val('NULL');
                 
            if ( !$('#eumed_sonification_RENEWAL').is(':checked') && 
                  !$('#eumed_sonification_DISABLEVOMS').is(':checked') 
             ) $('#eumed_sonification_OPTIONS').val('NULL');
                 
            if ( !$('#gisela_sonification_RENEWAL').is(':checked') && 
                  !$('#gisela_sonification_DISABLEVOMS').is(':checked') 
             ) $('#gisela_sonification_OPTIONS').val('NULL');                             
                 
            if ( !$('#sagrid_sonification_RENEWAL').is(':checked') && 
                  !$('#sagrid_sonification_DISABLEVOMS').is(':checked') 
             ) $('#sagrid_sonification_OPTIONS').val('NULL');
                 
             //("#sonification_ENABLEINFRASTRUCTURE").val(EnabledInfrastructure);                          
       });                
    });                
</script>

<br/>
<p style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
    Please, select the SONIFICATION Grid Settings before to start</p>  

<!DOCTYPE html>
<form id="SonificationEditForm"
      name="SonificationEditForm"
      action="<portlet:actionURL><portlet:param name="ActionEvent" value="CONFIG_SONIFICATION_PORTLET"/></portlet:actionURL>" 
      method="POST">

<fieldset>
<legend>Portlet Settings</legend>
<div style="margin-left:15px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;" id="error_message"></div>
<br/>
<table id="results" border="0" width="620" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">

<!-- COMETA -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="sonification_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= sonification_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='cometa'}">
                <c:set var="results_cometa" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_cometa=='true'}">
            <input type="checkbox" 
                   id="cometa_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="cometa"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="cometa_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="cometa"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="cometa_sonification_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="cometa_sonification_INFRASTRUCTURE"
               name="cometa_sonification_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="COMETA" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="cometa_sonification_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="cometa_sonification_VONAME"
               name="cometa_sonification_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= cometa_sonification_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="cometa_sonification_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="cometa_sonification_TOPBDII"
               name="cometa_sonification_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= cometa_sonification_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"         
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="cometa_sonification_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>          
        <c:forEach var="wms" items="<%= cometa_sonification_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_cometaWMS">
            <input type="text"                
                   name="cometa_sonification_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_cometa2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_cometa_sonification_WMS">
        <input type="text"                
               name="cometa_sonification_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_cometa" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_cometa" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="cometa_sonification_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="cometa_sonification_MYPROXYSERVER"
               name="cometa_sonification_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= cometa_sonification_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="cometa_sonification_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="cometa_sonification_ETOKENSERVER"
               name="cometa_sonification_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= cometa_sonification_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="cometa_sonification_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="cometa_sonification_PORT"
               name="cometa_sonification_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= cometa_sonification_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="cometa_sonification_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="cometa_sonification_ROBOTID"
               name="cometa_sonification_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= cometa_sonification_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="cometa_sonification_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="cometa_sonification_ROLE"
               name="cometa_sonification_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= cometa_sonification_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="cometa_sonification_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="cometa_sonification_RENEWAL"
               name="cometa_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= cometa_sonification_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="cometa_sonification_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="cometa_sonification_DISABLEVOMS"
               name="cometa_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= cometa_sonification_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- GARUDA -->  
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="sonification_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= sonification_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='garuda'}">
                <c:set var="results_garuda" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_garuda=='true'}">
            <input type="checkbox" 
                   id="garuda_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="garuda"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="garuda_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="garuda"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="garuda_sonification_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="garuda_sonification_INFRASTRUCTURE"
               name="garuda_sonification_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="GARUDA" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="garuda_sonification_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="garuda_sonification_VONAME"
               name="garuda_sonification_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= garuda_sonification_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="garuda_sonification_TOPBDII">TopBDII</label>
    </td>    
    <td>
        <input type="text" 
               id="garuda_sonification_TOPBDII"
               name="garuda_sonification_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= garuda_sonification_TOPBDII %>" /> 
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"         
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="garuda_sonification_WMS">WSGRAM<em>*</em></label>
    </td>
    <td>          
        <c:forEach var="wms" items="<%= garuda_sonification_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_garudaWMS">
            <input type="text"                
                   name="garuda_sonification_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_garuda2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WSGRAM Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_garuda_sonification_WMS">
        <input type="text"                
               name="garuda_sonification_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_garuda" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WSGRAM Endopoint" />
        <img type="button" class="btnDel_garuda" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WSGRAM Endopoint" />
        </div>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="garuda_sonification_MYPROXYSERVER">MyProxyServer</label>
    </td>
    <td>
        <input type="text" 
               id="garuda_sonification_MYPROXYSERVER"
               name="garuda_sonification_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= garuda_sonification_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="garuda_sonification_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="garuda_sonification_ETOKENSERVER"
               name="garuda_sonification_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= garuda_sonification_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="garuda_sonification_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="garuda_sonification_PORT"
               name="garuda_sonification_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= garuda_sonification_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="garuda_sonification_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="garuda_sonification_ROBOTID"
               name="garuda_sonification_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= garuda_sonification_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="garuda_sonification_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="garuda_sonification_ROLE"
               name="garuda_sonification_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= garuda_sonification_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="garuda_sonification_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="garuda_sonification_RENEWAL"
               name="garuda_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= garuda_sonification_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="garuda_sonification_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="garuda_sonification_DISABLEVOMS"
               name="garuda_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= garuda_sonification_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>
    
<!-- GRIDIT -->  
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="sonification_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= sonification_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gridit'}">
                <c:set var="results_gridit" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_gridit=='true'}">
            <input type="checkbox" 
                   id="gridit_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="gridit"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="gridit_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="gridit"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%=renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="gridit_sonification_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="gridit_sonification_INFRASTRUCTURE"
               name="gridit_sonification_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="GRIDIT" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="gridit_sonification_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="gridit_sonification_VONAME"
               name="gridit_sonification_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= gridit_sonification_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="gridit_sonification_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="gridit_sonification_TOPBDII"
               name="gridit_sonification_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gridit_sonification_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"         
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="gridit_sonification_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>          
        <c:forEach var="wms" items="<%= gridit_sonification_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_griditWMS">
            <input type="text"                
                   name="gridit_sonification_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_gridit2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_gridit_sonification_WMS">
        <input type="text"                
               name="gridit_sonification_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_gridit" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_gridit" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="gridit_sonification_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gridit_sonification_MYPROXYSERVER"
               name="gridit_sonification_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gridit_sonification_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="gridit_sonification_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gridit_sonification_ETOKENSERVER"
               name="gridit_sonification_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gridit_sonification_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="gridit_sonification_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gridit_sonification_PORT"
               name="gridit_sonification_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= gridit_sonification_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="gridit_sonification_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gridit_sonification_ROBOTID"
               name="gridit_sonification_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gridit_sonification_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="gridit_sonification_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="gridit_sonification_ROLE"
               name="gridit_sonification_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= gridit_sonification_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="gridit_sonification_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="gridit_sonification_RENEWAL"
               name="gridit_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= gridit_sonification_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="gridit_sonification_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="gridit_sonification_DISABLEVOMS"
               name="gridit_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= gridit_sonification_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- GILDA -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="sonification_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= sonification_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gilda'}">
                <c:set var="results_gilda" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_gilda=='true'}">
            <input type="checkbox" 
                   id="gilda_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="gilda"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="gilda_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="gilda"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="GILDA_sonification_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="gilda_sonification_INFRASTRUCTURE"
               name="gilda_sonification_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="GILDA" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="gilda_sonification_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="gilda_sonification_VONAME"
               name="gilda_sonification_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= gilda_sonification_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="gilda_sonification_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="gilda_sonification_TOPBDII"
               name="gilda_sonification_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gilda_sonification_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="gilda_sonification_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>
        <c:forEach var="wms" items="<%= gilda_sonification_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_gildaWMS">
            <input type="text"                
                   name="gilda_sonification_WMS"
                   id="gilda_sonification_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_gilda2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_gilda_sonification_WMS">
        <input type="text" 
               id="gilda_sonification_WMS"
               name="gilda_sonification_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_gilda" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_gilda" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>                     
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="gilda_sonification_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gilda_sonification_MYPROXYSERVER"
               name="gilda_sonification_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gilda_sonification_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="gilda_sonification_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gilda_sonification_ETOKENSERVER"
               name="gilda_sonification_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gilda_sonification_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="gilda_sonification_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gilda_sonification_PORT"
               name="gilda_sonification_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= gilda_sonification_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="gilda_sonification_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gilda_sonification_ROBOTID"
               name="gilda_sonification_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gilda_sonification_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="gilda_sonification_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="gilda_sonification_ROLE"
               name="gilda_sonification_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= gilda_sonification_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="gilda_sonification_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="gilda_sonification_RENEWAL"
               name="gilda_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= gilda_sonification_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="gilda_sonification_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="gilda_sonification_DISABLEVOMS"
               name="gilda_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= gilda_sonification_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- EUMED -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="sonification_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= sonification_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='eumed'}">
                <c:set var="results_eumed" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_eumed=='true'}">
            <input type="checkbox" 
                   id="eumed_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="eumed"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="eumed_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="eumed"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="EUMED_sonification_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="eumed_sonification_INFRASTRUCTURE"
               name="eumed_sonification_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="EUMED" />        
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="eumed_sonification_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="eumed_sonification_VONAME"
               name="eumed_sonification_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= eumed_sonification_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="eumed_sonification_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="eumed_sonification_TOPBDII"
               name="eumed_sonification_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_sonification_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="eumed_sonification_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>
        <c:forEach var="wms" items="<%= eumed_sonification_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_eumedWMS">
            <input type="text"                
                   name="eumed_sonification_WMS"
                   id="eumed_sonification_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_eumed2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>        
        
        <div style="margin-bottom:4px;" class="cloned_eumed_sonification_WMS">
        <input type="text" 
               id="eumed_sonification_WMS"
               name="eumed_sonification_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_eumed" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_eumed" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>                     
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="eumed_sonification_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_sonification_MYPROXYSERVER"
               name="eumed_sonification_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_sonification_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="eumed_sonification_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_sonification_ETOKENSERVER"
               name="eumed_sonification_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_sonification_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="eumed_sonification_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_sonification_PORT"
               name="eumed_sonification_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= eumed_sonification_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="eumed_sonification_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="eumed_sonification_ROBOTID"
               name="eumed_sonification_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= eumed_sonification_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="eumed_sonification_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="eumed_sonification_ROLE"
               name="eumed_sonification_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= eumed_sonification_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="eumed_sonification_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="eumed_sonification_RENEWAL"
               name="eumed_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= eumed_sonification_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="eumed_sonification_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="eumed_sonification_DISABLEVOMS"
               name="eumed_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= eumed_sonification_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- GISELA -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="sonification_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= sonification_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gisela'}">
                <c:set var="results_gisela" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_gisela=='true'}">
            <input type="checkbox" 
                   id="gisela_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="gisela"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="gisela_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="gisela"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="GISELA_sonification_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="gisela_sonification_INFRASTRUCTURE"
               name="gisela_sonification_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="GISELA" />
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="gisela_sonification_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="gisela_sonification_VONAME"
               name="gisela_sonification_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= gisela_sonification_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="gisela_sonification_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="gisela_sonification_TOPBDII"
               name="gisela_sonification_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gisela_sonification_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="gisela_sonification_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>
        
        <c:forEach var="wms" items="<%= gisela_sonification_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_giselaWMS">
            <input type="text"                
                   name="gisela_sonification_WMS"
                   id="gisela_sonification_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_gisela2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>
        
        <div style="margin-bottom:4px;" class="cloned_gisela_sonification_WMS">
        <input type="text" 
               id="gisela_sonification_WMS"
               name="gisela_sonification_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_gisela" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_gisela" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>                     
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="gisela_sonification_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gisela_sonification_MYPROXYSERVER"
               name="gisela_sonification_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gisela_sonification_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="gisela_sonification_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gisela_sonification_ETOKENSERVER"
               name="gisela_sonification_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gisela_sonification_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="gisela_sonification_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gisela_sonification_PORT"
               name="gisela_sonification_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= gisela_sonification_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="gisela_sonification_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="gisela_sonification_ROBOTID"
               name="gisela_sonification_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= gisela_sonification_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="gisela_sonification_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="gisela_sonification_ROLE"
               name="gisela_sonification_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= gisela_sonification_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="gisela_sonification_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="gisela_sonification_RENEWAL"
               name="gisela_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= gisela_sonification_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="gisela_sonification_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="gisela_sonification_DISABLEVOMS"
               name="gisela_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= gisela_sonification_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- SAGRID -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the Infrastructure Acronym" />
   
        <label for="sonification_ENABLEINFRASTRUCTURE">Enabled<em>*</em></label>
    </td>    
    <td>
        <c:forEach var="enabled" items="<%= sonification_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='sagrid'}">
                <c:set var="results_sagrid" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_sagrid=='true'}">
            <input type="checkbox" 
                   id="sagrid_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="sagrid"
                   checked="checked"/>            
            </c:when>
            <c:otherwise>
            <input type="checkbox" 
                   id="sagrid_sonification_ENABLEINFRASTRUCTURE"
                   name="sonification_ENABLEINFRASTRUCTURES"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="48px;"
                   value="sagrid"/>
            </c:otherwise>
        </c:choose>
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Infrastructure Acronym" />
   
        <label for="SAGRID_sonification_INFRASTRUCTURE">Infrastructure<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="sagrid_sonification_INFRASTRUCTURE"
               name="sagrid_sonification_INFRASTRUCTURE"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value="SAGRID" />
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The VO name" />
   
        <label for="sagrid_sonification_VONAME">VOname<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="sagrid_sonification_VONAME"
               name="sagrid_sonification_VONAME"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= sagrid_sonification_VONAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The TopBDII hostname for accessing the Infrastructure" />
   
        <label for="sagrid_sonification_TOPBDII">TopBDII<em>*</em></label>
    </td>    
    <td>
        <input type="text" 
               id="sagrid_sonification_TOPBDII"
               name="sagrid_sonification_TOPBDII"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= sagrid_sonification_TOPBDII %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The WMProxy hostname for accessing the Infrastructure" />
   
        <label for="sagrid_sonification_WMS">WMS Endpoint<em>*</em></label>
    </td>
    <td>
        
        <c:forEach var="wms" items="<%= sagrid_sonification_WMS %>">
            <c:if test="${(!empty wms && wms!='N/A')}">
            <div style="margin-bottom:4px;" class="cloned_cached_sagridWMS">
            <input type="text"                
                   name="sagrid_sonification_WMS"
                   id="sagrid_sonification_WMS"
                   class="textfield ui-widget ui-widget-content ui-state-focus required"
                   size="50px;"               
                   value=" <c:out value="${wms}"/>" />
            <img type="button" class="btnDel_sagrid2" width="18"
                 src="<%= renderRequest.getContextPath()%>/images/remove.png" 
                 border="0" title="Remove a WMS Endopoint" />
            </div>
            </c:if>
        </c:forEach>
        
        <div style="margin-bottom:4px;" class="cloned_sagrid_sonification_WMS">
        <input type="text" 
               id="sagrid_sonification_WMS"
               name="sagrid_sonification_WMS"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;"               
               value=" N/A"/>
        <img type="button" id="adding_WMS_sagrid" width="18"
             src="<%= renderRequest.getContextPath()%>/images/plus_orange.png" 
             border="0" title="Add a new WMS Endopoint" />
        <img type="button" class="btnDel_sagrid" width="18"
             src="<%= renderRequest.getContextPath()%>/images/remove.png" 
             border="0" title="Remove a WMS Endopoint" />
        </div>                     
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The MyProxyServer hostname for requesting long-term grid proxies" />
   
        <label for="sagrid_sonification_MYPROXYSERVER">MyProxyServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="sagrid_sonification_MYPROXYSERVER"
               name="sagrid_sonification_MYPROXYSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= sagrid_sonification_MYPROXYSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer hostname to be contacted for requesting grid proxies" />
   
        <label for="sagrid_sonification_ETOKENSERVER">eTokenServer<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="sagrid_sonification_ETOKENSERVER"
               name="sagrid_sonification_ETOKENSERVER"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= sagrid_sonification_ETOKENSERVER %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The eTokenServer port" />
   
        <label for="sagrid_sonification_PORT">Port<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="sagrid_sonification_PORT"
               name="sagrid_sonification_PORT"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= sagrid_sonification_PORT %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The certificate serial number to generate proxies" />
   
        <label for="sagrid_sonification_ROBOTID">Serial Number<em>*</em></label>
    </td>
    <td>
        <input type="text" 
               id="sagrid_sonification_ROBOTID"
               name="sagrid_sonification_ROBOTID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= sagrid_sonification_ROBOTID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The FQANs for the grid proxy (if any)" />
   
        <label for="sagrid_sonification_ROLE">Role</label>
    </td>
    <td>
        <input type="text" 
               id="sagrid_sonification_ROLE"
               name="sagrid_sonification_ROLE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= sagrid_sonification_ROLE %>" />            
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Enable the creation of a long-term proxy to a MyProxy Server" />
   
        <label for="sagrid_sonification_RENEWAL">Proxy Renewal</label>
    </td>
    <td>
        <input type="checkbox" 
               id="sagrid_sonification_RENEWAL"
               name="sagrid_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               <%= sagrid_sonification_RENEWAL %> 
               value="enableRENEWAL" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="Disable the creation of a VOMS proxy" />
   
        <label for="sagrid_sonification_DISABLEVOMS">Disable VOMS</label>
    </td>
    <td>
        <input type="checkbox" 
               id="sagrid_sonification_DISABLEVOMS"
               name="sagrid_sonification_OPTIONS"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               <%= sagrid_sonification_DISABLEVOMS %>
               size="50px;" 
               value="disableVOMS" />
    </td>    
</tr>

<!-- LAST -->
<tr></tr>
<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The ApplicationID for SONIFICATION" />
   
        <label for="sonification_APPID">AppID<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="sonification_APPID"
               name="sonification_APPID"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="15px;" 
               value=" <%= sonification_APPID %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The output path of the server's file-system where download results" />
   
        <label for="sonification_OUTPUT_PATH">Output Path<em>*</em></label> 
    </td>
    <td>
        <input type="text" 
               id="sonification_OUTPUT_PATH"
               name="sonification_OUTPUT_PATH"
               class="textfield ui-widget ui-widget-content ui-state-focus required"
               size="50px;" 
               value=" <%= sonification_OUTPUT_PATH %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Application Software TAG" />
   
        <label for="sonification_SOFTWARE">SoftwareTAG</label>
    </td>
    <td>
        <input type="text" 
               id="sonification_SOFTWARE"
               name="sonification_SOFTWARE"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= sonification_SOFTWARE %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The Tracking DB Server Hostname" />
   
        <label for="TRACKING_DB_HOSTNAME">HostName</label>
    </td>
    <td>
        <input type="text" 
               id="TRACKING_DB_HOSTNAME"
               name="TRACKING_DB_HOSTNAME"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= TRACKING_DB_HOSTNAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The username credential for login the Tracking DB" />
   
        <label for="TRACKING_DB_USERNAME">UserName</label>
    </td>
    <td>
        <input type="text" 
               id="TRACKING_DB_USERNAME"
               name="TRACKING_DB_USERNAME"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= TRACKING_DB_USERNAME %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The password credential for login  the Tracking DB" />
   
        <label for="TRACKING_DB_PASSWORD">Password</label>
    </td>
    <td>
        <input type="password" 
               id="TRACKING_DB_PASSWORD"
               name="TRACKING_DB_PASSWORD"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= TRACKING_DB_PASSWORD %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The SMTP Server for sending notification" />
   
        <label for="SMTP_HOST">SMTP</label>
    </td>
    <td>
        <input type="text" 
               id="SMTP_HOST"
               name="SMTP_HOST"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= SMTP_HOST %>" />    
    </td>    
</tr>

<tr>    
    <td width="150">
    <img width="30" 
         align="absmiddle"
         src="<%= renderRequest.getContextPath()%>/images/question.png"  
         border="0" title="The email address for sending notification" />
   
        <label for="Sender">Sender</label>
    </td>
    <td>
        <input type="text" 
               id="SENDER_MAIL"
               name="SENDER_MAIL"
               class="textfield ui-widget ui-widget-content ui-state-focus"
               size="50px;" 
               value=" <%= SENDER_MAIL %>" />
    </td>    
</tr>

<!-- Buttons -->
<tr>            
    <tr><td>&nbsp;</td></tr>
    <td align="left">    
    <input type="image" src="<%= renderRequest.getContextPath()%>/images/save.png"
           width="50"
           name="Submit" title="Save the portlet settings" />        
    </td>
</tr>  

</table>
<br/>
<div id="pageNavPosition" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">   
</div>
</fieldset>
           
<script type="text/javascript">
    var pager = new Pager('results', 13); 
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
</script>
</form>
