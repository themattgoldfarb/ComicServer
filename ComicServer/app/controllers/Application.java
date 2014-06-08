package controllers;

import play.*;
import play.mvc.*;
import views.html.*;

import java.util.Enumeration;
import java.util.zip.*;
import java.util.*;
import java.awt.Image;
import java.io.*;

import models.*;
import AppCode.*;
import com.google.gson.Gson;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result read() {
    	return ok(read.render());
    	
    }
    
    
    public static Result reader(int zipId ){
    	ZipFileReader f = new ZipFileReader();
    	UnzipModel um = f.ReadAndParseZip(zipId);
    	Gson gson = new Gson();
    	String json = gson.toJson(um);
    	return ok(reader.render(json));
    }
    
    public static Result library(){
    	ZipFileReader f = new ZipFileReader();
    	UnzipModel um = f.GetCovers();
    	return ok(library.render(um));
    }
    
   
    public static Result page(int zipId, int pageId){
    	/*try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	ZipFileReader f = new ZipFileReader();
    	InputStream is = f.GetPage(zipId, pageId);
    	return ok(is);
    }
}

