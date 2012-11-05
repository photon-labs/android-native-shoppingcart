package com.photon.phresco.uiconstants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import android.app.Instrumentation;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.photon.phresco.nativeapp.functional.R;

public class Data {
	private ReadXMLFile readXml;

	
	public String CONFIRM_PASSWORD_DIALOG = "text1";
	public String CONFIRM_LOGIN_DIALOG = "text2";
	public String CONFIRM_CATEGORY_DIALOG = "text2";
	InputStream inputStream = null;
    public Data() {
		
	}
    public void parser1(Instrumentation inst)
    {
    	try {
    		
    		/*AssetFileDescriptor descriptor = context.getAssets().openFd("userinfo.xml");
    		FileReader reader = new FileReader(descriptor.getFileDescriptor());
    		AssetManager am = context.getAssets();
    		InputStream fs = am.open("userinfo.xml");*/


    		//InputStream inst=reader;
    	//	InputStream inputStream=context.getResources().openRawResource(R.raw.userinfo);
    		
    		
    		
    		
    		Resources res = inst.getContext().getResources();
    		InputStream instream = res.openRawResource(R.raw.data);
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		AssetManager assetManager = inst.getContext().getAssets();
    		
    		try {
    		    inputStream = assetManager.open("data.xml");
    		}
    		catch (IOException e){
    		   // Log.e("message: ",e.getMessage());
    		}


    		
    		
    		
    	//InputStream inputStream=context.getAssets().open("userinfo.xml");

			readXml = new ReadXMLFile();
			readXml.loadUserInfoConstants(instream);
			Field[] arrayOfField1 = super.getClass().getFields();
			Field[] arrayOfField2 = arrayOfField1;
			int i = arrayOfField2.length;
			for (int j = 0; j < i; ++j) {
				Field localField = arrayOfField2[j];
				Object localObject = localField.get(this);
				if (localObject instanceof String)
					localField
							.set(this, readXml.getValue((String) localObject));

			}
		} catch (Exception localException) {
			throw new RuntimeException("Loading "
					+ super.getClass().getSimpleName() + " failed",
					localException);
		}
    }
}
