package com.test.servlet;

import com.mytomcat.server.MyRequest;
import com.mytomcat.server.MyResponse;
import com.mytomcat.server.Servlet;

/**
 * ����Servlet
 * 
 * @author ��ү
 *
 */
public class TestServlet extends Servlet {

	@Override
	public void doGet(MyRequest request, MyResponse response) {
		int i =9/0;
		response.write(MyResponse.responseHeader + "Get Method");
	}

	@Override
	public void doPost(MyRequest request, MyResponse response) {
		response.write(MyResponse.responseHeader + "Post Method");

	}

}
