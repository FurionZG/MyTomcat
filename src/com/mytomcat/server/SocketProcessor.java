package com.mytomcat.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import org.apache.log4j.Logger;

/**
 * 多线程处理接收到的客户端Socket
 * 分发方法,根据请求中的路径访问注册在web.xml中的对应的类，若没有，则查看是否为静态页面，若都不是，则返回404，页面出现异常时，返回500
 * 
 * @param request
 * @param response
 * 
 * 
 * @author 四爷
 *
 */
public class SocketProcessor extends Thread {
	protected Socket socket;
	private static Logger logger = Logger.getLogger(TomcatServer.class);

	public SocketProcessor(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		logger.info("Thread:" + Thread.currentThread().getName());
		MyRequest request = null;
		MyResponse response = null;
		try {
			request = new MyRequest(socket.getInputStream());
			response = new MyResponse(socket.getOutputStream());
			String url = request.getUrl();
			Class<?> clz = TomcatServer.servletMap.get(url);
			File pageFile = new File(System.getProperty("user.dir") + "\\WebContent" + url.substring(url.indexOf("/")));
			if (null != clz) {
				// 200 正常情况，根据请求方式Get/Post，调用不同的方法
				Servlet servlet = (Servlet) clz.newInstance();
				servlet.serviece(request, response);
			} else if (pageFile.exists()) {
				// 如果页面存在，则为静态页面请求，发送页面数据到客户端
				logger.info("PageFile Absolute Path:" + pageFile.getAbsolutePath());
				sendStaticResource(socket.getOutputStream(), pageFile);
			} else {
				// 404 类或请求路径在web.xml中不存在
				response.write("404 Not Found");
			}
		} catch (Exception e) {
			// 500 页面中抛出异常
			response.write("500 Error\r\n" + Arrays.toString(e.getStackTrace()));
		} finally {
			if (null != socket) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void sendStaticResource(OutputStream out, File pageFile) {
		// 定义一个字节数组，用于存放本次请求的静态页面资源
		byte[] bytes = new byte[2048];
		// 定义一个文件输入流，用于获取请求页面的内容
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(pageFile);
			int i = 0;
			// 向客户端发送静态页面信息
			while ((i = fileIn.read(bytes)) != -1) {
				out.write(bytes, 0, i);
			}
		} catch (Exception e) {
			// 500 页面中抛出异常
			new MyResponse(out).write("500 Error\r\n" + Arrays.toString(e.getStackTrace()));
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
