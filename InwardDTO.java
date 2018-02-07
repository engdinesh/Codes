package DataTransferObject;

/**
 * Created by TBS_Dinesh on 22-Nov-17.
 */

public class InwardDTO
{

    String id = "",date = "",receipt = "", product_type="",product="",vendor="",
            rate="",quantity="" ,addedby="";

    public InwardDTO(String id, String date, String receipt, String product_type
            , String product, String vendor, String rate, String quantity, String addedby)
    {
        super();
        this.id = id;
        this.date = date;
        this.receipt = receipt;
        this.product_type = product_type;
        this.product = product;
        this.vendor = vendor;
        this.rate = rate;
        this.quantity = quantity;
        this.addedby = addedby;

    }
    public String getid()
    {
        return this.id;
    }
    public String getdate()
    {
        return this.date;
    }
    public String getreceipt()
    {
        return this.receipt;
    }
    public String getproduct_type()
    {
        return this.product_type;
    }
    public String getproduct()
    {
        return this.product;
    }
    public String getvendor()
    {
        return this.vendor;
    }
    public String getrate()
    {
        return this.rate;
    }
    public String getquantity()
    {
        return this.quantity;
    }
    public String getaddedby()
    {
        return this.addedby;
    }
}
