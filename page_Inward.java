package com.simranfisheries;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DataTransferObject.InwardDTO;
import DataTransferObject.OutwardDTO;


public class page_Inward extends Fragment
{
    RelativeLayout.LayoutParams rl1 = null;
    int width=0,height=0;
    static Context context = null;
    View v = null;
    public static List<InwardDTO> inwardList=new ArrayList<InwardDTO>();
    private RecyclerView recyclerView;
    Button search_button=null,add_inward=null;
    private InwardAdapter adapter;


    public static page_Inward newInstance(Context ctx)
    {
        page_Inward fragment = new page_Inward();
        Bundle args = new Bundle();
        context = ctx;
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.page_inward, container, false);

        v.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;



        search_button=(Button)v.findViewById(R.id.search_button);
        add_inward=(Button)v.findViewById(R.id.add_inward);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);

        inwardList.clear();

        for (int i=0;i<6;i++)
        {
            inwardList.add(new InwardDTO("1", "13/01/2018", "1546456", "Nav ", "NAAV-1000","Vendor 1","1500.00","3","Sonu Singh"));
        }

        adapter = new InwardAdapter(context, inwardList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        add_inward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(context, page_Add_Inward.class));
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

            }
        });

        return v;
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


    class InwardAdapter extends RecyclerView.Adapter<InwardAdapter.MyViewHolder>
    {

        private Context mContext;
        private List<InwardDTO> inwardDTOList;

        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView date, receipt;
            public Button edit;

            public MyViewHolder(View view)
            {
                super(view);
                date = (TextView) view.findViewById(R.id.date);
                receipt = (TextView) view.findViewById(R.id.receipt);
                edit= (Button) view.findViewById(R.id.edit);


            }
        }


        public InwardAdapter(Context mContext, List<InwardDTO> outwardDTOs)
        {
            this.mContext = mContext;
            this.inwardDTOList = outwardDTOs;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_inward, parent, false);

            return new MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position)
        {
            InwardDTO inwardDTO = inwardDTOList.get(position);

        }

        @Override
        public int getItemCount() {
            return inwardDTOList.size();
        }
    }

}


