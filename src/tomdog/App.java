package tomdog;

public abstract class App {
	
	/**
	 * 此为所有app的父类 不执行具体操作 不要求为接口全部实现
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
	 * response与request之间通信
	 * @param response
	 * @param request
	 */
	
}
