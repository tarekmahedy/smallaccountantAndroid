package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.haarman.supertooltips.ToolTip;
import com.haarman.supertooltips.ToolTipRelativeLayout;
import com.haarman.supertooltips.ToolTipView;

import android.app.TabActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ext.SatelliteMenu;
import android.view.ext.SatelliteMenuItem;
import android.view.ext.SatelliteMenu.SateliteClickedListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

public class SmallAccountActivity extends TabActivity implements ToolTipView.OnToolTipViewClickedListener {
	/** Called when the activity is first created. */

	Resources res ;
	TabHost tabHost;
	Intent intent; 
	TabHost.TabSpec tab1; 
	TabHost.TabSpec tab2; 
	TabHost.TabSpec tab3; 
	TabHost.TabSpec tab4; 
	ImageView closicon;
	ImageView helpicon;
	public  LinearLayout headerbar;
	Boolean notificationruning =false;
	int lang=2;
	Bundle savedInstanceState;
	boolean hor;
	Aboutdialog aboudial;
	RelativeLayout mainlayout;
	TextView msgtext;
	int msgactive=0;
	public static Display display;
	static Context _context;
    public UserData user;
    SatelliteMenu smenu;
    private ToolTipView myToolTipView;
    private ToolTipRelativeLayout toolTipRelativeLayout;
    public static String appversion="29";
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.optionmenu, menu);

		return true;
	}


	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

			 case R.id.settingoption:
				Intent settingintent = new Intent(getApplicationContext(), settingactivity.class);
				startActivityForResult(settingintent, 0);
               break;
			case R.id.helpoption:
				
				Intent helpintent = new Intent(getApplicationContext(), Helpactivity.class);
				startActivityForResult(helpintent, 0);
				break;
				
		case R.id.iconlogout: 

			SmallAccountActivity.this.finish();   
			break;
			
		case R.id.aboutoption: 
			if(aboudial!=null){
				aboudial.show();
				aboudial.setContenttext(getString(R.string.aboutcontent));

			}
			break;

		case R.id.iconfullscreen: 
			setfullscreen();
			break;

		case R.id.toolsoption: 
			
			ToolsGrid dialacc=new ToolsGrid(this);
			dialacc.show();
	
			break;


		}
		return true;
	}


	@Override
	public void onBackPressed() {

		super.onBackPressed();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		//init user data
	//	user=new UserData();
		//user.intuser(getBaseContext());
		
		String langcode=getString(R.string.langcode);
     //   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean usingArabic = prefs.getBoolean("usingArabic",false);
	    if(usingArabic) langcode  = "ar";
	//	else langcode=langcode;

		Locale locale = new Locale(langcode);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config, null);



		String langu=getString(R.string.langdir);
		lang=Integer.valueOf(langu);
		display = getWindowManager().getDefaultDisplay();
		startui();
		_context=getBaseContext();

	//	boolean smsparsed = prefs.getBoolean("readbanksms",false);
