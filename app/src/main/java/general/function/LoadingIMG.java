package general.function;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
public class LoadingIMG extends AsyncTask<String, String, Bitmap> {
//		private ProgressDialog pDialog;
		private  Bitmap bitmap;
		ImageView _imgView;
		public LoadingIMG(ImageView PimgViev){
			_imgView = PimgViev;
		}
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(_Activity);
//            pDialog.setMessage("Loading Image ....");
//            pDialog.show();
            
		}
	     protected Bitmap doInBackground(String... args) {
	    	 try {
		           bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

	    	} catch (Exception e) {
		          e.printStackTrace();
		    }
			return bitmap;
	     }

	     protected void onPostExecute(Bitmap image) {

	    	 if(image != null){
	    		 _imgView.setImageBitmap(image);
//	         pDialog.dismiss();
	    	 
	    	 }else{
		     
//	         pDialog.dismiss();
//	         Toast.makeText(_Activity, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
	    		 
	    	 }
	     }
	 } 
	
	
