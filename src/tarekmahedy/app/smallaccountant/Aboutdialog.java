package tarekmahedy.app.smallaccountant;
import tarekmahedy.app.smallaccountant.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Aboutdialog extends Dialog {

	public Aboutdialog(Context context) {
		super(context);
		contenttext=context.getString(R.string.about);

	}
	
	private String contenttext;
	TextView textcontent;
	LinearLayout aboutlay;
	private int hieght=280;
	private int width=240;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.aboutdialog);
		aboutlay=(LinearLayout)findViewById(R.id.aboutlayout);

		//aboutlay.setLayoutParams(new LayoutParams(getWidth(),getHieght()));
		textcontent=(TextView)findViewById(R.id.abouttext);
		textcontent.setText(getContenttext());
		textcontent=SmallAccountActivity.setcustomfont(textcontent,getContext());

	}

	void setContenttext(String contenttext) {
		this.contenttext = contenttext;
		textcontent.setText(contenttext);
	}

	String getContenttext() {
		return contenttext;
	}

	void setHieght(int hieght) {
		this.hieght = hieght;
	}

	int getHieght() {
		return hieght;
	}

	void setWidth(int width) {
		this.width = width;
	}

	int getWidth() {
		return width;
	}

}
