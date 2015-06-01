package tarekmahedy.app.smallaccountant;
import tarekmahedy.app.smallaccountant.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;


public class Helpactivity extends Activity {

	ExpandableListView mainhelplist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.helplayout);
		
		mainhelplist=(ExpandableListView)findViewById(R.id.expandableListView1);
		LinearLayout lay=(LinearLayout)findViewById(R.id.mainview);
		lay.setBackgroundColor(Color.LTGRAY);
		mainhelplist.setCacheColorHint(Color.TRANSPARENT);
		Helpadptor adpt=new Helpadptor(getBaseContext());
    	mainhelplist.setAdapter(adpt);
    	
    	
	}
	
	
	
}