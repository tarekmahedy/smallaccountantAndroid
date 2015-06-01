package tarekmahedy.app.smallaccountant;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Headerbar extends LinearLayout{

	
	public ImageView optionicon;
	public ImageView helpicon;
	public  LinearLayout headerbar;
	int optionviewed=1;
	public  TextView apptitle;
	public  String headertitle="";
	static int fullscreen=0;
	static Notifimsg notify;
	public static Display display;
	static SmallAccountActivity activ;
	
	public Headerbar(Context context,SmallAccountActivity _activ) {
		super(context);
		
		   display=SmallAccountActivity.display;
		
		   LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			int headerlyid=R.layout.headerbar;
			int langid=Integer.valueOf(context.getString(R.string.langdir));
	        if(langid==1)
				headerlyid=R.layout.headerbar1;
	        headerbar=(LinearLayout)inflater.inflate(headerlyid,null);
	        
	        optionicon=(ImageView)headerbar.findViewById(R.id.appicone);
	    	apptitle=(TextView)headerbar.findViewById(R.id.textView1);
	    	apptitle=SmallAccountActivity.setcustomfont(apptitle,context);
	    	apptitle.setTextSize(17);
	    	helpicon=(ImageView)headerbar.findViewById(R.id.helpicon);
	    	
	    	 setBackgroundResource(R.drawable.top_bg);
	    	 
	    	 addView(headerbar,display.getWidth(),LayoutParams.WRAP_CONTENT);
	         activ=_activ;
	    	
	}


	
	

	public  void shownotify(String _msg,Boolean _view){

		activ.shownotify(_msg, _view);


	}

	
	public static void alert(String _msg,Boolean _view){

		activ.shownotify(_msg, _view);


	}
	
	
}
