package Communication;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import DataTransforObject.AddPropertyDTO;
import DataTransforObject.AdvertisDTO;
import DataTransforObject.AreaType;
import DataTransforObject.Discription;
import DataTransforObject.LocationType;
import DataTransforObject.MessageObject;
import DataTransforObject.MyPropertyDTO;
import DataTransforObject.NearByLocationDTO;
import DataTransforObject.NearbyCategoryDTO;
import DataTransforObject.PropertyFeatures;
import DataTransforObject.PropertyType;
import DataTransforObject.Property_array;
import DataTransforObject.SearchResult;
import DataTransforObject.SubDivisionType;
import DataTransforObject.UserInfo;
import Utility.AppSession;
import Utility.CommanString;

public class HttpRequest
{
    String serviceUrl = "";
    String TAG=getClass().getSimpleName();
    private String json = null;
    Context mContext;


    String BasrUrl = "http://bahamasrenter.com";

   public static String BasrUrlV2 = "http://v2.bahamasrenter.com/api/";

  //  String BasrUrl_test = "http://techlect.in/bahamasdev";
   // String BasrUrl = "http://bahamas.techlect.in";
  //String BasrUrl =  "http://theloyalty.co/bahamas";

    public HttpRequest(Context context)
    {
        super();
        mContext=context;
    }


