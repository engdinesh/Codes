
package Communication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.formduniya.MainActivity;
import com.formduniya.R;
import com.formduniya.page_Login;
import com.formduniya.page_RegistrationActivity;
import com.google.gson.Gson;
import java.io.IOException;
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
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import CommonString.AppSession;
import CommonString.CommanString;
import CommonString.CommonUtility;
import DataTransferObject.UserdataDTO;
import DataTransferObject.activitylogDTO;
import DataTransferObject.addressdetailDTO;
import DataTransferObject.admissionDTO;
import DataTransferObject.allDepartmentsDTO;
import DataTransferObject.allJobLocationsDTO;
import DataTransferObject.allQualificationDTO;
import DataTransferObject.appliedjobDTO;
import DataTransferObject.appliedjobstatusDTO;
import DataTransferObject.articleKeywordsDTO;
import DataTransferObject.cityDTO;
import DataTransferObject.communityDTO;
import DataTransferObject.coursesDTO;
import DataTransferObject.dashboardDTO;
import DataTransferObject.documentdataDTO;
import DataTransferObject.educationdetailDTO;
import DataTransferObject.formsummaryDTO;
import DataTransferObject.latestjobDTO;
import DataTransferObject.newsDTO;
import DataTransferObject.personaldetailDTO;
import DataTransferObject.prefereddashDTO;
import DataTransferObject.preferedjobDTO;
import DataTransferObject.profilestatusDTO;
import DataTransferObject.qualificationDTO;
import DataTransferObject.referDTO;
import DataTransferObject.refundActivityDTO;
import DataTransferObject.solvedpaperDTO;
import DataTransferObject.topcoachingDTO;
import DataTransferObject.documentsDTO;
import DataTransferObject.walletDTO;
import DataTransferObject.walletdepositdetailDTO;
import DataTransferObject.wallethistoryDTO;
import DataTransferObject.websettingDTO;
import Database.DatabaseHelper;

public class HttpRequest
{

    DatabaseHelper dbhelper=null;
    String TAG = getClass().getSimpleName();
    private String json = null;
    Context mContext;

    String Model="",Manufacture="",Brand="",Sdk="",MobileVersion="",AppVersion="";

    public HttpRequest(Context context) {
        super();
        mContext = context;
        dbhelper = new DatabaseHelper(mContext);
    }


