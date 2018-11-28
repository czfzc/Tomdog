package tomdog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Tomdog��response����
 * @author czfzc in xianfeng net
 *  it only can be displayed for teaching, but for sale. 
 */

public class Response {
	
	/**
	 * map�ļ�Ϊͷ���Ե����� ��ֵΪͷ���Ե�����
	 * list����Ϊһ��������ͷ����
	 */
	
	private LinkedHashMap<String,String> map=new LinkedHashMap<>();
	private LinkedList<String> list=new LinkedList<String>();
	private PrintWriter printwriter=null;
	private int status=200;
	private String string="";
	private String content="";
	private String path="";

	/**
	 * ��ʼ�����캯�� �ڹ��캯����������������ͷ���� һ��˵����ǰTomdog�汾 ��һ��ָ�����ݽ���Ϊhtml
	 * @param outputstream ���������� ָ��response�����ķ���
	 */
	public Response(OutputStream outputstream){
		printwriter=new PrintWriter(outputstream);
		map.put("Server", "Tomdog/1.0.0");
	}
	
	/**
	 *	������ҳresponse��Ӧֵ Ĭ��Ϊ200
	 * @param status
	 */
	
	private void setStatus(int status){
		this.status=status;
	}
	
	/**
	 *  ��Ϊ˽�з��� �����������Ӧ����������� ������ҳͷ ����ҳ���� ��app������κζ���֮ǰִ�� �Ա�֤����ҳͷ�ڴ�����ǰ��
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
	 * ��ȡwriter ���Զ���������ͻ��˵�����
	 * ��������ִ����executeAll()���� ����֤ͷ�������ǰ��
	 * @return
	 */
	
	public PrintWriter getWriter(){
		return printwriter;
	}
	
	/**
	 * ��д��object���toString()����
	 * ���response�Ĵ�raw���� ����ҳͷ ����ҳ����
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
	 * ����ҳ������ݣ�Ĭ��Ϊtext/html
	 * @param value
	 */
	
	public void setContentType(String value){
		map.put("Content-type", value);
	}
	
	/**
	 * ���ÿͻ��˵�cookieֵ
	 * @param name cookie������
	 * @param value cookie������
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
	 * ����ͷ���Զ������Ժ�ֵ
	 * @param name
	 * @param value
	 */
	
	public void setHeadAttribute(String name,String value){
		map.put(name, value);
	}
	
	/**
	 * �����Զ����е�ͷ��
	 * @param value
	 */
	
	public void setSthToHead(String value){
		list.add(value);
	}
	
	/**
	 * ������ҳ������
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
