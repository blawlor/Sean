package ie.lawlor.sean.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import ie.lawlor.sean.domain.Person;

public class DisplayUtil {

  private static Bitmap createBitmap(Person person, int reqWidth, int reqHeight) {
    return createBitmapFromFilename(person.getImageFileName(), reqWidth, reqHeight);
  }

  private static Bitmap createBitmapFromFilename(String filename, int reqWidth, int reqHeight) {
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(ContentUtil.getImagePath(filename), options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;

    Bitmap bm = BitmapFactory.decodeFile(ContentUtil.getImagePath(filename), options);

    return bm;

  }

  public static Drawable createBitmapFileDrawable(Activity activity, String filename,
      int reqWidth, int reqHeight) {
    return new BitmapDrawable(activity.getResources(),
        createBitmapFromFilename(filename, reqWidth, reqHeight));
  }

  public static BitmapDrawable createBitmapDrawable(Context context, int resId, int reqWidth, int reqHeight) {
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(context.getResources(), resId, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;

    Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId, options);

    return new BitmapDrawable(context.getResources(), bm);
  }

  public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

      // Calculate ratios of height and width to requested height and width
      final int heightRatio = Math.round((float) height / (float) reqHeight);
      final int widthRatio = Math.round((float) width / (float) reqWidth);

      // Choose the smallest ratio as inSampleSize value, this will guarantee
      // a final image with both dimensions larger than or equal to the
      // requested height and width.
      inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    return inSampleSize;
  }

  public static BitmapDrawable createBitmapDrawable(Context context, Person person, int reqWidth, int reqHeight) {
    Bitmap bm = createBitmap(person, reqWidth, reqHeight);
    return new BitmapDrawable(context.getResources(), bm);
  }

  public static Dimension getDimensionInDp(Context context, Bitmap bm) {
    int bmWidthDp = pxToDp(context, bm.getWidth());
    int bmHeightDp = pxToDp(context, bm.getHeight());
    return new Dimension(bmWidthDp, bmHeightDp);
  }

  public static int dpToPx(Context context, int dp) {
    float density = context.getResources().getDisplayMetrics().density;
    return Math.round((float) dp * density);
  }

  public static int pxToDp(Context context, int px) {
    float density = context.getResources().getDisplayMetrics().density;
    return Math.round((float) px / density);
  }

  public static class Dimension {
    private final int x;

    private final int y;

    public Dimension(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

  }

}
