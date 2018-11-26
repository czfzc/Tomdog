package tomdog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Request {
	
	/**
	 * paramsmap存储get与post的参数名和参数值
	 * cookiesmap存储客户端传来的所有cookie
	 */
	
	private String content="";
	private HashMap<String,String> paramsmap=new HashMap<>();
	private HashMap<String,String> cookiesmap=new HashMap<>();
	private String queryString="";
	private int contentLength=0;
	private String method="GET";
	private String path="";
	
	/**
	 * 构造函数 接受来自客户端的输入流
	 * 并分析输入流 将内容归类
	 * @param inputstream
	 */
	
	public Request(InputStream inputstream){
        try {
            BufferedReader bd=new BufferedReader(new InputStreamReader(inputstream));
            
            /**
             * 接受HTTP请求
             */
            String requestHeader;
			while((requestHeader=bd.readLine())!=null&&!requestHeader.isEmpty()){
			    content+=requestHeader;
			    System.out.println(requestHeader);
			    /**
			     * 获得GET参数
			     */
			    if(requestHeader.startsWith("GET")){
			    	method="GET";
			        int begin = requestHeader.indexOf("/?")+2;
			        int end = requestHeader.indexOf("HTTP/");
			        String condition=requestHeader.substring(begin, end);
			        queryString=condition;
			        
			        path=getPath(requestHeader);
			        
			        addParametersToMap(condition);
			    }else if(requestHeader.startsWith("POST")){
			    	method="POST";
			    }
			    
			    /**
			     * 获得POST参数
			     * 1.获取请求内容长度
			     */
			    if(requestHeader.startsWith("Content-Length")){
			        int begin=requestHeader.indexOf("Content-Length:")+"Content-Length:".length();
			        String postParamterLength=requestHeader.substring(begin).trim();
			        contentLength=Integer.parseInt(postParamterLength);
			    }
			    
			    if(requestHeader.startsWith("Cookie")){
			        int begin=requestHeader.indexOf("Cookie:")+"Cookie:".length();
			    	String[] cookie=requestHeader.substring(begin).trim().split(";");
			    	for(int i=0;i<cookie.length;i++){
			    		if(cookie[i].contains("=")){
			    			cookiesmap.put(cookie[i].split("=")[0],cookie[i].split("=")[0]);
			    		}
			    	}
			    }
			    
			}
		
	        StringBuffer sb=new StringBuffer();
	        if(contentLength>0){
	            for (int i = 0; i < contentLength; i++) {
	                sb.append((char)bd.read());
	            }
	            addParametersToMap(sb.toString());
	        }
        
        } catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getPath(String line){
		return line.split(" ")[1].replaceFirst("/", "");	
	}
	
	public static void main(String[] args){

		System.out.println(getPath("GET http://127.0.0.1:8080/js/jquery-3.2.1.min.js HTTP/1.1"));
	}
	
	/**
	 * 私有方法 将一串参数存入参数图中
	 * @param parameters
	 */

	private void addParametersToMap(String parameters){
		String[] params=parameters.split("&");
		for(int i=0;i<params.length;i++){
			if(params[i].contains("="))
				paramsmap.put(params[i].split("=")[0], params[i].split("=")[1]);
		}
	}
	
	/**
	 * 获取url中的参数字符串
	 * @return
	 */
	
	public String getQueryString(){
		return queryString;
	}
	
	/**
	 * 获取post的参数长度
	 * @return
	 */
	
	public int getContentLength(){
		return contentLength;
	}
	
	/**
	 * 获取客户端的请求方法 post或者get
	 * @return
	 */
	
	public String getMethod(){
		return method;
	}
	
	/**
	 * 获取来自客户端的cookie
	 * @param name 传入的cookie名
	 * @return
	 */
	
	public String getCookie(String name){
		return cookiesmap.get(name);
	}
	
	/**
	 * 获取属性的值
	 * @param name 传入的属性名
	 * @return
	 */
	
	public String getParameter(String name){
		return paramsmap.get(name);
	}
	
	/**
	 * 重写的object超父类的方法 返回来自客户端浏览器的纯raw代码
	 */
	
	/**
	 * 获取网页
	 * @return
	 */
	
	public String getPath(){
		return path;
	}
	
	@Override
	public String toString(){
		return content;
	}
}
