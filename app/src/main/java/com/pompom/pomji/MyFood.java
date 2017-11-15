package com.pompom.pomji;

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

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

class InventoryItem{
    private ArrayList<Food> food = new ArrayList<Food>();
    private ArrayList<Medicine> medicines = new ArrayList<Medicine>();
    private ArrayList<Integer> foodQuantity = new ArrayList<Integer>();
    private ArrayList<Integer> medQuantity = new ArrayList<Integer>();

    public ArrayList<Food> getFood(){
        return food;
    }
    public ArrayList<Medicine> getMedicines(){
        return medicines;
    }
    public void popFood(int i){
        food.remove(i);
    }
    public void popFoodQuantity(int i){
        foodQuantity.remove(i);
    }
    public int getFoodQuantity(int i){
        return foodQuantity.get(i);
    }
    public int getMedQuantity(int i){
        return medQuantity.get(i);
    }
    public void useFood(int i){
        foodQuantity.set(i,getFoodQuantity(i)-1);
    }
}

public class MyFood extends Fragment {

    private InventoryItem inventoryItem = new InventoryItem();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.food_inventory, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.lsFoodInventory);
        MyFood.CustomAdapter customAdapter = new MyFood.CustomAdapter();
        listView.setAdapter(customAdapter);

        return rootView;
    }

    class CustomAdapter extends BaseAdapter {
        private Context mContext;

        @Override
        public int getCount() {
            return inventoryItem.getFood().size();
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
//                    SharedPreferences shared = getContext().getSharedPreferences("my_ref",MODE_PRIVATE);
//                    SharedPreferences.Editor editor = shared.edit();
                    inventoryItem.useFood(i);
                    if(inventoryItem.getFoodQuantity(i)==0){
                        inventoryItem.popFood(i);
                        inventoryItem.popFoodQuantity(i);
                    }
                }
            });


            imageView.setImageResource(inventoryItem.getFood().get(i).getImg());
            txtName.setText(inventoryItem.getFood().get(i).getName());
            txtValue.setText(String.valueOf(inventoryItem.getFood().get(i).getEnergy()));
            txtQuantity.setText(String.valueOf(inventoryItem.getFoodQuantity(i)));

            return view;
        }
    }
}
