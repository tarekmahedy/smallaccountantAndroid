package tarekmahedy.app.smallaccountant;
import tarekmahedy.app.smallaccountant.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Notifimsg extends LinearLayout {

	private String msg;
	private int errortype;
	private Boolean vibr;
	private Boolean talk;
	int height=60;
	int width=340;
	TextToSpeech tts;
	TextView msgtext;
	protected boolean _active = true;
	protected int _splashTime = 500;

	
	public Notifimsg(Context context,int _height,int _width,ViewGroup _view) {
		super(context);
		View.inflate(context, R.layout.notifcatiobanner,_view);
		
		
		this.height=_height;
		this.width=_width;
		init();
	}


	public void show() {

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
		boolean vibre = prefs.getBoolean("vibr",false);
		boolean voice = prefs.getBoolean("voice",false);
		setVibr(vibre);
		setTalk(voice);
		LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_bottom_to_top_slide);
		setLayoutAnimation(controller);
		msgtext.setText(getMsg());
		setVisibility(View.VISIBLE);
		if(getVibr())vibrdevice();
		if(getTalk())readmsg(this.msg);


	}



	public void hide(){

		setVisibility(View.GONE);	

	}


	void init(){

		setVisibility(View.GONE);
		setMsg("yt");
		setErrortype(1);
		
		setBackgroundColor(Color.parseColor("#48A4FF"));
		msgtext=new TextView(getContext());
		LinearLayout.LayoutParams laypar=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,height);
		setLayoutParams(laypar);
		msgtext.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,height));
		msgtext.setTextColor(Color.WHITE);
		msgtext.setTextSize(24);
		msgtext.setGravity(Gravity.CENTER);
		addView(msgtext,0);
		tts=new TextToSpeech(getContext(),null);
		setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hide();

			}
		});
		
	}



	void vibrdevice(){

		Vibrator vib = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
		vib.vibrate(500);

	}

	void readmsg(String _msg){

	//	tts.speak(_msg,TextToSpeech.QUEUE_FLUSH,null);
		Uri notif_sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone notif_ring = RingtoneManager.getRingtone(getContext(), notif_sound);
		
		notif_ring.play();

	}
	void setMsg(String msg) {
		this.msg = msg;
	}


	String getMsg() {
		return msg;
	}


	void setErrortype(int errortype) {
		this.errortype = errortype;
	}


	int getErrortype() {
		return errortype;
	}


	void setVibr(Boolean vibr) {
		this.vibr = vibr;
	}


	Boolean getVibr() {
		return vibr;
	}


	private void setTalk(Boolean talk) {
		this.talk = talk;
	}


	private Boolean getTalk() {
		return talk;
	}




}
