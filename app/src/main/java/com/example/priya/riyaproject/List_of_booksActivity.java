package com.example.priya.riyaproject;

import android.content.Context;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    List<Books> cachedEntries;
    List<String> reclist;
    int j;
    int cacheupdate;
    Double userid;
    Timestamp timestamp,timestamp1;
    String year1,year2,Cust_email;
    Integer number;
    File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_books);
        listView = findViewById(R.id.booklist);
        list = new ArrayList<>();
        list = null;
        reclist=new ArrayList<>();
        reclist=null;
        timestamp=null;
        cachedEntries=new ArrayList<>();
        cachedEntries=null;
        j = 0;
        btn = findViewById(R.id.btnrefresh);
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


        year1 = null;
        Intent i = getIntent();
        Cust_email=i.getStringExtra("email");
        userid = i.getDoubleExtra("userid", 0);
         Integer integer=(int)Math.round(userid);
         fetch("http://useruserfinal-env.xwik2y2dnd.us-east-1.elasticbeanstalk.com/api/"+integer.toString());

        number = i.getIntExtra("number", 9);
        if (number == 1) {
            year2 = i.getStringExtra("year");
            year1 = i.getStringExtra("year2");
            try {
                cachedEntries = (List<Books>) Internalstorage.readObject(this, year1+year2);
                if(cachedEntries!=null){
                    timestamp1=(Timestamp)Internalstorage.readObject(this,"timestamp");
                    cacheupdate=updatecache(dynamoDBMapper);
                    while(timestamp==null){ }
                    if(timestamp.before(timestamp1)){
                        cachedEntries=null;
                        File dir = getFilesDir();
                        File file = new File(dir, year1+year2);
                        file.delete();
                        Timestamp entries=new Timestamp(System.currentTimeMillis());
                        Internalstorage.writeObject(this, "timestamp", entries);
                        //writetotable(dynamoDBMapper,entries);

                    }
               }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        else {
            try {
                cachedEntries = (List<Books>) Internalstorage.readObject(this, "4");
                if(cachedEntries!=null){
                    timestamp1=(Timestamp)Internalstorage.readObject(this,"timestamp");
                    cacheupdate=updatecache(dynamoDBMapper);
                    while(timestamp==null){ }
                    if(timestamp.before(timestamp1)){
                        cachedEntries=null;
                        File dir = getFilesDir();
                        File file = new File(dir, "4");
                        file.delete();
                        Timestamp entries=new Timestamp(System.currentTimeMillis());
                        Internalstorage.writeObject(this, "timestamp", entries);
                       // writetotable(dynamoDBMapper,entries);

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cachedEntries==null){
                    File dir = getFilesDir();
                    File file = new File(dir, "timestamp");
                    file.delete();
                if (number == 1) {
                    try {
                        FindBookwithSpecifiedattribute(dynamoDBMapper, year2, year1);
                        while (list==null){}
                        try
                        {
                            FileOutputStream fos = openFileOutput(year1+year2, Context.MODE_PRIVATE);
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            List<Books> kj=new ArrayList<>(list);
                            oos.writeObject(kj);
                            oos.close();
                            fos.close();
                        }
                        catch (IOException ioe)
                        {
                            ioe.printStackTrace();
                        }


                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (number == 2) {
                    FindBookwithratingbest(dynamoDBMapper, "4");
                    while (list==null){}
                    try
                    {
                        FileOutputStream fos = openFileOutput("4", Context.MODE_PRIVATE);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        ArrayList<Books>a=new ArrayList<>(list);
                        oos.writeObject(a);
                        oos.close();
                        fos.close();
                    }
                    catch (IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                }}
                else{
                        Listadapter la=new Listadapter(getApplicationContext(), cachedEntries);

                        listView.setAdapter(la);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent it= new Intent(List_of_booksActivity.this,BookpurchaseActivity.class);
                                Bundle b=new Bundle();
                                b.putString("na",cachedEntries.get(position).getBookName());
                                b.putString("image",cachedEntries.get(position).getImageUrl());
                                b.putString("rate",cachedEntries.get(position).getRating());
                                b.putString("price",cachedEntries.get(position).getPrice());
                                b.putDouble("userid",userid);
                                b.putString("email",Cust_email);
                                b.putDouble("bid",cachedEntries.get(position).getISBN());
                                it.putExtra("bund",b);
                                it.putExtra("key3", (Serializable) reclist);

                                startActivity(it);

                            }
                        });



                }


            }
        });

    }

    private void writetotable(final DynamoDBMapper dynamoDBMapper, final Timestamp entries) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Lastid lastid=new Lastid();
                lastid=dynamoDBMapper.load(Lastid.class,"Books");
                lastid.setTimestamp(entries.toString());
                dynamoDBMapper.save(lastid);
            }
        }).start();
    }

    private int updatecache(final DynamoDBMapper dynamoDBMapper) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Lastid lastid=new Lastid();
                lastid=dynamoDBMapper.load(Lastid.class,"Books");
                String time= null;
                time=lastid.getTimestamp();
                timestamp=Timestamp.valueOf(time);
                }
        }).start();



        return  0;
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
        while (list==null){j++;}
        Listadapter la=new Listadapter(getApplicationContext(), list);

        listView.setAdapter(la);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent it= new Intent(List_of_booksActivity.this,BookpurchaseActivity.class);
                Bundle b=new Bundle();
                b.putString("na",list.get(position).getBookName());
                b.putDouble("userid",userid);
                b.putString("email",Cust_email);
                b.putDouble("bid",list.get(position).getISBN());
                b.putString("image",list.get(position).getImageUrl());
                b.putString("rate",list.get(position).getRating());
                b.putString("price",list.get(position).getPrice());
                it.putExtra("bund",b);
                it.putExtra("key3", (Serializable) reclist);
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
        while (list==null){j++;}
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
                b.putString("email",Cust_email);
                b.putDouble("bid",list.get(position).getISBN());
                it.putExtra("bund",b);
                it.putExtra("key3", (Serializable) reclist);
                startActivity(it);

            }
        });


    }

    private void fetch(String url) {
        Fetchitem process = new Fetchitem(List_of_booksActivity.this,this);
        process.execute();
    }
    public void setList(List<String> list) {
        this.reclist = list;
    }}

