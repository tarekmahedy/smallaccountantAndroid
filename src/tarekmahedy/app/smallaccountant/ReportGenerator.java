package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;

import org.apache.http.util.LangUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;




public class ReportGenerator {



	public ReportGenerator(Context _context,int _reporttype) {
		// TODO Auto-generated constructor stub
		this._context=_context;
		setlabelsstring();
		screenwidth=SmallAccountActivity.display.getWidth();
		headerrowheight=50;
		rowheight=45;
		type=_reporttype;
		headerbackgroundcolor=Color.RED;
		cellbackgroundcolorrow1=Color.YELLOW;
		cellbackgroundcolorrow2=Color.BLUE;
		cellfontcolor=Color.BLACK;
		headerfontcolor=Color.WHITE;
	}

	int type;
	//for rotating the app
	int forcerotate;
	int orientation;
	int screenwidth;
	int headerrowheight;
	int landir;
	int headerbackgroundcolor;
	int cellbackgroundcolorrow1;
	int cellbackgroundcolorrow2;
	int cellfontcolor;
	int headerfontcolor;
	Context _context;
	int rowheight;



	//   header labels:
	//	[value,quantity,price,credite,debite,accountname,productname,date];
	//	footer lables:
	//	[value,total,totalquantity,total price];

	ArrayList<String[]> headerlabels;
	String []footerlabels;
	ArrayList<int[]> headercolspercent;

	private void setlabelsstring(){

		String datela=_context.getString(R.string.operationdate);
		String accountnamlabel=_context.getString(R.string.accountanamelabel);
		String vallabel=_context.getString(R.string.reportvalue);
		String headerlabelcredite=_context.getString(R.string.creditlabel);
		String headerlabeldebite=_context.getString(R.string.debitlabel);
		String headerlabelproductname=_context.getString(R.string.productname);
		String headerlabelprice=_context.getString(R.string.valuelable);
		String headerlabelquantity=_context.getString(R.string.quantitylabel);

		headerlabels=new ArrayList<String[]>();
		headerlabels.add(new String[]{vallabel,accountnamlabel,datela});
		headerlabels.add(new String[]{headerlabelcredite,headerlabeldebite,accountnamlabel});
		headerlabels.add(new String[]{headerlabelcredite,headerlabelproductname,datela});//need to update
		headerlabels.add(new String[]{vallabel,headerlabelproductname,datela});
		headerlabels.add(new String[]{headerlabelquantity,headerlabelproductname,datela});
		headerlabels.add(new String[]{vallabel,headerlabelproductname,datela});


		footerlabels=new String[1];
		footerlabels[0]=_context.getString(R.string.totallabel);

		headercolspercent=new ArrayList<int[]>();
		headercolspercent.add(new int[]{15,60,25,2});	
		headercolspercent.add(new int[]{15,15,70,1});	
		headercolspercent.add(new int[]{15,60,25,1});	//need to update
		headercolspercent.add(new int[]{15,60,25,2});	
		headercolspercent.add(new int[]{15,60,25,2});	
		headercolspercent.add(new int[]{15,60,25,2});	

	}

	//

	//reprtrow
	//names[],price,quantity,value,date
	//with custome style

	//	1- Value/accountname(s)/date[20,50,30]
	//	2- Credit/debit/accountname[20,20,60]
	//	3- Credit/debit/accountname/date
	//	4- Value/productname(s)/date[20,50,30]
	//	4- quantity/productname(s)/date[20,50,30]
	//	5- value/[productname,price,quantity]/date[20,50,30]



	public LinearLayout generatecell(Recordecolums col){

		String []headerlabel=headerlabels.get(type);
		int []headerwidth=headercolspercent.get(type);
		rowheight=45*headerwidth[3];

		LinearLayout cellrow=new LinearLayout(_context);
		cellrow.setLayoutParams(new ListView.LayoutParams(screenwidth,rowheight));
		cellrow.setBackgroundColor(cellbackgroundcolorrow1);


		int looper=0;
		int len=headerlabel.length;
		int basev=0;

		TextView label=generatelabel(col.txtoperationdate.toString(),headerwidth[len-(looper+1)],rowheight);	
		cellrow.addView(label);
		looper++;	
		// TextView label2=generatelabel(col.txtfromname,headerwidth[looper],rowheight);	
		cellrow.addView(generatecellmaincol(new String[]{col.txtfromname},headerwidth[len-(looper+1)]));
		looper++;

		TextView label3=generatelabel(col.value.toString(),headerwidth[len-(looper+1)],rowheight);	
		cellrow.addView(label3);
		looper++;	


		return cellrow;

	}


