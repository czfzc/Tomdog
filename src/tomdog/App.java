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
		htmlLoader hl=new htmlLoader(response.getWriter());
		try{
			hl.print(request.getPath());
		}catch(Exception e){
			e.printStackTrace();
			response.sendRediret(404);
		}
	}
	
	public void doPost(Response response,Request request){
		doGet(response,request);
	}
}
