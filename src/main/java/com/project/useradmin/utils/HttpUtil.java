package com.project.useradmin.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * http请求工具类
 * @author dbz
 *
 */
public class HttpUtil {
	private static String encoding = "GBK";

	/**
	 * 发送post请求
	 * 
	 * @param url
	 *            url
	 * @Param msg 参数
	 */

	public static String postMessage(String url, String msg) {
		String data = null;
		try {
			URL dataUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
			con.setRequestProperty("accept", "*/*");
			con.setRequestProperty("connection", "Keep-Alive");
			con.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			OutputStream os = con.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.write(msg.getBytes(encoding));
			dos.flush();
			dos.close();
			String sysSep = System.getProperty("line.separator");
			String str = "";
			InputStreamReader isr = new InputStreamReader(con.getInputStream(), encoding);
			BufferedReader br = new BufferedReader(isr);
			String temp = null;
			while ((temp = br.readLine()) != null) {
				if (temp.equals("")) {
					str += sysSep;
				} else {
					str += temp;
				}
			}
			data = str;
			con.disconnect();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return data;
	}

	 
}
