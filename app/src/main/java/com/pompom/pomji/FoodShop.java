package com.pompom.pomji;

import android.content.Context;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

class Food{
    private String name;
    private String description;
    private int price;
    private int energy;
    private int img;
    Food(int img,String name,String description,int price,int energy){
        this.img = img;
        this.name = name;
        this.description = description;
        this.price = price;
        this.energy = energy;
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
    public int getEnergy(){
        return energy;
    }
}



public class FoodShop extends Fragment {
    private Food[] food = new Food[3];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.food_shop, container, false);
        food[0] = new Food(R.drawable.logo,"Food1","Eat Eat Eat",100,20);
        food[1] = new Food(R.drawable.logo,"Food2","Eat Eat Eat",200,40);
        food[2] = new Food(R.drawable.logo,"Food3","Eat Eat Eat",300,80);
        ListView listView = (ListView) rootView.findViewById(R.id.lsFoodShop);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        return rootView;
    }

    class CustomAdapter extends BaseAdapter{
        private Context mContext;

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
                    if(shared.getInt("coin",0)-food[i].getPrice()<0){
                        Toast.makeText(getContext(),"Not enough coin.", Toast.LENGTH_LONG).show();
                    }else {
                        editor.putInt("coin", shared.getInt("coin", 0) - food[i].getPrice());
                    }
                    editor.commit();
                    Log.v("money",String.valueOf(shared.getInt("coin",0)));
                }
            });


            imageView.setImageResource(food[i].getImg());
            txtName.setText(food[i].getName());
            txtDes.setText(food[i].getDescription());
            txtValue.setText(String.valueOf(food[i].getEnergy()));
            txtPrice.setText(String.valueOf(food[i].getPrice()));

            return view;
        }
    }


}
