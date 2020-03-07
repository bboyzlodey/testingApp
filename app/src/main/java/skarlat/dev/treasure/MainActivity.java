package skarlat.dev.treasure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    Button      webView;
    Button      playGame;
    View        dye;
    ImageView   kill;
    Image hel;
    Drawable k;
    LinearLayout    linearLayout;

    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playGame = (Button) findViewById(R.id.play_game_btn);
        webView = (Button) findViewById(R.id.web_view_button);
    }

    public void     playGame(View view){
        setContentView(R.layout.activity_game);
        dye = (View) findViewById(R.id.dye_dialog);
    }

    public void     webViewClick(View view){
        char a = 'a';
    }


    public void     touch(View view){

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
        kill = (ImageView)findViewById(R.id.killer);


        if (cont.equals("snake"))
            kill.setImageResource(R.drawable.slot_001);
        else if (cont.equals("scorpion"))
            kill.setImageResource(R.drawable.slot_002);
        else if (cont.equals("finish"))
        {
            kill.setImageResource(R.drawable.money);
            TextView finish = (TextView) findViewById(R.id.dialog_text);
            finish.setText("Ты помог мартышке найти золото!\nСыграем еще раз?");
        }
        dye.setVisibility(View.VISIBLE);
    }
}
