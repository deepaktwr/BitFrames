package proj.me.bitframedemo;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;



@SuppressLint("ValidFragment")
public class CustomDialogFrag extends DialogFragment
{
	private View view = null;
	private ListView list = null;
	private Context context = null;
	private CustomAdapter_Dialog customAdapter = null;
	private ArrayList<String> data = null;
	private ImageIntent imageIntent;

	public CustomDialogFrag(ImageIntent imageIntent)
	{
		this.imageIntent = imageIntent;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.custom_list, container,false);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle arg0) 
	{
		super.onActivityCreated(arg0);
		
		list=(ListView)view.findViewById(R.id.list);
		context=getActivity();
		setDataImage();
		
		customAdapter=new CustomAdapter_Dialog(context, data);
		list.setAdapter(customAdapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				setAction(arg2);
			}
		});
	}
	
	
	private void setAction(int id)
	{
		if(id==0){
			this.dismiss();
			imageIntent.takePhoto();
		}
		else if(id==1){
			this.dismiss();
			imageIntent.fetchFromGallery();
		}
	}

	private void setDataImage()
	{
		data=new ArrayList<String>();
		data.add("TAKE PICTURE");
		data.add("FETCH PHOTO FROM GALLERY");
	}
}
