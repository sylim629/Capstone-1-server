package com.example.leanne.capstonedesign_1_;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * Created by dalaetm on 2016-04-12.
 */
public class SocketConnector implements Runnable {

	private String receiverHost;
	private InetAddress receiverIP;
	private int receiverPort;

	private DataInputStream inFromServer;
	private DataOutputStream outToServer;
	private Socket senderSocket;
	private BufferedReader br;

	private String replyMsg;

    public SocketConnector() {
        receiverPort = 7772;
        receiverHost = "119.67.245.149";
    }
    public void run() {
        try {
            receiverIP = InetAddress.getByName(receiverHost);
            Log.d("IP: " , receiverHost);

        } catch (UnknownHostException uhe) {
            Log.d("unknown host: " , receiverHost);
            return;
        }
        // connect a socket to the receiver
        try {
            senderSocket = new Socket(receiverHost, receiverPort);
        } catch (ConnectException e) {
            // exit (or wait until the receiver is ready)
            e.printStackTrace();
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
