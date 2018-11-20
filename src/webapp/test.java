package webapp;

import java.io.PrintWriter;

import tomdog.App;
import tomdog.Request;
import tomdog.Response;

/**
 * 此为App的一个实例 即一个子类 实际的一个自定义网页App
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
		response.setContent("<h1>访问成功！</h1>");
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
