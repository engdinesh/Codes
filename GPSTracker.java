package Utility;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Locale;

public class GPSTracker extends Service implements LocationListener {

	private final Context mContext;

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude

     String CountryName="" ;
     String StateName="" ;
     String CityName="";
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	// Declaring a Location Manager
	protected LocationManager locationManager;

	public GPSTracker(Context context) {
		this.mContext = context;
		getLocation();
	}

	public Location getLocation()
    {
		try {
			locationManager = (LocationManager) mContext
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				this.canGetLocation = true;
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.i("Network", "Network");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.i("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}
	
	/**
	 * Stop using GPS listener
	 * Calling this function will stop using GPS in your app
	 * */
	public void stopUsingGPS(){
		if(locationManager != null){
			locationManager.removeUpdates(GPSTracker.this);
		}		
	}
	
	/**
	 * Function to get latitude
	 * */
	public double getLatitude(){
		if(location != null){
			latitude = location.getLatitude();
		}
		
		// return latitude
		return latitude;
	}
	
	/**
	 * Function to get longitude
	 * */
	public double getLongitude(){
		if(location != null){
			longitude = location.getLongitude();
		}
		
		// return longitude
		return longitude;
	}

    /**
     * Function to get latitude
     * */
    public double getLatitudeFromCell()
    {
        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitudeFromCell()
    {
        // return longitude
        return longitude;
    }
    public String getCountryName()
    {
        return CountryName;
    }
    public String getStateName()
    {
        return StateName;
    }
    public String getCityName()
    {
        return  CityName;
    }

	
	/**
	 * Function to check GPS/wifi enabled
	 * @return boolean
	 * */
	public boolean canGetLocation() {
		return this.canGetLocation;
	}
	
	/**
	 * Function to show settings alert dialog
	 * On pressing Settings button will lauch Settings Options
	 * */

	public void showSettingsAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
   	 
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
 
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            	mContext.startActivity(intent);
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
	}


    public boolean getLoctionFromCell(Context ct)
    {
        boolean isSuccess = false;
        try
        {
        TelephonyManager telephonyManager ;
        GsmCellLocation cellLocation = null ;
        //CdmaCellLocation cellLocation1= null;


/*	try
	{*/
        telephonyManager = (TelephonyManager)ct.getSystemService(ct.TELEPHONY_SERVICE);
        cellLocation = (GsmCellLocation)telephonyManager.getCellLocation();
        final int	cid = cellLocation.getCid();
        final int	lac = cellLocation.getLac();


        {
            // TODO Auto-generated method stub
            if(RqsLocation(cid, lac))
            {

                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(ct, Locale.getDefault());
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    try
                    {



                    }
                    catch (Exception e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    String temp=addresses.get(0).getPostalCode();

                    for(int i=0;i<addresses.size();i++)
                    {
                        CountryName = addresses.get(i).getCountryName();
                        StateName = addresses.get(i).getAdminArea();
                        CityName = addresses.get(i).getLocality();

                    }

                   /* AppSession  appSession=new AppSession(ct);
                    List<UserDTO> userDTOs=appSession.getConnections();
                    if(null!=userDTOs && userDTOs.size()>0)
                    {
                        userDTOs.get(0).setLat("" + latitude);
                        userDTOs.get(0).setLang("" + longitude);
                        userDTOs.get(0).setCity(CityName);
                        userDTOs.get(0).setLocation(CityName+","+StateName+","+CountryName);
                        appSession.storeConnections(userDTOs);
                    }else
                    {
                         userDTOs = new ArrayList<UserDTO>();
                         UserDTO dto = new UserDTO("", "", "", "","", "", "", "", "", "", "", "", "", "", "", CityName,StateName, ""+latitude, ""+longitude,"", "", "", CityName+","+StateName+","+CountryName,"");
                         userDTOs.add(dto);
                         appSession.storeConnections(userDTOs);
                    }
*/

                isSuccess = true;
            }else
            {
                isSuccess = false;
            }
        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return isSuccess;
    }


    public boolean getLoctionFromCell1(Context ct)
    {
        boolean isSuccess = false;

        TelephonyManager telephonyManager ;
        GsmCellLocation cellLocation = null ;
        //CdmaCellLocation cellLocation1= null;


/*	try
	{*/
        telephonyManager = (TelephonyManager)ct.getSystemService(Context.TELEPHONY_SERVICE);
        cellLocation = (GsmCellLocation)telephonyManager.getCellLocation();
        final int	cid = cellLocation.getCid();
        final int	lac = cellLocation.getLac();


        {
            // TODO Auto-generated method stub
            if(RqsLocation(cid, lac))
            {
                try
                {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(ct, Locale.getDefault());
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    try
                    {



                    }
                    catch (Exception e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    String temp=addresses.get(0).getPostalCode();

                    for(int i=0;i<addresses.size();i++)
                    {
                        CountryName = addresses.get(i).getCountryName();
                        StateName = addresses.get(i).getAdminArea();
                        CityName = addresses.get(i).getLocality();

                    }
;


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                isSuccess = true;
            }else
            {
                isSuccess = false;
            }
        }
        return isSuccess;
    }






    private Boolean RqsLocation(int cid, int lac)
    {
        Boolean result = false;
        String urlmmap = "http://www.google.com/glm/mmap";
        try
        {
            URL url = new URL(urlmmap);
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.connect();
            OutputStream outputStream = httpConn.getOutputStream();
            WriteData(outputStream, cid, lac);
            InputStream inputStream = httpConn.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            dataInputStream.readShort();
            dataInputStream.readByte();
            int code = dataInputStream.readInt();
            if (code == 0) {
                latitude = (float)dataInputStream.readInt()/1000000;
                longitude = (float)dataInputStream.readInt()/1000000;
                if (latitude<=0 && longitude<=0)
                {
                    //invalid location
                }
                else
                {
                    //CollectionObject.LATITUDE = myLatitude;
                    //CollectionObject.LONGITUTE = myLongitude;
                }
                result = true;
            }
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    private void WriteData(OutputStream out, int cid, int lac) throws IOException
    {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        dataOutputStream.writeShort(21);
        dataOutputStream.writeLong(0);
        dataOutputStream.writeUTF("en");
        dataOutputStream.writeUTF("Android");
        dataOutputStream.writeUTF("1.0");
        dataOutputStream.writeUTF("Web");
        dataOutputStream.writeByte(27);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(3);
        dataOutputStream.writeUTF("");
        dataOutputStream.writeInt(cid);
        dataOutputStream.writeInt(lac);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.flush();
    }
   /* public  String GetLocationAddress(final Location location)
    {
        final String string = "";
        Thread thread = null;
        try
        {
         thread = new Thread( new Runnable() {
             @Override
             public void run() {
                 Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
                 try {
                     List<Address> addressList = geocoder.getFromLocation(
                             location.getLatitude(), location.getLongitude(), 1);
                     if (addressList != null && addressList.size() > 0) {
                         Address address = addressList.get(0);
                         StringBuilder sb = new StringBuilder();
                         for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                             sb.append(address.getAddressLine(i)).append("\n");
                         }
                         sb.append(address.getLocality()).append("\n");
                         sb.append(address.getPostalCode()).append("\n");
                         sb.append(address.getCountryName());
                         string = sb.toString();
                     }
                 } catch (IOException e) {

                 } finally {
                     Message message = Message.obtain();
                     message.setTarget(handler);
                     if (result != null) {
                         message.what = 1;
                         Bundle bundle = new Bundle();
                         string = "Latitude: " + latitude + " Longitude: " + longitude +
                                 "\n\nAddress:\n" + result;
                         bundle.putString("address", result);
                         message.setData(bundle);
                     } else {
                         message.what = 1;
                         Bundle bundle = new Bundle();
                         result = "Latitude: " + latitude + " Longitude: " + longitude +
                                 "\n Unable to get address for this lat-long.";
                         bundle.putString("address", result);
                         message.setData(bundle);
                     }
                     message.sendToTarget();
                 }



             }
         });

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  string;
    }*/

   /* public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n\nAddress:\n" + result;
                        bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n Unable to get address for this lat-long.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }*/

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
