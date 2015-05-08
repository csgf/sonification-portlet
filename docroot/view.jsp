<%
/**************************************************************************
Copyright (c) 2011-2014:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy
    
See http://www.infn.it and and http://www.consorzio-cometa.it for details 
on the copyright holders.
    
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
**************************************************************************/
%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portal.model.Company" %>
<%@ page import="javax.portlet.*" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<%
  Company company = PortalUtil.getCompany(request);
  String gateway = company.getName();
%>

<jsp:useBean id="GPS_table" class="java.lang.String" scope="request"/>
<jsp:useBean id="GPS_queue" class="java.lang.String" scope="request"/>

<jsp:useBean id="cometa_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_sonification_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="garuda_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_sonification_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gridit_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_sonification_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gilda_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_sonification_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="eumed_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_sonification_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gisela_sonification_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_sonification_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_sonification_WMS" class="java.lang.String" scope="request"/>
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
<jsp:useBean id="sagrid_sonification_WMS" class="java.lang.String" scope="request"/>
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
    
    var latlng2markers = [];
    var icons = [];
    
    icons["plus"] = new google.maps.MarkerImage(
          '<%= renderRequest.getContextPath()%>/images/plus_new.png',
          new google.maps.Size(16,16),
          new google.maps.Point(0,0),
          new google.maps.Point(8,8));
    
    icons["minus"] = new google.maps.MarkerImage(
          '<%= renderRequest.getContextPath()%>/images/minus_new.png',
          new google.maps.Size(16,16),
          new google.maps.Point(0,0),
          new google.maps.Point(8,8));
    
    icons["ce"] = new google.maps.MarkerImage(
            '<%= renderRequest.getContextPath()%>/images/ce-run.png',
            new google.maps.Size(16,16),
            new google.maps.Point(0,0),
            new google.maps.Point(8,8));
    
    function hideMarkers(markersMap,map) 
    {
            for( var k in markersMap) 
            {
                if (markersMap[k].markers.length >1) {
                    var n = markersMap[k].markers.length;
                    var centerMark = new google.maps.Marker({
                        title: "Coordinates:" + markersMap[k].markers[0].getPosition().toString(),
                        position: markersMap[k].markers[0].getPosition(),
                        icon: icons["plus"]
                    });
                    for ( var i=0 ; i<n ; i++ ) 
                        markersMap[k].markers[i].setVisible(false);
                    
                    centerMark.setMap(map);
                    google.maps.event.addListener(centerMark, 'click', function() {
                        var markersMap = latlng2markers;
                        var k = this.getPosition().toString();
                        var visibility = markersMap[k].markers[0].getVisible();
                        if (visibility == false ) {
                            splitMarkersOnCircle(markersMap[k].markers,
                            markersMap[k].connectors,
                            this.getPosition(),
                            map
                        );
                            this.setIcon(icons["minus"]);
                        }
                        else {
                            var n = markersMap[k].markers.length;
                            for ( var i=0 ; i<n ; i++ ) {
                                markersMap[k].markers[i].setVisible(false);
                                markersMap[k].connectors[i].setMap(null);
                            }
                            markersMap[k].connectors = [];
                            this.setIcon(icons["plus"]);
                        }
                    });
                }
            }
    }
    
    function splitMarkersOnCircle(markers, connectors, center, map) 
    {
            var z = map.getZoom();
            var r = 64.0 / (z*Math.exp(z/2));
            var n = markers.length;
            var dtheta = 2.0 * Math.PI / n
            var theta = 0;
            
            for ( var i=0 ; i<n ; i++ ) 
            {
                var X = center.lat() + r*Math.cos(theta);
                var Y = center.lng() + r*Math.sin(theta);
                markers[i].setPosition(new google.maps.LatLng(X,Y));
                markers[i].setVisible(true);
                theta += dtheta;
                
                var line = new google.maps.Polyline({
                    path: [center,new google.maps.LatLng(X,Y)],
                    clickable: false,
                    strokeColor: "#0000ff",
                    strokeOpacity: 1,
                    strokeWeight: 2
                });
                
                line.setMap(map);
                connectors.push(line);
            }
    }
    
    function updateAverage(name) {
        
        $.getJSON('<portlet:resourceURL><portlet:param name="action" value="get-ratings"/></portlet:resourceURL>&sonification_CE='+name,
        function(data) {                                               
            console.log(data.avg);
            $("#fake-stars-on").width(Math.round(parseFloat(data.avg)*20)); // 20 = 100(px)/5(stars)
            $("#fake-stars-cap").text(new Number(data.avg).toFixed(2) + " (" + data.cnt + ")");
        });                
    }
    
    // Create the Google Map JavaScript APIs V3
    function initialize(lat, lng, zoom) {
        console.log(lat);
        console.log(lng);
        console.log(zoom);
        
        var myOptions = {
            zoom: zoom,
            center: new google.maps.LatLng(lat, lng),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        
        var map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);  
        var image = new google.maps.MarkerImage('<%= renderRequest.getContextPath() %>/images/ce-run.png');
        
        var strVar="";
        strVar += "<table>";
        strVar += "<tr>";
        strVar += "<td>";
        strVar += "Vote the resource availability";
        strVar += "<\/td>";
        strVar += "<\/tr>";
        strVar += "<tr><td>\&nbsp;\&nbsp;";
        strVar += "<\/td><\/tr>";
        
        strVar += "<tr>";
        strVar += "<td>";
        strVar += "Rating: <span id=\"stars-cap\"><\/span>";
        strVar += "<div id=\"stars-wrapper2\">";
        strVar += "<select name=\"selrate\">";
        strVar += "<option value=\"1\">Very poor<\/option>";
        strVar += "<option value=\"2\">Not that bad<\/option>";
        strVar += "<option value=\"3\" selected=\"selected\">Average<\/option>";
        strVar += "<option value=\"4\">Good<\/option>";
        strVar += "<option value=\"5\">Perfect<\/option>";
        strVar += "<\/select>";
        strVar += "<\/div>";
        strVar += "<\/td>";
        strVar += "<\/tr>";

        strVar += "<tr>";        
        strVar += "<td>";
        strVar += "Average: <span id=\"fake-stars-cap\"><\/span>";
        strVar += "";
        strVar += "<div id=\"fake-stars-off\" class=\"stars-off\" style=\"width:100px\">";
        strVar += "<div id=\"fake-stars-on\" class=\"stars-on\"><\/div>";
        strVar += "";
        strVar += "<\/div>";
        strVar += "<\/td>";
        strVar += "<\/tr>";
        strVar += "<\/table>";
    
        var data = <%= GPS_table %>;
        var queues = <%= GPS_queue %>;
        
        var infowindow = new google.maps.InfoWindow();
        google.maps.event.addListener(infowindow, 'closeclick', function() {
            this.setContent('');
        });
        
        var infowindowOpts = { 
            maxWidth: 200
        };
               
       infowindow.setOptions(infowindowOpts);       
       
       var markers = [];
       for( var key in data ){
                        
            var LatLong = new google.maps.LatLng(parseFloat(data[key]["LAT"]), 
                                                 parseFloat(data[key]["LNG"]));                    
            
            // Identify locations on the map
            var marker = new google.maps.Marker ({
                animation: google.maps.Animation.DROP,
                position: LatLong,
                icon: image,
                title : key
            });    
  
            // Add the market to the map
            marker.setMap(map);
            
            var latlngKey=marker.position.toString();
            if ( latlng2markers[latlngKey] == null )
                latlng2markers[latlngKey] = {markers:[], connectors:[]};
            
            latlng2markers[latlngKey].markers.push(marker);
            markers.push(marker);
        
            google.maps.event.addListener(marker, 'click', function() {
             
            var ce_hostname = this.title;
            
            google.maps.event.addListenerOnce(infowindow, 'domready', function() {
                                        
                    $("#stars-wrapper2").stars({
                        cancelShow: false, 
                        oneVoteOnly: true,
                        inputType: "select",
                        callback: function(ui, type, value)
                        { 
                            $.getJSON('<portlet:resourceURL><portlet:param name="action" value="set-ratings"/></portlet:resourceURL>' +
                                '&sonification_CE=' + ce_hostname + 
                                '&vote=' + value);
                            
                            updateAverage(ce_hostname);                      
                        }
                    });
                    
                    updateAverage(ce_hostname);               
                });              
                                                
                infowindow.setContent('<h3>' + ce_hostname + '</h3><br/>' + strVar);
                infowindow.open(map, this);
                                           
                var CE_queue = (queues[ce_hostname]["QUEUE"]);
                $('#sonification_CE').val(CE_queue);
                
                marker.setMap(map);
            }); // function                             
        } // for
        
        hideMarkers(latlng2markers, map);
        var markerCluster = new MarkerClusterer(map, markers, {maxZoom: 3, gridSize: 20});
    }    
