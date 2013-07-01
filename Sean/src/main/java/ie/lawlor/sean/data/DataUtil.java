package ie.lawlor.sean.data;

import android.database.Cursor;

public class DataUtil {

  public static long getLong(Cursor cursor, String columnName) {
    return cursor.getLong(cursor.getColumnIndex(columnName));
  }

  public static String getString(Cursor cursor, String columnName) {
    return cursor.getString(cursor.getColumnIndex(columnName));
  }

  public static int getInteger(Cursor cursor, String columnName) {
    return cursor.getInt(cursor.getColumnIndex(columnName));
  }

  public static boolean getBoolean(Cursor cursor, String columnName) {
    return (cursor.getInt(cursor.getColumnIndex(columnName)) == 1 ? true : false);
  }

}