//		if(!smsparsed){
//			parseallsms();
//			SharedPreferences.Editor editor = prefs.edit();
//			editor.putBoolean("readbanksms", true);
//			editor.commit();
//
//		}
		
		
	   smenu= (SatelliteMenu) findViewById(R.id.menu);
	   List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
       items.add(new SatelliteMenuItem(0, R.drawable.homeicon));
       items.add(new SatelliteMenuItem(1, R.drawable.services_icon));
       items.add(new SatelliteMenuItem(2, R.drawable.put_out_icon));
       items.add(new SatelliteMenuItem(3, R.drawable.clock));
       smenu.addItems(items);        
       
       smenu.setOnItemClickedListener(new SateliteClickedListener() {
			
			public void eventOccured(int id) {
				
				if(lang==1)id=3-id;
				tabHost.setCurrentTab(id);
			}
		});

       
       
	}
	
	int currenttooltipindex=0;
	ArrayList<View> _viewslist;
	ArrayList<String>_msgs;
	ToolTipRelativeLayout toolTipRelativeLayouttemp;
	ArrayList<ToolTipView> showntips=new ArrayList<ToolTipView>();
	int forcestop=0;
	public void viewhelp(ArrayList<View> _viewslist,ArrayList<String>_msgs){
		
       int shlen=showntips.size();
		
		if(shlen==0){
			forcestop=0;
		this._viewslist=_viewslist;
		this._msgs=_msgs;
		this.currenttooltipindex=0;
	    toolTipRelativeLayouttemp = (ToolTipRelativeLayout) findViewById(R.id.main_tooltipRelativeLayout);
	    viewnexttip();
	    
	}
	else {
		forcestop=1;
	    int loop=0;
		while(loop<shlen){
			ToolTipView  myToolTipViewtemp1=showntips.get(loop);
			 myToolTipViewtemp1.performClick();
			loop++;
		}
		
		showntips=new ArrayList<ToolTipView>();
		currenttooltipindex=0;
		_viewslist=null;
	    _msgs=null;
	     
	}
		
	}
	
	
	public void viewnexttip(){
		
		if(forcestop==0){
		int a=_viewslist.size();
		
		if(currenttooltipindex<a){
		helphandeler react=new helphandeler();
		react._view=_viewslist.get(currenttooltipindex);
		react.msg=_msgs.get(currenttooltipindex);
		react.toolTipRelativeLayouttemp=toolTipRelativeLayouttemp;
		new Handler().postDelayed(react,800);
		currenttooltipindex++;
		
		}
		}
		
	}
	
	
	
	
	public class helphandeler implements Runnable {
		
		
		View _view;
        String msg;
        ToolTipRelativeLayout toolTipRelativeLayouttemp ;
        int type=0;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(type==0){
			  ToolTipView  myToolTipViewtemp = toolTipRelativeLayouttemp.showToolTipForView(
		                new ToolTip()
		                        .withText(msg)
		                        .withColor(Color.DKGRAY)
		                        .withShadow(false)
		                        .withAnimationType(ToolTip.ANIMATIONTYPE_FROMTOP),_view);
		        
			     TextView texttip=(TextView)myToolTipViewtemp.findViewById(R.id.tooltip_contenttv);
		         texttip= setcustomfont(texttip, getBaseContext());
	             texttip.setTextSize(12);
	             texttip.setTextColor(Color.WHITE);
			  
		    myToolTipViewtemp.setOnToolTipViewClickedListener(SmallAccountActivity.this);
		   
		    showntips.add(myToolTipViewtemp);
		    
		    viewnexttip(); 
			}
		    
		}
		
	
		
	}
	
	
    public void addRedToolTipView(View _view,String msg) {
    	
    	 toolTipRelativeLayout = (ToolTipRelativeLayout) findViewById(R.id.main_tooltipRelativeLayout);
         
       myToolTipView = toolTipRelativeLayout.showToolTipForView(
                new ToolTip()
                        .withText(msg)
                        .withColor(Color.DKGRAY)
                        .withShadow(false),_view);
          TextView texttip=(TextView)myToolTipView.findViewById(R.id.tooltip_contenttv);
       
             texttip= setcustomfont(texttip, this);
             texttip.setTextSize(12);
             texttip.setTextColor(Color.WHITE);
            myToolTipView.setOnToolTipViewClickedListener(SmallAccountActivity.this);
        
    }

    
	public void startui(){

		setContentView(R.layout.main);
        msgtext=(TextView)findViewById(R.id.notemsg);
        msgtext=SmallAccountActivity.setcustomfont(msgtext,getBaseContext());
        msgtext.setTextSize(14);
        msgtext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				shownotify("",false);

			}
		});
        
		mainlayout=(RelativeLayout)findViewById(R.id.mainlayout);
		aboudial=new Aboutdialog(SmallAccountActivity.this);

		res = getResources();
		tabHost = getTabHost();
		tabHost.setBackgroundResource(R.drawable.back_bround);

		tabHost.setup();

		initabs(lang);
		if(lang==1)tabHost.setCurrentTab(3);



	}


	Timer timer;
	
	public  void shownotify(String _msg,Boolean _view){
  
		
		if(msgactive==0&&_view){
			
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			boolean vibre = prefs.getBoolean("vibr",false);
			if(vibre)vibrdevice();
			this.msgtext.setText(_msg);
			
			ViewGroup.LayoutParams params = this.tabHost.getLayoutParams();
				((MarginLayoutParams) params).setMargins(0, 40, 0, 0);
			this.tabHost.setLayoutParams(params);
			if(fullscreen==0){
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			}
			msgactive=1; 
			timer = new Timer();
	        TimerTask updateProfile = new CustomTimerTask(this);
	        timer.scheduleAtFixedRate(updateProfile,5000,5000);
	        
		        
         }else if(msgactive==1&&!_view)hide();
	   
         else if(msgactive==1&&_view)this.msgtext.setText(_msg);
        
	}

	
	void hide(){
		 this.timer.cancel();
		if(fullscreen==0){
	        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);    
        	 }
			ViewGroup.LayoutParams params = this.tabHost.getLayoutParams();
			((MarginLayoutParams) params).setMargins(0,0, 0, 0);
		    this.tabHost.setLayoutParams(params);
	        msgactive=0;   
	       
		
	}
	
	
	void vibrdevice(){

		Vibrator vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		vib.vibrate(500);

	}

	
	
	static int fullscreen=0;

	public void setfullscreen(){


		if(fullscreen==0){
			tabHost.getTabWidget().setVisibility(View.GONE);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			fullscreen=1;
			if(msgactive==1)
			shownotify("",false);
			smenu.setVisibility(View.VISIBLE);
			
		}else {
			tabHost.getTabWidget().setVisibility(View.VISIBLE);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);  
			fullscreen=0;
			smenu.setVisibility(View.GONE);
		}


	}



	int runing=0;
	OnTabChangeListener tabchangedlisten=new OnTabChangeListener() {

		@Override
		public void onTabChanged(String tabId) {

			//	LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getBaseContext(), R.anim.layout_bottom_to_top_slide);
			//	tabHost.getTabContentView().setLayoutAnimation(controller);


		}
	};


	private View createIndicatorView(TabHost tabHost, CharSequence label, Drawable icon) {

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View tabIndicator = inflater.inflate(R.layout.tab_indecator,
				tabHost.getTabWidget(), // tab widget is the parent
				false); // no inflate params

		tabIndicator.setBackgroundResource(R.drawable.tab_indicator);
		 TextView tv = (TextView) tabIndicator.findViewById(R.id.title);
		tv.setTextColor(Color.BLACK);
		tv.setText(label);
		tv=SmallAccountActivity.setcustomfont(tv,getBaseContext());
		tv.setTextSize(14);
		final ImageView iconView = (ImageView) tabIndicator.findViewById(R.id.icon);
		iconView.setImageDrawable(icon);

		return tabIndicator;
	}


	public void initabs(int _lang){

		String alarmtab="";
		String hometab="";
		String tabstr2="";
		String tabstr3="";

		hometab=getString(R.string.apptile);
		alarmtab=getString(R.string.alarmtab);
		tabstr2=getString(R.string.tab2);
		tabstr3=getString(R.string.tab3);


		intent = new Intent().setClass(this, OperationsTab.class);
		
		
		tab4 = tabHost.newTabSpec(hometab).setIndicator(createIndicatorView(tabHost,hometab,
				res.getDrawable(R.drawable.homeicon)))
				.setContent(intent);


		/////////////////////////////////////////////////////////////////

		intent = new Intent().setClass(this, AccountsTab.class);
		tab2 = tabHost.newTabSpec(tabstr2).setIndicator(createIndicatorView(tabHost,tabstr2,
				res.getDrawable(R.drawable.services_icon)))
				.setContent(intent);


		////////////////////////////////////////////////////////////////

		intent = new Intent().setClass(this, ReportsTab.class);
		tab3 = tabHost.newTabSpec(tabstr3).setIndicator(createIndicatorView(tabHost,tabstr3,
				res.getDrawable(R.drawable.put_out_icon)))
				.setContent(intent);


		///////////////////////////////////////////////////////////////

		intent = new Intent().setClass(this, NotificationTab.class);
		tab1 = tabHost.newTabSpec(alarmtab).setIndicator(createIndicatorView(tabHost,alarmtab,
				res.getDrawable(R.drawable.clock)))
				.setContent(intent);

		if(lang==2){
			tabHost.addTab(tab4);
			tabHost.addTab(tab2);
			tabHost.addTab(tab3);
			tabHost.addTab(tab1);

		}
		else {
			tabHost.addTab(tab1);
			tabHost.addTab(tab3);
			tabHost.addTab(tab2);
			tabHost.addTab(tab4);
		}


		tabHost.setOnTabChangedListener(tabchangedlisten);
		TabWidget tw = (TabWidget)tabHost.findViewById(android.R.id.tabs);
		tw.setBackgroundResource(R.drawable.bottom_bar_bg_on);


	}


	public static TextView setcustomfont(TextView _text,Context _context){


		Typeface font = Typeface.createFromAsset(_context.getAssets(), "DroidKufi-Regular.ttf");  
		_text.setTypeface(font); 
		_text.setTextSize(15);

		return _text;

	}
	
	
	public void parseallsms(){

		Thread backupthread=new Thread(initmsgner);
		backupthread.start();

	}


	
	
	Runnable initmsgner=new Runnable() {

		public void run() {  
			 
			Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
			cursor.moveToFirst();

			do{
				String msgData = "";
				BanksParser bparser=new BanksParser();
				bparser._context=getBaseContext();
				msgData =cursor.getString(11);
				bparser.Parser(msgData);

			}while(cursor.moveToNext());



		}
	};
	
	
	public class CustomTimerTask extends TimerTask {

	    public CustomTimerTask(Context con) {
	      
	    }

	    @Override
	    public void run() {
	    	
	    	runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					 hide();
					
				}
			});
	    }

	}


	@Override
	public void onToolTipViewClicked(ToolTipView toolTipView) {
		// TODO Auto-generated method stub
		toolTipView=null;
		
		
		
	}
	
	
	
	


}