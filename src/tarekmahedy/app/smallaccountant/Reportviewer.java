package tarekmahedy.app.smallaccountant;


import java.util.ArrayList;

import tarekmahedy.app.smallaccountant.R;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Reportviewer extends LinearLayout {



	private SQLiteDatabase database;
	dbhelper helper;
	int lang=2;
	ListView reportviewerlist;
	public Reportviewer(Context context) {
		super(context);

		lang=Integer.valueOf(getContext().getString(R.string.langdir));
		int resuid=R.layout.reportviewerlayout;

		View.inflate(context,resuid,this);
		reportviewerlist=(ListView)findViewById(R.id.reportviewerlist);
		reportviewerlist.setCacheColorHint(Color.TRANSPARENT);

		//initui();
	}

	public void initui(InputLlistCell _cell){
		try{

			helper = new dbhelper(getContext());
			database = helper.getWritableDatabase();
			long accid=0;
			int type=0;
			int detview=0;
			accid= _cell.accid;
			type=_cell.type;
			detview=_cell.detview;

			type=gettype(type,detview);
			int tembtype=type;
			if(detview==4)tembtype=3;

			Reportadaptor adpt=new Reportadaptor(getContext(),getcolums(accid,tembtype),type,lang);
			reportviewerlist.setAdapter(adpt);

		    }catch (Exception e) {
			   
		    	Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

		}

	}


	int gettype(int _type,int _detview){
		if(_detview==4)return 0;
		switch (_type) {
		case 19: return 0;
		case 2: return 1;
		case 4: return 1;
		default:	return 2;

		}

	}



	public void getreporttype(int _type,long _id){

		Reportadaptor adpt=new Reportadaptor(getContext(),getcolums(_id,_type),_type,lang);
		reportviewerlist.setAdapter(adpt);

	}


	//get report data 

	public ArrayList<Recordecolums> getcolums(long _reportid,int _type){

		//get report data and type switch type 

		int settotal=1;

		ArrayList<Recordecolums> columes=new ArrayList<Recordecolums>();

		String querytxt="select op.id _id,acc.accountname as toname,acct.accountname as fromname,value,op.operationdate,op.comments from operations as op , accounts  as acc , accounts  as acct  where acc.id=op.toid and acct.id=op.fromid order by op.id desc";
		String headertitle=getContext().getString(R.string.accountanamelabel);
		String footertitle=getContext().getString(R.string.totallabel);

		switch (_type) {
		case 3:
			querytxt="select op.id _id,acc.accountname as toname,acct.accountname as fromname,value,op.toid,op.operationdate,op.comments from operations as op , accounts  as acc , accounts  as acct  where acc.id=op.toid and acct.id=op.fromid and (op.toid in (select id from accounts where type="+String.valueOf(_reportid)+") or op.fromid in (select id from accounts where type="+String.valueOf(_reportid)+")) order by op.id desc";
			break;
		case 2:
			querytxt="select op.id _id,acc.accountname as toname,acc.accountname as fromname,value,op.toid,op.operationdate,op.comments from operations as op , accounts  as acc  where (op.toid="+String.valueOf(_reportid)+" and acc.id=op.fromid)or (op.fromid="+String.valueOf(_reportid)+" and acc.id=op.toid) order by op.id desc";
			break;
		case 1:
			querytxt="select op.id _id,acc.accountname as toname,acc.accountname as fromname,value,op.toid,op.operationdate,op.comments from operations as op , accounts  as acc  where (op.toid="+String.valueOf(_reportid)+" and acc.id=op.fromid)or (op.fromid="+String.valueOf(_reportid)+" and acc.id=op.toid) order by op.id desc";
			break;
		case 0:
			querytxt="select op.id _id,acc.accountname as toname,acct.accountname as fromname,value,op.toid,op.operationdate,op.comments from operations as op , accounts  as acc , accounts  as acct  where acc.id=op.toid and acct.id=op.fromid order by op.id desc";
			break;

		}

		Cursor data = database.rawQuery(querytxt,null);
		Double total=Double.valueOf(0);

		columes.add(new Recordecolums(-2,headertitle,"",total,getContext().getString(R.string.operationdate),null));

		while(data.moveToNext()){
         	Recordecolums colum1=new Recordecolums(data.getLong(0),data.getString(1),data.getString(2),data.getDouble(3),data.getString(5),data.getString(6));
			if(_type==2){
				long debt=data.getLong(4);
				if(debt==_reportid)colum1.debttype=1;

			}
			if(colum1.debttype==1)
				total-=data.getDouble(3);
			else total+=data.getDouble(3);

			columes.add(colum1);
		}
		if(settotal==1){

			if(total<0){
				total=total*-1;
				Recordecolums col =new Recordecolums(-1,footertitle,"",total,"",null);
				col.debttype=1;
				columes.add(col);

			}
			else {
				Recordecolums col =new Recordecolums(-1,footertitle,"",total,"",null);
				columes.add(col);
			}

		}

		return columes;


	}





}
