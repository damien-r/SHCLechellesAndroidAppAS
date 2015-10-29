package com.rossier.shclechelles;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orm.SugarConfig;
import com.orm.SugarDb;
import com.rossier.data.Data;
import com.rossier.data.NotifState;
import com.rossier.data.Team;
import com.rossier.data.TeamLight;
import com.rossier.data.Token;
import com.rossier.shclechelles.adapter.TeamAdapter;
import com.rossier.shclechelles.service.RegistrationIntentService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    List<TeamLight> teamsList;
    private static String URL = "http://1-dot-shcrestservice.appspot.com/rest/teams/all";
    private Gson gson = new Gson();
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("MainActivity","registrationComplete");
            }
        };
        if(Data.getInstance().getTeamsLightData()==null){
            new TeamLoadTask().execute();
        }else{
            teamsList = Data.getInstance().getTeamsLightData();
            initNotifFirstRun();
            startServiceNotif();
        }
    }

    private void startServiceNotif(){
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
        case 0://current
        	fragmentManager.beginTransaction()
            .replace(R.id.container, MatchListFragment.newInstance(position+1,"current",-1))
            .commit();
			break;
        case 1://next match
        	fragmentManager.beginTransaction()
            .replace(R.id.container, MatchListFragment.newInstance(position+1,"next",50))
            .commit();
			break;
		case 2://last match
			fragmentManager.beginTransaction()
            .replace(R.id.container, MatchListFragment.newInstance(position+1,"last",50))
            .commit();
			break;
		case 3://team
			fragmentManager.beginTransaction()
            .replace(R.id.container, TeamListFragment.newInstance(position+1))
            .commit();
			break;
		case 4://player
			fragmentManager.beginTransaction()
            .replace(R.id.container, PlayerListFragment.newInstance(position+1))
            .commit();
			break;
		case 5://param
			fragmentManager.beginTransaction()
            .replace(R.id.container, NotifListFragment.newInstance(position+1))
            .commit();
			break;
		}
        
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, 9000).show();
            } else {
                Log.i("MainActivity", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    public boolean initNotifFirstRun(){
       Log.i("MainActivity", "check first run");
        List<NotifState> notifState = NotifState.listAll(NotifState.class);
        if(notifState.size() == 0){
            initBdAndTopics(teamsList);
        }else{
            for(NotifState state : notifState) if( state.isState())Log.i("MainActivity", "Registrered to : "+state.getName());
        }
        return true;
    }

    private boolean initBdAndTopics(List<TeamLight> teamsList){
        for(TeamLight team : teamsList){
            NotifState state = new NotifState(team.getTopicId(), true);
            state.save();
        }
        return true;
    }

    class TeamLoadTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(URL);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                // if this is null the web service returned an empty page
                if (httpEntity == null) // response is empty so exit out
                    return null;

                String jsonString = EntityUtils.toString(httpEntity);

                if (jsonString != null && jsonString.length() != 0)
                {
                    Type listType = new TypeToken<List<TeamLight>>() {}.getType();
                    teamsList = gson.fromJson(jsonString, listType);
                    initNotifFirstRun();
                    startServiceNotif();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("ERROR SOMEWHERE!!!! ", e.toString());
            }
            return null;

        }
    }
}
