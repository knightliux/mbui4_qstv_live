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
import com.moon.android.live.custom007.model.LiveData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ProgramAdapter extends BaseAdapter{
	
	private Context mContext;
	private LayoutInflater mInflater;
	private List<LiveData> mListVodVideo;
	private int mSelectPosInAllProgram;
	private ImageView mFirstImage;
	private DisplayImageOptions mOptions;
	private ImageLoadingListener animateFirstListener = new SimpleImageLoadingListener();
	public ProgramAdapter(Context context, List<LiveData> list, DisplayImageOptions options){
		mContext = context;
		mListVodVideo = list;
		mOptions = options;
		mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		return mListVodVideo.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mListVodVideo.get(arg0);
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
			convertView = mInflater.inflate(R.layout.program_list_item, null);
			holder.image = (ImageView) convertView.findViewById(R.id.program_item_play_image);
			holder.icon = (ImageView) convertView.findViewById(R.id.program_icons);
			holder.vodName = (TextView) convertView.findViewById(R.id.program_item_name);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		LiveData liveData = mListVodVideo.get(position);
		
		String programId = String.valueOf(position + 1); 
		if(null != programId)
		if(programId.length() <= 1)
			programId = new StringBuilder("00").append(programId).toString();
		else if(programId.length() <= 2)
			programId = new StringBuilder("0").append(programId).toString();
		else programId = String.valueOf(programId);
		
		String vodName = new StringBuilder(programId).append("  ")
								.append(liveData.getDname()).toString();
		holder.vodName.setText(vodName);
		
		int inAllPos = Integer.valueOf(liveData.getInAllProgramPos());
		int iconVisibility = mSelectPosInAllProgram == inAllPos ? View.VISIBLE : View.INVISIBLE;
		holder.image.setVisibility(iconVisibility);
		if(View.VISIBLE == iconVisibility)
			mFirstImage = holder.image; 
		String iconUrl = liveData.getIco();
		ImageLoader.getInstance().displayImage(iconUrl, holder.icon, mOptions, animateFirstListener);
			
		return convertView;
	}
	
	class Holder{
		ImageView image;
		ImageView icon;
		TextView vodName;
	}

	public void setSelectPos(int selectPosInAllProgram){
		mSelectPosInAllProgram = selectPosInAllProgram;
		notifyDataSetChanged();
	}
	
	public void hiddenFirstShowImage(){
		if(null != mFirstImage)
			mFirstImage.setVisibility(View.INVISIBLE);
	}
}