	public RelativeLayout generatecellmaincol(String [] _values,int width){

		int widthv=(screenwidth/100)*width;
		RelativeLayout cellrow=new RelativeLayout(_context);
		cellrow.setLayoutParams(new LinearLayout.LayoutParams(widthv,LinearLayout.LayoutParams.FILL_PARENT));
		cellrow.setBackgroundColor(Color.TRANSPARENT);

		int cellheight=45;   
		int looper=0;
		int len=_values.length;

		while(len>looper){

			TextView label=null;

			if(looper==0){
				label=generatelabel(_values[looper],widthv,cellheight);	
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthv,cellheight);
				if((looper+1)>=len) params.addRule(RelativeLayout.ALIGN_BASELINE, RelativeLayout.TRUE);
				else {
					params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
				}


				label.setLayoutParams(params);
			}
			else if(looper==1){
				label=generatelabel(_values[looper],(widthv/2),cellheight);	
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((widthv/2),cellheight);
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
				params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
				label.setLayoutParams(params);
			}
			else if(looper==2){
				label=generatelabel(_values[looper],(widthv/2),cellheight);	
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((widthv/2),cellheight);
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
				params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
				label.setLayoutParams(params);
			}

			cellrow.addView(label);
			looper++;	

		}

		return cellrow;

	}


	public LinearLayout generateheader(){

		LinearLayout headerlayout=new LinearLayout(_context);
		headerlayout.setLayoutParams(new ListView.LayoutParams(screenwidth,headerrowheight));
		headerlayout.setBackgroundColor(headerbackgroundcolor);
		String []headerlabel=headerlabels.get(type);
		int []headerwidth=headercolspercent.get(type);
		int looper=0;
		int len=headerlabel.length;
		int basev=0;
		while(len>looper){

			if(landir==1)basev=len-(looper+1);
			else basev=looper;

			TextView label=generatemainlabel(headerlabel[basev],headerwidth[basev]);	
			headerlayout.addView(label);

			looper++;	
		}


		return headerlayout;

	}


	public LinearLayout generatefooter(String []_values){

		LinearLayout footerlayout=new LinearLayout(_context);
		footerlayout.setLayoutParams(new ListView.LayoutParams(screenwidth,headerrowheight));
		footerlayout.setBackgroundColor(headerbackgroundcolor);

		int []headerwidth=headercolspercent.get(type);
		int looper=0;
		int len=_values.length;
		int basev=0;
		TextView labelv=generatemainlabel(footerlabels[0],80);	
		if(landir==1)footerlayout.addView(labelv);
		while(len>looper){
			basev=looper;
			TextView label=generatemainlabel(_values[basev],headerwidth[basev]);	
			footerlayout.addView(label);

			looper++;	
		}


		if(landir==2)footerlayout.addView(labelv);




		return footerlayout;



	}



	public TextView generatelabel(String _text,int _labelwidth,int _cellheight){

		TextView label=new TextView(_context);
		int widthv=(screenwidth/100)*_labelwidth;
		label.setLayoutParams(new LinearLayout.LayoutParams(widthv,_cellheight));
		label.setGravity(Gravity.CENTER);
		label.setTextColor(cellfontcolor);
		label.setText(_text);
		label.setPadding(3,3,3,3); 
		label=SmallAccountActivity.setcustomfont(label, _context);
		return label;
	}




	public TextView generatemainlabel(String _text,int _labelwidth){

		TextView label=new TextView(_context);
		int widthv=(screenwidth/100)*_labelwidth;
		label.setLayoutParams(new LinearLayout.LayoutParams(widthv,headerrowheight));
		label.setGravity(Gravity.CENTER);
		label.setText(_text);
		label=SmallAccountActivity.setcustomfont(label, _context);
		label.setTextColor(headerfontcolor);
		label.setTextSize(16);

		return label;

	}






}
