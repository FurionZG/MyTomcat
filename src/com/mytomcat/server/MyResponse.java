package com.mytomcat.server;

import java.io.IOException;
import java.io.OutputStream;
/**
 * ��Ӧ��
 * @author ��ү
 *
 */
public class MyResponse {
	private OutputStream out;
	public static final String responseHeader = "HTTP/1.1 200 \r\n" + "Content-Type: text/html\r\n" + "\r\n";

	/**
	 * Response�Ĺ��췽����������
	 * 
	 * @param out
	 */
	public MyResponse(OutputStream out) {
		this.out = out;
	}

	/**
	 * ��ͻ��˷������ݵķ���
	 * 
	 * @param content
	 */
	public void write(String content) {
		try {
			out.write(content.getBytes());
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
