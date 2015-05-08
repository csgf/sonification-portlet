/*
 *************************************************************************
Copyright (c) 2011-2013:
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
 ***************************************************************************
 */
package it.infn.ct.sonification;

// import liferay libraries
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

// import DataEngine libraries
import com.liferay.portal.util.PortalUtil;
import it.infn.ct.GridEngine.InformationSystem.BDII;
import it.infn.ct.GridEngine.Job.*;

// import generic Java libraries
import it.infn.ct.GridEngine.UsersTracking.UsersTrackingDBInterface;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URI;

// import portlet libraries
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

// Importing Apache libraries
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Sonification extends GenericPortlet {

    private static Log log = LogFactory.getLog(Sonification.class);

    @Override
    protected void doEdit(RenderRequest request,
            RenderResponse response)
            throws PortletException, IOException
    {

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        response.setContentType("text/html");
        
        // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the COMETA VO
        String cometa_sonification_INFRASTRUCTURE = portletPreferences.getValue("cometa_sonification_INFRASTRUCTURE", "N/A");
        // Get the SONIFICATION VONAME from the portlet preferences for the COMETA VO
        String cometa_sonification_VONAME = portletPreferences.getValue("cometa_sonification_VONAME", "N/A");
        // Get the SONIFICATION TOPPBDII from the portlet preferences for the COMETA VO
        String cometa_sonification_TOPBDII = portletPreferences.getValue("cometa_sonification_TOPBDII", "N/A");
        // Get the SONIFICATION WMS from the portlet preferences for the COMETA VO
        String[] cometa_sonification_WMS = portletPreferences.getValues("cometa_sonification_WMS", new String[5]);
        // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the COMETA VO
        String cometa_sonification_ETOKENSERVER = portletPreferences.getValue("cometa_sonification_ETOKENSERVER", "N/A");
        // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the COMETA VO
        String cometa_sonification_MYPROXYSERVER = portletPreferences.getValue("cometa_sonification_MYPROXYSERVER", "N/A");
        // Get the SONIFICATION PORT from the portlet preferences for the COMETA VO
        String cometa_sonification_PORT = portletPreferences.getValue("cometa_sonification_PORT", "N/A");
        // Get the SONIFICATION ROBOTID from the portlet preferences for the COMETA VO
        String cometa_sonification_ROBOTID = portletPreferences.getValue("cometa_sonification_ROBOTID", "N/A");
        // Get the SONIFICATION ROLE from the portlet preferences for the COMETA VO
        String cometa_sonification_ROLE = portletPreferences.getValue("cometa_sonification_ROLE", "N/A");
        // Get the SONIFICATION RENEWAL from the portlet preferences for the COMETA VO
        String cometa_sonification_RENEWAL = portletPreferences.getValue("cometa_sonification_RENEWAL", "checked");
        // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the COMETA VO
        String cometa_sonification_DISABLEVOMS = portletPreferences.getValue("cometa_sonification_DISABLEVOMS", "unchecked");
        
        // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the GARUDA VO
        String garuda_sonification_INFRASTRUCTURE = portletPreferences.getValue("garuda_sonification_INFRASTRUCTURE", "N/A");
        // Get the SONIFICATION VONAME from the portlet preferences for the GARUDA VO
        String garuda_sonification_VONAME = portletPreferences.getValue("garuda_sonification_VONAME", "N/A");
        // Get the SONIFICATION TOPPBDII from the portlet preferences for the GARUDA VO
        String garuda_sonification_TOPBDII = portletPreferences.getValue("garuda_sonification_TOPBDII", "N/A");
        // Get the SONIFICATION WMS from the portlet preferences for the GARUDA VO
        String[] garuda_sonification_WMS = portletPreferences.getValues("garuda_sonification_WMS", new String[5]);
        // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the GARUDA VO
        String garuda_sonification_ETOKENSERVER = portletPreferences.getValue("garuda_sonification_ETOKENSERVER", "N/A");
        // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the GARUDA VO
        String garuda_sonification_MYPROXYSERVER = portletPreferences.getValue("garuda_sonification_MYPROXYSERVER", "N/A");
        // Get the SONIFICATION PORT from the portlet preferences for the GARUDA VO
        String garuda_sonification_PORT = portletPreferences.getValue("garuda_sonification_PORT", "N/A");
        // Get the SONIFICATION ROBOTID from the portlet preferences for the GARUDA VO
        String garuda_sonification_ROBOTID = portletPreferences.getValue("garuda_sonification_ROBOTID", "N/A");
        // Get the SONIFICATION ROLE from the portlet preferences for the GARUDA VO
        String garuda_sonification_ROLE = portletPreferences.getValue("garuda_sonification_ROLE", "N/A");
        // Get the SONIFICATION RENEWAL from the portlet preferences for the GARUDA VO
        String garuda_sonification_RENEWAL = portletPreferences.getValue("garuda_sonification_RENEWAL", "checked");
        // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GARUDA VO
        String garuda_sonification_DISABLEVOMS = portletPreferences.getValue("garuda_sonification_DISABLEVOMS", "unchecked");

        // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the GRIDIT VO
        String gridit_sonification_INFRASTRUCTURE = portletPreferences.getValue("gridit_sonification_INFRASTRUCTURE", "N/A");
        // Get the SONIFICATION VONAME from the portlet preferences for the GRIDIT VO
        String gridit_sonification_VONAME = portletPreferences.getValue("gridit_sonification_VONAME", "N/A");
        // Get the SONIFICATION TOPPBDII from the portlet preferences for the GRIDIT VO
        String gridit_sonification_TOPBDII = portletPreferences.getValue("gridit_sonification_TOPBDII", "N/A");
        // Get the SONIFICATION WMS from the portlet preferences for the GRIDIT VO
        String[] gridit_sonification_WMS = portletPreferences.getValues("gridit_sonification_WMS", new String[5]);
        // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the GRIDIT VO
        String gridit_sonification_ETOKENSERVER = portletPreferences.getValue("gridit_sonification_ETOKENSERVER", "N/A");
        // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the GRIDIT VO
        String gridit_sonification_MYPROXYSERVER = portletPreferences.getValue("gridit_sonification_MYPROXYSERVER", "N/A");
        // Get the SONIFICATION PORT from the portlet preferences for the GRIDIT VO
        String gridit_sonification_PORT = portletPreferences.getValue("gridit_sonification_PORT", "N/A");
        // Get the SONIFICATION ROBOTID from the portlet preferences for the GRIDIT VO
        String gridit_sonification_ROBOTID = portletPreferences.getValue("gridit_sonification_ROBOTID", "N/A");
        // Get the SONIFICATION ROLE from the portlet preferences for the GRIDIT VO
        String gridit_sonification_ROLE = portletPreferences.getValue("gridit_sonification_ROLE", "N/A");
        // Get the SONIFICATION RENEWAL from the portlet preferences for the GRIDIT VO
        String gridit_sonification_RENEWAL = portletPreferences.getValue("gridit_sonification_RENEWAL", "checked");
        // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GRIDIT VO
        String gridit_sonification_DISABLEVOMS = portletPreferences.getValue("gridit_sonification_DISABLEVOMS", "unchecked");
        
        // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the GILDA VO
        String gilda_sonification_INFRASTRUCTURE = portletPreferences.getValue("gilda_sonification_INFRASTRUCTURE", "N/A");
        // Get the SONIFICATION VONAME from the portlet preferences for the GILDA VO
        String gilda_sonification_VONAME = portletPreferences.getValue("gilda_sonification_VONAME", "N/A");
        // Get the SONIFICATION TOPPBDII from the portlet preferences for the GILDA VO
        String gilda_sonification_TOPBDII = portletPreferences.getValue("gilda_sonification_TOPBDII", "N/A");
        // Get the SONIFICATION WMS from the portlet preferences for the GILDA VO
        String[] gilda_sonification_WMS = portletPreferences.getValues("gilda_sonification_WMS", new String[5]);
        // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the GILDA VO
        String gilda_sonification_ETOKENSERVER = portletPreferences.getValue("gilda_sonification_ETOKENSERVER", "N/A");
        // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the GILDA VO
        String gilda_sonification_MYPROXYSERVER = portletPreferences.getValue("gilda_sonification_MYPROXYSERVER", "N/A");
        // Get the SONIFICATION PORT from the portlet preferences for the GILDA VO
        String gilda_sonification_PORT = portletPreferences.getValue("gilda_sonification_PORT", "N/A");
        // Get the SONIFICATION ROBOTID from the portlet preferences for the GILDA VO
        String gilda_sonification_ROBOTID = portletPreferences.getValue("gilda_sonification_ROBOTID", "N/A");
        // Get the SONIFICATION ROLE from the portlet preferences for the GILDA VO
        String gilda_sonification_ROLE = portletPreferences.getValue("gilda_sonification_ROLE", "N/A");
        // Get the SONIFICATION RENEWAL from the portlet preferences for the GILDA VO
        String gilda_sonification_RENEWAL = portletPreferences.getValue("gilda_sonification_RENEWAL", "checked");
        // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GILDA VO
        String gilda_sonification_DISABLEVOMS = portletPreferences.getValue("gilda_sonification_DISABLEVOMS", "unchecked");

        // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the EUMED VO
        String eumed_sonification_INFRASTRUCTURE = portletPreferences.getValue("eumed_sonification_INFRASTRUCTURE", "N/A");
        // Get the SONIFICATION VONAME from the portlet preferences for the EUMED VO
        String eumed_sonification_VONAME = portletPreferences.getValue("eumed_sonification_VONAME", "N/A");
        // Get the SONIFICATION TOPPBDII from the portlet preferences for the EUMED VO
        String eumed_sonification_TOPBDII = portletPreferences.getValue("eumed_sonification_TOPBDII", "N/A");
        // Get the SONIFICATION WMS from the portlet preferences for the EUMED VO
        String[] eumed_sonification_WMS = portletPreferences.getValues("eumed_sonification_WMS", new String[5]);
        // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the EUMED VO
        String eumed_sonification_ETOKENSERVER = portletPreferences.getValue("eumed_sonification_ETOKENSERVER", "N/A");
        // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the EUMED VO
        String eumed_sonification_MYPROXYSERVER = portletPreferences.getValue("eumed_sonification_MYPROXYSERVER", "N/A");
        // Get the SONIFICATION PORT from the portlet preferences for the EUMED VO
        String eumed_sonification_PORT = portletPreferences.getValue("eumed_sonification_PORT", "N/A");
        // Get the SONIFICATION ROBOTID from the portlet preferences for the EUMED VO
        String eumed_sonification_ROBOTID = portletPreferences.getValue("eumed_sonification_ROBOTID", "N/A");
        // Get the SONIFICATION ROLE from the portlet preferences for the EUMED VO
        String eumed_sonification_ROLE = portletPreferences.getValue("eumed_sonification_ROLE", "N/A");
        // Get the SONIFICATION RENEWAL from the portlet preferences for the EUMED VO
        String eumed_sonification_RENEWAL = portletPreferences.getValue("eumed_sonification_RENEWAL", "checked");
        // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the EUMED VO
        String eumed_sonification_DISABLEVOMS = portletPreferences.getValue("eumed_sonification_DISABLEVOMS", "unchecked");

        // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the GISELA VO
        String gisela_sonification_INFRASTRUCTURE = portletPreferences.getValue("gisela_sonification_INFRASTRUCTURE", "N/A");
        // Get the SONIFICATION VONAME from the portlet preferences for the GISELA VO
        String gisela_sonification_VONAME = portletPreferences.getValue("gisela_sonification_VONAME", "N/A");
        // Get the SONIFICATION TOPPBDII from the portlet preferences for the GISELA VO
        String gisela_sonification_TOPBDII = portletPreferences.getValue("gisela_sonification_TOPBDII", "N/A");
        // Get the SONIFICATION WMS from the portlet preferences for the GISELA VO
        String[] gisela_sonification_WMS = portletPreferences.getValues("gisela_sonification_WMS", new String[5]);
        // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the GISELA VO
        String gisela_sonification_ETOKENSERVER = portletPreferences.getValue("gisela_sonification_ETOKENSERVER", "N/A");
        // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the GISELA VO
        String gisela_sonification_MYPROXYSERVER = portletPreferences.getValue("gisela_sonification_MYPROXYSERVER", "N/A");
        // Get the SONIFICATION PORT from the portlet preferences for the GISELA VO
        String gisela_sonification_PORT = portletPreferences.getValue("gisela_sonification_PORT", "N/A");
        // Get the SONIFICATION ROBOTID from the portlet preferences for the GISELA VO
        String gisela_sonification_ROBOTID = portletPreferences.getValue("gisela_sonification_ROBOTID", "N/A");
        // Get the SONIFICATION ROLE from the portlet preferences for the GISELA VO
        String gisela_sonification_ROLE = portletPreferences.getValue("gisela_sonification_ROLE", "N/A");
        // Get the SONIFICATION RENEWAL from the portlet preferences for the GISELA VO
        String gisela_sonification_RENEWAL = portletPreferences.getValue("gisela_sonification_RENEWAL", "checked");
        // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GISELA VO
        String gisela_sonification_DISABLEVOMS = portletPreferences.getValue("gisela_sonification_DISABLEVOMS", "unchecked");
        
        // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the SAGRID VO
        String sagrid_sonification_INFRASTRUCTURE = portletPreferences.getValue("sagrid_sonification_INFRASTRUCTURE", "N/A");
        // Get the SONIFICATION VONAME from the portlet preferences for the SAGRID VO
        String sagrid_sonification_VONAME = portletPreferences.getValue("sagrid_sonification_VONAME", "N/A");
        // Get the SONIFICATION TOPPBDII from the portlet preferences for the SAGRID VO
        String sagrid_sonification_TOPBDII = portletPreferences.getValue("sagrid_sonification_TOPBDII", "N/A");
        // Get the SONIFICATION WMS from the portlet preferences for the SAGRID VO
        String[] sagrid_sonification_WMS = portletPreferences.getValues("sagrid_sonification_WMS", new String[5]);
        // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the SAGRID VO
        String sagrid_sonification_ETOKENSERVER = portletPreferences.getValue("sagrid_sonification_ETOKENSERVER", "N/A");
        // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the SAGRID VO
        String sagrid_sonification_MYPROXYSERVER = portletPreferences.getValue("sagrid_sonification_MYPROXYSERVER", "N/A");
        // Get the SONIFICATION PORT from the portlet preferences for the SAGRID VO
        String sagrid_sonification_PORT = portletPreferences.getValue("sagrid_sonification_PORT", "N/A");
        // Get the SONIFICATION ROBOTID from the portlet preferences for the SAGRID VO
        String sagrid_sonification_ROBOTID = portletPreferences.getValue("sagrid_sonification_ROBOTID", "N/A");
        // Get the SONIFICATION ROLE from the portlet preferences for the SAGRID VO
        String sagrid_sonification_ROLE = portletPreferences.getValue("sagrid_sonification_ROLE", "N/A");
        // Get the SONIFICATION RENEWAL from the portlet preferences for the SAGRID VO
        String sagrid_sonification_RENEWAL = portletPreferences.getValue("sagrid_sonification_RENEWAL", "checked");
        // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the SAGRID VO
        String sagrid_sonification_DISABLEVOMS = portletPreferences.getValue("sagrid_sonification_DISABLEVOMS", "unchecked");

        // Get the SONIFICATION APPID from the portlet preferences
        String sonification_APPID = portletPreferences.getValue("sonification_APPID", "N/A");
        // Get the SONIFICATION OUTPUT from the portlet preferences
        String sonification_OUTPUT_PATH = portletPreferences.getValue("sonification_OUTPUT_PATH", "/tmp");
        // Get the SONIFICATION SFOTWARE from the portlet preferences
        String sonification_SOFTWARE = portletPreferences.getValue("sonification_SOFTWARE", "N/A");
        // Get the TRACKING_DB_HOSTNAME from the portlet preferences
        String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
        // Get the TRACKING_DB_USERNAME from the portlet preferences
        String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
        // Get the TRACKING_DB_PASSWORD from the portlet preferences
        String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD", "N/A");
        // Get the SMTP_HOST from the portlet preferences
        String SMTP_HOST = portletPreferences.getValue("SMTP_HOST", "N/A");
        // Get the SENDER MAIL from the portlet preferences
        String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL", "N/A");
        // Get the list of enabled Infrastructures
        String[] infras = portletPreferences.getValues("sonification_ENABLEINFRASTRUCTURE", new String[7]);

        // Set the default portlet preferences
        request.setAttribute("gridit_sonification_INFRASTRUCTURE", gridit_sonification_INFRASTRUCTURE.trim());
        request.setAttribute("gridit_sonification_VONAME", gridit_sonification_VONAME.trim());
        request.setAttribute("gridit_sonification_TOPBDII", gridit_sonification_TOPBDII.trim());
        request.setAttribute("gridit_sonification_WMS", gridit_sonification_WMS);
        request.setAttribute("gridit_sonification_ETOKENSERVER", gridit_sonification_ETOKENSERVER.trim());
        request.setAttribute("gridit_sonification_MYPROXYSERVER", gridit_sonification_MYPROXYSERVER.trim());
        request.setAttribute("gridit_sonification_PORT", gridit_sonification_PORT.trim());
        request.setAttribute("gridit_sonification_ROBOTID", gridit_sonification_ROBOTID.trim());
        request.setAttribute("gridit_sonification_ROLE", gridit_sonification_ROLE.trim());
        request.setAttribute("gridit_sonification_RENEWAL", gridit_sonification_RENEWAL);
        request.setAttribute("gridit_sonification_DISABLEVOMS", gridit_sonification_DISABLEVOMS);
        
        request.setAttribute("garuda_sonification_INFRASTRUCTURE", garuda_sonification_INFRASTRUCTURE.trim());
        request.setAttribute("garuda_sonification_VONAME", garuda_sonification_VONAME.trim());
        request.setAttribute("garuda_sonification_TOPBDII", garuda_sonification_TOPBDII.trim());
        request.setAttribute("garuda_sonification_WMS", garuda_sonification_WMS);
        request.setAttribute("garuda_sonification_ETOKENSERVER", garuda_sonification_ETOKENSERVER.trim());
        request.setAttribute("garuda_sonification_MYPROXYSERVER", garuda_sonification_MYPROXYSERVER.trim());
        request.setAttribute("garuda_sonification_PORT", garuda_sonification_PORT.trim());
        request.setAttribute("garuda_sonification_ROBOTID", garuda_sonification_ROBOTID.trim());
        request.setAttribute("garuda_sonification_ROLE", garuda_sonification_ROLE.trim());
        request.setAttribute("garuda_sonification_RENEWAL", garuda_sonification_RENEWAL);
        request.setAttribute("garuda_sonification_DISABLEVOMS", garuda_sonification_DISABLEVOMS);
        
        request.setAttribute("cometa_sonification_INFRASTRUCTURE", cometa_sonification_INFRASTRUCTURE.trim());
        request.setAttribute("cometa_sonification_VONAME", cometa_sonification_VONAME.trim());
        request.setAttribute("cometa_sonification_TOPBDII", cometa_sonification_TOPBDII.trim());
        request.setAttribute("cometa_sonification_WMS", cometa_sonification_WMS);
        request.setAttribute("cometa_sonification_ETOKENSERVER", cometa_sonification_ETOKENSERVER.trim());
        request.setAttribute("cometa_sonification_MYPROXYSERVER", cometa_sonification_MYPROXYSERVER.trim());
        request.setAttribute("cometa_sonification_PORT", cometa_sonification_PORT.trim());
        request.setAttribute("cometa_sonification_ROBOTID", cometa_sonification_ROBOTID.trim());
        request.setAttribute("cometa_sonification_ROLE", cometa_sonification_ROLE.trim());
        request.setAttribute("cometa_sonification_RENEWAL", cometa_sonification_RENEWAL);
        request.setAttribute("cometa_sonification_DISABLEVOMS", cometa_sonification_DISABLEVOMS);
        
        request.setAttribute("gilda_sonification_INFRASTRUCTURE", gilda_sonification_INFRASTRUCTURE.trim());
        request.setAttribute("gilda_sonification_VONAME", gilda_sonification_VONAME.trim());
        request.setAttribute("gilda_sonification_TOPBDII", gilda_sonification_TOPBDII.trim());
        request.setAttribute("gilda_sonification_WMS", gilda_sonification_WMS);
        request.setAttribute("gilda_sonification_ETOKENSERVER", gilda_sonification_ETOKENSERVER.trim());
        request.setAttribute("gilda_sonification_MYPROXYSERVER", gilda_sonification_MYPROXYSERVER.trim());
        request.setAttribute("gilda_sonification_PORT", gilda_sonification_PORT.trim());
        request.setAttribute("gilda_sonification_ROBOTID", gilda_sonification_ROBOTID.trim());
        request.setAttribute("gilda_sonification_ROLE", gilda_sonification_ROLE.trim());
        request.setAttribute("gilda_sonification_RENEWAL", gilda_sonification_RENEWAL);
        request.setAttribute("gilda_sonification_DISABLEVOMS", gilda_sonification_DISABLEVOMS);

        request.setAttribute("eumed_sonification_INFRASTRUCTURE", eumed_sonification_INFRASTRUCTURE.trim());
        request.setAttribute("eumed_sonification_VONAME", eumed_sonification_VONAME.trim());
        request.setAttribute("eumed_sonification_TOPBDII", eumed_sonification_TOPBDII.trim());
        request.setAttribute("eumed_sonification_WMS", eumed_sonification_WMS);
        request.setAttribute("eumed_sonification_ETOKENSERVER", eumed_sonification_ETOKENSERVER.trim());
        request.setAttribute("eumed_sonification_MYPROXYSERVER", eumed_sonification_MYPROXYSERVER.trim());
        request.setAttribute("eumed_sonification_PORT", eumed_sonification_PORT.trim());
        request.setAttribute("eumed_sonification_ROBOTID", eumed_sonification_ROBOTID.trim());
        request.setAttribute("eumed_sonification_ROLE", eumed_sonification_ROLE.trim());
        request.setAttribute("eumed_sonification_RENEWAL", eumed_sonification_RENEWAL);
        request.setAttribute("eumed_sonification_DISABLEVOMS", eumed_sonification_DISABLEVOMS);                

        request.setAttribute("gisela_sonification_INFRASTRUCTURE", gisela_sonification_INFRASTRUCTURE.trim());
        request.setAttribute("gisela_sonification_VONAME", gisela_sonification_VONAME.trim());
        request.setAttribute("gisela_sonification_TOPBDII", gisela_sonification_TOPBDII.trim());
        request.setAttribute("gisela_sonification_WMS", gisela_sonification_WMS);
        request.setAttribute("gisela_sonification_ETOKENSERVER", gisela_sonification_ETOKENSERVER.trim());
        request.setAttribute("gisela_sonification_MYPROXYSERVER", gisela_sonification_MYPROXYSERVER.trim());
        request.setAttribute("gisela_sonification_PORT", gisela_sonification_PORT.trim());
        request.setAttribute("gisela_sonification_ROBOTID", gisela_sonification_ROBOTID.trim());
        request.setAttribute("gisela_sonification_ROLE", gisela_sonification_ROLE.trim());
        request.setAttribute("gisela_sonification_RENEWAL", gisela_sonification_RENEWAL);
        request.setAttribute("gisela_sonification_DISABLEVOMS", gisela_sonification_DISABLEVOMS);
        
        request.setAttribute("sagrid_sonification_INFRASTRUCTURE", sagrid_sonification_INFRASTRUCTURE.trim());
        request.setAttribute("sagrid_sonification_VONAME", sagrid_sonification_VONAME.trim());
        request.setAttribute("sagrid_sonification_TOPBDII", sagrid_sonification_TOPBDII.trim());
        request.setAttribute("sagrid_sonification_WMS", sagrid_sonification_WMS);
        request.setAttribute("sagrid_sonification_ETOKENSERVER", sagrid_sonification_ETOKENSERVER.trim());
        request.setAttribute("sagrid_sonification_MYPROXYSERVER", sagrid_sonification_MYPROXYSERVER.trim());
        request.setAttribute("sagrid_sonification_PORT", sagrid_sonification_PORT.trim());
        request.setAttribute("sagrid_sonification_ROBOTID", sagrid_sonification_ROBOTID.trim());
        request.setAttribute("sagrid_sonification_ROLE", sagrid_sonification_ROLE.trim());
        request.setAttribute("sagrid_sonification_RENEWAL", sagrid_sonification_RENEWAL);
        request.setAttribute("sagrid_sonification_DISABLEVOMS", sagrid_sonification_DISABLEVOMS);

        request.setAttribute("sonification_ENABLEINFRASTRUCTURE", infras);
        request.setAttribute("sonification_APPID", sonification_APPID.trim());
        request.setAttribute("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
        request.setAttribute("sonification_SOFTWARE", sonification_SOFTWARE.trim());
        request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
        request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
        request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
        request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
        request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());        

        log.info("\nStarting the EDIT mode...with this settings"
        + "\ncometa_sonification_INFRASTRUCTURE: " + cometa_sonification_INFRASTRUCTURE
        + "\ncometa_sonification_VONAME: " + cometa_sonification_VONAME
        + "\ncometa_sonification_TOPBDII: " + cometa_sonification_TOPBDII                    
        + "\ncometa_sonification_ETOKENSERVER: " + cometa_sonification_ETOKENSERVER
        + "\ncometa_sonification_MYPROXYSERVER: " + cometa_sonification_MYPROXYSERVER
        + "\ncometa_sonification_PORT: " + cometa_sonification_PORT
        + "\ncometa_sonification_ROBOTID: " + cometa_sonification_ROBOTID
        + "\ncometa_sonification_ROLE: " + cometa_sonification_ROLE
        + "\ncometa_sonification_RENEWAL: " + cometa_sonification_RENEWAL
        + "\ncometa_sonification_DISABLEVOMS: " + cometa_sonification_DISABLEVOMS
                
        + "\n\ngaruda_sonification_INFRASTRUCTURE: " + garuda_sonification_INFRASTRUCTURE
        + "\ngaruda_sonification_VONAME: " + garuda_sonification_VONAME
        + "\ngaruda_sonification_TOPBDII: " + garuda_sonification_TOPBDII                    
        + "\ngaruda_sonification_ETOKENSERVER: " + garuda_sonification_ETOKENSERVER
        + "\ngaruda_sonification_MYPROXYSERVER: " + garuda_sonification_MYPROXYSERVER
        + "\ngaruda_sonification_PORT: " + garuda_sonification_PORT
        + "\ngaruda_sonification_ROBOTID: " + garuda_sonification_ROBOTID
        + "\ngaruda_sonification_ROLE: " + garuda_sonification_ROLE
        + "\ngaruda_sonification_RENEWAL: " + garuda_sonification_RENEWAL
        + "\ngaruda_sonification_DISABLEVOMS: " + garuda_sonification_DISABLEVOMS 
                
        + "\n\ngridit_sonification_INFRASTRUCTURE: " + gridit_sonification_INFRASTRUCTURE
        + "\ngridit_sonification_VONAME: " + gridit_sonification_VONAME
        + "\ngridit_sonification_TOPBDII: " + gridit_sonification_TOPBDII                    
        + "\ngridit_sonification_ETOKENSERVER: " + gridit_sonification_ETOKENSERVER
        + "\ngridit_sonification_MYPROXYSERVER: " + gridit_sonification_MYPROXYSERVER
        + "\ngridit_sonification_PORT: " + gridit_sonification_PORT
        + "\ngridit_sonification_ROBOTID: " + gridit_sonification_ROBOTID
        + "\ngridit_sonification_ROLE: " + gridit_sonification_ROLE
        + "\ngridit_sonification_RENEWAL: " + gridit_sonification_RENEWAL
        + "\ngridit_sonification_DISABLEVOMS: " + gridit_sonification_DISABLEVOMS
                
        + "\n\ngilda_sonification_INFRASTRUCTURE: " + gilda_sonification_INFRASTRUCTURE
        + "\ngilda_sonification_VONAME: " + gilda_sonification_VONAME
        + "\ngilda_sonification_TOPBDII: " + gilda_sonification_TOPBDII                    
        + "\ngilda_sonification_ETOKENSERVER: " + gilda_sonification_ETOKENSERVER
        + "\ngilda_sonification_MYPROXYSERVER: " + gilda_sonification_MYPROXYSERVER
        + "\ngilda_sonification_PORT: " + gilda_sonification_PORT
        + "\ngilda_sonification_ROBOTID: " + gilda_sonification_ROBOTID
        + "\ngilda_sonification_ROLE: " + gilda_sonification_ROLE
        + "\ngilda_sonification_RENEWAL: " + gilda_sonification_RENEWAL
        + "\ngilda_sonification_DISABLEVOMS: " + gilda_sonification_DISABLEVOMS

        + "\n\neumed_sonification_INFRASTRUCTURE: " + eumed_sonification_INFRASTRUCTURE
        + "\neumed_sonification_VONAME: " + eumed_sonification_VONAME
        + "\neumed_sonification_TOPBDII: " + eumed_sonification_TOPBDII                    
        + "\neumed_sonification_ETOKENSERVER: " + eumed_sonification_ETOKENSERVER
        + "\neumed_sonification_MYPROXYSERVER: " + eumed_sonification_MYPROXYSERVER
        + "\neumed_sonification_PORT: " + eumed_sonification_PORT
        + "\neumed_sonification_ROBOTID: " + eumed_sonification_ROBOTID
        + "\neumed_sonification_ROLE: " + eumed_sonification_ROLE
        + "\neumed_sonification_RENEWAL: " + eumed_sonification_RENEWAL
        + "\neumed_sonification_DISABLEVOMS: " + eumed_sonification_DISABLEVOMS

        + "\n\ngisela_sonification_INFRASTRUCTURE: " + gisela_sonification_INFRASTRUCTURE
        + "\ngisela_sonification_VONAME: " + gisela_sonification_VONAME
        + "\ngisela_sonification_TOPBDII: " + gisela_sonification_TOPBDII                   
        + "\ngisela_sonification_ETOKENSERVER: " + gisela_sonification_ETOKENSERVER
        + "\ngisela_sonification_MYPROXYSERVER: " + gisela_sonification_MYPROXYSERVER
        + "\ngisela_sonification_PORT: " + gisela_sonification_PORT
        + "\ngisela_sonification_ROBOTID: " + gisela_sonification_ROBOTID
        + "\ngisela_sonification_ROLE: " + gisela_sonification_ROLE
        + "\ngisela_sonification_RENEWAL: " + gisela_sonification_RENEWAL
        + "\ngisela_sonification_DISABLEVOMS: " + gisela_sonification_DISABLEVOMS
                
        + "\n\nsagrid_sonification_INFRASTRUCTURE: " + sagrid_sonification_INFRASTRUCTURE
        + "\nsagrid_sonification_VONAME: " + sagrid_sonification_VONAME
        + "\nsagrid_sonification_TOPBDII: " + sagrid_sonification_TOPBDII                   
        + "\nsagrid_sonification_ETOKENSERVER: " + sagrid_sonification_ETOKENSERVER
        + "\nsagrid_sonification_MYPROXYSERVER: " + sagrid_sonification_MYPROXYSERVER
        + "\nsagrid_sonification_PORT: " + sagrid_sonification_PORT
        + "\nsagrid_sonification_ROBOTID: " + sagrid_sonification_ROBOTID
        + "\nsagrid_sonification_ROLE: " + sagrid_sonification_ROLE
        + "\nsagrid_sonification_RENEWAL: " + sagrid_sonification_RENEWAL
        + "\nsagrid_sonification_DISABLEVOMS: " + sagrid_sonification_DISABLEVOMS
                 
        + "\nsonification_APPID: " + sonification_APPID
        + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
        + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
        + "\nSMTP Server: " + SMTP_HOST
        + "\nSender: " + SENDER_MAIL);

        PortletRequestDispatcher dispatcher =
                getPortletContext().getRequestDispatcher("/edit.jsp");

        dispatcher.include(request, response);
    }

    @Override
    protected void doHelp(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        //super.doHelp(request, response);

        response.setContentType("text/html");

        log.info("\nStarting the HELP mode...");
        PortletRequestDispatcher dispatcher =
                getPortletContext().getRequestDispatcher("/help.jsp");

        dispatcher.include(request, response);
    }

    @Override
    protected void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        response.setContentType("text/html");

        //java.util.Enumeration listPreferences = portletPreferences.getNames();
        PortletRequestDispatcher dispatcher = null;
        
        String cometa_sonification_TOPBDII = "";
        String cometa_sonification_VONAME = "";
        String garuda_sonification_TOPBDII = "";
        String garuda_sonification_VONAME = "";
        String gridit_sonification_TOPBDII = "";
        String gridit_sonification_VONAME = "";
        String gilda_sonification_TOPBDII = "";
        String gilda_sonification_VONAME = "";
        String eumed_sonification_TOPBDII = "";
        String eumed_sonification_VONAME = "";
        String gisela_sonification_TOPBDII = "";
        String gisela_sonification_VONAME = "";
        String sagrid_sonification_TOPBDII = "";
        String sagrid_sonification_VONAME = "";
        
        String cometa_sonification_ENABLEINFRASTRUCTURE="";
        String garuda_sonification_ENABLEINFRASTRUCTURE="";
        String gridit_sonification_ENABLEINFRASTRUCTURE="";
        String gilda_sonification_ENABLEINFRASTRUCTURE="";
        String eumed_sonification_ENABLEINFRASTRUCTURE="";
        String gisela_sonification_ENABLEINFRASTRUCTURE="";
        String sagrid_sonification_ENABLEINFRASTRUCTURE="";
        String[] infras = new String[7];
                
        String[] sonification_INFRASTRUCTURES = 
                portletPreferences.getValues("sonification_ENABLEINFRASTRUCTURE", new String[7]);
        
        for (int i=0; i<sonification_INFRASTRUCTURES.length; i++) {            
            if (sonification_INFRASTRUCTURES[i]!=null && sonification_INFRASTRUCTURES[i].equals("cometa")) 
                { cometa_sonification_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n COMETA!"); }
            if (sonification_INFRASTRUCTURES[i]!=null && sonification_INFRASTRUCTURES[i].equals("garuda")) 
                { garuda_sonification_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GARUDA!"); }
            if (sonification_INFRASTRUCTURES[i]!=null && sonification_INFRASTRUCTURES[i].equals("gridit")) 
                { gridit_sonification_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GRIDIT!"); }
            if (sonification_INFRASTRUCTURES[i]!=null && sonification_INFRASTRUCTURES[i].equals("gilda")) 
                { gilda_sonification_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GILDA!"); }
            if (sonification_INFRASTRUCTURES[i]!=null && sonification_INFRASTRUCTURES[i].equals("eumed")) 
                { eumed_sonification_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n EUMED!"); }
            if (sonification_INFRASTRUCTURES[i]!=null && sonification_INFRASTRUCTURES[i].equals("gisela")) 
                { gisela_sonification_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GISELA!"); }
            if (sonification_INFRASTRUCTURES[i]!=null && sonification_INFRASTRUCTURES[i].equals("sagrid")) 
                { sagrid_sonification_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n SAGRID!"); }
        }
                
        // Get the SONIFICATION APPID from the portlet preferences
        String sonification_APPID = portletPreferences.getValue("sonification_APPID", "N/A");
        // Get the SONIFICATION APPID from the portlet preferences
        String sonification_OUTPUT_PATH = portletPreferences.getValue("sonification_OUTPUT_PATH", "N/A");
        // Get the SONIFICATION SOFTWARE from the portlet preferences
        String sonification_SOFTWARE = portletPreferences.getValue("sonification_SOFTWARE", "N/A");
        // Get the TRACKING_DB_HOSTNAME from the portlet preferences
        String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
        // Get the TRACKING_DB_USERNAME from the portlet preferences
        String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
        // Get the TRACKING_DB_PASSWORD from the portlet preferences
        String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD", "N/A");
        // Get the SMTP_HOST from the portlet preferences
        String SMTP_HOST = portletPreferences.getValue("SMTP_HOST", "N/A");
        // Get the SENDER_MAIL from the portlet preferences
        String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL", "N/A");
        
        if (cometa_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[0]="cometa";
            // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the COMETA VO
            String cometa_sonification_INFRASTRUCTURE = portletPreferences.getValue("cometa_sonification_INFRASTRUCTURE", "N/A");
            // Get the SONIFICATION VONAME from the portlet preferences for the COMETA VO
            cometa_sonification_VONAME = portletPreferences.getValue("cometa_sonification_VONAME", "N/A");
            // Get the SONIFICATION TOPPBDII from the portlet preferences for the COMETA VO
            cometa_sonification_TOPBDII = portletPreferences.getValue("cometa_sonification_TOPBDII", "N/A");
            // Get the SONIFICATION WMS from the portlet preferences for the COMETA VO
            String[] cometa_sonification_WMS = portletPreferences.getValues("cometa_sonification_WMS", new String[5]);
            // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the COMETA VO
            String cometa_sonification_ETOKENSERVER = portletPreferences.getValue("cometa_sonification_ETOKENSERVER", "N/A");
            // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the COMETA VO
            String cometa_sonification_MYPROXYSERVER = portletPreferences.getValue("cometa_sonification_MYPROXYSERVER", "N/A");
            // Get the SONIFICATION PORT from the portlet preferences for the COMETA VO
            String cometa_sonification_PORT = portletPreferences.getValue("cometa_sonification_PORT", "N/A");
            // Get the SONIFICATION ROBOTID from the portlet preferences for the COMETA VO
            String cometa_sonification_ROBOTID = portletPreferences.getValue("gridit_sonification_ROBOTID", "N/A");
            // Get the SONIFICATION ROLE from the portlet preferences for the COMETA VO
            String cometa_sonification_ROLE = portletPreferences.getValue("cometa_sonification_ROLE", "N/A");
            // Get the SONIFICATION RENEWAL from the portlet preferences for the COMETA VO
            String cometa_sonification_RENEWAL = portletPreferences.getValue("cometa_sonification_RENEWAL", "checked");
            // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the COMETA VO
            String cometa_sonification_DISABLEVOMS = portletPreferences.getValue("cometa_sonification_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the COMETA VO
            String cometa_WMS = "";
            if (cometa_sonification_ENABLEINFRASTRUCTURE.equals("checked")) {
                if (cometa_sonification_WMS!=null) {
                    //log.info("length="+cometa_sonification_WMS.length);
                    for (int i = 0; i < cometa_sonification_WMS.length; i++)
                        if (!(cometa_sonification_WMS[i].trim().equals("N/A")) ) 
                            cometa_WMS += cometa_sonification_WMS[i] + " ";                        
                } else { log.info("WMS not set for COMETA!"); cometa_sonification_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("cometa_sonification_INFRASTRUCTURE", cometa_sonification_INFRASTRUCTURE.trim());
            request.setAttribute("cometa_sonification_VONAME", cometa_sonification_VONAME.trim());
            request.setAttribute("cometa_sonification_TOPBDII", cometa_sonification_TOPBDII.trim());
            request.setAttribute("cometa_sonification_WMS", cometa_WMS);
            request.setAttribute("cometa_sonification_ETOKENSERVER", cometa_sonification_ETOKENSERVER.trim());
            request.setAttribute("cometa_sonification_MYPROXYSERVER", cometa_sonification_MYPROXYSERVER.trim());
            request.setAttribute("cometa_sonification_PORT", cometa_sonification_PORT.trim());
            request.setAttribute("cometa_sonification_ROBOTID", cometa_sonification_ROBOTID.trim());
            request.setAttribute("cometa_sonification_ROLE", cometa_sonification_ROLE.trim());
            request.setAttribute("cometa_sonification_RENEWAL", cometa_sonification_RENEWAL);
            request.setAttribute("cometa_sonification_DISABLEVOMS", cometa_sonification_DISABLEVOMS);
            
            //request.setAttribute("sonification_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("sonification_APPID", sonification_APPID.trim());
            request.setAttribute("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
            request.setAttribute("sonification_SOFTWARE", sonification_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (garuda_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[1]="garuda";
            // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the GARUDA VO
            String garuda_sonification_INFRASTRUCTURE = portletPreferences.getValue("garuda_sonification_INFRASTRUCTURE", "N/A");
            // Get the SONIFICATION VONAME from the portlet preferences for the GARUDA VO
            garuda_sonification_VONAME = portletPreferences.getValue("garuda_sonification_VONAME", "N/A");
            // Get the SONIFICATION TOPPBDII from the portlet preferences for the GARUDA VO
            garuda_sonification_TOPBDII = portletPreferences.getValue("garuda_sonification_TOPBDII", "N/A");
            // Get the SONIFICATION WMS from the portlet preferences for the GARUDA VO
            String[] garuda_sonification_WMS = portletPreferences.getValues("garuda_sonification_WMS", new String[5]);
            // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the GARUDA VO
            String garuda_sonification_ETOKENSERVER = portletPreferences.getValue("garuda_sonification_ETOKENSERVER", "N/A");
            // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the GARUDA VO
            String garuda_sonification_MYPROXYSERVER = portletPreferences.getValue("garuda_sonification_MYPROXYSERVER", "N/A");
            // Get the SONIFICATION PORT from the portlet preferences for the GARUDA VO
            String garuda_sonification_PORT = portletPreferences.getValue("garuda_sonification_PORT", "N/A");
            // Get the SONIFICATION ROBOTID from the portlet preferences for the GARUDA VO
            String garuda_sonification_ROBOTID = portletPreferences.getValue("garuda_sonification_ROBOTID", "N/A");
            // Get the SONIFICATION ROLE from the portlet preferences for the GARUDA VO
            String garuda_sonification_ROLE = portletPreferences.getValue("garuda_sonification_ROLE", "N/A");
            // Get the SONIFICATION RENEWAL from the portlet preferences for the GARUDA VO
            String garuda_sonification_RENEWAL = portletPreferences.getValue("garuda_sonification_RENEWAL", "checked");
            // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GARUDA VO
            String garuda_sonification_DISABLEVOMS = portletPreferences.getValue("garuda_sonification_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the GARUDA VO
            String garuda_WMS = "";
            if (garuda_sonification_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (garuda_sonification_WMS!=null) {
                    //log.info("length="+garuda_sonification_WMS.length);
                    for (int i = 0; i < garuda_sonification_WMS.length; i++)
                        if (!(garuda_sonification_WMS[i].trim().equals("N/A")) ) 
                            garuda_WMS += garuda_sonification_WMS[i] + " ";                        
                } else { log.info("WSGRAM not set for GARUDA!"); garuda_sonification_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("garuda_sonification_INFRASTRUCTURE", garuda_sonification_INFRASTRUCTURE.trim());
            request.setAttribute("garuda_sonification_VONAME", garuda_sonification_VONAME.trim());
            request.setAttribute("garuda_sonification_TOPBDII", garuda_sonification_TOPBDII.trim());
            request.setAttribute("garuda_sonification_WMS", garuda_WMS);
            request.setAttribute("garuda_sonification_ETOKENSERVER", garuda_sonification_ETOKENSERVER.trim());
            request.setAttribute("garuda_sonification_MYPROXYSERVER", garuda_sonification_MYPROXYSERVER.trim());
            request.setAttribute("garuda_sonification_PORT", garuda_sonification_PORT.trim());
            request.setAttribute("garuda_sonification_ROBOTID", garuda_sonification_ROBOTID.trim());
            request.setAttribute("garuda_sonification_ROLE", garuda_sonification_ROLE.trim());
            request.setAttribute("garuda_sonification_RENEWAL", garuda_sonification_RENEWAL);
            request.setAttribute("garuda_sonification_DISABLEVOMS", garuda_sonification_DISABLEVOMS);
            
            //request.setAttribute("sonification_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("sonification_APPID", sonification_APPID.trim());
            request.setAttribute("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
            request.setAttribute("sonification_SOFTWARE", sonification_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (gridit_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[2]="gridit";
            // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the GRIDIT VO
            String gridit_sonification_INFRASTRUCTURE = portletPreferences.getValue("gridit_sonification_INFRASTRUCTURE", "N/A");
            // Get the SONIFICATION VONAME from the portlet preferences for the GRIDIT VO
            gridit_sonification_VONAME = portletPreferences.getValue("gridit_sonification_VONAME", "N/A");
            // Get the SONIFICATION TOPPBDII from the portlet preferences for the GRIDIT VO
            gridit_sonification_TOPBDII = portletPreferences.getValue("gridit_sonification_TOPBDII", "N/A");
            // Get the SONIFICATION WMS from the portlet preferences for the GRIDIT VO
            String[] gridit_sonification_WMS = portletPreferences.getValues("gridit_sonification_WMS", new String[5]);
            // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the GRIDIT VO
            String gridit_sonification_ETOKENSERVER = portletPreferences.getValue("gridit_sonification_ETOKENSERVER", "N/A");
            // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the GRIDIT VO
            String gridit_sonification_MYPROXYSERVER = portletPreferences.getValue("gridit_sonification_MYPROXYSERVER", "N/A");
            // Get the SONIFICATION PORT from the portlet preferences for the GRIDIT VO
            String gridit_sonification_PORT = portletPreferences.getValue("gridit_sonification_PORT", "N/A");
            // Get the SONIFICATION ROBOTID from the portlet preferences for the GRIDIT VO
            String gridit_sonification_ROBOTID = portletPreferences.getValue("gridit_sonification_ROBOTID", "N/A");
            // Get the SONIFICATION ROLE from the portlet preferences for the GRIDIT VO
            String gridit_sonification_ROLE = portletPreferences.getValue("gridit_sonification_ROLE", "N/A");
            // Get the SONIFICATION RENEWAL from the portlet preferences for the GRIDIT VO
            String gridit_sonification_RENEWAL = portletPreferences.getValue("gridit_sonification_RENEWAL", "checked");
            // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GRIDIT VO
            String gridit_sonification_DISABLEVOMS = portletPreferences.getValue("gridit_sonification_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the GRIDIT VO
            String gridit_WMS = "";
            if (gridit_sonification_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (gridit_sonification_WMS!=null) {
                    //log.info("length="+gridit_sonification_WMS.length);
                    for (int i = 0; i < gridit_sonification_WMS.length; i++)
                        if (!(gridit_sonification_WMS[i].trim().equals("N/A")) ) 
                            gridit_WMS += gridit_sonification_WMS[i] + " ";                        
                } else { log.info("WMS not set for GRIDIT!"); gridit_sonification_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("gridit_sonification_INFRASTRUCTURE", gridit_sonification_INFRASTRUCTURE.trim());
            request.setAttribute("gridit_sonification_VONAME", gridit_sonification_VONAME.trim());
            request.setAttribute("gridit_sonification_TOPBDII", gridit_sonification_TOPBDII.trim());
            request.setAttribute("gridit_sonification_WMS", gridit_WMS);
            request.setAttribute("gridit_sonification_ETOKENSERVER", gridit_sonification_ETOKENSERVER.trim());
            request.setAttribute("gridit_sonification_MYPROXYSERVER", gridit_sonification_MYPROXYSERVER.trim());
            request.setAttribute("gridit_sonification_PORT", gridit_sonification_PORT.trim());
            request.setAttribute("gridit_sonification_ROBOTID", gridit_sonification_ROBOTID.trim());
            request.setAttribute("gridit_sonification_ROLE", gridit_sonification_ROLE.trim());
            request.setAttribute("gridit_sonification_RENEWAL", gridit_sonification_RENEWAL);
            request.setAttribute("gridit_sonification_DISABLEVOMS", gridit_sonification_DISABLEVOMS);
            
            //request.setAttribute("sonification_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("sonification_APPID", sonification_APPID.trim());
            request.setAttribute("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
            request.setAttribute("sonification_SOFTWARE", sonification_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (gilda_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[3]="gilda";
            // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the GILDA VO
            String gilda_sonification_INFRASTRUCTURE = portletPreferences.getValue("gilda_sonification_INFRASTRUCTURE", "N/A");
            // Get the SONIFICATION VONAME from the portlet preferences for the GILDA VO
            gilda_sonification_VONAME = portletPreferences.getValue("gilda_sonification_VONAME", "N/A");
            // Get the SONIFICATION TOPPBDII from the portlet preferences for the GILDA VO
            gilda_sonification_TOPBDII = portletPreferences.getValue("gilda_sonification_TOPBDII", "N/A");
            // Get the SONIFICATION WMS from the portlet preferences for the GILDA VO
            String[] gilda_sonification_WMS = portletPreferences.getValues("gilda_sonification_WMS", new String[5]);
            // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the GILDA VO
            String gilda_sonification_ETOKENSERVER = portletPreferences.getValue("gilda_sonification_ETOKENSERVER", "N/A");
            // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the GILDA VO
            String gilda_sonification_MYPROXYSERVER = portletPreferences.getValue("gilda_sonification_MYPROXYSERVER", "N/A");
            // Get the SONIFICATION PORT from the portlet preferences for the GILDA VO
            String gilda_sonification_PORT = portletPreferences.getValue("gilda_sonification_PORT", "N/A");
            // Get the SONIFICATION ROBOTID from the portlet preferences for the GILDA VO
            String gilda_sonification_ROBOTID = portletPreferences.getValue("gilda_sonification_ROBOTID", "N/A");
            // Get the SONIFICATION ROLE from the portlet preferences for the GILDA VO
            String gilda_sonification_ROLE = portletPreferences.getValue("gilda_sonification_ROLE", "N/A");
            // Get the SONIFICATION RENEWAL from the portlet preferences for the GILDA VO
            String gilda_sonification_RENEWAL = portletPreferences.getValue("gilda_sonification_RENEWAL", "checked");
            // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GILDA VO
            String gilda_sonification_DISABLEVOMS = portletPreferences.getValue("gilda_sonification_DISABLEVOMS", "unchecked");
                                    
            // Fetching all the WMS Endpoints for the GILDA VO
            String gilda_WMS = "";
            if (gilda_sonification_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (gilda_sonification_WMS!=null) {
                    //log.info("length="+gilda_sonification_WMS.length);
                    for (int i = 0; i < gilda_sonification_WMS.length; i++)
                        if (!(gilda_sonification_WMS[i].trim().equals("N/A")) ) 
                            gilda_WMS += gilda_sonification_WMS[i] + " ";                        
                } else { log.info("WMS not set for GILDA!"); gilda_sonification_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("gilda_sonification_INFRASTRUCTURE", gilda_sonification_INFRASTRUCTURE.trim());
            request.setAttribute("gilda_sonification_VONAME", gilda_sonification_VONAME.trim());
            request.setAttribute("gilda_sonification_TOPBDII", gilda_sonification_TOPBDII.trim());
            request.setAttribute("gilda_sonification_WMS", gilda_WMS);
            request.setAttribute("gilda_sonification_ETOKENSERVER", gilda_sonification_ETOKENSERVER.trim());
            request.setAttribute("gilda_sonification_MYPROXYSERVER", gilda_sonification_MYPROXYSERVER.trim());
            request.setAttribute("gilda_sonification_PORT", gilda_sonification_PORT.trim());
            request.setAttribute("gilda_sonification_ROBOTID", gilda_sonification_ROBOTID.trim());
            request.setAttribute("gilda_sonification_ROLE", gilda_sonification_ROLE.trim());
            request.setAttribute("gilda_sonification_RENEWAL", gilda_sonification_RENEWAL);
            request.setAttribute("gilda_sonification_DISABLEVOMS", gilda_sonification_DISABLEVOMS);

            //request.setAttribute("sonification_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("sonification_APPID", sonification_APPID.trim());
            request.setAttribute("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
            request.setAttribute("sonification_SOFTWARE", sonification_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (eumed_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[4]="eumed";
            // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the EUMED VO
            String eumed_sonification_INFRASTRUCTURE = portletPreferences.getValue("eumed_sonification_INFRASTRUCTURE", "N/A");
            // Get the SONIFICATION VONAME from the portlet preferences for the EUMED VO
            eumed_sonification_VONAME = portletPreferences.getValue("eumed_sonification_VONAME", "N/A");
            // Get the SONIFICATION TOPPBDII from the portlet preferences for the EUMED VO
            eumed_sonification_TOPBDII = portletPreferences.getValue("eumed_sonification_TOPBDII", "N/A");
            // Get the SONIFICATION WMS from the portlet preferences for the EUMED VO
            String[] eumed_sonification_WMS = portletPreferences.getValues("eumed_sonification_WMS", new String[5]);
            // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the EUMED VO
            String eumed_sonification_ETOKENSERVER = portletPreferences.getValue("eumed_sonification_ETOKENSERVER", "N/A");
            // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the EUMED VO
            String eumed_sonification_MYPROXYSERVER = portletPreferences.getValue("eumed_sonification_MYPROXYSERVER", "N/A");
            // Get the SONIFICATION PORT from the portlet preferences for the EUMED VO
            String eumed_sonification_PORT = portletPreferences.getValue("eumed_sonification_PORT", "N/A");
            // Get the SONIFICATION ROBOTID from the portlet preferences for the EUMED VO
            String eumed_sonification_ROBOTID = portletPreferences.getValue("eumed_sonification_ROBOTID", "N/A");
            // Get the SONIFICATION ROLE from the portlet preferences for the EUMED VO
            String eumed_sonification_ROLE = portletPreferences.getValue("eumed_sonification_ROLE", "N/A");
            // Get the SONIFICATION RENEWAL from the portlet preferences for the EUMED VO
            String eumed_sonification_RENEWAL = portletPreferences.getValue("eumed_sonification_RENEWAL", "checked");
            // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the EUMED VO
            String eumed_sonification_DISABLEVOMS = portletPreferences.getValue("eumed_sonification_DISABLEVOMS", "unchecked");
                                    
            // Fetching all the WMS Endpoints for the EUMED VO
            String eumed_WMS = "";
            if (eumed_sonification_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (eumed_sonification_WMS!=null) {
                    //log.info("length="+eumed_sonification_WMS.length);
                    for (int i = 0; i < eumed_sonification_WMS.length; i++)
                        if (!(eumed_sonification_WMS[i].trim().equals("N/A")) ) 
                            eumed_WMS += eumed_sonification_WMS[i] + " ";                        
                } else { log.info("WMS not set for EUMED!"); eumed_sonification_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("eumed_sonification_INFRASTRUCTURE", eumed_sonification_INFRASTRUCTURE.trim());
            request.setAttribute("eumed_sonification_VONAME", eumed_sonification_VONAME.trim());
            request.setAttribute("eumed_sonification_TOPBDII", eumed_sonification_TOPBDII.trim());
            request.setAttribute("eumed_sonification_WMS", eumed_WMS);
            request.setAttribute("eumed_sonification_ETOKENSERVER", eumed_sonification_ETOKENSERVER.trim());
            request.setAttribute("eumed_sonification_MYPROXYSERVER", eumed_sonification_MYPROXYSERVER.trim());
            request.setAttribute("eumed_sonification_PORT", eumed_sonification_PORT.trim());
            request.setAttribute("eumed_sonification_ROBOTID", eumed_sonification_ROBOTID.trim());
            request.setAttribute("eumed_sonification_ROLE", eumed_sonification_ROLE.trim());
            request.setAttribute("eumed_sonification_RENEWAL", eumed_sonification_RENEWAL);
            request.setAttribute("eumed_sonification_DISABLEVOMS", eumed_sonification_DISABLEVOMS);

            //request.setAttribute("sonification_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("sonification_APPID", sonification_APPID.trim());
            request.setAttribute("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
            request.setAttribute("sonification_SOFTWARE", sonification_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }

        if (gisela_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[5]="gisela";
            // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the GISELA VO
            String gisela_sonification_INFRASTRUCTURE = portletPreferences.getValue("gisela_sonification_INFRASTRUCTURE", "N/A");
            // Get the SONIFICATION VONAME from the portlet preferences for the GISELA VO
            gisela_sonification_VONAME = portletPreferences.getValue("gisela_sonification_VONAME", "N/A");
            // Get the SONIFICATION TOPPBDII from the portlet preferences for the GISELA VO
            gisela_sonification_TOPBDII = portletPreferences.getValue("gisela_sonification_TOPBDII", "N/A");
            // Get the SONIFICATION WMS from the portlet preferences for the GISELA VO
            String[] gisela_sonification_WMS = portletPreferences.getValues("gisela_sonification_WMS", new String[5]);
            // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the GISELA VO
            String gisela_sonification_ETOKENSERVER = portletPreferences.getValue("gisela_sonification_ETOKENSERVER", "N/A");
            // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the GISELA VO
            String gisela_sonification_MYPROXYSERVER = portletPreferences.getValue("gisela_sonification_MYPROXYSERVER", "N/A");
            // Get the SONIFICATION PORT from the portlet preferences for the GISELA VO
            String gisela_sonification_PORT = portletPreferences.getValue("gisela_sonification_PORT", "N/A");
            // Get the SONIFICATION ROBOTID from the portlet preferences for the GISELA VO
            String gisela_sonification_ROBOTID = portletPreferences.getValue("gisela_sonification_ROBOTID", "N/A");
            // Get the SONIFICATION ROLE from the portlet preferences for the GISELA VO
            String gisela_sonification_ROLE = portletPreferences.getValue("gisela_sonification_ROLE", "N/A");
            // Get the SONIFICATION RENEWAL from the portlet preferences for the GISELA VO
            String gisela_sonification_RENEWAL = portletPreferences.getValue("gisela_sonification_RENEWAL", "checked");
            // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GISELA VO
            String gisela_sonification_DISABLEVOMS = portletPreferences.getValue("gisela_sonification_DISABLEVOMS", "unchecked");              
            
            // Fetching all the WMS Endpoints for the GISELA VO
            String gisela_WMS = "";
            if (gisela_sonification_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (gisela_sonification_WMS!=null) {
                    //log.info("length="+gisela_sonification_WMS.length);
                    for (int i = 0; i < gisela_sonification_WMS.length; i++)
                        if (!(gisela_sonification_WMS[i].trim().equals("N/A")) ) 
                            gisela_WMS += gisela_sonification_WMS[i] + " ";                        
                } else { log.info("WMS not set for GISELA!"); gisela_sonification_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("gisela_sonification_INFRASTRUCTURE", gisela_sonification_INFRASTRUCTURE.trim());
            request.setAttribute("gisela_sonification_VONAME", gisela_sonification_VONAME.trim());
            request.setAttribute("gisela_sonification_TOPBDII", gisela_sonification_TOPBDII.trim());
            request.setAttribute("gisela_sonification_WMS", gisela_WMS);
            request.setAttribute("gisela_sonification_ETOKENSERVER", gisela_sonification_ETOKENSERVER.trim());
            request.setAttribute("gisela_sonification_MYPROXYSERVER", gisela_sonification_MYPROXYSERVER.trim());
            request.setAttribute("gisela_sonification_PORT", gisela_sonification_PORT.trim());
            request.setAttribute("gisela_sonification_ROBOTID", gisela_sonification_ROBOTID.trim());
            request.setAttribute("gisela_sonification_ROLE", gisela_sonification_ROLE.trim());
            request.setAttribute("gisela_sonification_RENEWAL", gisela_sonification_RENEWAL);
            request.setAttribute("gisela_sonification_DISABLEVOMS", gisela_sonification_DISABLEVOMS);

            //request.setAttribute("sonification_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("sonification_APPID", sonification_APPID.trim());
            request.setAttribute("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
            request.setAttribute("sonification_SOFTWARE", sonification_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (sagrid_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
        {
            infras[6]="sagrid";
            // Get the SONIFICATION INFRASTRUCTURE from the portlet preferences for the SAGRID VO
            String sagrid_sonification_INFRASTRUCTURE = portletPreferences.getValue("sagrid_sonification_INFRASTRUCTURE", "N/A");
            // Get the SONIFICATION VONAME from the portlet preferences for the SAGRID VO
            sagrid_sonification_VONAME = portletPreferences.getValue("sagrid_sonification_VONAME", "N/A");
            // Get the SONIFICATION TOPPBDII from the portlet preferences for the SAGRID VO
            sagrid_sonification_TOPBDII = portletPreferences.getValue("sagrid_sonification_TOPBDII", "N/A");
            // Get the SONIFICATION WMS from the portlet preferences for the SAGRID VO
            String[] sagrid_sonification_WMS = portletPreferences.getValues("sagrid_sonification_WMS", new String[5]);
            // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the SAGRID VO
            String sagrid_sonification_ETOKENSERVER = portletPreferences.getValue("sagrid_sonification_ETOKENSERVER", "N/A");
            // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the SAGRID VO
            String sagrid_sonification_MYPROXYSERVER = portletPreferences.getValue("sagrid_sonification_MYPROXYSERVER", "N/A");
            // Get the SONIFICATION PORT from the portlet preferences for the SAGRID VO
            String sagrid_sonification_PORT = portletPreferences.getValue("sagrid_sonification_PORT", "N/A");
            // Get the SONIFICATION ROBOTID from the portlet preferences for the SAGRID VO
            String sagrid_sonification_ROBOTID = portletPreferences.getValue("sagrid_sonification_ROBOTID", "N/A");
            // Get the SONIFICATION ROLE from the portlet preferences for the SAGRID VO
            String sagrid_sonification_ROLE = portletPreferences.getValue("sagrid_sonification_ROLE", "N/A");
            // Get the SONIFICATION RENEWAL from the portlet preferences for the SAGRID VO
            String sagrid_sonification_RENEWAL = portletPreferences.getValue("sagrid_sonification_RENEWAL", "checked");
            // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the SAGRID VO
            String sagrid_sonification_DISABLEVOMS = portletPreferences.getValue("sagrid_sonification_DISABLEVOMS", "unchecked");              
            // Fetching all the WMS Endpoints for the SAGRID VO
            String sagrid_WMS = "";
            if (sagrid_sonification_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (sagrid_sonification_WMS!=null) {
                    //log.info("length="+sagrid_sonification_WMS.length);
                    for (int i = 0; i < sagrid_sonification_WMS.length; i++)
                        if (!(sagrid_sonification_WMS[i].trim().equals("N/A")) ) 
                            sagrid_WMS += sagrid_sonification_WMS[i] + " ";                        
                } else { log.info("WMS not set for SAGRID!"); sagrid_sonification_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("sagrid_sonification_INFRASTRUCTURE", sagrid_sonification_INFRASTRUCTURE.trim());
            request.setAttribute("sagrid_sonification_VONAME", sagrid_sonification_VONAME.trim());
            request.setAttribute("sagrid_sonification_TOPBDII", sagrid_sonification_TOPBDII.trim());
            request.setAttribute("sagrid_sonification_WMS", sagrid_WMS);
            request.setAttribute("sagrid_sonification_ETOKENSERVER", sagrid_sonification_ETOKENSERVER.trim());
            request.setAttribute("sagrid_sonification_MYPROXYSERVER", sagrid_sonification_MYPROXYSERVER.trim());
            request.setAttribute("sagrid_sonification_PORT", sagrid_sonification_PORT.trim());
            request.setAttribute("sagrid_sonification_ROBOTID", sagrid_sonification_ROBOTID.trim());
            request.setAttribute("sagrid_sonification_ROLE", sagrid_sonification_ROLE.trim());
            request.setAttribute("sagrid_sonification_RENEWAL", sagrid_sonification_RENEWAL);
            request.setAttribute("sagrid_sonification_DISABLEVOMS", sagrid_sonification_DISABLEVOMS);

            //request.setAttribute("sonification_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("sonification_APPID", sonification_APPID.trim());
            request.setAttribute("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
            request.setAttribute("sonification_SOFTWARE", sonification_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        // Save in the preferences the list of supported infrastructures 
        request.setAttribute("sonification_ENABLEINFRASTRUCTURE", infras);        

         HashMap<String,Properties> GPS_table = new HashMap<String, Properties>();
         HashMap<String,Properties> GPS_queue = new HashMap<String, Properties>();

         // ********************************************************
         List<String> CEqueues_cometa = null;                  
         List<String> CEqueues_garuda = null;
         List<String> CEqueues_gridit = null;
         List<String> CEqueues_gilda = null;
         List<String> CEqueues_eumed = null;
         List<String> CEqueues_gisela = null;
         List<String> CEqueues_sagrid = null;
         
         List<String> CEs_list_cometa = null;
         List<String> CEs_list_garuda = null;
         List<String> CEs_list_gridit = null;
         List<String> CEs_list_gilda = null;
         List<String> CEs_list_eumed = null;
         List<String> CEs_list_gisela = null;
         List<String> CEs_list_sagrid = null;
         
         BDII bdii = null;

         try {
                if (cometa_sonification_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!cometa_sonification_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<COMETA>*RESOURCES*-----");
                    bdii = new BDII(new URI(cometa_sonification_TOPBDII));
                    CEs_list_cometa = 
                            getListofCEForSoftwareTag(cometa_sonification_VONAME,
                                                      cometa_sonification_TOPBDII,
                                                      sonification_SOFTWARE);
                    
                    CEqueues_cometa = 
                            bdii.queryCEQueues(cometa_sonification_VONAME);
                }
                
                //=========================================================
                // IMPORTANT: THIS FIX IS ONLY FOR INSTANCIATE THE 
                //            CHAIN INTEROPERABILITY DEMO                
                //=========================================================
                // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
                if (garuda_sonification_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!garuda_sonification_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GARUDA>*RESOURCES*-----");
                    CEs_list_garuda = new ArrayList();
                    CEs_list_garuda.add("xn03.ctsf.cdacb.in");
                    
                    CEqueues_garuda = new ArrayList();
                    CEqueues_garuda.add("wsgram://xn03.ctsf.cdacb.in:8443/GW");
                }
                // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
             
                if (gridit_sonification_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!gridit_sonification_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GRIDIT>*RESOURCES*-----");
                    bdii = new BDII(new URI(gridit_sonification_TOPBDII));
                    CEs_list_gridit = 
                                getListofCEForSoftwareTag(gridit_sonification_VONAME,
                                                          gridit_sonification_TOPBDII,
                                                          sonification_SOFTWARE);
                    
                    CEqueues_gridit = 
                                bdii.queryCEQueues(gridit_sonification_VONAME);
                }
                
                if (gilda_sonification_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!gilda_sonification_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GILDA>*RESOURCES*-----");
                    bdii = new BDII(new URI(gilda_sonification_TOPBDII));
                    CEs_list_gilda = 
                                getListofCEForSoftwareTag(gilda_sonification_VONAME,
                                                          gilda_sonification_TOPBDII,
                                                          sonification_SOFTWARE);
                    
                    CEqueues_gilda = 
                            bdii.queryCEQueues(gilda_sonification_VONAME);
                }
                
                if (eumed_sonification_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!eumed_sonification_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<EUMED>*RESOURCES*-----");
                    bdii = new BDII(new URI(eumed_sonification_TOPBDII));
                    CEs_list_eumed = 
                                getListofCEForSoftwareTag(eumed_sonification_VONAME,
                                                          eumed_sonification_TOPBDII,
                                                          sonification_SOFTWARE);
                    
                    CEqueues_eumed = 
                            bdii.queryCEQueues(eumed_sonification_VONAME);
                }
                
                if (gisela_sonification_ENABLEINFRASTRUCTURE.equals("checked") &&
                   (!gisela_sonification_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GISELA>*RESOURCES*-----");
                    bdii = new BDII(new URI(gisela_sonification_TOPBDII));
                    CEs_list_gisela = 
                                getListofCEForSoftwareTag(gisela_sonification_VONAME,
                                                          gisela_sonification_TOPBDII,
                                                          sonification_SOFTWARE);
                    
                    CEqueues_gisela = 
                            bdii.queryCEQueues(gisela_sonification_VONAME);
                }
                
                if (sagrid_sonification_ENABLEINFRASTRUCTURE.equals("checked") &&
                   (!sagrid_sonification_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<SAGRID>*RESOURCES*-----");
                    bdii = new BDII(new URI(sagrid_sonification_TOPBDII));
                    CEs_list_sagrid = 
                                getListofCEForSoftwareTag(sagrid_sonification_VONAME,
                                                          sagrid_sonification_TOPBDII,
                                                          sonification_SOFTWARE);
                    
                    CEqueues_sagrid = 
                            bdii.queryCEQueues(sagrid_sonification_VONAME);
                }
                
                // Merging the list of CEs and queues
                List<String> CEs_list_TOT = new ArrayList<String>();
                if (cometa_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_cometa);
                if (gridit_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_gridit);
                // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
                if (garuda_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_garuda);
                // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
                if (gilda_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_gilda);
                if (eumed_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_eumed);
                if (gisela_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_gisela);
                if (sagrid_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_sagrid);
                                
                List<String> CEs_queue_TOT = new ArrayList<String>();
                if (cometa_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_cometa);
                if (gridit_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_gridit);
                if (gilda_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_gilda);                
                if (eumed_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_eumed);
                if (gisela_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_gisela);
                if (sagrid_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_sagrid); 
                
                //=========================================================
                // IMPORTANT: INSTANCIATE THE UsersTrackingDBInterface
                //            CLASS USING THE EMPTY CONSTRUCTOR WHEN
                //            WHEN THE PORTLET IS DEPLOYED IN PRODUCTION!!!
                //=========================================================
                /*UsersTrackingDBInterface DBInterface =
                        new UsersTrackingDBInterface(
                                TRACKING_DB_HOSTNAME.trim(),
                                TRACKING_DB_USERNAME.trim(),
                                TRACKING_DB_PASSWORD.trim());*/
                
                UsersTrackingDBInterface DBInterface =
                        new UsersTrackingDBInterface();
                    
                if ( (CEs_list_TOT != null) && (!CEs_queue_TOT.isEmpty()) )
                {
                    log.info("NOT EMPTY LIST!");
                    // Fetching the list of CEs publushing the SW
                    for (String CE:CEs_list_TOT) 
                    {
                        //log.info("Fetching the CE="+CE);
                        Properties coordinates = new Properties();
                        Properties queue = new Properties();

                        float coords[] = DBInterface.getCECoordinate(CE);                        

                        String GPS_LAT = Float.toString(coords[0]);
                        String GPS_LNG = Float.toString(coords[1]);

                        coordinates.setProperty("LAT", GPS_LAT);
                        coordinates.setProperty("LNG", GPS_LNG);
                        log.info("Fetching CE settings for [ " + CE + 
                                 " ] Coordinates [ " + GPS_LAT + 
                                 ", " + GPS_LNG + " ]");

                        // Fetching the Queues
                        for (String CEqueue:CEs_queue_TOT) {
                                if (CEqueue.contains(CE))
                                    queue.setProperty("QUEUE", CEqueue);
                        }

                        // Saving the GPS location in a Java HashMap
                        GPS_table.put(CE, coordinates);

                        // Saving the queue in a Java HashMap
                        GPS_queue.put(CE, queue);
                    }
                } else log.info ("EMPTY LIST!");
             } catch (URISyntaxException ex) {
               Logger.getLogger(Sonification.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException e) {}

            // Checking the HashMap
            Set set = GPS_table.entrySet();
            Iterator iter = set.iterator();
            while ( iter.hasNext() )
            {
                Map.Entry entry = (Map.Entry)iter.next();
                log.info(" - GPS location of the CE " +
                           entry.getKey() + " => " + entry.getValue());
            }

            // Checking the HashMap
            set = GPS_queue.entrySet();
            iter = set.iterator();
            while ( iter.hasNext() )
            {
                Map.Entry entry = (Map.Entry)iter.next();
                log.info(" - Queue " +
                           entry.getKey() + " => " + entry.getValue());
            }

            Gson gson = new GsonBuilder().create();
            request.setAttribute ("GPS_table", gson.toJson(GPS_table));
            request.setAttribute ("GPS_queue", gson.toJson(GPS_queue));

            // ********************************************************

            dispatcher = getPortletContext().getRequestDispatcher("/view.jsp");       
            dispatcher.include(request, response);
    }

    // The init method will be called when installing for the first time the portlet
    // This is the right time to setup the default values into the preferences
    @Override
    public void init() throws PortletException {
        super.init();
    }

    @Override
    public void processAction(ActionRequest request,
                              ActionResponse response)
                throws PortletException, IOException {

        String action = "";

        // Get the action to be processed from the request
        action = request.getParameter("ActionEvent");

        // Determine the username and the email address
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);        
        User user = themeDisplay.getUser();
        String username = user.getScreenName();
        String emailAddress = user.getDisplayEmailAddress();        

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();                
        
        if (action.equals("CONFIG_SONIFICATION_PORTLET")) {
            log.info("\nPROCESS ACTION => " + action);
            
            // Get the SONIFICATION APPID from the portlet request
            String sonification_APPID = request.getParameter("sonification_APPID");
            // Get the SONIFICATION OUTPUT from the portlet request
            String sonification_OUTPUT_PATH = request.getParameter("sonification_OUTPUT_PATH");
            // Get the SONIFICATION SOFTWARE from the portlet request
            String sonification_SOFTWARE = request.getParameter("sonification_SOFTWARE");
            // Get the TRACKING_DB_HOSTNAME from the portlet request
            String TRACKING_DB_HOSTNAME = request.getParameter("TRACKING_DB_HOSTNAME");
            // Get the TRACKING_DB_USERNAME from the portlet request
            String TRACKING_DB_USERNAME = request.getParameter("TRACKING_DB_USERNAME");
            // Get the TRACKING_DB_PASSWORD from the portlet request
            String TRACKING_DB_PASSWORD = request.getParameter("TRACKING_DB_PASSWORD");
            // Get the SMTP_HOST from the portlet request
            String SMTP_HOST = request.getParameter("SMTP_HOST");
            // Get the SENDER_MAIL from the portlet request
            String SENDER_MAIL = request.getParameter("SENDER_MAIL");
            String[] infras = new String[7];
            
            String cometa_sonification_ENABLEINFRASTRUCTURE = "unchecked";
            String garuda_sonification_ENABLEINFRASTRUCTURE = "unchecked";
            String gridit_sonification_ENABLEINFRASTRUCTURE = "unchecked";
            String gilda_sonification_ENABLEINFRASTRUCTURE = "unchecked";
            String eumed_sonification_ENABLEINFRASTRUCTURE = "unchecked";
            String gisela_sonification_ENABLEINFRASTRUCTURE = "unchecked";
            String sagrid_sonification_ENABLEINFRASTRUCTURE = "unchecked";
        
            String[] sonification_INFRASTRUCTURES = request.getParameterValues("sonification_ENABLEINFRASTRUCTURES");
        
            if (sonification_INFRASTRUCTURES != null) {
                Arrays.sort(sonification_INFRASTRUCTURES);                    
                cometa_sonification_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(sonification_INFRASTRUCTURES, "cometa") >= 0 ? "checked" : "unchecked";
                garuda_sonification_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(sonification_INFRASTRUCTURES, "garuda") >= 0 ? "checked" : "unchecked";
                gridit_sonification_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(sonification_INFRASTRUCTURES, "gridit") >= 0 ? "checked" : "unchecked";
                gilda_sonification_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(sonification_INFRASTRUCTURES, "gilda") >= 0 ? "checked" : "unchecked";
                eumed_sonification_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(sonification_INFRASTRUCTURES, "eumed") >= 0 ? "checked" : "unchecked";
                gisela_sonification_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(sonification_INFRASTRUCTURES, "gisela") >= 0 ? "checked" : "unchecked";
                sagrid_sonification_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(sonification_INFRASTRUCTURES, "sagrid") >= 0 ? "checked" : "unchecked";
            }
        
            if (cometa_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[0]="cometa";
                // Get the SONIFICATION INFRASTRUCTURE from the portlet request for the COMETA VO
                String cometa_sonification_INFRASTRUCTURE = request.getParameter("cometa_sonification_INFRASTRUCTURE");
                // Get the SONIFICATION VONAME from the portlet request for the COMETA VO
                String cometa_sonification_VONAME = request.getParameter("cometa_sonification_VONAME");
                // Get the SONIFICATION TOPBDII from the portlet request for the COMETA VO
                String cometa_sonification_TOPBDII = request.getParameter("cometa_sonification_TOPBDII");
                // Get the SONIFICATION WMS from the portlet request for the COMETA VO
                String[] cometa_sonification_WMS = request.getParameterValues("cometa_sonification_WMS");
                // Get the SONIFICATION ETOKENSERVER from the portlet request for the COMETA VO
                String cometa_sonification_ETOKENSERVER = request.getParameter("cometa_sonification_ETOKENSERVER");
                // Get the SONIFICATION MYPROXYSERVER from the portlet request for the COMETA VO
                String cometa_sonification_MYPROXYSERVER = request.getParameter("cometa_sonification_MYPROXYSERVER");
                // Get the SONIFICATION PORT from the portlet request for the COMETA VO
                String cometa_sonification_PORT = request.getParameter("cometa_sonification_PORT");
                // Get the SONIFICATION ROBOTID from the portlet request for the COMETA VO
                String cometa_sonification_ROBOTID = request.getParameter("cometa_sonification_ROBOTID");
                // Get the SONIFICATION ROLE from the portlet request for the COMETA VO
                String cometa_sonification_ROLE = request.getParameter("cometa_sonification_ROLE");
                // Get the SONIFICATION OPTIONS from the portlet request for the COMETA VO
                String[] cometa_sonification_OPTIONS = request.getParameterValues("cometa_sonification_OPTIONS");                

                String cometa_sonification_RENEWAL = "";
                String cometa_sonification_DISABLEVOMS = "";

                if (cometa_sonification_OPTIONS == null){
                    cometa_sonification_RENEWAL = "checked";
                    cometa_sonification_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(cometa_sonification_OPTIONS);
                    // Get the SONIFICATION RENEWAL from the portlet preferences for the COMETA VO
                    cometa_sonification_RENEWAL = Arrays.binarySearch(cometa_sonification_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the COMETA VO
                    cometa_sonification_DISABLEVOMS = Arrays.binarySearch(cometa_sonification_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < cometa_sonification_WMS.length; i++)
                    if ( cometa_sonification_WMS[i]!=null && (!cometa_sonification_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] cometa_sonification_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    cometa_sonification_WMS_trimmed[i]=cometa_sonification_WMS[i].trim();
                    log.info ("\n\nCOMETA [" + i + "] WMS=[" + cometa_sonification_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("cometa_sonification_INFRASTRUCTURE", cometa_sonification_INFRASTRUCTURE.trim());
                portletPreferences.setValue("cometa_sonification_VONAME", cometa_sonification_VONAME.trim());
                portletPreferences.setValue("cometa_sonification_TOPBDII", cometa_sonification_TOPBDII.trim());
                portletPreferences.setValues("cometa_sonification_WMS", cometa_sonification_WMS_trimmed);
                portletPreferences.setValue("cometa_sonification_ETOKENSERVER", cometa_sonification_ETOKENSERVER.trim());
                portletPreferences.setValue("cometa_sonification_MYPROXYSERVER", cometa_sonification_MYPROXYSERVER.trim());
                portletPreferences.setValue("cometa_sonification_PORT", cometa_sonification_PORT.trim());
                portletPreferences.setValue("cometa_sonification_ROBOTID", cometa_sonification_ROBOTID.trim());
                portletPreferences.setValue("cometa_sonification_ROLE", cometa_sonification_ROLE.trim());
                portletPreferences.setValue("cometa_sonification_RENEWAL", cometa_sonification_RENEWAL);
                portletPreferences.setValue("cometa_sonification_DISABLEVOMS", cometa_sonification_DISABLEVOMS);                
                
                portletPreferences.setValue("sonification_APPID", sonification_APPID.trim());
                portletPreferences.setValue("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
                portletPreferences.setValue("sonification_SOFTWARE", sonification_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the SONIFICATION portlet preferences ..."
                    + "\ncometa_sonification_INFRASTRUCTURE: " + cometa_sonification_INFRASTRUCTURE
                    + "\ncometa_sonification_VONAME: " + cometa_sonification_VONAME
                    + "\ncometa_sonification_TOPBDII: " + cometa_sonification_TOPBDII                    
                    + "\ncometa_sonification_ETOKENSERVER: " + cometa_sonification_ETOKENSERVER
                    + "\ncometa_sonification_MYPROXYSERVER: " + cometa_sonification_MYPROXYSERVER
                    + "\ncometa_sonification_PORT: " + cometa_sonification_PORT
                    + "\ncometa_sonification_ROBOTID: " + cometa_sonification_ROBOTID
                    + "\ncometa_sonification_ROLE: " + cometa_sonification_ROLE
                    + "\ncometa_sonification_RENEWAL: " + cometa_sonification_RENEWAL
                    + "\ncometa_sonification_DISABLEVOMS: " + cometa_sonification_DISABLEVOMS
                        
                    + "\n\nsonification_ENABLEINFRASTRUCTURE: " + "cometa"
                    + "\nsonification_APPID: " + sonification_APPID
                    + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                    + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
            }
            
            if (garuda_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[1]="garuda";
                // Get the SONIFICATION INFRASTRUCTURE from the portlet request for the GARUDA VO
                String garuda_sonification_INFRASTRUCTURE = request.getParameter("garuda_sonification_INFRASTRUCTURE");
                // Get the SONIFICATION VONAME from the portlet request for the GARUDA VO
                String garuda_sonification_VONAME = request.getParameter("garuda_sonification_VONAME");
                // Get the SONIFICATION TOPBDII from the portlet request for the GARUDA VO
                String garuda_sonification_TOPBDII = request.getParameter("garuda_sonification_TOPBDII");
                // Get the SONIFICATION WMS from the portlet request for the GARUDA VO
                String[] garuda_sonification_WMS = request.getParameterValues("garuda_sonification_WMS");
                // Get the SONIFICATION ETOKENSERVER from the portlet request for the GARUDA VO
                String garuda_sonification_ETOKENSERVER = request.getParameter("garuda_sonification_ETOKENSERVER");
                // Get the SONIFICATION MYPROXYSERVER from the portlet request for the GARUDA VO
                String garuda_sonification_MYPROXYSERVER = request.getParameter("garuda_sonification_MYPROXYSERVER");
                // Get the SONIFICATION PORT from the portlet request for the GARUDA VO
                String garuda_sonification_PORT = request.getParameter("garuda_sonification_PORT");
                // Get the SONIFICATION ROBOTID from the portlet request for the GARUDA VO
                String garuda_sonification_ROBOTID = request.getParameter("garuda_sonification_ROBOTID");
                // Get the SONIFICATION ROLE from the portlet request for the GARUDA VO
                String garuda_sonification_ROLE = request.getParameter("garuda_sonification_ROLE");
                // Get the SONIFICATION OPTIONS from the portlet request for the GARUDA VO
                String[] garuda_sonification_OPTIONS = request.getParameterValues("garuda_sonification_OPTIONS"); 

                String garuda_sonification_RENEWAL = "";
                String garuda_sonification_DISABLEVOMS = "";

                if (garuda_sonification_OPTIONS == null){
                    garuda_sonification_RENEWAL = "checked";
                    garuda_sonification_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(garuda_sonification_OPTIONS);
                    // Get the SONIFICATION RENEWAL from the portlet preferences for the GARUDA VO
                    garuda_sonification_RENEWAL = Arrays.binarySearch(garuda_sonification_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GARUDA VO
                    garuda_sonification_DISABLEVOMS = Arrays.binarySearch(garuda_sonification_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < garuda_sonification_WMS.length; i++)
                    if ( garuda_sonification_WMS[i]!=null && (!garuda_sonification_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] garuda_sonification_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    garuda_sonification_WMS_trimmed[i]=garuda_sonification_WMS[i].trim();
                    log.info ("\n\nGARUDA [" + i + "] WSGRAM=[" + garuda_sonification_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("garuda_sonification_INFRASTRUCTURE", garuda_sonification_INFRASTRUCTURE.trim());
                portletPreferences.setValue("garuda_sonification_VONAME", garuda_sonification_VONAME.trim());
                portletPreferences.setValue("garuda_sonification_TOPBDII", garuda_sonification_TOPBDII.trim());
                portletPreferences.setValues("garuda_sonification_WMS", garuda_sonification_WMS_trimmed);
                portletPreferences.setValue("garuda_sonification_ETOKENSERVER", garuda_sonification_ETOKENSERVER.trim());
                portletPreferences.setValue("garuda_sonification_MYPROXYSERVER", garuda_sonification_MYPROXYSERVER.trim());
                portletPreferences.setValue("garuda_sonification_PORT", garuda_sonification_PORT.trim());
                portletPreferences.setValue("garuda_sonification_ROBOTID", garuda_sonification_ROBOTID.trim());
                portletPreferences.setValue("garuda_sonification_ROLE", garuda_sonification_ROLE.trim());
                portletPreferences.setValue("garuda_sonification_RENEWAL", garuda_sonification_RENEWAL);
                portletPreferences.setValue("garuda_sonification_DISABLEVOMS", garuda_sonification_DISABLEVOMS);
                
                portletPreferences.setValue("sonification_APPID", sonification_APPID.trim());
                portletPreferences.setValue("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
                portletPreferences.setValue("sonification_SOFTWARE", sonification_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the SONIFICATION portlet preferences ..."
                + "\ngaruda_sonification_INFRASTRUCTURE: " + garuda_sonification_INFRASTRUCTURE
                + "\ngaruda_sonification_VONAME: " + garuda_sonification_VONAME
                + "\ngaruda_sonification_TOPBDII: " + garuda_sonification_TOPBDII                    
                + "\ngaruda_sonification_ETOKENSERVER: " + garuda_sonification_ETOKENSERVER
                + "\ngaruda_sonification_MYPROXYSERVER: " + garuda_sonification_MYPROXYSERVER
                + "\ngaruda_sonification_PORT: " + garuda_sonification_PORT
                + "\ngaruda_sonification_ROBOTID: " + garuda_sonification_ROBOTID
                + "\ngaruda_sonification_ROLE: " + garuda_sonification_ROLE
                + "\ngaruda_sonification_RENEWAL: " + garuda_sonification_RENEWAL
                + "\ngaruda_sonification_DISABLEVOMS: " + garuda_sonification_DISABLEVOMS
                        
                + "\n\nsonification_ENABLEINFRASTRUCTURE: " + "garuda"
                + "\nsonification_APPID: " + sonification_APPID
                + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
            }

            if (gridit_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[2]="gridit";
                // Get the SONIFICATION INFRASTRUCTURE from the portlet request for the GRIDIT VO
                String gridit_sonification_INFRASTRUCTURE = request.getParameter("gridit_sonification_INFRASTRUCTURE");
                // Get the SONIFICATION VONAME from the portlet request for the GRIDIT VO
                String gridit_sonification_VONAME = request.getParameter("gridit_sonification_VONAME");
                // Get the SONIFICATION TOPBDII from the portlet request for the GRIDIT VO
                String gridit_sonification_TOPBDII = request.getParameter("gridit_sonification_TOPBDII");
                // Get the SONIFICATION WMS from the portlet request for the GRIDIT VO
                String[] gridit_sonification_WMS = request.getParameterValues("gridit_sonification_WMS");
                // Get the SONIFICATION ETOKENSERVER from the portlet request for the GRIDIT VO
                String gridit_sonification_ETOKENSERVER = request.getParameter("gridit_sonification_ETOKENSERVER");
                // Get the SONIFICATION MYPROXYSERVER from the portlet request for the GRIDIT VO
                String gridit_sonification_MYPROXYSERVER = request.getParameter("gridit_sonification_MYPROXYSERVER");
                // Get the SONIFICATION PORT from the portlet request for the GRIDIT VO
                String gridit_sonification_PORT = request.getParameter("gridit_sonification_PORT");
                // Get the SONIFICATION ROBOTID from the portlet request for the GRIDIT VO
                String gridit_sonification_ROBOTID = request.getParameter("gridit_sonification_ROBOTID");
                // Get the SONIFICATION ROLE from the portlet request for the GRIDIT VO
                String gridit_sonification_ROLE = request.getParameter("gridit_sonification_ROLE");
                // Get the SONIFICATION OPTIONS from the portlet request for the GRIDIT VO
                String[] gridit_sonification_OPTIONS = request.getParameterValues("gridit_sonification_OPTIONS");                

                String gridit_sonification_RENEWAL = "";
                String gridit_sonification_DISABLEVOMS = "";

                if (gridit_sonification_OPTIONS == null){
                    gridit_sonification_RENEWAL = "checked";
                    gridit_sonification_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(gridit_sonification_OPTIONS);
                    // Get the SONIFICATION RENEWAL from the portlet preferences for the GRIDIT VO
                    gridit_sonification_RENEWAL = Arrays.binarySearch(gridit_sonification_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GRIDIT VO
                    gridit_sonification_DISABLEVOMS = Arrays.binarySearch(gridit_sonification_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < gridit_sonification_WMS.length; i++)
                    if ( gridit_sonification_WMS[i]!=null && (!gridit_sonification_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] gridit_sonification_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    gridit_sonification_WMS_trimmed[i]=gridit_sonification_WMS[i].trim();
                    log.info ("\n\nGRIDIT [" + i + "] WMS=[" + gridit_sonification_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("gridit_sonification_INFRASTRUCTURE", gridit_sonification_INFRASTRUCTURE.trim());
                portletPreferences.setValue("gridit_sonification_VONAME", gridit_sonification_VONAME.trim());
                portletPreferences.setValue("gridit_sonification_TOPBDII", gridit_sonification_TOPBDII.trim());
                portletPreferences.setValues("gridit_sonification_WMS", gridit_sonification_WMS_trimmed);
                portletPreferences.setValue("gridit_sonification_ETOKENSERVER", gridit_sonification_ETOKENSERVER.trim());
                portletPreferences.setValue("gridit_sonification_MYPROXYSERVER", gridit_sonification_MYPROXYSERVER.trim());
                portletPreferences.setValue("gridit_sonification_PORT", gridit_sonification_PORT.trim());
                portletPreferences.setValue("gridit_sonification_ROBOTID", gridit_sonification_ROBOTID.trim());
                portletPreferences.setValue("gridit_sonification_ROLE", gridit_sonification_ROLE.trim());
                portletPreferences.setValue("gridit_sonification_RENEWAL", gridit_sonification_RENEWAL);
                portletPreferences.setValue("gridit_sonification_DISABLEVOMS", gridit_sonification_DISABLEVOMS);                
                
                portletPreferences.setValue("sonification_APPID", sonification_APPID.trim());
                portletPreferences.setValue("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
                portletPreferences.setValue("sonification_SOFTWARE", sonification_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the SONIFICATION portlet preferences ..."
                + "\ngridit_sonification_INFRASTRUCTURE: " + gridit_sonification_INFRASTRUCTURE
                + "\ngridit_sonification_VONAME: " + gridit_sonification_VONAME
                + "\ngridit_sonification_TOPBDII: " + gridit_sonification_TOPBDII                    
                + "\ngridit_sonification_ETOKENSERVER: " + gridit_sonification_ETOKENSERVER
                + "\ngridit_sonification_MYPROXYSERVER: " + gridit_sonification_MYPROXYSERVER
                + "\ngridit_sonification_PORT: " + gridit_sonification_PORT
                + "\ngridit_sonification_ROBOTID: " + gridit_sonification_ROBOTID
                + "\ngridit_sonification_ROLE: " + gridit_sonification_ROLE
                + "\ngridit_sonification_RENEWAL: " + gridit_sonification_RENEWAL
                + "\ngridit_sonification_DISABLEVOMS: " + gridit_sonification_DISABLEVOMS
                        
                + "\n\nsonification_ENABLEINFRASTRUCTURE: " + "gridit"
                + "\nsonification_APPID: " + sonification_APPID
                + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
            }
            
            if (gilda_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[3]="gilda";
                // Get the SONIFICATION INFRASTRUCTURE from the portlet request for the GILDA VO
                String gilda_sonification_INFRASTRUCTURE = request.getParameter("gilda_sonification_INFRASTRUCTURE");
                // Get the SONIFICATION VONAME from the portlet request for the GILDA VO
                String gilda_sonification_VONAME = request.getParameter("gilda_sonification_VONAME");
                // Get the SONIFICATION TOPBDII from the portlet request for the GILDA VO
                String gilda_sonification_TOPBDII = request.getParameter("gilda_sonification_TOPBDII");
                // Get the SONIFICATION WMS from the portlet request for the GILDA VO
                String[] gilda_sonification_WMS = request.getParameterValues("gilda_sonification_WMS");
                // Get the SONIFICATION ETOKENSERVER from the portlet request for the GILDA VO
                String gilda_sonification_ETOKENSERVER = request.getParameter("gilda_sonification_ETOKENSERVER");
                // Get the SONIFICATION MYPROXYSERVER from the portlet request for the GILDA VO
                String gilda_sonification_MYPROXYSERVER = request.getParameter("gilda_sonification_MYPROXYSERVER");
                // Get the SONIFICATION PORT from the portlet request for the GILDA VO
                String gilda_sonification_PORT = request.getParameter("gilda_sonification_PORT");
                // Get the SONIFICATION ROBOTID from the portlet request for the GILDA VO
                String gilda_sonification_ROBOTID = request.getParameter("gilda_sonification_ROBOTID");
                // Get the SONIFICATION ROLE from the portlet request for the GILDA VO
                String gilda_sonification_ROLE = request.getParameter("gilda_sonification_ROLE");
                // Get the SONIFICATION OPTIONS from the portlet request for the GILDA VO
                String[] gilda_sonification_OPTIONS = request.getParameterValues("gilda_sonification_OPTIONS");                

                String gilda_sonification_RENEWAL = "";
                String gilda_sonification_DISABLEVOMS = "";

                if (gilda_sonification_OPTIONS == null){
                    gilda_sonification_RENEWAL = "checked";
                    gilda_sonification_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(gilda_sonification_OPTIONS);
                    // Get the SONIFICATION RENEWAL from the portlet preferences for the GILDA VO
                    gilda_sonification_RENEWAL = Arrays.binarySearch(gilda_sonification_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GILDA VO
                    gilda_sonification_DISABLEVOMS = Arrays.binarySearch(gilda_sonification_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < gilda_sonification_WMS.length; i++)
                    if ( gilda_sonification_WMS[i]!=null && (!gilda_sonification_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] gilda_sonification_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    gilda_sonification_WMS_trimmed[i]=gilda_sonification_WMS[i].trim();
                    log.info ("\n\nCOMETA [" + i + "] WMS=[" + gilda_sonification_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("gilda_sonification_INFRASTRUCTURE", gilda_sonification_INFRASTRUCTURE.trim());
                portletPreferences.setValue("gilda_sonification_VONAME", gilda_sonification_VONAME.trim());
                portletPreferences.setValue("gilda_sonification_TOPBDII", gilda_sonification_TOPBDII.trim());
                portletPreferences.setValues("gilda_sonification_WMS", gilda_sonification_WMS_trimmed);
                portletPreferences.setValue("gilda_sonification_ETOKENSERVER", gilda_sonification_ETOKENSERVER.trim());
                portletPreferences.setValue("gilda_sonification_MYPROXYSERVER", gilda_sonification_MYPROXYSERVER.trim());
                portletPreferences.setValue("gilda_sonification_PORT", gilda_sonification_PORT.trim());
                portletPreferences.setValue("gilda_sonification_ROBOTID", gilda_sonification_ROBOTID.trim());
                portletPreferences.setValue("gilda_sonification_ROLE", gilda_sonification_ROLE.trim());
                portletPreferences.setValue("gilda_sonification_RENEWAL", gilda_sonification_RENEWAL);
                portletPreferences.setValue("gilda_sonification_DISABLEVOMS", gilda_sonification_DISABLEVOMS);
                
                portletPreferences.setValue("sonification_APPID", sonification_APPID.trim());
                portletPreferences.setValue("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
                portletPreferences.setValue("sonification_SOFTWARE", sonification_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the SONIFICATION portlet preferences ..."                    
                    + "\n\ngilda_sonification_INFRASTRUCTURE: " + gilda_sonification_INFRASTRUCTURE
                    + "\ngilda_sonification_VONAME: " + gilda_sonification_VONAME
                    + "\ngilda_sonification_TOPBDII: " + gilda_sonification_TOPBDII                    
                    + "\ngilda_sonification_ETOKENSERVER: " + gilda_sonification_ETOKENSERVER
                    + "\ngilda_sonification_MYPROXYSERVER: " + gilda_sonification_MYPROXYSERVER
                    + "\ngilda_sonification_PORT: " + gilda_sonification_PORT
                    + "\ngilda_sonification_ROBOTID: " + gilda_sonification_ROBOTID
                    + "\ngilda_sonification_ROLE: " + gilda_sonification_ROLE
                    + "\ngilda_sonification_RENEWAL: " + gilda_sonification_RENEWAL
                    + "\ngilda_sonification_DISABLEVOMS: " + gilda_sonification_DISABLEVOMS

                    + "\n\nsonification_ENABLEINFRASTRUCTURE: " + "gilda"
                    + "\nsonification_APPID: " + sonification_APPID
                    + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                    + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
            }

            if (eumed_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[4]="eumed";
                // Get the SONIFICATION INFRASTRUCTURE from the portlet request for the EUMED VO
                String eumed_sonification_INFRASTRUCTURE = request.getParameter("eumed_sonification_INFRASTRUCTURE");
                // Get the SONIFICATION VONAME from the portlet request for the EUMED VO
                String eumed_sonification_VONAME = request.getParameter("eumed_sonification_VONAME");
                // Get the SONIFICATION TOPBDII from the portlet request for the EUMED VO
                String eumed_sonification_TOPBDII = request.getParameter("eumed_sonification_TOPBDII");
                // Get the SONIFICATION WMS from the portlet request for the EUMED VO
                String[] eumed_sonification_WMS = request.getParameterValues("eumed_sonification_WMS");
                // Get the SONIFICATION ETOKENSERVER from the portlet request for the EUMED VO
                String eumed_sonification_ETOKENSERVER = request.getParameter("eumed_sonification_ETOKENSERVER");
                // Get the SONIFICATION MYPROXYSERVER from the portlet request for the EUMED VO
                String eumed_sonification_MYPROXYSERVER = request.getParameter("eumed_sonification_MYPROXYSERVER");
                // Get the SONIFICATION PORT from the portlet request for the EUMED VO
                String eumed_sonification_PORT = request.getParameter("eumed_sonification_PORT");
                // Get the SONIFICATION ROBOTID from the portlet request for the EUMED VO
                String eumed_sonification_ROBOTID = request.getParameter("eumed_sonification_ROBOTID");
                // Get the SONIFICATION ROLE from the portlet request for the EUMED VO
                String eumed_sonification_ROLE = request.getParameter("eumed_sonification_ROLE");
                // Get the SONIFICATION OPTIONS from the portlet request for the EUMED VO
                String[] eumed_sonification_OPTIONS = request.getParameterValues("eumed_sonification_OPTIONS");                

                String eumed_sonification_RENEWAL = "";
                String eumed_sonification_DISABLEVOMS = "";

                if (eumed_sonification_OPTIONS == null){
                    eumed_sonification_RENEWAL = "checked";
                    eumed_sonification_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(eumed_sonification_OPTIONS);
                    // Get the SONIFICATION RENEWAL from the portlet preferences for the EUMED VO
                    eumed_sonification_RENEWAL = Arrays.binarySearch(eumed_sonification_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GRIDIT VO
                    eumed_sonification_DISABLEVOMS = Arrays.binarySearch(eumed_sonification_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < eumed_sonification_WMS.length; i++)
                    if ( eumed_sonification_WMS[i]!=null && (!eumed_sonification_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] eumed_sonification_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    eumed_sonification_WMS_trimmed[i]=eumed_sonification_WMS[i].trim();
                    log.info ("\n\nEUMED [" + i + "] WMS=[" + eumed_sonification_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("eumed_sonification_INFRASTRUCTURE", eumed_sonification_INFRASTRUCTURE.trim());
                portletPreferences.setValue("eumed_sonification_VONAME", eumed_sonification_VONAME.trim());
                portletPreferences.setValue("eumed_sonification_TOPBDII", eumed_sonification_TOPBDII.trim());
                portletPreferences.setValues("eumed_sonification_WMS", eumed_sonification_WMS_trimmed);
                portletPreferences.setValue("eumed_sonification_ETOKENSERVER", eumed_sonification_ETOKENSERVER.trim());
                portletPreferences.setValue("eumed_sonification_MYPROXYSERVER", eumed_sonification_MYPROXYSERVER.trim());
                portletPreferences.setValue("eumed_sonification_PORT", eumed_sonification_PORT.trim());
                portletPreferences.setValue("eumed_sonification_ROBOTID", eumed_sonification_ROBOTID.trim());
                portletPreferences.setValue("eumed_sonification_ROLE", eumed_sonification_ROLE.trim());
                portletPreferences.setValue("eumed_sonification_RENEWAL", eumed_sonification_RENEWAL);
                portletPreferences.setValue("eumed_sonification_DISABLEVOMS", eumed_sonification_DISABLEVOMS); 
                
                portletPreferences.setValue("sonification_APPID", sonification_APPID.trim());
                portletPreferences.setValue("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
                portletPreferences.setValue("sonification_SOFTWARE", sonification_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the SONIFICATION portlet preferences ..."                    
                + "\n\neumed_sonification_INFRASTRUCTURE: " + eumed_sonification_INFRASTRUCTURE
                + "\neumed_sonification_VONAME: " + eumed_sonification_VONAME
                + "\neumed_sonification_TOPBDII: " + eumed_sonification_TOPBDII                    
                + "\neumed_sonification_ETOKENSERVER: " + eumed_sonification_ETOKENSERVER
                + "\neumed_sonification_MYPROXYSERVER: " + eumed_sonification_MYPROXYSERVER
                + "\neumed_sonification_PORT: " + eumed_sonification_PORT
                + "\neumed_sonification_ROBOTID: " + eumed_sonification_ROBOTID
                + "\neumed_sonification_ROLE: " + eumed_sonification_ROLE
                + "\neumed_sonification_RENEWAL: " + eumed_sonification_RENEWAL
                + "\neumed_sonification_DISABLEVOMS: " + eumed_sonification_DISABLEVOMS

                + "\n\nsonification_ENABLEINFRASTRUCTURE: " + "eumed"
                + "\nsonification_APPID: " + sonification_APPID
                + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
            }

            if (gisela_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[5]="gisela";
                // Get the SONIFICATION INFRASTRUCTURE from the portlet request for the GISELA VO
                String gisela_sonification_INFRASTRUCTURE = request.getParameter("gisela_sonification_INFRASTRUCTURE");
                // Get the SONIFICATION VONAME from the portlet request for the GISELA VO
                String gisela_sonification_VONAME = request.getParameter("gisela_sonification_VONAME");
                // Get the SONIFICATION TOPBDII from the portlet request for the GISELA VO
                String gisela_sonification_TOPBDII = request.getParameter("gisela_sonification_TOPBDII");
                // Get the SONIFICATION WMS from the portlet request for the GISELA VO
                String[] gisela_sonification_WMS = request.getParameterValues("gisela_sonification_WMS");
                // Get the SONIFICATION ETOKENSERVER from the portlet request for the GISELA VO
                String gisela_sonification_ETOKENSERVER = request.getParameter("gisela_sonification_ETOKENSERVER");
                // Get the SONIFICATION MYPROXYSERVER from the portlet request for the GISELA VO
                String gisela_sonification_MYPROXYSERVER = request.getParameter("gisela_sonification_MYPROXYSERVER");
                // Get the SONIFICATION PORT from the portlet request for the GISELA VO
                String gisela_sonification_PORT = request.getParameter("gisela_sonification_PORT");
                // Get the SONIFICATION ROBOTID from the portlet request for the GISELA VO
                String gisela_sonification_ROBOTID = request.getParameter("gisela_sonification_ROBOTID");
                // Get the SONIFICATION ROLE from the portlet request for the GISELA VO
                String gisela_sonification_ROLE = request.getParameter("gisela_sonification_ROLE");
                // Get the SONIFICATION OPTIONS from the portlet request for the GISELA VO
                String[] gisela_sonification_OPTIONS = request.getParameterValues("gisela_sonification_OPTIONS");                

                String gisela_sonification_RENEWAL = "";
                String gisela_sonification_DISABLEVOMS = "";

                if (gisela_sonification_OPTIONS == null){
                    gisela_sonification_RENEWAL = "checked";
                    gisela_sonification_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(gisela_sonification_OPTIONS);
                    // Get the SONIFICATION RENEWAL from the portlet preferences for the GISELA VO
                    gisela_sonification_RENEWAL = Arrays.binarySearch(gisela_sonification_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GISELA VO
                    gisela_sonification_DISABLEVOMS = Arrays.binarySearch(gisela_sonification_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }                       

                int nmax=0;                
                for (int i = 0; i < gisela_sonification_WMS.length; i++)
                    if ( gisela_sonification_WMS[i]!=null && (!gisela_sonification_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] gisela_sonification_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    gisela_sonification_WMS_trimmed[i]=gisela_sonification_WMS[i].trim();
                    log.info ("\n\nGISELA [" + i + "] WMS=[" + gisela_sonification_WMS_trimmed[i] + "]");
                }

                // Set the portlet preferences
                portletPreferences.setValue("gisela_sonification_INFRASTRUCTURE", gisela_sonification_INFRASTRUCTURE.trim());
                portletPreferences.setValue("gisela_sonification_VONAME", gisela_sonification_VONAME.trim());
                portletPreferences.setValue("gisela_sonification_TOPBDII", gisela_sonification_TOPBDII.trim());
                portletPreferences.setValues("gisela_sonification_WMS", gisela_sonification_WMS_trimmed);
                portletPreferences.setValue("gisela_sonification_ETOKENSERVER", gisela_sonification_ETOKENSERVER.trim());
                portletPreferences.setValue("gisela_sonification_MYPROXYSERVER", gisela_sonification_MYPROXYSERVER.trim());
                portletPreferences.setValue("gisela_sonification_PORT", gisela_sonification_PORT.trim());
                portletPreferences.setValue("gisela_sonification_ROBOTID", gisela_sonification_ROBOTID.trim());
                portletPreferences.setValue("gisela_sonification_ROLE", gisela_sonification_ROLE.trim());
                portletPreferences.setValue("gisela_sonification_RENEWAL", gisela_sonification_RENEWAL);
                portletPreferences.setValue("gisela_sonification_DISABLEVOMS", gisela_sonification_DISABLEVOMS);
                
                portletPreferences.setValue("sonification_APPID", sonification_APPID.trim());
                portletPreferences.setValue("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
                portletPreferences.setValue("sonification_SOFTWARE", sonification_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the SONIFICATION portlet preferences ..."                    
                + "\n\ngisela_sonification_INFRASTRUCTURE: " + gisela_sonification_INFRASTRUCTURE
                + "\ngisela_sonification_VONAME: " + gisela_sonification_VONAME
                + "\ngisela_sonification_TOPBDII: " + gisela_sonification_TOPBDII                    
                + "\ngisela_sonification_ETOKENSERVER: " + gisela_sonification_ETOKENSERVER
                + "\ngisela_sonification_MYPROXYSERVER: " + gisela_sonification_MYPROXYSERVER
                + "\ngisela_sonification_PORT: " + gisela_sonification_PORT
                + "\ngisela_sonification_ROBOTID: " + gisela_sonification_ROBOTID
                + "\ngisela_sonification_ROLE: " + gisela_sonification_ROLE
                + "\ngisela_sonification_RENEWAL: " + gisela_sonification_RENEWAL
                + "\ngisela_sonification_DISABLEVOMS: " + gisela_sonification_DISABLEVOMS

                + "\n\nsonification_ENABLEINFRASTRUCTURE: " + "gisela"
                + "\nsonification_APPID: " + sonification_APPID
                + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
            }
            
            if (sagrid_sonification_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[6]="sagrid";
                // Get the SONIFICATION INFRASTRUCTURE from the portlet request for the SAGRID VO
                String sagrid_sonification_INFRASTRUCTURE = request.getParameter("sagrid_sonification_INFRASTRUCTURE");
                // Get the SONIFICATION VONAME from the portlet request for the SAGRID VO
                String sagrid_sonification_VONAME = request.getParameter("sagrid_sonification_VONAME");
                // Get the SONIFICATION TOPBDII from the portlet request for the SAGRID VO
                String sagrid_sonification_TOPBDII = request.getParameter("sagrid_sonification_TOPBDII");
                // Get the SONIFICATION WMS from the portlet request for the SAGRID VO
                String[] sagrid_sonification_WMS = request.getParameterValues("sagrid_sonification_WMS");
                // Get the SONIFICATION ETOKENSERVER from the portlet request for the SAGRID VO
                String sagrid_sonification_ETOKENSERVER = request.getParameter("sagrid_sonification_ETOKENSERVER");
                // Get the SONIFICATION MYPROXYSERVER from the portlet request for the SAGRID VO
                String sagrid_sonification_MYPROXYSERVER = request.getParameter("sagrid_sonification_MYPROXYSERVER");
                // Get the SONIFICATION PORT from the portlet request for the SAGRID VO
                String sagrid_sonification_PORT = request.getParameter("sagrid_sonification_PORT");
                // Get the SONIFICATION ROBOTID from the portlet request for the SAGRID VO
                String sagrid_sonification_ROBOTID = request.getParameter("sagrid_sonification_ROBOTID");
                // Get the SONIFICATION ROLE from the portlet request for the SAGRID VO
                String sagrid_sonification_ROLE = request.getParameter("sagrid_sonification_ROLE");
                // Get the SONIFICATION OPTIONS from the portlet request for the SAGRID VO
                String[] sagrid_sonification_OPTIONS = request.getParameterValues("sagrid_sonification_OPTIONS");                

                String sagrid_sonification_RENEWAL = "";
                String sagrid_sonification_DISABLEVOMS = "";

                if (sagrid_sonification_OPTIONS == null){
                    sagrid_sonification_RENEWAL = "checked";
                    sagrid_sonification_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(sagrid_sonification_OPTIONS);
                    // Get the SONIFICATION RENEWAL from the portlet preferences for the SAGRID VO
                    sagrid_sonification_RENEWAL = Arrays.binarySearch(sagrid_sonification_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the SAGRID VO
                    sagrid_sonification_DISABLEVOMS = Arrays.binarySearch(sagrid_sonification_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }                       

                int nmax=0;                
                for (int i = 0; i < sagrid_sonification_WMS.length; i++)
                    if ( sagrid_sonification_WMS[i]!=null && (!sagrid_sonification_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] sagrid_sonification_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    sagrid_sonification_WMS_trimmed[i]=sagrid_sonification_WMS[i].trim();
                    log.info ("\n\nSAGRID [" + i + "] WMS=[" + sagrid_sonification_WMS_trimmed[i] + "]");
                }

                // Set the portlet preferences
                portletPreferences.setValue("sagrid_sonification_INFRASTRUCTURE", sagrid_sonification_INFRASTRUCTURE.trim());
                portletPreferences.setValue("sagrid_sonification_VONAME", sagrid_sonification_VONAME.trim());
                portletPreferences.setValue("sagrid_sonification_TOPBDII", sagrid_sonification_TOPBDII.trim());
                portletPreferences.setValues("sagrid_sonification_WMS", sagrid_sonification_WMS_trimmed);
                portletPreferences.setValue("sagrid_sonification_ETOKENSERVER", sagrid_sonification_ETOKENSERVER.trim());
                portletPreferences.setValue("sagrid_sonification_MYPROXYSERVER", sagrid_sonification_MYPROXYSERVER.trim());
                portletPreferences.setValue("sagrid_sonification_PORT", sagrid_sonification_PORT.trim());
                portletPreferences.setValue("sagrid_sonification_ROBOTID", sagrid_sonification_ROBOTID.trim());
                portletPreferences.setValue("sagrid_sonification_ROLE", sagrid_sonification_ROLE.trim());
                portletPreferences.setValue("sagrid_sonification_RENEWAL", sagrid_sonification_RENEWAL);
                portletPreferences.setValue("sagrid_sonification_DISABLEVOMS", sagrid_sonification_DISABLEVOMS);
                
                portletPreferences.setValue("sonification_APPID", sonification_APPID.trim());
                portletPreferences.setValue("sonification_OUTPUT_PATH", sonification_OUTPUT_PATH.trim());
                portletPreferences.setValue("sonification_SOFTWARE", sonification_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the SONIFICATION portlet preferences ..."                    
                + "\n\nsagrid_sonification_INFRASTRUCTURE: " + sagrid_sonification_INFRASTRUCTURE
                + "\nsagrid_sonification_VONAME: " + sagrid_sonification_VONAME
                + "\nsagrid_sonification_TOPBDII: " + sagrid_sonification_TOPBDII                    
                + "\nsagrid_sonification_ETOKENSERVER: " + sagrid_sonification_ETOKENSERVER
                + "\nsagrid_sonification_MYPROXYSERVER: " + sagrid_sonification_MYPROXYSERVER
                + "\nsagrid_sonification_PORT: " + sagrid_sonification_PORT
                + "\nsagrid_sonification_ROBOTID: " + sagrid_sonification_ROBOTID
                + "\nsagrid_sonification_ROLE: " + sagrid_sonification_ROLE
                + "\nsagrid_sonification_RENEWAL: " + sagrid_sonification_RENEWAL
                + "\nsagrid_sonification_DISABLEVOMS: " + sagrid_sonification_DISABLEVOMS

                + "\n\nsonification_ENABLEINFRASTRUCTURE: " + "sagrid"
                + "\nsonification_APPID: " + sonification_APPID
                + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
            }
            
            for (int i=0; i<infras.length; i++)
            log.info("\n - Infrastructure Enabled = " + infras[i]);
            portletPreferences.setValues("sonification_ENABLEINFRASTRUCTURE", infras);
            portletPreferences.setValue("cometa_sonification_ENABLEINFRASTRUCTURE",infras[0]);
            portletPreferences.setValue("garuda_sonification_ENABLEINFRASTRUCTURE",infras[1]);
            portletPreferences.setValue("gridit_sonification_ENABLEINFRASTRUCTURE",infras[2]);
            portletPreferences.setValue("gilda_sonification_ENABLEINFRASTRUCTURE",infras[3]);
            portletPreferences.setValue("eumed_sonification_ENABLEINFRASTRUCTURE",infras[4]);
            portletPreferences.setValue("gisela_sonification_ENABLEINFRASTRUCTURE",infras[5]);
            portletPreferences.setValue("sagrid_sonification_ENABLEINFRASTRUCTURE",infras[6]);

            portletPreferences.store();
            response.setPortletMode(PortletMode.VIEW);
        } // end PROCESS ACTION [ CONFIG_SONIFICATION_PORTLET ]
        

        if (action.equals("SUBMIT_SONIFICATION_PORTLET")) {
            log.info("\nPROCESS ACTION => " + action);            
            InfrastructureInfo infrastructures[] = new InfrastructureInfo[7];
            int NMAX=0;                        

            // Get the SONIFICATION APPID from the portlet preferences
            String sonification_APPID = portletPreferences.getValue("sonification_APPID", "N/A");
            // Get the SONIFICATION APPID from the portlet preferences
            String sonification_OUTPUT_PATH = portletPreferences.getValue("sonification_OUTPUT_PATH", "/tmp");
            // Get the SONIFICATION SOFTWARE from the portlet preferences
            String sonification_SOFTWARE = portletPreferences.getValue("sonification_SOFTWARE", "N/A");
            // Get the TRACKING_DB_HOSTNAME from the portlet request
            String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
            // Get the TRACKING_DB_USERNAME from the portlet request
            String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
            // Get the TRACKING_DB_PASSWORD from the portlet request
            String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD","N/A");
            // Get the SMTP_HOST from the portlet request
            String SMTP_HOST = portletPreferences.getValue("SMTP_HOST","N/A");
            // Get the SENDER_MAIL from the portlet request
            String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL","N/A");        
            
            String cometa_sonification_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("cometa_sonification_ENABLEINFRASTRUCTURE","null");
            String garuda_sonification_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("garuda_sonification_ENABLEINFRASTRUCTURE","null");
            String gridit_sonification_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("gridit_sonification_ENABLEINFRASTRUCTURE","null");
            String gilda_sonification_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("gilda_sonification_ENABLEINFRASTRUCTURE","null");
            String eumed_sonification_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("eumed_sonification_ENABLEINFRASTRUCTURE","null");
            String gisela_sonification_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("gisela_sonification_ENABLEINFRASTRUCTURE","null");
            String sagrid_sonification_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("sagrid_sonification_ENABLEINFRASTRUCTURE","null");
        
            if (cometa_sonification_ENABLEINFRASTRUCTURE != null &&
                cometa_sonification_ENABLEINFRASTRUCTURE.equals("cometa"))
            {                
                NMAX++;                
                // Get the SONIFICATION VONAME from the portlet preferences for the COMETA VO
                String cometa_sonification_INFRASTRUCTURE = portletPreferences.getValue("cometa_sonification_INFRASTRUCTURE", "N/A");
                // Get the SONIFICATION VONAME from the portlet preferences for the COMETA VO
                String cometa_sonification_VONAME = portletPreferences.getValue("cometa_sonification_VONAME", "N/A");
                // Get the SONIFICATION TOPPBDII from the portlet preferences for the COMETA VO
                String cometa_sonification_TOPBDII = portletPreferences.getValue("cometa_sonification_TOPBDII", "N/A");
                // Get the SONIFICATION WMS from the portlet preferences for the COMETA VO                
                String[] cometa_sonification_WMS = portletPreferences.getValues("cometa_sonification_WMS", new String[5]);
                // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the COMETA VO
                String cometa_sonification_ETOKENSERVER = portletPreferences.getValue("cometa_sonification_ETOKENSERVER", "N/A");
                // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the COMETA VO
                String cometa_sonification_MYPROXYSERVER = portletPreferences.getValue("cometa_sonification_MYPROXYSERVER", "N/A");
                // Get the SONIFICATION PORT from the portlet preferences for the COMETA VO
                String cometa_sonification_PORT = portletPreferences.getValue("cometa_sonification_PORT", "N/A");
                // Get the SONIFICATION ROBOTID from the portlet preferences for the COMETA VO
                String cometa_sonification_ROBOTID = portletPreferences.getValue("cometa_sonification_ROBOTID", "N/A");
                // Get the SONIFICATION ROLE from the portlet preferences for the COMETA VO
                String cometa_sonification_ROLE = portletPreferences.getValue("cometa_sonification_ROLE", "N/A");
                // Get the SONIFICATION RENEWAL from the portlet preferences for the COMETA VO
                String cometa_sonification_RENEWAL = portletPreferences.getValue("cometa_sonification_RENEWAL", "checked");
                // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the COMETA VO
                String cometa_sonification_DISABLEVOMS = portletPreferences.getValue("cometa_sonification_DISABLEVOMS", "unchecked");
                // Get the random CE for the Sonification portlet               
                //RANDOM_CE = getRandomCE(cometa_sonification_VONAME, cometa_sonification_TOPBDII, sonification_SOFTWARE);
                
                log.info("\n- Getting the SONIFICATION portlet preferences ..."
                + "\ncometa_sonification_INFRASTRUCTURE: " + cometa_sonification_INFRASTRUCTURE
                + "\ncometa_sonification_VONAME: " + cometa_sonification_VONAME
                + "\ncometa_sonification_TOPBDII: " + cometa_sonification_TOPBDII                    
                + "\ncometa_sonification_ETOKENSERVER: " + cometa_sonification_ETOKENSERVER
                + "\ncometa_sonification_MYPROXYSERVER: " + cometa_sonification_MYPROXYSERVER
                + "\ncometa_sonification_PORT: " + cometa_sonification_PORT
                + "\ncometa_sonification_ROBOTID: " + cometa_sonification_ROBOTID
                + "\ncometa_sonification_ROLE: " + cometa_sonification_ROLE
                + "\ncometa_sonification_RENEWAL: " + cometa_sonification_RENEWAL
                + "\ncometa_sonification_DISABLEVOMS: " + cometa_sonification_DISABLEVOMS
                 
                + "\n\nsonification_ENABLEINFRASTRUCTURE: " + cometa_sonification_ENABLEINFRASTRUCTURE
                + "\nsonification_APPID: " + sonification_APPID
                + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "COMETA" Infrastructure
                int nmax=0;
                for (int i = 0; i < cometa_sonification_WMS.length; i++)
                    if ((cometa_sonification_WMS[i]!=null) && (!cometa_sonification_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (cometa_sonification_WMS[i]!=null) {
                    wmsList[i]=cometa_sonification_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to COMETA ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[0] = new InfrastructureInfo(
                    cometa_sonification_VONAME,
                    cometa_sonification_TOPBDII,
                    wmsList,
                    cometa_sonification_ETOKENSERVER,
                    cometa_sonification_PORT,
                    cometa_sonification_ROBOTID,
                    cometa_sonification_VONAME,
                    cometa_sonification_ROLE,
                    "VO-" + cometa_sonification_VONAME + "-" + sonification_SOFTWARE);
            }
            
            if (garuda_sonification_ENABLEINFRASTRUCTURE != null &&
                garuda_sonification_ENABLEINFRASTRUCTURE.equals("garuda"))
            {
                NMAX++;                
                // Get the SONIFICATION VONAME from the portlet preferences for the GARUDA VO
                String garuda_sonification_INFRASTRUCTURE = portletPreferences.getValue("garuda_sonification_INFRASTRUCTURE", "N/A");
                // Get the SONIFICATION VONAME from the portlet preferences for the GARUDA VO
                String garuda_sonification_VONAME = portletPreferences.getValue("garuda_sonification_VONAME", "N/A");
                // Get the SONIFICATION TOPPBDII from the portlet preferences for the GARUDA VO
                String garuda_sonification_TOPBDII = portletPreferences.getValue("garuda_sonification_TOPBDII", "N/A");
                // Get the SONIFICATION WMS from the portlet preferences for the GARUDA VO                
                String[] garuda_sonification_WMS = portletPreferences.getValues("garuda_sonification_WMS", new String[5]);
                // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the GARUDA VO
                String garuda_sonification_ETOKENSERVER = portletPreferences.getValue("garuda_sonification_ETOKENSERVER", "N/A");
                // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the GARUDA VO
                String garuda_sonification_MYPROXYSERVER = portletPreferences.getValue("garuda_sonification_MYPROXYSERVER", "N/A");
                // Get the SONIFICATION PORT from the portlet preferences for the GARUDA VO
                String garuda_sonification_PORT = portletPreferences.getValue("garuda_sonification_PORT", "N/A");
                // Get the SONIFICATION ROBOTID from the portlet preferences for the GARUDA VO
                String garuda_sonification_ROBOTID = portletPreferences.getValue("garuda_sonification_ROBOTID", "N/A");
                // Get the SONIFICATION ROLE from the portlet preferences for the GARUDA VO
                String garuda_sonification_ROLE = portletPreferences.getValue("garuda_sonification_ROLE", "N/A");
                // Get the SONIFICATION RENEWAL from the portlet preferences for the GARUDA VO
                String garuda_sonification_RENEWAL = portletPreferences.getValue("garuda_sonification_RENEWAL", "checked");
                // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GARUDA VO
                String garuda_sonification_DISABLEVOMS = portletPreferences.getValue("garuda_sonification_DISABLEVOMS", "unchecked"); 
                
                log.info("\n- Getting the SONIFICATION portlet preferences ..."
                + "\ngaruda_sonification_INFRASTRUCTURE: " + garuda_sonification_INFRASTRUCTURE
                + "\ngaruda_sonification_VONAME: " + garuda_sonification_VONAME
                + "\ngaruda_sonification_TOPBDII: " + garuda_sonification_TOPBDII                    
                + "\ngaruda_sonification_ETOKENSERVER: " + garuda_sonification_ETOKENSERVER
                + "\ngaruda_sonification_MYPROXYSERVER: " + garuda_sonification_MYPROXYSERVER
                + "\ngaruda_sonification_PORT: " + garuda_sonification_PORT
                + "\ngaruda_sonification_ROBOTID: " + garuda_sonification_ROBOTID
                + "\ngaruda_sonification_ROLE: " + garuda_sonification_ROLE
                + "\ngaruda_sonification_RENEWAL: " + garuda_sonification_RENEWAL
                + "\ngaruda_sonification_DISABLEVOMS: " + garuda_sonification_DISABLEVOMS
                   
                + "\n\nsonification_ENABLEINFRASTRUCTURE: " + garuda_sonification_ENABLEINFRASTRUCTURE
                + "\nsonification_APPID: " + sonification_APPID
                + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "GARUDA" Infrastructure
                int nmax=0;
                for (int i = 0; i < garuda_sonification_WMS.length; i++)
                    if ((garuda_sonification_WMS[i]!=null) && (!garuda_sonification_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (garuda_sonification_WMS[i]!=null) {
                    wmsList[i]=garuda_sonification_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to GARUDA ["
                                      + i
                                      + "] using WSGRAM=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[1] = new InfrastructureInfo(
                    "GARUDA",
                    "wsgram",
                    "",
                    wmsList,
                    garuda_sonification_ETOKENSERVER,
                    garuda_sonification_PORT,
                    garuda_sonification_ROBOTID,
                    garuda_sonification_VONAME,
                    garuda_sonification_ROLE);
            }
            
            if (gridit_sonification_ENABLEINFRASTRUCTURE != null &&
                gridit_sonification_ENABLEINFRASTRUCTURE.equals("gridit"))
            {
                NMAX++;                
                // Get the SONIFICATION VONAME from the portlet preferences for the GRIDIT VO
                String gridit_sonification_INFRASTRUCTURE = portletPreferences.getValue("gridit_sonification_INFRASTRUCTURE", "N/A");
                // Get the SONIFICATION VONAME from the portlet preferences for the GRIDIT VO
                String gridit_sonification_VONAME = portletPreferences.getValue("gridit_sonification_VONAME", "N/A");
                // Get the SONIFICATION TOPPBDII from the portlet preferences for the GRIDIT VO
                String gridit_sonification_TOPBDII = portletPreferences.getValue("gridit_sonification_TOPBDII", "N/A");
                // Get the SONIFICATION WMS from the portlet preferences for the GRIDIT VO                
                String[] gridit_sonification_WMS = portletPreferences.getValues("gridit_sonification_WMS", new String[5]);
                // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the GRIDIT VO
                String gridit_sonification_ETOKENSERVER = portletPreferences.getValue("gridit_sonification_ETOKENSERVER", "N/A");
                // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the GRIDIT VO
                String gridit_sonification_MYPROXYSERVER = portletPreferences.getValue("gridit_sonification_MYPROXYSERVER", "N/A");
                // Get the SONIFICATION PORT from the portlet preferences for the GRIDIT VO
                String gridit_sonification_PORT = portletPreferences.getValue("gridit_sonification_PORT", "N/A");
                // Get the SONIFICATION ROBOTID from the portlet preferences for the GRIDIT VO
                String gridit_sonification_ROBOTID = portletPreferences.getValue("gridit_sonification_ROBOTID", "N/A");
                // Get the SONIFICATION ROLE from the portlet preferences for the GRIDIT VO
                String gridit_sonification_ROLE = portletPreferences.getValue("gridit_sonification_ROLE", "N/A");
                // Get the SONIFICATION RENEWAL from the portlet preferences for the GRIDIT VO
                String gridit_sonification_RENEWAL = portletPreferences.getValue("gridit_sonification_RENEWAL", "checked");
                // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GRIDIT VO
                String gridit_sonification_DISABLEVOMS = portletPreferences.getValue("gridit_sonification_DISABLEVOMS", "unchecked"); 
                // Get the random CE for the Sonification portlet               
                //RANDOM_CE = getRandomCE(gridit_sonification_VONAME, gridit_sonification_TOPBDII, sonification_SOFTWARE);
                
                log.info("\n- Getting the SONIFICATION portlet preferences ..."
                + "\ngridit_sonification_INFRASTRUCTURE: " + gridit_sonification_INFRASTRUCTURE
                + "\ngridit_sonification_VONAME: " + gridit_sonification_VONAME
                + "\ngridit_sonification_TOPBDII: " + gridit_sonification_TOPBDII                    
                + "\ngridit_sonification_ETOKENSERVER: " + gridit_sonification_ETOKENSERVER
                + "\ngridit_sonification_MYPROXYSERVER: " + gridit_sonification_MYPROXYSERVER
                + "\ngridit_sonification_PORT: " + gridit_sonification_PORT
                + "\ngridit_sonification_ROBOTID: " + gridit_sonification_ROBOTID
                + "\ngridit_sonification_ROLE: " + gridit_sonification_ROLE
                + "\ngridit_sonification_RENEWAL: " + gridit_sonification_RENEWAL
                + "\ngridit_sonification_DISABLEVOMS: " + gridit_sonification_DISABLEVOMS
                   
                + "\n\nsonification_ENABLEINFRASTRUCTURE: " + gridit_sonification_ENABLEINFRASTRUCTURE
                + "\nsonification_APPID: " + sonification_APPID
                + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "GRIDIT" Infrastructure
                int nmax=0;
                for (int i = 0; i < gridit_sonification_WMS.length; i++)
                    if ((gridit_sonification_WMS[i]!=null) && (!gridit_sonification_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (gridit_sonification_WMS[i]!=null) {
                    wmsList[i]=gridit_sonification_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to GRIDIT ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[2] = new InfrastructureInfo(
                    gridit_sonification_VONAME,
                    gridit_sonification_TOPBDII,
                    wmsList,
                    gridit_sonification_ETOKENSERVER,
                    gridit_sonification_PORT,
                    gridit_sonification_ROBOTID,
                    gridit_sonification_VONAME,
                    gridit_sonification_ROLE,
                    "VO-" + gridit_sonification_VONAME + "-" + sonification_SOFTWARE);
            }
            
            if (gilda_sonification_ENABLEINFRASTRUCTURE != null &&
                gilda_sonification_ENABLEINFRASTRUCTURE.equals("gilda"))
            {
                NMAX++;                
                // Get the SONIFICATION VONAME from the portlet preferences for the GILDA VO
                String gilda_sonification_INFRASTRUCTURE = portletPreferences.getValue("gilda_sonification_INFRASTRUCTURE", "N/A");
                // Get the SONIFICATION VONAME from the portlet preferences for the GILDA VO
                String gilda_sonification_VONAME = portletPreferences.getValue("gilda_sonification_VONAME", "N/A");
                // Get the SONIFICATION TOPPBDII from the portlet preferences for the GILDA VO
                String gilda_sonification_TOPBDII = portletPreferences.getValue("gilda_sonification_TOPBDII", "N/A");
                // Get the SONIFICATION WMS from the portlet preferences for the GILDA VO
                String[] gilda_sonification_WMS = portletPreferences.getValues("gilda_sonification_WMS", new String[5]);
                // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the GILDA VO
                String gilda_sonification_ETOKENSERVER = portletPreferences.getValue("gilda_sonification_ETOKENSERVER", "N/A");
                // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the GILDA VO
                String gilda_sonification_MYPROXYSERVER = portletPreferences.getValue("gilda_sonification_MYPROXYSERVER", "N/A");
                // Get the SONIFICATION PORT from the portlet preferences for the GILDA VO
                String gilda_sonification_PORT = portletPreferences.getValue("gilda_sonification_PORT", "N/A");
                // Get the SONIFICATION ROBOTID from the portlet preferences for the GILDA VO
                String gilda_sonification_ROBOTID = portletPreferences.getValue("gilda_sonification_ROBOTID", "N/A");
                // Get the SONIFICATION ROLE from the portlet preferences for the GILDA VO
                String gilda_sonification_ROLE = portletPreferences.getValue("gilda_sonification_ROLE", "N/A");
                // Get the SONIFICATION RENEWAL from the portlet preferences for the GILDA VO
                String gilda_sonification_RENEWAL = portletPreferences.getValue("gilda_sonification_RENEWAL", "checked");
                // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GILDA VO
                String gilda_sonification_DISABLEVOMS = portletPreferences.getValue("gilda_sonification_DISABLEVOMS", "unchecked");
                // Get the random CE for the Sonification portlet               
                //RANDOM_CE = getRandomCE(gilda_sonification_VONAME, gilda_sonification_TOPBDII, sonification_SOFTWARE);
                
                log.info("\n- Getting the SONIFICATION portlet preferences ..."
                    + "\n\ngilda_sonification_INFRASTRUCTURE: " + gilda_sonification_INFRASTRUCTURE
                    + "\ngilda_sonification_VONAME: " + gilda_sonification_VONAME
                    + "\ngilda_sonification_TOPBDII: " + gilda_sonification_TOPBDII                    
                    + "\ngilda_sonification_ETOKENSERVER: " + gilda_sonification_ETOKENSERVER
                    + "\ngilda_sonification_MYPROXYSERVER: " + gilda_sonification_MYPROXYSERVER
                    + "\ngilda_sonification_PORT: " + gilda_sonification_PORT
                    + "\ngilda_sonification_ROBOTID: " + gilda_sonification_ROBOTID
                    + "\ngilda_sonification_ROLE: " + gilda_sonification_ROLE
                    + "\ngilda_sonification_RENEWAL: " + gilda_sonification_RENEWAL
                    + "\ngilda_sonification_DISABLEVOMS: " + gilda_sonification_DISABLEVOMS

                    + "\n\nsonification_ENABLEINFRASTRUCTURE: " + gilda_sonification_ENABLEINFRASTRUCTURE
                    + "\nsonification_APPID: " + sonification_APPID
                    + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                    + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "GILDA" Infrastructure
                int nmax=0;
                for (int i = 0; i < gilda_sonification_WMS.length; i++)
                    if ((gilda_sonification_WMS[i]!=null) && (!gilda_sonification_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (gilda_sonification_WMS[i]!=null) {
                    wmsList[i]=gilda_sonification_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to GILDA ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[3] = new InfrastructureInfo(
                    gilda_sonification_VONAME,
                    gilda_sonification_TOPBDII,
                    wmsList,
                    gilda_sonification_ETOKENSERVER,
                    gilda_sonification_PORT,
                    gilda_sonification_ROBOTID,
                    gilda_sonification_VONAME,
                    gilda_sonification_ROLE,
                    "VO-" + gilda_sonification_VONAME + "-" + sonification_SOFTWARE);
            }
            
            if (eumed_sonification_ENABLEINFRASTRUCTURE != null &&
                eumed_sonification_ENABLEINFRASTRUCTURE.equals("eumed"))
            {
                NMAX++;                
                // Get the SONIFICATION VONAME from the portlet preferences for the EUMED VO
                String eumed_sonification_INFRASTRUCTURE = portletPreferences.getValue("eumed_sonification_INFRASTRUCTURE", "N/A");
                // Get the SONIFICATION VONAME from the portlet preferences for the EUMED VO
                String eumed_sonification_VONAME = portletPreferences.getValue("eumed_sonification_VONAME", "N/A");
                // Get the SONIFICATION TOPPBDII from the portlet preferences for the EUMED VO
                String eumed_sonification_TOPBDII = portletPreferences.getValue("eumed_sonification_TOPBDII", "N/A");
                // Get the SONIFICATION WMS from the portlet preferences for the EUMED VO
                String[] eumed_sonification_WMS = portletPreferences.getValues("eumed_sonification_WMS", new String[5]);
                // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the EUMED VO
                String eumed_sonification_ETOKENSERVER = portletPreferences.getValue("eumed_sonification_ETOKENSERVER", "N/A");
                // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the EUMED VO
                String eumed_sonification_MYPROXYSERVER = portletPreferences.getValue("eumed_sonification_MYPROXYSERVER", "N/A");
                // Get the SONIFICATION PORT from the portlet preferences for the EUMED VO
                String eumed_sonification_PORT = portletPreferences.getValue("eumed_sonification_PORT", "N/A");
                // Get the SONIFICATION ROBOTID from the portlet preferences for the EUMED VO
                String eumed_sonification_ROBOTID = portletPreferences.getValue("eumed_sonification_ROBOTID", "N/A");
                // Get the SONIFICATION ROLE from the portlet preferences for the EUMED VO
                String eumed_sonification_ROLE = portletPreferences.getValue("eumed_sonification_ROLE", "N/A");
                // Get the SONIFICATION RENEWAL from the portlet preferences for the EUMED VO
                String eumed_sonification_RENEWAL = portletPreferences.getValue("eumed_sonification_RENEWAL", "checked");
                // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the EUMED VO
                String eumed_sonification_DISABLEVOMS = portletPreferences.getValue("eumed_sonification_DISABLEVOMS", "unchecked");
                // Get the random CE for the Sonification portlet               
                //RANDOM_CE = getRandomCE(eumed_sonification_VONAME, eumed_sonification_TOPBDII, sonification_SOFTWARE);
                
                log.info("\n- Getting the SONIFICATION portlet preferences ..."
                + "\n\neumed_sonification_INFRASTRUCTURE: " + eumed_sonification_INFRASTRUCTURE
                + "\neumed_sonification_VONAME: " + eumed_sonification_VONAME
                + "\neumed_sonification_TOPBDII: " + eumed_sonification_TOPBDII                    
                + "\neumed_sonification_ETOKENSERVER: " + eumed_sonification_ETOKENSERVER
                + "\neumed_sonification_MYPROXYSERVER: " + eumed_sonification_MYPROXYSERVER
                + "\neumed_sonification_PORT: " + eumed_sonification_PORT
                + "\neumed_sonification_ROBOTID: " + eumed_sonification_ROBOTID
                + "\neumed_sonification_ROLE: " + eumed_sonification_ROLE
                + "\neumed_sonification_RENEWAL: " + eumed_sonification_RENEWAL
                + "\neumed_sonification_DISABLEVOMS: " + eumed_sonification_DISABLEVOMS

                + "\n\nsonification_ENABLEINFRASTRUCTURE: " + eumed_sonification_ENABLEINFRASTRUCTURE
                + "\nsonification_APPID: " + sonification_APPID
                + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "EUMED" Infrastructure
                int nmax=0;
                for (int i = 0; i < eumed_sonification_WMS.length; i++)
                    if ((eumed_sonification_WMS[i]!=null) && (!eumed_sonification_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (eumed_sonification_WMS[i]!=null) {
                    wmsList[i]=eumed_sonification_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to EUMED ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[4] = new InfrastructureInfo(
                    eumed_sonification_VONAME,
                    eumed_sonification_TOPBDII,
                    wmsList,
                    eumed_sonification_ETOKENSERVER,
                    eumed_sonification_PORT,
                    eumed_sonification_ROBOTID,
                    eumed_sonification_VONAME,
                    eumed_sonification_ROLE,
                    "VO-" + eumed_sonification_VONAME + "-" + sonification_SOFTWARE);
            }

            if (gisela_sonification_ENABLEINFRASTRUCTURE != null &&
                gisela_sonification_ENABLEINFRASTRUCTURE.equals("gisela")) 
            {
                NMAX++;                
                // Get the SONIFICATION VONAME from the portlet preferences for the GISELA VO
                String gisela_sonification_INFRASTRUCTURE = portletPreferences.getValue("gisela_sonification_INFRASTRUCTURE", "N/A");
                // Get the SONIFICATION VONAME from the portlet preferences for the GISELA VO
                String gisela_sonification_VONAME = portletPreferences.getValue("gisela_sonification_VONAME", "N/A");
                // Get the SONIFICATION TOPPBDII from the portlet preferences for the GISELA VO
                String gisela_sonification_TOPBDII = portletPreferences.getValue("gisela_sonification_TOPBDII", "N/A");
                // Get the SONIFICATION WMS from the portlet preferences for the GISELA VO
                String[] gisela_sonification_WMS = portletPreferences.getValues("gisela_sonification_WMS", new String[5]);
                // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the GISELA VO
                String gisela_sonification_ETOKENSERVER = portletPreferences.getValue("gisela_sonification_ETOKENSERVER", "N/A");
                // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the GISELA VO
                String gisela_sonification_MYPROXYSERVER = portletPreferences.getValue("gisela_sonification_MYPROXYSERVER", "N/A");
                // Get the SONIFICATION PORT from the portlet preferences for the GISELA VO
                String gisela_sonification_PORT = portletPreferences.getValue("gisela_sonification_PORT", "N/A");
                // Get the SONIFICATION ROBOTID from the portlet preferences for the GISELA VO
                String gisela_sonification_ROBOTID = portletPreferences.getValue("gisela_sonification_ROBOTID", "N/A");
                // Get the SONIFICATION ROLE from the portlet preferences for the GISELA VO
                String gisela_sonification_ROLE = portletPreferences.getValue("gisela_sonification_ROLE", "N/A");
                // Get the SONIFICATION RENEWAL from the portlet preferences for the GISELA VO
                String gisela_sonification_RENEWAL = portletPreferences.getValue("gisela_sonification_RENEWAL", "checked");
                // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the GISELA VO
                String gisela_sonification_DISABLEVOMS = portletPreferences.getValue("gisela_sonification_DISABLEVOMS", "unchecked");          
                // Get the random CE for the Sonification portlet               
                //RANDOM_CE = getRandomCE(gisela_sonification_VONAME, gisela_sonification_TOPBDII, sonification_SOFTWARE);
                
                log.info("\n- Getting the SONIFICATION portlet preferences ..."
                + "\n\ngisela_sonification_INFRASTRUCTURE: " + gisela_sonification_INFRASTRUCTURE
                + "\ngisela_sonification_VONAME: " + gisela_sonification_VONAME
                + "\ngisela_sonification_TOPBDII: " + gisela_sonification_TOPBDII                        
                + "\ngisela_sonification_ETOKENSERVER: " + gisela_sonification_ETOKENSERVER
                + "\ngisela_sonification_MYPROXYSERVER: " + gisela_sonification_MYPROXYSERVER
                + "\ngisela_sonification_PORT: " + gisela_sonification_PORT
                + "\ngisela_sonification_ROBOTID: " + gisela_sonification_ROBOTID
                + "\ngisela_sonification_ROLE: " + gisela_sonification_ROLE
                + "\ngisela_sonification_RENEWAL: " + gisela_sonification_RENEWAL
                + "\ngisela_sonification_DISABLEVOMS: " + gisela_sonification_DISABLEVOMS

                + "\n\nsonification_ENABLEINFRASTRUCTURE: " + gisela_sonification_ENABLEINFRASTRUCTURE
                + "\nsonification_APPID: " + sonification_APPID
                + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "GISELA" Infrastructure
                int nmax=0;
                for (int i = 0; i < gisela_sonification_WMS.length; i++)
                    if ((gisela_sonification_WMS[i]!=null) && (!gisela_sonification_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [gisela_sonification_WMS.length];
                for (int i = 0; i < gisela_sonification_WMS.length; i++)
                {
                    if (gisela_sonification_WMS[i]!=null) {
                    wmsList[i]=gisela_sonification_WMS[i].trim();
                    log.info ("\n\nSubmitting for GISELA [" + i + "] using WMS=[" + wmsList[i] + "]");
                    }
                }

                infrastructures[5] = new InfrastructureInfo(
                    gisela_sonification_VONAME,
                    gisela_sonification_TOPBDII,
                    wmsList,
                    gisela_sonification_ETOKENSERVER,
                    gisela_sonification_PORT,
                    gisela_sonification_ROBOTID,
                    gisela_sonification_VONAME,
                    gisela_sonification_ROLE,
                    "VO-" + gisela_sonification_VONAME + "-" + sonification_SOFTWARE);
            }
            
            if (sagrid_sonification_ENABLEINFRASTRUCTURE != null &&
                sagrid_sonification_ENABLEINFRASTRUCTURE.equals("sagrid")) 
            {
                NMAX++;                
                // Get the SONIFICATION VONAME from the portlet preferences for the SAGRID VO
                String sagrid_sonification_INFRASTRUCTURE = portletPreferences.getValue("sagrid_sonification_INFRASTRUCTURE", "N/A");
                // Get the SONIFICATION VONAME from the portlet preferences for the SAGRID VO
                String sagrid_sonification_VONAME = portletPreferences.getValue("sagrid_sonification_VONAME", "N/A");
                // Get the SONIFICATION TOPPBDII from the portlet preferences for the SAGRID VO
                String sagrid_sonification_TOPBDII = portletPreferences.getValue("sagrid_sonification_TOPBDII", "N/A");
                // Get the SONIFICATION WMS from the portlet preferences for the SAGRID VO
                String[] sagrid_sonification_WMS = portletPreferences.getValues("sagrid_sonification_WMS", new String[5]);
                // Get the SONIFICATION ETOKENSERVER from the portlet preferences for the SAGRID VO
                String sagrid_sonification_ETOKENSERVER = portletPreferences.getValue("sagrid_sonification_ETOKENSERVER", "N/A");
                // Get the SONIFICATION MYPROXYSERVER from the portlet preferences for the SAGRID VO
                String sagrid_sonification_MYPROXYSERVER = portletPreferences.getValue("sagrid_sonification_MYPROXYSERVER", "N/A");
                // Get the SONIFICATION PORT from the portlet preferences for the SAGRID VO
                String sagrid_sonification_PORT = portletPreferences.getValue("sagrid_sonification_PORT", "N/A");
                // Get the SONIFICATION ROBOTID from the portlet preferences for the SAGRID VO
                String sagrid_sonification_ROBOTID = portletPreferences.getValue("sagrid_sonification_ROBOTID", "N/A");
                // Get the SONIFICATION ROLE from the portlet preferences for the SAGRID VO
                String sagrid_sonification_ROLE = portletPreferences.getValue("sagrid_sonification_ROLE", "N/A");
                // Get the SONIFICATION RENEWAL from the portlet preferences for the SAGRID VO
                String sagrid_sonification_RENEWAL = portletPreferences.getValue("sagrid_sonification_RENEWAL", "checked");
                // Get the SONIFICATION DISABLEVOMS from the portlet preferences for the SAGRID VO
                String sagrid_sonification_DISABLEVOMS = portletPreferences.getValue("sagrid_sonification_DISABLEVOMS", "unchecked");          
                // Get the random CE for the Sonification portlet               
                //RANDOM_CE = getRandomCE(sagrid_sonification_VONAME, sagrid_sonification_TOPBDII, sonification_SOFTWARE);
                
                log.info("\n- Getting the SONIFICATION portlet preferences ..."
                + "\n\nsagrid_sonification_INFRASTRUCTURE: " + sagrid_sonification_INFRASTRUCTURE
                + "\nsagrid_sonification_VONAME: " + sagrid_sonification_VONAME
                + "\nsagrid_sonification_TOPBDII: " + sagrid_sonification_TOPBDII                        
                + "\nsagrid_sonification_ETOKENSERVER: " + sagrid_sonification_ETOKENSERVER
                + "\nsagrid_sonification_MYPROXYSERVER: " + sagrid_sonification_MYPROXYSERVER
                + "\nsagrid_sonification_PORT: " + sagrid_sonification_PORT
                + "\nsagrid_sonification_ROBOTID: " + sagrid_sonification_ROBOTID
                + "\nsagrid_sonification_ROLE: " + sagrid_sonification_ROLE
                + "\nsagrid_sonification_RENEWAL: " + sagrid_sonification_RENEWAL
                + "\nsagrid_sonification_DISABLEVOMS: " + sagrid_sonification_DISABLEVOMS

                + "\n\nsonification_ENABLEINFRASTRUCTURE: " + sagrid_sonification_ENABLEINFRASTRUCTURE
                + "\nsonification_APPID: " + sonification_APPID
                + "\nsonification_OUTPUT_PATH: " + sonification_OUTPUT_PATH
                + "\nsonification_SOFTWARE: " + sonification_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                + "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "SAGRID" Infrastructure
                int nmax=0;
                for (int i = 0; i < sagrid_sonification_WMS.length; i++)
                    if ((sagrid_sonification_WMS[i]!=null) && (!sagrid_sonification_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [sagrid_sonification_WMS.length];
                for (int i = 0; i < sagrid_sonification_WMS.length; i++)
                {
                    if (sagrid_sonification_WMS[i]!=null) {
                    wmsList[i]=sagrid_sonification_WMS[i].trim();
                    log.info ("\n\nSubmitting for SAGRID [" + i + "] using WMS=[" + wmsList[i] + "]");
                    }
                }

                infrastructures[6] = new InfrastructureInfo(
                    sagrid_sonification_VONAME,
                    sagrid_sonification_TOPBDII,
                    wmsList,
                    sagrid_sonification_ETOKENSERVER,
                    sagrid_sonification_PORT,
                    sagrid_sonification_ROBOTID,
                    sagrid_sonification_VONAME,
                    sagrid_sonification_ROLE,
                    "VO-" + sagrid_sonification_VONAME + "-" + sonification_SOFTWARE);
            }

            String[] SONIFICATION_Parameters = new String [15];

            // Upload the input settings for the application
            SONIFICATION_Parameters = uploadSonificationSettings( request, response, username );

            log.info("\n- Input Parameters: ");
            log.info("\n- ASCII or Text = " + SONIFICATION_Parameters[0]);
            log.info("\n- Sonification Type = " + SONIFICATION_Parameters[1]);
            log.info("\n- SONIFICATION_CE = " + SONIFICATION_Parameters[2]);
            log.info("\n- Enable Notification = " + SONIFICATION_Parameters[3]);
            log.info("\n- Enable MIDI Analysis = " + SONIFICATION_Parameters[5]);
            log.info("\n- Description = " + SONIFICATION_Parameters[4]);
            
            // Advanced Settings for ASCIItoMIDI...
            log.info("\n- Range Min = " + SONIFICATION_Parameters[6]);
            log.info("\n- Range Max = " + SONIFICATION_Parameters[7]);
            log.info("\n- MultiScale = " + SONIFICATION_Parameters[8]);
            log.info("\n- Speed = " + SONIFICATION_Parameters[9]);
            
            // Advanced Settings for DATASETtoMIDI...
            log.info("\n- Time = " + SONIFICATION_Parameters[10]);
            
            // Advanced Settings for DATASETtoWAVEFORM...
            log.info("\n- Bytes per Sample = " + SONIFICATION_Parameters[11]);
            log.info("\n- Sample Frequency = " + SONIFICATION_Parameters[12]);
            log.info("\n- Resample Frequency = " + SONIFICATION_Parameters[13]);
            log.info("\n- Scale = " + SONIFICATION_Parameters[14]);
            log.info("\n- DataType = " + SONIFICATION_Parameters[15]);
            
            // Preparing to submit SONIFICATION jobs in different grid infrastructure..
            //=============================================================
            // IMPORTANT: INSTANCIATE THE MultiInfrastructureJobSubmission
            //            CLASS USING THE EMPTY CONSTRUCTOR WHEN
            //            WHEN THE PORTLET IS DEPLOYED IN PRODUCTION!!!
            //=============================================================
            /*MultiInfrastructureJobSubmission SonificationMultiJobSubmission =
            new MultiInfrastructureJobSubmission(TRACKING_DB_HOSTNAME,
                                                 TRACKING_DB_USERNAME,
                                                 TRACKING_DB_PASSWORD);*/
            
            MultiInfrastructureJobSubmission SonificationMultiJobSubmission =
                new MultiInfrastructureJobSubmission();

            // Set the list of infrastructure(s) activated for the SONIFICATION portlet           
            if (infrastructures[0]!=null) {
                log.info("\n- Adding the COMETA Infrastructure.");
                 SonificationMultiJobSubmission.addInfrastructure(infrastructures[0]); 
            }
            if (infrastructures[1]!=null) {
                log.info("\n- Adding the GARUDA Infrastructure.");
                 SonificationMultiJobSubmission.addInfrastructure(infrastructures[1]); 
            }
            if (infrastructures[2]!=null) {
                log.info("\n- Adding the GRIDIT Infrastructure.");
                 SonificationMultiJobSubmission.addInfrastructure(infrastructures[2]); 
            }
            if (infrastructures[3]!=null) {
                log.info("\n- Adding the GILDA Infrastructure.");
                 SonificationMultiJobSubmission.addInfrastructure(infrastructures[3]);
            }
            if (infrastructures[4]!=null) {
                log.info("\n- Adding the EUMED Infrastructure.");
                 SonificationMultiJobSubmission.addInfrastructure(infrastructures[4]);
            }
            if (infrastructures[5]!=null) {
                log.info("\n- Adding the GISELA Infrastructure.");
                 SonificationMultiJobSubmission.addInfrastructure(infrastructures[5]);
            }
            if (infrastructures[6]!=null) {
                log.info("\n- Adding the SAGRID Infrastructure.");
                 SonificationMultiJobSubmission.addInfrastructure(infrastructures[6]);
            }

            String SonificationFilesPath = getPortletContext().getRealPath("/") +
                                    "WEB-INF/config"; 
            
            InfrastructureInfo infrastructure = 
                    SonificationMultiJobSubmission.getInfrastructure();
            
            String Middleware = null;
            if (infrastructure.getMiddleware().equals("glite"))
                    Middleware = "glite";
            if (infrastructure.getMiddleware().equals("wsgram"))
                    Middleware = "wsgram";
            
            String EnableMIDI = "null";
            if (SONIFICATION_Parameters[5] != null)
                EnableMIDI = "EnableMIDI";
            
            log.info("\n- Enabled Middleware = " + Middleware);
            log.info("\n- Enabled MIDI ANalysis = " + EnableMIDI);
            
            // Set the Output path forresults
            //SonificationMultiJobSubmission.setOutputPath("/tmp");
            SonificationMultiJobSubmission.setOutputPath(sonification_OUTPUT_PATH);

            // Set the StandardOutput for SONIFICATION
            SonificationMultiJobSubmission.setJobOutput("Text2Midi.out");

            // Set the StandardError for SONIFICATION
            SonificationMultiJobSubmission.setJobError("Text2Midi.err");

            // A.) SONIFICTION TYPE = [ ASCIItoMIDI ]
            if (SONIFICATION_Parameters[1].equals("ASCIItoMIDI")) 
            {
                log.info ("\n\nPreparing a simulation for the following Sonification Type [ ASCIItoMIDI ] ");
                
                // Set the Executable for SONIFICATION
                SonificationMultiJobSubmission.setExecutable("Text2Midi.sh");

                String Arguments = SONIFICATION_Parameters[0] + 
                                   "," +
                                   Middleware + 
                                   "," +
                                   EnableMIDI +
                                   "," +
                                   SONIFICATION_Parameters[6] +
                                   "," +
                                   SONIFICATION_Parameters[7] +
                                   "," +
                                   SONIFICATION_Parameters[8] +
                                   "," +
                                   SONIFICATION_Parameters[9];
                
                // Set the list of Arguments for SONIFICATION
                SonificationMultiJobSubmission.setArguments(Arguments);
                
                String InputSandbox = SonificationFilesPath + "/Text2Midi.sh" +
                                      "," +
                                      SonificationFilesPath + "/Text2Midi.java" +
                                      "," +
                                      SonificationFilesPath + "/jmusic.jar" +
                                      "," +
                                      SonificationFilesPath + "/jargs.jar" +
                                      "," +
                                      SonificationFilesPath + "/log4j-1.2.15.jar" +
                                      "," +
                                      SonificationFilesPath + "/run_midi.sh" +
                                      "," +
                                      SonificationFilesPath + "/midi_wrapper.sh" +
                                      "," +
                                      SonificationFilesPath + "/midi" +                                      
                                      "," +
                                      SONIFICATION_Parameters[0];

                // Set InputSandbox files (string with comma separated list of file names)
                SonificationMultiJobSubmission.setInputFiles(InputSandbox);                                

                // OutputSandbox (string with comma separated list of file names)                
                String SonificationFiles="";
                if (EnableMIDI.equals("EnableMIDI")) 
                    SonificationFiles="messages.mid, output.README, figure.zip";
                else SonificationFiles="messages.mid, output.README";                    

                // Set the OutputSandbox files (string with comma separated list of file names)
                SonificationMultiJobSubmission.setOutputFiles(SonificationFiles);
            } 
            
            // B.) SONIFICTION TYPE = [ DATASETtoMIDI ]
            if (SONIFICATION_Parameters[1].equals("DATASETtoMIDI")) 
            {     
                log.info ("\n\nPreparing a simulation for the following Sonification Type [ DATASETtoMIDI ] ");
                
                // Set the Executable for SONIFICATION
                SonificationMultiJobSubmission.setExecutable("Data2Midi.sh");
                
                String Arguments = SONIFICATION_Parameters[0] + 
                                   "," +
                                   Middleware + 
                                   "," +
                                   EnableMIDI +
                                   "," +
                                   SONIFICATION_Parameters[10];
                
                // Set the list of Arguments for SONIFICATION
                SonificationMultiJobSubmission.setArguments(Arguments);
                
                String InputSandbox = SonificationFilesPath + "/Data2Midi.sh" +
                                      "," +
                                      SonificationFilesPath + "/TunguScore.java" +
                                      "," +
                                      SonificationFilesPath + "/jmusic.jar" +
                                      "," +
                                      SonificationFilesPath + "/jargs.jar" +
                                      "," +
                                      SonificationFilesPath + "/log4j-1.2.15.jar" +
                                      "," +
                                      SonificationFilesPath + "/run_midi.sh" +
                                      "," +
                                      SonificationFilesPath + "/midi_wrapper.sh" +
                                      "," +
                                      SonificationFilesPath + "/midi" +
                                      "," +
                                      SONIFICATION_Parameters[0];

                // Set InputSandbox files (string with comma separated list of file names)
                SonificationMultiJobSubmission.setInputFiles(InputSandbox);
                
                // OutputSandbox (string with comma separated list of file names)                
                String SonificationFiles="";
                if (EnableMIDI.equals("EnableMIDI")) 
                    SonificationFiles="TunguScore.mid, output.README, figure.zip";
                else SonificationFiles="TunguScore.mid, output.README";
                
                // Set the OutputSandbox files (string with comma separated list of file names)
                SonificationMultiJobSubmission.setOutputFiles(SonificationFiles);                                
            }
            
            // C.) SONIFICTION TYPE = [ DATASETtoWAVE ]
            if (SONIFICATION_Parameters[1].equals("DATASETtoWAVE")) 
            {     
                log.info ("\n\nPreparing a simulation for the following Sonification Type [ DATASETtoWAVE ] ");
                
                // Set the Executable for SONIFICATION
                SonificationMultiJobSubmission.setExecutable("sonification.sh");
                
                String Arguments = SONIFICATION_Parameters[0] + 
                                   "," +
                                   Middleware + 
                                   "," +
                                   SONIFICATION_Parameters[11] +
                                   "," +
                                   SONIFICATION_Parameters[12] +
                                   "," +
                                   SONIFICATION_Parameters[13] +
                                   "," +
                                   SONIFICATION_Parameters[14] +
                                   "," +
                                   SONIFICATION_Parameters[15];
                
                // Set the list of Arguments for SONIFICATION
                SonificationMultiJobSubmission.setArguments(Arguments);
                
                String InputSandbox = SonificationFilesPath + "/Sonification.java" +
                                      "," +
                                      SonificationFilesPath + "/sonification.sh" +                                        
                                      "," +
                                      SonificationFilesPath + "/jargs.jar" +
                                      "," +
                                      SonificationFilesPath + "/log4j-1.2.15.jar" +
                                      "," +
                                      SONIFICATION_Parameters[0];

                // Set InputSandbox files (string with comma separated list of file names)
                SonificationMultiJobSubmission.setInputFiles(InputSandbox);
                
                // OutputSandbox (string with comma separated list of file names)
                String SonificationFiles="Sonification.aiff, Sound.dat, output.README";
                
                // Set the OutputSandbox files (string with comma separated list of file names)
                SonificationMultiJobSubmission.setOutputFiles(SonificationFiles);
            }
            
            // D.) SONIFICTION TYPE = [ ASCIItoSPHERE ]
            if (SONIFICATION_Parameters[1].equals("ASCIItoSPHERE")) 
            {     
                log.info ("\n\nPreparing a simulation for the following Sonification Type [ ASCIItoSPHERE ] ");
                
                // Set the Executable for SONIFICATION
                SonificationMultiJobSubmission.setExecutable("MessageToSpheres.sh");
                
                String Arguments = SONIFICATION_Parameters[0] + 
                                   "," +
                                   Middleware;
                
                // Set the list of Arguments for SONIFICATION
                SonificationMultiJobSubmission.setArguments(Arguments);
                
                String InputSandbox = SonificationFilesPath + "/MessageToSpheres.sh" +
                                      "," +
                                      SonificationFilesPath + "/MessageToSpheres.java" +
                                      "," +
                                      SonificationFilesPath + "/jmusic.jar" +
                                      "," +
                                      SonificationFilesPath + "/jargs.jar" +
                                      "," +
                                      SonificationFilesPath + "/log4j-1.2.15.jar" +
                                      "," +
                                      SonificationFilesPath + "/inputs.tar.gz" +
                                      "," +
                                      SonificationFilesPath + "/povray.conf" +
                                      "," +
                                      SonificationFilesPath + "/povray-template.ini" +
                                      "," +
                                      SonificationFilesPath + "/povray-template-garuda.ini" +
                                      "," +
                                      SONIFICATION_Parameters[0];
                
                // Set InputSandbox files (string with comma separated list of file names)
                SonificationMultiJobSubmission.setInputFiles(InputSandbox);                                
                
                // OutputSandbox (string with comma separated list of file names)
                String SonificationFiles = "MessageToSpheres1.png, "
                                         + "MessageToSpheres2.png, "
                                         + "MessageToSpheres3.png, "
                                         + "MessageToSpheres4.png, "
                                         + "MessageToSpheres5.png, "                                         
                                         + "output.README";                
                
                // Set the OutputSandbox files (string with comma separated list of file names)
                SonificationMultiJobSubmission.setOutputFiles(SonificationFiles);
            }            

            // Set the queue if it's defined
            // This option is not supported in multi-infrastructures mode
            if (NMAX==1) {
                if (!SONIFICATION_Parameters[2].isEmpty())
                    SonificationMultiJobSubmission.setJobQueue(SONIFICATION_Parameters[2]);
                //else //SonificationMultiJobSubmission.setRandomCE(true);
                  //  SonificationMultiJobSubmission.setJobQueue(RANDOM_CE);
            }
            
            // Adding the proper requirements to run more than 24h
            /*String jdlRequirements[] = new String[1];
            jdlRequirements[0] = "JDLRequirements=(other.GlueCEPolicyMaxCPUTime>1440)";
            SonificationMultiJobSubmission.setJDLRequirements(jdlRequirements);*/

            InetAddress addr = InetAddress.getLocalHost();           
            Company company;
            
            try {
                company = PortalUtil.getCompany(request);
                String gateway = company.getName();
                
                // Send a notification email to the user if enabled.
                if (SONIFICATION_Parameters[3]!=null)
                    if ( (SMTP_HOST==null) || 
                         (SMTP_HOST.trim().equals("")) ||
                         (SMTP_HOST.trim().equals("N/A")) ||
                         (SENDER_MAIL==null) || 
                         (SENDER_MAIL.trim().equals("")) ||
                         (SENDER_MAIL.trim().equals("N/A"))
                       )
                    log.info ("\nThe Notification Service is not properly configured!!");
                    
                 else {
                        // Enabling Job's notification via email
                        SonificationMultiJobSubmission.setUserEmail(emailAddress);
                        
                        sendHTMLEmail(username, 
                                      emailAddress, 
                                      SENDER_MAIL, 
                                      SMTP_HOST, 
                                      "SONIFICATION", 
                                      gateway);
                }
                
                // Submitting...
                log.info("\n- SONIFICATION job submittion in progress using JSAGA JobEngine");
                SonificationMultiJobSubmission.submitJobAsync(
                    infrastructure,
                    username,
                    addr.getHostAddress()+":8162",
                    Integer.valueOf(sonification_APPID),
                    SONIFICATION_Parameters[4]);
                
            } catch (PortalException ex) {
                Logger.getLogger(Sonification.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(Sonification.class.getName()).log(Level.SEVERE, null, ex);
            }                        
        } // end PROCESS ACTION [ SUBMIT_SONIFICATION_PORTLET ]
    }

    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response)
                throws PortletException, IOException
    {
        //super.serveResource(request, response);

        PortletPreferences portletPreferences = (PortletPreferences) request.getPreferences();

        final String action = (String) request.getParameter("action");

        if (action.equals("get-ratings")) {
            //Get CE Ratings from the portlet preferences
            String sonification_CE = (String) request.getParameter("sonification_CE");

            String json = "{ \"avg\":\"" + 
        	          portletPreferences.getValue(sonification_CE+"_avg", "0.0") +
                    	  "\", \"cnt\":\"" + 
			  portletPreferences.getValue(sonification_CE+"_cnt", "0") + "\"}";

            response.setContentType("application/json");
            response.getPortletOutputStream().write( json.getBytes() );

        } else if (action.equals("set-ratings")) {

            String sonification_CE = (String) request.getParameter("sonification_CE");
            int vote = Integer.parseInt(request.getParameter("vote"));

             double avg = Double.parseDouble(portletPreferences.getValue(sonification_CE+"_avg", "0.0"));
             long cnt = Long.parseLong(portletPreferences.getValue(sonification_CE+"_cnt", "0"));

             portletPreferences.setValue(sonification_CE+"_avg", Double.toString(((avg*cnt)+vote) / (cnt +1)));
             portletPreferences.setValue(sonification_CE+"_cnt", Long.toString(cnt+1));

             portletPreferences.store();
        }
    }


    // Upload SONIFICATION input files
    public String[] uploadSonificationSettings(ActionRequest actionRequest,
                                        ActionResponse actionResponse, String username)
    {
        String[] SONIFICATION_Parameters = new String [16];
        boolean status;

        // Check that we have a file upload request
        boolean isMultipart = PortletFileUpload.isMultipartContent(actionRequest);

        if (isMultipart) {
            // Create a factory for disk-based file items.
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Set factory constrains
            File SONIFICATION_Repository = new File ("/tmp");
            if (!SONIFICATION_Repository.exists()) status = SONIFICATION_Repository.mkdirs();
            factory.setRepository(SONIFICATION_Repository);

            // Create a new file upload handler.
            PortletFileUpload upload = new PortletFileUpload(factory);

            try {
                    // Parse the request
                    List items = upload.parseRequest(actionRequest);

                    // Processing items
                    Iterator iter = items.iterator();

                    while (iter.hasNext())
                    {
                        FileItem item = (FileItem) iter.next();

                        String fieldName = item.getFieldName();
                        
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        String timeStamp = dateFormat.format(Calendar.getInstance().getTime());

                        // Processing a regular form field
                        if ( item.isFormField() )
                        {                                                        
                            if (fieldName.equals("sonification_textarea")) 
                            {
                                SONIFICATION_Parameters[0]=
                                        SONIFICATION_Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        ".txt";
                            
                                // Store the textarea in a ASCII file
                                storeString(SONIFICATION_Parameters[0], 
                                            item.getString());                               
                            }
                            
                            if (fieldName.equals("sonificationtype"))
                                SONIFICATION_Parameters[1]=item.getString();
                                                        
                            if (fieldName.equals("sonification_CE"))
                                SONIFICATION_Parameters[2]=item.getString();                                                       
                            
                        } else {
                            // Processing a file upload
                            if (fieldName.equals("sonification_file"))
                            {                                                               
                                log.info("\n- Uploading the following user's file: "
                                       + "\n[ " + item.getName() + " ]"
                                       + "\n[ " + item.getContentType() + " ]"
                                       + "\n[ " + item.getSize() + "KBytes ]"
                                       );                               

                                // Writing the file to disk
                                String uploadSonificationFile = 
                                        SONIFICATION_Repository +
                                        "/" + timeStamp +
                                        "_" + username +
                                        "_" + item.getName();

                                log.info("\n- Writing the user's file: [ "
                                        + uploadSonificationFile.toString()
                                        + " ] to disk");

                                item.write(new File(uploadSonificationFile)); 
                                
                                SONIFICATION_Parameters[0]=uploadSonificationFile;                                
                            }
                        }
                        
                        if (fieldName.equals("EnableNotification"))
                                SONIFICATION_Parameters[3]=item.getString(); 
                        
                        if (fieldName.equals("sonification_desc"))                                
                                if (item.getString().equals("Please, insert here a description for your job"))
                                    SONIFICATION_Parameters[4]="Data Sonification Simulation Started";
                                else
                                    SONIFICATION_Parameters[4]=item.getString();
                        
                        if (fieldName.equals("EnableMIDIAnalysis"))
                                SONIFICATION_Parameters[5]=item.getString(); 
                        
                        // Advanced Settings here..
                        if (fieldName.equals("range_min"))
                                SONIFICATION_Parameters[6]=item.getString();
                        
                        if (fieldName.equals("range_max"))
                                SONIFICATION_Parameters[7]=item.getString();
                        
                        if (fieldName.equals("multiscale"))
                                SONIFICATION_Parameters[8]=item.getString();
                        
                        if (fieldName.equals("speed"))
                                SONIFICATION_Parameters[9]=item.getString();
                        
                        if (fieldName.equals("time"))
                                SONIFICATION_Parameters[10]=item.getString();
                        
                        if (fieldName.equals("bytes_per_sample"))
                                SONIFICATION_Parameters[11]=item.getString();
                        
                        if (fieldName.equals("sample_frequency"))
                                SONIFICATION_Parameters[12]=item.getString();
                        
                        if (fieldName.equals("resample_frequency"))
                                SONIFICATION_Parameters[13]=item.getString();
                        
                        if (fieldName.equals("scale"))
                                SONIFICATION_Parameters[14]=item.getString();
                        
                        if (fieldName.equals("datatype"))
                                SONIFICATION_Parameters[15]=item.getString();
                        
                    } // end while
            } catch (FileUploadException ex) {
              Logger.getLogger(Sonification.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
              Logger.getLogger(Sonification.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return SONIFICATION_Parameters;
    }
    
    // Retrieve a random Computing Element
    // matching the Software Tag for the SONIFICATION application    
    public String getRandomCE(String sonification_VONAME,
                              String sonification_TOPBDII,
                              String sonification_SOFTWARE)
                              throws PortletException, IOException
    {
        String randomCE = null;
        BDII bdii = null;    
        List<String> CEqueues = null;
                        
        log.info("\n- Querying the Information System [ " + 
                  sonification_TOPBDII + 
                  " ] and retrieving a random CE matching the SW tag [ VO-" + 
                  sonification_VONAME +
                  "-" +
                  sonification_SOFTWARE + " ]");  

       try {               

                bdii = new BDII( new URI(sonification_TOPBDII) );
                
                // Get the list of the available queues
                CEqueues = bdii.queryCEQueues(sonification_VONAME);
                
                // Get the list of the Computing Elements for the given SW tag
                randomCE = bdii.getRandomCEForSWTag("VO-" + 
                                              sonification_VONAME + 
                                              "-" +
                                              sonification_SOFTWARE);
                
                // Fetching the Queues
                for (String CEqueue:CEqueues) {
                    if (CEqueue.contains(randomCE))
                        randomCE=CEqueue;
                }

        } catch (URISyntaxException ex) {
                Logger.getLogger(Sonification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
                Logger.getLogger(Sonification.class.getName()).log(Level.SEVERE, null, ex);
        }                   

        return randomCE;
    }

    // Retrieve the list of Computing Elements
    // matching the Software Tag for the SONIFICATION application    
    public List<String> getListofCEForSoftwareTag(String sonification_VONAME,
                                                  String sonification_TOPBDII,
                                                  String sonification_SOFTWARE)
                                throws PortletException, IOException
    {
        List<String> CEs_list = null;
        BDII bdii = null;        
        
        log.info("\n- Querying the Information System [ " + 
                  sonification_TOPBDII + 
                  " ] and looking for CEs matching the SW tag [ VO-" + 
                  sonification_VONAME +
                  "-" +
                  sonification_SOFTWARE + " ]");  

       try {               
           
                bdii = new BDII( new URI(sonification_TOPBDII) );                
                
                if (!sonification_SOFTWARE.trim().isEmpty())                     
                    CEs_list = bdii.queryCEForSWTag("VO-" +
                                                    sonification_VONAME +
                                                    "-" +
                                                    sonification_SOFTWARE);                
                /*else
                    CEs_list = bdii.queryCEQueues(sonification_VONAME);*/
                
                // Fetching the CE hostnames
                for (String CE:CEs_list) 
                    log.info("\n- CE host found = " + CE);

        } catch (URISyntaxException ex) {
                Logger.getLogger(Sonification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
                Logger.getLogger(Sonification.class.getName()).log(Level.SEVERE, null, ex);
        }

        return CEs_list;
    }

    // Get the GPS location of the given grid resource
    public String[] getCECoordinate(RenderRequest request,
                                    String CE)
                                    throws PortletException, IOException
    {
        String[] GPS_locations = null;
        BDII bdii = null;

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        // Get the SONIFICATION TOPPBDII from the portlet preferences
        String cometa_sonification_TOPBDII = 
                portletPreferences.getValue("cometa_sonification_TOPBDII", "N/A");
        String gridit_sonification_TOPBDII = 
                portletPreferences.getValue("gridit_sonification_TOPBDII", "N/A");
        String gilda_sonification_TOPBDII = 
                portletPreferences.getValue("gilda_sonification_TOPBDII", "N/A");
        String eumed_sonification_TOPBDII = 
                portletPreferences.getValue("eumed_sonification_TOPBDII", "N/A");
        String gisela_sonification_TOPBDII = 
                portletPreferences.getValue("gisela_sonification_TOPBDII", "N/A");
        String sagrid_sonification_TOPBDII = 
                portletPreferences.getValue("sagrid_sonification_TOPBDII", "N/A");
        
        // Get the SONIFICATION ENABLEINFRASTRUCTURE from the portlet preferences
        String sonification_ENABLEINFRASTRUCTURE = 
                portletPreferences.getValue("sonification_ENABLEINFRASTRUCTURE", "N/A");

            try {
                if ( sonification_ENABLEINFRASTRUCTURE.equals("cometa") )
                     bdii = new BDII( new URI(cometa_sonification_TOPBDII) );
                
                if ( sonification_ENABLEINFRASTRUCTURE.equals("gridit") )
                     bdii = new BDII( new URI(gridit_sonification_TOPBDII) );
                
                if ( sonification_ENABLEINFRASTRUCTURE.equals("gilda") )
                     bdii = new BDII( new URI(gilda_sonification_TOPBDII) );                

                if ( sonification_ENABLEINFRASTRUCTURE.equals("eumed") )
                     bdii = new BDII( new URI(eumed_sonification_TOPBDII) );

                if ( sonification_ENABLEINFRASTRUCTURE.equals("gisela") )
                    bdii = new BDII( new URI(gisela_sonification_TOPBDII) );
                
                if ( sonification_ENABLEINFRASTRUCTURE.equals("sagrid") )
                    bdii = new BDII( new URI(sagrid_sonification_TOPBDII) );

                GPS_locations = bdii.queryCECoordinate("ldap://" + CE + ":2170");

            } catch (URISyntaxException ex) {
                Logger.getLogger(Sonification.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Sonification.class.getName()).log(Level.SEVERE, null, ex);
            }

            return GPS_locations;
    }
    
    private void storeString (String fileName, String fileContent) 
                              throws IOException 
    { 
        log.info("\n- Writing textarea in a ASCII file [ " + fileName + " ]");
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));                
        writer.write(fileContent);
        writer.write("\n");
        writer.close();
    }
    
    private void sendHTMLEmail (String USERNAME,
                                String TO, 
                                String FROM, 
                                String SMTP_HOST, 
                                String ApplicationAcronym,
                                String GATEWAY)
    {
                
        log.info("\n- Sending email notification to the user " + USERNAME + " [ " + TO + " ]");
        
        log.info("\n- SMTP Server = " + SMTP_HOST);
        log.info("\n- Sender = " + FROM);
        log.info("\n- Receiver = " + TO);
        log.info("\n- Application = " + ApplicationAcronym);
        log.info("\n- Gateway = " + GATEWAY);        
        
        // Assuming you are sending email from localhost
        String HOST = "localhost";
        
        // Get system properties
        Properties properties = System.getProperties();
        properties.setProperty(SMTP_HOST, HOST);
        
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        
        try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(FROM));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
         //message.addRecipient(Message.RecipientType.CC, new InternetAddress(FROM));

         // Set Subject: header field
         message.setSubject(" [liferay-sg-gateway] - [ " + GATEWAY + " ] ");

	 Date currentDate = new Date();
	 currentDate.setTime (currentDate.getTime());

         // Send the actual HTML message, as big as you like
         message.setContent(
	 "<br/><H4>" +         
	 "<img src=\"http://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc6/195775_220075701389624_155250493_n.jpg\" width=\"100\">Science Gateway Notification" +
	 "</H4><hr><br/>" +
         "<b>Description:</b> Notification for the application <b>[ " + ApplicationAcronym + " ]</b><br/><br/>" +         
         "<i>The application has been successfully submitted from the [ " + GATEWAY + " ]</i><br/><br/>" +
         "<b>TimeStamp:</b> " + currentDate + "<br/><br/>" +
	 "<b>Disclaimer:</b><br/>" +
	 "<i>This is an automatic message sent by the Science Gateway based on Liferay technology.<br/>" + 
	 "If you did not submit any jobs through the Science Gateway, please " +
         "<a href=\"mailto:" + FROM + "\">contact us</a></i>",         
	 "text/html");

         // Send message
         Transport.send(message);         
      } catch (MessagingException mex) { mex.printStackTrace(); }        
    }
}
