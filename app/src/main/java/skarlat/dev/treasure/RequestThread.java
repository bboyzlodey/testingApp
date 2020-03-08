package skarlat.dev.treasure;

public class RequestThread extends Thread {
	String request;
	MainActivity mainActivity;

	public RequestThread(MainActivity mainActivity)
	{
		super();
		this.mainActivity = mainActivity;
	}
	@Override
	public void run()
	{
		this.mainActivity.request = MainActivity.doGet("http://www.cbr-xml-daily.ru/daily_json.js");
	}
}
