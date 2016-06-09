package com.example.leanne.capstonedesign_1_;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * Created by dalaetm on 2016-04-12.
 */
public class SocketConnector implements Runnable {

	private String receiverHost;
	private int receiverPort;

	private DataOutputStream outToServer;
	private Socket senderSocket;
	private BufferedReader br;

	private String replyMsg;

	public SocketConnector() {
		receiverPort = 7779;
		receiverHost = "192.168.0.8";
	}

	public void run() {
		try {
			InetAddress receiverIP = InetAddress.getByName(receiverHost);
			Log.d("IP: ", receiverHost);
			Log.d("IP: ", receiverIP.toString());
		} catch (UnknownHostException uhe) {
			Log.d("unknown host: ", receiverHost);
			return;
		}
		// connect a socket to the receiver
		try {
			senderSocket = new Socket(receiverHost, receiverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}


		try {
			outToServer = new DataOutputStream(senderSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();

		}
		try {
			br = new BufferedReader(new InputStreamReader(senderSocket.getInputStream(), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public DataOutputStream getOutToServer() {
		return outToServer;
	}

	public BufferedReader getBr() {
		return br;
	}

	public static SocketConnector getConnectedSocket() {
		return connectedSocket;
	}

	public void setReplyMsg(String replyMsgFromAsyncTask) {
		replyMsg = replyMsgFromAsyncTask;
	}

	public String getReplyMsg() {
		return replyMsg;
	}

	private static SocketConnector connectedSocket = new SocketConnector();
}
