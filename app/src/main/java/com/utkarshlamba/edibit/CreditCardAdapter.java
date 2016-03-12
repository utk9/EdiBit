package com.utkarshlamba.edibit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jeffrey on 2016-03-12.
 */

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.ViewHolder>{

    public static int TRIANGLE_REQUEST_CODE = 1000;

    ArrayList<String[]> data;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView expDate,holderName,cardNumber,cardType;
        ImageView image;
        View view;

        public ViewHolder(View v) {
            super(v);
            expDate = (TextView) v.findViewById(R.id.expDateTextCard);
            holderName = (TextView) v.findViewById(R.id.holderNameTextCard);
            cardNumber = (TextView) v.findViewById(R.id.cardNumberTextCard);
            cardType = (TextView) v.findViewById(R.id.cardTypeTextCard);

            image = (ImageView) v.findViewById(R.id.cardImageView);

            view = v;
        }
    }

    public CreditCardAdapter(ArrayList<String[]> data){
        this.data = data;
    }


    @Override
    public CreditCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.credit_card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CreditCardAdapter.ViewHolder v, int i) {
        String type = data.get(i)[0];
        String number = data.get(i)[1];
        String name = data.get(i)[2];
        String exp = data.get(i)[3];

        v.cardType.setText(type);
        v.cardNumber.setText(number);
        v.holderName.setText(name);
        v.expDate.setText(exp);

        final int index = i;

        v.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder popup = new AlertDialog.Builder(view.getContext());
                popup.setMessage("Delete card?");
                popup.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.remove(index);
                        notifyDataSetChanged();
                    }
                });
                popup.setNegativeButton("No", null);
                popup.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(data == null){
            return 0;
        }
        return data.size();
    }

    public void addCard(String type, String number, String name, String exp){
        String[] data = {type,number,name,exp};
        this.data.add(data);
        notifyDataSetChanged();
    }
}
