package general.function;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class General {

	Context context;
	String sHour, sMinute, sDay, sMonth, sYear;
	boolean Check_internet;
	
	  public General(Context context) {
	        this.context = context;
	}
	public String FormatDateIn_ddMMHHMM(String pDate) {
		String sResurl = "";
		if (pDate.equals("")) {
			return sResurl;
		} else {
			String[] Mang = pDate.split(" ");
			String[] sdate = Mang[0].split("-");
			sYear = sdate[0];
			sMonth = sdate[1];
			sDay = sdate[2];

			String[] stime = Mang[1].split(":");
			sHour = stime[0];
			sMinute = stime[1];
			sResurl = sDay + "/" + sMonth + " " + sHour + ":" + sMinute;
			return sResurl;
		}
	}
	
	public String FormatDate_ddMMYYYY(String pDate) {
		String sResurl = "";
		if (pDate.equals("")) {
			return sResurl;
		} else {
			String[] Mang = pDate.split(" ");
			String[] sdate = Mang[0].split("-");
			sYear = sdate[0];
			sMonth = sdate[1];
			sDay = sdate[2];

			String[] stime = Mang[1].split(":");
			sHour = stime[0];
			sMinute = stime[1];
			sResurl = sDay + "/" + sMonth + "/" + sYear;
			return sResurl;
		}
	}

	public String FormatDateLastChek(String pDate) {
		String sResurl = "";
		if (pDate == "") {
			return sResurl;
		} else {
			String[] Mang = pDate.split(" ");
			String[] sdate = Mang[0].split("-");
			sYear = sdate[0];
			sMonth = sdate[1];
			sDay = sdate[2];

			String[] stime = Mang[1].split(":");
			sHour = stime[0];
			sMinute = stime[1];
			sResurl = " LastCheck: " + sDay + "/" + sMonth + " " + sHour + ":"
					+ sMinute;
			return sResurl;
		}
	}
	
	
	public String FormatDate_ddMMYY(String pDate) {
		String sResurl = "";
		if (pDate == "") {
			return sResurl;
		} else {
			String[] Mang = pDate.split(" ");
			String[] sdate = Mang[0].split("-");
			sYear = sdate[0];
			sMonth = sdate[1];
			sDay = sdate[2];

			String[] stime = Mang[1].split(":");
			sHour = stime[0];
			sMinute = stime[1];
			sResurl =  sDay + "/" + sMonth +"/"+ sYear.substring(2,sYear.length()) ;
			return sResurl;
		}
	}
	
	public String FormatDate_MMddYYYY(String pDate) {
		String sResurl = "";
		if (pDate == "") {
			return sResurl;
		} else {
			String[] Mang = pDate.split(" ");
			String[] sdate = Mang[0].split("-");
			sYear = sdate[0];
			sMonth = sdate[1];
			sDay = sdate[2];

			String[] stime = Mang[1].split(":");
			sHour = stime[0];
			sMinute = stime[1];
			sResurl =  sMonth + "/" + sDay +"/"+ sYear ;
			return sResurl;
		}
	}
	
	public boolean ConvertStringToChecked(String PString) {
		boolean Checked = false;
		if (PString.equals("1"))
		{
			Checked = true;
		}
		return Checked;
	}
	
	
	public String FormatDateFull(String pDate) {
		String sResurl = "";
		if (pDate == "") {
			return sResurl;
		} else {
			String[] Mang = pDate.split(" ");
			String[] sdate = Mang[0].split("-");
			sYear = sdate[0];
			sMonth = sdate[1];
			sDay = sdate[2];

			String[] stime = Mang[1].split(":");
			sHour = stime[0];
			sMinute = stime[1];
			sResurl =  sMonth + "/" + sDay +"/"+ sYear +" "+ sHour +":"+ sMinute +":00";

			return sResurl;
		}
	}
	
	public String FormatHour(String pDate) {
		String sResurl = "";
		if (pDate == "") {
			return sResurl;
		} else {
			String[] Mang = pDate.split(" ");
			@SuppressWarnings("unused")
			String[] sdate = Mang[0].split("-");
			String[] stime = Mang[1].split(":");
			sHour = stime[0];
			sMinute = stime[1];
			sResurl =  sHour + ":" + sMinute ;
			return sResurl;
		}
	}
	
//	public String Convert_ddMMYYYY_TO_MMddYYYY(String pDate) {
//		String sResurl = "";
//		if (pDate == "") {
//			return sResurl;
//		} else {
//			String[] Mang = pDate.split(" ");
//			String[] sdate = Mang[0].split("-");
//			sYear = sdate[2];
//			sMonth = sdate[1];
//			sDay = sdate[0];
//			sResurl = sMonth +"/"+sDay +"/ "+sYear ;
//			return sResurl;
//		}
//	}
//	
	@SuppressLint("SimpleDateFormat") 
	public long calculationTimeMinutes(String pTimeStart) {
		long sResurl = 0;
		if (pTimeStart == "") {
			return sResurl;
		} else {
			SimpleDateFormat format = new SimpleDateFormat(
					"MM/dd/yyyy HH:mm:ss");
			Date d1 = null;

			try {
				d1 = format.parse(pTimeStart);
				Date d2 = new Date();
				// Toast.makeText(context, "" + d1, Toast.LENGTH_SHORT).show();
				// in milliseconds
				long diff = d2.getTime() - d1.getTime();
				@SuppressWarnings("unused")
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				long SDiffTime = (diffDays * 24 * 60) + (diffHours * 60)
						+ diffMinutes;
				sResurl = SDiffTime;
//				Toast.makeText(
//						context,
//						"" + diff + "-" + diffSeconds + "-" + diffMinutes + "-"
//								+ diffHours + "-" + diffDays,
//						Toast.LENGTH_SHORT).show();

			} catch (Exception e) {
				sResurl = 0;
			}
		}
		return sResurl;
	}
	
	
	public long calculationTimeMinutes(String pTimeStart, String pTimeEnd) {
		long sResurl = 0;
		if (pTimeStart == "") {
			return sResurl;
		} else {
			SimpleDateFormat format = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss");
			Date d1 = null;
			Date d2 = null;

			try {
				d1 = format.parse(pTimeStart);
				d2 = format.parse(pTimeEnd);
				// Toast.makeText(context, "" + d1, Toast.LENGTH_SHORT).show();
				// in milliseconds
				long diff = d2.getTime() - d1.getTime();
				@SuppressWarnings("unused")
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				long SDiffTime = (diffDays * 24 * 60) + (diffHours * 60)
						+ diffMinutes;
				sResurl = SDiffTime;
//				Toast.makeText(
//						context,
//						"" + diff + "-" + diffSeconds + "-" + diffMinutes + "-"
//								+ diffHours + "-" + diffDays,
//						Toast.LENGTH_SHORT).show();

			} catch (Exception e) {
				sResurl = 0;
			}
		}
		return sResurl;
	}

	public boolean CheckingInternet() {
		ConnectivityManager connect_wifi = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = connect_wifi.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						Check_internet = true;
				}
			}

		} else {
			Check_internet = false;
		}
		boolean _Status = Check_internet;
		if (Check_internet)
		{
			if (ping("195.184.11.230"))
			{
				_Status = true;
			}
			else
			{
				_Status = false;
			}
			
			
			
//			if (getWifiName(context).toString().trim().length() > 0)
//			{
//				String NameWifi = getWifiName(context).toString().trim().substring(1,getWifiName(context).toString().trim().length() -1);
////				Log.e("name", NameWifi);
//				if (NameWifi.toString().trim().equals("SCSVN-LinkSys") || NameWifi.toString().trim().equals("WHC")||NameWifi.toString().trim().equals("Guestswire") )
//				{
//					_Status = true;
//				}
//				else
//				{
//					_Status = false;
//				}
//			}
//			else
//			{
//				_Status = false;
//			}
			
		}
		else
		{
			_Status = false;
		}
		return _Status;
	}
	
	public String getWifiName(Context context) {
		String NameWifi = null;
	    WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	    if (manager.isWifiEnabled()) {
	       WifiInfo wifiInfo = manager.getConnectionInfo();
	       if (wifiInfo != null) {
	          DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
	          if (state == DetailedState.CONNECTED || state == DetailedState.OBTAINING_IPADDR) {
	        	  NameWifi =wifiInfo.getSSID();
	          }
	          else
	          {
	        	  NameWifi = null;
	          }
	       }
	       else
	       {
	    	   NameWifi = null;
	       }
	    }
	    else
	    {
	    	NameWifi = null;
	    }
	    return NameWifi;
	}
	private static String getLocalIpAddress() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
	                    return inetAddress.getHostAddress();
	                }
	            }
	        }
	    } catch (SocketException ex) {
	    	 return null;
	    }
	    return null;
	}
	
	public boolean CheckingWifiIp() {
		boolean Check_internet = false;
		if (CheckingInternet())
		{
			if (getLocalIpAddress() != null)
			{
				if (getLocalIpAddress().substring(0, 10).equals("195.184.11"))
				{
					Check_internet= true;
				}
			}
			else
			{
				Check_internet= false;
			}
		}
		else
		{
			Check_internet= false;
		}
		return Check_internet;
	}
	@SuppressWarnings("unused")
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	public int ConvertStringToInt(String str)  
	{  
		int Number = 0;
	  try  
	  {  
		  Number = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
		  Number = 0;
	  }  
	  return Number;  
	}
	
	public static String FormatString(String PFormat,String Source)
	{
		String SResults = "";
		
		SResults = new DecimalFormat(PFormat).format(Integer.parseInt(Source));
		
		return SResults;
		
	}
	
	 @SuppressWarnings("unused")
	private Drawable LoadImageFromWebOperations(String url)
	    {
	          try{
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	      }catch (Exception e) {
	        System.out.println("Exc="+e);
	        return null;
	      }
	    }
	
	 public void startWebView(final Activity PActivity,WebView webView, String url) {
		    
		    //Create new webview Client to show progress dialog
		    //When opening a url or click on link
		 
		    webView.setWebViewClient(new WebViewClient() {
		    	ProgressDialog progressDialog;
		        //If you will not use this method url links are opeen in new brower not in webview
		        public boolean shouldOverrideUrlLoading(WebView view, String url) {
		            view.loadUrl(url);
		            return true;
		        }
		    
		        //Show loader on url load
		        public void onLoadResource (WebView view, String url) {
		            if (progressDialog == null) {
		                // in standard case YourActivity.this
		                progressDialog = new ProgressDialog(PActivity);
		                progressDialog.setMessage("Loading...");
		                progressDialog.show();
		            }
		        }
		        public void onPageFinished(WebView view, String url) {
		            try{
		            if (progressDialog.isShowing()) {
		                progressDialog.dismiss();
//		                progressDialog = null;
		            }
		            }catch(Exception exception){
		                exception.printStackTrace();
		            }
		        }
		         
		    }); 
		      
		     // Javascript inabled on webview  
		    webView.getSettings().setJavaScriptEnabled(true); 
		     
		    // Other webview options
		  
		    webView.getSettings().setLoadWithOverviewMode(true);
		    webView.getSettings().setUseWideViewPort(true);
		    webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		    webView.setScrollbarFadingEnabled(false);
		    webView.getSettings().setBuiltInZoomControls(true);
		    
		     
		    /*
		     String summary = "<html><body>You scored <b>192</b> points.</body></html>";
		     webview.loadData(summary, "text/html", null); 
		     */
		     
		    //Load url in webview
		    webView.loadUrl(url);
		}

	 
	 
	 
	   public void showAlert(Activity PActivity, String pMessage) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PActivity);
	        alertDialog.setTitle("Thông báo ...");
	        alertDialog
	                .setMessage(pMessage);
	        alertDialog.setNegativeButton("Ok",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.cancel();
	                    }
	                });
	        alertDialog.show();
	    }
	 
	   public void showAlertShowActivity(final Activity PActivity,final Class<?> NewActivity,String pMessage) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PActivity);
	        alertDialog.setTitle("Thông báo ...");
	        alertDialog.setMessage(pMessage);
	        alertDialog.setPositiveButton("Cập Nhật",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        Intent Newintent ;
	                        Newintent = new Intent(PActivity, NewActivity);
	                        PActivity.startActivity(Newintent);
	                    }
	                });
	        alertDialog.setNegativeButton("Để Sau",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.cancel();
	                    }
	                });
	        alertDialog.show();
	    }
	   public  boolean ping(String host){
			boolean Result = false;
	        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
	        ProcessBuilder processBuilder = new ProcessBuilder("ping", isWindows? "-n" : "-c", "1", host);
	        Process proc;
			try {
				proc = processBuilder.start();
				 int returnVal = proc.waitFor();
				 if (returnVal == 0)
				 {
					 Result = true;
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return Result;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				return Result;
			}

			 return Result ;
	    }
	 
	 
//End Class

}