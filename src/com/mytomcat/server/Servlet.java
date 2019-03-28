package com.mytomcat.server;

/**
 * 模拟Servlet接口
 * 
 * @author 四爷
 *
 */
public abstract class Servlet {
	public abstract void doGet(MyRequest request, MyResponse response);

	public abstract void doPost(MyRequest request, MyResponse response);
/**
 * 根据不同的请求Get/Post，调用不同的方法
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
