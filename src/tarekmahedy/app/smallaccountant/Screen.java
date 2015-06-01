package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class Screen extends LinearLayout {


	Context _context;
	dbhelper helper;
	int lang=2;
	LinearLayout liner;
	ArrayList<View> _viewslist;
	ArrayList<String>_msgs;

	public Screen(Context context) {
		super(context);

		_context=context;
		String langu=getContext().getString(R.string.langdir);
		lang=Integer.valueOf(langu);
		helper = new dbhelper(getContext());
		setlayout();
	}

	public void setlayout(){
		int sreenwidth=SmallAccountActivity.display.getWidth();

		setLayoutParams(new LayoutParams(sreenwidth,LayoutParams.FILL_PARENT));


	}

}
