package com.example.srushtee.dummy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by srushtee on 23-03-2017.
 */

public class derp_adapter extends RecyclerView.Adapter<derp_adapter.derp_holder> {

    private ItemClickCallback itemClickCallback;
    public interface ItemClickCallback {

        void onItemClick(int p);
        void onSecondaryIconClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback){
        this.itemClickCallback=itemClickCallback;
    }



    private List<list_item> list_data;
    private LayoutInflater inflater;
    public derp_adapter(List<list_item> list_data,Context c){
        this.inflater=LayoutInflater.from(c);
        this.list_data=list_data;
    }



    @Override
    public derp_adapter.derp_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_item,parent,false);
        return new derp_holder(view);
    }

    @Override
    public void onBindViewHolder(derp_holder holder, int position) {
        list_item item=list_data.get(position);
        holder.title.setText(item.getTitle());
        holder.status.setText(item.getStatus());
        holder.icon.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    class derp_holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title,status;
        private ImageView icon;
        private View container;
        public derp_holder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.lbl_item_text);
            icon=(ImageView)itemView.findViewById(R.id.im_item_icon);
            status=(TextView)itemView.findViewById(R.id.status);
            container=itemView.findViewById(R.id.cont_item_root);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.cont_item_root){
                itemClickCallback.onItemClick(getAdapterPosition());
            }
            else{
                itemClickCallback.onSecondaryIconClick(getAdapterPosition());
            }
        }
    }
}

