package org.ayo.app.tmpl.pagegroup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import genius.android.view.R;

/**
 * 定制的 组件
 *  描述：
 *  	横向排列N个条目组件
 *  关联Resource:
 *  	layout: 	ayo_layout_page_group_custom_radio_button.xmlustom_radio_button.xml
 *  	drawable: 	page_indicator_focused.png		
 */

@SuppressLint("NewApi")
public class CustomRadioGroup extends LinearLayout{
	/**
	 * IMAGE/TEXT 	:	条目的图片/文字  
	 * START/DIF	: 	初始值/和目标的差值
	 * R/G/B		：  	目标颜色RGB格式下的R/G/B
	 */
	private static final int 
		TEXT_START_COLOR=Color.GRAY,
		END_COLOR=Color.parseColor("#ff0000"),
		TEXT_START_R=Color.red(TEXT_START_COLOR),		
		TEXT_START_G=Color.green(TEXT_START_COLOR),			
		TEXT_START_B=Color.blue(TEXT_START_COLOR),
		TEXT_DIF_R=Color.red(END_COLOR)-TEXT_START_R,	
		TEXT_DIF_G=Color.green(END_COLOR)-TEXT_START_G,		
		TEXT_DIF_B=Color.blue(END_COLOR)-TEXT_START_B;
	;
	//相关的资源ID：
	private final int ID_LAYOUT= R.layout.ayo_layout_page_group_custom_radio_button,ID_LAYOUT_TEXT=R.layout.ayo_layout_page_group_custom_radio_button2,
			ID_IMAGE_TOP=R.id.custom_radio_button_image_top,
			ID_IMAGE_BOTTOM=R.id.custom_radio_button_image_botom,
			ID_TEXT=R.id.custom_radio_button_text,
			ID_NEWS=R.id.custom_radio_button_news;
	//条目变更监听
	private OnItemChangedListener onItemChangedListener;
	//条目的LinearLayout.LayoutParams
	private LayoutParams itemLayoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
	private LayoutInflater inflater;
	//当前选择的条目
	private int checkedIndex=0;
	//条目列表
	private List<RadioButton> lists=new ArrayList<RadioButton>();
	public CustomRadioGroup(Context c) {
		super(c);
		init();
	}
	public CustomRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();	
	}
	/**
	 * 构造函数里适用的初始化部分
	 */
	private void init() {
		inflater=LayoutInflater.from(getContext());
		itemLayoutParams.weight=1;
		setOrientation(HORIZONTAL);
	}
	/**
	 * @param f 		颜色渐变参考值。
	 */
	private static int getNewColor(float f){
		int newR,newG,newB;
		newR=(int)(TEXT_DIF_R*f)+TEXT_START_R;
		newG=(int)(TEXT_DIF_G*f)+TEXT_START_G;
		newB=(int)(TEXT_DIF_B*f)+TEXT_START_B;
		return Color.rgb(newR, newG, newB);
	}
	/**
	 * 添加条目
	 * @param unSelected 没有选中时的图片
	 * @param selected	 选中时的图片
	 * @param text		 文本内容
	 */
	public void addItem(int unSelected,int selected,String text){
		RadioButton rb=new RadioButton(unSelected,selected,text);
		final int i=lists.size();
		rb.v.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				setCheckedIndex(i);
			}
		});
		addView(rb.v);
		lists.add(rb);
	}
	/**
	 * 添加条目
	 * @param text		 文本内容
	 */
