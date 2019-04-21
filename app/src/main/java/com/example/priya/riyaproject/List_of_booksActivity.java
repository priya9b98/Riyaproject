package com.example.priya.riyaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

public class List_of_booksActivity extends AppCompatActivity {
    ListView listView;
    public List<Books> list;
    DynamoDBMapper dynamoDBMapper;
    String genre;
    Button btn;
    Double userid;
    String year1,year2;
    Integer number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_books);
        listView=findViewById(R.id.booklist);
        list=new ArrayList<>();
        btn=findViewById(R.id.btnrefresh);
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
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

        Intent i=getIntent();
        userid=i.getDoubleExtra("userid",0);

        number=i.getIntExtra("number",9);
        if(number==1) {
            year2 = i.getStringExtra("year");
            year1 = i.getStringExtra("year2");
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number==1){
                try {
                    FindBookwithSpecifiedattribute(dynamoDBMapper,year2,year1);
                } catch (Exception e) {
                    e.printStackTrace();
                }}
                else if(number==2){
                    FindBookwithratingbest(dynamoDBMapper,"4");
                }


            }
        });





    }

    private void FindBookwithratingbest(final DynamoDBMapper dynamoDBMapper, String s) {
        Map<String, AttributeValue> es=new HashMap<>();
        es.put(":val", new AttributeValue().withS(s));
        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("Rating > :val").withExpressionAttributeValues(es);
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<Books> list2=dynamoDBMapper.scan(Books.class,scanExpression);
                list=list2;

            }
        }).start();
        Listadapter la=new Listadapter(getApplicationContext(), list);

        listView.setAdapter(la);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent it= new Intent(List_of_booksActivity.this,BookpurchaseActivity.class);
                Bundle b=new Bundle();
                b.putString("na",list.get(position).getBookName());
                b.putDouble("userid",userid);
                b.putDouble("bid",list.get(position).getISBN());
                b.putString("image",list.get(position).getImageUrl());
                b.putString("rate",list.get(position).getRating());
                b.putString("price",list.get(position).getPrice());
                it.putExtra("bund",b);
                startActivity(it);

            }
        });


    }

    private void FindBookwithSpecifiedattribute(final DynamoDBMapper dynamoDBMapper, String year1,String year2) throws  Exception {
        Map<String, AttributeValue> ev=new HashMap<>();
        ev.put(":val1", new AttributeValue().withS(year1));
        ev.put(":val2",new AttributeValue().withS(year2));

        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("PublicationYear between :val2 and :val1").withExpressionAttributeValues(ev);

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<Books> list1=dynamoDBMapper.scan(Books.class,scanExpression);
                list=list1;

            }
        }).start();
        Listadapter la=new Listadapter(getApplicationContext(), list);

        listView.setAdapter(la);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it= new Intent(List_of_booksActivity.this,BookpurchaseActivity.class);
                Bundle b=new Bundle();
                b.putString("na",list.get(position).getBookName());
                b.putString("image",list.get(position).getImageUrl());
                b.putString("rate",list.get(position).getRating());
                b.putString("price",list.get(position).getPrice());
                b.putDouble("userid",userid);
                b.putDouble("bid",list.get(position).getISBN());
                it.putExtra("bund",b);

                startActivity(it);

            }
        });


    }
}

