package takepicture;

import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class DataAdapter_NoteTakePhoto_ShowImage extends BaseAdapter {
	
	private Context context;
	private  ArrayList<HashMap<String, String>> ListNoteTakePhotoIMG;
	private HashMap<String, String> res;
	HashMap<String, Drawable> datacolumIMG = new HashMap<String, Drawable>();
	TextView txtDispatchingOrderNumber,txtDispatchingOrderDate,txtCustomerNumber,txtCustomerName,txtSpecialRequirement,txtDispatchingOrderID;
	private TextView txtHead;
	private TextView txtDetail;
	
	Activity _Activity;
	private TextView txtFileName;
	public DataAdapter_NoteTakePhoto_ShowImage(Activity PActivity,Context context,
			ArrayList<HashMap<String, String>> _ListNoteTakePhotoIMG) {
		this.context = context;
		this.ListNoteTakePhotoIMG = _ListNoteTakePhotoIMG;
		_Activity=PActivity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ListNoteTakePhotoIMG.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ListNoteTakePhotoIMG.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	public void UpdateDataAdapter(ArrayList<HashMap<String, String>> _ListNoteTakePhotoIMG) {
		
		this.ListNoteTakePhotoIMG = _ListNoteTakePhotoIMG;
	}
	@SuppressWarnings("static-access")
	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		res = ListNoteTakePhotoIMG.get(position);
		View  view = View.inflate(context, R.layout.items_showimg, null);
/**
 * Get String
 */
//		String SAttachmentID =  res.get("AttachmentID");
//		String SOrderNumber = res.get("OrderNumber");
		String SAttachmentFile = res.get("AttachmentFile");
		String SAttachmentDate = res.get("AttachmentDate");
		String SAttachmentUser = res.get("AttachmentUser");
		String SAttachmentFileSize = res.get("AttachmentFileSize");
//		String SOriginalFileName = res.get("OriginalFileName");
		String SAttachmentDescription = res.get("AttachmentDescription");
		ImageView imgView =(ImageView)view.findViewById(R.id.imgShowTakePicture_Img);
		VideoView videoView =(VideoView)view.findViewById(R.id.VideoShowTakePicture_Video);
		txtHead = (TextView)view.findViewById(R.id.txtShowTakePicture_Head);
		txtDetail =  (TextView)view.findViewById(R.id.txtShowTakePicture_Detail);
		txtFileName =  (TextView)view.findViewById(R.id.txtShowTakePicture_FileName);
		
		txtHead.setText(SAttachmentDescription);
		txtDetail.setText(SAttachmentDate + "   ("+ SAttachmentUser + ")  | "+ SAttachmentFileSize + "kb." );
		txtFileName.setText(SAttachmentFile);
//		String extension = SAttachmentFile.substring((SAttachmentFile.lastIndexOf(".") + 1), SAttachmentFile.length());
		
		 imgView.setVisibility(view.GONE);
         videoView.setVisibility(view.GONE);
		
//		if (extension.equals("mp4"))
//				{
//		    	imgView.setVisibility(view.INVISIBLE);
//		    	videoView.setVisibility(view.VISIBLE);
//				}
//		else
//		{
//			if (datacolumIMG.get(SAttachmentFile) != null)
//			{
//				imgView.setImageDrawable(datacolumIMG.get(SAttachmentFile));
//				 imgView.setVisibility(view.VISIBLE);
//		         videoView.setVisibility(view.GONE);
//			}
//			else
//			{
//
////				 new LoadingIMG(imgView).execute("http://195.184.11.254:804/Photos/" + SAttachmentFile);		
//		         imgView.setVisibility(view.VISIBLE);
//		         videoView.setVisibility(view.GONE);
//			}
           
//		}
		
			
		
		return view;
	}

		
}



