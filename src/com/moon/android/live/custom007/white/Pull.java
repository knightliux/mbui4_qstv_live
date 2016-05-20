package com.moon.android.live.custom007.white;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


public class Pull {
	public List<ChianPull> getPersons(InputStream in) throws Exception {
		ChianPull p = null;
		List<ChianPull> persons = null;
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(in, "UTF-8");
		int event = parser.getEventType();// 产生第一个事�?
		while (event != XmlPullParser.END_DOCUMENT) { // 如果不是文档结束事件
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				persons = new ArrayList<ChianPull>(); // 在文档的�?��实例化集�?
				break;
			case XmlPullParser.START_TAG:
				String names = parser.getName();// 取得当前解析器指向的元素名称
				if ("heartBeatServer".equals(names)) {
					p = new ChianPull();

				} else if ("Server".equals(names)) {
					p.chian = parser.getAttributeValue(0);
					p.ip = parser.getAttributeValue(1);
					// System.out.println("china=="+p.chian+",ip=="+p.ip);
				}
				break;
			case XmlPullParser.END_TAG:
				if ("heartBeatServer".equals(parser.getName())) { // person标签结束就把数据存入集合
					persons.add(p);
					p = null;
				}
				break;
			}
			event = parser.next();
		}
		return persons;
	}
}
