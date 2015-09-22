package eclectic.dininghall;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

    private TextView menu;
    private Spinner spinnerMeal, spinnerDiningHall;

    public CustomOnItemSelectedListener(TextView menu, Spinner meal, Spinner hall) {
        this.menu = menu;
        this.spinnerMeal = meal;
        this.spinnerDiningHall = hall;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {

        menu.setText("Loading menu");
        new WebThread().execute();
        /*Toast.makeText(parent.getContext(),
                "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_LONG).show();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    private class WebThread extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            return WebParser.postToMenus(spinnerMeal.getSelectedItem().toString(), "" + (spinnerDiningHall.getSelectedItemPosition() + 1));
        }

        protected void onPostExecute(String result) {
            menu.setText(result);
        }
    }

}