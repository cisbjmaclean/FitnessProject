package com.cis2250.network;

import android.content.pm.*;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import java.util.List;

/**
 * Created by Tim Connolly and Jeff White on 04/02/14.
 * 
    ******Here is the code that needs to be implemented in the onclick event of the mainActivity******
    @Override
    public void onClick(View view) {
    // create a new instance of the SocialPosting class
    SocialPosting sp = new SocialPosting();
    // get EditText by id
    EditText inputTxt = (EditText) findViewById(R.id.messageToPost);
    // store EditText in Variable
    String message = inputTxt.getText().toString();

    // create a new instance of the PackageManager Class for retrieving information related
    // to the application packages that are currently installed on the device.
    PackageManager pm = getPackageManager();

    try {
    // start the Facebook activity, sending the message and Package Manager instance SocialPosting class
    startActivity(sp.facebook(message, pm));
    // start the Twitter activity, sending the message and Package Manager instance SocialPosting class
    startActivity(sp.twitter(message, pm));
    } catch (Exception e) {
    e.printStackTrace();
    }
    }
    ******End onclick event of the mainActivity******
 */
public class SocialPosting extends ActionBarActivity  {
    public static Intent tweetIntent;
    public static Intent faceIntent;

    public static Intent twitter(final String message, final PackageManager pm){
        // Code to run Twitter intent

        // create a new instance of Intent called tweetIntent to send message to application (Twitter)
        tweetIntent = new Intent(Intent.ACTION_SEND);
        // get message from editText in MainActivity
        tweetIntent.putExtra(Intent.EXTRA_TEXT, message);
        // set mime type
        tweetIntent.setType("text/plain");

        // Information that is returned from resolving an intent against an IntentFilter.
        // This partially corresponds to information collected from the AndroidManifest.xml's <intent> tags.
        List<ResolveInfo> resolvedInfoList = pm.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);
        //System.out.println(resolvedInfoList);

        // set resolved to false
        boolean resolved = false;

        // loop through returned packages until Twitter is found and set resolved to true
        for(ResolveInfo resolveInfo: resolvedInfoList){
            if(resolveInfo.activityInfo.packageName.contains("com.twitter.android")){
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name );
                resolved = true;
                break;
            }
        }
        if(!resolved){
            // if Twitter not installed, tweetIntent will redirect to Twitter url to allow manual post
            String url = "https://twitter.com/intent/tweet?source=webclient&text="+message;
            tweetIntent = new Intent(Intent.ACTION_VIEW);
            tweetIntent.setData(Uri.parse(url));
        }
    return tweetIntent;
    }

    public static Intent facebook(final String message, final PackageManager pm){
        // Code to run facebook intent

        // create a new instance of Intent called faceIntent to send message to application (Facebook)
        faceIntent = new Intent(Intent.ACTION_SEND);
        faceIntent.setType("text/plain");
        // this is needed for Facebook or Email
        faceIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        // get message from editText in MainActivity
        faceIntent.putExtra(Intent.EXTRA_TEXT, message);

        // Information that is returned from resolving an intent against an IntentFilter.
        // This partially corresponds to information collected from the AndroidManifest.xml's <intent> tags.
        List<ResolveInfo> resolvedInfoList = pm.queryIntentActivities(faceIntent, PackageManager.MATCH_DEFAULT_ONLY);
        //System.out.println(resolvedInfoList);

        // set resolved to false
        boolean resolved = false;

        // loop through returned packages until Facebook is found and set resolved to true
        for(ResolveInfo resolveInfo: resolvedInfoList){
            if(resolveInfo.activityInfo.packageName.contains("com.facebook.katana")){
                faceIntent.setClassName(
                resolveInfo.activityInfo.packageName,
                resolveInfo.activityInfo.name );
                resolved = true;
                break;
            }
        }
        if(!resolved){
            // if Facebook not installed, faceIntent will redirect to Facebook url to allow manual post
            String url = "https://m.facebook.com/sharer?_rdr"+message;
            faceIntent = new Intent(Intent.ACTION_VIEW);
            faceIntent.setData(Uri.parse(url));
        }

        return faceIntent;
    }
}