</script>

<script type="text/javascript">  
    var EnabledInfrastructure = null;           
    var infrastructures = new Array();  
    var i = 0;    
    
    <c:forEach items="<%= sonification_ENABLEINFRASTRUCTURE %>" var="current">
    <c:choose>
    <c:when test="${current!=null}">
        infrastructures[i] = '<c:out value='${current}'/>';       
        i++;  
    </c:when>
    </c:choose>
    </c:forEach>
        
    for (var i = 0; i < infrastructures.length; i++) {
       console.log("Reading array = " + infrastructures[i]);
       if (infrastructures[i]=="cometa") EnabledInfrastructure='cometa';
       if (infrastructures[i]=="garuda") EnabledInfrastructure='garuda';
       if (infrastructures[i]=="gridit") EnabledInfrastructure='gridit';
       if (infrastructures[i]=="gilda")  EnabledInfrastructure='gilda';
       if (infrastructures[i]=="eumed")  EnabledInfrastructure='eumed';
       if (infrastructures[i]=="gisela") EnabledInfrastructure='gisela';
       if (infrastructures[i]=="sagrid") EnabledInfrastructure='sagrid';
    }
    
    var NMAX = infrastructures.length;
    //console.log (NMAX);
    
    $(document).ready(function() 
    {   
        // Toggling the hidden div for the first time.        
        if ($('#divToToggle').css('display') == 'block')
            $('#divToToggle').toggle();
        
        var lat; var lng; var zoom;
        var found=0;
        
        if (parseInt(NMAX)>1) { 
            console.log ("More than one infrastructure has been configured!");
            $("#error_infrastructure img:last-child").remove();
            $('#error_infrastructure').append("<img width='70' src='<%= renderRequest.getContextPath()%>/images/world.png' border='0'> More than one infrastructure has been configured!");
            lat=19;lng=14;zoom=2; found=1;
        } else if (EnabledInfrastructure=='cometa') {
            console.log ("Start up: enabled the COMETA Grid Infrastructure!");
            $('#cometa_sonification_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#cometa_enabled').show();            
            $('#gridit_enabled').hide();
            $('#gilda_enabled').hide();
            $('#eumed_enabled').hide();            
            $('#gisela_enabled').hide();*/
            //$('#error_infrastructure').hide();
            lat=37;lng=14;zoom=7;
            found=1;
        } else if (EnabledInfrastructure=='garuda') {
            console.log ("Start up: enabling garuda!");
            $('#garuda_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=29.15;lng=77.41;zoom=4;
            found=1;
        } else if (EnabledInfrastructure=='gridit') {        
            console.log ("Start up: enabled the Italian Grid Infrastructure!");
            $('#gridit_sonification_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gridit_enabled').show();            
            $('#cometa_enabled').hide();
            $('#gilda_enabled').hide();
            $('#eumed_enabled').hide();
            $('#gisela_enabled').hide();*/
            //$('#error_infrastructure').hide();     
            lat=42;lng=12;zoom=5;
            found=1;            
        } else if (EnabledInfrastructure=='eumed') {       
            console.log ("Start up: enabled the Mediterranen Grid Infrastructure!");
            $('#eumed_sonification_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#eumed_enabled').show();            
            $('#gridit_enabled').hide();
            $('#cometa_enabled').hide();
            $('#gilda_enabled').hide();
            $('#gisela_enabled').hide();*/
            //$('#error_infrastructure').hide();            
            lat=34;lng=20;zoom=4;
            found=1;
        } else if (EnabledInfrastructure=='gilda') {
            console.log ("Start up: enabled the Indian Grid Infrastructure!");
            $('#gilda_sonification_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gilda_enabled').show();
            $('#eumed_enabled').hide();            
            $('#gridit_enabled').hide();
            $('#cometa_enabled').hide();            
            $('#gisela_enabled').hide();*/
            //$('#error_infrastructure').hide();            
            lat=42;lng=12;zoom=5;
            found=1;    
        } else if (EnabledInfrastructure=='gisela') {        
            console.log ("Start up: enabled the Latin America Grid Infrastructure!");
            $('#gisela_sonification_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gisela_enabled').show();            
            $('#cometa_enabled').hide();
            $('#gridit_enabled').hide();
            $('#eumed_enabled').hide();
            $('#gilda_enabled').hide();*/
            //$('#error_infrastructure').hide();            
            lat=2;lng=-36;zoom=2;
            found=1;            
        } else if (EnabledInfrastructure=='sagrid') {
            console.log ("Start up: enabled the sagrid VO!");
            $('#sagrid_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gisela_enabled').show();            
            $('#cometa_enabled').hide();
            $('#gridit_enabled').hide();
            $('#eumed_enabled').hide();
            $('#gilda_enabled').hide();*/
            //$('#error_infrastructure').hide();
            lat=-16;lng=-24;zoom=2;
            found=1;
        }                 
                
        if (found==0) { 
            console.log ("None of the grid infrastructures have been configured!");
            $("#error_infrastructure img:last-child").remove();
            $('#error_infrastructure').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> None of the available grid infrastructures have been configured!");
        }                
        
        var accOpts = {
            change: function(e, ui) {                       
                $("<div style='width:650px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;'>").addClass("notify ui-corner-all").text(ui.newHeader.find("a").text() +
                    " was activated... ").appendTo("#error_message").fadeOut(2500, function(){ $(this).remove(); });               
                // Get the active option                
                var active = $("#accordion").accordion("option", "active");
                if (active==1) initialize(lat, lng, zoom);
            },
            autoHeight: false
        };
        
        // Create the accordions
        //$("#accordion").accordion({ autoHeight: false });
        $("#accordion").accordion(accOpts);
        
        // Create the sliders
        $( "#slider-range-min" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 1,
            min: 0,
            max: 255,
            slide: function( event, ui ) {
                $( "#range_min" ).val( ui.value );
                $( "input[type=hidden][name='range_min']").val( ui.value);
            }
        });
        $( "#range_min" ).val( $( "#slider-range-min" ).slider( "value" ) );
        
        // Create the sliders
        $( "#slider-range-max" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 255,
            min: 0,
            max: 255,
            slide: function( event, ui ) {
                $( "#range_max" ).val( ui.value );
                $( "input[type=hidden][name='range_max']").val( ui.value);
            }
        });
        $( "#range_max" ).val( $( "#slider-range-max" ).slider( "value" ) );
        
        // Create the sliders
        $( "#slider-time" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 120,
            min: 0,
            max: 200,
            slide: function( event, ui ) {
                $( "#time" ).val( ui.value );
                $( "input[type=hidden][name='time']").val( ui.value);
            }
        });
        $( "#time" ).val( $( "#slider-time" ).slider( "value" ) );
          
        // Validate input form
        //$('#commentForm').validate();
        $('#commentForm').validate({
            rules: {
                range_min: {
                    required: true,
                    min: 0,
                    max: 255                    
                },
                range_max: {                    
                    required: true,
                    range: [ 0, 255 ]
                }
            }                                  
        });
        
        // Check file input size with jQuery (Max. 2.5MB)
        $('input[type=file][name=\'sonification_file\']').bind('change', function() {
            if (this.files[0].size/1000 > 25600) {     
                // Remove the img and text (if any)
                $("#error_message img:last-child").remove();
                $("#error_message").empty();
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> The user demo file must be less than 2.5MB");
                $("#error_message").css({"color":"red","font-size":"14px"});
                // Removing the input file
                $('input[type=\'file\'][name=\'sonification_file\']').val('');
                return false;
            }           
        });                
        
        $("#commentForm").bind('submit', function() {        
                        
            var flag=true;
            // Remove the img and text error (if any)
            $("#error_message img:last-child").remove();
            $("#error_message").empty();
            
            // Check if the range of (range_min and range_max) params are ok.
            if ( parseInt($("#range_max").val()) < parseInt($("#range_min").val()) ) {                
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> Wrong parameters range");
                $("#error_message").css({"color":"red","font-size":"14px"});                   
                return false;
                flag=false;
            }
            
            // Check if the uploaded file is a .txt file.
            if ($('input:checked[type=\'radio\'][name=\'sonification_demo\']').val() == "sonification_ASCII")
            {
                var ext = ($('input[type=file][name=\'sonification_file\']').val().split('.').pop().toLowerCase());                
                if($.inArray(ext, ['txt']) == -1) {                    
                    $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> Invalid file format");
                    $("#error_message").css({"color":"red","font-size":"14px"});                   
                    return false;
                    flag=false;
                }                
            } else
                      
            // Check if the input settings are valid before to
            // display the warning message.
            if ( (($('input:checked[type=\'radio\'][name=\'sonification_demo\']').val() == "sonification_ASCII") &&
                 ($('input[type=file][name=sonification_file]').val() == "")) ||
                
                 (($('input:checked[type=\'radio\'][name=\'sonification_demo\']').val() == "sonification_TEXTAREA") &&
                 $('#sonification_textarea').val() == "") ||
                
                 (($('input:checked[type=\'radio\'][name=\'sonification_demo\']').val() != "sonification_ASCII") &&
                 ($('input:checked[type=\'radio\'][name=\'sonification_demo\']').val() != "sonification_TEXTAREA")) ) 
            {            
                // Display the warning message  
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> You missed many settings! They have been highlighted below.");
                $("#error_message").css({"color":"red","font-size":"14px"});
                flag=false;
            }
            
            if (flag) {
                $("#error_message").css({"color":"red","font-size":"14px", "font-family": "Tahoma,Verdana,sans-serif,Arial"});                
                $('#error_message').append("<img width='30' src='<%= renderRequest.getContextPath()%>/images/button_ok.png' border='0'> Submission in progress...")(30000, function(){ $(this).remove(); });
            }            
        });
                   
        // Roller
        $('#sonification_footer').rollchildren({
            delay_time         : 3000,
            loop               : true,
            pause_on_mouseover : true,
            roll_up_old_item   : true,
            speed              : 'slow'   
        });
        
        $("#stars-wrapper1").stars({
            cancelShow: false,
            captionEl: $("#stars-cap"),
            callback: function(ui, type, value)
            {
                $.getJSON("ratings.php", {rate: value}, function(json)
                {                                        
                    $("#fake-stars-on").width(Math.round( $("#fake-stars-off").width()/ui.options.items*parseFloat(json.avg) ));
                    $("#fake-stars-cap").text(json.avg + " (" + json.votes + ")");
                });
            }
         });                  
         
    });

    function enable_SonificationDemo(f) {
        if ($('input:checked[type=\'radio\'][name=\'sonification_demo\']',f).val() == "sonification_ASCII") {
            // Enabling the uploading of the user ASCII file
            $('input[type=\'file\'][name=\'sonification_file\']').removeAttr('disabled');
            // Disabling the specification of the sonification text via textarea
            $('#sonification_textarea').attr('disabled','disabled');
        } else {        
            // Disabling the uploading of the user file
            $('input[type=\'file\'][name=\'sonification_file\']').attr('disabled','disabled');
            // Enabling the specification of the sonification text via textarea
            $('#sonification_textarea').val('');
            $('#sonification_textarea').removeAttr('disabled');
        }     
    }
    
    function DisableElement() {
        if (($("select option:selected").val()=="ASCIItoSPHERE") ||
             ($("select option:selected").val()=="DATASETtoWAVE"))            
            {
                // Firstly remove the selection (if any)
                $("#EnableMIDIAnalysis").attr("checked", false);
                // Lastly disable the checkbox
                $("#EnableMIDIAnalysis").attr("disabled", true);
            }
        else 
            $("#EnableMIDIAnalysis").removeAttr("disabled");
                
        if ($('#divToToggle').css('display') == 'block')
            $('#divToToggle').toggle();                
    }
    
    function toggleAndChangeText() {        
        
        if ($('#divToToggle').css('display') == 'none') {
            // Collapse the div
            $('#aTag').html('Advanced Settings &#9658');
            if ($("select option:selected").val()=="ASCIItoMIDI") {
                console.log("Configure advanced settings for the algorithm ASCIItoMIDI");               
                $('#ASCIISETtoMIDI_Toggle').show();
                $('#DATASETtoMIDI_Toggle').hide();
                $('#DATASETtoWAVE_Toggle').hide();
            }
            if ($("select option:selected").val()=="DATASETtoMIDI") {
                console.log("Configure advanced settings for the algorithm DATASETtoMIDI");
                $('#DATASETtoMIDI_Toggle').show();
                $('#ASCIISETtoMIDI_Toggle').hide();  
                $('#DATASETtoWAVE_Toggle').hide();
            }
            if ($("select option:selected").val()=="ASCIItoSPHERE") {
                console.log("Configure advanced settings for the algorithm ASCIItoSHERE");
                $('#DATASETtoMIDI_Toggle').hide();
                $('#ASCIISETtoMIDI_Toggle').hide();
                $('#DATASETtoWAVE_Toggle').hide();
            }
            if ($("select option:selected").val()=="DATASETtoWAVE") {                
                console.log("Configure advanced settings for the algorithm DATASETtoWAVE");
                $('#DATASETtoWAVE_Toggle').show();
                $('#DATASETtoMIDI_Toggle').hide();
                $('#ASCIISETtoMIDI_Toggle').hide();
            }
        } else 
            // Expand the div
            $('#aTag').html('Advanced Settings &#9660');
        
        $('#divToToggle').toggle();
}
    
