package webapp;

import java.io.PrintWriter;

import tomdog.App;
import tomdog.Request;
import tomdog.Response;

/**
 * ��ΪApp��һ��ʵ�� ��һ������ ʵ�ʵ�һ���Զ�����ҳApp
 * @author czf
 *
 */

public class test extends App{
	public test(){
		super();
	}
	
	@Override
	public void doGet(Response response,Request request){
		super.doGet(response, request);
		response.setContent("<h1>���ʳɹ���</h1>");
		response.setCookies("username","czfzc");
		PrintWriter pw=response.getWriter();
		pw.println("<h2>this is my test</h2>");
		pw.flush();
	}
	
	@Override
	public void doPost(Response response,Request request){
		super.doPost(response, request);
	}
	
}
