package Utility;

import android.app.Activity;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import DataTransforObject.LocationType;
import DataTransforObject.MyPropertyDTO;
import DataTransforObject.PropertyType;
import DataTransforObject.SearchResult;


/**
 * Created by abc on 11/06/2015.
 */
public class CommanString
{



 final public static String USER_ID = "user_id";
 final public static String FIRST_NAME = "firstName";
 final public static String LAST_NAME = "lastName";
 final public static String EMAIL_ID = "email";
 final public static String USER_TYPE = "user_type";
 final public static String PAYMENT_URL = "paymnet_url";

 final public static String EXPIRY_DATE = "expiry_date";
 final public static String PROFILE_STATUS = "profile_status";
 final public static String PAYMENT_STATUS = "payment_status";


 final public static String USER_PASSWORD = "user_password";


 final public static String VERSION_CODE = "version_code";

 final public static String VERSION_URL = "version_url";

 final public static String LATTITUDE = "lattitude";
 final public static String LONGITUDE = "longitude";

 public static Activity  prevactivity= null;
 public static   Activity  SearchHome = null;

 public static   Activity  SearchActivity = null;

  public static List<PropertyType> Property_list = null;
 public static List<LocationType> Location_list = null;

 public static String msg_read_status = "";
 public static List<SearchResult> SearchResult_list = null;

 public static List<SearchResult> FeaturedContent_list = new ArrayList<SearchResult>();




 public static List<SearchResult> NewestContent_list = new ArrayList<SearchResult>();


 public static List<SearchResult> CompareResultList = new ArrayList<SearchResult>();
 public static List<SearchResult> CompareNormalResultList = new ArrayList<SearchResult>();


 public static List<SearchResult> favorite_list = new ArrayList<SearchResult>();;


 public static List<SearchResult> NormalContent_list = null;



 public static List<MyPropertyDTO> myPropertyDTOs = new ArrayList<MyPropertyDTO>();
 public  static List<Bitmap> myproperty_image = new ArrayList<Bitmap>();;


 //public static List<Bitmap> Featured_image_list = null;
// public static List<Bitmap> Favorite_image_list = null;


 //public static List<Bitmap> Compare_image_list = null;

 //public static List<Bitmap> CompareNormal_image_list = null;



// public static List<Bitmap> Normal_image_list = null;

 public static boolean is_from_map = false;
 public static String property_type = "";
 public static int  property_index = 0;

 public static boolean From_Myaccount = false;
 public static boolean From_MProperty = false;

 public static boolean From_Message = false;
 public static boolean From_Favorite = false;

 public static boolean is_from_mainactivity1 = false;

final public static String Image_Url = "http://v2.bahamasrenter.com/Media/propertiesImage/";
// final public static String Image_Url = "http://bahamasrenter.com/applicationMediaFiles/propertiesImage/";



 public static ArrayList<String> Keywordslist = null;
 public static ArrayList<String> nearbycategorylist = null;


}
