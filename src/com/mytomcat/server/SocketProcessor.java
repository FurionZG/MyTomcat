package com.mytomcat.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import org.apache.log4j.Logger;

/**
 * ���̴߳�����յ��Ŀͻ���Socket
 * �ַ�����,���������е�·������ע����web.xml�еĶ�Ӧ���࣬��û�У���鿴�Ƿ�Ϊ��̬ҳ�棬�������ǣ��򷵻�404��ҳ������쳣ʱ������500
 * 
 * @param request
 * @param response
 * 
 * 
 * @author ��ү
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
				// 200 �����������������ʽGet/Post�����ò�ͬ�ķ���
				Servlet servlet = (Servlet) clz.newInstance();
				servlet.serviece(request, response);
			} else if (pageFile.exists()) {
				// ���ҳ����ڣ���Ϊ��̬ҳ�����󣬷���ҳ�����ݵ��ͻ���
				logger.info("PageFile Absolute Path:" + pageFile.getAbsolutePath());
				sendStaticResource(socket.getOutputStream(), pageFile);
			} else {
				// 404 �������·����web.xml�в�����
				response.write("404 Not Found");
			}
		} catch (Exception e) {
			// 500 ҳ�����׳��쳣
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
		// ����һ���ֽ����飬���ڴ�ű�������ľ�̬ҳ����Դ
		byte[] bytes = new byte[2048];
		// ����һ���ļ������������ڻ�ȡ����ҳ�������
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(pageFile);
			int i = 0;
			// ��ͻ��˷��;�̬ҳ����Ϣ
			while ((i = fileIn.read(bytes)) != -1) {
				out.write(bytes, 0, i);
			}
		} catch (Exception e) {
			// 500 ҳ�����׳��쳣
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
