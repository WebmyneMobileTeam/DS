package com.wm.model;

public class AppConstants {
    // changed again

    public static boolean isSandBox = false;
    /** Duration for splash screen in LONG **/
    public static long SPLASH_DURATION = 2000;
    /** Time INterval for splash screen in LONG **/
    public static long SPLASH_INTERVAL = 1000;
    /** Show hide logs and prints from all application to save memory **/
    public static boolean SHOW_LOGS = true;

    public static boolean USER_TYPE = false; // false-new user, true-registered
    public static boolean ADDRESS_TYPE = false; // false-old address, true-new address
    /** Custom Font name stored in asset folder **/
    public static String CUSTOM_FONT = "GOTHIC.TTF";
    
    public static String SERVER_URL;
    public static String IMAGE_LINK_PREFIX;
    public static String URL_GETCUSTOMERINFO;
    public static String URL_SUBMITORDER;
    public static String URL_CONFIRMPAYMENT;

    public static void init(){

        if(isSandBox == true){ // webmyne
            
                SERVER_URL = "http://ws-srv-net.in.webmyne.com/Applications/Harmony/DaraybSofe/Daraybsofe/Products.svc/json/"; // main
                IMAGE_LINK_PREFIX = "http://ws-srv-net.in.webmyne.com/Applications/Harmony/DaraybSofe/admin.daryabsofe.com/"; // main
                URL_GETCUSTOMERINFO = "http://ws-srv-net.in.webmyne.com/Applications/Harmony/DaraybSofe/Daraybsofe/User.svc/json/GetCustomerInfo"; // main
                URL_SUBMITORDER = "http://ws-srv-net.in.webmyne.com/Applications/Harmony/DaraybSofe/Daraybsofe/Order.svc/json/SubmitOrder"; // main
                URL_CONFIRMPAYMENT = "http:// ws-srv-net.in.webmyne.com/Applications/Harmony/DaraybSofe/Daraybsofe/Order.svc/json/ConfirmPayment"; //main

        }else{ // google play
            /*   SERVER_URL = "http://ws.wvps46-163-104-177.dedicated.hosteurope.de/Products.svc/json/";// client
               IMAGE_LINK_PREFIX = "http://mysite.wvps46-163-104-177.dedicated.hosteurope.de"; // client
               URL_GETCUSTOMERINFO = "http://ws.wvps46-163-104-177.dedicated.hosteurope.de/User.svc/json/GetCustomerInfo"; // client
               URL_SUBMITORDER = "http://ws.wvps46-163-104-177.dedicated.hosteurope.de/Order.svc/json/SubmitOrder"; // client
               URL_CONFIRMPAYMENT = "http://ws.wvps46-163-104-177.dedicated.hosteurope.de/Order.svc/json/ConfirmPayment"; //client*/

            SERVER_URL = "http://46.163.104.177:180/Products.svc/json/";// client
            IMAGE_LINK_PREFIX = "http://ws-srv-net.in.webmyne.com/Applications/Harmony/DaraybSofe/admin.daryabsofe.com/"; // client
            URL_GETCUSTOMERINFO = "http://46.163.104.177:180/User.svc/json/GetCustomerInfo"; // client
            URL_SUBMITORDER = "http://46.163.104.177:180/Order.svc/json/SubmitOrder"; // client
            URL_CONFIRMPAYMENT = "http://46.163.104.177:180/Order.svc/json/ConfirmPayment"; //client

        }
        
    }
    
    
   // http://ws.wvps46-163-104-177.dedicated.hosteurope.de/Products.svc/json/getAllProducts

  //  http://ws.wvps46-163-104-177.dedicated.hosteurope.de/Products.svc/json/getAllProducts

	




}
