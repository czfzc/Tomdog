package tomdog;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Init {
	
	private Response response=null;
	private Request request=null;
	
	/**
	 * 初始化Tomdog服务器软件 需要传入指定端口 和网页App的上下文 以调取该App的get/post方法
	 * @param port
	 * @param context
	 */
	
	public Init(int port,App context){
		try {
            ServerSocket ss=new ServerSocket(port);

            while(true){
                Socket socket=ss.accept();   //阻滞方法 等待客户端浏览器的套接字连接（自动三次握手 非纯套接字 会话层非传输层）
                
                Request request=new Request(socket.getInputStream());
                
                Response response=new Response(socket.getOutputStream());
                
                if(request.getMethod().equals("GET"))
                	context.doGet(response, request);
                else if(request.getMethod().equals("POST"))
                	context.doPost(response, request);
                
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
/*	public Response getResponse(){
		return response;
	}
	
	public Request getRequest(){
		return request;
	}
	
	*/
	
}
