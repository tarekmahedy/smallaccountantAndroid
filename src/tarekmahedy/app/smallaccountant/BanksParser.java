package tarekmahedy.app.smallaccountant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;



public class BanksParser extends BroadcastReceiver
{
   
	Context _context;
	
	@Override
    public void onReceive(Context context, Intent intent) 
    {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras(); 
        _context=context;
        SmsMessage[] msgs = null;
        String str = "";            
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                str = msgs[i].getMessageBody().toString();
                Parser(str);
                      
            }
            
        }                         
    }
	
	
	
	int _fromtype=2;
	int _totype=1;
	int _dbtype=0;
	int _type=0;
	Double amount;
	String toAccountTitle,fromAccountTitle="";
	String datetext="";
	long toid,fromid=-1;
	
	String startingstr="Êã";
	String addstarttext="ÇÖÇÝÉ";
	String minstarttext="ÎÕã";
	String tostarttext="ãä";
	String substarttext="Çáì";
	dbhelper helper;
	int dbttype=0;
	
	
	public Boolean Parser(String _smstext){
  
		try{
			
	    helper = new dbhelper(_context);
		String []items=_smstext.split(" ");
		int len=items.length;
		String comment="";
		if(len<11||len>15)return false;
		
		int looper=0;
		while(len>looper){
			
		switch (looper) {
		case 0:
			if(!items[looper].trim().equals(startingstr))return false;
			break;

		case 1:
			if(items[looper].trim().equals(addstarttext))_dbtype=0;
			else if(items[looper].trim().equals(minstarttext))_dbtype=1;
			else return false;
			break;
		case 2:
        	amount=Double.valueOf(items[looper]);
             break;
		case 6:
			toAccountTitle=items[looper];
			break;
		case 8:
			datetext=items[looper];
			break;
		case 12:
			fromAccountTitle=items[12];
			looper++;
			while(len>looper){
				fromAccountTitle+=" "+items[looper];
			    looper++;
			    
				}
			
			break;
		
		
		}	
			
			looper++;
		
		}
		
			
		toid=helper.getaccountsid(toAccountTitle);
		
		if(toid<=0)helper.autoaccount(1,toAccountTitle);
		fromid=helper.getaccountsid(fromAccountTitle);
		if(fromid<=0)helper.autoaccount(2,fromAccountTitle);
		
		helper.addinputoperation(toid, fromid, amount,_dbtype, datetext,comment);
		
		return true;
		
	   }catch (Exception e) {
		return false;
	  }
		
	}
	
	
	
	
}