    public String makeConnection(String serviceUrl, MultipartEntity entity)
    {
        String response="",urlStr="";
        String completeURL ="";
        try
        {
            urlStr = serviceUrl;

           HttpPost httpPost = new HttpPost(urlStr);
            httpPost.setEntity(entity);

            //HttpGet httpPost = new HttpGet(urlStr);
          //  httpPost.setEntity(entity);
           // httpPost.set
            HttpParams httpParameters = new BasicHttpParams();

            int timeoutConnection = 5000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);



            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

            Log.i(TAG, "makeConnection completeURL: " + serviceUrl + "&" + completeURL);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity, HTTP.UTF_8);
            entity = null;
        } catch (UnsupportedEncodingException e) {
            response = "Can't connect to server.";
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            response = "Can't connect to server.";
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            response = "Can't connect to server.";
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e) {
            response = "Can't connect to server.";
            e.printStackTrace();
            return null;
        } catch (Exception e)
        {
            response = "Can't connect to server.";
            e.printStackTrace();
            return null;
        }
        Log.i(TAG, "makeConnection response :: " + response);
        return response;
    }


    public String Addproprty(AddPropertyDTO advertisDTO)
    {
        try {
            serviceUrl = BasrUrlV2 + "property/manageProperties";
            //http://v2.bahamasrenter.com/api/property/manageProperties
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            String jsonData = new Gson().toJson(advertisDTO);
            reqEntity.addPart("data", new StringBody(jsonData));
            json = makeConnection(serviceUrl, reqEntity);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return submitproperty(json);
    }

    public List<PropertyType> GetIsland(String RegId)
    {
        serviceUrl = BasrUrlV2+ "Other/getIsland";


        try
        {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            reqEntity.addPart("reg_id", new StringBody(RegId));
            reqEntity.addPart("user_id", new StringBody(AppSession.getInstance().ReadData(mContext,CommanString.USER_ID,"")));
            json  = makeConnection(serviceUrl, reqEntity);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ParsePropertyInfo(json);
    }

    public List<SubDivisionType> GetSubdivision(int id,String subdiv)
    {
       //  serviceUrl = BasrUrl+"/api/getSubdivisionOrArea/"+id+"/"+subdiv;
        serviceUrl = BasrUrlV2+"property/getSubdivisionOrArea"+id+"/"+subdiv;

        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        json  = makeConnection(serviceUrl, reqEntity);

            return Parsesubdiv(json);

    }


    public List<UserInfo> LoginUser( String email, String pass )
    {
        serviceUrl =   BasrUrlV2+"user/userLogin?email="+email+"&pass="+pass;

        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        json  = makeConnection(serviceUrl, reqEntity);

        return ParseRegistration(json);

    }


    private List <AdvertisDTO>  Advtparse(String jsonResponse)
    {


          String  ad_id ="", ad_url  ="", post_ad_ur  ="";
        AdvertisDTO advt = null;
        String message ="";

         List<AdvertisDTO> Adinfolist = new ArrayList<AdvertisDTO>();
        // List<LocationType> location_info = new ArrayList<LocationType>();

        try
        {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject)
            {
                JSONObject jsonObject = new JSONObject(jsonResponse);


                    JSONArray json_array = null;

                        if(jsonObject.has("data"))
                            json_array = jsonObject.getJSONArray("data");

                    for(int i=0;i<json_array.length();i++)
                    {
                        JSONObject before_geometry_jsonObj = json_array
                                .getJSONObject(i);


                        if (before_geometry_jsonObj.has("add_id"))
                            ad_id = before_geometry_jsonObj.getString("add_id");
                        if (before_geometry_jsonObj.has("img_src"))
                            ad_url = before_geometry_jsonObj.getString("img_src");
                        if (before_geometry_jsonObj.has("rediret_link"))
                            post_ad_ur = before_geometry_jsonObj.getString("rediret_link");


                        //advt = new AdvertisDTO(ad_id,ad_url,post_ad_ur);
                        Adinfolist.add(new AdvertisDTO(ad_id, ad_url, post_ad_ur));
                    }

            }
            else
            {
                return Adinfolist;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Adinfolist;
    }



    private String parsepaymentstatus (String jsonResponse)
    {


        //  String  status ="", Property_id  ="", Property_name  ="", forterm  ="";
        String version_no ="",version_link="",expiry_date,profile_status,payment_status;
        int i;
        // List<String> post_info = new ArrayList<String>();
        // List<LocationType> location_info = new ArrayList<LocationType>();
        AppSession save_pref = AppSession.getInstance();

        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject)
            {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.has("data"))
                {
                   String data  = jsonObject.getString("data");

                    JSONObject dataobject = new JSONObject(data);

                    if(dataobject.has("expiry_date")) {
                        expiry_date = dataobject.getString("expiry_date");
                        if(expiry_date!= null && !expiry_date.equalsIgnoreCase(""))
                        save_pref.SaveData(mContext,CommanString.EXPIRY_DATE,expiry_date);
                    }

                    if(dataobject.has("profile_status"))
                    {
                        profile_status = dataobject.getString("profile_status");
                        if(profile_status!= null && !profile_status.equalsIgnoreCase(""))
                        save_pref.SaveData(mContext,CommanString.PROFILE_STATUS,profile_status);
                    }

                    if(dataobject.has("payment_status")) {
                        payment_status = dataobject
                                .getString("payment_status");
                        if(payment_status!= null && !payment_status.equalsIgnoreCase(""))
                            save_pref.SaveData(mContext,CommanString.PAYMENT_STATUS,payment_status);
                    }

                }




            }
            else
            {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version_no;
    }







    private List<MessageObject> ParseMessage(String jsonResponse)
    {


        //  String  status ="", Property_id  ="", Property_name  ="", forterm  ="";
        String status ="";
        String request_id ="", first_name  ="", last_name  ="", monthly_salary  ="",job_occupation = ""
                ,number_of_occupants="",phone_contact="",email_from="",email_to = "",
                property_url = "",employment="",user_id="",subject="",message="",recd="",created_date="",msg_read = "";
        int i;
        List<MessageObject> featured_info = new ArrayList<MessageObject>();


        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status.equals("1") && jsonObject.has("data"))
                {


                    JSONArray json_array=null;
                    if(jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    if(json_array!=null) {
                        for (i = 0; i < json_array.length(); i++)
                        {



                            JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                            if(jsonObject1.has("r_id"))
                            request_id = jsonObject1.getString("r_id");
                            if(jsonObject1.has("first_name"))
                            first_name = jsonObject1.getString("first_name");
                            if(jsonObject1.has("last_name"))
                            last_name = jsonObject1.getString("last_name");
                            if(jsonObject1.has("monthly_salary"))
                            monthly_salary = jsonObject1.getString("monthly_salary");
                            if(jsonObject1.has("job_occupation"))
                            job_occupation = jsonObject1.getString("job_occupation");
                            if(jsonObject1.has("number_of_occupants"))
                            number_of_occupants = jsonObject1.getString("number_of_occupants");
                            if(jsonObject1.has("phone_contact"))
                            phone_contact = jsonObject1.getString("phone_contact");
                            if(jsonObject1.has("email_from"))
                            email_from = jsonObject1.getString("email_from");
                            if(jsonObject1.has("email_to"))
                            email_to = jsonObject1.getString("email_to");
                            if(jsonObject1.has("property_url"))
                            property_url = jsonObject1.getString("property_url");
                            if(jsonObject1.has("employment"))
                            employment = jsonObject1.getString("employment");
                            if(jsonObject1.has("user_id"))
                            user_id = jsonObject1.getString("user_id");
                            if(jsonObject1.has("subject"))
                            subject = jsonObject1.getString("subject");
                            if(jsonObject1.has("message"))
                            message = jsonObject1.getString("message");
                            if(jsonObject1.has("recd"))
                            recd = jsonObject1.getString("recd");
                            if(jsonObject1.has("created_date"))
                            created_date = jsonObject1.getString("created_date");
                            if(jsonObject1.has("msg_read"))
                                msg_read = jsonObject1.getString("msg_read");



                            featured_info.add(new MessageObject(request_id , first_name  , last_name , monthly_salary,job_occupation
                                    ,number_of_occupants,phone_contact,email_from,email_to ,
                                    property_url ,employment,user_id,subject,message,recd,created_date,msg_read));


                        }

                    }


                }
                else
                {
                    featured_info = null;



                }

            }
            else
            {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return featured_info;
    }











    private List<SearchResult> Parsefavorite(String jsonResponse)
    {


        //  String  status ="", Property_id  ="", Property_name  ="", forterm  ="";
        String [] bookingDates = null;
        String status ="";
        String bahamas_name = "",typeName = "",square_footage = "",property_rent= "" ,bathrooms= "" ,
                bedrooms = "",property_id = "",property_name = "",property_description = ""
                ,property_price = "",property_address = "",property_sqft = "",latitude = "",longitude = ""
                ,property_category_name = "",agent_id = "",agent_name = "",agent_company_address = "",agent_company_phone_number = ""
                ,agent_company_cell_number = "",image_name = "",property_views_count  = "" ,property_favorites_count = "",
                property_key = "",nearby_name = "",nearby_logo = "",bahamas_subdivision = "",favproptt = "";
        int i;
        List<SearchResult> featured_info = new ArrayList<SearchResult>();
      //  List<SearchResult> Normal_info = new ArrayList<SearchResult>();
        // List<LocationType> location_info = new ArrayList<LocationType>();

        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status.equals("1") && jsonObject.has("data"))
                {
                    //   JSONArray json_array = jsonObject.getJSONArray("data");

                    // JSONObject jsonObject2 = new JSONObject("data");
                 //   String   bahamas_name1 = jsonObject.getString("data");

                    //JSONArray json_array = bahamas_name1.getJSONArray("featured_property");
                  //  JSONObject jsonObject4 = new JSONObject(bahamas_name1);

                    JSONArray json_array=null;
                    if(jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    if(json_array!=null) {
                        for (i = 0; i < json_array.length(); i++) {

                            JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                            if(jsonObject1.has("bahamas_name"))
                            bahamas_name = jsonObject1.getString("bahamas_name");

                            if(jsonObject1.has("typeName"))
                            typeName = jsonObject1.getString("typeName");

                            if(jsonObject1.has("square_footage"))
                            square_footage = jsonObject1.getString("square_footage");

                            if(jsonObject1.has("property_rent"))
                            property_rent = jsonObject1.getString("property_rent");

                            if(jsonObject1.has("bathrooms"))
                            bathrooms = jsonObject1.getString("bathrooms");

                            if(jsonObject1.has("bedrooms"))
                            bedrooms = jsonObject1.getString("bedrooms");

                            if(jsonObject1.has("property_id"))
                            property_id = jsonObject1.getString("property_id");


                            if(jsonObject1.has("property_name"))
                            property_name = jsonObject1.getString("property_name");


                            if(jsonObject1.has("property_description"))
                            property_description = jsonObject1.getString("property_description");

                            if(jsonObject1.has("property_price"))
                            property_price = jsonObject1.getString("property_price");

                            if(jsonObject1.has("property_address"))
                            property_address = jsonObject1.getString("property_address");

                            if(jsonObject1.has("property_sqft"))
                            property_sqft = jsonObject1.getString("property_sqft");

                            if(jsonObject1.has("latitude"))
                            latitude = jsonObject1.getString("latitude");

                            if(jsonObject1.has("longitude"))
                            longitude = jsonObject1.getString("longitude");

                            if(jsonObject1.has("property_category_name"))
                            property_category_name = jsonObject1.getString("property_category_name");


                            if(jsonObject1.has("agent_id"))
                            agent_id = jsonObject1.getString("agent_id");


                            if(jsonObject1.has("agent_name"))
                            agent_name = jsonObject1.getString("agent_name");


                            if(jsonObject1.has("agent_company_address"))
                            agent_company_address = jsonObject1.getString("agent_company_address");

                            if(jsonObject1.has("agent_company_phone_number"))
                            agent_company_phone_number = jsonObject1.getString("agent_company_phone_number");

                            if(jsonObject1.has("agent_company_cell_number"))
                            agent_company_cell_number = jsonObject1.getString("agent_company_cell_number");


                            if(jsonObject1.has("image_name"))
                            image_name = jsonObject1.getString("image_name");


                            if(jsonObject1.has("property_views_count"))
                            property_views_count = jsonObject1.getString("property_views_count");

                            if(jsonObject1.has("property_favorites_count"))
                            property_favorites_count = jsonObject1.getString("property_favorites_count");

                            if (jsonObject1.has("bahamas_subdivision"))
                                bahamas_subdivision = jsonObject1.getString("bahamas_subdivision");

                            if (jsonObject1.has("property_key"))
                                property_key = jsonObject1.getString("property_key");


                            if (jsonObject1.has("nearby_name"))
                                nearby_name = jsonObject1.getString("nearby_name");
                            if (jsonObject1.has("nearby_logo"))
                                nearby_logo = jsonObject1.getString("nearby_logo");


                            if (jsonObject1.has("bookingDates"))
                            {
                                String bookingDate = jsonObject1.getString("bookingDates");
                                if(bookingDate!= null && !bookingDate.equalsIgnoreCase(""))
                                    bookingDates = bookingDate.split(",");
                            }

                            if (jsonObject1.has("favoritPropt"))
                                favproptt = jsonObject1.getString("favoritPropt");

                            featured_info.add(new SearchResult(bahamas_name, typeName, square_footage, property_rent, bathrooms,
                                    bedrooms, property_id, property_name, property_description, property_price, property_address, property_sqft, latitude, longitude, property_category_name, agent_id, agent_name, agent_company_address, agent_company_phone_number, agent_company_cell_number, image_name, property_views_count, property_favorites_count, property_key, nearby_name, nearby_logo,bahamas_subdivision,favproptt,"","",null,null,null,null,null,bookingDates));
                        }

                    }


                }
                else
                {
                    featured_info = null;

                    CommanString.favorite_list = featured_info;

                }

            }
            else
            {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return featured_info;
    }




    private List<SearchResult> ParseSearchResult(String jsonResponse)
    {


        //  String  status ="", Property_id  ="", Property_name  ="", forterm  ="";

        String[] property_feature = null,utilities = null,appliances  = null
                ,laundries = null,parkingSecurity = null,bookingDates = null;


        String status ="";
        String bahamas_name = "",periscope_time = "",typeName = "",square_footage = "",property_rent= "" ,bathrooms= "" ,
                bedrooms = "",property_id = "",property_name = "",property_description = ""
                ,property_price = "",property_address = "",property_sqft = "",latitude = "",longitude = ""
                ,property_category_name = "",agent_id = "",agent_name = "",agent_company_address = "",agent_company_phone_number = ""
                ,agent_company_cell_number = "",image_name = "",property_views_count  = "" ,property_favorites_count = "",
                property_key = "",nearby_name = "",nearby_logo = "",bahamas_subdivision = "",favproptt = "",createddate = "";
        int i;
        List<SearchResult> featured_info = new ArrayList<SearchResult>();
        List<SearchResult> Normal_info = new ArrayList<SearchResult>();

        List<SearchResult> newest_property = new ArrayList<SearchResult>();

        // List<LocationType> location_info = new ArrayList<LocationType>();

        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status.equals("1") && jsonObject.has("data"))
                {

                  String   bahamas_name1 = jsonObject.getString("data");
                    JSONObject jsonObject4 = new JSONObject(bahamas_name1);
                    JSONArray json_array=null;

                    if(jsonObject4.has("featured_property"))
                     json_array = jsonObject4.getJSONArray("featured_property");

                    if(json_array!=null) {
                        for (i = 0; i < json_array.length(); i++) {

                            JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                            if(jsonObject1.has("bahamas_name"))
                            bahamas_name = jsonObject1.getString("bahamas_name");

                            if(jsonObject1.has("typeName"))
                            typeName = jsonObject1.getString("typeName");

                            if(jsonObject1.has("square_footage"))
                            square_footage = jsonObject1.getString("square_footage");

                            if(jsonObject1.has("property_rent"))
                            property_rent = jsonObject1.getString("property_rent");

                            if(jsonObject1.has("bathrooms"))
                            bathrooms = jsonObject1.getString("bathrooms");

                            if(jsonObject1.has("bedrooms"))
                            bedrooms = jsonObject1.getString("bedrooms");

                            if(jsonObject1.has("property_id"))
                            property_id = jsonObject1.getString("property_id");

                            if(jsonObject1.has("property_name"))
                            property_name = jsonObject1.getString("property_name");

                            if(jsonObject1.has("property_description"))
                            property_description = jsonObject1.getString("property_description");

                            if(jsonObject1.has("property_price"))
                            property_price = jsonObject1.getString("property_price");


                            if(jsonObject1.has("property_address"))
                            property_address = jsonObject1.getString("property_address");

                            if(jsonObject1.has("property_sqft"))
                            property_sqft = jsonObject1.getString("property_sqft");

                            if(jsonObject1.has("latitude"))
                            latitude = jsonObject1.getString("latitude");

                            if(jsonObject1.has("longitude"))
                            longitude = jsonObject1.getString("longitude");

                            if(jsonObject1.has("periscope_time"))
                            periscope_time = jsonObject1.getString("periscope_time");

                            if(jsonObject1.has("property_category_name"))
                                property_category_name = jsonObject1.getString("property_category_name");

                            if(jsonObject1.has("agent_id"))
                            agent_id = jsonObject1.getString("agent_id");

                            if(jsonObject1.has("agent_name"))
                            agent_name = jsonObject1.getString("agent_name");

                            if(jsonObject1.has("agent_company_address"))
                            agent_company_address = jsonObject1.getString("agent_company_address");

                            if(jsonObject1.has("agent_company_phone_number"))
                            agent_company_phone_number = jsonObject1.getString("agent_company_phone_number");

                            if(jsonObject1.has("agent_company_cell_number"))
                            agent_company_cell_number = jsonObject1.getString("agent_company_cell_number");

                            if(jsonObject1.has("leadImg"))
                            image_name = jsonObject1.getString("leadImg");

                            if(jsonObject1.has("property_views_count"))
                            property_views_count = jsonObject1.getString("property_views_count");

                            if(jsonObject1.has("property_favorites_count"))
                            property_favorites_count = jsonObject1.getString("property_favorites_count");



                            if (jsonObject1.has("furnishing_type"))
                            {
                                String features = jsonObject1.getString("furnishing_type");
                                if(features!= null && !features.equalsIgnoreCase(""))
                                    property_feature = features.split(",");
                            }

                            if (jsonObject1.has("appliances"))
                            {
                                String appliancess = jsonObject1.getString("appliances");
                                if(appliancess!= null && !appliancess.equalsIgnoreCase(""))
                                    appliances = appliancess.split(",");
                            }

                            if (jsonObject1.has("utilities"))
                            {
                                String utilitiess = jsonObject1.getString("utilities");
                                if(utilitiess!= null && !utilitiess.equalsIgnoreCase(""))
                                    utilities = utilitiess.split(",");
                            }

                            if (jsonObject1.has("parkingSecurity"))
                            {
                                String parkingSecuritys = jsonObject1.getString("parkingSecurity");
                                if(parkingSecuritys!= null && !parkingSecuritys.equalsIgnoreCase(""))
                                    parkingSecurity = parkingSecuritys.split(",");
                            }

                            if (jsonObject1.has("laundries"))
                            {
                                String laundriess = jsonObject1.getString("laundries");
                                if(laundriess!= null && !laundriess.equalsIgnoreCase(""))
                                    laundries = laundriess.split(",");
                            }

                            if (jsonObject1.has("bookingDates"))
                            {
                                String bookingDate = jsonObject1.getString("bookingDates");
                                if(bookingDate!= null && !bookingDate.equalsIgnoreCase(""))
                                    bookingDates = bookingDate.split(",");
                            }


                            if (jsonObject1.has("createdDate"))
                                createddate = jsonObject1.getString("createdDate");

                            if (jsonObject1.has("bahamas_subdivision"))
                                bahamas_subdivision = jsonObject1.getString("bahamas_subdivision");

                            if (jsonObject1.has("property_key"))
                                property_key = jsonObject1.getString("property_key");




                            if (jsonObject1.has("nearby_name"))
                                nearby_name = jsonObject1.getString("nearby_name");
                            if (jsonObject1.has("nearby_logo"))
                                nearby_logo = jsonObject1.getString("nearby_logo");

                            if (jsonObject1.has("favoritPropt"))
                                favproptt = jsonObject1.getString("favoritPropt");



                            featured_info.add(new SearchResult(bahamas_name, typeName, square_footage, property_rent, bathrooms,
                                    bedrooms, property_id, property_name, property_description, property_price, property_address, property_sqft, latitude, longitude, property_category_name, agent_id, agent_name, agent_company_address, agent_company_phone_number, agent_company_cell_number, image_name, property_views_count, property_favorites_count, property_key, nearby_name, nearby_logo,bahamas_subdivision,favproptt,periscope_time,createddate,property_feature,appliances,utilities,laundries,parkingSecurity,bookingDates));
                        }
                    }


                    json_array = null;
                    if(jsonObject4.has("newest_property"))
                        json_array = jsonObject4.getJSONArray("newest_property");

                    if(json_array!=null)
                    {
                        for (i = 0; i < json_array.length(); i++) {

                            JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                            if(jsonObject1.has("bahamas_name"))
                                bahamas_name = jsonObject1.getString("bahamas_name");

                            if(jsonObject1.has("typeName"))
                                typeName = jsonObject1.getString("typeName");

                            if(jsonObject1.has("square_footage"))
                                square_footage = jsonObject1.getString("square_footage");

                            if(jsonObject1.has("property_rent"))
                                property_rent = jsonObject1.getString("property_rent");

                            if(jsonObject1.has("bathrooms"))
                                bathrooms = jsonObject1.getString("bathrooms");

                            if(jsonObject1.has("bedrooms"))
                                bedrooms = jsonObject1.getString("bedrooms");

                            if(jsonObject1.has("property_id"))
                                property_id = jsonObject1.getString("property_id");

                            if(jsonObject1.has("property_name"))
                                property_name = jsonObject1.getString("property_name");

                            if(jsonObject1.has("property_description"))
                                property_description = jsonObject1.getString("property_description");

                            if(jsonObject1.has("property_price"))
                                property_price = jsonObject1.getString("property_price");


                            if(jsonObject1.has("property_address"))
                                property_address = jsonObject1.getString("property_address");

                            if(jsonObject1.has("property_sqft"))
                                property_sqft = jsonObject1.getString("property_sqft");

                            if(jsonObject1.has("latitude"))
                                latitude = jsonObject1.getString("latitude");

                            if(jsonObject1.has("longitude"))
                                longitude = jsonObject1.getString("longitude");

                            if(jsonObject1.has("periscope_time"))
                                periscope_time = jsonObject1.getString("periscope_time");

                            if(jsonObject1.has("property_category_name"))
                                property_category_name = jsonObject1.getString("property_category_name");

                            if(jsonObject1.has("agent_id"))
                                agent_id = jsonObject1.getString("agent_id");

                            if(jsonObject1.has("agent_name"))
                                agent_name = jsonObject1.getString("agent_name");

                            if(jsonObject1.has("agent_company_address"))
                                agent_company_address = jsonObject1.getString("agent_company_address");

                            if(jsonObject1.has("agent_company_phone_number"))
                                agent_company_phone_number = jsonObject1.getString("agent_company_phone_number");

                            if(jsonObject1.has("agent_company_cell_number"))
                                agent_company_cell_number = jsonObject1.getString("agent_company_cell_number");

                            if(jsonObject1.has("leadImg"))
                                image_name = jsonObject1.getString("leadImg");

                            if(jsonObject1.has("property_views_count"))
                                property_views_count = jsonObject1.getString("property_views_count");

                            if(jsonObject1.has("property_favorites_count"))
                                property_favorites_count = jsonObject1.getString("property_favorites_count");



                            if (jsonObject1.has("furnishing_type"))
                            {
                                String features = jsonObject1.getString("furnishing_type");
                                if(features!= null && !features.equalsIgnoreCase(""))
                                    property_feature = features.split(",");
                            }

                            if (jsonObject1.has("appliances"))
                            {
                                String appliancess = jsonObject1.getString("appliances");
                                if(appliancess!= null && !appliancess.equalsIgnoreCase(""))
                                    appliances = appliancess.split(",");
                            }

                            if (jsonObject1.has("utilities"))
                            {
                                String utilitiess = jsonObject1.getString("utilities");
                                if(utilitiess!= null && !utilitiess.equalsIgnoreCase(""))
                                    utilities = utilitiess.split(",");
                            }

                            if (jsonObject1.has("parkingSecurity"))
                            {
                                String parkingSecuritys = jsonObject1.getString("parkingSecurity");
                                if(parkingSecuritys!= null && !parkingSecuritys.equalsIgnoreCase(""))
                                    parkingSecurity = parkingSecuritys.split(",");
                            }

                            if (jsonObject1.has("laundries"))
                            {
                                String laundriess = jsonObject1.getString("laundries");
                                if(laundriess!= null && !laundriess.equalsIgnoreCase(""))
                                    laundries = laundriess.split(",");
                            }

                            if (jsonObject1.has("bookingDates"))
                            {
                                String bookingDate = jsonObject1.getString("bookingDates");
                                if(bookingDate!= null && !bookingDate.equalsIgnoreCase(""))
                                    bookingDates = bookingDate.split(",");
                            }


                            if (jsonObject1.has("createdDate"))
                                createddate = jsonObject1.getString("createdDate");

                            if (jsonObject1.has("bahamas_subdivision"))
                                bahamas_subdivision = jsonObject1.getString("bahamas_subdivision");

                            if (jsonObject1.has("property_key"))
                                property_key = jsonObject1.getString("property_key");




                            if (jsonObject1.has("nearby_name"))
                                nearby_name = jsonObject1.getString("nearby_name");
                            if (jsonObject1.has("nearby_logo"))
                                nearby_logo = jsonObject1.getString("nearby_logo");

                            if (jsonObject1.has("favoritPropt"))
                                favproptt = jsonObject1.getString("favoritPropt");



                            newest_property.add(new SearchResult(bahamas_name, typeName, square_footage, property_rent, bathrooms,
                                    bedrooms, property_id, property_name, property_description, property_price, property_address, property_sqft, latitude, longitude, property_category_name, agent_id, agent_name, agent_company_address, agent_company_phone_number, agent_company_cell_number, image_name, property_views_count, property_favorites_count, property_key, nearby_name, nearby_logo,bahamas_subdivision,favproptt,periscope_time,createddate,property_feature,appliances,utilities,laundries,parkingSecurity,bookingDates));
                        }
                    }




                    JSONArray json_array3=null;
                    if(jsonObject4.has("normal_property"))
                        json_array3 = jsonObject4.getJSONArray("normal_property");

        if(json_array3!=null) {
    for (i = 0; i < json_array3.length(); i++) {

        JSONObject jsonObject1 = new JSONObject(json_array3.getString(i));

        if (jsonObject1.has("bahamas_name"))
        bahamas_name = jsonObject1.getString("bahamas_name");

        if(jsonObject1.has("typeName"))
            typeName = jsonObject1.getString("typeName");

        if(jsonObject1.has("square_footage"))
            square_footage = jsonObject1.getString("square_footage");

        if(jsonObject1.has("property_rent"))
            property_rent = jsonObject1.getString("property_rent");

        if(jsonObject1.has("bathrooms"))
            bathrooms = jsonObject1.getString("bathrooms");

        if(jsonObject1.has("bedrooms"))
            bedrooms = jsonObject1.getString("bedrooms");

        if(jsonObject1.has("property_id"))
            property_id = jsonObject1.getString("property_id");

        if(jsonObject1.has("property_name"))
            property_name = jsonObject1.getString("property_name");

        if(jsonObject1.has("property_description"))
            property_description = jsonObject1.getString("property_description");

        if(jsonObject1.has("property_price"))
            property_price = jsonObject1.getString("property_price");


        if(jsonObject1.has("property_address"))
            property_address = jsonObject1.getString("property_address");

        if(jsonObject1.has("property_sqft"))
            property_sqft = jsonObject1.getString("property_sqft");

        if(jsonObject1.has("latitude"))
            latitude = jsonObject1.getString("latitude");

        if(jsonObject1.has("longitude"))
            longitude = jsonObject1.getString("longitude");

        if(jsonObject1.has("periscope_time"))
            periscope_time = jsonObject1.getString("periscope_time");

        if(jsonObject1.has("property_category_name"))
            property_category_name = jsonObject1.getString("property_category_name");

        if(jsonObject1.has("agent_id"))
            agent_id = jsonObject1.getString("agent_id");

        if(jsonObject1.has("agent_name"))
            agent_name = jsonObject1.getString("agent_name");

        if(jsonObject1.has("agent_company_address"))
            agent_company_address = jsonObject1.getString("agent_company_address");

        if(jsonObject1.has("agent_company_phone_number"))
            agent_company_phone_number = jsonObject1.getString("agent_company_phone_number");

        if(jsonObject1.has("agent_company_cell_number"))
            agent_company_cell_number = jsonObject1.getString("agent_company_cell_number");

        if(jsonObject1.has("leadImg"))
            image_name = jsonObject1.getString("leadImg");

        if(jsonObject1.has("property_views_count"))
            property_views_count = jsonObject1.getString("property_views_count");

        if(jsonObject1.has("property_favorites_count"))
            property_favorites_count = jsonObject1.getString("property_favorites_count");


        if (jsonObject1.has("furnishing_type"))
        {
            String features = jsonObject1.getString("furnishing_type");
            if(features!= null && !features.equalsIgnoreCase(""))
                property_feature = features.split(",");
        }

        if (jsonObject1.has("appliances"))
        {
            String appliancess = jsonObject1.getString("appliances");
            if(appliancess!= null && !appliancess.equalsIgnoreCase(""))
                appliances = appliancess.split(",");
        }

        if (jsonObject1.has("utilities"))
        {
            String utilitiess = jsonObject1.getString("utilities");
            if(utilitiess!= null && !utilitiess.equalsIgnoreCase(""))
                utilities = utilitiess.split(",");
        }

        if (jsonObject1.has("parkingSecurity"))
        {
            String parkingSecuritys = jsonObject1.getString("parkingSecurity");
            if(parkingSecuritys!= null && !parkingSecuritys.equalsIgnoreCase(""))
                parkingSecurity = parkingSecuritys.split(",");
        }


        if (jsonObject1.has("bookingDates"))
        {
            String bookingDate = jsonObject1.getString("bookingDates");
            if(bookingDate!= null && !bookingDate.equalsIgnoreCase(""))
                bookingDates = bookingDate.split(",");
        }


        if (jsonObject1.has("laundries"))
        {
            String laundriess = jsonObject1.getString("laundries");
            if(laundriess!= null && !laundriess.equalsIgnoreCase(""))
                laundries = laundriess.split(",");
        }


        if (jsonObject1.has("createdDate"))
            createddate = jsonObject1.getString("createdDate");

        if (jsonObject1.has("property_key"))
            property_key = jsonObject1.getString("property_key");

        if (jsonObject1.has("bahamas_subdivision"))
            bahamas_subdivision = jsonObject1.getString("bahamas_subdivision");



        if (jsonObject1.has("nearby_name"))
            nearby_name = jsonObject1.getString("nearby_name");
        if (jsonObject1.has("nearby_logo"))
            nearby_logo = jsonObject1.getString("nearby_logo");


        if (jsonObject1.has("favoritPropt"))
            favproptt = jsonObject1.getString("favoritPropt");

        Normal_info.add(new SearchResult(bahamas_name, typeName, square_footage, property_rent, bathrooms,
                bedrooms, property_id, property_name, property_description, property_price, property_address, property_sqft, latitude, longitude, property_category_name, agent_id, agent_name, agent_company_address, agent_company_phone_number, agent_company_cell_number, image_name, property_views_count, property_favorites_count, property_key, nearby_name, nearby_logo,bahamas_subdivision,favproptt,"",createddate,property_feature,appliances,utilities,laundries,parkingSecurity,bookingDates));
    }
}

                    if(CommanString.FeaturedContent_list!= null)
                        CommanString.FeaturedContent_list = null;

                    if(CommanString.NormalContent_list!= null)
                        CommanString.NormalContent_list = null;

                    if(CommanString.NewestContent_list!= null)
                        CommanString.NewestContent_list = null;



                    /*CommanString.FeaturedContent_list = new ArrayList<SearchResult>();
                    CommanString.NormalContent_list = new ArrayList<SearchResult>();*/

                    CommanString.FeaturedContent_list = featured_info;
                    CommanString.NormalContent_list = Normal_info;
                    CommanString.NewestContent_list =newest_property;



                }
                else
                {
                    if(CommanString.FeaturedContent_list!= null)
                        CommanString.FeaturedContent_list = null;

                    if(CommanString.NormalContent_list!= null)
                        CommanString.NormalContent_list = null;

                    featured_info = null;
                    Normal_info = null;
                    CommanString.FeaturedContent_list = featured_info;
                    CommanString.NormalContent_list = Normal_info;
                }

            }
            else
            {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return featured_info;
    }


    private List <PropertyFeatures>  ParseCompare(String jsonResponse)
    {


        //  String  status ="", Property_id  ="", Property_name  ="", forterm  ="";
        String status ="", feature  ="", utilities  ="", appliances  ="",laundries ="",parkingSecurity="",owner_Id = "",owner_email = "";
        int i;
        List <PropertyFeatures> post_info = new ArrayList<PropertyFeatures> ();
        Discription discription = null;

        // List<LocationType> location_info = new ArrayList<LocationType>();

        try {

            //  jsonResponse.repreplace("\", "");
            //jsonResponse = jsonResponse.replaceAll("/", "#");

            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject)
            {


                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");




                if (status.equals("1") && jsonObject.has("properties"))
                {


                    JSONArray propertyarray = jsonObject.getJSONArray("properties");






                  //  JSONArray propertyarray = new JSONArray("properties");

                        for(int j = 0; j<propertyarray.length();j++ )
                        {


                            JSONObject jsoninnerObject = new JSONObject(propertyarray.getString(j));


                            String address = "", property_type = "", island = "", subdivision = "", bedroom = "", bathroom = "", property_sq_footage = "", property_rent_fees = "", property_move_in_req = "", property_description = "";
                            String bahamas_name1 = "";
                            if( jsoninnerObject.has("features"))
                             bahamas_name1 = jsoninnerObject.getString("features");

                            JSONObject featuresarray = new JSONObject(bahamas_name1);

                            if( featuresarray.has("features"))
                            feature = featuresarray.getString("features");

                            if( jsoninnerObject.has("utilities"))
                            bahamas_name1 = jsoninnerObject.getString("utilities");

                            JSONObject utilitiesarray = new JSONObject(bahamas_name1);

                            utilities = utilitiesarray.getString("utilities");


                            bahamas_name1 = jsoninnerObject.getString("appliances");
                            JSONObject appliancesarray = new JSONObject(bahamas_name1);
                            appliances = appliancesarray.getString("appliances");

                            bahamas_name1 = jsoninnerObject.getString("laundries");
                            JSONObject laundriesarray = new JSONObject(bahamas_name1);
                            laundries = laundriesarray.getString("laundries");

                            bahamas_name1 = jsoninnerObject.getString("parkingSecurity");
                            JSONObject parkingSecurityarray = new JSONObject(bahamas_name1);
                            parkingSecurity = parkingSecurityarray.getString("parkingSecurity");

                            bahamas_name1 = jsoninnerObject.getString("user_detail");

                            JSONObject user_detailyarray = new JSONObject(bahamas_name1);
                            bahamas_name1 = user_detailyarray.getString("user_detail");

                            JSONObject user_detailyarray1 = new JSONObject(bahamas_name1);

                            owner_Id = user_detailyarray1.getString("owner_id");
                            owner_email = user_detailyarray1.getString("owner_email");

                            bahamas_name1 = jsoninnerObject.getString("description");

                            JSONObject jsonObject1 = new JSONObject(bahamas_name1);


                            address = jsonObject1.getString("address");
                            property_type = jsonObject1.getString("property_type");
                            island = jsonObject1.getString("island");

                            subdivision = jsonObject1.getString("subdivision");
                            bedroom = jsonObject1.getString("bedroom");
                            bathroom = jsonObject1.getString("bathroom");
                            property_sq_footage = jsonObject1.getString("property_sq_footage");
                            property_rent_fees = jsonObject1.getString("property_rent_fees");
                            property_move_in_req = jsonObject1.getString("property_move_in_req");
                            property_description = jsonObject1.getString("property_description");

                            discription = new Discription(address, property_type, island, subdivision, bedroom, bathroom
                                    , property_sq_footage, property_rent_fees, property_move_in_req, property_description);

                            // }


                            post_info.add(new PropertyFeatures(discription, feature, utilities, appliances, laundries, parkingSecurity, owner_Id, owner_email));
                        }
                }
                else
                    post_info = null;

            }
            else
            {
                return null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return post_info;
    }


    private List<MyPropertyDTO> Parsemyproperty(String jsonResponse)
    {


        String[] furnishing_type = null,utilities = null,appliances  = null,  bookingDates = null
                ,laundries = null,parkingSecurity = null,movein_requirements = null,property_image = null,property_image_title = null,
                property_lead_image;

        String  image_name = "", image_name1 = "";

        String property_type = "" , bedrooms = "", bathrooms = "",property_description = ""
                ,property_island = "",property_subdivision = "",property_area = "",address = "",
                square_footage = "",security_deposit = "",payment_cycle = "",
                property_rent = "",property_term = "",contact_info = "",property_make_offline = "", user_id = "",property_id = "",lat = "",lang = "";
        String prostatus = "",property_number = "";



        String island_id = "",subdivision_id = "",typename_id = "";

        int i;
        List<MyPropertyDTO> myPropertyDTOs = new ArrayList<MyPropertyDTO>();
        // List<LocationType> location_info = new ArrayList<LocationType>();
        String furnishing_typestring = "",utilitiesstring = "",appliancesstring = "",laundriesstring = "",parkingSecuritystring = "";

String movein_requirementsstring = "";

        //List<Property_array> property_arrayLists = null;


String  periscope_time = "";


        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject)
            {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.has("status"))
                    prostatus = jsonObject.getString("status");

                if (prostatus.equals("1") && jsonObject.has("property"))
                {
                    JSONArray json_array = jsonObject.getJSONArray("property");

                    for ( i = 0; i< json_array.length(); i++)
                    {

                        List<Property_array> property_array  = new ArrayList<Property_array>();

                        JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                        if(jsonObject1.has("bahamas_subdivision"))
                            property_subdivision = jsonObject1.getString("bahamas_subdivision");
                        if(jsonObject1.has("utilities")) {
                            utilitiesstring = jsonObject1.getString("utilities");
                            utilities = utilitiesstring.split(",");
                        }
                        if(jsonObject1.has("furnishing_type"))
                        {
                            furnishing_typestring = jsonObject1.getString("furnishing_type");
                            furnishing_type = furnishing_typestring.split(",");
                        }
                       /* if(jsonObject1.has("featured"))
                            furnishing_typestring = jsonObject1.getString("featured");
                        furnishing_type = furnishing_typestring.split(",");*/


                        if(jsonObject1.has("appliances")) {
                            appliancesstring = jsonObject1.getString("appliances");
                            appliances = appliancesstring.split(",");
                        }
                        if(jsonObject1.has("status"))
                            prostatus = jsonObject1.getString("status");
                        if(jsonObject1.has("property_no"))
                            property_number = jsonObject1.getString("property_no");
                        if(jsonObject1.has("laundries"))
                        {
                            laundriesstring = jsonObject1.getString("laundries");
                            laundries = laundriesstring.split(",");

                        }
                        if(jsonObject1.has("image_name"))
                        image_name = jsonObject1.getString("image_name");

                        if(jsonObject1.has("parkingSecurity"))
                        {
                            parkingSecuritystring = jsonObject1.getString("parkingSecurity");
                            parkingSecurity = parkingSecuritystring.split(",");
                        }

                        if(jsonObject1.has("bookingDates"))
                        {
                            parkingSecuritystring = jsonObject1.getString("bookingDates");
                            bookingDates = parkingSecuritystring.split(",");
                        }



                        if(jsonObject1.has("amenities")) {
                            movein_requirementsstring = jsonObject1.getString("amenities");
                            movein_requirements = movein_requirementsstring.split(",");
                        }
                        if(jsonObject1.has("bahamas_name"))
                            property_island = jsonObject1.getString("bahamas_name");
                        if(jsonObject1.has("typeName"))
                            property_type = jsonObject1.getString("typeName");
                        if(jsonObject1.has("make_offline"))
                            property_make_offline = jsonObject1.getString("make_offline");
                        if(jsonObject1.has("square_footage"))
                            square_footage = jsonObject1.getString("square_footage");
                        if(jsonObject1.has("property_rent"))
                            property_rent = jsonObject1.getString("property_rent");
                        if(jsonObject1.has("bathrooms"))
                            bathrooms = jsonObject1.getString("bathrooms");
                        if(jsonObject1.has("bedrooms"))
                            bedrooms = jsonObject1.getString("bedrooms");
                        if(jsonObject1.has("property_id"))
                            property_id = jsonObject1.getString("property_id");

                        if(jsonObject1.has("property_description"))
                            property_description = jsonObject1.getString("property_description");

                        if(jsonObject1.has("periscope_time"))
                            periscope_time = jsonObject1.getString("periscope_time");



                        if(jsonObject1.has("property_address"))
                            address = jsonObject1.getString("property_address");
                        if(jsonObject1.has("latitude"))
                            lat = jsonObject1.getString("latitude");
                        if(jsonObject1.has("longitude"))
                            lang = jsonObject1.getString("longitude");


                        if(jsonObject1.has("subdivision_id"))
                            subdivision_id = jsonObject1.getString("subdivision_id");

                        if(jsonObject1.has("island_id"))
                            island_id = jsonObject1.getString("island_id");

                        if(jsonObject1.has("typename_id"))
                            typename_id = jsonObject1.getString("typename_id");

                            if(jsonObject1.has("image_arr"))
                            {
                               // String image_array = jsonObject1.getString("image_arr");

                                JSONArray image_array = jsonObject1.getJSONArray("image_arr");

                                for ( int j = 0; j< image_array.length(); j++)
                                {

                                    JSONObject jsonObject11 = new JSONObject(image_array.getString(j));


                                    String lead_imge = "",Imagetitle = "";

                                    image_name1 = jsonObject11.getString("image_name");
                                    Imagetitle = jsonObject11.getString("image_title");
                                    lead_imge = jsonObject11.getString("lead_image");

                                    property_array.add(new Property_array(image_name1,Imagetitle,lead_imge));

                                }


                            }

                        myPropertyDTOs.add(new MyPropertyDTO("",property_type,bedrooms,bathrooms,property_description,property_island,property_subdivision,""
                        ,address,square_footage,furnishing_type,utilities,appliances,laundries,parkingSecurity,movein_requirements,""
                        ,"",property_rent,"",property_make_offline,property_array,property_id,lat,lang,prostatus,property_number, image_name,island_id,subdivision_id,typename_id,periscope_time,bookingDates));

                    }


                }
                else
                    myPropertyDTOs = null;

            }
            else
            {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myPropertyDTOs;
    }



}



