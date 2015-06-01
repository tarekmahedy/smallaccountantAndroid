package tarekmahedy.app.smallaccountant;


public class Recordecolums {
	
	public long id;
	public String txtfromname;
	public String txttoname;
	public String txtcomment;
	public String txtoperationdate;
	public	Double value;
	public int debttype=0;
	
	Recordecolums(long _id,String _txtfromname,String _txttoname,Double _value,String _txtoperationdate,String _comment){
		
		id=_id;
		txtfromname=_txtfromname;
		txttoname=_txttoname;
		txtcomment=_comment;
		txtoperationdate=_txtoperationdate;
		value=_value;
		if(id==-1)debttype=2;
		if(id==-2)debttype=3;

	}

	
	
}
