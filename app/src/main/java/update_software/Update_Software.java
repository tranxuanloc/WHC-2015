package update_software;
		
import general.function.General;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
/**
		* This Class has functions to upload & download large files from server.
		* @author Vikrant  
		*/
public class Update_Software {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    Activity _Activity;
	String _Path = "/sdcard/Download/WHC-2015.apk";
    public Update_Software(Activity PActivity){
    	this._Activity= PActivity;
    }
    public void startDownload(String purl,String pPath) {
//        String url = "http://195.184.11.254:804/File_Download/WHC-2015.apk";
    	new File(pPath).delete();
    	_Path = pPath;
        new DownloadFileAsync().execute(purl);
    }
    
    
    private void installApk(){
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(new File(_Path));
//        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        _Activity.startActivity(intent);
        
        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        intent.setData( Uri.fromFile(new File(_Path)) );
        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
        _Activity.startActivity(intent);
    }
    
    
    
 class DownloadFileAsync extends AsyncTask<String, String, String> {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mProgressDialog = new ProgressDialog(_Activity);
				mProgressDialog.setMessage("Downloading file..");
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				mProgressDialog.setCancelable(false);
				mProgressDialog.show();
			}

			@SuppressLint("SdCardPath") @Override
			protected String doInBackground(String... aurl) {
				int count;
			General PCheckingInternet = new General(_Activity);
			String Result = null;
			if (PCheckingInternet.CheckingInternet())
			{
				try {

					URL url = new URL(aurl[0]);
					URLConnection conexion = url.openConnection();
					conexion.connect();

					int lenghtOfFile = conexion.getContentLength();

					InputStream input = new BufferedInputStream(url.openStream());
					OutputStream output = new FileOutputStream("/sdcard/Download/WHC-2015.apk");

					byte data[] = new byte[1024];

					long total = 0;

						while ((count = input.read(data)) != -1) {
							total += count;
							publishProgress(""+(int)((total*100)/lenghtOfFile));
							output.write(data, 0, count);
						}

						output.flush();
						output.close();
						input.close();
					} catch (Exception e) {
						Result = e.getMessage();
					}
			}
			else
			{
				Result = "Vui lòng kiểm tra lại wifi.";
			}
			
			return Result;

			}
			protected void onProgressUpdate(String... progress) {
				 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
			}

			@Override
			protected void onPostExecute(String unused) {
				mProgressDialog.dismiss();
				if (unused ==null)
				{
					  File file = new File(_Path); 
			            if(file.exists()){
			            	installApk();
			             }
				}
				else
				{
					new General(_Activity).showAlert(_Activity, "Download Error : " + unused);
				}
				
			}

		}
		 
}