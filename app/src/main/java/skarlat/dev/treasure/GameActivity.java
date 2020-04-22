package skarlat.dev.treasure;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class GameActivity extends AppCompatActivity {
	View dye;
	ImageView kill;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		dye = (View) findViewById(R.id.dye_dialog);
	}
	public void touch(View view) {

		int tmpId = 0;

		view.setId(View.generateViewId());
		tmpId = view.getId();
		CharSequence content = view.getContentDescription();
		ImageButton clicked = (ImageButton) findViewById(tmpId);

		if (content == null) {
			clicked.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out));
			clicked.setImageResource(R.drawable.element_without);
			clicked.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
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
	public void retry(View view){
		setContentView(R.layout.activity_game);
	}
}
