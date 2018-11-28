package tomdog;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Init {
	
	private Response response=null;
	private Request request=null;
	
	/**
	 * ��ʼ��Tomdog��������� ��Ҫ����ָ���˿� ����ҳApp�������� �Ե�ȡ��App��get/post����
	 * @param port
	 * @param context
	 */
	
	public Init(int port,App context){
		try {
            ServerSocket ss=new ServerSocket(port);

            while(true){
                Socket socket=ss.accept();   //���ͷ��� �ȴ��ͻ�����������׽������ӣ��Զ��������� �Ǵ��׽��� �Ự��Ǵ���㣩
                
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
