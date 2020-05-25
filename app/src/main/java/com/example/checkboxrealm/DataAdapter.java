package com.example.checkboxrealm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends BaseAdapter {

    List<Data> dataList;
    Context context;
    MainActivity mainActivity;
    static int sumPrice = 0;
    public DataAdapter(List<Data> dataList, Context context, MainActivity mainActivity) {
        this.dataList = dataList;
        this.context = context;
        this.mainActivity = mainActivity;

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.data,parent,false);
            viewHolder= new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
        viewHolder = (ViewHolder) convertView.getTag();
        }
        //
        final Data data = (Data) getItem(position);

        viewHolder.tvName.setText(data.getName());
        viewHolder.tvPrice.setText(data.getPrice());
        viewHolder.tvDescription.setText(data.getDescription());

        final int numPosition = data.getId();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Main2Activity.class);
                intent.putExtra("numPosition",numPosition);
                context.startActivity(intent);
            }
        });

        //
         final TextView tvPrice = mainActivity.findViewById(R.id.tvPrice);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sumPrice += (Integer.parseInt(data.getPrice()));
                    tvPrice.setText(String.valueOf(sumPrice));
                }else {
                    sumPrice -= (Integer.parseInt(data.getPrice()));
                    tvPrice.setText(String.valueOf(sumPrice));
                }
            }
        });
        return convertView;
    }

    private class ViewHolder{
        TextView tvName,tvPrice,tvDescription;
        CheckBox checkBox;
        public ViewHolder(View view) {
            tvName = view.findViewById(R.id.tvName);
            tvPrice = view.findViewById(R.id.tvPriceData);
            tvDescription = view.findViewById(R.id.tvDescription);
            checkBox = view.findViewById(R.id.cbData);
        }
    }
}

