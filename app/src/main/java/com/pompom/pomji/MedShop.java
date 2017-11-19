package com.pompom.pomji;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
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

class Medicine extends Item {
    private int cure;

    Medicine(int img, String name, String description, int price, int cure) {
        super(img, name, description, price);
        this.cure = cure;
    }

    public int getCure() {
        return cure;
    }
}

public class MedShop extends Fragment {
    private Medicine[] med = new Medicine[2];

    private ArrayList<MedInventory> mymed = new ArrayList<>();
    private ArrayList<String> checkmed = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.food_shop, container, false);
        med[0] = new Medicine(R.drawable.bluebottle, "B.potion", "Bitter", 100, 1);
        med[1] = new Medicine(R.drawable.redbottle, "R.potion", "Sweet", 200, 2);
        ListView listView = (ListView) rootView.findViewById(R.id.lsFoodShop);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        Gson gson = new Gson();

        SharedPreferences shared = getContext().getSharedPreferences("my_ref", MODE_PRIVATE);
        String json = shared.getString("med", "");
        Type type = new TypeToken<ArrayList<MedInventory>>() {
        }.getType();
        mymed = gson.fromJson(json, type);

        if (mymed == null) {
            mymed = new ArrayList<>();
        }

        json = shared.getString("checkmed", "");
        type = new TypeToken<ArrayList<String>>() {
        }.getType();
        checkmed = gson.fromJson(json, type);
        if (checkmed == null) {
            checkmed = new ArrayList<>();
        }

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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.cutom_shop_layout, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.imgItem);
            TextView txtName = (TextView) view.findViewById(R.id.txtItemName);
            TextView txtDes = (TextView) view.findViewById(R.id.txtItemDescription);
            TextView txtValue = (TextView) view.findViewById(R.id.txtItemValue);
            TextView txtPrice = (TextView) view.findViewById(R.id.txtItemPrice);

            Button buyButton = (Button) view.findViewById(R.id.buyButton);

            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences shared = getContext().getSharedPreferences("my_ref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.button);
                    mp.start();
                    String json = shared.getString("User", "");
                    Gson gson = new Gson();
                    User user = gson.fromJson(json, User.class);
                    if (user.getMoney() < med[i].getPrice()) {
                        Toast.makeText(getContext(), "Not enough coin.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Buy successful.", Toast.LENGTH_LONG).show();
                        editor.putInt("coin", shared.getInt("coin", 0) - med[i].getPrice());

                        user.setMoney(user.getMoney() - med[i].getPrice());
                        json = gson.toJson(user);
                        editor.putString("User", json);

                        if (checkmed.contains(med[i].getName())) {
                            mymed.get(checkmed.indexOf(med[i].getName())).addItem();
                        } else {
                            checkmed.add(med[i].getName());
                            mymed.add(new MedInventory(med[i], 1));
                        }
                        json = gson.toJson(mymed);
                        editor.putString("med", json);
                        json = gson.toJson(checkmed);
                        editor.putString("checkmed", json);
                    }
                    editor.commit();
                    Log.v("money", String.valueOf(shared.getInt("coin", 0)));
                }
            });

            imageView.setImageResource(med[i].getImg());
            txtName.setText(med[i].getName());
            txtDes.setText(med[i].getDescription());
            txtValue.setText(String.valueOf(med[i].getCure()));
            txtPrice.setText("Price: " + String.valueOf(med[i].getPrice()));

            return view;
        }
    }
}
