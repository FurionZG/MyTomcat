package com.mytomcat.server;

import java.io.IOException;
import java.io.OutputStream;
/**
 * 响应类
 * @author 四爷
 *
 */
public class MyResponse {
	private OutputStream out;
	public static final String responseHeader = "HTTP/1.1 200 \r\n" + "Content-Type: text/html\r\n" + "\r\n";

	/**
	 * Response的构造方法是流对象
	 * 
	 * @param out
	 */
	public MyResponse(OutputStream out) {
		this.out = out;
	}

	/**
	 * 向客户端发送数据的方法
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
