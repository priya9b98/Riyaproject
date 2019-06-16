package com.example.priya.riyaproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText name,password;
    Button login;
    List<String> n;
    String url;
    List<String> cached;
    List<String> n2;
    DynamoDBMapper dynamoDBMapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        url=null;

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
            n=new ArrayList<>();
            n2=new ArrayList<>();
            cached=new ArrayList<>();
            cached=null;
            n2=null;
            n=null;
            name=findViewById(R.id.namelogin);
            password=findViewById(R.id.loginpass);
            login=findViewById(R.id.loginn);
        try {
            cached = (List<String>) Internalstorage.readObject(this, "mostrated");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CustomerTableDo customer=new CustomerTableDo();
                            String k=name.getText().toString();

                          customer=dynamoDBMapper.load(CustomerTableDo.class,k);
                            final CustomerTableDo finalCustomer = customer;
                            if(cached==null){
                            fetch("http://mostrated-env.i3updikueu.us-east-1.elasticbeanstalk.com/most/",1);
                                while(n==null){}
                                try{ FileOutputStream fos = openFileOutput("mostrated", Context.MODE_PRIVATE);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                List<String>a=new ArrayList<>(n);
                                oos.writeObject(a);
                                oos.close();
                                fos.close();} catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            }else {
                                n=cached;
                            }
                            Integer integer=(int)Math.round(customer.getUserId());
                            fetch("http://itemitem-env.m4mac22x8i.us-east-1.elasticbeanstalk.com/item/"+integer.toString(),0);
                            while(n2==null){}
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(finalCustomer!=null){
                                    if(finalCustomer.getCustPass().equals(password.getText().toString())){
                                        Intent intent=new Intent(LoginActivity.this,ProfileActivity.class);
                                        intent.putExtra("name",finalCustomer.getCustName());
                                        intent.putExtra("key1", (Serializable) n);
                                        intent.putExtra("key2", (Serializable) n2);
                                        intent.putExtra("userid",finalCustomer.getUserId());
                                        intent.putExtra("email",finalCustomer.getCustEmail());
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),"right",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"wrong password",Toast.LENGTH_SHORT).show();

                                    }}
                                    else{
                                        Toast.makeText(LoginActivity.this,"Check your Email",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).start();




                }
            });
        }

    private void fetch(String url,int k) {
        Fetch process = new Fetch(LoginActivity.this,this,url,k);
        process.execute();
    }
    public void setList(List<String> list) {
        this.n = list;
    }
    public  void setList0(List<String>list){
        this.n2=list;
    }


}
