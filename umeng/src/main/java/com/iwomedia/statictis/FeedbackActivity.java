package com.iwomedia.statictis;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.UserInfo;

/**
 * 
 * 反馈页面
 * @author wangkai
 * @creation 2012年9月10日 下午3:24:47
 */
public class FeedbackActivity extends Activity implements OnClickListener{

	public static void start(Context context){
		Intent i = new Intent(context, FeedbackActivity.class);
		context.startActivity(i);
	} 
	
	private static final String KEY_UMENG_CONTACT_INFO_PLAIN_TEXT = "plain";
	
	private EditText feedbackEt;
	private View commitTv;
	private Conversation defaultConversation;

	private FeedbackAgent agent;
	private EditText contactInfoEdit;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_feedback);
		
		findViewById(R.id.left_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
		
		initView();
		
	}
	

	public void initView() {
		agent = new FeedbackAgent(this);

		feedbackEt = (EditText) findViewById(R.id.et_feedback_content);
		commitTv = findViewById(R.id.tv_submit_feedback);
		commitTv.setOnClickListener(this);
		contactInfoEdit = (EditText) findViewById(R.id.et_phone);

		defaultConversation = agent.getDefaultConversation();
	};
	
	
	/**
	 * 提交反馈信息
	 */
	public void commitFeedbackInfo() {

		UserInfo info = agent.getUserInfo();
		if (info == null)
			info = new UserInfo();
		Map<String, String> contact = info.getContact();
		if (contact == null)
			contact = new HashMap<String, String>();
		 String contact_info = contactInfoEdit.getEditableText().toString();
		if(contact_info != null){
			
			contact.put(KEY_UMENG_CONTACT_INFO_PLAIN_TEXT, contact_info);
			info.setContact(contact);
		}

		String content = feedbackEt.getText().toString().trim();			//反馈的内容
		if (TextUtils.isEmpty(content)) {
			return;
		}
		
		defaultConversation.addUserReply(content);
		defaultConversation.sync(null);
		Toast.makeText(this, "您的反馈已经提交", Toast.LENGTH_SHORT).show();
		finish();

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.tv_submit_feedback){
			commitFeedbackInfo();
		}
	}
}
