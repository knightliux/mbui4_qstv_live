package com.moon.android.live.custom007.OSD;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moon.android.live.custom007.R;

public class ProgramClassifyAdapter extends BaseAdapter{
	
	private Context mContext;
	private LayoutInflater mInflater;
	private List<String> mListClassifyName;
	private ImageView mFirstImage;
	public ProgramClassifyAdapter(Context context, List<String> listClassifyName){
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mListClassifyName = listClassifyName;
	}
	
	@Override
	public int getCount() {
		return mListClassifyName.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mListClassifyName.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Holder holder = null;
		if(null == convertView){
			holder = new Holder();
			convertView = mInflater.inflate(R.layout.program_classify_item, null);
			holder.vodName = (TextView) convertView.findViewById(R.id.program_item_name);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		String classifyName = mListClassifyName.get(position);
		if(classifyName.contains("|")){
			String[] arr = classifyName.split("\\|");
			if(null != arr && arr.length > 1)
				classifyName = arr[1];
		}
		String cname = null != classifyName ? classifyName : "null";
		holder.vodName.setText(cname);
		return convertView;
	}
	
	class Holder{
		TextView vodName;
	}

	public void setSelectPos(int selectPos){
		notifyDataSetChanged();
	}
	
	public void hiddenFirstShowImage(){
		if(null != mFirstImage)
			mFirstImage.setVisibility(View.INVISIBLE);
	}
}
