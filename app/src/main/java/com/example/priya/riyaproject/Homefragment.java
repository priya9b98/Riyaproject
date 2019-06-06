package com.example.priya.riyaproject;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class Homefragment extends android.support.v4.app.Fragment {
    Button books,store;
    DynamoDBMapper dynamoDBMapper;


    public Homefragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_homefragment, container, false);
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

        books=view.findViewById(R.id.srchbook);
        store=view.findViewById(R.id.srchstore);

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iy=new Intent(getActivity(),SearchActivity.class);
                iy.putExtra("userid",((ProfileActivity)getActivity()).userid);

                startActivity(iy);
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
            imageView.setImageResource(R.mipmap.ic_launcher);
            gallary.addView(view1);

        }

        LayoutInflater inflater2=LayoutInflater.from(getActivity());
        for(int i=0;i<6;i++){
            View view2=inflater2.inflate(R.layout.item_list1,gallary1,false);
            TextView textView=view2.findViewById(R.id.textView);
            textView.setText("item"+i);
            ImageView imageView1=view2.findViewById(R.id.images);
            imageView1.setImageResource(R.mipmap.ic_launcher);
            gallary1.addView(view2);

        }
        return view;

        // Inflate the layout for this fragment
    }

}
