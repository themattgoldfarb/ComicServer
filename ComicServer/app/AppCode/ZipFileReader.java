package AppCode;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import models.UnzipModel;
import models.ZipPage;

public class ZipFileReader{
	
	private ArrayList<String> zips = new ArrayList<String>();
	private String comicDir = "/home/matt/comics/";
	
	public ZipFileReader(){
		//zips.add("/home/matt/comics/Amazing Spider-Man v1 #222.cbz");
		zips = readDirectory(comicDir);
		
	}
	
	private ArrayList<String> readDirectory(String dir){
		ArrayList<String> files = new ArrayList<String>();
		try{
			Files.walk(Paths.get(dir)).forEach(filePath -> {
				if( Files.isRegularFile(filePath)){
					String filename = filePath.toString();
					if(filename.contains(".cbz")){
						files.add(filename);
					}
				}
			});
		} catch(IOException e){
			
		}
		Collections.sort(files);
		return files;
	}
	
	private String lastSplit(String s){
		String[] a = s.split("\\/");
		return a[a.length-1];
	}
	
	
	public InputStream GetPage(int zipId, int pageId){
		File file = null;
		Image image = null;
		InputStream is = null;
		
		if(zips.size()<=zipId){
			return null;	//throw new Exception("Invalid Zip Id");
		}
		
		try {
			File f = new File(zips.get(zipId));
			ZipFile zip = new ZipFile(f);
			ZipEntry entry = null;
			
			for (Enumeration<? extends ZipEntry> e = zip.entries(); e.hasMoreElements() && pageId >= 0; pageId--){
				entry = (ZipEntry) e.nextElement();
				String entryName = entry.getName();	
			}
			image = ImageIO.read(zip.getInputStream(entry));
			is = zip.getInputStream(entry);
		} catch (ZipException e){
			
		} catch (IOException e){
			
		}
		
		return is;	
	}
	
	public UnzipModel GetCovers(){
		ArrayList<ZipPage> pages = new ArrayList<ZipPage>();
		
		for(int zipId = 0; zipId < zips.size(); zipId++){
			String fileName = zips.get(zipId);
			int filesRead = 0;
			String displayFileName = lastSplit(fileName);
			try {
				File f = new File(fileName);
				ZipFile zip = new ZipFile(f);
				for (Enumeration<? extends ZipEntry> e = zip.entries(); e.hasMoreElements(); ){
					ZipEntry entry = (ZipEntry) e.nextElement();
					if(!entry.isDirectory()){
						String entryName = lastSplit(entry.getName());
						pages.add(new ZipPage(zipId, filesRead, entryName, displayFileName));
						break;
					}
					filesRead++;
				}
			} catch (ZipException e){
				
			} catch (IOException e){
				
			}
			
		}
		UnzipModel um = new UnzipModel();
		um.pages = pages;
		um.message = "";
		return um;
	}
	

	public UnzipModel ReadAndParseZip(int zipId){
		int filesRead = 0;
		String fileName = zips.get(zipId);
		String displayFileName = lastSplit(fileName);
		ArrayList<ZipPage> pages = new ArrayList<ZipPage>();
		if(zips.size()<=zipId){
			return null;	//throw new Exception("Invalid Zip Id");
		}
		try {
			File f = new File(fileName);
			ZipFile zip = new ZipFile(f);
			for (Enumeration<? extends ZipEntry> e = zip.entries(); e.hasMoreElements(); ){
				ZipEntry entry = (ZipEntry) e.nextElement();
				if(!entry.isDirectory()){
					String entryName = lastSplit(entry.getName());
					pages.add(new ZipPage(zipId, filesRead, entryName, displayFileName));
				}
				filesRead++;
			}
		} catch (ZipException e){
			
		} catch (IOException e){
			
		}
		UnzipModel um = new UnzipModel();
		um.pages = pages;
		um.message = filesRead + " files read";
		return um;
	}
}