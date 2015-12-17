package general.function;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class ResizeFileIMG {

	@SuppressWarnings("unused")
	public boolean ResizeFile(String Ppath,int desiredWidth) throws FileNotFoundException
	{
		// Get the source image's dimensions
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(Ppath, options);

		int srcWidth = options.outWidth;
		int srcHeight = options.outHeight;

		// Only scale if the source is big enough. This code is just trying to fit a image into a certain width.
		if(desiredWidth > srcWidth)
		    desiredWidth = srcWidth;

		// Calculate the correct inSampleSize/scale value. This helps reduce memory use. It should be a power of 2
		// from: http://stackoverflow.com/questions/477572/android-strange-out-of-memory-issue/823966#823966
		int inSampleSize = 1;
		while(srcWidth / 2 > desiredWidth){
		    srcWidth /= 2;
		    srcHeight /= 2;
		    inSampleSize *= 2;
		}
		
		float desiredScale = (float) desiredWidth / srcWidth;

		// Decode with inSampleSize
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inSampleSize = inSampleSize;
		options.inScaled = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap sampledSrcBitmap = BitmapFactory.decodeFile(Ppath, options);

		// Resize
		Matrix matrix = new Matrix();
		matrix.postScale(desiredScale, desiredScale);
		Bitmap scaledBitmap = Bitmap.createBitmap(sampledSrcBitmap, 0, 0, sampledSrcBitmap.getWidth(), sampledSrcBitmap.getHeight(), matrix, true);
		sampledSrcBitmap = null;

		// Save
		FileOutputStream out = new FileOutputStream(Ppath);
		scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);
		scaledBitmap = null;

		return false;
		
	}
	
	public void ResizeFileSlow(String Path,int dWidth, int dHeight){
	     Bitmap bitmap_Source = BitmapFactory.decodeFile(Path);
	     float factorH = dHeight / (float)bitmap_Source.getHeight();
	     float factorW = dWidth / (float)bitmap_Source.getWidth();
	     float factorToUse = (factorH > factorW) ? factorW : factorH;
	     Bitmap bm = Bitmap.createScaledBitmap(bitmap_Source, 
	       (int) (bitmap_Source.getWidth() * factorToUse), 
	       (int) (bitmap_Source.getHeight() * factorToUse), 
	       false);
	     
	     try {
	    	    FileOutputStream bmpFile = new FileOutputStream(Path);
	    	    bm.compress(Bitmap.CompressFormat.JPEG, 5, bmpFile);
	    	    bmpFile.flush();
	    	    bmpFile.close();
	    	} catch (Exception e) {
//	    	    Log.e("E", "Error on saving file");
	    	}
	    
	    }
	
	  public void ResizeFileFast(String Path,int dWidth, int dHeight){
		     BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		  bmpFactoryOptions.inJustDecodeBounds = true;
		  Bitmap bm = BitmapFactory.decodeFile(Path, bmpFactoryOptions);
		   
		  int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)dHeight);
		  int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)dWidth);
		   
		  if (heightRatio > 1 || widthRatio > 1)
		  {
		   if (heightRatio > widthRatio){
		    bmpFactoryOptions.inSampleSize = heightRatio;  
		   } else {
		    bmpFactoryOptions.inSampleSize = widthRatio;   
		   }   
		  }
//		  Log.e("E",Path );
		  bmpFactoryOptions.inJustDecodeBounds = false;
		  bm = BitmapFactory.decodeFile(Path, bmpFactoryOptions);
		  
		  try {
	    	    FileOutputStream bmpFile = new FileOutputStream(Path);
	    	    bm.compress(Bitmap.CompressFormat.JPEG, 5, bmpFile);
	    	    bmpFile.flush();
	    	    bmpFile.close();
	    	} catch (Exception e) {
//	    	    Log.e("E", "Error on saving file");
	    	}
		  
		  
	}

	  
	  
}
