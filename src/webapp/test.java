package webapp;

import java.io.File;
import java.io.PrintWriter;

import tomdog.App;
import tomdog.Request;
import tomdog.Response;
import tomdog.htmlLoader;

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
		response.setCookies("username","czfzc");
		super.doGet(response, request);
	}
	
	@Override
	public void doPost(Response response,Request request){
		super.doPost(response, request);
	}
	
}
