package tarekmahedy.app.smallaccountant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import tarekmahedy.app.smallaccountant.R;
import android.content.Context;
import android.graphics.Color;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class Helpadptor extends BaseExpandableListAdapter {


	Context context;
	int lang=1;
	String xmlfile="helpen.xml";
	ArrayList<String[]> lists=null;
	
	Helpadptor(Context _context){
		context=_context;
		lang=Integer.valueOf(context.getString(R.string.langdir));
		xmlfile=context.getString(R.string.xmlfile);

		loadxmlfile();
	}

	
	void loadxmlfile(){

    	XmlPullParser parser = Xml.newPullParser();
		try {
			InputStreamReader isr = new InputStreamReader(context.getAssets().open(xmlfile));
			parser.setInput(isr);
			int eventType = parser.getEventType();
			String[] currCategory = null;
			boolean done = false;
			while (eventType != XmlPullParser.END_DOCUMENT && !done){
				String name = null;
				switch (eventType){
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();
					if (name.equalsIgnoreCase("items")){
						lists= new ArrayList<String[]>();
					} else if (lists != null){
						if (name.equalsIgnoreCase("item")){
							currCategory = new String[2];
							currCategory[0]=parser.getAttributeValue(0);
							currCategory[1]=parser.nextText();
						}

					}
					break;
				case XmlPullParser.END_TAG:
					name = parser.getName();
					if (name.equalsIgnoreCase("item") && currCategory != null){
						lists.add(currCategory);
					} else if (name.equalsIgnoreCase("items")){
						done = true;
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (FileNotFoundException e) {
			// TODO
		} catch (IOException e) {
			// TODO
		} catch (Exception e){
			// TODO
		}

	}


	public Object getChild(int groupPosition, int childPosition) {
		return lists.get(groupPosition)[1];
	}
	

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {

		TextView textView = new TextView(context);

		if(lang==1){
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		}
		else {
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		}

		textView.setPadding(2, 0, 2, 0);
		textView.setTextColor(Color.DKGRAY);
		textView.setTextSize(20);
		String conten=lists.get(groupPosition)[1];
		textView.setText(conten);
		return textView;

	}//getChildView

	public Object getGroup(int groupPosition) {
		return lists.get(groupPosition);
	}

	public int getGroupCount() {
		return lists.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
			ViewGroup parent) {
		TextView textView = new TextView(context);
		textView.setBackgroundColor(Color.WHITE);

		textView.setPadding(50, 10, 5,10);
		if(lang==1)
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		else{
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		}

		textView.setTextColor(Color.BLUE);
		textView.setTextSize(20);
		String conten=lists.get(groupPosition)[0];
		textView.setText(conten);
	
		return textView;
	}

	
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

}//customListAdapter


