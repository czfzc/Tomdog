package tomdog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class htmlLoader {
	
	final public static String wwwFolder="/home/czf/tomdogwww/";
	final public static String index="index.html";

	
	public static String getContent(String path) throws IOException{
			
			String toret="";
				FileReader fr = new FileReader(wwwFolder+(path.equals("")?index:path));
				BufferedReader bf=new BufferedReader(fr);
				
				String line=null;
				while((line=bf.readLine())!=null){
					toret+=line+"\n";
				}

			return toret;

	}
}
