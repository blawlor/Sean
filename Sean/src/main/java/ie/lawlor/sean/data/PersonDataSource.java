package ie.lawlor.sean.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ie.lawlor.sean.domain.Person;

import static ie.lawlor.sean.data.DataUtil.getInteger;
import static ie.lawlor.sean.data.DataUtil.getLong;
import static ie.lawlor.sean.data.DataUtil.getString;
import static ie.lawlor.sean.data.PersonDataHelper.COLUMN_ID;
import static ie.lawlor.sean.data.PersonDataHelper.COLUMN_IMAGE_FILE_NAME;
import static ie.lawlor.sean.data.PersonDataHelper.COLUMN_NAME;
import static ie.lawlor.sean.data.PersonDataHelper.COLUMN_ORDER;
import static ie.lawlor.sean.data.PersonDataHelper.TABLE_PERSON;

public class PersonDataSource {

  private SQLiteDatabase database;

  private PersonDataHelper dbHelper;

  private static String[] ALL_PAGE_COLUMNS = { COLUMN_ID, COLUMN_NAME, COLUMN_ORDER, COLUMN_IMAGE_FILE_NAME };

  public PersonDataSource(Context context) {
    dbHelper = new PersonDataHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void openForTransaction() throws SQLException {
    database = dbHelper.getWritableDatabase();
    database.beginTransaction();
  }

  public void endTransaction() {
    database.endTransaction();
  }

  public void endTransactionAndClose() {
    database.endTransaction();
    database.close();
  }

  public void setTransactionSuccessful() {
    database.setTransactionSuccessful();
  }

  public void close() {
    dbHelper.close();
  }

  public Person createPage(String name, long catalogId, String imageFileName) {
    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME, name);
    values.put(COLUMN_ORDER, catalogId);
    values.put(COLUMN_IMAGE_FILE_NAME, imageFileName);
    long insertId = database.insert(TABLE_PERSON, null, values);
    Cursor cursor = database.query(TABLE_PERSON, ALL_PAGE_COLUMNS, COLUMN_ID + " = " + insertId, null, null, null, null);
    try {
      cursor.moveToFirst();
      Person newPerson = cursorToPerson(cursor);
      return newPerson;
    } finally {
      cursor.close();
    }
  }

  public void deletePerson(Person person) {
    long id = person.getId();
    database.delete(PersonDataHelper.TABLE_PERSON, PersonDataHelper.COLUMN_ID + " = " + id, null);
  }

  public List<Person> getAllPeople() {
    List<Person> persons = new ArrayList<Person>();

    Cursor cursor = database.query(PersonDataHelper.TABLE_PERSON, ALL_PAGE_COLUMNS, null, null, null, null, null);
    try {
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
        Person person = cursorToPerson(cursor);
        persons.add(person);
        cursor.moveToNext();
      }
      return persons;
    } finally {
      cursor.close();
    }
  }


  private Person cursorToPerson(Cursor cursor) {
    Person person = new Person(getLong(cursor, COLUMN_ID), getString(cursor, COLUMN_NAME), getInteger(cursor,
        COLUMN_ORDER), getString(cursor, COLUMN_IMAGE_FILE_NAME));
    return person;
  }

}
