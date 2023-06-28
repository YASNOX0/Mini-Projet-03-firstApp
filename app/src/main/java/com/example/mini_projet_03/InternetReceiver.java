package com.example.mini_projet_03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Random;

public class InternetReceiver extends BroadcastReceiver {

    ImageView imageView;

    public InternetReceiver() {

    }

    public static InternetReceiver newInstance(ImageView imageView){
        InternetReceiver internetReceiver = new InternetReceiver();
        internetReceiver.imageView = imageView;
        return internetReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!CheckInternet.isConnected(context)) {
            imageView.setImageResource(R.drawable.image_not_available);
        }
        else {
            String[] imagesUrl = context.getResources().getStringArray(R.array.images_url);
            int randomNbr = new Random().nextInt(imagesUrl.length);

            Glide.with(context).load(imagesUrl[randomNbr]).into(imageView);
        }
    }
}
