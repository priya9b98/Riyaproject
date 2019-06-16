package com.example.priya.riyaproject;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import static android.provider.Settings.System.AIRPLANE_MODE_ON;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Homefragment extends android.support.v4.app.Fragment {
    Button books,store;
    DynamoDBMapper dynamoDBMapper;
    List<String> list11;
    List<String> list12;


    public Homefragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_homefragment, container, false);

        AWSMobileClient.getInstance().initialize(getActivity(), new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
                dynamoDBMapper = DynamoDBMapper.builder()
                        .dynamoDBClient(dynamoDBClient)
                        .awsConfiguration(
                                AWSMobileClient.getInstance().getConfiguration())
                        .build();
            }
        }).execute();

        list11=new ArrayList<>();
        list11=null;
        list12=new ArrayList<>();
        list12=null;
        list11=((ProfileActivity)getActivity()).list11;
        list12=((ProfileActivity)getActivity()).list12;



        books=view.findViewById(R.id.srchbook);
        store=view.findViewById(R.id.srchstore);

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iy=new Intent(getActivity(),SearchActivity.class);
                iy.putExtra("userid",((ProfileActivity)getActivity()).userid);
                iy.putExtra("email",((ProfileActivity)getActivity()).email);
                startActivity(iy);
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





        LinearLayout gallary=view.findViewById(R.id.gallary);
        LinearLayout gallary1=view.findViewById(R.id.gallary1);
        LayoutInflater inflater1=LayoutInflater.from(getActivity());
        for(int i=0;i<6;i++){

            View view1=inflater1.inflate(R.layout.item_list1,gallary,false);
            TextView textView=view1.findViewById(R.id.textView);
            textView.setText("item"+i);
            ImageView imageView=view1.findViewById(R.id.images);
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(getActivity(),BookDetails.class);
                    intent1.putExtra("imageurl",list11.get(finalI));
                    intent1.putExtra("userid",((ProfileActivity)getActivity()).userid);
                    intent1.putExtra("email",((ProfileActivity)getActivity()).email);
                    startActivity(intent1);
                }
            });
            Picasso.with(getContext()).load(list11.get(i)).into(imageView);
            //imageView.setImageResource(R.mipmap.ic_launcher);
            gallary.addView(view1);
        }



            LayoutInflater inflater2=LayoutInflater.from(getActivity());

        for(int j=0;j<6;j++){
            //isAirplaneModeOn(getContext());
            View view2=inflater2.inflate(R.layout.item_list2,gallary1,false);
            TextView textView=view2.findViewById(R.id.textView66);
            textView.setText("item"+j);
            ImageView imageView1=view2.findViewById(R.id.images66);
            final int finalI = j;
            if(list12.get(j).charAt(0)==' '){
            Picasso.with(getContext()).load(list12.get(j).substring(1)).into(imageView1);
               }
            else {
                Picasso.with(getContext()).load(list12.get(j)).into(imageView1);
            }
            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(getActivity(), BookDetails.class);
                    if(list12.get(finalI).charAt(0)==' '){
                    intent1.putExtra("imageurl", list12.get(finalI).substring(1));}
                    else
                        intent1.putExtra("imageurl", list12.get(finalI));
                    intent1.putExtra("userid",((ProfileActivity)getActivity()).userid);
                    intent1.putExtra("email",((ProfileActivity)getActivity()).email);
                startActivity(intent1);
                }
            });

            //   imageView1.setImageResource(R.mipmap.ic_launcher);
            gallary1.addView(view2);
        }

        return view;

        // Inflate the layout for this fragment
    }

 /*   private void inflatr(View view) {
        LinearLayout gallary1=view.findViewById(R.id.gallary1);

        LayoutInflater inflater2=LayoutInflater.from(getActivity());

        for(int i=0;i<6;i++){
            View view2=inflater2.inflate(R.layout.item_list1,gallary1,false);
            TextView textView=view2.findViewById(R.id.textView);
            textView.setText("item"+i);
            ImageView imageView1=view2.findViewById(R.id.images);
            if(j==6) {

                RequestOptions p=new RequestOptions();
                p.placeholder(R.mipmap.ic_launcher);
                Glide.with(getActivity()).applyDefaultRequestOptions(p).load(imagurls.get(i)).into(imageView1);
            }
            else{
                imageView1.setImageResource(R.mipmap.ic_launcher);}
            gallary1.addView(view2);

        }
    }

*/

    static boolean isAirplaneModeOn(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        return Settings.System.getInt(contentResolver, AIRPLANE_MODE_ON, 0) != 0;
    }

}
