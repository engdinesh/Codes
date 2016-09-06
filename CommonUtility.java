package Utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by abc on 11/06/2015.
 */
public class CommonUtility
{

    public static boolean isValidEmail(String target)
    {
        if (target == null)
        {
            return false;
        } else
        {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

/*

    public static boolean is_Network_Available(Context ct)
    {
        boolean isSuccess = false;
        ConnectivityManager connManager=null;
        NetworkInfo mWifi = null;
        NetworkInfo mobile = null;
        try
        {
            connManager = (ConnectivityManager)ct.getSystemService(Context.CONNECTIVITY_SERVICE);
            mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            mobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mWifi.isConnected())
                isSuccess = true;

            if(mobile.isConnected())
                isSuccess = true;

            if (connManager.getActiveNetworkInfo() != null
                    && connManager.getActiveNetworkInfo().isAvailable()
                    && connManager.getActiveNetworkInfo().isConnected())
            {
                isSuccess =  true;
            }


        }
        catch(Exception e)
        {
            ;
        }
        finally
        {
            connManager = null;
            mWifi = null;
            mobile = null;
        }
        return isSuccess;
    }

*/

    public static void hideSoftKeyboard(Activity activity)
    {
        try {


            InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @TargetApi(9)
    public static boolean CreateDirinInternalStorage(String sFile)
    {
        boolean isSuccess = false;
        File pFile = null;
        try
        {
            String path = getIntenalSDCardPath();
            if(path.endsWith("/"))
                pFile = new File(path + sFile);
            else
                pFile = new File(path + File.separator + sFile);

            if(null!= pFile && !pFile.isDirectory())
            {
                pFile.mkdir();
                pFile.setReadable(true, false);
                pFile.setWritable(true, false);
                pFile.setExecutable(true, false);
            }
            isSuccess = true;
        }
        catch(Exception e)
        {;}
        finally
        {
            pFile = null;
        }
        return isSuccess;
    }

    @TargetApi(9)
    public static boolean CreateDir(String sFile)
    {
        boolean isSuccess = false;
        File pFile = null;
        try
        {
            pFile = new File(sFile);
            if(null!=pFile && !pFile.isDirectory())
            {
                pFile.mkdir();
                pFile.setReadable(true, false);
                pFile.setWritable(true, false);
                pFile.setExecutable(true, false);
            }

            isSuccess = true;
        }
        catch(Exception e)
        {;}
        finally
        {
            pFile = null;
        }
        return isSuccess;
    }


    public static Bitmap get_bitmap_image(Context context)
    {
        Bitmap bmp = null;
        try
        {
            String image_name="";
            image_name="profile";
            String  PackageName = context.getPackageName();

            String  path1 = getIntenalSDCardPath();

            if(path1!= null)
            {
                String pathName= path1 + File.separator + image_name+".png";

                final BitmapFactory.Options options = new BitmapFactory.Options();
                bmp = BitmapFactory.decodeFile(pathName,options);
            }
            else
            {
                String pathName= Environment.getExternalStorageDirectory() + File.separator +
                        "Android" + File.separator + "data" + File.separator + "com.techlect.usavy" + File.separator + "profile"+ File.separator +image_name + ".png";

                final BitmapFactory.Options options = new BitmapFactory.Options();
                bmp = BitmapFactory.decodeFile(pathName,options);
            }

            return bmp;
        } catch (Exception e)
        {
            return bmp;
            // TODO: handle exception
        }

    }



    public static String getIntenalSDCardPath()
    {
        String Path = "";
        Path = "/storage/sdcard1/Android/data/com.bahamainrenter/bahamas";
        File fPath = new File(Path);
        if(null != fPath)
        {
            if(!fPath.exists())
            {
                Path = "";
                //Path = "/storage/sdcard0/Android/data/com.connectmore.ActivityClasses/m-AdCall";
                Path = "/storage/sdcard0/Android/data/com.bahamainrenter/bahamas";

                fPath = null;
                fPath = new File(Path);
                if(!fPath.exists())
                {
                    String extStorageDirectory =  Environment.getExternalStorageDirectory().toString();
                    String TARGET_BASE_PATH = extStorageDirectory + File.separator +
                            "Android" + File.separator + "data" + File.separator + "com.bahamainrenter" + File.separator +
                            "bahamas";

                    fPath = null;
                    fPath = new File(TARGET_BASE_PATH);
                    fPath.mkdirs();
                    Path = TARGET_BASE_PATH;
                }
            }
        }
        fPath = null;
        //ShowLog("CollectionFunctions", "path :: "+Path);
        return Path;
    }




    public static Bitmap get_bitmap_image(String path)
    {
        Bitmap bmp = null;
        try
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            bmp = BitmapFactory.decodeFile(path, options);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        return bmp;
    }



    public static String gettime()
    {
        String time;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        time = df.format(c.getTime());
        return time;

    }

    public static  String getdate()
    {
        String time;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        time = df.format(c.getTime());
        return time;

    }



    public static int show(String time)
    {
        int day = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");



            Date Date1 = format.parse(getdate() + " " + gettime());
            Date Date2 = format.parse(time);
            long mills = Date1.getTime() - Date2.getTime();
            long Day1 = mills / (1000 * 60 * 60);

             day = (int)Day1/24;
          //  long Mins = mills / (1000 * 60  );



            if(day < 0)
                day = 0;


          //  diff =""+day;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }



}
