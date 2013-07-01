package ie.lawlor.sean.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PersonDataHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 1;

  private static final String DATABASE_NAME = "person.db";

  public static final String TABLE_PERSON = "person";

  public static final String COLUMN_ID = "_id";

  public static final String COLUMN_NAME = "name";

  public static final String COLUMN_ORDER = "person_order";

  public static final String COLUMN_IMAGE_FILE_NAME = "image_file_name";

  private static final String PAGE_TABLE_CREATE = "CREATE TABLE " + TABLE_PERSON + " (" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_NAME + " text not null, " + COLUMN_ORDER
      + " integer not null, " + COLUMN_IMAGE_FILE_NAME + " text not null " + ");";


  PersonDataHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(PAGE_TABLE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(PersonDataHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
            + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
    onCreate(db);
  }
}
