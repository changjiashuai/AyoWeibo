package com.ayoview.sample.ztextview;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import org.ayo.jlog.JLog;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 自定义标签解析类，处理：呵呵呵<span style="{color:#e60012}">哈哈哈</span>嘿嘿嘿
 * Created by Administrator on 2016/3/29.
 */
public class SpanTagHandler implements Html.TagHandler {
    private int sIndex = 0;
    private  int eIndex=0;
    private final Context mContext;

    public SpanTagHandler(Context context){
        mContext=context;
    }

    private int count = 0;

    /**
     *  原文是：<span style="{color:#e60012}">哈哈哈</span>：
     *  html--第1次进来，opening=true, output=, tag=html
     *  html--第2次进来，opening=true, output=, tag=body
     *  html--第3次进来，opening=true, output=, tag=span           ---
     *  html--第4次进来，opening=false, output=哈哈哈, tag=span     ---
     *  html--第5次进来，opening=false, output=哈哈哈, tag=body
     *  html--第6次进来，opening=false, output=哈哈哈, tag=html
     * @param opening
     * @param tag
     * @param output
     * @param xmlReader
     */
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        count++;
        JLog.i("html", "html--第" + count + "次进来，opening=" + opening + ", output=" + output + ", tag=" + tag);

        //如果在这里直接用XmlReader解析，也没有解析多次？？比较奇怪，还是去看看源码吧
        //defaultHandler = xmlReader.getContentHandler();
        xmlReader.setContentHandler(new XMLContentHandler());

        if (tag.toLowerCase().equals("span")) {
            if (opening) {
                sIndex = output.length();
                //如果在这里用XmlReader解析，已经过了<span>标签的开始了，好像是解析的这部分：哈哈哈</span>
            }else {
                eIndex = output.length();
                if(fontColor == null || fontColor.equals("")){
                    fontColor = "#000000";
                }else{

                }
                JLog.i("html", "html--第" + count + "次进来，fontColor = " + fontColor);
                output.setSpan(new ForegroundColorSpan(Color.parseColor(fontColor)), sIndex, eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }


        }
    }

    private String fontColor = "";

    public class XMLContentHandler extends DefaultHandler {
        private static final String TAG = "XMLContentHandler";

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            Log.i(TAG, "解析内容：" + new String(ch, start, length));
        }
        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            Log.i(TAG, "文档解析完毕。");
            //defaultHandler.endDocument();
        }
        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            Log.i(TAG, localName+"解析完毕");
            //defaultHandler.endElement(uri, localName, qName);
        }
        @Override
        public void startDocument() throws SAXException {
            Log.i(TAG, "开始解析... ...");
        }
        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            Log.i(TAG, "解析元素："+localName);

            if(localName.equals("span")){
                Log.i(TAG, "解析元素："+localName);
                if(attributes != null){
                    for(int i = 0; i < attributes.getLength(); i++){
                        Log.i(TAG, "====" + attributes.getLocalName(i) + ": " + attributes.getQName(i) + ": " + attributes.getValue(i));
                        if(attributes.getLocalName(i).equals("style")){
                            String style = attributes.getValue(i); //{color:#e60012}
                            fontColor = style.replace("{", "").replace("}", "").replace("color", "").replace(":", "");
                            Log.i(TAG, "fontColor=" + fontColor);
                        }
                    }
                }
            }
        }
    }
}