package webapp;

import java.io.File;
import java.io.PrintWriter;

import tomdog.App;
import tomdog.Request;
import tomdog.Response;
import tomdog.htmlLoader;

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
		response.setCookies("username","czfzc");
		super.doGet(response, request);
	}
	
	@Override
	public void doPost(Response response,Request request){
		super.doPost(response, request);
	}
	
}
