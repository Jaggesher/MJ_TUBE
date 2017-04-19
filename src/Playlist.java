import java.io.File;
import java.util.Vector;

public class Playlist {
	public Vector<Icon_Data> books= new Vector<Icon_Data>();
	public void MakePlaylist(File path){
		books.clear();
		File[] filelist= path.listFiles();
		int len=filelist.length;
		for(int i=0;i<len;i++)
			if(filelist[i].isFile()){
				String Ex=getFileExtension(filelist[i]);
				if(Ex.equals("mp4")){
					books.add(new Icon_Data(filelist[i].getName(), "sources/a.jpg"));
				}
				else if(Ex.equals("mp3")){
					books.add(new Icon_Data(filelist[i].getName(), "sources/b.jpg"));
				}
			}
	}
	
	public void AddSingle(File path){
		books.clear();
		String Ex=getFileExtension(path);
		if(Ex.equals("mp4")){
			books.add(new Icon_Data(path.getName(), "sources/a.jpg"));
		}
		else if(Ex.equals("mp3")){
			books.add(new Icon_Data(path.getName(), "sources/b.jpg"));
		}
	}
	 private String getFileExtension(File file) {
	        String fileName = file.getName();
	        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
	        return fileName.substring(fileName.lastIndexOf(".")+1);
	        else return "";
	    }
}
