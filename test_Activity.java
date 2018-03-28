package com.formduniya;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import CommonString.CommanString;
import CommonString.CommonUtility;
import Communication.HttpRequest;
import DataTransferObject.topcoachingDTO;

public class page_Coaching_Activity extends ActionBarActivity {


    String ExternalFontPath;
    int page=1,s=1;
    String load="";
    ProgressBar loader=null;
    private DataAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    protected Handler handler;
    List<topcoachingDTO> data = new ArrayList<topcoachingDTO>();
    RelativeLayout.LayoutParams rl1 = null;
    RecyclerView highlightjobs = null;
    JsonObjectRequest jsonRequest=null;
    int small,medium,large;
    Typeface typeFace= null,typeFacebold=null;
    Typeface FontLoaderTypeface;
    Context ct =null;
    int width =0,height=0;
    Resources res=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.page_coaching_activity);
            ct = this;

            res=getResources();
            String name = res.getString(R.string.TOPCOACHING);
            load= res.getString(R.string.loading);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            typeFacebold = Typeface.createFromAsset(ct.getAssets(), "fonts/Roboto-Regular.ttf");

            setSupportActionBar(toolbar);
            RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);

            TextView TextViewNewFont = new TextView(ct);
            String text = "<font color=#ffffff>"+name+"</font>";
            TextViewNewFont.setText(Html.fromHtml(text));
            TextViewNewFont.setTextSize(17);
            TextViewNewFont.setTypeface(Typeface.DEFAULT_BOLD);
            TextViewNewFont.setLayoutParams(layoutparams);
            TextViewNewFont.setTextColor(Color.WHITE);
            TextViewNewFont.setGravity(Gravity.CENTER);

            ExternalFontPath = "fonts/Roboto-Regular.ttf";
            FontLoaderTypeface = Typeface.createFromAsset(getAssets(), ExternalFontPath);

            TextViewNewFont.setTypeface(FontLoaderTypeface);


            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(TextViewNewFont);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.appthemecolor)));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            DisplayMetrics  displayMetrics = getResources().getDisplayMetrics();
            width = displayMetrics.widthPixels;
            height = displayMetrics.heightPixels;

            CommonUtility.hideKeyboard(ct);

            small= CommanString.small;
            medium=CommanString.medium;
            large=CommanString.large;


            String check_internet = res.getString(R.string.check_internet);
            load= res.getString(R.string.loading);


            loader=(ProgressBar)findViewById(R.id.loader);
            highlightjobs = (RecyclerView)findViewById(R.id.highlightjobs);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ct, 1);
            highlightjobs.setLayoutManager(mLayoutManager);
            highlightjobs.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
            highlightjobs.setItemAnimator(new DefaultItemAnimator());

            CommonUtility.hideKeyboard(ct);

            boolean ch= isNetworkAvaliable(ct);
            if (ch)
            {
                getData();
            }
            else
            {
                ViewDialog1 alert = new ViewDialog1();
                alert.showDialog(ct, check_internet);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void getData()
    {
        final ProgressDialog loading=ProgressDialog.show(ct,"",load);
        loading.setCancelable(true);
        int page=s-1;
        String offset= String.valueOf(page);
        String url = CommanString.baseurl+"topCoachings?offset="+offset;

        jsonRequest= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                // the response is already constructed as a JSONObject!
                try {

                    HttpRequest http = new HttpRequest(ct);
                    data=http.Parsetopcoaching(response);

                    if ((loading != null) && loading.isShowing())
                    {
                        loading.dismiss();
                    }


                    if (data!=null && data.size()>0)
                    {
                        try
                        {
                            mAdapter = new DataAdapter(ct, data);
                            highlightjobs.setAdapter(mAdapter);


                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
            }
        });

        Volley.newRequestQueue(ct).add(jsonRequest);

    }

    class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder>
    {

        private Context mContext;
        private List<topcoachingDTO> topcoachingDTOList;

        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView title, contact, courses,address;
            public LinearLayout mainlayout;

            public MyViewHolder(View view)
            {
                super(view);

                title= (TextView) view.findViewById(R.id.title);
                contact= (TextView) view.findViewById(R.id.contact);
                address= (TextView) view.findViewById(R.id.address);
                courses= (TextView) view.findViewById(R.id.courses);
                mainlayout= (LinearLayout) view.findViewById(R.id.mainlayout);

            }
        }


        public DataAdapter(Context mContext, List<topcoachingDTO> topcoachingDTOS)
        {
            this.mContext = mContext;
            this.topcoachingDTOList = topcoachingDTOS;
        }

        @Override
        public DataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_topcoaching, parent, false);


            return new DataAdapter.MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(final DataAdapter.MyViewHolder holder, final int position)
        {
            final topcoachingDTO topcoachingDTO = data.get(position);

            TextView title = holder.title;
            title.setId(position + 1);
            title.setText(topcoachingDTO.getcoaching_name());
            title.setTypeface(typeFacebold);

            TextView contact = holder.contact;
            contact.setId(position + 2);
            contact.setText(topcoachingDTO.getcontact_number1());
            contact.setTypeface(typeFace);

            TextView address =  holder.address;
            address.setId(position + 3);
            address.setText(topcoachingDTO.getaddress());
            address.setTypeface(typeFace);

            TextView courses = holder.courses;
            courses.setId(position + 3);
            courses.setText(topcoachingDTO.gettotal_course());
            courses.setTypeface(typeFace);

            LinearLayout mainlayout=holder.mainlayout;
            mainlayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivity(new Intent(ct, page_Coaching_Detail.class));
                    overridePendingTransition(R.anim.enter_from_right, R.anim.hold);

                }
            });

        }

        @Override
        public int getItemCount() {
            return topcoachingDTOList.size();
        }
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge)
            {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount)
                { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)

               /* if (position >= spanCount)
                {
                    outRect.top = spacing; // item top
                }*/
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp)
    {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == android.R.id.home)
        {
            finish();
            overridePendingTransition(R.anim.hold, R.anim.exit_to_right);
        }
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_job, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setQueryHint("Search Coachings");
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        finish();
        overridePendingTransition(R.anim.hold, R.anim.exit_to_right);

    }

    public static boolean isNetworkAvaliable(Context ctx)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public class ViewDialog1
    {
        public void showDialog(Context ctx,String message)
        {
            final Dialog dialog = new Dialog(ctx);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_alert);

            final TextView msg = (TextView) dialog.findViewById(R.id.msg);
            msg.setText(message);
            Button ok = (Button) dialog.findViewById(R.id.ok);
            Button cancel = (Button) dialog.findViewById(R.id.cancel);
            cancel.setVisibility(View.GONE);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();

                }
            });

            dialog.show();

        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        CommonUtility.hideKeyboard(ct);

    }


}
