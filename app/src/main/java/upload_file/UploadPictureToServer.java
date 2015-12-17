package upload_file;

import general.function.ConnectionSQL;
import general.function.General;
import general.function.ResizeFileIMG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import scs.whc.R;
import upload_file.AndroidMultiPartEntity.ProgressListener;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import database.UserLogin;

public class UploadPictureToServer extends Activity {
	// LogCat tag
	private General pStatusinternet;
	private ProgressBar progressBar;
	private String filePath = null;
	private TextView txtPercentage;
	private ImageView imgPreview;
	private VideoView vidPreview;
	private Button btnUpload;
	long totalSize = 0;
	boolean isImage;
	private TextView txtOderID;
	private EditText EditDescription;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_uploadpicturetoserver);
		/**
		 * Menu bar
		 * */
		getActionBar().setDisplayHomeAsUpEnabled(true);
	    getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		
		
		pStatusinternet = new General(getApplicationContext());
		txtPercentage = (TextView) findViewById(R.id.txtUploadPictureToServer_Percentage);
		btnUpload = (Button) findViewById(R.id.CmdUploadPictureToServer_Upload);
		progressBar = (ProgressBar) findViewById(R.id.PssUploadPictureToServer_progressBar);
		imgPreview = (ImageView) findViewById(R.id.PriIMGUploadPictureToServer_Preview);
		vidPreview = (VideoView) findViewById(R.id.PriVideoUploadPictureToServer_Video);

		// Changing action bar background color
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor(getResources().getString(
						R.color.action_bar))));

		// Receiving the data from previous activity
		Intent i = getIntent();

		// image or video path that is captured in previous activity
		filePath = i.getStringExtra("filePath");

		// boolean flag to identify the media type, image or video
		isImage = i.getBooleanExtra("isImage", true);

		// Get OderID + Type
		String isDispatchingOderID = i.getStringExtra("OderID");
		txtOderID	= (TextView) findViewById(R.id.txtUploadPictureToServer_OderID);
		txtOderID.setText(isDispatchingOderID);
		
		// declare txtdescription
		EditDescription	= (EditText) findViewById(R.id.EditUploadPictureToServer_Description);
		if (filePath != null) {
			// Displaying the image or video on the screen
			previewMedia(isImage);
			
		} else {
			Toast.makeText(getApplicationContext(),
					"Sorry, file path is missing!", Toast.LENGTH_LONG).show();
		}

		btnUpload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String SDescription = "Attachment By Android";
				if (EditDescription.getText().toString().trim().length() > 0 )
				{
					SDescription = EditDescription.getText().toString();
					if (isImage)
					{
						
						try {
							// Resize file
							ResizeFileIMG pResizeFileIMG = new ResizeFileIMG();
							pResizeFileIMG.ResizeFile(filePath, 1280);
							// uploading the file to server
							File pFile = new File(filePath);
						
							if (InserIntoServerDO(SDescription,txtOderID.getText().toString(),pFile.getName() ,Integer.parseInt(String.valueOf(pFile.length()/1024))))
							{
//								Toast.makeText(getApplicationContext(), "Successfully.", Toast.LENGTH_SHORT).show();
								new UploadFileToServer().execute();
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Update Server Failed.", Toast.LENGTH_SHORT).show();
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							Toast.makeText(getApplicationContext(), "Resize Failed.", Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						int pSize = Integer.parseInt(String.valueOf(new File(filePath).length()/1024/1024));
						if (pSize > 2)
						{
							Toast.makeText(getApplicationContext(), "Cannot upload large files (2Mb).", Toast.LENGTH_SHORT).show();
						}
						else
						{
							// uploading the file to server
							File pFile = new File(filePath);
						
							if (InserIntoServerDO(SDescription,txtOderID.getText().toString(),pFile.getName(), Integer.parseInt(String.valueOf(pFile.length()/1024))))
							{
//								Toast.makeText(getApplicationContext(), "Successfully.", Toast.LENGTH_SHORT).show();
								new UploadFileToServer().execute();
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Update Server Failed.", Toast.LENGTH_SHORT).show();
							}
						}
					}
					
	
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please enter the Description !", Toast.LENGTH_SHORT).show();
				}

					
			}
		});
