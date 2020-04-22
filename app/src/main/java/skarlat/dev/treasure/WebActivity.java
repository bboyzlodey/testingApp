package skarlat.dev.treasure;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebActivity extends AppCompatActivity {
	Button webView;
	Button playGame;
	View dye;
	ImageView kill;
	String request;
	String currentView;
	private ImageButton animated;
	Bundle currentInstance;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);

		Runnable runnable = new Runnable() {
			public void run() {
				Runnable progress = new Runnable() {
					@Override
					public void run() {
						handler2.sendEmptyMessage(0);
					}
				};
				Thread thread = new Thread(progress);
				thread.start();
				request = doGet("http://www.cbr-xml-daily.ru/daily_json.js");
				handler.sendEmptyMessage(0);
				thread.interrupt();
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
	Handler handler2 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			setContentView(R.layout.activity_request);
		}
	};
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			printResult();
		}
	};

	public void printResult(){
		setContentView(R.layout.activity_result);
		TextView requestText = (TextView) findViewById(R.id.dollar);
		if (request == null && requestText != null)
			requestText.setText("Не получилось");
		else {
			findDollarValue();
			setContentView(R.layout.activity_result);
			requestText = (TextView) findViewById(R.id.dollar);
			requestText.setText(request);
		}
		WebView webView = (WebView) findViewById(R.id.web_view);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://www.banki.ru/products/currency/cash/moskva/");
		webView.setWebViewClient(new WebActivity.MyWebViewClient());
		currentView = "web";
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

	public void showWeb(View view) {
		WebView webView = (WebView) findViewById(R.id.web_view);
		webView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out));
		webView.setVisibility(View.VISIBLE);
		webView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));

	}

	public void findDollarValue(){
		int tmp = 0;
		if (request.contains("Failed"))
			return;
		tmp = request.indexOf("USD");
		tmp = request.indexOf("Value", tmp);
		tmp = request.indexOf(" ", tmp);
		request = request.substring(tmp + 1, request.indexOf(',', tmp + 1));
	}


}
