package com.placto.consumer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 12/29/2016.
 */

public class CustomlistAdapter extends ArrayAdapter<Customlist> {

    public CustomlistAdapter(Context context,ArrayList<Customlist> merchant)
    {
        super(context,0,merchant);
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent)
    {
        Customlist merchant=getItem(position); //Error place
        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_single, parent, false);
        }
        TextView shop_name = (TextView)convertView.findViewById(R.id.shop_name);
        TextView name = (TextView)convertView.findViewById(R.id.name);
        TextView verified = (TextView)convertView.findViewById(R.id.verified);
        TextView rating=(TextView)convertView.findViewById(R.id.rating);
        Button chat = (Button)convertView.findViewById(R.id.chat);
        Button plus = (Button)convertView.findViewById(R.id.plus);
        Button navigate = (Button)convertView.findViewById(R.id.navigate);
        shop_name.setText(merchant.shop_name);
        name.setText(merchant.name);

        String verified_text=merchant.verified;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date = new Date();
        try
        {
            date = df.parse(verified_text);
            System.out.println("date : "+df.format(date));
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }
        verified.setText(getTimeDiff(date, cal.getTime()));

        rating.setText(merchant.rating);
        double ratingvalue= Double.parseDouble(merchant.rating);
        if(ratingvalue>=0 && ratingvalue<=1)
        {
            rating.setBackgroundResource(R.drawable.ratingboxlowest);
        }
        if(ratingvalue>1 && ratingvalue<=2)
        {
            rating.setBackgroundResource(R.drawable.ratingboxlow);
        }
        if(ratingvalue>2 && ratingvalue<=3)
        {
            rating.setBackgroundResource(R.drawable.ratingboxmid);
        }
        if(ratingvalue>3 && ratingvalue<=4)
        {
            rating.setBackgroundResource(R.drawable.ratingboxhigh);
        }
        if(ratingvalue>4 && ratingvalue<=5)
        {
            rating.setBackgroundResource(R.drawable.ratingboxhighest);
        }
        return convertView;
    }

    private String getTimeDiff(Date dateOne, Date dateTwo) {
        String diff = "";
        long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
        long days = TimeUnit.MILLISECONDS.toDays(timeDiff);
        long hours =  TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.HOURS.toHours(TimeUnit.MILLISECONDS.toDays(timeDiff));
        long mins = TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff));
        if (days > 0)
            diff = String.format(Locale.ENGLISH, "%d days %d hours", days, hours);
        else
            diff = String.format(Locale.ENGLISH, "%d hours %d mins", hours, mins);
        return diff;
    }

}