//End Create
	}
	
	  /**
     * Menu
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case android.R.id.home:
//			Toast.makeText(this, "home", Toast.LENGTH_SHORT)
//					.show();
			onBackPressed();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 super.onCreateOptionsMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Insert Into server
	 * */
	private Boolean InserIntoServerDO(String pDescription,String pOderID,String pFileName,int pSize){
		Boolean pStatus =false;
	   	if (pStatusinternet.CheckingInternet()) {
			UserLogin PUser = new UserLogin(getApplicationContext());
			ConnectionSQL pConnectionSQL = new ConnectionSQL(getApplicationContext());
			String PStrExec ="STAndroid_AttachmentInsert N'" + pOderID + "',N'"+ pFileName + "',N'" + PUser.getUser()+"'," + pSize +",N'"+ pDescription +"'";
			boolean PStatus = pConnectionSQL.ExecuteStringSwire( PStrExec);
			
			if (PStatus)
			{
				pStatus = true;
//				Toast.makeText(getApplicationContext(), "Upload Successfully", Toast.LENGTH_SHORT).show();
				if (pOderID.substring(0, 2).compareTo("CC") != 0)
				{
					pConnectionSQL.ExecuteString( "SP_WebContainerChecking_UpdateDetail " + pOderID.replace("CC-", "").toString() + ",N'',N'',N'',12");
				}
			}
			else
			{
				pStatus = false;
			}
		} else {
			Toast MsgInternet = Toast.makeText(getApplicationContext(),
					"Not Access Internet.", Toast.LENGTH_SHORT);
			MsgInternet.show();
		}
		return pStatus;
	}
	

	/**
	 * Displaying captured image/video on the screen
	 * */
	private void previewMedia(boolean isImage) {
		// Checking whether captured media is image or video
		if (isImage) {
			
			imgPreview.setVisibility(View.VISIBLE);
			vidPreview.setVisibility(View.GONE);
			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// down sizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;

			final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

			imgPreview.setImageBitmap(bitmap);
		} else {
			imgPreview.setVisibility(View.GONE);
			vidPreview.setVisibility(View.VISIBLE);
			vidPreview.setVideoPath(filePath);
			// start playing
			vidPreview.start();
		}
	}

	/**
	 * Uploading the file to server
	 * */
	private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			// setting progress bar to zero
			progressBar.setProgress(0);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			// Making progress bar visible
			progressBar.setVisibility(View.VISIBLE);

			// updating progress bar value
			progressBar.setProgress(progress[0]);

			// updating percentage value
			txtPercentage.setText(String.valueOf(progress[0]) + "%");
		}

		@Override
		protected String doInBackground(Void... params) {
			return uploadFile();
		}

		@SuppressWarnings({ "deprecation", "unused" })
		private String uploadFile() {
			String responseString = null;

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

			try {
				AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
						new ProgressListener() {

							@Override
							public void transferred(long num) {
								publishProgress((int) ((num / (float) totalSize) * 100));
							}
						});
				File sourceFile = new File(filePath);
				
				
				// Adding file data to http body
				entity.addPart("image", new FileBody(sourceFile));

//				 Extra parameters if you want to pass to server
//				entity.addPart("website",new StringBody("www.androidhive.info"));
//				entity.addPart("email", new StringBody("abc@gmail.com"));

				totalSize = entity.getContentLength();
				httppost.setEntity(entity);

				// Making server call
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity r_entity = response.getEntity();
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					// Server response
//					responseString = EntityUtils.toString(r_entity);
					responseString = "Upload Successfully!";
				} 
				else {
					responseString = "Error occurred! Http Status Code: "
							+ statusCode;
				}

			} catch (ClientProtocolException e) {
				responseString = e.toString();
			} catch (IOException e) {
				responseString = e.toString();
			}

			return responseString;
		}

		@Override
		protected void onPostExecute(String result) {
			// showing the server response in an alert dialog
			showAlert(result);

			super.onPostExecute(result);
		}

	}
	/**
	 * Method to show alert dialog
	 * */
	private void showAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setTitle("Response from Servers")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do nothing
						onBackPressed();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	
	
	
//	End Class
}
