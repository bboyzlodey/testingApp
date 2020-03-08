package skarlat.dev.treasure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


import java.io.IOException;


public class MainActivity extends AppCompatActivity {

	Button webView;
	Button playGame;
	View dye;
	ImageView kill;
	String request;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		playGame = (Button) findViewById(R.id.play_game_btn);
		webView = (Button) findViewById(R.id.web_view_button);
	}

	public void playGame(View view) {
		setContentView(R.layout.activity_game);
		dye = (View) findViewById(R.id.dye_dialog);
	}

	public static String doGet(String url) {

		try {
			URL obj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			connection.setRequestProperty("Content-Type", "application/json");

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = bufferedReader.readLine()) != null) {
				response.append(inputLine);
			}
			bufferedReader.close();
			return response.toString();
		} catch (Exception e) {
			e.getStackTrace();
			return "Failed";
		}
	}

	public void webViewClick(View view) throws InterruptedException {
		setContentView(R.layout.activity_request);


		TextView requestText = (TextView) findViewById(R.id.dollar);


		RequestThread requestThread = new RequestThread(this);
		requestThread.start();
		if (requestThread.isAlive()) {
			try {
				requestThread.join();
			} catch (Exception e) {
				requestText.setText("Попытка подождать не удалась");
			}
		}
		if (request == null)
			requestText.setText("Не получилось");
		else {
			findDollarValue();
			setContentView(R.layout.activity_result);
			requestText = (TextView) findViewById(R.id.dollar);
			requestText.setText(request);
		}
	}


	public void touch(View view) {

		int tmpId = 0;

		view.setId(View.generateViewId());
		tmpId = view.getId();
		CharSequence content = view.getContentDescription();
		ImageButton clicked = (ImageButton) findViewById(tmpId);

		if (content == null) {
			clicked.setImageResource(R.drawable.element_without);
			return;
		}
		String cont = (view.getContentDescription().toString());
		kill = (ImageView) findViewById(R.id.killer);


		if (cont.equals("snake"))
			kill.setImageResource(R.drawable.slot_001);
		else if (cont.equals("scorpion"))
			kill.setImageResource(R.drawable.slot_002);
		else if (cont.equals("finish")) {
			kill.setImageResource(R.drawable.money);
			TextView finish = (TextView) findViewById(R.id.dialog_text);
			finish.setText("Ты помог мартышке найти золото!\nСыграем еще раз?");
		}
		dye.setVisibility(View.VISIBLE);
	}


	public void showWeb(View view) {
		WebView webView = (WebView) findViewById(R.id.web_view);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://www.banki.ru/products/currency/cash/moskva/");
		webView.setWebViewClient(new MyWebViewClient());
		webView.setVisibility(View.VISIBLE);
	}

	public void findDollarValue(){
		int tmp = 0;
		tmp = request.indexOf("USD");
		tmp = request.indexOf("Value", tmp);
		tmp = request.indexOf(" ", tmp);
		request = request.substring(tmp + 1, request.indexOf(',', tmp + 1));
	}

	private class MyWebViewClient extends WebViewClient {
		@TargetApi(Build.VERSION_CODES.N)
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
			view.loadUrl(request.getUrl().toString());
			return true;
		}

		// Для старых устройств
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
}