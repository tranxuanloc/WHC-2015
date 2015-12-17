package takepicture;


import general.function.ConnectionSQL;
import general.function.General;
import general.function.MD5;
import general.function.NavigationDrawer;
import general.java.PullToRefreshListView;
import general.java.PullToRefreshListView.OnRefreshListener;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import scs.whc.R;
import upload_file.Config;
import upload_file.UploadPictureToServer;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("hiding")
@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") 
public class Note_TakePhoto<AlbumStorageDirFactory> extends Activity {
	private DrawerLayout drawerLayout;
	private General pStatusinternet;
	Connection Mycon;
	DataAdapter_NoteTakePhoto SAd;
	CallableStatement proc_stmt = null;
	ListView LV_NoteTakePhoto;
	

	
//	Call Uplaod
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    private static final int CAMERA_SCANNER_REQUEST_CODE = 300;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Uri fileUri; // file url to store image/video
    private Button CmdCapturePicture, CmdRecordVideo;
	private static TextView txtDispatchingOder;
	private PullToRefreshListView LV_IMG;
	private DataAdapter_NoteTakePhoto_ShowImage SAdIMG;
	private TextView tvResult;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint({ "NewApi", "CutPasteId" }) @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_notetakephoto);
		
		getActionBar().setTitle("Photo Notes");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		Intent myIntent = getIntent();
		String SOrderID = myIntent.getStringExtra("OrderID");
		
		
		
	
		tvResult = (TextView) findViewById(R.id.txtNoteTakePhoto_DispatchingOderID);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		CmdCapturePicture = (Button) findViewById(R.id.CmdNoteTakePhoto_TakePicture);
		CmdRecordVideo = (Button) findViewById(R.id.CmdNoteTakePhoto_Video);
		txtDispatchingOder =(TextView) findViewById(R.id.txtNoteTakePhoto_DispatchingOderID);

		if (SOrderID.toString().trim().length() > 0)
		{
			tvResult.setText(SOrderID);
			inicializarDO(SOrderID);
		    inicializarIMG(SOrderID);
		}
		
     /**
      * Capture image button click event
      */
     CmdCapturePicture.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
             // capture picture
        	 if (txtDispatchingOder.getText().toString().trim().length() == 0)
 			{
 				Toast.makeText(getApplicationContext(), "Please Scan a few.", Toast.LENGTH_SHORT).show();
 			}
 			else
 			{
 				captureImage();
 			}
         }
     });

     /**
      * Record video button click event
      */
     CmdRecordVideo.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
             // record video
        	 if (txtDispatchingOder.getText().toString().trim().length() == 0)
  			{
  				Toast.makeText(getApplicationContext(), "Please Scan a few.", Toast.LENGTH_SHORT).show();
  			}
  			else
  			{
  				recordVideo();
  			}
             
         }
     });

     // Checking camera availability
     if (!isDeviceSupportCamera()) {
         Toast.makeText(getApplicationContext(),
                 "Sorry! Your device doesn't support camera",
                 Toast.LENGTH_LONG).show();
         // will close the app if the device does't have camera
         finish();
     }
