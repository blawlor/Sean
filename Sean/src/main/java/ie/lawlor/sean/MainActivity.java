package ie.lawlor.sean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMenuButtons();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void setUpMenuButtons() {
        Button peopleButton = (Button) findViewById(R.id.peopleMenuButton);
        peopleButton.setOnClickListener(this);
        Button placesButton = (Button) findViewById(R.id.placesMenuButton);
        placesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.peopleMenuButton:
                showPeople();
                break;
            case R.id.placesMenuButton:
                showPlaces();
                break;
        }
    }


    private void showPeople() {
        Intent showPeopleActivity = new Intent(this, PeopleActivity.class);
        startActivity(showPeopleActivity);
    }


    private void showPlaces() {
        Intent showPlacesActivity = new Intent(this, PlacesActivity.class);
        startActivity(showPlacesActivity);

    }
}
