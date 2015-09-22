package eclectic.dininghall;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private Spinner spinnerMeal, spinnerDiningHall;
    private TextView menuText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        menuText = (TextView)findViewById(R.id.labelMenu);
        addListenerOnSpinnerItemSelection();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinnerMeal = (Spinner)findViewById(R.id.spinnerMeal);
        spinnerDiningHall = (Spinner)findViewById(R.id.spinnerHall);
        spinnerMeal.setOnItemSelectedListener(new CustomOnItemSelectedListener(menuText, spinnerMeal, spinnerDiningHall));
        spinnerDiningHall.setOnItemSelectedListener(new CustomOnItemSelectedListener(menuText, spinnerMeal, spinnerDiningHall));
    }
}
