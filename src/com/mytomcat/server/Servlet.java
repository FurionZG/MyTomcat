package com.mytomcat.server;

/**
 * ģ��Servlet�ӿ�
 * 
 * @author ��ү
 *
 */
public abstract class Servlet {
	public abstract void doGet(MyRequest request, MyResponse response);

	public abstract void doPost(MyRequest request, MyResponse response);
/**
 * ���ݲ�ͬ������Get/Post�����ò�ͬ�ķ���
 * @param request
 * @param response
 */
	public void serviece(MyRequest request, MyResponse response) {
		if ("GET".equals(request.getMethod())) {
			doGet(request, response);
		} else {
			doPost(request, response);
		}
	}
}
