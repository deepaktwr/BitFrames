package proj.me.bitframedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import proj.me.bitframe.helper.Utils;


public class CustomAdapter_Dialog extends BaseAdapter{

	private ArrayList<String> data;
	private Context context;
	private LayoutInflater inflater;
	private ViewHolder_Dialog holder;
    private int colorPosition;
	public CustomAdapter_Dialog(Context context,ArrayList<String> data)
	{
		this.context=context;
		this.data=data;
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
    public void setColorForPosition(int position){
        colorPosition=position;
        notifyDataSetChanged();;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null)
		{
			holder=new ViewHolder_Dialog();
			convertView=inflater.inflate(R.layout.custom_text, null);
			holder.text=(TextView) convertView.findViewById(R.id.dynamic_view_text);

			convertView.setTag(holder);
			
		}
		else
			holder=(ViewHolder_Dialog)convertView.getTag();

        if( position == colorPosition ){
            convertView.findViewById(R.id.ll_top_custom).setBackgroundColor(Utils.getVersionColor(context,
					R.color.title_of_selected_forms));
        }else{
            convertView.findViewById(R.id.ll_top_custom).setBackgroundColor(Utils.getVersionColor(context,
					R.color.white));
        }
		holder.text.setText(data.get(position));
		return convertView;
	}

}
