package tomdog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class htmlLoader {
	
	private PrintWriter printwriter;
	final public static String wwwFolder="E:/tomdogwww/";
	final public static String index="index.html";
	
	public htmlLoader(PrintWriter printwriter){
		this.printwriter=printwriter;
	}
	
	public void print(String path) throws IOException{
			FileReader fr=new FileReader(wwwFolder+(path.equals("")?index:path));
			BufferedReader bf=new BufferedReader(fr);
			String line=null;
			while((line=bf.readLine())!=null){
				printwriter.println(line);
			}
			printwriter.flush();
	}
}
