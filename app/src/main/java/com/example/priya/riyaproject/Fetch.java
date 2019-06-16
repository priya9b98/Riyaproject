package com.example.priya.riyaproject;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class Fetch extends AsyncTask<Void,Void,List<String>> {
    String data ="";
    private LoginActivity activity;
    Context mc;
    int whi;
    Character prev;
    List<String> list;
    String adu;
    Fetch(LoginActivity context,Context mc,String ur,int i){
        activity=context;
        this.mc=mc;
        this.adu=ur;
        this.whi=i;
    }
    @Override
    protected List<String> doInBackground(Void... voids) {
        try {
            URL url = new URL(adu);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //httpURLConnection.setRequestMethod("GET");
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            list=new ArrayList<>();


            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            //    line = bufferedReader.readLine();
            //  i++;
            int j=0;
            prev='u';
            int i=0;
           /*while(j<6){
                if(data.charAt(i)=='<' ){
                    i=i+3;
                    list.add(f);
                     f="";
                     j++;
                }
                else
                   f=f+Character.toString(data.charAt(i));
                i++;
            }

*/
           int take=0;
            String f="";
            while(j<6){
                f="";

                while(data.charAt(i)!='<'){

                    f=f.concat(Character.toString(data.charAt(i)));
                    i++;
                    prev=data.charAt(i);
                }
                if(take==0){
                list.add(f);}
                j++;
                i=i+4;}





        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPostExecute(List<String> str) {
        super.onPostExecute(str);
        str=list;
        if(whi==1)
          activity.setList(str);
        else
            activity.setList0(str);

    }

}