</script>

<br/>
<form enctype="multipart/form-data" 
      id="commentForm" 
      action="<portlet:actionURL><portlet:param name="ActionEvent" 
      value="SUBMIT_SONIFICATION_PORTLET"/></portlet:actionURL>"      
      method="POST">

<fieldset>
<legend>Data Sonification Input Form</legend>
<div style="margin-left:15px" id="error_message"></div>

<!-- Accordions -->
<div id="accordion" style="width:650px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
<h3><a href="#">
    <img width="32" src="<%=renderRequest.getContextPath()%>/images/glass_numbers_1.png" />
    <b>Display Settings</b>
    </a>
</h3>
<div> <!-- Inizio primo accordion -->
<p>The current SONIFICATION portlet has been configured for:</p>
<table id="results" border="0" width="600">
    
<!-- COMETA -->
<tr></tr>
<tr>
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
                   name="cometa_sonification_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The COMETA Grid Infrastructure
            
            <!--img width="20"
                 id="cometa_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- GARUDA -->
<tr></tr>
<tr>
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
                   name="garuda_sonification_ENABLEINFRASTRUCTURE"
                   size="55px;" 
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The GARUDA Infrastructure
        
            <!--img width="20"
                 id="gridit_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>       
    </td>
</tr>

<!-- GRIDIT -->
<tr></tr>
<tr>
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
                   name="gridit_sonification_ENABLEINFRASTRUCTURE"
                   size="55px;" 
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The Italian and the France Grid (France Grilles) Infrastructures
        
            <!--img width="20"
                 id="gridit_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>       
    </td>
