package com.pompom.pomji;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

class Medicine{
    private int img;
    private String name;
    private String description;
    private int price;
    private int cure;
    Medicine(int img,String name,String description,int price,int cure){
        this.img = img;
        this.name = name;
        this.description = description;
        this.price = price;
        this.cure = cure;
    }
    public int getImg(){
        return img;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public int getPrice(){
        return price;
    }
    public int getCure(){
        return cure;
    }
}

public class MedShop extends Fragment {
    private Medicine[] med = new Medicine[2];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.food_shop, container, false);
        med[0] = new Medicine(R.drawable.logo,"Med1","eiei",100,1);
        med[1] = new Medicine(R.drawable.logo,"Med2","eiei",200,2);
        ListView listView = (ListView) rootView.findViewById(R.id.lsFoodShop);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        return rootView;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return med.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i,View view,ViewGroup viewGroup){
            view = getLayoutInflater().inflate(R.layout.cutom_shop_layout,null);

            ImageView imageView = (ImageView) view.findViewById(R.id.imgItem);
            TextView txtName = (TextView) view.findViewById(R.id.txtItemName);
            TextView txtDes = (TextView) view.findViewById(R.id.txtItemDescription);
            TextView txtValue = (TextView) view.findViewById(R.id.txtItemValue);
            TextView txtPrice = (TextView) view.findViewById(R.id.txtItemPrice);

            imageView.setImageResource(med[i].getImg());
            txtName.setText(med[i].getName());
            txtDes.setText(med[i].getDescription());
            txtValue.setText(String.valueOf(med[i].getCure()));
            txtPrice.setText(String.valueOf(med[i].getPrice()));

            return view;
        }
    }


}
