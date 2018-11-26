package tomdog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Request {
	
	/**
	 * paramsmap�洢get��post�Ĳ������Ͳ���ֵ
	 * cookiesmap�洢�ͻ��˴���������cookie
	 */
	
	private String content="";
	private HashMap<String,String> paramsmap=new HashMap<>();
	private HashMap<String,String> cookiesmap=new HashMap<>();
	private String queryString="";
	private int contentLength=0;
	private String method="GET";
	private String path="";
	
	/**
	 * ���캯�� �������Կͻ��˵�������
	 * ������������ �����ݹ���
	 * @param inputstream
	 */
	
	public Request(InputStream inputstream){
        try {
            BufferedReader bd=new BufferedReader(new InputStreamReader(inputstream));
            
            /**
             * ����HTTP����
             */
            String requestHeader;
			while((requestHeader=bd.readLine())!=null&&!requestHeader.isEmpty()){
			    content+=requestHeader;
			    System.out.println(requestHeader);
			    /**
			     * ���GET����
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
			     * ���POST����
			     * 1.��ȡ�������ݳ���
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
	 * ˽�з��� ��һ�������������ͼ��
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
	 * ��ȡurl�еĲ����ַ���
	 * @return
	 */
	
	public String getQueryString(){
		return queryString;
	}
	
	/**
	 * ��ȡpost�Ĳ�������
	 * @return
	 */
	
	public int getContentLength(){
		return contentLength;
	}
	
	/**
	 * ��ȡ�ͻ��˵����󷽷� post����get
	 * @return
	 */
	
	public String getMethod(){
		return method;
	}
	
	/**
	 * ��ȡ���Կͻ��˵�cookie
	 * @param name �����cookie��
	 * @return
	 */
	
	public String getCookie(String name){
		return cookiesmap.get(name);
	}
	
	/**
	 * ��ȡ���Ե�ֵ
	 * @param name �����������
	 * @return
	 */
	
	public String getParameter(String name){
		return paramsmap.get(name);
	}
	
	/**
	 * ��д��object������ķ��� �������Կͻ���������Ĵ�raw����
	 */
	
	/**
	 * ��ȡ��ҳ
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
