package com.example.leanne.capstonedesign_1_;

/*
 * Created by dalaetm on 2016-04-07.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RequestMsgSender extends AsyncTask<String, String, String> {

	/*
	protected void onPreExecute() {
		super.onPreExecute();
	}*/
	@Override
	protected String doInBackground(String... inputMsg) {
		String replyLine = null;
		String requestMsgToSend = inputMsg[0] + "\n";
		byte[] requestToSend = new byte[requestMsgToSend.length()];
		try {
			requestToSend = requestMsgToSend.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			Log.d("doing in background", "request msg: " + requestMsgToSend);
			SocketConnector.getConnectedSocket().getOutToServer().write(requestToSend);
		} catch (IOException e) {
			//e.printStackTrace();
		}
		try {
			replyLine = SocketConnector.getConnectedSocket().getBr().readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d("doing in background", "reply msg: " + replyLine);
		SocketConnector.getConnectedSocket().setReplyMsg(replyLine);
		return replyLine;

	}

	@Override
	protected void onPostExecute(String replyLine) {
		super.onPostExecute(replyLine);
		try {
			SocketConnector.getConnectedSocket().getOutToServer().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}