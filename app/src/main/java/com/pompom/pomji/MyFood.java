package com.pompom.pomji;

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

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

class FoodInventory{
    private Food food;
    private int quantity;

    FoodInventory(Food food, int quantity){
        this.food = food;
        this.quantity = quantity;
    }

    public void setFood(Food food){
        this.food = food;
    }

    public void addItem(){
        quantity++;
    }

    public void useItem(){
        quantity-=1;
    }

    public Food getFood(){
        return food;
    }

    public int getQuantity(){
        return quantity;
    }
}


public class MyFood extends Fragment {

    private ArrayList<FoodInventory> food = new ArrayList<>();
    private ArrayList<String> check = new ArrayList<>();
    private MyFood.CustomAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Gson gson = new Gson();
        View rootView = inflater.inflate(R.layout.food_inventory, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.lsFoodInventory);
        customAdapter = new MyFood.CustomAdapter();
        listView.setAdapter(customAdapter);

        SharedPreferences shared = getContext().getSharedPreferences("my_ref", MODE_PRIVATE);

        String json = shared.getString("food","");
        Type type = new TypeToken<ArrayList<FoodInventory>>(){}.getType();
        food = gson.fromJson(json, type);

        if (food == null) {
            food = new ArrayList<>();
        }

        json = shared.getString("checkfood","");
        type = new TypeToken<ArrayList<String>>(){}.getType();
        check = gson.fromJson(json,type);
        if (check == null) {
            check = new ArrayList<>();
        }

        return rootView;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return food.size();
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
                    SharedPreferences shared = getContext().getSharedPreferences("my_ref",MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    food.get(i).useItem();
                    if(food.get(i).getQuantity()==0){
                        food.remove(i);
                        check.remove(i);
                    }
                    Gson gson = new Gson();
                    String json = gson.toJson(food);
                    editor.putString("food",json);
                    json = gson.toJson(check);
                    editor.putString("checkfood",json);
                    editor.commit();
                    customAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(getContext(), main.class);
                    startActivity(intent);
                }
            });


            imageView.setImageResource(food.get(i).getFood().getImg());
            txtName.setText(food.get(i).getFood().getName());
            txtValue.setText(String.valueOf(food.get(i).getFood().getEnergy()));
            txtQuantity.setText(String.valueOf(food.get(i).getQuantity()));

            return view;
        }
    }
}