</tr>

<!-- GILDA -->
<tr></tr>
<tr>
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
                   name="gilda_sonification_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The GILDA t-Infrastructure
        
            <!--img width="20"
                 id="gilda_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- EUMED -->
<tr></tr>
<tr>
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
                   name="eumed_sonification_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The Mediterranean Grid Infrastructure
        
            <!--img width="20"
                 id="eumed_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- GISELA -->
<tr></tr>
<tr>
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
                   name="gisela_sonification_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The Latin America Grid Infrastructure
        
            <!--img width="20"
                 id="gisela_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>                
    </td>
</tr>

<!-- SAGRID -->
<tr></tr>
<tr>
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
                   name="sagrid_sonification_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The South African Grid Infrastructure
        
            <!--img width="20"
                 id="sagrid_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>                
    </td>
</tr>

</table>
<br/>
<div style="margin-left:15px" 
     id="error_infrastructure" 
     style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px; display:none;">    
</div>
<br/>

<p align="center">
<img width="120" src="<%=renderRequest.getContextPath()%>/images/separatore.gif"/>
</p>

<p align="justify">
Instructions for users:<br/>
~ This portlet implements the sonification of data by means of sound signals.
  Generally speaking any sonification procedure is a mathematical mapping from a certain data set
  (numbers, strings, images, ...) to a sound string.<br/>  
  <img width="20" src="<%=renderRequest.getContextPath()%>/images/help.png" title="Read the Help"/>
   For further details, please click
   <a href="<portlet:renderURL portletMode='HELP'><portlet:param name='action' value='./help.jsp' />
            </portlet:renderURL>" >here</a>
   <br/><br/>