//	public void addItem(String text){
//		RadioButton rb=new RadioButton(text);
//		final int i=lists.size();
//		rb.v.setOnClickListener(new OnClickListener() {
//			public void onClick(View arg0) {
//				setCheckedIndex(i);
//			}
//		});
//		addView(rb.v);
//		lists.add(rb);
//	}
	
	public void addItem(String text, boolean addSeperator){
		RadioButton rb=new RadioButton(text);
		final int i=lists.size();
		rb.v.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				setCheckedIndex(i);
			}
		});
		addView(rb.v);
		View sep = new View(getContext());
		sep.setBackgroundColor(Color.parseColor("#11000000"));
		LayoutParams lp = new LayoutParams(1, LayoutParams.MATCH_PARENT);
		if(addSeperator) addView(sep, lp);
		lists.add(rb);
	}
	
	/**
	 * 获取选中的条目索引
	 */
	public int getCheckedIndex() {
		return checkedIndex;
	}	
	/**
	 * 两个item 改变透明度
	 * @param leftIndex 	左边的条目索引
	 * @param rightIndex	右边的条目索引
	 * @param alpha			[0,1)透明度
	 */
	@SuppressLint("NewApi")
	public void itemChangeChecked(int leftIndex,int rightIndex,float alpha){
		if (leftIndex<0||leftIndex>=lists.size()||rightIndex<0||rightIndex>=lists.size()) {
			return ;
		}
		RadioButton a=lists.get(leftIndex);
		RadioButton b=lists.get(rightIndex);
		a.top.setAlpha(alpha);
		a.bottom.setAlpha(1-alpha);
		b.top.setAlpha(1-alpha);
		b.bottom.setAlpha(alpha);
		int aColor=getNewColor(1-alpha);
		int bColor=getNewColor(alpha);
		a.text.setTextColor(aColor);
		b.text.setTextColor(bColor);
	}
	/**
	 * 选择制定索引的条目
	 */
	public void setCheckedIndex(int index) {
		
//		if(index == 2){
//			if(!UserInfo.isSignup()){
//				LoginActivity.start(getContext());
//				return;
//			}
//		}
		
		for (int i = 0; i < lists.size(); i++) {
			if (i==index) {
				lists.get(i).setCheceked(true);
				lists.get(i).text.setTextColor(END_COLOR);
			}else{
				lists.get(i).setCheceked(false);
				lists.get(i).text.setTextColor(TEXT_START_COLOR);
			}
		}
		this.checkedIndex=index;
		if (this.onItemChangedListener!=null) {
			onItemChangedListener.onItemChanged(this.checkedIndex);
		}
	}
	/**
	 * 设置指定索引的条目的消息数量
	 * @param index 条目的索引 
	 * @param count	消息的数量
	 */
	public void setItemNewsCount(int index,int count){
		lists.get(index).setNewsCount(count);
	}
	
	/**
	 * 设置条目变更监听器
	 * @param onItemChangedListener
	 */
	public void setOnItemChangedListener(OnItemChangedListener onItemChangedListener) {
		this.onItemChangedListener = onItemChangedListener;
	}
	
	/**
	 * 自定义的RadioButton
	 */
    private	class RadioButton {
		View v;				//条目样式
		ImageView top,bottom;	//条目的图片
		TextView text,news;	//条目的文字，消息
		public RadioButton(int unSelected,int selected,String string) {
			v=inflater.inflate(ID_LAYOUT, null);
			top=(ImageView)v.findViewById(ID_IMAGE_TOP);
			bottom=(ImageView)v.findViewById(ID_IMAGE_BOTTOM);
			text=(TextView)v.findViewById(ID_TEXT);
			news=(TextView)v.findViewById(ID_NEWS);
			
			top.setImageResource(unSelected);
			top.setAlpha(1.0f);
			bottom.setImageResource(selected);
			bottom.setAlpha(0.0f);
			text.setText(string);
			news.setVisibility(INVISIBLE);
			v.setLayoutParams(itemLayoutParams);
		}
		
		public RadioButton(String string) {
			v=inflater.inflate(ID_LAYOUT_TEXT, null);
			top=(ImageView)v.findViewById(ID_IMAGE_TOP);
			bottom=(ImageView)v.findViewById(ID_IMAGE_BOTTOM);
			text=(TextView)v.findViewById(ID_TEXT);
			news=(TextView)v.findViewById(ID_NEWS);
			
			top.setAlpha(1.0f);
			bottom.setAlpha(0.0f);
			text.setText(string);
			news.setVisibility(INVISIBLE);
			v.setLayoutParams(itemLayoutParams);
		}
		/**
		 * 设置消息数量
		 */
		void setCheceked(boolean b){
			if (b) {
				top.setAlpha(0.0f);
				bottom.setAlpha(1.0f);
			}else{
				top.setAlpha(1.0f);
				bottom.setAlpha(0.0f);
			}
		}
		
		void setNewsCount(int count){
			if (count<=0) {
				news.setVisibility(INVISIBLE);
			}else {
				String c = count + "";
				if(count > 99){
					c = "99+";
				}
				news.setText(c);
				news.setVisibility(VISIBLE);
			}
		}
		
		void setText(String str){
			this.text.setText(str);
		}
	}
	/**
	 * 条目变更监听接口
	 */
	public interface OnItemChangedListener{
		public void onItemChanged(int index);
	}
	
	public void setText(int index, String str){
		try {
			if(index >= 0 && index < lists.size()){
				lists.get(index).setText(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
