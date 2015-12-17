package general.function;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") public class ConnectionSQL 
{
	//connect SQL
	
	Context context;
	Connection Mycon;
	Statement proc_stmt = null;
	String _user="ManagerUser";
	String _Pass="Hio09NMU";
	String _DB="DocumentDB";
	String _Server= "195.184.11.230";
	public ConnectionSQL(Context pcontext)
	{
		 this.context = pcontext;
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") 
	public Connection ConnDC()
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Connection conn = null;
		String ConnURL = null;
			try {
				
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				ConnURL = "jdbc:jtds:sqlserver://" + _Server + ";databaseName=" + _DB +";user=" + _user +";password=" + _Pass;
				DriverManager.setLoginTimeout(5);
					conn = DriverManager.getConnection(ConnURL);

			} catch (ClassNotFoundException e){
				return conn;
			} catch (java.sql.SQLException e) {
				return conn;
			} 
			catch (Exception e) {
				return conn;
			} 
		return conn;
	}

	
	public boolean ExecuteString (String CommandoSQL)
	{
		
		try {
			int rus;
			Mycon = new ConnectionSQL(context).ConnDC();
			proc_stmt =Mycon.createStatement();
			rus = proc_stmt.executeUpdate(CommandoSQL);
			if (rus > 0)
			{
				return true;
				
			}
			else
			{
				return false;
			}
		} catch (java.sql.SQLException e) {
			return false;
		}
	}
	
	public Connection ConnSwire()
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Connection conn = null;
		String ConnURL = null;
			try {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				ConnURL = "jdbc:jtds:sqlserver://" + _Server + ";databaseName=SwireDB;user=WMSUser ;password=WMSU2008";
				DriverManager.setLoginTimeout(5);
				conn = DriverManager.getConnection(ConnURL);
			} catch (ClassNotFoundException e){
				return conn;
			} catch (java.sql.SQLException e) {
				return conn;
			} 
			catch (Exception e) {
				return conn;
			} 
		return conn;
	}

	
	public boolean ExecuteStringSwire (String CommandoSQL)
	{
		
		try {
			int rus;
			Mycon = new ConnectionSQL(context).ConnSwire();
			proc_stmt =Mycon.createStatement();
			rus = proc_stmt.executeUpdate(CommandoSQL);
			if (rus > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (java.sql.SQLException e) {
			return false;
		}
	}

}