//	End Create
	}
	
	  /**
     * Menu
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Action_Setting:
			if (drawerLayout.isDrawerVisible(Gravity.START)) {
				drawerLayout.closeDrawer(Gravity.START);
			} else {
				drawerLayout.openDrawer(Gravity.START);
			}
			break;
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.Action_Refresh:
			if (txtDispatchingOder.getText().toString().trim().length() > 0)
			{
				inicializarIMG(txtDispatchingOder.getText().toString().trim());
			}
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_setting_refresh, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
 
	
	public void onClickScan(View v) {
		// respond to clicks
		if (v.getId() == R.id.CmdNoteTakePhoto_Scan) {
			// scan
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
//			intent.putExtra("SCAN_FORMATS", "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF");
			startActivityForResult(intent, CAMERA_SCANNER_REQUEST_CODE); 
		}
	}
	
	private void inicializarIMG(String pSreach) {
		pStatusinternet = new General(getApplicationContext());
		LV_IMG = (PullToRefreshListView) findViewById(R.id.LVNoteTakePhoto_ShowIMG);
		if (pStatusinternet.CheckingInternet()) {
			Mycon = new ConnectionSQL(getApplicationContext()).ConnSwire();
			SAdIMG = new DataAdapter_NoteTakePhoto_ShowImage(Note_TakePhoto.this,getApplicationContext(), QueryStringIMG(pSreach));
			LV_IMG.setAdapter(SAdIMG);
		} else {
			Toast MsgInternet = Toast.makeText(getApplicationContext(),
					"Not Access Internet.", Toast.LENGTH_SHORT);
			MsgInternet.show();
		}
		
		LV_IMG.setOnItemClickListener(new OnItemClickListener() {
			TextView txtfilename;
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				txtfilename = (TextView)view.findViewById(R.id.txtShowTakePicture_FileName);
				
				Intent MyIntenFileName ;
				MyIntenFileName =  new Intent(Note_TakePhoto.this, ViewIMG.class);
				MyIntenFileName.putExtra("FileName", txtfilename.getText().toString());
				startActivity(MyIntenFileName);
			}
		});
		
		LV_IMG.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// Your code to refresh the list contents goes here
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						inicializarIMG(txtDispatchingOder.getText().toString().trim());
						LV_IMG.onRefreshComplete();
					}
				}, 2000);
			}
		});
		
	}
	
	public ArrayList<HashMap<String, String>> QueryStringIMG(String CommandoSQL) {
		ResultSet rus;
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		try {
			Statement Statement1 = Mycon.createStatement();
			rus = Statement1.executeQuery("STAttachmentView N'" + CommandoSQL +"'");
			

			// day dl len listview
			while (rus.next()) {
				HashMap<String, String> datacolum = new HashMap<String, String>();
				datacolum.put("AttachmentID", rus.getString("AttachmentID"));
				datacolum.put("OrderNumber", rus.getString("OrderNumber"));
				datacolum.put("AttachmentFile", rus.getString("AttachmentFile"));
				datacolum.put("AttachmentUser", rus.getString("AttachmentUser"));
				datacolum.put("AttachmentFileSize", rus.getString("AttachmentFileSize"));
				datacolum.put("OriginalFileName", rus.getString("OriginalFileName"));
				datacolum.put("AttachmentDescription", rus.getString("AttachmentDescription"));
				if (rus.getString("AttachmentDate") == null)
				{
					datacolum.put("AttachmentDate", "");
				}
				else
				{
					datacolum.put("AttachmentDate",new General(getApplicationContext()).FormatDateIn_ddMMHHMM(rus.getString("AttachmentDate")));
				}
				data.add(datacolum);
//				break;
			}
		} catch (java.sql.SQLException e) {
		}
		return data;
	}


		private void inicializarDO(String pSreach) {
			pStatusinternet = new General(getApplicationContext());
			LV_NoteTakePhoto = (ListView) findViewById(R.id.LVNoteTakePhoto_Information);
			if (pStatusinternet.CheckingInternet()) {
				Mycon = new ConnectionSQL(getApplicationContext()).ConnSwire();
				SAd = new DataAdapter_NoteTakePhoto(getApplicationContext(),QueryStringDO(pSreach));
				LV_NoteTakePhoto.setAdapter(SAd);
			} else {
				 Toast.makeText(getApplicationContext(),"Not Access Internet.", Toast.LENGTH_SHORT).show();
			}
		}
		
		public ArrayList<HashMap<String, String>> QueryStringDO(String CommandoSQL) {
			ResultSet rus;
			ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
			try {
				Statement Statement1 = Mycon.createStatement();
//				Toast.makeText(getApplicationContext(),CommandoSQL, Toast.LENGTH_SHORT).show();
				rus = Statement1.executeQuery("STAndroid_BarcodeScan_PhotoNote N'" + CommandoSQL + "'");
				

				// day dl len listview
				while (rus.next()) {
					HashMap<String, String> datacolum = new HashMap<String, String>();
					datacolum.put("DispatchingOrderID", rus.getString("OrderID"));
					datacolum.put("CustomerNumber", rus.getString("CustomerNumber"));
					datacolum.put("CustomerName", rus.getString("CustomerName"));
					datacolum.put("DispatchingOrderNumber", rus.getString("OrderNumber"));
					datacolum.put("SpecialRequirement", rus.getString("SpecialRequirement"));
					datacolum.put("DockNumber", rus.getString("DockNumber"));

					if (rus.getString("OrderDate") == null)
					{
						datacolum.put("DispatchingOrderDate","");
					}
					else{
						datacolum.put("DispatchingOrderDate",rus.getString("OrderDate").substring(0, 10));
					}
					data.add(datacolum);
					break;
				}
			} catch (java.sql.SQLException e) {
			}
			return data;
		}


//	Call Upload
			
			 /**
		     * Checking device has camera hardware or not
		     * */
		    private boolean isDeviceSupportCamera() {
		        if (getApplicationContext().getPackageManager().hasSystemFeature(
		                PackageManager.FEATURE_CAMERA)) {
		            // this device has a camera
		            return true;
		        } else {
		            // no camera on this device
		            return false;
		        }
		    }
		 
		    /**
		     * Launching camera app to capture image
		     */
		    private void captureImage() {
		        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		 
		        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		 
		        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		 
		        // start the image capture Intent
		        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
		    }
		    
		    /**
		     * Launching camera app to record video
		     */
		    private void recordVideo() {
		        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		 
		        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
		 
		        // set video quality
		        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		 
		        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
		                                                            // name
		 
		        // start the video capture Intent
		        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
		    }
		 
		    /**
		     * Here we store the file url as it will be null after returning from camera
		     * app
		     */
		    @Override
		    protected void onSaveInstanceState(Bundle outState) {
		        super.onSaveInstanceState(outState);
		 
		        // save file url in bundle as it will be null on screen orientation
		        // changes
		        outState.putParcelable("file_uri", fileUri);
		    }
		 
		    @Override
		    protected void onRestoreInstanceState(Bundle savedInstanceState) {
		        super.onRestoreInstanceState(savedInstanceState);
		 
		        // get the file url
		        fileUri = savedInstanceState.getParcelable("file_uri");
		    }
		 
		    
		 
		    /**
		     * Receiving activity result method will be called after closing the camera
		     * */
		    @Override
		    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		        // if the result is capturing Image
		        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
		            if (resultCode == RESULT_OK) {
		            	// successfully captured the image
		                // launching upload activity
		            	launchUploadActivity(true);
		            } else if (resultCode == RESULT_CANCELED) {
		            	// user cancelled Image capture
		                Toast.makeText(getApplicationContext(),
		                        "User cancelled image capture", Toast.LENGTH_SHORT)
		                        .show();
		            
		            } else {
		                // failed to capture image
		                Toast.makeText(getApplicationContext(),
		                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
		                        .show();
		            }
		        
		        } else 
		        	if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
		            if (resultCode == RESULT_OK) {
		                
		            	// video successfully recorded
		                // launching upload activity
		            	launchUploadActivity(false);
		            
		            } else if (resultCode == RESULT_CANCELED) {
		                
		            	// user cancelled recording
		                Toast.makeText(getApplicationContext(),
		                        "User cancelled video recording", Toast.LENGTH_SHORT)
		                        .show();
		            
		            } else {
		                // failed to record video
		                Toast.makeText(getApplicationContext(),
		                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
		                        .show();
		            }
		        }
		        	else
		        	{

		    		    if (requestCode == CAMERA_SCANNER_REQUEST_CODE) {
//		    		      TextView tvStatus=(TextView)findViewById(R.id.tvStatus);
		    		     
		    		      if (resultCode == RESULT_OK) {
//		    		        tvStatus.setText(intent.getStringExtra("SCAN_RESULT_FORMAT"));
//		    		        tvResult.setText(intent.getStringExtra("SCAN_RESULT"));
		    		        String scanContent= data.getStringExtra("SCAN_RESULT");
		    		        if ((scanContent.substring(0, 2).toString().trim().equals("DO"))||(scanContent.substring(0, 2).toString().trim().equals("RO")))
		    		        {
		    		        	inicializarDO(scanContent);
				    		    inicializarIMG(scanContent.substring(0, 2).toString()+"-" + Integer.parseInt(scanContent.substring(scanContent.length() - 9)));
				    		    tvResult.setText(scanContent.substring(0, 2).toString()+"-" + Integer.parseInt(scanContent.substring(scanContent.length() - 9)));
		    		        }
		    		        else
		    		        {
		    		        	  Toast.makeText(getApplicationContext(), "Please Scan a few.", Toast.LENGTH_SHORT).show();
		    		        }
		    		       
		    		      } else if (resultCode == RESULT_CANCELED) {
		    		        tvResult.setText("Scan cancelled.");
		    		      }
		    		    }
		        	}
		    }
		    
		    private void launchUploadActivity(boolean isImage){
		    	Intent i = new Intent(Note_TakePhoto.this, UploadPictureToServer.class);
		        i.putExtra("filePath", fileUri.getPath());
		        i.putExtra("isImage", isImage);
		        i.putExtra("OderID", txtDispatchingOder.getText().toString().trim());
		        startActivity(i);
		    }
		     
		    /**
		     * ------------ Helper Methods ---------------------- 
		     * */
		    /**
		     * Creating file uri to store image/video
		     */
		    public Uri getOutputMediaFileUri(int type) {
		        return Uri.fromFile(getOutputMediaFile(type));
		        
		    }
		 
		    /**
		     * returning image / video
		     */
		    @SuppressWarnings("static-access")
			private static File getOutputMediaFile(int type) {
		 
		        // External sdcard location
		        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),Config.IMAGE_DIRECTORY_NAME);
		 
		        // Create the storage directory if it does not exist
		        if (!mediaStorageDir.exists()) {
		            if (!mediaStorageDir.mkdirs()) {
		                return null;
		            }
		        }
		 
		        // Create a media file name
		        MD5 pMD5 = new MD5();
		        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(new Date());
		        File mediaFile;
		        
		        if (type == MEDIA_TYPE_IMAGE) {
					String SFileNameImg =pMD5.MD5_Encrypt(txtDispatchingOder.getText().toString()+"IMG_" + timeStamp + ".jpg"); 
		            mediaFile = new File(mediaStorageDir.getPath() + File.separator+ SFileNameImg + ".jpg");
		        } else if (type == MEDIA_TYPE_VIDEO) {
		        	String SFileNameVid =pMD5.MD5_Encrypt(txtDispatchingOder.getText().toString()+"VID_" + timeStamp + ".mp4"); 
		            mediaFile = new File(mediaStorageDir.getPath() + File.separator+ "VID_" + SFileNameVid + ".mp4");
		        } else {
		            return null;
		        }
		 
		        return mediaFile;
		    }
			
	
//	End Class
}
