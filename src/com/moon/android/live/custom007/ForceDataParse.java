package com.moon.android.live.custom007;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.moon.android.live.custom007.model.ForceChanInfo;
import com.moon.android.live.custom007.model.ForceLoadItem;

public class ForceDataParse {
	
	/**
	 * http://127.0.0.1:9906/api?func=query_chan_data_info&id=
	 * @param xml
	 * @return
	 */
	public static boolean isVodHasSignal(String xml){
		Document doc = null;
		try {
			String parseXml = xml.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("ver=1.0", "");
			doc = DocumentHelper.parseText(parseXml); 
			Node root = doc.getRootElement();
			Element element = (Element) root.selectSingleNode("/forcetv/channel/datainfo");
			int begin = Integer.valueOf(element.attribute("begin").getValue());
			int end = Integer.valueOf(element.attribute("end").getValue());
			if(begin == 0 && end == 0)
				return false;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * @param xml
	 * @return
	 * flag �Ƿ�ɹ������ı�־  0 Ϊ�ɹ�����  ����Ϊʧ��
	 * reason �����Ƿ�ɹ��ľ����������
	 */
	public static ForceLoadItem vodCanPlay(String xml){
		Document doc = null;
		try {
			String parseXml = xml.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("ver=1.0", "");
			doc = DocumentHelper.parseText(parseXml); 
			Node root = doc.getRootElement();
			Element element = (Element) root.selectSingleNode("/forcetv/result");
			int flag = Integer.valueOf(element.attribute("ret").getValue());
			String reason= element.attribute("reason").getValue();
			return new ForceLoadItem(flag,reason);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * http://127.0.0.1:9906/api?func=query_chan_info&id=
	 * @param mForcePlayInfo
	 * @return 
	 */
	public static ForceChanInfo parseForcePlayInfo(String mForcePlayInfo) {
		Document doc = null;
		try {
			String parseXml = mForcePlayInfo.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("ver=1.0", "");
			doc = DocumentHelper.parseText(parseXml); 
			Node root = doc.getRootElement();
			Element elementr = (Element) root.selectSingleNode("/forcetv/result");
			int ret = Integer.valueOf(elementr.attribute("ret").getValue());
			String reason = elementr.attribute("reason").getValue();

			Element element = (Element) root.selectSingleNode("/forcetv/channel");
			int cacheTime = 0;
			int traffic = 0;
			int checkRet = -1;
			String checkReason = null;
			try{
				cacheTime = Integer.valueOf(element.attribute("cache_time").getValue());
				traffic = Integer.valueOf(element.attribute("download_flowkbps").getValue());
				checkRet = Integer.valueOf(element.attribute("check_ret").getValue());
				checkReason = element.attribute("check_reason").getValue();
			}catch(Exception e){
				e.printStackTrace();
			}
			ForceChanInfo chanInfo = new ForceChanInfo(ret,reason,checkRet,checkReason,cacheTime,traffic);
			return chanInfo;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	};
}
