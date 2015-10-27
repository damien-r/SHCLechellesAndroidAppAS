package com.rossier.shclechelles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SplashScreen extends Activity {
	
	private static final long SPLASH_TIME = 3000; //3 seconds
	Handler mHandler;
	Runnable mJumpRunnable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		ImageView img = (ImageView) findViewById(R.id.splash);
		img.setImageResource(R.drawable.splash);
		mJumpRunnable = new Runnable() {
		  public void run() {
		   jump();
		  }
		};
	    mHandler = new Handler();
	    mHandler.postDelayed(mJumpRunnable, SPLASH_TIME);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

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
	
	private void jump() {
		//it is safe to use this code even if you
		//do not intend to allow users to skip the splash
		if(isFinishing())
		  return;
		 startActivity(new Intent(this, MainActivity.class));
		 finish();
		}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	 jump();
	 return true;
	}
}
