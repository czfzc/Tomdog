package tomdog;

public abstract class App {
	
	/**
	 * ��Ϊ����app�ĸ��� ��ִ�о������ ��Ҫ��Ϊ�ӿ�ȫ��ʵ��
	 */
	
	final public static int PORT=8080;
	
	public App(){
		new Init(PORT,this);
	}
	
	public void doGet(Response response,Request request){
		response.setPath(request.getPath());
		response.executeAll();
	}
	
	public void doPost(Response response,Request request){
		doGet(response,request);
	}
	
	/**
	 * response��request֮��ͨ��
	 * @param response
	 * @param request
	 */
	
}
