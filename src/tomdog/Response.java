package tomdog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Tomdog的response对象
 * @author czfzc in xianfeng net
 *  it only can be displayed for teaching, but for sale. 
 */

public class Response {
	
	/**
	 * map的键为头属性的名称 键值为头属性的内容
	 * list内容为一个单独的头属性
	 */
	
	private LinkedHashMap<String,String> map=new LinkedHashMap<>();
	private LinkedList<String> list=new LinkedList<String>();
	private PrintWriter printwriter=null;
	private int status=200;
	private String string="";
	private String content="";
	private String path="";

	/**
	 * 初始化构造函数 在构造函数内添加两个必须的头属性 一个说明当前Tomdog版本 另一个指定内容解释为html
	 * @param outputstream 传入的输出流 指定response流出的方向
	 */
	public Response(OutputStream outputstream){
		printwriter=new PrintWriter(outputstream);
		map.put("Server", "Tomdog/1.0.0");
	}
	
	/**
	 *	设置网页response响应值 默认为200
	 * @param status
	 */
	
	private void setStatus(int status){
		this.status=status;
	}
	
	/**
	 *  此为私有方法 用来输出所有应该输出的内容 包括网页头 和网页内容 在app内输出任何东西之前执行 以保证让网页头在代码最前面
	 */

	public void setPath(String path) {
		if(path.contains(".css"))
			map.put("Content-type", "text/css");
		else if(path.contains(".js"))
			map.put("Content-type", "application/javascript");
		else map.put("Content-type", "text/html;charset=UTF-8");
		try {
			content=htmlLoader.getContent(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			this.sendRediret(404);
		} catch (IOException e2) {
			this.sendRediret(500);
		}
		
	}
	
	protected void executeAll(){
		printwriter.println("HTTP/1.1 "+status);
		for(int i=0;i<list.size();i++){
			printwriter.println(list.get(i));
		}
		for(String key:map.keySet()){
			printwriter.println(key+":"+map.get(key));
		}
		printwriter.println();
		printwriter.println(content);
		printwriter.flush();
	}
	
	/**
	 * 获取writer 以自定义输出到客户端的内容
	 * 但是里面执行了executeAll()方法 来保证头输出到最前面
	 * @return
	 */
	
	public PrintWriter getWriter(){
		return printwriter;
	}
	
	/**
	 * 重写了object类的toString()方法
	 * 输出response的纯raw内容 即网页头 加网页代码
	 */
	
	@Override
	public String toString(){
		string+="HTTP/1.1 "+status+"\n";
		for(int i=0;i<list.size();i++){
			string+=list.get(i);
		}
		for(String key:map.keySet()){
			string+=key+":"+map.get(key)+"\n";
		}
		string+="\n";
		string+=content;
		return string;
	}
	
	/**
	 * 设置页面的内容，默认为text/html
	 * @param value
	 */
	
	public void setContentType(String value){
		map.put("Content-type", value);
	}
	
	/**
	 * 设置客户端的cookie值
	 * @param name cookie的名称
	 * @param value cookie的内容
	 */
	
	public void setCookies(String name,String value){
		if(map.get("Set-Cookie")==null){
			map.put("Set-Cookie", name+"="+value);
		}else{
			String cookie=map.get("Set-Cookie");
			cookie+=";"+name+"="+value;
			map.put("Set-Cookie", cookie);
		}
	}
	
	/**
	 * 设置头的自定义属性和值
	 * @param name
	 * @param value
	 */
	
	public void setHeadAttribute(String name,String value){
		map.put(name, value);
	}
	
	/**
	 * 设置自定义行到头中
	 * @param value
	 */
	
	public void setSthToHead(String value){
		list.add(value);
	}
	
	/**
	 * 设置网页的内容
	 * @param content
	 */
	
	public void setContent(String content){
		this.content=content;
	}
	
	
	public void sendRediret(int code){
		this.setStatus(code);
		executeAll();
	}
}
