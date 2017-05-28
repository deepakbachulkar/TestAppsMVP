package com.deepak.testapp.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deepak.testapp.R;
import com.deepak.testapp.model.User;
import java.util.List;


/**
 * Created by Will on 2/8/2015.
 */
public class Intermediary implements IRecyclerViewIntermediary
{
    private List<User> mDataSet;
    private Context mContext;
    private View.OnClickListener onClickListener;
    public Intermediary(List<User> items, View.OnClickListener onClickListener)
    {
        mDataSet= items;
        this.onClickListener= onClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int type)
    {
        View v = View.inflate(viewGroup.getContext(), R.layout.row_item, null);
        View vr= v.getRootView();
        RelativeLayout.LayoutParams rp
                = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        vr.setLayoutParams(rp);
        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position)
    {
        return position % 4;  //any logic can go here
    }

    @Override
    public void populateViewHolder(RecyclerView.ViewHolder viewHolder, final int position)
    {
        final ViewHolder  holder = (ViewHolder)viewHolder;
        try
        {
            holder.txtName.setText(mDataSet.get(position).getUserName());
            holder.txtMobile.setText(""+mDataSet.get(position).getMobileNo());
            holder.layout.setTag(position);
            holder.layout.setOnClickListener(onClickListener);
        }catch (Exception e){}
   }

    private class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtName;
        public TextView txtMobile;
        public LinearLayout layout;
        public ViewHolder(View v)
        {
            super(v);
            txtName = (TextView) v.findViewById(R.id.txtName);
            txtMobile = (TextView) v.findViewById(R.id.txtMobile);
            layout = (LinearLayout) v.findViewById(R.id.layout);
        }
    }
}