</p>

  Inputs:<br/>
~ The portlet takes as input an ASCII data (uploaded via file or text area);<br/>
~ The sonification algorithm.<br/><br/>

Each run will produce:<br/>
~ std.out: the standard output file;<br/>
~ std.err: the standard error file;<br/>
~ .wav: the MIDI file produced during the sonification process;<br/>
~ *.png: a list of 3D rendering images produced with <a href="http://www.povray.org/">POVRay</a> if enabled.<br/><br/>

<table id="results" border="0">
    <tr>
        <td>
        <u>MIDI Toolbox</u><br/>
        Enabling this feature, a compilation of functions for analyzing and visualizing MIDI files
        in the Matlab computing environment will be used.<br/><br/>
        Eerola, T. & Toiviainen, P. (2004). MIDI Toolbox: MATLAB Tools for Music Research.
        University of Jyväskylä: Kopijyvä, Jyväskylä, Finland.
        </td>
        <td width="100">
            <a href="https://www.jyu.fi/hum/laitokset/musiikki/en/research/coe/materials/miditoolbox">
            <img width="85" src="<%=renderRequest.getContextPath()%>/images/midilogo_medium.gif"/>
            </a>
        </td>
    </tr>
</table>

<br/>
For further information, please refer to the output.README file produced during the run.
<br/><br/>

