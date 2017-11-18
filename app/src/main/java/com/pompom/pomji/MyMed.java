package com.pompom.pomji;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

class MedInventory{
    private Medicine med;
    private int quantity;

    MedInventory(Medicine med, int quantity){
        this.med = med;
        this.quantity = quantity;
    }

    public void addItem(){
        quantity++;
    }

    public void useItem(){
        quantity-=1;
    }

    public Medicine getMed(){
        return med;
    }

    public int getQuantity(){
        return quantity;
    }
}

public class MyMed extends Fragment {

    private ArrayList<MedInventory> mymed = new ArrayList<>();
    private ArrayList<String> checkmed = new ArrayList<>();
    private MyMed.CustomAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Gson gson = new Gson();
        View rootView = inflater.inflate(R.layout.med_inventory, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.lsMedInventory);
        customAdapter = new MyMed.CustomAdapter();
        listView.setAdapter(customAdapter);

        SharedPreferences shared = getContext().getSharedPreferences("my_ref", MODE_PRIVATE);

        String json = shared.getString("med","");
        Type type = new TypeToken<ArrayList<MedInventory>>(){}.getType();
        mymed = gson.fromJson(json, type);

        if (mymed == null) {
            mymed = new ArrayList<>();
        }

        json = shared.getString("checkmed","");
        type = new TypeToken<ArrayList<String>>(){}.getType();
        checkmed = gson.fromJson(json,type);
        if (checkmed == null) {
            checkmed = new ArrayList<>();
        }

        return rootView;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mymed.size();
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
        public View getView(final int i,View view,ViewGroup viewGroup){
            view = getLayoutInflater().inflate(R.layout.cutom_inventory_layout,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imgItem);
            TextView txtName = (TextView) view.findViewById(R.id.txtItemName);
            TextView txtValue = (TextView) view.findViewById(R.id.txtItemValue);
            TextView txtQuantity = (TextView) view.findViewById(R.id.txtItemQuantity);
            Button useButton = (Button) view.findViewById(R.id.useButton);

            useButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson = new Gson();
                    SharedPreferences shared = getContext().getSharedPreferences("my_ref",MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    String json = shared.getString("User", "");
                    User user = gson.fromJson(json, User.class);
                    user.getPom().setSick(false);
                    mymed.get(i).useItem();
                    json = gson.toJson(user);
                    editor.putString("User", json);
                    if(mymed.get(i).getQuantity()==0){
                        mymed.remove(i);
                        checkmed.remove(i);
                    }

                    json = gson.toJson(mymed);
                    editor.putString("med",json);
                    json = gson.toJson(checkmed);
                    editor.putString("checkmed",json);
                    editor.commit();
                    customAdapter.notifyDataSetChanged();
                    Activity activity = (Activity)getContext();
                    activity.finish();
                }
            });


            imageView.setImageResource(mymed.get(i).getMed().getImg());
            txtName.setText(mymed.get(i).getMed().getName());
            txtValue.setText(String.valueOf(mymed.get(i).getMed().getCure()));
            txtQuantity.setText(String.valueOf(mymed.get(i).getQuantity()));

            return view;
        }
    }
}
