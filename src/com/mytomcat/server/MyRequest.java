package com.mytomcat.server;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 * 请求类
 * 
 * @author 四爷
 *
 */
public class MyRequest {
	private String url;
	private String method;
	private InputStream in;
	private static Logger logger = Logger.getLogger(MyRequest.class);

	/**
	 * Request的构造方法是流对象
	 * 
	 * @param in
	 */
	public MyRequest(InputStream in) {
		this.in = in;
		// 获取请求信息
		String content = null;
		byte[] buf = new byte[1024];
		int len = 0;
		try {
			if ((len = in.read(buf)) > 0) {
				content = new String(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		parseContent(content);
		logger.info("Method:" + method + ",Url:" + url);
	}

	/**
	 * 通过解析请求信息，完成对Method和Url的赋值
	 * 
	 * @param content
	 */
	private void parseContent(String content) {
		String command = content.split("\\n")[0];
		String words[] = command.split("\\s");
		setMethod(words[0]);
		setUrl(words[1]);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