    public String makeConnection(String serviceUrl, MultipartEntity entity)
    {
        String response = "", urlStr = "";
        String completeURL = "";
        try {
            urlStr = serviceUrl;

            HttpPost httpPost = new HttpPost(urlStr);
            httpPost.setEntity(entity);
            HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection = 5000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);


            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

         //   HttpURLConnection httpClient=new HttpURLConnection(httpParameters);

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


    public String uploadphoto(String uphoto)
    {
        try
        {
            if(uphoto==null || uphoto.equalsIgnoreCase(""))
            {
                uphoto="";
            }

            String jsonData = "";
            String user_id = AppSession.getInstance().ReadData(mContext, CommanString.USERID,"");
            String curl = CommanString.baseurl + "editUploadFiles";
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("user_id", new StringBody(user_id));
            reqEntity.addPart("photo", new StringBody(uphoto));

            json = makeConnection(curl, reqEntity);


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Parsedata(json);
    }


    public String uploadesignature(String uphoto)
    {
        try
        {
            if(uphoto==null || uphoto.equalsIgnoreCase(""))
            {
                uphoto="";
            }

            String jsonData = "";
            String user_id = AppSession.getInstance().ReadData(mContext, CommanString.USERID,"");
            String curl = CommanString.baseurl + "editUploadFiles";
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("user_id", new StringBody(user_id));
            reqEntity.addPart("english_signature", new StringBody(uphoto));

            json = makeConnection(curl, reqEntity);


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Parsedata(json);
    }

    public String uploadhsignature(String uphoto)
    {
        try
        {
            if(uphoto==null || uphoto.equalsIgnoreCase(""))
            {
                uphoto="";
            }

            String jsonData = "";
            String user_id = AppSession.getInstance().ReadData(mContext, CommanString.USERID,"");
            String curl = CommanString.baseurl + "editUploadFiles";
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("user_id", new StringBody(user_id));
            reqEntity.addPart("left_thumb_print", new StringBody(uphoto));

            json = makeConnection(curl, reqEntity);


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Parsedata(json);
    }


    public String updatedocument(List<documentdataDTO> dataDTO1)
    {
        try
        {

            String jsonData = "";
            String user_id = AppSession.getInstance().ReadData(mContext, CommanString.USERID,"");
            String curl = CommanString.baseurl + "editDocumentsDetails";
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            jsonData = new Gson().toJson(dataDTO1);
            reqEntity.addPart("user_id", new StringBody(user_id));
            reqEntity.addPart("data", new StringBody(jsonData));


            json = makeConnection(curl, reqEntity);


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Parsedata(json);
    }


    public List<websettingDTO> getWebsetting(String Model,String Manufacture,String Brand,String Sdk,String MobileVersion,String AppVersion)
    {
        JSONObject jsonObject=null;
        try
        {
            String device_id= AppSession.getInstance().ReadData(mContext,CommanString.REGID,"");
            String user_id = AppSession.getInstance().ReadData(mContext, CommanString.USERID,"");
            String curl = CommanString.baseurl + "getWebsetting";
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("user_id", new StringBody(user_id));
            reqEntity.addPart("Model", new StringBody(Model));
            reqEntity.addPart("Manufacture", new StringBody(Manufacture));
            reqEntity.addPart("Brand", new StringBody(Brand));
            reqEntity.addPart("Sdk", new StringBody(Sdk));
            reqEntity.addPart("MobileVersion", new StringBody(MobileVersion));
            reqEntity.addPart("AppVersion", new StringBody(AppVersion));


            json = makeConnection(curl, reqEntity);

            jsonObject= new JSONObject(json);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Parsewebsetting(jsonObject);
    }


    public List<UserdataDTO> sociallogin(String firstName, String lastName, String network_type, String network_id, String email )
    {
        try
        {
            Model =Build.MODEL;
            Manufacture =Build.MANUFACTURER;
            Brand =Build.BRAND;
            Sdk = Build.VERSION.SDK;
            MobileVersion=Build.VERSION.RELEASE;
            AppVersion=CommanString.version;

            String device_id= AppSession.getInstance().ReadData(mContext,CommanString.REGID,"");
            String curl = CommanString.baseurl + "loginWithSocialNetwork";
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            reqEntity.addPart("firstName", new StringBody(firstName));
            reqEntity.addPart("lastName", new StringBody(lastName));
            reqEntity.addPart("network_type", new StringBody(network_type));
            reqEntity.addPart("network_id", new StringBody(network_id));
            reqEntity.addPart("email", new StringBody(email));
            reqEntity.addPart("device_id", new StringBody(device_id));
            reqEntity.addPart("device_type", new StringBody("android"));
            reqEntity.addPart("Model", new StringBody(Model));
            reqEntity.addPart("Manufacture", new StringBody(Manufacture));
            reqEntity.addPart("Brand", new StringBody(Brand));
            reqEntity.addPart("Sdk", new StringBody(Sdk));
            reqEntity.addPart("MobileVersion", new StringBody(MobileVersion));
            reqEntity.addPart("AppVersion", new StringBody(AppVersion));

            json = makeConnection(curl, reqEntity);


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Parsesociallogin(json);
    }

    public List<UserdataDTO> userregister(String firstName, String lastName, String phone_number, String email, String password, String urefer  )
    {
        try
        {
            Model = Build.MODEL;
            Manufacture =Build.MANUFACTURER;
            Brand =Build.BRAND;
            Sdk = Build.VERSION.SDK;
            MobileVersion=Build.VERSION.RELEASE;
            AppVersion=CommanString.version;

            String device_id= AppSession.getInstance().ReadData(mContext,CommanString.REGID,"");
            String curl = CommanString.baseurl + "register";
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);


            reqEntity.addPart("firstName", new StringBody(firstName));
            reqEntity.addPart("lastName", new StringBody(lastName));
            reqEntity.addPart("phone_number", new StringBody(phone_number));
            reqEntity.addPart("email", new StringBody(email));
            reqEntity.addPart("password", new StringBody(password));
            reqEntity.addPart("device_id", new StringBody(device_id));
            reqEntity.addPart("device_type", new StringBody("android"));
            reqEntity.addPart("referred_by", new StringBody(urefer));
            reqEntity.addPart("Model", new StringBody(Model));
            reqEntity.addPart("Manufacture", new StringBody(Manufacture));
            reqEntity.addPart("Brand", new StringBody(Brand));
            reqEntity.addPart("Sdk", new StringBody(Sdk));
            reqEntity.addPart("MobileVersion", new StringBody(MobileVersion));
            reqEntity.addPart("AppVersion", new StringBody(AppVersion));


            json = makeConnection(curl, reqEntity);


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Parsesociallogin(json);
    }



    public List<topcoachingDTO> searchcoaching(String course_id,String city_id)
    {
        try
        {
            String curl = CommanString.baseurl + "searchCoaching";
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("city_id", new StringBody(city_id));
            reqEntity.addPart("course_id", new StringBody(course_id));

            json = makeConnection(curl, reqEntity);


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Parsesearchcoaching(json);
    }



    public String Parsedata(String jsonResponse)
    {
        String status="", error_code="", message="";

        int i;

        try {

            JSONObject jsonObject = new JSONObject(jsonResponse);
            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }

            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return message;
    }


    public String Parseobjectdata(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        int i;

        try {


            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }

            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return message;
    }

    public String Parseapplystatus(String jsonResponse)
    {

        String status="", error_code="", ORDERID="",message="";

        int i;

        try {

            JSONObject jsonObject = new JSONObject(jsonResponse);
            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");
            AppSession.getInstance().SaveData(mContext,CommanString.ORDERID,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("ORDER_ID"))
            {
                ORDERID = jsonObject.getString("ORDER_ID");
                AppSession.getInstance().SaveData(mContext,CommanString.ORDERID,ORDERID);
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }

            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return message;
    }


    public String Parselogoutdata(JSONObject jsonObject)
    {

        String  status ="", message  ="", Property_name  ="", forterm  ="";

        int i;

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return message;
    }


    public String Parseforgotdata(String jsonResponse)
    {

        String status="", error_code="", message="", otp="";

        int i;

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");
            AppSession.getInstance().SaveData(mContext,CommanString.OTP,"");

            JSONObject jsonObject = new JSONObject(jsonResponse);

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (!status.equalsIgnoreCase("0"))
            {
                if (jsonObject.has("message"))
                {
                    message = jsonObject.getString("message");
                }
                if (jsonObject.has("otp"))
                {
                    otp = jsonObject.getString("otp");
                    AppSession.getInstance().SaveData(mContext, CommanString.OTP, otp);
                }
                else
                {
                    return null;
                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0"))
            {
                JSONObject msgObject = jsonObject.getJSONObject("message");

                if (msgObject.has("phone_number"))
                {
                    message = msgObject.getString("phone_number");
                }
                if (msgObject.has("email"))
                {
                    message = msgObject.getString("email");
                }

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return message;
    }

    public String Parseotpdata(String jsonResponse)
    {
        String  status ="", error_code="",message  ="", otp="";

        int i;

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");
            AppSession.getInstance().SaveData(mContext,CommanString.OTP,"");

            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message= jsonObject.getString("message");
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (!status.equalsIgnoreCase("0"))
            {
                if (jsonObject.has("otp"))
                {
                    otp = jsonObject.getString("otp");
                    AppSession.getInstance().SaveData(mContext, CommanString.OTP, otp);
                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }


    public String ParseVerificationStatus(String jsonResponse)
    {
        String  status ="", message  ="", otp="";

        try
        {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }


    public List<UserdataDTO> Parseuserdata(String jsonResponse)
    {
        String job_preference_status = "",job_preference_message = "",personal_detail_status="",personal_detail_message="",
                education_detail_status="", education_detail_message="",address_detail_status="",address_detail_message="" ;

        String status="", error_code="", message="";
        List<UserdataDTO> userdata=new ArrayList<UserdataDTO>();
        CommanString.profilestatusdata.clear();

         String user_id="",firstName="",lastName="",date_of_birth="",email="",gender="",profile_image=""
                 ,phone_number="",address="",facebook_id="",gmail_id="",linkden_id="",twitter_id="",account_status="",
                 zipcode="",city="",state="",country="",facebook_login_id="",gmail_login_id="",
                device_type="", device_id="",emailVerificationStatus="", otp="",MobileVerificationStatus="";

        try {

            AppSession save_pref = AppSession.getInstance();


            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");
            AppSession.getInstance().SaveData(mContext,CommanString.VERIFYSTATUS,"");
            AppSession.getInstance().SaveData(mContext, CommanString.USERID, "");
            AppSession.getInstance().SaveData(mContext, CommanString.EMAIL_ID, "");
            AppSession.getInstance().SaveData(mContext, CommanString.MOBILENUMBER,"");
            AppSession.getInstance().SaveData(mContext, CommanString.PROFILE_PIC, "");
            AppSession.getInstance().SaveData(mContext, CommanString.PREFERENCE_STATUS, "");
            AppSession.getInstance().SaveData(mContext, CommanString.PERSONAL_STATUS, "");
            AppSession.getInstance().SaveData(mContext, CommanString.EDUCATION_STATUS, "");
            AppSession.getInstance().SaveData(mContext, CommanString.ADDRESS_STATUS, "");

            JSONObject jsonObject = new JSONObject(jsonResponse);

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }

            if (jsonObject.has("user_profile_status"))
            {
                JSONObject profile = jsonObject.getJSONObject("user_profile_status");

                if (profile.has("job_preference_status"))
                {
                    job_preference_status = profile.getString("job_preference_status");
                    AppSession.getInstance().SaveData(mContext, CommanString.PREFERENCE_STATUS, job_preference_status);

                }

                if (profile.has("job_preference_message"))
                {
                    job_preference_message = profile.getString("job_preference_message");
                    AppSession.getInstance().SaveData(mContext, CommanString.PREFERENCE_MSG, job_preference_message);

                }

                if (profile.has("personal_detail_status"))
                {
                    personal_detail_status = profile.getString("personal_detail_status");
                    AppSession.getInstance().SaveData(mContext, CommanString.PERSONAL_STATUS, personal_detail_status);

                }

                if (profile.has("personal_detail_message"))
                {
                    personal_detail_message = profile.getString("personal_detail_message");
                    AppSession.getInstance().SaveData(mContext, CommanString.PERSONAL_MSG, personal_detail_message);

                }

                if (profile.has("education_detail_status"))
                {
                    education_detail_status = profile.getString("education_detail_status");
                    AppSession.getInstance().SaveData(mContext, CommanString.EDUCATION_STATUS, education_detail_status);

                }

                if (profile.has("education_detail_message"))
                {
                    education_detail_message = profile.getString("education_detail_message");
                    AppSession.getInstance().SaveData(mContext, CommanString.EDUCATION_MSG, education_detail_message);

                }

                if (profile.has("address_detail_status"))
                {
                    address_detail_status = profile.getString("address_detail_status");
                    AppSession.getInstance().SaveData(mContext, CommanString.ADDRESS_STATUS, address_detail_status);

                }

                if (profile.has("address_detail_message"))
                {
                    address_detail_message = profile.getString("address_detail_message");
                    AppSession.getInstance().SaveData(mContext, CommanString.ADDRESS_MSG, address_detail_message);
                }

                CommanString.profilestatusdata.add(new profilestatusDTO(job_preference_status,job_preference_message,personal_detail_status,personal_detail_message,
                        education_detail_status,education_detail_message,address_detail_status,address_detail_message));

            }
            else
            {

                personal_detail_message="Please complete your Personal Detail";
                education_detail_message="Please complete your Education Detail";
                job_preference_message="Please set your job Preference";
                address_detail_message="Please complete your address Detail";

                AppSession.getInstance().SaveData(mContext, CommanString.PERSONAL_MSG, personal_detail_message);
                AppSession.getInstance().SaveData(mContext, CommanString.EDUCATION_MSG, education_detail_message);
                AppSession.getInstance().SaveData(mContext, CommanString.PREFERENCE_MSG, job_preference_message);
                AppSession.getInstance().SaveData(mContext, CommanString.ADDRESS_MSG, address_detail_message);

                CommanString.profilestatusdata.add(new profilestatusDTO(job_preference_status,job_preference_message,personal_detail_status,personal_detail_message,
                        education_detail_status,education_detail_message,address_detail_status,address_detail_message));

            }



            if (status.equalsIgnoreCase("1") && jsonObject.has("data"))
            {
                JSONArray json_array = jsonObject.getJSONArray("data");

                for (int i = 0; i < json_array.length(); i++)
                {
                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if (jsonObject1.has("user_id"))
                    {
                        user_id = jsonObject1.getString("user_id");
                    }
                    if (jsonObject1.has("firstName"))
                    {
                        firstName = jsonObject1.getString("firstName");
                    }
                    if (jsonObject1.has("lastName"))
                    {
                        lastName = jsonObject1.getString("lastName");
                    }
                    if (jsonObject1.has("email"))
                    {
                        email = jsonObject1.getString("email");
                    }

                    if (jsonObject1.has("profile_image"))
                    {
                        profile_image = jsonObject1.getString("profile_image");
                    }
                    if (jsonObject1.has("MobileVerificationStatus"))
                    {
                        MobileVerificationStatus = jsonObject1.getString("MobileVerificationStatus");
                        save_pref.SaveData(mContext, CommanString.VERIFYSTATUS, MobileVerificationStatus);
                    }

                    if (jsonObject1.has("phone_number"))
                    {
                        String number = jsonObject1.getString("phone_number");
                        int len=number.length();

                        if (!number.equalsIgnoreCase("") && !number.equalsIgnoreCase("null") && len>9)
                        {
                            phone_number = number.substring(number.length() - 10);
                            save_pref.SaveData(mContext, CommanString.MOBILENUMBER, phone_number);
                        }
                        else
                        {
                            save_pref.SaveData(mContext, CommanString.MOBILENUMBER, number);
                        }

                    }
                    userdata.add(new UserdataDTO(user_id,firstName,lastName,date_of_birth,email,gender,profile_image,phone_number,address,facebook_id,gmail_id,linkden_id,
                            twitter_id,account_status,zipcode,city,state,country,facebook_login_id,gmail_login_id,device_type,device_id,emailVerificationStatus,MobileVerificationStatus));
                }

            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return userdata;
    }


    public List<UserdataDTO> Parsesociallogin(String jsonResponse)
    {

        String job_preference_status = "",job_preference_message = "",personal_detail_status="",personal_detail_message="",
                education_detail_status="", education_detail_message="",address_detail_status="",address_detail_message="" ;


        List<UserdataDTO> userdata=new ArrayList<UserdataDTO>();

        String status="", error_code="", message="",user_id="",firstName="",lastName="",date_of_birth="",email="",gender="",profile_image=""
                ,phone_number="",address="",facebook_id="",gmail_id="",linkden_id="",twitter_id="",account_status="",
                zipcode="",city="",state="",country="",facebook_login_id="",gmail_login_id="",
                device_type="", device_id="",emailVerificationStatus="",MobileVerificationStatus="";

        try {

            AppSession save_pref = AppSession.getInstance();

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");
            AppSession.getInstance().SaveData(mContext,CommanString.VERIFYSTATUS,"");
            AppSession.getInstance().SaveData(mContext,CommanString.OTP,"");
            AppSession.getInstance().SaveData(mContext, CommanString.USERID, "");
            AppSession.getInstance().SaveData(mContext, CommanString.EMAIL_ID, "");
            AppSession.getInstance().SaveData(mContext, CommanString.MOBILENUMBER,"");
            AppSession.getInstance().SaveData(mContext, CommanString.PROFILE_PIC, "");
            AppSession.getInstance().SaveData(mContext, CommanString.NAME, "");
            AppSession.getInstance().SaveData(mContext, CommanString.PREFERENCE_STATUS, "");
            AppSession.getInstance().SaveData(mContext, CommanString.PERSONAL_STATUS, "");
            AppSession.getInstance().SaveData(mContext, CommanString.EDUCATION_STATUS, "");
            AppSession.getInstance().SaveData(mContext, CommanString.ADDRESS_STATUS, "");
            AppSession.getInstance().SaveData(mContext, CommanString.PREFERENCE_MSG, "");
            AppSession.getInstance().SaveData(mContext, CommanString.PERSONAL_MSG, "");
            AppSession.getInstance().SaveData(mContext, CommanString.EDUCATION_MSG, "");
            AppSession.getInstance().SaveData(mContext, CommanString.ADDRESS_MSG, "");


            JSONObject jsonObject = new JSONObject(jsonResponse);

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (jsonObject.has("otp"))
            {
                String otp = jsonObject.getString("otp");
                AppSession.getInstance().SaveData(mContext, CommanString.OTP, otp);
            }

            if (jsonObject.has("user_profile_status"))
            {
                JSONObject profile = jsonObject.getJSONObject("user_profile_status");

                if (profile.has("job_preference_status"))
                {
                    job_preference_status = profile.getString("job_preference_status");
                    AppSession.getInstance().SaveData(mContext, CommanString.PREFERENCE_STATUS, job_preference_status);

                }

                if (profile.has("job_preference_message"))
                {
                    job_preference_message = profile.getString("job_preference_message");
                    AppSession.getInstance().SaveData(mContext, CommanString.PREFERENCE_MSG, job_preference_message);

                }

                if (profile.has("personal_detail_status"))
                {
                    personal_detail_status = profile.getString("personal_detail_status");
                    AppSession.getInstance().SaveData(mContext, CommanString.PERSONAL_STATUS, personal_detail_status);

                }

                if (profile.has("personal_detail_message"))
                {
                    personal_detail_message = profile.getString("personal_detail_message");
                    AppSession.getInstance().SaveData(mContext, CommanString.PERSONAL_MSG, personal_detail_message);

                }

                if (profile.has("education_detail_status"))
                {
                    education_detail_status = profile.getString("education_detail_status");
                    AppSession.getInstance().SaveData(mContext, CommanString.EDUCATION_STATUS, education_detail_status);

                }

                if (profile.has("education_detail_message"))
                {
                    education_detail_message = profile.getString("education_detail_message");
                    AppSession.getInstance().SaveData(mContext, CommanString.EDUCATION_MSG, education_detail_message);

                }

                if (profile.has("address_detail_status"))
                {
                    address_detail_status = profile.getString("address_detail_status");
                    AppSession.getInstance().SaveData(mContext, CommanString.ADDRESS_STATUS, address_detail_status);

                }

                if (profile.has("address_detail_message"))
                {
                    address_detail_message = profile.getString("address_detail_message");
                    AppSession.getInstance().SaveData(mContext, CommanString.ADDRESS_MSG, address_detail_message);
                }

                CommanString.profilestatusdata.add(new profilestatusDTO(job_preference_status,job_preference_message,personal_detail_status,personal_detail_message,
                        education_detail_status,education_detail_message,address_detail_status,address_detail_message));

            }
            else
            {

                personal_detail_message="Please complete your Personal Detail";
                education_detail_message="Please complete your Education Detail";
                job_preference_message="Please set your job Preference";
                address_detail_message="Please complete your address Detail";

                AppSession.getInstance().SaveData(mContext, CommanString.PERSONAL_MSG, personal_detail_message);
                AppSession.getInstance().SaveData(mContext, CommanString.EDUCATION_MSG, education_detail_message);
                AppSession.getInstance().SaveData(mContext, CommanString.PREFERENCE_MSG, job_preference_message);
                AppSession.getInstance().SaveData(mContext, CommanString.ADDRESS_MSG, address_detail_message);

                CommanString.profilestatusdata.add(new profilestatusDTO(job_preference_status,job_preference_message,personal_detail_status,personal_detail_message,
                        education_detail_status,education_detail_message,address_detail_status,address_detail_message));

            }

            if (status.equalsIgnoreCase("1") && jsonObject.has("data"))
            {
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    if (jsonObject1.has("user_id"))
                    {
                        user_id = jsonObject1.getString("user_id");
                    }
                    if (jsonObject1.has("firstName"))
                    {
                        firstName = jsonObject1.getString("firstName");
                    }
                    if (jsonObject1.has("lastName"))
                    {
                        lastName = jsonObject1.getString("lastName");
                    }
                    if (jsonObject1.has("email"))
                    {
                        email = jsonObject1.getString("email");
                    }

                    if (jsonObject1.has("profile_image"))
                    {
                        profile_image = jsonObject1.getString("profile_image");
                    }
                    if (jsonObject1.has("MobileVerificationStatus"))
                    {
                        MobileVerificationStatus = jsonObject1.getString("MobileVerificationStatus");
                        save_pref.SaveData(mContext, CommanString.VERIFYSTATUS, MobileVerificationStatus);
                    }

                if (jsonObject1.has("phone_number"))
                    {
                    String number = jsonObject1.getString("phone_number");
                    int len=number.length();

                    if (!number.equalsIgnoreCase("") && !number.equalsIgnoreCase("null") && len>9)
                    {
                        phone_number = number.substring(number.length() - 10);
                        save_pref.SaveData(mContext, CommanString.MOBILENUMBER, phone_number);
                    }
                    else
                    {
                        save_pref.SaveData(mContext, CommanString.MOBILENUMBER, number);
                    }

                }
                    userdata.add(new UserdataDTO(user_id,firstName,lastName,date_of_birth,email,gender,profile_image,phone_number,address,facebook_id,gmail_id,linkden_id,
                            twitter_id,account_status,zipcode,city,state,country,facebook_login_id,gmail_login_id,device_type,device_id,emailVerificationStatus,MobileVerificationStatus));

            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return userdata;
    }


    public List<UserdataDTO> Parseuseraccountdata(JSONObject jsonObject)
    {
        String status="", error_code="", message="";
        List<UserdataDTO> userdata=new ArrayList<UserdataDTO>();

        String user_id="",firstName="",lastName="",date_of_birth="",email="",gender="",profile_image=""
                ,phone_number="",address="",facebook_id="",gmail_id="",linkden_id="",twitter_id="",account_status="",
                zipcode="",city="",state="",country="",facebook_login_id="",gmail_login_id="",
                device_type="", device_id="",emailVerificationStatus="", otp="",MobileVerificationStatus="";

        try {

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }

            if (jsonObject.has("data"))
            {
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    if (jsonObject1.has("user_id"))
                    {
                        user_id = jsonObject1.getString("user_id");
                    }
                    if (jsonObject1.has("firstName"))
                    {
                        firstName = jsonObject1.getString("firstName");
                    }
                    if (jsonObject1.has("lastName"))
                    {
                        lastName = jsonObject1.getString("lastName");
                    }
                    if (jsonObject1.has("email"))
                    {
                        email = jsonObject1.getString("email");
                    }

                    if (jsonObject1.has("profile_image"))
                    {
                        profile_image = jsonObject1.getString("profile_image");
                    }
                    if (jsonObject1.has("MobileVerificationStatus"))
                    {
                        MobileVerificationStatus = jsonObject1.getString("MobileVerificationStatus");
                    }

                if (jsonObject1.has("phone_number"))
                {
                    String number = jsonObject1.getString("phone_number");
                    int len=number.length();
                    if (number!=null && !number.equalsIgnoreCase("") && !number.equalsIgnoreCase("null") && len>9)
                    {
                        phone_number = number.substring(number.length() - 10);
                    }
                    else
                    {
                        phone_number = jsonObject1.getString("phone_number");
                    }

                }
                    userdata.add(new UserdataDTO(user_id,firstName,lastName,date_of_birth,email,gender,profile_image,phone_number,address,facebook_id,gmail_id,linkden_id,
                            twitter_id,account_status,zipcode,city,state,country,facebook_login_id,gmail_login_id,device_type,device_id,emailVerificationStatus,MobileVerificationStatus));
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return userdata;
    }


    public List<latestjobDTO> Parselatestjobs(JSONObject jsonObject)
    {
        String status="", error_code="", message="",loadmore="";

        String article_id="",category_alias="",article_title = "",article_alias="",job_website_link="",department_title="",department_alias="",lastdate = "",qualification = "",total_vacancy="",location_title="",location_alias="",board_title="",board_alias="",post_title="",post_alias="",qualification_title="",qualification_alias="",applied="",checkExpiry="" ;

        int i;
        List<latestjobDTO> linksdata = new ArrayList<latestjobDTO>();

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (jsonObject.has("loadmore"))
            {
                loadmore = jsonObject.getString("loadmore");
                AppSession.getInstance().SaveData(mContext,CommanString.LOADMORE,loadmore);
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONArray json_array = jsonObject.getJSONArray("data");

                for ( i = 0; i< json_array.length(); i++)
                {
                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if(jsonObject1.has("category_alias"))
                    {
                        category_alias = jsonObject1.getString("category_alias");
                    }
                    if(jsonObject1.has("article_id"))
                    {
                        article_id = jsonObject1.getString("article_id");
                    }
                    if(jsonObject1.has("article_title"))
                    {
                        article_title = jsonObject1.getString("article_title");
                    }
                    if(jsonObject1.has("article_alias"))
                    {
                        article_alias = jsonObject1.getString("article_alias");
                    }
                    if(jsonObject1.has("submission_last_date"))
                    {
                        lastdate = jsonObject1.getString("submission_last_date");
                    }
                    if(jsonObject1.has("total_vacancy"))
                    {
                        total_vacancy = jsonObject1.getString("total_vacancy");
                    }
                    if(jsonObject1.has("job_website_link"))
                    {
                        job_website_link = jsonObject1.getString("job_website_link");
                    }
                    if(jsonObject1.has("department_title"))
                    {
                        department_title = jsonObject1.getString("department_title");
                    }
                    if(jsonObject1.has("department_alias"))
                    {
                        department_alias = jsonObject1.getString("department_alias");
                    }
                    if(jsonObject1.has("location_title"))
                    {
                        location_title = jsonObject1.getString("location_title");
                    }
                    if(jsonObject1.has("location_alias"))
                    {
                        location_alias = jsonObject1.getString("location_alias");
                    }
                    if(jsonObject1.has("board_title"))
                    {
                        board_title = jsonObject1.getString("board_title");
                    }
                    if(jsonObject1.has("board_alias"))
                    {
                        board_alias = jsonObject1.getString("board_alias");
                    }
                    if(jsonObject1.has("post_title"))
                    {
                        post_title = jsonObject1.getString("post_title");
                    }
                    if(jsonObject1.has("post_alias"))
                    {
                        post_alias = jsonObject1.getString("post_alias");
                    }
                    if(jsonObject1.has("qualification_title"))
                    {
                        qualification_title = jsonObject1.getString("qualification_title");
                    }
                    if(jsonObject1.has("qualification_alias"))
                    {
                        qualification_alias = jsonObject1.getString("qualification_alias");
                    }
                    if(jsonObject1.has("checkExpiry"))
                    {
                        checkExpiry = jsonObject1.getString("checkExpiry");
                    }
                    if(jsonObject1.has("applied"))
                    {
                        applied = jsonObject1.getString("applied");


                    }
                    linksdata.add(new latestjobDTO(article_id,category_alias,article_title,article_alias,lastdate,total_vacancy,job_website_link,department_title,department_alias,location_title,location_alias,board_title,board_alias,post_title,post_alias,qualification_title,qualification_alias,checkExpiry,applied));

                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }



    public List<activitylogDTO> Parseactivitylog(JSONObject jsonObject)
    {
        String status="", error_code="", message="",loadmore="";

        String activity_id = "",heading="",data_category = "",activity="",activity_by="",activity_source="",
                user_type="",table_name="", data_id="",description="",activity_url="",activity_state="",activity_date="";

        int i;
        List<activitylogDTO> linksdata = new ArrayList<activitylogDTO>();

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (jsonObject.has("loadmore"))
            {
                loadmore = jsonObject.getString("loadmore");
                AppSession.getInstance().SaveData(mContext,CommanString.LOADMORE,loadmore);
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONArray json_array = jsonObject.getJSONArray("data");

                for ( i = 0; i< json_array.length(); i++)
                {
                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if(jsonObject1.has("activity_id"))
                    {
                        activity_id = jsonObject1.getString("activity_id");
                    }
                    if(jsonObject1.has("heading"))
                    {
                        heading = jsonObject1.getString("heading");
                    }
                    if(jsonObject1.has("data_category"))
                    {
                        data_category = jsonObject1.getString("data_category");
                    }
                    if(jsonObject1.has("activity"))
                    {
                        activity = jsonObject1.getString("activity");
                    }
                    if(jsonObject1.has("activity_by"))
                    {
                        activity_by = jsonObject1.getString("activity_by");
                    }
                    if(jsonObject1.has("activity_source"))
                    {
                        activity_source = jsonObject1.getString("activity_source");
                    }
                    if(jsonObject1.has("user_type"))
                    {
                        user_type = jsonObject1.getString("user_type");
                    }
                    if(jsonObject1.has("table_name"))
                    {
                        table_name = jsonObject1.getString("table_name");
                    }
                    if(jsonObject1.has("data_id"))
                    {
                        data_id = jsonObject1.getString("data_id");
                    }
                    if(jsonObject1.has("description"))
                    {
                        description = jsonObject1.getString("description");
                    }
                    if(jsonObject1.has("activity_url"))
                    {
                        activity_url = jsonObject1.getString("activity_url");
                    }
                    if(jsonObject1.has("activity_state"))
                    {
                        activity_state = jsonObject1.getString("activity_state");
                    }
                    if(jsonObject1.has("activity_date"))
                    {
                        activity_date = jsonObject1.getString("activity_date");
                    }

                    linksdata.add(new activitylogDTO(activity_id ,heading,data_category ,activity,activity_by,activity_source,
                            user_type,table_name, data_id,description,activity_url,activity_state,activity_date));

                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }



    public List<formsummaryDTO> Parseformsummary(JSONObject jsonObject)
    {
        String status="", error_code="", message="",loadmore="";

        String firstName="",lastName="",article_title = "",article_alias="",total_vacancy="",lastdate="",available_balance="",challan_fee="",
                service_fee="",total_fee="",payable_amount = "",transaction_fee = "",tax="",community="",extra_info="",order_total="";

        int i;
        List<formsummaryDTO> linksdata = new ArrayList<formsummaryDTO>();

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.COMMUNITY,"");
            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }

            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    if(jsonObject1.has("firstName"))
                    {
                        firstName = jsonObject1.getString("firstName");
                    }
                    if(jsonObject1.has("lastName"))
                    {
                        lastName = jsonObject1.getString("lastName");
                    }
                    if(jsonObject1.has("article_title"))
                    {
                        article_title = jsonObject1.getString("article_title");
                    }
                    if(jsonObject1.has("article_alias"))
                    {
                        article_alias = jsonObject1.getString("article_alias");
                    }
                    if(jsonObject1.has("submission_last_date"))
                    {
                        lastdate = jsonObject1.getString("submission_last_date");
                    }
                    if(jsonObject1.has("total_vacancy"))
                    {
                        total_vacancy = jsonObject1.getString("total_vacancy");
                    }
                    if(jsonObject1.has("available_balance"))
                    {
                        available_balance = jsonObject1.getString("available_balance");
                    }
                    if(jsonObject1.has("challan_fee"))
                    {
                        challan_fee = jsonObject1.getString("challan_fee");
                    }
                    if(jsonObject1.has("service_fee"))
                    {
                        service_fee = jsonObject1.getString("service_fee");
                    }
                    if(jsonObject1.has("total_fee"))
                    {
                        total_fee = jsonObject1.getString("total_fee");
                    }
                    if(jsonObject1.has("payable_amount"))
                    {
                        payable_amount = jsonObject1.getString("payable_amount");
                    }
                    if(jsonObject1.has("transaction_fee"))
                    {
                        transaction_fee = jsonObject1.getString("transaction_fee");
                    }
                    if(jsonObject1.has("order_total"))
                    {
                        order_total = jsonObject1.getString("order_total");
                    }
                if(jsonObject1.has("tax"))
                {
                    tax = jsonObject1.getString("tax");
                }
                if(jsonObject1.has("community"))
                {
                    community = jsonObject1.getString("community");
                    AppSession.getInstance().SaveData(mContext,CommanString.COMMUNITY,community);
                }
                if(jsonObject1.has("extra_info"))
                {
                    extra_info = jsonObject1.getString("extra_info");
                }
                if(jsonObject1.has("community"))
                {
                    community = jsonObject1.getString("community");
                }

                    linksdata.add(new formsummaryDTO(firstName,lastName,article_title,article_alias,lastdate,total_vacancy,available_balance
                            ,challan_fee,service_fee,tax,total_fee,payable_amount,transaction_fee,order_total,extra_info,community));

            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }

    public List<communityDTO> Parsecommunity(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String name=null,form_fee=null,transaction_fee=null,service_fee=null,totalServiceFree=null,
                total = "",tvalue = "",tstatus="";
        int i;
        List<communityDTO> linksdata = new ArrayList<communityDTO>();

        try {


            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONObject dataObject = jsonObject.getJSONObject("data");

                if (dataObject.has("general"))
                {
                    JSONObject jsonObject1 = dataObject.getJSONObject("general");

                    if(jsonObject1.has("name"))
                    {
                        name = jsonObject1.getString("name");
                    }
                    if(jsonObject1.has("form_fee"))
                    {
                        form_fee = jsonObject1.getString("form_fee");
                    }
                    if(jsonObject1.has("transaction_fee"))
                    {
                        transaction_fee = jsonObject1.getString("transaction_fee");
                    }
                    if(jsonObject1.has("service_fee"))
                    {
                        service_fee = jsonObject1.getString("service_fee");
                    }
                    if(jsonObject1.has("totalServiceFree"))
                    {
                        totalServiceFree = jsonObject1.getString("totalServiceFree");
                    }
                    if(jsonObject1.has("total"))
                    {
                        total = jsonObject1.getString("total");
                    }
                    if(jsonObject1.has("tvalue"))
                    {
                        tvalue = jsonObject1.getString("tvalue");
                    }
                    if(jsonObject1.has("status"))
                    {
                        tstatus = jsonObject1.getString("status");
                    }
                    linksdata.add(new communityDTO(name,form_fee,transaction_fee,service_fee,totalServiceFree,total,tvalue,tstatus));

                }

                if (dataObject.has("obc"))
                {
                    JSONObject jsonObject1 = dataObject.getJSONObject("obc");

                    if(jsonObject1.has("name"))
                    {
                        name = jsonObject1.getString("name");
                    }
                    if(jsonObject1.has("form_fee"))
                    {
                        form_fee = jsonObject1.getString("form_fee");
                    }
                    if(jsonObject1.has("transaction_fee"))
                    {
                        transaction_fee = jsonObject1.getString("transaction_fee");
                    }
                    if(jsonObject1.has("service_fee"))
                    {
                        service_fee = jsonObject1.getString("service_fee");
                    }
                    if(jsonObject1.has("totalServiceFree"))
                    {
                        totalServiceFree = jsonObject1.getString("totalServiceFree");
                    }
                    if(jsonObject1.has("total"))
                    {
                        total = jsonObject1.getString("total");
                    }
                    if(jsonObject1.has("tvalue"))
                    {
                        tvalue = jsonObject1.getString("tvalue");
                    }
                    if(jsonObject1.has("status"))
                    {
                        tstatus = jsonObject1.getString("status");
                    }
                    linksdata.add(new communityDTO(name,form_fee,transaction_fee,service_fee,totalServiceFree,total,tvalue,tstatus));

                }

                if (dataObject.has("sc_st"))
                {
                    JSONObject jsonObject1 = dataObject.getJSONObject("sc_st");

                    if(jsonObject1.has("name"))
                    {
                        name = jsonObject1.getString("name");
                    }
                    if(jsonObject1.has("form_fee"))
                    {
                        form_fee = jsonObject1.getString("form_fee");
                    }
                    if(jsonObject1.has("transaction_fee"))
                    {
                        transaction_fee = jsonObject1.getString("transaction_fee");
                    }
                    if(jsonObject1.has("service_fee"))
                    {
                        service_fee = jsonObject1.getString("service_fee");
                    }
                    if(jsonObject1.has("totalServiceFree"))
                    {
                        totalServiceFree = jsonObject1.getString("totalServiceFree");
                    }
                    if(jsonObject1.has("total"))
                    {
                        total = jsonObject1.getString("total");
                    }
                    if(jsonObject1.has("tvalue"))
                    {
                        tvalue = jsonObject1.getString("tvalue");
                    }
                    if(jsonObject1.has("status"))
                    {
                        tstatus = jsonObject1.getString("status");
                    }
                    linksdata.add(new communityDTO(name,form_fee,transaction_fee,service_fee,totalServiceFree,total,tvalue,tstatus));

                }


                if (dataObject.has("women"))
                {
                    JSONObject jsonObject1 = dataObject.getJSONObject("women");

                    if(jsonObject1.has("name"))
                    {
                        name = jsonObject1.getString("name");
                    }
                    if(jsonObject1.has("form_fee"))
                    {
                        form_fee = jsonObject1.getString("form_fee");
                    }
                    if(jsonObject1.has("transaction_fee"))
                    {
                        transaction_fee = jsonObject1.getString("transaction_fee");
                    }
                    if(jsonObject1.has("service_fee"))
                    {
                        service_fee = jsonObject1.getString("service_fee");
                    }
                    if(jsonObject1.has("totalServiceFree"))
                    {
                        totalServiceFree = jsonObject1.getString("totalServiceFree");
                    }
                    if(jsonObject1.has("total"))
                    {
                        total = jsonObject1.getString("total");
                    }
                    if(jsonObject1.has("tvalue"))
                    {
                        tvalue = jsonObject1.getString("tvalue");
                    }

                    if(jsonObject1.has("status"))
                    {
                        tstatus = jsonObject1.getString("status");
                    }
                    linksdata.add(new communityDTO(name,form_fee,transaction_fee,service_fee,totalServiceFree,total,tvalue,tstatus));

                }

                if (dataObject.has("handicapt"))
                {
                    JSONObject jsonObject1 = dataObject.getJSONObject("handicapt");

                    if(jsonObject1.has("name"))
                    {
                        name = jsonObject1.getString("name");
                    }
                    if(jsonObject1.has("form_fee"))
                    {
                        form_fee = jsonObject1.getString("form_fee");
                    }
                    if(jsonObject1.has("transaction_fee"))
                    {
                        transaction_fee = jsonObject1.getString("transaction_fee");
                    }
                    if(jsonObject1.has("service_fee"))
                    {
                        service_fee = jsonObject1.getString("service_fee");
                    }
                    if(jsonObject1.has("totalServiceFree"))
                    {
                        totalServiceFree = jsonObject1.getString("totalServiceFree");
                    }
                    if(jsonObject1.has("total"))
                    {
                        total = jsonObject1.getString("total");
                    }
                    if(jsonObject1.has("tvalue"))
                    {
                        tvalue = jsonObject1.getString("tvalue");
                    }
                    if(jsonObject1.has("status"))
                    {
                        tstatus = jsonObject1.getString("status");
                    }
                    linksdata.add(new communityDTO(name,form_fee,transaction_fee,service_fee,totalServiceFree,total,tvalue,tstatus));

                }


                if (dataObject.has("exservicemen_fee"))
                {
                    JSONObject jsonObject1 = dataObject.getJSONObject("exservicemen_fee");

                    if(jsonObject1.has("name"))
                    {
                        name = jsonObject1.getString("name");
                    }
                    if(jsonObject1.has("form_fee"))
                    {
                        form_fee = jsonObject1.getString("form_fee");
                    }
                    if(jsonObject1.has("transaction_fee"))
                    {
                        transaction_fee = jsonObject1.getString("transaction_fee");
                    }
                    if(jsonObject1.has("service_fee"))
                    {
                        service_fee = jsonObject1.getString("service_fee");
                    }
                    if(jsonObject1.has("totalServiceFree"))
                    {
                        totalServiceFree = jsonObject1.getString("totalServiceFree");
                    }
                    if(jsonObject1.has("total"))
                    {
                        total = jsonObject1.getString("total");
                    }
                    if(jsonObject1.has("tvalue"))
                    {
                        tvalue = jsonObject1.getString("tvalue");
                    }
                    if(jsonObject1.has("status"))
                    {
                        tstatus = jsonObject1.getString("status");
                    }
                    linksdata.add(new communityDTO(name,form_fee,transaction_fee,service_fee,totalServiceFree,total,tvalue,tstatus));

                }



            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }


    public List<educationdetailDTO> Parseeducationdata(JSONObject jsonObject)
    {
        String status="", error_code="", extra_info="",message="";

        String education_detail_id="",category_title="",category_id="",exam=null,board=null,year = null,rollno=null,omark=null,tmark=null,percent=null
                ,education_detail="",other_education_detail="",marksheet="",certificate="";

        String qualification_id = null,scategory_id = null,qualification_title=null,qualification_alias=null ;

        int i;
        List<educationdetailDTO> linksdata = new ArrayList<educationdetailDTO>();


        try {


            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("extra_info"))
            {
                extra_info = jsonObject.getString("extra_info");
                CommanString.extra_info=extra_info;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }

            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONArray json_array = jsonObject.getJSONArray("data");

                for ( i = 0; i< json_array.length(); i++)
                {
                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if(jsonObject1.has("education_detail_id"))
                    {
                        education_detail_id = jsonObject1.getString("education_detail_id");
                    }
                    if(jsonObject1.has("category_id"))
                    {
                        category_id = jsonObject1.getString("category_id");
                    }
                    if(jsonObject1.has("category_title"))
                    {
                        category_title = jsonObject1.getString("category_title");
                    }
                    if(jsonObject1.has("exam_name"))
                    {
                        exam = jsonObject1.getString("exam_name");
                    }
                    if(jsonObject1.has("exam_board"))
                    {
                        board = jsonObject1.getString("exam_board");
                    }

                    if(jsonObject1.has("year_of_passing"))
                    {
                        year = jsonObject1.getString("year_of_passing");
                    }

                    if(jsonObject1.has("roll_number"))
                    {
                        rollno = jsonObject1.getString("roll_number");
                    }

                    if(jsonObject1.has("obtained_marks"))
                    {
                        omark = jsonObject1.getString("obtained_marks");
                    }

                    if(jsonObject1.has("total_marks"))
                    {
                        tmark = jsonObject1.getString("total_marks");
                    }
                    if(jsonObject1.has("percent"))
                    {
                        percent = jsonObject1.getString("percent");
                    }
                    if(jsonObject1.has("education_detail"))
                    {
                        education_detail = jsonObject1.getString("education_detail");
                    }
                    if(jsonObject1.has("other_education_detail"))
                    {
                        other_education_detail = jsonObject1.getString("other_education_detail");
                    }
                    if(jsonObject1.has("marksheet"))
                    {
                        marksheet = jsonObject1.getString("marksheet");
                    }
                    if(jsonObject1.has("certificate"))
                    {
                        certificate = jsonObject1.getString("certificate");
                    }

                    linksdata.add(new educationdetailDTO(education_detail_id,category_id,category_title,exam,board,year,rollno,omark,tmark,percent,education_detail,other_education_detail,marksheet,certificate));

                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }


    public List<educationdetailDTO> Parseeducationbyiddata(JSONObject jsonObject)
    {
        String status="", mode="",error_code="", message="";

        String education_detail_id="",category_title="",category_id="",exam=null,board=null,year = null,rollno=null,omark=null,tmark=null,percent=null
                ,education_detail="",other_education_detail="",marksheet="",certificate="";

        String qualification_id = null,scategory_id = null,qualification_title=null,qualification_alias=null ;

        int i;
        List<educationdetailDTO> linksdata = new ArrayList<educationdetailDTO>();

        CommanString.qualifications.clear();

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.MODE,"");
            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }

            if (jsonObject.has("mode"))
            {
                mode = jsonObject.getString("mode");
                AppSession.getInstance().SaveData(mContext,CommanString.MODE,mode);
            }

            if (jsonObject.has("year_list"))
            {
                JSONArray json_array = jsonObject.getJSONArray("year_list");

                CommanString.yearsdata=new String[json_array.length()];
                for (i = 0; i < json_array.length(); i++)
                {
                    CommanString.yearsdata[i]=json_array.get(i).toString();
                }
            }

            if (jsonObject.has("qualifications"))
            {
                if (!jsonObject.get("qualifications").toString().equalsIgnoreCase(""))
                {
                    JSONArray json_array = jsonObject.getJSONArray("qualifications");

                    for (i = 0; i < json_array.length(); i++)
                    {
                        JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                        if (jsonObject1.has("qualification_id"))
                        {
                            qualification_id = jsonObject1.getString("qualification_id");
                        }
                        if (jsonObject1.has("category_id"))
                        {
                            scategory_id = jsonObject1.getString("category_id");
                        }
                        if (jsonObject1.has("qualification_title"))
                        {
                            qualification_title = jsonObject1.getString("qualification_title");
                        }
                        if (jsonObject1.has("qualification_alias"))
                        {
                            qualification_alias = jsonObject1.getString("qualification_alias");
                        }

                        CommanString.qualifications.add(new qualificationDTO(qualification_id, scategory_id, qualification_title, qualification_alias));
                    }
                }
            }


            if (status.equals("1") && jsonObject.has("data"))
            {
                if (!jsonObject.get("data").toString().equalsIgnoreCase(""))
                {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    if (jsonObject1.has("education_detail_id")) {
                        education_detail_id = jsonObject1.getString("education_detail_id");
                    }
                    if (jsonObject1.has("category_id")) {
                        category_id = jsonObject1.getString("category_id");
                    }
                    if (jsonObject1.has("category_title")) {
                        category_title = jsonObject1.getString("category_title");
                    }
                    if (jsonObject1.has("exam_name")) {
                        exam = jsonObject1.getString("exam_name");
                    }
                    if (jsonObject1.has("exam_board")) {
                        board = jsonObject1.getString("exam_board");
                    }

                    if (jsonObject1.has("year_of_passing")) {
                        year = jsonObject1.getString("year_of_passing");
                    }

                    if (jsonObject1.has("roll_number")) {
                        rollno = jsonObject1.getString("roll_number");
                    }

                    if (jsonObject1.has("obtained_marks")) {
                        omark = jsonObject1.getString("obtained_marks");
                    }

                    if (jsonObject1.has("total_marks")) {
                        tmark = jsonObject1.getString("total_marks");
                    }
                    if (jsonObject1.has("percent")) {
                        percent = jsonObject1.getString("percent");
                    }
                    if (jsonObject1.has("education_detail")) {
                        education_detail = jsonObject1.getString("education_detail");
                    }
                    if (jsonObject1.has("other_education_detail")) {
                        other_education_detail = jsonObject1.getString("other_education_detail");
                    }
                    if (jsonObject1.has("marksheet")) {
                        marksheet = jsonObject1.getString("marksheet");
                    }
                    if (jsonObject1.has("certificate")) {
                        certificate = jsonObject1.getString("certificate");
                    }

                    linksdata.add(new educationdetailDTO(education_detail_id, category_id, category_title, exam, board, year, rollno, omark, tmark, percent, education_detail, other_education_detail, marksheet, certificate));
                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }




    public List<personaldetailDTO> Parsepersonaldetail(JSONObject jsonObject)
    {

        String status="", error_code="", message="";

        String personal_detail_id="",firstName="",middleName="",lastName="",gender = "",birth_place="",dob="",father_name="",mother_name="",nationality="",marital_status = "",physically_challenged = "",challenged_category="",challenged_percentage="",
                community="",creamy_layer="",minority="",religion="",day="",month="",exservicemen="",year="",fee_remission_allowed="" ;

        int i;
        List<personaldetailDTO> linksdata = new ArrayList<personaldetailDTO>();

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }

            if (status.equals("1") &&jsonObject.has("data"))
            {
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    if(jsonObject1.has("personal_detail_id"))
                    {
                        personal_detail_id = jsonObject1.getString("personal_detail_id");
                    }
                    if(jsonObject1.has("firstName"))
                    {
                        firstName = jsonObject1.getString("firstName");
                    }
                if(jsonObject1.has("middleName"))
                {
                    middleName = jsonObject1.getString("middleName");
                }
                if(jsonObject1.has("lastName"))
                {
                    lastName = jsonObject1.getString("lastName");
                }
                    if(jsonObject1.has("gender"))
                    {
                        gender = jsonObject1.getString("gender");
                    }
                    if(jsonObject1.has("dob"))
                    {
                        dob = jsonObject1.getString("dob");
                    }
                if(jsonObject1.has("birth_place"))
                {
                    birth_place = jsonObject1.getString("birth_place");
                }

                    if(jsonObject1.has("father_name"))
                    {
                        father_name = jsonObject1.getString("father_name");
                    }
                    if(jsonObject1.has("mother_name"))
                    {
                        mother_name = jsonObject1.getString("mother_name");
                    }
                    if(jsonObject1.has("nationality"))
                    {
                        nationality = jsonObject1.getString("nationality");
                    }
                    if(jsonObject1.has("marital_status"))
                    {
                        marital_status = jsonObject1.getString("marital_status");
                    }
                    if(jsonObject1.has("physically_challenged"))
                    {
                        physically_challenged = jsonObject1.getString("physically_challenged");
                    }
                    if(jsonObject1.has("challenged_category"))
                    {
                        challenged_category = jsonObject1.getString("challenged_category");
                    }
                    if(jsonObject1.has("challenged_percentage"))
                    {
                        challenged_percentage = jsonObject1.getString("challenged_percentage");
                    }
                    if(jsonObject1.has("community"))
                    {
                        community = jsonObject1.getString("community");
                    }
                    if(jsonObject1.has("creamy_layer"))
                    {
                        creamy_layer = jsonObject1.getString("creamy_layer");
                    }
                    if(jsonObject1.has("minority"))
                    {
                        minority = jsonObject1.getString("minority");
                    }
                    if(jsonObject1.has("religion"))
                    {
                        religion = jsonObject1.getString("religion");
                    }
                    if(jsonObject1.has("fee_remission_allowed"))
                    {
                        fee_remission_allowed = jsonObject1.getString("fee_remission_allowed");
                    }
                if(jsonObject1.has("day"))
                {
                    day = jsonObject1.getString("day");
                }
                if(jsonObject1.has("month"))
                {
                    month = jsonObject1.getString("month");
                }
                if(jsonObject1.has("year"))
                {
                    year = jsonObject1.getString("year");
                }
                if(jsonObject1.has("exservicemen"))
                {
                    exservicemen = jsonObject1.getString("exservicemen");
                }
                    linksdata.add(new personaldetailDTO(personal_detail_id,firstName,middleName,lastName,gender,dob,birth_place,father_name,mother_name,nationality,marital_status,physically_challenged,challenged_category,challenged_percentage,community,creamy_layer,minority,religion,fee_remission_allowed,day,month,year,exservicemen));

            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }


  /*  public List<educationdetailDTO> Parseeducationdetail(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String[] exam=null,board=null,year = null,rollno=null,omark=null,tmark=null,percent=null;

        int i;
        List<educationdetailDTO> linksdata = new ArrayList<educationdetailDTO>();

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                if (jsonObject1.getString("education").equalsIgnoreCase("false") || jsonObject1.getString("education").equalsIgnoreCase("False") || jsonObject1.getString("education").equalsIgnoreCase("") )
                {

                }
                else
                {
                    JSONObject eduobj = jsonObject1.getJSONObject("education");

                    JSONArray examArray = eduobj.getJSONArray("exam");

                    exam = new String[examArray.length()];

                    for (int j = 0; j < examArray.length(); j++)
                    {
                        exam[j] = examArray.get(j).toString();
                    }

                    JSONArray boardarray = eduobj.getJSONArray("board");

                    board = new String[boardarray.length()];

                    for (int j = 0; j < boardarray.length(); j++) {
                        board[j] = boardarray.get(j).toString();
                    }

                    JSONArray yeararray = eduobj.getJSONArray("year");

                    year = new String[yeararray.length()];

                    for (int j = 0; j < yeararray.length(); j++) {
                        year[j] = yeararray.get(j).toString();
                    }

                    JSONArray rollnoarray = eduobj.getJSONArray("rollno");

                    rollno = new String[rollnoarray.length()];

                    for (int j = 0; j < rollnoarray.length(); j++) {
                        rollno[j] = rollnoarray.get(j).toString();
                    }

                    JSONArray omarkarray = eduobj.getJSONArray("omark");

                    omark = new String[omarkarray.length()];

                    for (int j = 0; j < omarkarray.length(); j++) {
                        omark[j] = omarkarray.get(j).toString();
                    }

                    JSONArray tmarkarray = eduobj.getJSONArray("tmark");

                    tmark = new String[tmarkarray.length()];

                    for (int j = 0; j < tmarkarray.length(); j++) {
                        tmark[j] = tmarkarray.get(j).toString();
                    }

                    JSONArray percentarray = eduobj.getJSONArray("percent");

                    percent = new String[percentarray.length()];

                    for (int j = 0; j < percentarray.length(); j++) {
                        percent[j] = percentarray.get(j).toString();
                    }

                    linksdata.add(new educationdetailDTO(exam, board, year, rollno, omark, tmark, percent));
                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }
*/


    public List<documentsDTO> Parseuploadfiles(JSONObject jsonObject)
    {
        String status="", error_code="", message="";
        JSONArray documentarray=null;
        String doc_alias = "",doc_type="",title="",number="",image="";

        int i,len=0;
        List<documentsDTO> linksdata = new ArrayList<documentsDTO>();

        try {

            AppSession.getInstance().SaveData(mContext, CommanString.STATUS, "");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext, CommanString.STATUS, status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message = message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE = error_code;
            }

            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONArray json_array = jsonObject.getJSONArray("data");

                for ( i = 0; i< json_array.length(); i++)
                {

                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if (jsonObject1.has("doc_alias"))
                    {
                        doc_alias = jsonObject1.getString("doc_alias");
                    }
                    if (jsonObject1.has("doc_type"))
                    {
                        doc_type = jsonObject1.getString("doc_type");
                    }
                    if (jsonObject1.has("title"))
                    {
                        title = jsonObject1.getString("title");
                    }
                    if (jsonObject1.has("number"))
                    {
                        number = jsonObject1.getString("number");
                    }
                    if (jsonObject1.has("image"))
                    {
                        image = jsonObject1.getString("image");
                    }


                    linksdata.add(new documentsDTO(doc_alias, doc_type, title, number, image));
                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }

    public List<documentsDTO> Parsedocsingle(JSONObject jsonObject)
    {
        String status="", error_code="", message="";
        JSONArray documentarray=null;
        String doc_alias = "",doc_type="",title="",number="",image="";

        int i,len=0;
        List<documentsDTO> linksdata = new ArrayList<documentsDTO>();

        try {

            AppSession.getInstance().SaveData(mContext, CommanString.STATUS, "");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext, CommanString.STATUS, status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message = message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE = error_code;
            }

            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    if (jsonObject1.has("doc_alias"))
                    {
                        doc_alias = jsonObject1.getString("doc_alias");
                    }
                    if (jsonObject1.has("doc_type"))
                    {
                        doc_type = jsonObject1.getString("doc_type");
                    }
                    if (jsonObject1.has("title"))
                    {
                        title = jsonObject1.getString("title");
                    }
                    if (jsonObject1.has("number"))
                    {
                        number = jsonObject1.getString("number");
                    }
                    if (jsonObject1.has("image"))
                    {
                        image = jsonObject1.getString("image");
                    }

                    linksdata.add(new documentsDTO(doc_alias, doc_type, title, number, image));
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }


    public List<dashboardDTO> Parsedashboard(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String total_deposit = "",total_refund = "",all_jobs_count="",total_withdrawal="",total_balance="" ,total_applied_jobs="",total_prefered_jobs="";
        String order_id="",job_id="",community="",application_fee="",transaction_fee="",service_fee="",total_fee="",order_status="",order_date="",registration_number="",transaction_number="",article_id="",
                article_title = "",article_alias="",job_website_link="",department_title="",department_alias="",lastdate = "",qualification = "",total_vacancy="",location_title="",location_alias="",board_title="",board_alias="",post_title="",post_alias="",qualification_title="",qualification_alias="",category_alias="",applied="",checkExpiry="" ;

        int i;
        List<dashboardDTO> linksdata = new ArrayList<dashboardDTO>();

        CommanString.appliedjobdata.clear();
        CommanString.prefjobdata.clear();
        AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

        try {


            JSONObject dataobject=null;

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                dataobject = jsonObject.getJSONObject("data");

                if (dataobject.has("all_jobs_count"))
                {
                    all_jobs_count = dataobject.getString("all_jobs_count");
                }
                if (dataobject.has("total_applied_jobs"))
                {
                    total_applied_jobs = dataobject.getString("total_applied_jobs");
                }
                if (dataobject.has("total_prefered_jobs"))
                {
                    total_prefered_jobs = dataobject.getString("total_prefered_jobs");
                }

                JSONArray wp = dataobject.getJSONArray("wallet_balance");

                JSONObject wltaobj = new JSONObject(wp.getString(0));

                if (wltaobj.has("total_deposited_amt"))
                {
                    if (wltaobj.getString("total_deposited_amt").equalsIgnoreCase("") || wltaobj.getString("total_deposited_amt").equalsIgnoreCase("null") || wltaobj.getString("total_deposited_amt")==null)
                    {
                        total_balance = "0";
                    }
                    else
                    {
                        total_balance = wltaobj.getString("total_deposited_amt");
                    }
                }

                linksdata.add(new dashboardDTO(total_deposit, total_refund, total_withdrawal, total_balance, total_applied_jobs, total_prefered_jobs,all_jobs_count));


                if (dataobject.has("prefered_jobs"))
                {
                    if (dataobject.getString("prefered_jobs").equalsIgnoreCase("false") || dataobject.getString("prefered_jobs").equalsIgnoreCase("") ) {

                    }
                    else
                    {

                        JSONArray json_array = dataobject.getJSONArray("prefered_jobs");

                        for (i = 0; i < json_array.length(); i++) {

                            JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                            if (jsonObject1.has("category_alias")) {
                                category_alias = jsonObject1.getString("category_alias");
                            }
                            if (jsonObject1.has("article_id")) {
                                article_id = jsonObject1.getString("article_id");
                            }
                            if (jsonObject1.has("article_title")) {
                                article_title = jsonObject1.getString("article_title");
                            }
                            if (jsonObject1.has("article_alias")) {
                                article_alias = jsonObject1.getString("article_alias");
                            }
                            if (jsonObject1.has("submission_last_date")) {
                                lastdate = jsonObject1.getString("submission_last_date");
                            }
                            if (jsonObject1.has("total_vacancy")) {
                                total_vacancy = jsonObject1.getString("total_vacancy");
                            }
                            if (jsonObject1.has("job_website_link")) {
                                job_website_link = jsonObject1.getString("job_website_link");
                            }
                            if (jsonObject1.has("department_title")) {
                                department_title = jsonObject1.getString("department_title");
                            }
                            if (jsonObject1.has("department_alias")) {
                                department_alias = jsonObject1.getString("department_alias");
                            }
                            if (jsonObject1.has("location_title")) {
                                location_title = jsonObject1.getString("location_title");
                            }
                            if (jsonObject1.has("location_alias")) {
                                location_alias = jsonObject1.getString("location_alias");
                            }
                            if (jsonObject1.has("board_title")) {
                                board_title = jsonObject1.getString("board_title");
                            }
                            if (jsonObject1.has("board_alias")) {
                                board_alias = jsonObject1.getString("board_alias");
                            }
                            if (jsonObject1.has("post_title")) {
                                post_title = jsonObject1.getString("post_title");
                            }
                            if (jsonObject1.has("post_alias")) {
                                post_alias = jsonObject1.getString("post_alias");
                            }
                            if (jsonObject1.has("qualification_title")) {

                                if (jsonObject1.has("qualification_title")) {
                                    if (!jsonObject1.getString("qualification_title").equalsIgnoreCase("null")) {
                                        qualification_title = jsonObject1.getString("qualification_title");
                                    } else {
                                        qualification_title = "N/A";
                                    }
                                }
                            }
                            if (jsonObject1.has("qualification_alias")) {
                                qualification_alias = jsonObject1.getString("qualification_alias");
                            }
                            if (jsonObject1.has("checkExpiry")) {
                                checkExpiry = jsonObject1.getString("checkExpiry");
                            }
                            if (jsonObject1.has("applied")) {
                                applied = jsonObject1.getString("applied");
                            }
                            CommanString.prefjobdata.add(new prefereddashDTO(article_id, category_alias, article_title, article_alias, lastdate, total_vacancy, job_website_link, department_title, department_alias, location_title, location_alias, board_title, board_alias, post_title, post_alias, qualification_title, qualification_alias, checkExpiry, applied));

                        }
                    }
                }

                if (dataobject.has("applied_jobs")) {

                    JSONArray json_array = dataobject.getJSONArray("applied_jobs");

                    for (i = 0; i < json_array.length(); i++) {

                        JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                        if (jsonObject1.has("order_id")) {
                            order_id = jsonObject1.getString("order_id");
                            AppSession.getInstance().SaveData(mContext,CommanString.ORDERID,order_id);

                        }
                        if (jsonObject1.has("job_id")) {
                            job_id = jsonObject1.getString("job_id");
                        }
                        if (jsonObject1.has("community")) {
                            community = jsonObject1.getString("community");
                        }
                        if (jsonObject1.has("application_fee")) {
                            application_fee = jsonObject1.getString("application_fee");
                        }
                        if (jsonObject1.has("transaction_fee")) {
                            transaction_fee = jsonObject1.getString("transaction_fee");
                        }
                        if (jsonObject1.has("service_fee")) {
                            service_fee = jsonObject1.getString("service_fee");
                        }
                        if (jsonObject1.has("total_fee")) {
                            total_fee = jsonObject1.getString("total_fee");
                        }
                        if (jsonObject1.has("order_status")) {
                            order_status = jsonObject1.getString("order_status");
                        }
                        if (jsonObject1.has("order_date")) {
                            order_date = jsonObject1.getString("order_date");
                        }
                        if (jsonObject1.has("registration_number")) {
                            registration_number = jsonObject1.getString("registration_number");
                        }
                        if (jsonObject1.has("transaction_number")) {
                            transaction_number = jsonObject1.getString("transaction_number");
                        }
                        if (jsonObject1.has("article_id")) {
                            article_id = jsonObject1.getString("article_id");
                        }
                        if (jsonObject1.has("article_title")) {
                            article_title = jsonObject1.getString("article_title");
                        }
                        if (jsonObject1.has("article_alias")) {
                            article_alias = jsonObject1.getString("article_alias");
                        }
                        if (jsonObject1.has("submission_last_date")) {
                            lastdate = jsonObject1.getString("submission_last_date");
                        }
                        if (jsonObject1.has("total_vacancy")) {
                            total_vacancy = jsonObject1.getString("total_vacancy");
                        }
                        if (jsonObject1.has("job_website_link")) {
                            job_website_link = jsonObject1.getString("job_website_link");
                        }
                        if (jsonObject1.has("department_title")) {
                            department_title = jsonObject1.getString("department_title");
                        }
                        if (jsonObject1.has("department_alias")) {
                            department_alias = jsonObject1.getString("department_alias");
                        }
                        if (jsonObject1.has("location_title")) {
                            location_title = jsonObject1.getString("location_title");
                        }
                        if (jsonObject1.has("location_alias")) {
                            location_alias = jsonObject1.getString("location_alias");
                        }
                        if (jsonObject1.has("board_title")) {
                            board_title = jsonObject1.getString("board_title");
                        }
                        if (jsonObject1.has("board_alias")) {
                            board_alias = jsonObject1.getString("board_alias");
                        }
                        if (jsonObject1.has("post_title")) {
                            post_title = jsonObject1.getString("post_title");
                        }
                        if (jsonObject1.has("post_alias")) {
                            post_alias = jsonObject1.getString("post_alias");
                        }
                        if (jsonObject1.has("qualification_title")) {
                            qualification_title = jsonObject1.getString("qualification_title");
                        }
                        if (jsonObject1.has("qualification_alias")) {
                            qualification_alias = jsonObject1.getString("qualification_alias");
                        }
                        if (jsonObject1.has("checkExpiry")) {
                            checkExpiry = jsonObject1.getString("checkExpiry");
                        }
                        CommanString.appliedjobdata.add(new appliedjobDTO(order_id, job_id, community, application_fee, transaction_fee, service_fee, total_fee,
                                order_status, order_date, registration_number, transaction_number, article_id, category_alias, article_title, article_alias, lastdate, total_vacancy, job_website_link, department_title, department_alias, location_title, location_alias, board_title, board_alias, post_title, post_alias, qualification_title, qualification_alias, checkExpiry));

                    }
                }

            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }


    public List<preferedjobDTO> Parsepreferedjobs(JSONObject jsonObject)
    {
        String status="", error_code="", message="";
        String[] articleKeywords=null,department=null,location=null,qualification=null;

        String article_id="", article_title = "",article_alias="",job_website_link="",department_title="",department_alias="",lastdate = "",total_vacancy="",location_title="",location_alias="",board_title="",board_alias="",post_title="",post_alias="",qualification_title="",qualification_alias="",category_alias="",checkExpiry="" ;

        int i;
        List<preferedjobDTO> linksdata = new ArrayList<preferedjobDTO>();

        try {

            JSONObject dataobject = null;
            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");
            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                dataobject = jsonObject.getJSONObject("data");

                if (dataobject.has("articleKeywords"))
                {

                    if (dataobject.get("articleKeywords") != null && !dataobject.get("articleKeywords").toString().equalsIgnoreCase("null") && !dataobject.get("articleKeywords").toString().equalsIgnoreCase("false") && !dataobject.get("articleKeywords").toString().equalsIgnoreCase(""))
                    {
                        JSONArray examArray = dataobject.getJSONArray("articleKeywords");

                        articleKeywords = new String[examArray.length()];

                        for (int j = 0; j < examArray.length(); j++) {
                            articleKeywords[j] = examArray.get(j).toString();
                        }
                    }

                }


                if (dataobject.has("location"))
                {

                    if (dataobject.get("location") != null && !dataobject.get("location").toString().equalsIgnoreCase("null") && !dataobject.get("location").toString().equalsIgnoreCase(""))
                    {

                        JSONArray locationArray = dataobject.getJSONArray("location");

                        location = new String[locationArray.length()];

                        for (int j = 0; j < locationArray.length(); j++) {
                            location[j] = locationArray.get(j).toString();
                        }
                    }
                }

                if (dataobject.has("department"))
                {
                    if (dataobject.get("department") != null && !dataobject.get("department").toString().equalsIgnoreCase("null") && !dataobject.get("department").toString().equalsIgnoreCase(""))
                    {
                        JSONArray departmentArray = dataobject.getJSONArray("department");

                        department = new String[departmentArray.length()];

                        for (int j = 0; j < departmentArray.length(); j++) {
                            department[j] = departmentArray.get(j).toString();
                        }
                    }


                }


                if (dataobject.has("qualification"))
                {

                    if (dataobject.get("qualification") != null && !dataobject.get("qualification").toString().equalsIgnoreCase("null") && !dataobject.get("qualification").toString().equalsIgnoreCase(""))
                    {
                        JSONArray qualificationArray = dataobject.getJSONArray("qualification");

                        qualification = new String[qualificationArray.length()];

                        for (int j = 0; j < qualificationArray.length(); j++) {
                            qualification[j] = qualificationArray.get(j).toString();
                        }
                    }
                }


                if (dataobject.has("prefered_jobs")) {

                    if (dataobject.get("prefered_jobs") != null && !dataobject.get("prefered_jobs").toString().equalsIgnoreCase("null") && !dataobject.get("prefered_jobs").toString().equalsIgnoreCase("false") && !dataobject.get("prefered_jobs").toString().equalsIgnoreCase("")) {

                        JSONArray json_array = dataobject.getJSONArray("prefered_jobs");

                        if (json_array.length() > 0) {
                            for (i = 0; i < json_array.length(); i++) {

                                JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                                if (jsonObject1.has("category_alias")) {
                                    category_alias = jsonObject1.getString("category_alias");
                                }
                                if (jsonObject1.has("article_id")) {
                                    article_id = jsonObject1.getString("article_id");
                                }
                                if (jsonObject1.has("article_title")) {
                                    article_title = jsonObject1.getString("article_title");
                                }
                                if (jsonObject1.has("article_alias")) {
                                    article_alias = jsonObject1.getString("article_alias");
                                }
                                if (jsonObject1.has("submission_last_date")) {
                                    lastdate = jsonObject1.getString("submission_last_date");
                                }
                                if (jsonObject1.has("total_vacancy")) {
                                    total_vacancy = jsonObject1.getString("total_vacancy");
                                }
                                if (jsonObject1.has("job_website_link")) {
                                    job_website_link = jsonObject1.getString("job_website_link");
                                }
                                if (jsonObject1.has("department_title")) {
                                    department_title = jsonObject1.getString("department_title");
                                }
                                if (jsonObject1.has("department_alias")) {
                                    department_alias = jsonObject1.getString("department_alias");
                                }
                                if (jsonObject1.has("location_title")) {
                                    location_title = jsonObject1.getString("location_title");
                                }
                                if (jsonObject1.has("location_alias")) {
                                    location_alias = jsonObject1.getString("location_alias");
                                }
                                if (jsonObject1.has("board_title")) {
                                    board_title = jsonObject1.getString("board_title");
                                }
                                if (jsonObject1.has("board_alias")) {
                                    board_alias = jsonObject1.getString("board_alias");
                                }
                                if (jsonObject1.has("post_title")) {
                                    post_title = jsonObject1.getString("post_title");
                                }
                                if (jsonObject1.has("post_alias")) {
                                    post_alias = jsonObject1.getString("post_alias");
                                }
                                if (jsonObject1.has("qualification_title")) {
                                    if (!jsonObject1.getString("qualification_title").equalsIgnoreCase("null")) {
                                        qualification_title = jsonObject1.getString("qualification_title");
                                    } else {
                                        qualification_title = "N/A";
                                    }
                                }
                                if (jsonObject1.has("qualification_alias")) {
                                    qualification_alias = jsonObject1.getString("qualification_alias");
                                }
                                if (jsonObject1.has("checkExpiry")) {
                                    checkExpiry = jsonObject1.getString("checkExpiry");
                                }

                                linksdata.add(new preferedjobDTO(articleKeywords, department, location, qualification, article_id, category_alias, article_title, article_alias, lastdate, total_vacancy, job_website_link, department_title, department_alias, location_title, location_alias, board_title, board_alias, post_title, post_alias, qualification_title, qualification_alias, checkExpiry));

                            }

                        } else {
                            linksdata.add(new preferedjobDTO(articleKeywords, department, location, qualification, article_id, category_alias, article_title, article_alias, lastdate, total_vacancy, job_website_link, department_title, department_alias, location_title, location_alias, board_title, board_alias, post_title, post_alias, qualification_title, qualification_alias, checkExpiry));
                        }

                    }
                } else {
                    linksdata.add(new preferedjobDTO(articleKeywords, department, location, qualification, article_id, category_alias, article_title, article_alias, lastdate, total_vacancy, job_website_link, department_title, department_alias, location_title, location_alias, board_title, board_alias, post_title, post_alias, qualification_title, qualification_alias, checkExpiry));
                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }


    public List<articleKeywordsDTO> Parseallfilter(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String keyword_id = "",keyword_title = "",keyword_alias="",parent_id="";

        String department_id = "",department_title = "",department_alias="";

        String location_id = "",location_title = "",location_alias="";

        String qualification_id = "",qualification_title = "",qualification_alias="";

        CommanString.keyworddata.clear();
        CommanString.qualificationdata.clear();
        CommanString.locationdata.clear();
        CommanString.departmentdata.clear();

        int i;

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
                if (status.equalsIgnoreCase("1") && jsonObject.has("data"))
                {

                    JSONObject dataobject = jsonObject.getJSONObject("data");

                JSONArray json_array = dataobject.getJSONArray("allArticleKeywords");

                for ( i = 0; i< json_array.length(); i++)
                {
                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if (jsonObject1.has("keyword_id"))
                    {
                        keyword_id = jsonObject1.getString("keyword_id");
                    }
                    if (jsonObject1.has("keyword_title")) {
                        keyword_title = jsonObject1.getString("keyword_title");
                    }
                    if (jsonObject1.has("keyword_alias")) {
                        keyword_alias = jsonObject1.getString("keyword_alias");
                    }
                    if (jsonObject1.has("parent_id")) {
                        parent_id = jsonObject1.getString("parent_id");
                    }

                    CommanString.keyworddata.add(new articleKeywordsDTO(keyword_id,keyword_title,keyword_alias,parent_id));

                }

                    JSONArray allDepartments = dataobject.getJSONArray("allDepartments");

                    for ( i = 0; i< allDepartments.length(); i++)
                    {
                        JSONObject jsonObject1 = new JSONObject(allDepartments.getString(i));

                        if(jsonObject1.has("department_id"))
                        {
                            department_id = jsonObject1.getString("department_id");
                        }
                        if(jsonObject1.has("department_title"))
                        {
                            department_title = jsonObject1.getString("department_title");
                        }
                        if(jsonObject1.has("department_alias"))
                        {
                            department_alias = jsonObject1.getString("department_alias");
                        }

                        CommanString.departmentdata.add(new allDepartmentsDTO(department_id,department_title,department_alias));

                    }


                    JSONArray allJobLocations = dataobject.getJSONArray("allJobLocations");

                    for ( i = 0; i< allJobLocations.length(); i++)
                    {
                        JSONObject jsonObject1 = new JSONObject(allJobLocations.getString(i));

                        if(jsonObject1.has("location_id"))
                        {
                            location_id = jsonObject1.getString("location_id");
                        }
                        if(jsonObject1.has("location_title"))
                        {
                            location_title = jsonObject1.getString("location_title");
                        }
                        if(jsonObject1.has("location_alias"))
                        {
                            location_alias = jsonObject1.getString("location_alias");
                        }

                        CommanString.locationdata.add(new allJobLocationsDTO(location_id,location_title,location_alias));

                    }

                    JSONArray allQualification = dataobject.getJSONArray("allQualification");

                    for ( i = 0; i< allQualification.length(); i++)
                    {
                        JSONObject jsonObject1 = new JSONObject(allQualification.getString(i));

                        if(jsonObject1.has("qualification_id"))
                        {
                            qualification_id = jsonObject1.getString("qualification_id");
                        }
                        if(jsonObject1.has("qualification_title"))
                        {
                            qualification_title = jsonObject1.getString("qualification_title");
                        }
                        if(jsonObject1.has("qualification_alias"))
                        {
                            qualification_alias = jsonObject1.getString("qualification_alias");
                        }

                        CommanString.qualificationdata.add(new allQualificationDTO(qualification_id,qualification_title,qualification_alias));

                    }

            }
                else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
                {
                    Checkuser alert = new Checkuser();
                    alert.showDialog(mContext, CommanString.Message);
                }
                else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
                {
                    Checkuser alert = new Checkuser();
                    alert.showDialog(mContext, CommanString.Message);
                }
                else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
                {
                    Sendmessage alert = new Sendmessage();
                    alert.showDialog(mContext, CommanString.Message);
                }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return CommanString.keyworddata;
    }


    public List<topcoachingDTO> Parsetopcoaching(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String coaching_id="",coaching_name="",address = "",contact_number1="",contact_number2="",email="",website="",logo = "",rating = "",total_city="",total_course="",total_rate="";
        String total_coachings="";
        int i;
        List<topcoachingDTO> linksdata = new ArrayList<topcoachingDTO>();
        AppSession.getInstance().SaveData(mContext,CommanString.TOTAL_COACHING,"");

        try {

            JSONObject dataobject = null;
            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data")) {
                dataobject = jsonObject.getJSONObject("data");

                if (dataobject.has("total_coachings")) {
                    total_coachings = dataobject.getString("total_coachings");
                    AppSession.getInstance().SaveData(mContext, CommanString.TOTAL_COACHING, total_coachings);
                }

                if (dataobject.has("coaching_center")) {

                    JSONArray json_array = dataobject.getJSONArray("coaching_center");

                    for (i = 0; i < json_array.length(); i++) {

                        JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                        if (jsonObject1.has("coaching_id")) {
                            coaching_id = jsonObject1.getString("coaching_id");
                        }
                        if (jsonObject1.has("coaching_name")) {
                            coaching_name = jsonObject1.getString("coaching_name");
                        }
                        if (jsonObject1.has("address")) {
                            address = jsonObject1.getString("address");
                        }
                        if (jsonObject1.has("contact_number1")) {
                            contact_number1 = jsonObject1.getString("contact_number1");
                        }
                        if (jsonObject1.has("contact_number2")) {
                            contact_number2 = jsonObject1.getString("contact_number2");
                        }
                        if (jsonObject1.has("email")) {
                            email = jsonObject1.getString("email");
                        }
                        if (jsonObject1.has("website")) {
                            website = jsonObject1.getString("website");
                        }
                        if (jsonObject1.has("logo")) {
                            logo = jsonObject1.getString("logo");
                        }
                        if (jsonObject1.has("rating")) {
                            rating = jsonObject1.getString("rating");
                        }
                        if (jsonObject1.has("total_city")) {
                            total_city = jsonObject1.getString("total_city");
                        }
                        if (jsonObject1.has("total_course")) {
                            total_course = jsonObject1.getString("total_course");
                        }
                        if (jsonObject1.has("total_rate")) {
                            total_rate = jsonObject1.getString("total_rate");
                        }

                        linksdata.add(new topcoachingDTO(coaching_id, coaching_name, address, contact_number1, contact_number2, email, website
                                , logo, rating, total_city, total_course, total_rate));

                    }
                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }


    public List<topcoachingDTO> Parsesearchcoaching(String jsonResponse)
    {
        String status="", error_code="", message="";

        String coaching_id="",coaching_name="",address = "",contact_number1="",contact_number2="",email="",website="",logo = "",rating = "",total_city="",total_course="",total_rate="";
        AppSession.getInstance().SaveData(mContext,CommanString.TOTAL_COACHING,"");
        int i;
        List<topcoachingDTO> linksdata = new ArrayList<topcoachingDTO>();

        try {

            JSONObject jsonObject = new JSONObject(jsonResponse);
            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equalsIgnoreCase("1") && jsonObject.has("data"))
            {

                JSONArray json_array = jsonObject.getJSONArray("data");

                for ( i = 0; i< json_array.length(); i++)
                {

                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if(jsonObject1.has("coaching_id"))
                    {
                        coaching_id = jsonObject1.getString("coaching_id");
                    }
                    if(jsonObject1.has("coaching_name"))
                    {
                        coaching_name = jsonObject1.getString("coaching_name");
                    }
                    if(jsonObject1.has("address"))
                    {
                        address = jsonObject1.getString("address");
                    }
                    if(jsonObject1.has("contact_number1"))
                    {
                        contact_number1 = jsonObject1.getString("contact_number1");
                    }
                    if(jsonObject1.has("contact_number2"))
                    {
                        contact_number2 = jsonObject1.getString("contact_number2");
                    }
                    if(jsonObject1.has("email"))
                    {
                        email = jsonObject1.getString("email");
                    }
                    if(jsonObject1.has("website"))
                    {
                        website = jsonObject1.getString("website");
                    }
                    if(jsonObject1.has("logo"))
                    {
                        logo = jsonObject1.getString("logo");
                    }
                    if(jsonObject1.has("rating"))
                    {
                        rating = jsonObject1.getString("rating");
                    }
                    if(jsonObject1.has("total_city"))
                    {
                        total_city = jsonObject1.getString("total_city");
                    }
                    if(jsonObject1.has("total_course"))
                    {
                        total_course = jsonObject1.getString("total_course");
                    }
                    if(jsonObject1.has("total_rate"))
                    {
                        total_rate = jsonObject1.getString("total_rate");
                    }

                    linksdata.add(new topcoachingDTO(coaching_id,coaching_name,address,contact_number1,contact_number2,email,website
                            ,logo,rating,total_city,total_course,total_rate));

                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }



    public List<appliedjobDTO> Parseappliedjobs(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String total_deposit = "",total_refund = "",total_withdrawal="",total_balance="" ,total_applied_jobs="",total_prefered_jobs="";
        String order_id="",job_id="",community="",application_fee="",transaction_fee="",service_fee="",total_fee="",order_status="",order_date="",registration_number="",transaction_number="",article_id="",
                article_title = "",article_alias="",job_website_link="",department_title="",department_alias="",lastdate = "",qualification = "",total_vacancy="",location_title="",location_alias="",board_title="",board_alias="",post_title="",post_alias="",qualification_title="",qualification_alias="",category_alias="",checkExpiry="" ;

        int i;
        List<appliedjobDTO> linksdata = new ArrayList<appliedjobDTO>();

        try {

            JSONObject dataobject = null;

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONArray json_array = jsonObject.getJSONArray("data");

                for ( i = 0; i< json_array.length(); i++)
                {

                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if (jsonObject1.has("order_id"))
                    {
                            order_id = jsonObject1.getString("order_id");
                            AppSession.getInstance().SaveData(mContext,CommanString.ORDERID,order_id);

                    }
                        if (jsonObject1.has("job_id")) {
                            job_id = jsonObject1.getString("job_id");
                        }
                        if (jsonObject1.has("community")) {
                            community = jsonObject1.getString("community");
                        }
                        if (jsonObject1.has("application_fee")) {
                            application_fee = jsonObject1.getString("application_fee");
                        }
                        if (jsonObject1.has("transaction_fee")) {
                            transaction_fee = jsonObject1.getString("transaction_fee");
                        }
                        if (jsonObject1.has("service_fee")) {
                            service_fee = jsonObject1.getString("service_fee");
                        }
                        if (jsonObject1.has("total_fee")) {
                            total_fee = jsonObject1.getString("total_fee");
                        }
                        if (jsonObject1.has("order_status")) {
                            order_status = jsonObject1.getString("order_status");
                        }
                        if (jsonObject1.has("order_date")) {
                            order_date = jsonObject1.getString("order_date");
                        }
                        if (jsonObject1.has("registration_number")) {
                            registration_number = jsonObject1.getString("registration_number");
                        }
                        if (jsonObject1.has("transaction_number")) {
                            transaction_number = jsonObject1.getString("transaction_number");
                        }
                        if (jsonObject1.has("article_id")) {
                            article_id = jsonObject1.getString("article_id");
                        }
                        if (jsonObject1.has("article_title")) {
                            article_title = jsonObject1.getString("article_title");
                        }
                        if (jsonObject1.has("article_alias")) {
                            article_alias = jsonObject1.getString("article_alias");
                        }
                        if (jsonObject1.has("submission_last_date")) {
                            lastdate = jsonObject1.getString("submission_last_date");
                        }
                        if (jsonObject1.has("total_vacancy")) {
                            total_vacancy = jsonObject1.getString("total_vacancy");
                        }
                        if (jsonObject1.has("job_website_link"))
                        {
                            job_website_link = jsonObject1.getString("job_website_link");
                        }
                        if (jsonObject1.has("department_title"))
                        {
                            department_title = jsonObject1.getString("department_title");
                        }
                        if (jsonObject1.has("department_alias"))
                        {
                            department_alias = jsonObject1.getString("department_alias");
                        }
                        if (jsonObject1.has("location_title")) {
                            location_title = jsonObject1.getString("location_title");
                        }
                        if (jsonObject1.has("location_alias")) {
                            location_alias = jsonObject1.getString("location_alias");
                        }
                        if (jsonObject1.has("board_title")) {
                            board_title = jsonObject1.getString("board_title");
                        }
                        if (jsonObject1.has("board_alias")) {
                            board_alias = jsonObject1.getString("board_alias");
                        }
                        if (jsonObject1.has("post_title")) {
                            post_title = jsonObject1.getString("post_title");
                        }
                        if (jsonObject1.has("post_alias")) {
                            post_alias = jsonObject1.getString("post_alias");
                        }
                        if (jsonObject1.has("qualification_title")) {
                            qualification_title = jsonObject1.getString("qualification_title");
                        }
                        if (jsonObject1.has("qualification_alias")) {
                            qualification_alias = jsonObject1.getString("qualification_alias");
                        }
                        if (jsonObject1.has("checkExpiry"))
                        {
                            checkExpiry = jsonObject1.getString("checkExpiry");
                        }
                    linksdata.add(new appliedjobDTO(order_id, job_id, community, application_fee, transaction_fee, service_fee, total_fee,
                                order_status, order_date, registration_number, transaction_number, article_id, category_alias, article_title, article_alias, lastdate, total_vacancy, job_website_link, department_title, department_alias, location_title, location_alias, board_title, board_alias, post_title, post_alias, qualification_title, qualification_alias, checkExpiry));

                    }
                }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

            }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return linksdata;
    }


    public List<appliedjobstatusDTO> Parseappliedjobsstatus(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String order_id="",job_id="",community="",application_fee="",transaction_fee="",tax="",service_fee="",total_fee="",payment_status="",order_status="",order_date="",registration_number="",transaction_number="",
                article_title = "" ;
        String[] action_name=null,action_date=null,operator_id=null,action_id=null;

        int i;
        List<appliedjobstatusDTO> linksdata = new ArrayList<appliedjobstatusDTO>();

        try
        {
            JSONObject dataobject = null;

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    if (jsonObject1.has("order_id"))
                    {
                        order_id = jsonObject1.getString("order_id");
                        AppSession.getInstance().SaveData(mContext,CommanString.ORDERID,order_id);
                    }
                    if (jsonObject1.has("job_id"))
                    {
                        job_id = jsonObject1.getString("job_id");
                    }
                    if (jsonObject1.has("community"))
                    {
                        community = jsonObject1.getString("community");
                    }
                    if (jsonObject1.has("application_fee"))
                    {
                        application_fee = jsonObject1.getString("application_fee");
                    }
                    if (jsonObject1.has("transaction_fee"))
                    {
                        transaction_fee = jsonObject1.getString("transaction_fee");
                    }
                    if (jsonObject1.has("service_fee"))
                    {
                        service_fee = jsonObject1.getString("service_fee");
                    }
                     if (jsonObject1.has("tax"))
                     {
                        tax = jsonObject1.getString("tax");
                     }
                    if (jsonObject1.has("total"))
                    {
                        total_fee = jsonObject1.getString("total");
                    }
                    if (jsonObject1.has("order_status"))
                    {
                        order_status = jsonObject1.getString("order_status");
                    }
                if (jsonObject1.has("payment_status"))
                {
                    payment_status = jsonObject1.getString("payment_status");
                }
                    if (jsonObject1.has("order_date"))
                    {
                        order_date = jsonObject1.getString("order_date");
                    }
                    if (jsonObject1.has("registration_number"))
                    {
                        registration_number = jsonObject1.getString("registration_number");
                    }
                    if (jsonObject1.has("transaction_number"))
                    {
                        transaction_number = jsonObject1.getString("transaction_number");
                    }
                    if (jsonObject1.has("article_title"))
                    {
                        article_title = jsonObject1.getString("article_title");
                    }

                   JSONArray json_array1=null;

                    if (jsonObject1.has("action_data"))
                    {
                        json_array1 = jsonObject1.getJSONArray("action_data");
                    }

                    if (json_array1!=null && json_array1.length()>0)
                    {

                        action_id=new String[json_array1.length()];
                        operator_id=new String[json_array1.length()];
                        action_name=new String[json_array1.length()];
                        action_date=new String[json_array1.length()];

                        for (i = 0; i < json_array1.length(); i++)
                        {
                            JSONObject actionObject = new JSONObject(json_array1.getString(i));

                            if (actionObject.has("action_id"))
                            {
                                action_id[i] = actionObject.getString("action_id");
                            }
                            if (actionObject.has("operator_id"))
                            {
                                operator_id[i] = actionObject.getString("operator_id");
                            }
                            if (actionObject.has("action_name"))
                            {
                                action_name[i] = actionObject.getString("action_name");
                            }
                            if (actionObject.has("action_date"))
                            {
                                action_date[i] = actionObject.getString("action_date");
                            }

                        }
                    }
                else
                    {
                        action_id=new String[4];
                        operator_id=new String[4];
                        action_name=new String[4];
                        action_date=new String[4];

                        for (i = 0; i < 4; i++)
                        {
                            action_id[i] = "";
                            operator_id[i] = "";
                            action_name[i] = "";
                            action_date[i] = "";

                        }
                    }
                        linksdata.add(new appliedjobstatusDTO(order_id, job_id, community, application_fee, transaction_fee, service_fee,tax, total_fee,
                            order_status, payment_status,order_date, registration_number, transaction_number,article_title, action_date,action_name,operator_id,action_id));

            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return linksdata;
    }


    public List<referDTO> Parserefer(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String total_refer = "",refercode="",referral_rules="",total_earning="",referral_url="",point_id[]=null,no_of_referral[]=null,no_of_points[]=null ;

        List<referDTO> linksdata = new ArrayList<referDTO>();

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equalsIgnoreCase("1") && jsonObject.has("data"))
            {
                    JSONObject jsonObject1= jsonObject.getJSONObject("data");

                    if (jsonObject1.has("total_referred"))
                    {
                        total_refer = jsonObject1.getString("total_referred");
                    }
                    if (jsonObject1.has("referral_code"))
                    {
                        refercode = jsonObject1.getString("referral_code");
                    }
                    if (jsonObject1.has("referral_rules"))
                    {
                        referral_rules = jsonObject1.getString("referral_rules");
                    }
                    if (jsonObject1.has("referral_url"))
                    {
                        referral_url = jsonObject1.getString("referral_url");
                    }
                if (jsonObject1.has("total_earning"))
            {
                total_earning = jsonObject1.getString("total_earning");
            }

                linksdata.add(new referDTO(total_refer,total_earning,refercode,referral_rules,referral_url));
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return linksdata;
    }


    public List<coursesDTO> Parsecourses(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String board_id = "",board_title = "",total_coaching="" ;

        int i;
        List<coursesDTO> linksdata = new ArrayList<coursesDTO>();

        try {

            JSONObject dataobject = null;

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONArray json_array = jsonObject.getJSONArray("data");

                for ( i = 0; i< json_array.length(); i++)
                {

                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if (jsonObject1.has("board_id"))
                    {
                        board_id = jsonObject1.getString("board_id");
                    }
                    if (jsonObject1.has("board_title"))
                    {
                        board_title = jsonObject1.getString("board_title");
                    }
                    if (jsonObject1.has("total_coaching"))
                    {
                        total_coaching = jsonObject1.getString("total_coaching");
                    }
                    linksdata.add(new coursesDTO(board_id, board_title, total_coaching));

                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return linksdata;
    }

    public List<admissionDTO> Parseadmission(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String board_id = "",board_title = "",total_coaching="" ;

        int i;
        List<admissionDTO> linksdata = new ArrayList<admissionDTO>();

        try {

            JSONObject dataobject = null;

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }

            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONArray json_array = jsonObject.getJSONArray("data");

                for ( i = 0; i< json_array.length(); i++)
                {

                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if (jsonObject1.has("board_id"))
                    {
                        board_id = jsonObject1.getString("board_id");
                    }
                    if (jsonObject1.has("board_title"))
                    {
                        board_title = jsonObject1.getString("board_title");
                    }
                    if (jsonObject1.has("total_coaching"))
                    {
                        total_coaching = jsonObject1.getString("total_coaching");
                    }
                    linksdata.add(new admissionDTO(board_id, board_title, total_coaching));

                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return linksdata;
    }

    public List<cityDTO> Parsecity(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String cityId = "",city = "",total_coaching="" ;

        int i;
        List<cityDTO> linksdata = new ArrayList<cityDTO>();

        try {

            JSONObject dataobject = null;

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }

            if (status.equals("1") &&  jsonObject.has("data"))
            {
                JSONArray json_array = jsonObject.getJSONArray("data");

                for ( i = 0; i< json_array.length(); i++)
                {

                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if (jsonObject1.has("cityId"))
                    {
                        cityId = jsonObject1.getString("cityId");
                    }
                    if (jsonObject1.has("city"))
                    {
                        city = jsonObject1.getString("city");
                    }
                    if (jsonObject1.has("total_coaching"))
                    {
                        total_coaching = jsonObject1.getString("total_coaching");
                    }
                    linksdata.add(new cityDTO(cityId, city, total_coaching));

                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return linksdata;
    }



    public List<walletDTO> Parsewallet(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String total_deposit = "",total_refund = "",total_withdrawal="",total_balance="",total_referral="";

        int i;
        List<walletDTO> linksdata = new ArrayList<walletDTO>();

        try {

                JSONObject dataobject=null;

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
                {
                    dataobject =jsonObject.getJSONObject("data");

                    JSONObject wp= dataobject.getJSONObject("wallet");

                    if(wp.has("Deposit"))
                    {
                        JSONObject wp1= wp.getJSONObject("Deposit");
                        if (wp1.has("value"))
                        {
                            total_deposit = wp1.getString("value");
                        }

                    }

                    if(wp.has("Refund"))
                    {
                        JSONObject wp1= wp.getJSONObject("Refund");
                        if (wp1.has("value"))
                        {
                            total_refund = wp1.getString("value");
                        }
                    }

                    if(wp.has("Withdrawal"))
                    {
                        JSONObject wp1= wp.getJSONObject("Withdrawal");
                        if (wp1.has("value"))
                        {
                            String string = wp1.getString("value");

                            if (string==null || string.equalsIgnoreCase("null") || string.equalsIgnoreCase("0") || string.equalsIgnoreCase("0.00"))
                            {
                                total_withdrawal = wp1.getString("value");
                            }
                            else
                            {
                                String[] parts = string.split("-");
                                String part1 = parts[0]; // 004
                                String part2 = parts[1];
                                total_withdrawal = part2;
                            }
                        }

                    }
                    if(wp.has("Available"))
                    {
                        JSONObject wp1= wp.getJSONObject("Available");
                        if (wp1.has("value"))
                        {
                            total_balance = wp1.getString("value");
                        }
                    }
                    if(wp.has("Referral"))
                    {
                        JSONObject wp1= wp.getJSONObject("Referral");
                        if (wp1.has("value"))
                        {
                            total_referral = wp1.getString("value");
                        }
                    }

                    linksdata.add(new walletDTO(total_deposit,total_refund,total_withdrawal,total_balance,total_referral));
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return linksdata;
    }


    public List<walletdepositdetailDTO> Parsewalletdeposit(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String name = "",value = "",extramessage="";

        int i;
        List<walletdepositdetailDTO> linksdata = new ArrayList<walletdepositdetailDTO>();

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }

            if (jsonObject.has("extra_info"))
            {
                extramessage = jsonObject.getString("extra_info");
            }

            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONArray json_array = jsonObject.getJSONArray("data");

                for ( i = 0; i< json_array.length(); i++)
                {
                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if (jsonObject1.has("label"))
                    {
                        name = jsonObject1.getString("label");
                    }

                    if (jsonObject1.has("value"))
                    {
                        value = jsonObject1.getString("value");
                    }

                    linksdata.add(new walletdepositdetailDTO(name, value, extramessage));
                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return linksdata;
    }



    public List<wallethistoryDTO> Parsewalletdata(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String pid="",DEPOSITAMT="",payment_type="",STATUS="",date="",payment_req_id="",ORDERID="",TXNAMOUNT="",TXNFEE="",CURRENCY="",TXNID="",GATEWAYNAME="",date_requested="",refund_status="",request_id="";

        int i;
        List<wallethistoryDTO> linksdata = new ArrayList<wallethistoryDTO>();

        try {

            JSONObject dataobject=null;

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                dataobject =jsonObject.getJSONObject("data");

                JSONArray json_array = dataobject.getJSONArray("wallet");

                for ( i = 0; i< json_array.length(); i++)
                {
                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if (jsonObject1.has("payment_id"))
                    {
                        pid = jsonObject1.getString("payment_id");
                    }
                    if (jsonObject1.has("payment_date"))
                    {
                        String datestring = jsonObject1.getString("payment_date");
                        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date dates = dt.parse(datestring);

                        SimpleDateFormat simpleDate = new SimpleDateFormat("dd MMM yyyy hh:mm aaa");
                        String strdt = simpleDate.format(dates);

                        date = strdt;
                    }
                    if (jsonObject1.has("payment_type"))
                    {
                        payment_type = jsonObject1.getString("payment_type");
                    }
                    if (jsonObject1.has("payment_req_id"))
                    {
                        payment_req_id = jsonObject1.getString("payment_req_id");
                    }
                    if (jsonObject1.has("ORDERID"))
                    {
                        ORDERID = jsonObject1.getString("ORDERID");
                    }
                    if (jsonObject1.has("TXNAMOUNT"))
                    {
                        TXNAMOUNT = jsonObject1.getString("TXNAMOUNT");
                    }
                    if (jsonObject1.has("TXNFEE"))
                    {
                        TXNFEE = jsonObject1.getString("TXNFEE");
                    }
                    if (jsonObject1.has("DEPOSITAMT"))
                    {
                        DEPOSITAMT = jsonObject1.getString("DEPOSITAMT");
                    }
                    if (jsonObject1.has("CURRENCY"))
                    {
                        CURRENCY = jsonObject1.getString("CURRENCY");
                    }
                    if (jsonObject1.has("TXNID"))
                    {
                        TXNID = jsonObject1.getString("TXNID");
                    }
                    if (jsonObject1.has("STATUS"))
                    {
                        STATUS = jsonObject1.getString("STATUS");
                    }
                    if (jsonObject1.has("GATEWAYNAME"))
                    {
                        GATEWAYNAME = jsonObject1.getString("GATEWAYNAME");
                    }
                    if (jsonObject1.has("request_id"))
                    {
                        request_id = jsonObject1.getString("request_id");
                    }
                    if (jsonObject1.has("refund_status"))
                    {
                        refund_status = jsonObject1.getString("refund_status");
                    }
                    if (jsonObject1.has("date_requested"))
                    {
                        String datestring = jsonObject1.getString("date_requested");
                        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date dates = dt.parse(datestring);

                        SimpleDateFormat simpleDate = new SimpleDateFormat("dd MMM yyyy hh:mm aaa");
                        String strdt = simpleDate.format(dates);

                        date_requested = strdt;
                    }

                    linksdata.add(new wallethistoryDTO(pid,DEPOSITAMT,payment_type,STATUS,date,payment_req_id,ORDERID,TXNAMOUNT,TXNFEE,CURRENCY,TXNID,GATEWAYNAME,request_id,refund_status,date_requested));
                }


            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return linksdata;
    }


    public List<wallethistoryDTO> Parsetransactiondata(JSONObject jsonObject)
    {

        String status="", error_code="", message="";

        String pid="",DEPOSITAMT="",payment_type="",STATUS="",date="",payment_req_id="",ORDERID="",TXNAMOUNT="",TXNFEE="",CURRENCY="",TXNID="",GATEWAYNAME="",date_requested="",refund_status="",request_id="";

        String activity_id = "",refund_request_id = "",user_id="",user_type="",comment="",date_created="";

        int i;
        List<wallethistoryDTO> linksdata = new ArrayList<wallethistoryDTO>();

        try {

            CommanString.refunddata.clear();
            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONObject dataObject = jsonObject.getJSONObject("data");

                JSONObject jsonObject1 = dataObject.getJSONObject("paymentData");

                if (jsonObject1.has("payment_id")) {
                    pid = jsonObject1.getString("payment_id");
                }
                if (jsonObject1.has("payment_date"))
                {
                    String datestring = jsonObject1.getString("payment_date");
                    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date dates = dt.parse(datestring);

                    SimpleDateFormat simpleDate = new SimpleDateFormat("dd MMM yyyy hh:mm aaa");
                    String strdt = simpleDate.format(dates);

                    date = strdt;
                }
                if (jsonObject1.has("payment_type")) {
                    payment_type = jsonObject1.getString("payment_type");
                }
                if (jsonObject1.has("payment_req_id")) {
                    payment_req_id = jsonObject1.getString("payment_req_id");
                }
                if (jsonObject1.has("ORDERID")) {
                    ORDERID = jsonObject1.getString("ORDERID");
                }
                if (jsonObject1.has("TXNAMOUNT")) {
                    TXNAMOUNT = jsonObject1.getString("TXNAMOUNT");
                }
                if (jsonObject1.has("TXNFEE")) {
                    TXNFEE = jsonObject1.getString("TXNFEE");
                }
                if (jsonObject1.has("DEPOSITAMT")) {
                    DEPOSITAMT = jsonObject1.getString("DEPOSITAMT");
                }
                if (jsonObject1.has("CURRENCY")) {
                    CURRENCY = jsonObject1.getString("CURRENCY");
                }
                if (jsonObject1.has("TXNID")) {
                    TXNID = jsonObject1.getString("TXNID");
                }
                if (jsonObject1.has("STATUS")) {
                    STATUS = jsonObject1.getString("STATUS");
                }
                if (jsonObject1.has("GATEWAYNAME")) {
                    GATEWAYNAME = jsonObject1.getString("GATEWAYNAME");
                }
                if (jsonObject1.has("request_id")) {
                    request_id = jsonObject1.getString("request_id");
                }
                if (jsonObject1.has("refund_status")) {
                    refund_status = jsonObject1.getString("refund_status");
                }
                if (jsonObject1.has("date_requested"))
                {

                    String datestring = jsonObject1.getString("date_requested");
                    if (datestring==null || datestring.equalsIgnoreCase("") || datestring.equalsIgnoreCase("null"))
                    {
                        date_requested="";
                    }
                    else
                    {
                        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date dates = dt.parse(datestring);

                        SimpleDateFormat simpleDate = new SimpleDateFormat("dd MMM yyyy hh:mm aaa");
                        String strdt = simpleDate.format(dates);

                        date_requested = strdt;
                    }
                }

                linksdata.add(new wallethistoryDTO(pid, DEPOSITAMT, payment_type, STATUS, date, payment_req_id, ORDERID, TXNAMOUNT, TXNFEE, CURRENCY, TXNID, GATEWAYNAME, request_id, refund_status, date_requested));

                if (dataObject.has("refundActivityData"))
                {
                    JSONArray json_array = dataObject.getJSONArray("refundActivityData");

                    if (json_array!=null && json_array.length()>0)
                    {
                        for (i = 0; i < json_array.length(); i++)
                        {
                            JSONObject refundobject = new JSONObject(json_array.getString(i));

                            if (refundobject.has("activity_id"))
                            {
                                activity_id = refundobject.getString("activity_id");
                            }
                            if (refundobject.has("request_id"))
                            {
                                refund_request_id = refundobject.getString("request_id");
                            }
                            if (refundobject.has("user_id"))
                            {
                                user_id = refundobject.getString("user_id");
                            }
                            if (refundobject.has("user_type"))
                            {
                                user_type = refundobject.getString("user_type");
                            }
                            if (refundobject.has("comment"))
                            {
                                comment = refundobject.getString("comment");
                            }
                            if (refundobject.has("date_created"))
                            {
                                String datestring = refundobject.getString("date_created");
                                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date dates = dt.parse(datestring);

                                SimpleDateFormat simpleDate = new SimpleDateFormat("dd MMM yyyy hh:mm aaa");
                                String strdt = simpleDate.format(dates);

                                date_created = strdt;
                            }

                            CommanString.refunddata.add(new refundActivityDTO(activity_id,refund_request_id,user_id,user_type,comment,date_created));

                        }
                    }
                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return linksdata;
    }


    public List<addressdetailDTO> Parseaddressdetail(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String address_detail_id="",address_line_1="",tehsil = "",district="",state="",pin_code="",same_as_above = ""
                ,p_address_line_1 = "",p_tehsil="",p_district="",p_state="",p_pin_code="",area_code="",phone_number=""
                ,alternative_mobile_number="",alternative_email="" ;

        int i;
        List<addressdetailDTO> linksdata = new ArrayList<addressdetailDTO>();


        try {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONObject data = jsonObject.getJSONObject("data");

                JSONObject jsonObject1 = data.getJSONObject("addressDetail");

                    if (jsonObject1.has("address_detail_id"))
                    {
                        address_detail_id = jsonObject1.getString("address_detail_id");
                    }
                    if (jsonObject1.has("address_line_1"))
                    {
                        address_line_1 = jsonObject1.getString("address_line_1");
                    }
                    if (jsonObject1.has("tehsil"))
                    {
                        tehsil = jsonObject1.getString("tehsil");
                    }
                    if (jsonObject1.has("district"))
                    {
                        district = jsonObject1.getString("district");
                    }
                    if (jsonObject1.has("state"))
                    {
                        state = jsonObject1.getString("state");
                    }
                    if (jsonObject1.has("pin_code"))
                    {
                        pin_code = jsonObject1.getString("pin_code");
                    }
                    if (jsonObject1.has("same_as_above"))
                    {
                        same_as_above = jsonObject1.getString("same_as_above");
                    }
                    if (jsonObject1.has("p_address_line_1"))
                    {
                        p_address_line_1 = jsonObject1.getString("p_address_line_1");
                    }
                    if (jsonObject1.has("p_tehsil"))
                    {
                        p_tehsil = jsonObject1.getString("p_tehsil");
                    }
                    if (jsonObject1.has("p_district"))
                    {
                        p_district = jsonObject1.getString("p_district");
                    }
                    if (jsonObject1.has("p_state"))
                    {
                        p_state = jsonObject1.getString("p_state");
                    }
                    if (jsonObject1.has("p_pin_code"))
                    {
                        p_pin_code = jsonObject1.getString("p_pin_code");
                    }
                    if (jsonObject1.has("area_code"))
                    {
                        area_code = jsonObject1.getString("area_code");
                    }
                    if (jsonObject1.has("phone_number"))
                    {
                        phone_number = jsonObject1.getString("phone_number");
                    }
                    if (jsonObject1.has("alternative_mobile_number"))
                    {
                        alternative_mobile_number = jsonObject1.getString("alternative_mobile_number");
                    }
                    if (jsonObject1.has("alternative_email"))
                    {
                        alternative_email = jsonObject1.getString("alternative_email");
                    }

                    linksdata.add(new addressdetailDTO(address_detail_id, address_line_1, tehsil,  district, state, pin_code,
                            same_as_above, p_address_line_1, p_tehsil,p_district, p_state, p_pin_code, area_code, phone_number, alternative_mobile_number, alternative_email));

            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return linksdata;
    }


    public String Parseaboutus(JSONObject jsonObject)
    {

        String status="", error_code="", message="",aboutus="";
        try {

            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONArray data = jsonObject.getJSONArray("data");

                for ( int i = 0; i< data.length(); i++)
                {
                    JSONObject jsonObject1 = new JSONObject(data.getString(i));

                    if (jsonObject1.has("page_detail"))
                    {
                        aboutus=jsonObject1.getString("page_detail");
                    }
                }

            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return aboutus;
    }

    public List<newsDTO> Parsenews(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String news_title = "",news_id = "",created_date="" ;

        int i;
        List<newsDTO> linksdata = new ArrayList<newsDTO>();

        try {

            //  JSONObject jsonObject = new JSONObject(jsonResponse);
            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {
                JSONArray json_array = jsonObject.getJSONArray("data");

                for ( i = 0; i< json_array.length(); i++)
                {

                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if(jsonObject1.has("news_title"))
                    {
                        String text = jsonObject1.getString("news_title");
                        news_title = text.replace("/", "");
                    }
                    if(jsonObject1.has("news_id"))
                    {
                        news_id = jsonObject1.getString("news_id");
                    }
                    if(jsonObject1.has("created_date"))
                    {
                        created_date = jsonObject1.getString("created_date");
                    }
                    linksdata.add(new newsDTO(news_title,news_id,created_date));

                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("111"))
            {
                Sendmessage alert = new Sendmessage();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }


    public List<websettingDTO> Parsewebsetting(JSONObject jsonObject)
    {
        String SITE_EMAIL = "",CONTACT_NUMBER = "",SITE_ADDRESS = "" ,ORDER_PAYEE_EMAIL = "",MINIMUM_DEPOSIT_AMOUNT = "",APPVERSION="",MAXIMUM_DEPOSIT_AMOUNT = "";

        try {

            AppSession.getInstance().SaveData(mContext,CommanString.WEBCONTACTNUMBER,"");
            AppSession.getInstance().SaveData(mContext,CommanString.WEBCONTACTEMAIL,"");

            JSONObject dataObject = jsonObject.getJSONObject("data");

            if (dataObject.has("site_email"))
            {
                SITE_EMAIL = dataObject.getString("site_email");
                AppSession.getInstance().SaveData(mContext, CommanString.WEBCONTACTEMAIL, SITE_EMAIL);
            }

            if (dataObject.has("site_address"))
            {
                SITE_ADDRESS = dataObject.getString("site_address");
            }

            if (dataObject.has("contact_number"))
            {
                CONTACT_NUMBER=dataObject.getString("contact_number");
                AppSession.getInstance().SaveData(mContext,CommanString.WEBCONTACTNUMBER,CONTACT_NUMBER);
            }

            if (dataObject.has("maximum_deposit_amount"))
            {
                MAXIMUM_DEPOSIT_AMOUNT = dataObject.getString("maximum_deposit_amount");
                AppSession.getInstance().SaveData(mContext,CommanString.MAXWALLET,MAXIMUM_DEPOSIT_AMOUNT);
            }

            if (dataObject.has("minimum_deposit_amount"))
            {
                MINIMUM_DEPOSIT_AMOUNT = dataObject.getString("minimum_deposit_amount");
                AppSession.getInstance().SaveData(mContext,CommanString.MINWALLET,MINIMUM_DEPOSIT_AMOUNT);

            }

            if (dataObject.has("app_version"))
            {
                APPVERSION = dataObject.getString("app_version");
                CommanString.APPVERSION=APPVERSION;
            }

            CommanString.websetting.add(new websettingDTO(SITE_EMAIL ,CONTACT_NUMBER ,SITE_ADDRESS ,MINIMUM_DEPOSIT_AMOUNT,MAXIMUM_DEPOSIT_AMOUNT,APPVERSION));


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return CommanString.websetting;
    }


    public List<solvedpaperDTO> Parsesolvedpaper(JSONObject jsonObject)
    {
        String status="", error_code="", message="";

        String file_url="",paper_id = "",paper_name = "",created_date="",paper_alias="",paper_file_url="" ;

        int i;
        List<solvedpaperDTO> linksdata = new ArrayList<solvedpaperDTO>();

        try {

            //  JSONObject jsonObject = new JSONObject(jsonResponse);
            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {

                JSONObject data = jsonObject.getJSONObject("data");

                if(data.has("file_url"))
                {
                    file_url = data.getString("file_url");
                }

                JSONArray json_array = data.getJSONArray("solved_paper");

                for ( i = 0; i< json_array.length(); i++)
                {
                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if(jsonObject1.has("paper_id"))
                    {
                        paper_id = jsonObject1.getString("paper_id");
                    }
                    if(jsonObject1.has("paper_name"))
                    {
                        paper_name = jsonObject1.getString("paper_name");
                    }
                    if(jsonObject1.has("paper_alias"))
                    {
                        paper_alias = jsonObject1.getString("paper_alias");
                    }
                    if(jsonObject1.has("paper_file_url"))
                    {
                        paper_file_url = file_url+jsonObject1.getString("paper_file_url");
                    }
                    if(jsonObject1.has("created_date"))
                    {
                        created_date = jsonObject1.getString("created_date");
                    }
                    linksdata.add(new solvedpaperDTO(paper_id,paper_name,paper_alias,paper_file_url,status,created_date));

                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }


   /* public List<latestjobDTO> Parsesyllabus(JSONObject jsonObject)
    {
        String file_url="";
        String status="", error_code="", message="";

        String syllabus_id = "",syllabus_name = "",syllabus_alias="",syllabus_file_url="",created_date="" ;

        int i;
        List<latestjobDTO> linksdata = new ArrayList<latestjobDTO>();

        try {

            //  JSONObject jsonObject = new JSONObject(jsonResponse);
            AppSession.getInstance().SaveData(mContext,CommanString.STATUS,"");

            if (jsonObject.has("status"))
            {
                status = jsonObject.getString("status");
                AppSession.getInstance().SaveData(mContext,CommanString.STATUS,status);
            }
            if (jsonObject.has("message"))
            {
                message = jsonObject.getString("message");
                CommanString.Message=message;
            }
            if (jsonObject.has("error_code"))
            {
                error_code = jsonObject.getString("error_code");
                CommanString.ERROR_CODE=error_code;
            }
            if (status.equals("1") && jsonObject.has("data"))
            {

                JSONObject data = jsonObject.getJSONObject("data");

                if(data.has("file_url"))
                {
                    file_url = data.getString("file_url");
                }

                JSONArray json_array = data.getJSONArray("syllabus_list");

                for ( i = 0; i< json_array.length(); i++)
                {
                    JSONObject jsonObject1 = new JSONObject(json_array.getString(i));

                    if(jsonObject1.has("syllabus_id"))
                    {
                        syllabus_id = jsonObject1.getString("syllabus_id");
                    }
                    if(jsonObject1.has("syllabus_name"))
                    {
                        syllabus_name = jsonObject1.getString("syllabus_name");
                    }
                    if(jsonObject1.has("syllabus_alias"))
                    {
                        syllabus_alias = jsonObject1.getString("syllabus_alias");
                    }
                    if(jsonObject1.has("syllabus_file_url"))
                    {
                        syllabus_file_url = file_url+jsonObject1.getString("syllabus_file_url");
                    }
                    if(jsonObject1.has("status"))
                    {
                        status = jsonObject1.getString("status");
                    }
                    if(jsonObject1.has("created_date"))
                    {
                        created_date = jsonObject1.getString("created_date");
                    }
                    linksdata.add(new syllabusDTO(syllabus_id,syllabus_name,syllabus_alias,syllabus_file_url,status,created_date));

                }
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1404"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
            else if (status.equalsIgnoreCase("0") && error_code.equalsIgnoreCase("1403"))
            {
                Checkuser alert = new Checkuser();
                alert.showDialog(mContext, CommanString.Message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return linksdata;
    }
*/


    public class Checkuser
    {
        public void showDialog(Context ctx,String message)
        {
            final Dialog dialog = new Dialog(ctx);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_useralert);

            final TextView msg = (TextView) dialog.findViewById(R.id.msg);
            msg.setText(message);
            Button ok = (Button) dialog.findViewById(R.id.ok);
            Button cancel = (Button) dialog.findViewById(R.id.cancel);

            if (CommanString.ERROR_CODE.equalsIgnoreCase("1404"))
            {
                ok.setText("Create New Account");
            }
            else
            {
                ok.setText("Contact to Support Team");
            }

            cancel.setText("Exit");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
                    if (CommanString.ERROR_CODE.equalsIgnoreCase("1404"))
                    {
                        AppSession.getInstance().SaveData(mContext, CommanString.NAME, "");
                        AppSession.getInstance().SaveData(mContext, CommanString.EMAIL_ID, "");
                        AppSession.getInstance().SaveData(mContext, CommanString.MOBILENUMBER, "");
                        AppSession.getInstance().SaveData(mContext, CommanString.USERID, "");
                        AppSession.getInstance().SaveData(mContext, CommanString.REGID, "");

                        dbhelper.deletealldata();
                        Intent i = new Intent(mContext, page_RegistrationActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mContext.startActivity(i);
                        CommonUtility.disconnectFromFacebook(mContext);
                    }
                    else
                    {
                        MainActivity.Drawcontactus();
                    }

                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();

                    AppSession.getInstance().SaveData(mContext, CommanString.NAME, "");
                    AppSession.getInstance().SaveData(mContext, CommanString.EMAIL_ID, "");
                    AppSession.getInstance().SaveData(mContext, CommanString.MOBILENUMBER, "");
                    AppSession.getInstance().SaveData(mContext, CommanString.USERID, "");
                    AppSession.getInstance().SaveData(mContext, CommanString.REGID, "");

                    dbhelper.deletealldata();
                    Intent i = new Intent(mContext, page_Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(i);
                    CommonUtility.disconnectFromFacebook(mContext);



                }
            });

            dialog.show();

        }
    }


    public class Sendmessage
    {
        public void showDialog(Context ctx,String message)
        {
            final Dialog dialog = new Dialog(ctx);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_contactus);

            final TextView msg = (TextView) dialog.findViewById(R.id.msg);
            final TextView query = (EditText) dialog.findViewById(R.id.query);
            msg.setText(message);
            Button ok = (Button) dialog.findViewById(R.id.ok);
            Button cancel = (Button) dialog.findViewById(R.id.cancel);

            ok.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();

                }
            });

            cancel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
                    System.exit(0);

                }
            });

            dialog.show();

        }
    }


}
