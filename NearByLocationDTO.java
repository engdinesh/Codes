package DataTransforObject;

/**
 * Created by abc on 20/11/2015.
 */
public class NearByLocationDTO
{

    String name ="", address  ="", latitude  ="", longitude  ="",image = ""
            ,tel="",website="";


    public NearByLocationDTO( String name, String address, String latitude, String longitude,
                              String image, String tel, String website)
    {
        super();



        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image =image;
        this.tel = tel;
        this.website = website;

    }

    public String getname()
    {
        return name;
    }
    public String getaddress()
    {
        return address;
    }

    public String getlatitude()
    {
        return latitude;
    }
    public String getlongitude()
    {
        return longitude;
    }

    public String getimage()
    {
        return image;
    }
    public String gettel()
    {
        return tel;
    }
    public String getwebsite()
    {
        return website;
    }


}
