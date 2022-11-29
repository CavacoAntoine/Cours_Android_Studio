package seja.esiee.lanceur.tp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button tp1,tp2,tp3,tp4,tp5,tp6,tp7,tp8,tp9,tp10,tp11,tp12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bis);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = new Intent();
        tp1 = (Button)findViewById(R.id.button1);
        intent.setAction(getString(R.string.tp1));
        //tp1.setEnabled(isIntentAvailable(this,intent));
        //tp1.setBackgroundColor(getRandomColor());
        //setBackgroundColor(tp1);
        //tp1.setTextColor(getRandomColor());

        tp2 = (Button)findViewById(R.id.button2);
        intent.setAction(getString(R.string.tp2));
        //tp2.setEnabled(isIntentAvailable(this,intent));
        //setBackgroundColor(tp2);
        //tp2.setBackgroundColor(getRandomColor());
        //tp2.setTextColor(getRandomColor());

        tp3 = (Button)findViewById(R.id.button3);
        intent.setAction(getString(R.string.tp3));
        tp3.setEnabled(isIntentAvailable(this,intent));
        //setBackgroundColor(tp3);
        //tp3.setBackgroundColor(getRandomColor());
        //tp3.setTextColor(getRandomColor());

        tp4 = (Button)findViewById(R.id.button4);
        intent.setAction(getString(R.string.tp4));
        tp4.setEnabled(isIntentAvailable(this,intent));
        //setBackgroundColor(tp4);
        //tp4.setBackgroundColor(getRandomColor());
        //tp4.setTextColor(getRandomColor());

        tp5 = (Button)findViewById(R.id.button5);
        intent.setAction(getString(R.string.tp5));
        tp5.setEnabled(isIntentAvailable(this,intent));
        //setBackgroundColor(tp5);
        //tp5.setBackgroundColor(getRandomColor());
        //tp5.setTextColor(getRandomColor());

        tp6 = (Button)findViewById(R.id.button6);
        intent.setAction(getString(R.string.tp6));
        tp6.setEnabled(isIntentAvailable(this,intent));
        //setBackgroundColor(tp6);
        //tp6.setBackgroundColor(getRandomColor());
        //tp6.setTextColor(getRandomColor());

        tp7 = (Button)findViewById(R.id.button7);
        intent.setAction(getString(R.string.tp7));
        tp7.setEnabled(isIntentAvailable(this,intent));
        //setBackgroundColor(tp7);
        //tp7.setBackgroundColor(getRandomColor());
        //tp7.setTextColor(getRandomColor());

        tp8 = (Button)findViewById(R.id.button8);
        intent.setAction(getString(R.string.tp8));
        tp8.setEnabled(isIntentAvailable(this,intent));
        //setBackgroundColor(tp8);//
        // tp8.setBackgroundColor(getRandomColor());
        //tp8.setTextColor(getRandomColor());

        tp9 = (Button)findViewById(R.id.button9);
        intent.setAction(getString(R.string.tp9));
        tp9.setEnabled(isIntentAvailable(this,intent));
        //setBackgroundColor(tp9);//
        // tp9.setBackgroundColor(getRandomColor());
        //tp9.setTextColor(getRandomColor());

        tp10 = (Button)findViewById(R.id.button10);
        intent.setAction(getString(R.string.tp10));
        tp10.setEnabled(isIntentAvailable(this,intent));
        //setBackgroundColor(tp10);
        //tp10.setBackgroundColor(getRandomColor());
        //tp10.setTextColor(getRandomColor());

        tp11 = (Button)findViewById(R.id.button11);
        intent.setAction(getString(R.string.tp11));
        tp11.setEnabled(isIntentAvailable(this,intent));
        //setBackgroundColor(tp11);
        //tp11.setBackgroundColor(getRandomColor());
        //tp11.setTextColor(getRandomColor());

        tp12 = (Button)findViewById(R.id.button12);
        intent.setAction(getString(R.string.tp12));
        tp12.setEnabled(isIntentAvailable(this,intent));
        //tp12.setBackgroundColor(getRandomColor());
        //setBackgroundColor(tp12);
        //tp12.setTextColor(getRandomColor());

        View v = findViewById(R.id.v1);
        v.setBackgroundColor(getRandomColor());
        v = findViewById(R.id.v2);
        v.setBackgroundColor(getRandomColor());
        v = findViewById(R.id.v3);
        v.setBackgroundColor(getRandomColor());
    }

    public void onClickButton(View view){
        if(view instanceof Button) view.setBackgroundColor(getRandomColor());
        Toast.makeText(this,view.getTag().toString(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(view.getTag().toString());
        startActivity(intent);
    }

    //https://stackoverflow.com/questions/17823451/set-android-shape-color-programmatically/17825210
    private static void setBackgroundColor(Button btn){
        btn.setBackgroundColor(getRandomColor());
        Drawable background = btn.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(getRandomColor());
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(getRandomColor());
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable)background).setColor(getRandomColor());
        }
    }
    private static boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list = mgr.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private static int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

}