package downloadsmashcustommusic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public class Main
{
	public static void main(String[] args)
	{
		new Main(args);
	}
	
	public Main(String[] args) //dah main code
	{
		String[] arguments = new String[6];
		//this is to tell to the user if some arguments are missing
		if(args.length == 0 || args.length == 1 || args.length == 2) {
			System.out.println("Some args are missing, please follow instructions available at https://github.com/RedStoneMatt/DownloadSmashCustomMusic/");
			return;
		}
		//this is to avoid to get an ArrayIndexOutOfBounds Exception
		if(args.length == 3) {
			arguments[0] = args[0];
			arguments[1] = args[1];
			arguments[2] = args[2];
			arguments[3] = "";
			arguments[4] = "";
			arguments[5] = "";
		}
		if(args.length == 4) {
			arguments[0] = args[0];
			arguments[1] = args[1];
			arguments[2] = args[2];
			arguments[3] = args[3];
			arguments[4] = "";
			arguments[5] = "";
		}
		if(args.length == 5) {
			arguments[0] = args[0];
			arguments[1] = args[1];
			arguments[2] = args[2];
			arguments[3] = args[3];
			arguments[4] = args[4];
			arguments[5] = "";
		}
		if(args.length == 6) {
			arguments[0] = args[0];
			arguments[1] = args[1];
			arguments[2] = args[2];
			arguments[3] = args[3];
			arguments[4] = args[4];
			arguments[5] = args[6];
		}
		//this is to define values and tell them to the user
		int startid = Integer.parseInt(arguments[0]);
		System.out.println("Got startid: " + startid);
		int endid = Integer.parseInt(arguments[1]);
		System.out.println("Got endid: " + endid);
		String JarPath = "";
		JarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			JarPath = URLDecoder.decode(JarPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Got path: " + JarPath);
		System.out.println("Got extension(s) to download: " + arguments[2] + " " + arguments[3] + " " + arguments[4] + " " + arguments[5]);
		//this is for downloading
		for(int i = startid; i < endid; i ++)
		{
			try
			{
				//here is, for your eyes's pleasure, some shit to get the timestamp of the last archive of the page
				String BRSTMTest = savePage("https://web.archive.org/web/https://www.smashcustommusic.com/brstm/"+Integer.toString(i));
				String[] onlyURL = getBeetween(BRSTMTest, "sharer.php?u=https://web.archive.org/web/", "/https://www.smashcustommusic.com/brstm/" + i + "'");
				String onlyDate = onlyURL[0].split("web.archive.org/web/")[1];
				
				//here we get the game's name, if there's none, then we skip because it means there's no music, otherwise, continue
				String content = savePage("https://web.archive.org/web/https://www.smashcustommusic.com/"+Integer.toString(i));
				String[] gameNameO = getBeetween(content, "<h1 style=\"text-align:center;\">", "</h1>");
				if(gameNameO.length == 0) {
					//nyeh, no music here
					System.out.println("No music found for ID "+Integer.toString(i) + ".");
				}
				else {
					//noice there's a music, now remove the music's name from the game's name
					String gameName = gameNameO[0].split(":")[0];
					if(gameNameO[0].split(":").length == 3) {
						gameName = gameNameO[0].split(":")[gameNameO[0].split(":").length - 3] + gameNameO[0].split(":")[gameNameO[0].split(":").length - 2];
					}
					//then setup the URLs and folders now
					String urlbrstm = "https://web.archive.org/web/" + onlyDate + "if_/https://www.smashcustommusic.com/brstm/" + i;
					String urlbcstm = "https://web.archive.org/web/" + onlyDate + "if_/https://www.smashcustommusic.com/bcstm/" + i;
					String urlbfstm = "https://web.archive.org/web/" + onlyDate + "if_/https://www.smashcustommusic.com/bfstm/" + i;
					String urlswbfstm = "https://web.archive.org/web/" + onlyDate + "if_/https://www.smashcustommusic.com/sw_bfstm/" + i;
					String folderbrstm = JarPath + "\\BRSTM\\" + gameName;
					String folderbcstm = JarPath + "\\BCSTM\\" + gameName;
					String folderbfstm = JarPath + "\\BFSTM (Wii U)\\" + gameName;
					String folderswbfstm = JarPath + "\\BFSTM (Switch)\\" + gameName;
					File folderbrstmmk = new File(folderbrstm);
					File folderbcstmmk = new File(folderbcstm);
					File folderbfstmmk = new File(folderbfstm);
					File folderswbfstmmk = new File(folderswbfstm);
					//make the folders if they don't exist
					folderbrstmmk.mkdirs();
					folderbcstmmk.mkdirs();
					folderbfstmmk.mkdirs();
					folderswbfstmmk.mkdirs();
					try {
						//now download the files, according to the extensions choosed by the user
						if(arguments[2].equals("brstm") || arguments[3].equals("brstm") || arguments[4].equals("brstm") || arguments[5].equals("brstm")) {
							downloadFile(urlbrstm, folderbrstm, i);
						}
						if(arguments[2].equals("bcstm") || arguments[3].equals("bcstm") || arguments[4].equals("bcstm") || arguments[5].equals("bcstm")) {
							downloadFile(urlbcstm, folderbcstm, i);
						}
						if(arguments[2].equals("bfstmwiiu") || arguments[3].equals("bfstmwiiu") || arguments[4].equals("bfstmwiiu") || arguments[5].equals("bfstmwiiu")) {
							downloadFile(urlbfstm, folderbfstm, i);
						}
						if(arguments[2].equals("bfstmswitch") || arguments[3].equals("bfstmswitch") || arguments[4].equals("bfstmswitch") || arguments[5].equals("bfstmswitch")) {
							downloadFile(urlswbfstm, folderswbfstm, i);
						}
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			catch(IOException e)
			{
				//Oof, an error ? Shit, well then let's say it means there's no music found !
				System.out.println("No music found for ID "+Integer.toString(i) + ".");
			}
		}
	}
	
	public String[] getBeetween(String str, String behind, String front) //get a string between two parts of a string
	{
		//this part of code wasn't wrote by me, all credits goes to the original author of this part
		ArrayList<String> words = new ArrayList<>();
		for(String str_ : str.split(behind))
		{
			try
			{
				if(str_.contains(front))
				{
					words.add(str_.split(front)[0]);
				}
			}
			catch(Exception e) {}
		}
		return words.toArray(new String[words.size()]);
	}

	public String savePage(final String URL) throws IOException //save a page into a string with a URL
	{
		//this part of code wasn't wrote by me, all credits goes to the original author of this part
		String line = "", all = "";
		URL myUrl = null;
		BufferedReader in = null;
		try {
			myUrl = new URL(URL);
			in = new BufferedReader(new InputStreamReader(myUrl.openStream()));

			while ((line = in.readLine()) != null) all += line;
		}
		finally
		{
			if (in != null) in.close();
		}
		
		return all;
	}
	private static final int BUFFER_SIZE = 4096;
	 
    public static void downloadFile(String fileURL, String saveDir, int i) //download a file with a proper name using the URL of the file and the directory we need to save it in
            throws IOException {
		//this part of code wasn't entirely wrote by me, credits goes to the original author of this part
    	
    	//connect to the URL
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
 
        if (responseCode == HttpURLConnection.HTTP_OK) { //if it connected successfully, continue
        	//get some infos
            String fileName = "";
            String lfd = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
 
            if (disposition != null) { //if we've got a name, then remove the useless parts in it
                int index = disposition.indexOf("filename=");
                fileName = disposition.split("filename=\"")[1].split("\"")[0];
            } else { //otherwise, name it with the music id
            	fileName = "" + i;
            }
            
            InputStream inputStream = httpConn.getInputStream();
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            String saveFilePath = saveDir + File.separator + fileName;
             
            File tempFile = new File(saveFilePath);
            boolean exists = tempFile.exists();
            if(exists == true && tempFile.length() != 0) { //if there's already a file, tell it to the user and return
            	System.out.println("Ignoring id " + i + ": File already exists.");
            	return;
            }
            else {
            	//much shit to avoid problems with saving on windows because this shitty OS don't support special characters in the filenames
            	fileName = fileName.replace("/", "_");
            	fileName = fileName.replace("\\", "_");
            	fileName = fileName.replace(":", "_");
            	fileName = fileName.replace("?", "_");
            	fileName = fileName.replace("*", "_");
            	fileName = fileName.replace("\"", "_");
            	fileName = fileName.replace("<", "_");
            	fileName = fileName.replace(">", "_");
            	fileName = fileName.replace("|", "_");
            	System.out.println("Downloading id " + i + ": " + fileName); //tell to the user what we're downloading
            	saveFilePath = saveDir + File.separator + fileName;
	            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
	            int bytesRead = -1;
	            byte[] buffer = new byte[BUFFER_SIZE];
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); //saving
	            }
	            
	            outputStream.close();
	            inputStream.close();
	 
	            System.out.println("File downloaded."); //Yes we did it !
            }
        } else { //if it failed to connect, then tell it to the user
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect(); //disconnect from the URL
    }
}