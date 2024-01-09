package com.amtechsolutions.becpbas.utils;

/**
 * Created by Shourav Paul on 12-03-2022.
 **/
public class AppConstants
{
    //private static final String mainUrl = "http://188.135.51.77:9097/hrmsmui/";
    private static final String mainUrl = "https://hrapp.becoman.com:7476/hrmsmui/";
    public static String validateUrl = mainUrl + "validate?";
    public static String dataSyncUrl = mainUrl + "syncServiceReqMob/saveorUpdateSyncServiceRequest?lastSynDate=";
    public static String saveRecBySupervisorUrl = mainUrl + "supervisorReqMob/saveorUpdateSupervisorRequest";
    public static String saveRecBySelfUrl = mainUrl + "selfServiceReqMob/saveorUpdateSelfServiceRequest";
    public static int currentUserKey;
    public static int currentEmpKey;
    public static String currentUsername="";
    public static String currentEmpCode="";
    public static String geoFenceReq="";
    public static String capPhotoMand="";
    public static String captureMode="";
    public static String geoArrStr = "";
    public static int companyKey;
    public static String userType = "";
    public static String empCODE = "";
    public static String token = "eyJ1c2VyS2V5IjoxLCJuYW1lUCI6ImFkbWluIiwibmFtZVMiOiJhZG1pbiIsImxvZ2luSWQiOiJhZG1pbiIsInVzZXJQYXNzd29yZCI6IjgzMDU1NjgwNzY4MjY0NTkxNjEyMTE3MTMyNDM3MDUxNzA3MjY0IiwiZW1haWxJZCI6ImF6aGFyQGZvcnR1bmVvbWFuLmNvbSIsImNvbnRhY3RObyI6IjEyMzQyMSIsInVzZXJHcm91cCI6MSwiZW50aXR5S2V5IjpudWxsLCJhY3RpdmVZTiI6IkEiLCJlZmZTdGFydERhdGUiOm51bGwsImVmZkVuZERhdGUiOm51bGwsInNlcU5vIjpudWxsLCJjcmVhdGVkVXNlciI6MSwiY3JlYXRlZERhdGVUaW1lIjpudWxsLCJ1cGRhdGVkVXNlciI6MSwidXBkYXRlZERhdGVUaW1lIjpudWxsLCJ1c3JDb21wYW55S2V5IjoxLCJ1c3JBdXRoVHlwZSI6MjAxLCJ1c3JMb2NrWU4iOiJOIiwiZW1wS2V5IjoxNjUxLCJ1c3JBdXRoVHlwZUtleXdvcmQiOiJCIiwiZ3JvdXBOYW1lIjpudWxsLCJlbnRpdHlOYW1lIjpudWxsLCJjcmVhdGVkVXNlck5hbWUiOm51bGwsInVwZGF0ZWRVc2VyTmFtZSI6bnVsbCwicGFzc3dvcmRQb2xpY3lSZXN1bHQiOm51bGwsInVzZXJUaGVtZU5hbWUiOiJkZWZhdWx0IiwidXNlckhvbWVQYWdlQXR0ciI6Ik1haW5Ib21lIiwidXNlckRlZmF1bHRNb2R1bGUiOiJFTVBMT1lFRVNFTEZTRVJWSUNFIiwiYWxsQ29tcEFwcFRvVXNlciI6bnVsbCwiY29tcGFueU5hbWUiOiJCYWh3YW4gRW5naW5lZXJpbmcgQ29tcGFueSBMTEMiLCJhdWRpdE1lbUVtcE5hbWVQIjpudWxsLCJkZXNpZ25hdGlvbkNvZGUiOm51bGwsImVtcE5hbWUiOm51bGwsImRlc2lnS2V5IjoxOTMsImV4cGlyZXMiOjE2NDc0NTM0NjEwMDQsInBsYW5nIjoiRU4iLCJzbGFuZyI6IkFSIn0=.gkgleNawEgjWtm9Rb0r6fgbC4Ip0auKAhLKSTkq9Ia8=";
}
