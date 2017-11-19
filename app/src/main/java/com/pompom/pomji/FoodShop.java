package com.pompom.pomji;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

class Food extends Item{
    private int energy;
    Food(int img,String name,String description,int price,int energy){
        super(img,name,description,price);
        this.energy = energy;
    }
    public int getEnergy(){
        return energy;
    }
}



public class FoodShop extends Fragment {
    private Food[] food = new Food[3];

    private ArrayList<FoodInventory> myfood = new ArrayList<>();
    private ArrayList<String> check = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.food_shop, container, false);
        food[0] = new Food(R.drawable.sushi,"Sushi","Popular japanese food.",100,20);
        food[1] = new Food(R.drawable.steak,"Steak","Unique pom's food.",200,40);
        food[2] = new Food(R.drawable.berger,"Hamburger","The most delicious.",300,80);
        ListView listView = (ListView) rootView.findViewById(R.id.lsFoodShop);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        Gson gson = new Gson();

        SharedPreferences shared = getContext().getSharedPreferences("my_ref", MODE_PRIVATE);
        String json = shared.getString("food","");
        Type type = new TypeToken<ArrayList<FoodInventory>>(){}.getType();
        myfood = gson.fromJson(json, type);

        if (myfood == null) {
            myfood = new ArrayList<>();
        }

        json = shared.getString("checkfood","");
        type = new TypeToken<ArrayList<String>>(){}.getType();
        check = gson.fromJson(json,type);
        if (check == null) {
            check = new ArrayList<>();
        }

        return rootView;
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return food.length;
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
            view = getLayoutInflater().inflate(R.layout.cutom_shop_layout,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imgItem);
            TextView txtName = (TextView) view.findViewById(R.id.txtItemName);
            TextView txtDes = (TextView) view.findViewById(R.id.txtItemDescription);
            TextView txtValue = (TextView) view.findViewById(R.id.txtItemValue);
            TextView txtPrice = (TextView) view.findViewById(R.id.txtItemPrice);
            Button buyButton = (Button) view.findViewById(R.id.buyButton);

            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences shared = getContext().getSharedPreferences("my_ref",MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.button);
                    mp.start();
                    if(shared.getInt("coin",0)-food[i].getPrice()<0){
                        Toast.makeText(getContext(),"Not enough coin.", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(),"Buy successful.", Toast.LENGTH_LONG).show();
                        editor.putInt("coin", shared.getInt("coin", 0) - food[i].getPrice());
                        if(check.contains(food[i].getName())){
                            myfood.get(check.indexOf(food[i].getName())).addItem();
                        }else{
                            check.add(food[i].getName());
                            myfood.add(new FoodInventory(food[i],1));
                        }
                        Gson gson = new Gson();
                        String json = gson.toJson(myfood);
                        editor.putString("food",json);
                        json = gson.toJson(check);
                        editor.putString("checkfood",json);
                    }
                    editor.commit();
                }
            });


            imageView.setImageResource(food[i].getImg());
            txtName.setText(food[i].getName());
            txtDes.setText(food[i].getDescription());
            txtValue.setText("Energy: "+String.valueOf(food[i].getEnergy()));
            txtPrice.setText("Price: "+String.valueOf(food[i].getPrice()));

            return view;
        }
    }


}
