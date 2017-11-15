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

import static android.content.Context.MODE_PRIVATE;

public class MyMed extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.med_inventory, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.lsMedInventory);
        MyMed.CustomAdapter customAdapter = new MyMed.CustomAdapter();
        listView.setAdapter(customAdapter);

        return rootView;
    }

    class CustomAdapter extends BaseAdapter {
        private Context mContext;

        @Override
        public int getCount() {
            return 2;
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
//            ImageView imageView = (ImageView) view.findViewById(R.id.imgItem);
//            TextView txtName = (TextView) view.findViewById(R.id.txtItemName);
//            TextView txtDes = (TextView) view.findViewById(R.id.txtItemDescription);
//            TextView txtValue = (TextView) view.findViewById(R.id.txtItemValue);
//            TextView txtPrice = (TextView) view.findViewById(R.id.txtItemPrice);
//            Button buyButton = (Button) view.findViewById(R.id.buyButton);
//
//            buyButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    SharedPreferences shared = getContext().getSharedPreferences("my_ref",MODE_PRIVATE);
//                    SharedPreferences.Editor editor = shared.edit();
//                    if(shared.getInt("coin",0)-food[i].getPrice()<0){
//                        Toast.makeText(getContext(),"Not enough coin.", Toast.LENGTH_LONG).show();
//                    }else {
//                        editor.putInt("coin", shared.getInt("coin", 0) - food[i].getPrice());
//                    }
//                    editor.commit();
//                    Log.v("money",String.valueOf(shared.getInt("coin",0)));
//                }
//            });
//
//
//            imageView.setImageResource(food[i].getImg());
//            txtName.setText(food[i].getName());
//            txtDes.setText(food[i].getDescription());
//            txtValue.setText(String.valueOf(food[i].getEnergy()));
//            txtPrice.setText(String.valueOf(food[i].getPrice()));

            return view;
        }
    }
}