<p>If you need to change some preferences, please contact the
<a href="mailto:credentials-admin@ct.infn.it?Subject=Request for Technical Support [<%=gateway%> Science Gateway]&Body=Describe Your Problems&CC=sg-licence@ct.infn.it"> administrator</a>
</p>

<liferay-ui:ratings
    className="<%= it.infn.ct.sonification.Sonification.class.getName()%>"
    classPK="<%= request.getAttribute(WebKeys.RENDER_PORTLET).hashCode()%>" />
    
<!--div id="pageNavPosition"></div-->
</div> <!-- Fine Primo Accordion -->

<h3><a href="#">
    <img width="32" src="<%=renderRequest.getContextPath()%>/images/glass_numbers_2.png" />
    <b>Worldwide Software Distribution</b>
    </a>
</h3>           
<div> <!-- Inizio Terzo accordion -->            
    <p>See with the Google Map API where the SONIFICATION software has been successfully installed.</p>
    <p>Select the GPS location of the grid resource where you want run your demo
    <u>OR, BETTER,</u> let the Science Gateway to choose the best one for you!</p>
    <p>This option is <u>NOT SUPPORTED</u> in multi-infrastructure mode!</p>

    <table border="0">
        <tr>
            <td><legend>Legend</legend></td>
            <td>&nbsp;<img src="<%=renderRequest.getContextPath()%>/images/plus_new.png"/></td>
            <td>&nbsp;Split close sites&nbsp;</td>
        
            <td><img src="<%=renderRequest.getContextPath()%>/images/minus_new.png"/></td>
            <td>&nbsp;Unsplit close sites&nbsp;</td>
            
            <td><img src="<%=renderRequest.getContextPath()%>/images/ce-run.png"/></td>
            <td>&nbsp;Computing resource&nbsp;</td>
        </tr>    
        <tr><td>&nbsp;</td></tr>
    </table>

    <legend>
        <div id="map_canvas" style="width:570px; height:600px;"></div>
    </legend>

    <input type="hidden" 
           name="sonification_CE" 
           id="sonification_CE"
           size="25px;" 
           class="textfield ui-widget ui-widget-content"
           value=""/>                  
