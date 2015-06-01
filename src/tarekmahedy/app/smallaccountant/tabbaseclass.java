package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;

import com.agimind.widget.SlideHolder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;

public class tabbaseclass extends Activity {


	SmallAccountActivity parentactivity;
	int lang=1;
	int optionviewed=1;
	LinearLayout rootoptionslist;
	LinearLayout mainscreen;
	ListView optionslist;
	LayoutInflater inflater;
	public  Headerbar headerbar;
	realswitcher mainswitcher;
	View preoption;
	SlideHolder mSlideHolder;
	int sreenwidth;
	ArrayList<View> _viewslist;
	ArrayList<String>_msgs;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablayout);
		
		sreenwidth=SmallAccountActivity.display.getWidth();
	}


	public void setparent(){

    
		parentactivity=(SmallAccountActivity)getParent();
		lang=parentactivity.lang;


		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);
		if(lang==2)mSlideHolder.setDirection(SlideHolder.DIRECTION_LEFT);
		else 
			mSlideHolder.setDirection(SlideHolder.DIRECTION_RIGHT);
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		optionslist=(ListView)findViewById(R.id.optionlist1);
		mainscreen=(LinearLayout)findViewById(R.id.mainscreen);
		//optionslist.setSelector(R.drawable.listselector);
		optionslist.setCacheColorHint(Color.TRANSPARENT);
		mainswitcher=new realswitcher(getBaseContext());
		mainswitcher.setBackgroundColor(Color.BLACK);
		if(lang==1)
			optionviewed=1;
		else optionviewed=0;

		headerbar=new Headerbar(getBaseContext(),parentactivity);
		headerbar.optionicon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mSlideHolder.toggle();


			}
		});

		headerbar.helpicon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(_viewslist!=null && _msgs!=null){
					if(addoptionhelp==0){
						 addoptionhelp=1;
						_viewslist.add(0,headerbar.optionicon);
						_msgs.add(0,getString(R.string.optionbtnhelp));
					}
					parentactivity.viewhelp(_viewslist, _msgs);
				}
				else {
					_viewslist=new ArrayList<View>();
					_msgs=new ArrayList<String>();
					_viewslist.add(0,headerbar.optionicon);
					_msgs.add(0,getString(R.string.optionbtnhelp));
					parentactivity.viewhelp(_viewslist, _msgs);

				}
			}
		});
		mainscreen.addView(headerbar,0);

     

	}
	int addoptionhelp=0;


	public void setcontentslider(ArrayList<LinearLayout> childscreen){

		mainswitcher.slidepart=0;
		mainswitcher.pagewidth=sreenwidth;
		int chilcount=childscreen.size();
		mainswitcher.childnumber=chilcount;
		int looper=0;
		int index=0;
		while(looper<chilcount){

			index=looper;

			mainswitcher.addView(childscreen.get(index),looper);

			looper++;
		}

		mainscreen.addView(mainswitcher,new LayoutParams(sreenwidth,LayoutParams.FILL_PARENT));
		mainswitcher.snapToScreen(0);

	}



	public void setcolorbg(View arg1){

		arg1.setBackgroundColor(Color.GRAY);
		if(preoption!=arg1){
			if(preoption!=null)

				preoption.setBackgroundColor(Color.TRANSPARENT);
			preoption=arg1;
		}
	}

	public void intoptionlist(){


		rootoptionslist= (LinearLayout)inflater.inflate(R.layout.mainoptionmenu, null);
		optionslist=(ListView)rootoptionslist.findViewById(R.id.optionlist1);




	}

	@Override
	public void onBackPressed() {

		//message confirm exit
		super.onBackPressed();
	}




}
