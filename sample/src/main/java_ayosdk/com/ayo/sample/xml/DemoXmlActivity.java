package com.ayo.sample.xml;

import android.util.Log;

import com.cowthan.sample.BaseDemoMenuActivity;
import com.cowthan.sample.menu.Leaf;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2016/3/29.
 */
public class DemoXmlActivity extends BaseDemoMenuActivity {

    @Override
    protected Leaf[] getMenus() {
        Leaf[] leaves = {
                new Leaf("DOM解析", "", null),
                new Leaf("SAX解析", "", null),
                new Leaf("PULL解析", "", null),
        };
        return leaves;
    }


    @Override
    public void onClicked(String btnText) {
        if(btnText.equals("DOM解析")){
            parseByDom();
        }else if(btnText.equals("SAX解析")){
            parseBySax();
        }else if(btnText.equals("PULL解析")){
            parseByPull();
        }
    }

    private void parseByDom(){
        try{
            javax.xml.parsers.DocumentBuilder myDocBuilder = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder();
            org.w3c.dom.Document document = myDocBuilder.parse(new InputSource(new StringReader(xml)));

            //找到根Element
            org.w3c.dom.Element root= document.getDocumentElement();

            //找到forexast_conditions标签，这个标签有多个
            NodeList nodes=root.getElementsByTagName("forecast_conditions");

            //解析出未来5天的天气预报
            for(int i = 0; i < nodes.getLength(); i++){
                //这是一个forecast_conditions标签
                Node node = nodes.item(i);
                Log.i("dom", "==============begin：" + node.getNodeName());
                NodeList children = node.getChildNodes();
                for(int j = 0; j < children.getLength(); j++){
                    //遍历子节点
                    Node n = children.item(j);
                    Log.i("dom", "子节点：" + n.getNodeName() + "==>" + n.getNodeValue());
                    NamedNodeMap attrs = n.getAttributes();
                    if(attrs != null){
                        for(int k = 0; k < attrs.getLength(); k++){
                            //遍历attr
                            Log.i("dom", "----" + attrs.item(k).getNodeName() + "==>" + attrs.item(k).getNodeValue());
                        }
                    }
                }
                Log.i("dom", "==============over\n\n");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void parseBySax(){
        try {
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxFactory.newSAXParser();//利用获取到的对象创建一个解析器

            //parse方式1
            XMLContentHandler handler = new XMLContentHandler();//设置defaultHandler
            StringReader r = new StringReader(xml);
            saxParser.parse(new InputSource(r), handler);//进行解析
            r.close();
            /* parse方式2
            XMLReader xmlReader =saxFactory.newSAXParser().getXMLReader(); //获取一个XMLReader
            xmlReader.setContentHandler(handler);
            xmlReader.parse(newInputSource(stream));
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class XMLContentHandler extends DefaultHandler {
        private static final String TAG = "XMLContentHandler";

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            Log.i(TAG, "解析内容："+new String(ch,start,length));
        }
        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            Log.i(TAG, "文档解析完毕。");
        }
        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            Log.i(TAG, localName+"解析完毕");
        }
        @Override
        public void startDocument() throws SAXException {
            Log.i(TAG, "开始解析... ...");
        }
        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            Log.i(TAG, "解析元素："+localName);

            if(localName.equals("high")){
                Log.i(TAG, "解析元素："+localName);
                if(attributes != null){
                    for(int i = 0; i < attributes.getLength(); i++){
                        Log.i(TAG, "====" + attributes.getLocalName(i) + ": " + attributes.getQName(i) + ": " + attributes.getValue(i));
                    }
                }
            }
        }
    }

    private void parseByPull(){

    }

    public static String xml = "<xml_api_reply version=\"1\">\n" +
            "    <weather module_id=\"0\" tab_id=\"0\" mobile_row=\"0\" mobile_zipped=\"1\" row=\"0\" section=\"0\">\n" +
            "        <forecast_information>\n" +
            "            <city data=\"Beijing, Beijing\"/>\n" +
            "            <postal_code data=\"beijing\"/>\n" +
            "            <latitude_e6 data=\"\"/>\n" +
            "            <longitude_e6 data=\"\"/>\n" +
            "            <forecast_date data=\"2012-07-24\"/>\n" +
            "            <current_date_time data=\"2012-07-24 15:30:00 +0000\"/>\n" +
            "            <unit_system data=\"SI\"/>\n" +
            "        </forecast_information>\n" +
            "        <current_conditions>\n" +
            "            <condition data=\"多云\"/>\n" +
            "            <temp_f data=\"77\"/>\n" +
            "            <temp_c data=\"25\"/>\n" +
            "            <humidity data=\"湿度： 78%\"/>\n" +
            "            <icon data=\"/ig/images/weather/cn_cloudy.gif\"/>\n" +
            "            <wind_condition data=\"风向： 东北、风速：2 米/秒\"/>\n" +
            "        </current_conditions>\n" +
            "        <forecast_conditions>\n" +
            "            <day_of_week data=\"周二\"/>\n" +
            "            <low data=\"22\"/>\n" +
            "            <high data=\"30\"/>\n" +
            "            <icon data=\"/ig/images/weather/cn_cloudy.gif\"/>\n" +
            "            <condition data=\"多云\"/>\n" +
            "        </forecast_conditions>\n" +
            "        <forecast_conditions>\n" +
            "            <day_of_week data=\"周三\"/>\n" +
            "            <low data=\"24\"/>\n" +
            "            <high data=\"30\"/>\n" +
            "            <icon data=\"/ig/images/weather/chance_of_rain.gif\"/>\n" +
            "            <condition data=\"可能有雨\"/>\n" +
            "        </forecast_conditions>\n" +
            "        <forecast_conditions>\n" +
            "            <day_of_week data=\"周四\"/>\n" +
            "            <low data=\"25\"/>\n" +
            "            <high data=\"29\"/>\n" +
            "            <icon data=\"/ig/images/weather/thunderstorm.gif\"/>\n" +
            "            <condition data=\"雷阵雨\"/>\n" +
            "        </forecast_conditions>\n" +
            "        <forecast_conditions>\n" +
            "            <day_of_week data=\"周五\"/>\n" +
            "            <low data=\"24\"/>\n" +
            "            <high data=\"31\"/>\n" +
            "            <icon data=\"/ig/images/weather/chance_of_storm.gif\"/>\n" +
            "            <condition data=\"可能有暴风雨\"/>\n" +
            "        </forecast_conditions>\n" +
            "    </weather>\n" +
            "</xml_api_reply>";
}
