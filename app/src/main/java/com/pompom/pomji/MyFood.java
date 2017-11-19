package com.pompom.pomji;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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

class FoodInventory extends ItemInventory{
    private Food food;

    FoodInventory(Food food, int quantity){
        super(quantity);
        this.food = food;
    }

    public Food getFood(){
        return food;
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
                    Gson gson = new Gson();
                    MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.button);
                    mp.start();
                    SharedPreferences shared = getContext().getSharedPreferences("my_ref",MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    food.get(i).useItem();
                    String json = shared.getString("User", "");
                    User user = gson.fromJson(json, User.class);
                    user.getPom().setHunger(user.getPom().getHunger()+food.get(i).getFood().getEnergy());
                    if(user.getPom().getHunger()>100){
                        user.getPom().setHunger(100);
                    }
                    json = gson.toJson(user);
                    editor.putString("User", json);
                    if(food.get(i).getQuantity()==0){
                        food.remove(i);
                        check.remove(i);
                    }
                    json = gson.toJson(food);
                    editor.putString("food",json);
                    json = gson.toJson(check);
                    editor.putString("checkfood",json);
                    customAdapter.notifyDataSetChanged();
                    editor.commit();
                    Activity activity = (Activity)getContext();
                    activity.finish();
                }
            });


            imageView.setImageResource(food.get(i).getFood().getImg());
            txtName.setText(food.get(i).getFood().getName());
            txtValue.setText("Energy: "+String.valueOf(food.get(i).getFood().getEnergy()));
            txtQuantity.setText(String.valueOf(food.get(i).getQuantity()+" ea"));

            return view;
        }
    }
}
