package ie.lawlor.sean;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ViewFlipper;

import java.util.List;

import ie.lawlor.sean.data.PersonDataSource;
import ie.lawlor.sean.domain.Person;
import ie.lawlor.sean.utils.DisplayUtil;

public class PeopleActivity extends Activity implements View.OnClickListener {

    public static final int PICTURES_PER_VIEW = 8;
    public static final int PICTURES_PER_ROW = 4;
    private static final int  TAKE_PICTURE_CODE = 1;

    private PersonDataSource personDataSource = null;
    private List<Person> people;
    private int currentPage = 0; //TODO Save as part of state.
    private Button leftArrow;
    private Button rightArrow;
    private int numberOfPages;
    private Button selectedButton = null;
    private int selectedRow = -1;
    private int selectedColumn = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        personDataSource = new PersonDataSource(this);
        personDataSource.open();
        try {
        people = personDataSource.getAllPeople();
        } finally {
            personDataSource.close();
        }
        numberOfPages = people.size()/PICTURES_PER_VIEW;
        if (numberOfPages == 0) {
            numberOfPages = 1; // At last one page created
        }
        setUpPages();
        setUpArrows();
        displayPeople();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private void setUpPages() {
        ViewFlipper viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        if (viewFlipper.getChildCount() == 0){//If the view has not been created already
            for (int i = 0; i < numberOfPages;i++){
                // Inflate a picture_table and add it.
                View view = getLayoutInflater().inflate(R.layout.picture_table, null);
                viewFlipper.addView(view);
                setUpButtons(view);
            }
        }
    }

    private void setUpButtons(View view) {
        TableLayout tableLayout = (TableLayout)view;
        for (int i = 0; i < tableLayout.getChildCount(); i++){
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            final int rowNumber = i;
            for (int j = 0; j < row.getChildCount(); j++){
                final int columnNumber = j;
                Button personButton = (Button) row.getChildAt(j);
                personButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonClicked(v, rowNumber, columnNumber);
                    }
                });
            }
        }
    }

    private void buttonClicked(View v, int rowNumber, int columnNumber) {
        this.selectedButton = (Button) v;
        this.selectedRow = rowNumber;
        this.selectedColumn = columnNumber;
        if (buttonIsUnallocated(rowNumber, columnNumber)){
            takePicture();
        } else {
            // Display picture/speak picture name
        }
    }

    private boolean buttonIsUnallocated(int rowNumber, int columnNumber) {
        return ((currentPage*PICTURES_PER_VIEW)+ (rowNumber * PICTURES_PER_ROW) + columnNumber) >= people.size();
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, TAKE_PICTURE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        selectedButton.setBackgroundDrawable(new BitmapDrawable(imageBitmap));
    }

    private void setUpArrows() {
        leftArrow = (Button)findViewById(R.id.left);
        rightArrow = (Button) findViewById(R.id.right);
        leftArrow.setOnClickListener(this);
        rightArrow.setOnClickListener(this);
        leftArrow.setEnabled(false);
        rightArrow.setEnabled(false);
    }


    private void displayPeople() {
        // Distribute picture_table based on current page
        // Group into groups of 4 and display a maximum of two rows of 4.
        int rowNumber = 0;
        int columnNumber = 0;
        ViewFlipper viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        TableLayout currentTable = (TableLayout) viewFlipper.getChildAt(currentPage);
        List<Person> sublist = people.subList(currentPage*PICTURES_PER_VIEW, people.size());
        for (Person person: sublist){
            if (columnNumber < 4){ // Stay on current row and increment the column
                columnNumber++;
            }else {
                // Either create a new row, or stop and enable the 'next' button.
                if (rowNumber == 0){// Move to next row
                    rowNumber = 1;
                    columnNumber = 0;
                } else {//
                    rightArrow.setEnabled(true);
                }
            }
            addPerson(person, rowNumber, columnNumber, currentTable);
        }
    }

    private void addPerson(Person person, int rowNumber, int columnNumber, TableLayout currentTable) {
        TableRow row = (TableRow) currentTable.getChildAt(rowNumber);
        Button personButton = (Button)row.getChildAt(columnNumber);
        personButton.setBackgroundDrawable(DisplayUtil.createBitmapDrawable(this, person, 100, 160));// FIXME Magic numbers. Replace or remove
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left:
                goLeft();
                break;
            case R.id.right:
                goRight();
                break;
        }
    }

    private void goLeft() {
        currentPage--;
        if (currentPage == 0){
            leftArrow.setEnabled(false);
        }
    }

    private void goRight() {
        currentPage++;
        leftArrow.setEnabled(true);
    }
}
