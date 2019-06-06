package com.example.priya.riyaproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Historyfragment extends android.support.v4.app.Fragment {
    DynamoDBMapper dynamoDBMapper;
    Button reload;
    public List<Borrow> list;
    int j;
    Double userid;
    RecyclerView recyclerView;
    int a;


    public Historyfragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_historyfragment, container, false);
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
        reload=view.findViewById(R.id.reloadhistory);
        list = new ArrayList<>();
        list = null;
        j = 0;
        recyclerView=view.findViewById(R.id.listtest);
        userid=((ProfileActivity)getActivity()).userid;

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    funn(dynamoDBMapper,userid.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                while(list==null){
                }
                Borrowlistadapter la=new Borrowlistadapter(getContext(), list,dynamoDBMapper);
                if(list==null){
                    Log.d("YourMainActivity", "list is null");

                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(la);

            }
        });



        return view;
    }


    private void funn(final DynamoDBMapper dynamoDBMapper1, String s) {

        Map<String, AttributeValue> es=new HashMap<>();
        es.put(":val5", new AttributeValue().withN(s));
        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("CustID = :val5").withExpressionAttributeValues(es);
        new Thread(new Runnable() {
            @Override
            public void run() {

                final List<Borrow> list2=dynamoDBMapper1.scan(Borrow.class,scanExpression);
                list=list2;

            }
        }).start();
    }

    /*private void findbookbycustid(final DynamoDBMapper dynamoDBMapper, String us) {
        Map<String, AttributeValue> es=new HashMap<>();
        es.put(":val", new AttributeValue().withN(us));
        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("CustID = :val").withExpressionAttributeValues(es);
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<Borrow> list2=dynamoDBMapper.scan(Borrow.class,scanExpression);
                list=list2;

            }
        }).start();
        while (list==null){j++;}
        Borrowlistadapter la=new Borrowlistadapter(getActivity(), list);
        listView.setAdapter(la);
    }*/


}
