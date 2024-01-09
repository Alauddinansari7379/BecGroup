package com.amtechsolutions.becpbas.api;

public class RestUtils {

    public static String getEndPoint(String URL_Type) {

        String STG = "https://hrapp.becoman.com:7476/hrmsmui/";

        //        switch (URL_Type) {
//            case Constants.GOOD_API_DEFAULT:
//                baseUrl = BuildConfig.DEBUG ? BuildConfig.STAGING_END_POINT : BuildConfig.PRODUCTION_END_POINT;
//                break;
//            case Constants.GOOD_API_CO:
//                baseUrl = Constants.GOOD_API_CO;
//                break;
//            case Constants.GOOD_API_PH:
//                baseUrl = Constants.GOOD_API_PH;
//                break;
//        }
      // return BuildConfig.BASE_URL;
   // return TEST;
           return STG;
    }
}
