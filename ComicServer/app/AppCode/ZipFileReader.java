package AppCode;

import ViewModels.FilesViewModel;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;




public class ZipFileReader{

	private class ZipFileEntryComparator implements Comparator<ZipEntry> {
	  public int compare(ZipEntry z1, ZipEntry z2) {
	    return z1.getName().compareTo(z2.getName());
	  }

	  public boolean equals(ZipEntry z1, ZipEntry z2) {
	    return z1.getName().equals(z2.getName());
	  }
	}
	
    public FilesViewModel readDirectory(String dir){
        FilesViewModel viewModel = new FilesViewModel();
        viewModel.directory = dir;
        Path parent = Paths.get(dir).getParent();
        if(parent!=null) {
            viewModel.parent = parent.toString();
        }
        try{
            Files.list(Paths.get(dir)).forEach(filePath -> {
                if (Files.isDirectory(filePath)) {
                    viewModel.directories.add(filePath.getFileName().toString() + "/");
                } else if (Files.isRegularFile(filePath)) {
                    if(filePath.getFileName().toString().endsWith("cbz")) {
                        viewModel.files.add(filePath.getFileName().toString());
                    }
                }
            });

        }
        catch (IOException e){

        }
        Collections.sort(viewModel.directories, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(viewModel.files, String.CASE_INSENSITIVE_ORDER);
        return viewModel;
    }

	private ArrayList<String> readDirectoryForZips(String dir){
		ArrayList<String> files = new ArrayList<String>();
		try{
			Files.walk(Paths.get(dir)).forEach(filePath -> {
				if( Files.isRegularFile(filePath)){
					String filename = filePath.toString();
					if(filename.contains(".cbz") || filename.contains(".cbr")){
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

    public boolean bookExists(String path, String fileName){
        File f = new File(path+"/"+fileName);
        return f.exists();
    }

    public File getBook(String path, String fileName){

        File f = new File(path+"/"+fileName);
        if(f.exists()){
            return f;
        }

        return null;
    }

    public InputStream GetPage(String path, String fileName, int pageId){
        InputStream is = null;

        try {
            File f = new File(path+"/"+fileName);
            if(fileName.contains(".cbz")){
                ZipFile zip = new ZipFile(f);
                ZipEntry entry = null;
		List<? extends ZipEntry> entries = Collections.list(zip.entries());
		Collections.sort(entries, new ZipFileEntryComparator());

                for (Enumeration<? extends ZipEntry> e = Collections.enumeration(entries); 
			e.hasMoreElements() && pageId >= 0; pageId--){
                    entry =  e.nextElement();
                }
                is = zip.getInputStream(entry);
            }

        } catch (ZipException e){

        } catch (IOException e){

        }

        return is;
    }

	
    public int NumPages(String path, String fileName){
        int filesRead = 0;
        String fullName = path +"/"+fileName;
        try {
            File f = new File(fullName);
            ZipFile zip = new ZipFile(f);
            for (Enumeration<? extends ZipEntry> e = zip.entries(); e.hasMoreElements(); ){
                ZipEntry entry = e.nextElement();
                if(!entry.isDirectory()){
                    String entryName = lastSplit(entry.getName());
                    filesRead++;
                }
            }
        } catch (ZipException e){

        } catch (IOException e){

        }
        return filesRead;
    }

}