</div> <!-- Fine Secondo Accordion -->        

<h3><a href="#">
    <img width="32" src="<%=renderRequest.getContextPath()%>/images/glass_numbers_3.png" />
    <b>Specify your Input Settings</b>
    </a>
</h3>           
<div> <!-- Inizio Quarto accordion -->
<p>Please, paste the text you want to sonify in the textarea below, <u>OR</u> upload it in an ASCII file</p>
<table border="0" width="500">
    <tr>
        <td width="160">
            <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Upload your data to be processed as ASCII file"/>
            
            <input type="radio" 
                   name="sonification_demo"
                   id="sonification_demo"
                   value="sonification_ASCII"
                   class="required"
                   onchange="enable_SonificationDemo(this.form);"/>Upload your ASCII file (Max 2,5MB) <em>*</em>
            
             <input type="file" name="sonification_file" width="500" class="required" 
                    style="padding-left: 1px; border-style: solid; border-color: gray; border-width: 1px; padding-left: 1px;"
                    disabled="disabled"/>
        </td>
    </tr>
    
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Use the texta-area to insert the data to be processed"/>
        
            <input type="radio" 
                   name="sonification_demo" 
                   id="sonification_demo"
                   value="sonification_TEXTAREA" 
                   class="required"
                   onchange="enable_SonificationDemo(this.form);"/>Insert here the text to be sonified <em>*</em>
        </td>
    <tr>
        <td>            
            <textarea id="sonification_textarea" 
                      name="sonification_textarea"
                      style="padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;"
                      class="required"
                      disabled="disabled"
                      rows="5" cols="70">                
            </textarea>            
        </td>
    </tr>
    
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Choose a description for your run "/>
        
        <label for="sonification_desc">Description</label>
                      
        <input type="text"                
               id="sonification_desc"
               name="sonification_desc"
               style="padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;"
               value="Please, insert here a description for your job"
               size="57" />
        </td>           
    </tr>
    
    <tr><td><br/></td></tr>
        
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Please, select the algorithm to be used"/>
        
        <label for="sonificationtype">Algorithm </label><em>*</em>            
            
        <select name="sonificationtype" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;"
                onChange="DisableElement();">                                
            
        <option value="ASCIItoMIDI">From ASCII text to MIDI file</option>        
        <option value="DATASETtoMIDI">From DataSet to MIDI file</option>        
        <option value="DATASETtoWAVE">From DataSet to Waveform</option>
        <option value="ASCIItoSPHERE">From ASCII text to Spheres</option>
        </select>
        </td>                
    </tr>
    
    <tr><td><br/></td></tr>
    
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Enable MIDI analysis with MIDIToolBox"/>
                
            <input type="checkbox" 
               name="EnableMIDIAnalysis"
               id="EnableMIDIAnalysis"
               value="yes" /> MIDI Analysis                        
        </td>
    </tr>
    
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Enable email notification to the user"/>
        
        <c:set var="enabled_SMTP" value="<%= SMTP_HOST %>" />
        <c:set var="enabled_SENDER" value="<%= SENDER_MAIL %>" />
        <c:choose>
        <c:when test="${empty enabled_SMTP || empty enabled_SENDER}">
        <input type="checkbox" 
               name="EnableNotification"
               disabled="disable"
               value="yes" /> Notification
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               name="EnableNotification"               
               value="yes" /> Notification
        </c:otherwise>
        </c:choose>
        
        <img width="70"
             id="EnableNotificationid"             
             src="<%= renderRequest.getContextPath()%>/images/mailing2.png" 
             border="0"/>
        </td>                
    </tr>
    
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Customize some sonification algorithm's parameters (for experts)"/>
        
        <a id="aTag" href="javascript:toggleAndChangeText();">
        Advanced settings &#9660;
        </a>
        <div id="divToToggle" display="none">
        
        <!-- ------------------------------- -->
        <!--  ASCIItoMIDI Advanced Settings  -->
        <!-- ------------------------------- -->
        <div id="divASCIISETtoMIDI" display="none">
        <br/>Configure advanced settings for the selected algorithm (if any)<br/><br/>
        
        <table id="ASCIISETtoMIDI_Toggle" border="0">
        <tr> 
        <td width="150">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Range Min [ 0, ... ] " />

            <label for="range_min">Range Min.<em>*</em></label>                        
        </td>

        <td width="350"><div align="absmiddle" id="slider-range-min"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="range_min"
               value="1"/>
            
            <input type="text" 
               id="range_min"
               value="1"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>
        
        <tr> 
        <td width="150">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Range Max [ 0, ... ] " />

            <label for="range_max">Range Max.<em>*</em></label>                        
        </td>

        <td width="350"><div align="absmiddle" id="slider-range-max"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="range_max"
               value="255"/>
            
            <input type="text" 
               id="range_max"
               value="255"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>
        
        <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please, select the multiscale to be used"/>
        
        <label for="multiscale">Multiscale </label><em>*</em>
        </td>
            
        <td>
        <select name="multiscale" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;">                                
            
        <option value="0">Dodecatonic Tones</option>        
        <option value="1">Diatonic Tones</option>
        <option value="2">Blues Tones</option>
        <option value="3">Pentatonic Tones</option>
        <option value="4">Whole Tones</option>
        </select>
        </td>                
        </tr>
        
        <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please, select the Speed to be used"/>
        
        <label for="speed">Speed </label><em>*</em>
        </td>
        
        <td>
        <select name="speed" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;">
            
        <option value="2">Slow</option>
        <option value="1">Fast</option>
        </select>
        </td>                
        </tr>
        
        </table>
        </div>
        <!-- ------------------------------- -->
        <!--  ASCIItoMIDI Advanced Settings  -->
        <!-- ------------------------------- -->
        
        <!-- ------------------------------- -->
        <!-- DATASETtoMIDI Advanced Settings -->
        <!-- ------------------------------- -->        
        <table id="DATASETtoMIDI_Toggle" border="0">
        <tr> 
        <td width="150">
            <img width="30" 
                 align="absmiddle"
                 src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
                 border="0" title="Time [ 0, 200 ] " />

            <label for="time">Time<em>*</em></label>                        
        </td>

        <td width="350"><div align="absmiddle" id="slider-time"></div></td>
        <td width="50" align="right">
            <input type="hidden" 
               name="time"
               value="120"
               class="required"/>
            
            <input type="text" 
               id="time"
               value="120"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
        </td>
        </tr>
        </table>        
        <!-- ------------------------------- -->
        <!-- DATASETtoMIDI Advanced Settings -->
        <!-- ------------------------------- -->
        
        <!-- ------------------------------- -->
        <!-- DATASETtoWAVE Advanced Settings -->
        <!-- ------------------------------- -->
        <table id="DATASETtoWAVE_Toggle" border="0">
        <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please, select the Bytes per Sample to be used"/>
        
        <label for="bytes_per_sample">Bytes </label><em>*</em>
        </td>
        
        <td>
        <select name="bytes_per_sample" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;">
            
        <option value="2">2</option>        
        </select>
        </td>
        </tr>
        
        <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please, select the original sampling frequency to be used"/>
        
        <label for="sample_frequency">Sample Freq. </label><em>*</em>
        </td>
        
        <td>
        <select name="sample_frequency" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;">
            
        <option value="100">100 Hz.(default)</option>
        <option value="200">200 Hz.</option>
        </select>
        </td>
        </tr>
        
        <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please, select the resampling frequency to be used"/>
        
        <label for="resample_frequency">Resample Freq. </label><em>*</em>
        </td>
        
        <td>
        <select name="resample_frequency" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;">
            
        <option value="10">10 Hz.</option>        
        </select>
        </td>
        </tr>
        
        <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please, select the scale to be used"/>
        
        <label for="scale">Scale </label><em>*</em>
        </td>
        
        <td>
        <select name="scale" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;">
            
        <option value="2">2 (default)</option>        
        </select>
        </td>
        </tr>
        
        <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/pushpin.png" 
             border="0" title="Please, select the datatype to be used"/>
        
        <label for="datatype">DataType </label><em>*</em>
        </td>
        
        <td>
        <select name="datatype" 
                style="height:25px; padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;">
            
        <option value="1">1 (default)</option>
        <option value="0">0</option>
        </select>
        </td>
        </tr>
        </table>
        <!-- ------------------------------- -->
        <!-- DATASETtoWAVE Advanced Settings -->
        <!-- ------------------------------- -->
        
        <!-- ------------------------------- -->
        <!-- ASCIItoSPHERE Advanced Settings -->
        <!-- ------------------------------- -->    
        <!-- ------------------------------- -->
        <!-- ASCIItoSPHERE Advanced Settings -->
        <!-- ------------------------------- -->
                 
        </div>
        </td>
    </tr>

    <tr><td><br/></td></tr>

    <tr>                    
        <td align="left">
            <input type="image" 
                   src="<%= renderRequest.getContextPath()%>/images/start-icon.png"
                   width="60"                   
                   name="submit"
                   id ="submit" 
                   title="Run your Simulation!" />                    
        </td>
     </tr>                                            
</table>    
</div>	<!-- Fine Terzo Accordion -->
</div> <!-- Fine Accordions -->
</fieldset>    
</form>                                                                         

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
                 
<!--script type="text/javascript">
    var pager = new Pager('results', 13); 
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
</script-->
