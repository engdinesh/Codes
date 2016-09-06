package Utility;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

/**
 * Created by abc on 10/06/2015.
 */
public class AppSession
{
    private static AppSession objAppSessionModify = null;
    private boolean isWritting;
    private int delayCount;
    String SAVE_PREF = "top_house";
    private  AppSession()
    {
        isWritting = false;
        delayCount = 0;
    }

    public static AppSession getInstance()
    {
        if(null == objAppSessionModify)
            objAppSessionModify = new AppSession();
        return objAppSessionModify;
    }
    public String ReadData(Context mContext, String Key, String DefaultVal)
    {
        String returnString = "";
        SharedPreferences sp = null;
        try
        {
            sp = mContext.getSharedPreferences(SAVE_PREF, Context.MODE_PRIVATE);
            returnString = sp.getString(Key.trim(), DefaultVal.trim());
        }
        catch(Exception e)
        {
            returnString = "";
        }
        finally
        {
            sp = null;
        }
        return returnString;
    }
    public boolean SaveData(Context mContext, String Key, String Val)
    {
        boolean isSuccess = false;
        SharedPreferences sp = null;
        SharedPreferences.Editor edit = null;
        try
        {
            while(true)
            {
                if(false == isWritting)
                {
                    isWritting = true;
                    sp = mContext.getSharedPreferences(SAVE_PREF, Context.MODE_PRIVATE);
                    edit = sp.edit();
                    edit.putString(Key.trim(), Val.trim());
                    isSuccess = edit.commit();
                    edit = null;
                    isWritting = false;
                    break;
                }
                else
                {
                    delayCount++;
                    TimeUnit.MILLISECONDS.sleep(100);
                    if(3 == delayCount)
                    {
                        delayCount = 0;
                        isWritting = false;
                    }
                }
            }
        }
        catch(Exception e)
        {
            isSuccess = false;
        }
        finally
        {
            isWritting =  false;
            delayCount = 0;
            sp = null;
            if(null != edit)
            {
                edit.commit();
                edit = null;
            }
        }
        return isSuccess;
    }
}
