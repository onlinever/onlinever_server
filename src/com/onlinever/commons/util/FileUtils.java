package com.onlinever.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class FileUtils {
	
	public static boolean mkdir(String pathname) {
		File path = new File(pathname);
		if (!path.exists()) {
			return path.mkdirs();
		}
		return true;
	}
	
	public static void rm(String pathname) {
		File file = new File(pathname);
		if(file.exists()) {
			if(file.isFile())
				file.delete();
		}
	}
	
	public static boolean write(String pathname,String content) {
		File file = new File(pathname);
		boolean b = true;
		try {
			if(!file.exists()) {
				mkdir(file.getParentFile().getPath());
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content.getBytes("UTF-8"));
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	
	public static boolean writeAfter(String pathname,String content){
		try {
			FileWriter fw=new FileWriter(pathname, true);
			fw.write(content);
			fw.close();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}		
	}	
	
	public static boolean write(File file,String content) {
		boolean b = true;
		try {
			if(!file.exists()) {
				mkdir(file.getParentFile().getPath());
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content.getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}	
	
	public static String read(String pathname) {
		File file = new File(pathname);
		String s = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			s = new String(bytes);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static String toURIPath(String pathname,boolean isDirectory) {
		String p = pathname;
		if (File.separatorChar != '/')
		    p = p.replace(File.separatorChar, '/');
		if (!p.startsWith("/") && p.length() > 0)
		    p = "/" + p;
		if (!p.endsWith("/") && isDirectory)
			p = p + "/";
		if (p.startsWith("//"))
			p = "//" + p;
		URI uri = null;
		try {
			uri = new URI(null, null, p, null);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri == null ? null : uri.toString();
	}
	
	public static String getContentType(String fileName) {
        String fileNameTmp = fileName.toLowerCase();
        String ret = "";
        if (fileNameTmp.endsWith("txt")) {
            ret = "text/plain";
        }
        if (fileNameTmp.endsWith("gif")) {
            ret = "image/gif";
        }
        if (fileNameTmp.endsWith("jpg")) {
            ret = "image/jpeg";
        }
        if (fileNameTmp.endsWith("jpeg")) {
            ret = "image/jpeg";
        }
        if (fileNameTmp.endsWith("jpe")) {
            ret = "image/jpeg";
        }
        if (fileNameTmp.endsWith("zip")) {
            ret = "application/zip";
        }
        if (fileNameTmp.endsWith("rar")) {
            ret = "application/rar";
        }
        if (fileNameTmp.endsWith("doc")) {
            ret = "application/msword";
        }
        if (fileNameTmp.endsWith("ppt")) {
            ret = "application/vnd.ms-powerpoint";
        }
        if (fileNameTmp.endsWith("xls")) {
            ret = "application/vnd.ms-excel";
        }
        if (fileNameTmp.endsWith("html")) {
            ret = "text/html";
        }
        if (fileNameTmp.endsWith("htm")) {
            ret = "text/html";
        }
        if (fileNameTmp.endsWith("tif")) {
            ret = "image/tiff";
        }
        if (fileNameTmp.endsWith("tiff")) {
            ret = "image/tiff";
        }
        if (fileNameTmp.endsWith("pdf")) {
            ret = "application/pdf";
        }
        if (fileNameTmp.endsWith("chm")){
        	ret = "application/octet-stream";
        }
        return ret;
    }
	
	public static boolean isValidFile(String fileName) {
/*        String[] validFiles = { "txt", "gif", "jpg", "jpeg", "jpe", "zip",
                "rar", "doc", "ppt", "xls", "html", "htm", "tif", "tiff", "pdf", "chm" };*/
		String[] validFiles = {"gif", "jpg", "jpeg", "jpe"};
		boolean ret = false;
        for (int i = 0; i < validFiles.length; i++) {
            if (fileName.toLowerCase().endsWith(validFiles[i])) {
                ret = true;
                break;
            }
        }
        return ret;
    }
	
	public static void delFolder(String folderPath) {
	    try {
	    	delAllFile(folderPath);
	    	File myFilePath = new File(folderPath);
	    	myFilePath.delete();
	    }catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static void delAllFile(String path) {
	    File file = new File(path);
	    if (!file.exists()) {
	    	return;
	    }
	    if (!file.isDirectory()) {
	    	return;
	    }
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
		    	temp = new File(path + tempList[i]);
		    }else{
		        temp = new File(path + File.separator + tempList[i]);
		    }
		    if (temp.isFile()) {
		    	temp.delete();
		    }
		    if (temp.isDirectory()) {
		    	delAllFile(path+"/"+ tempList[i]);
		    	delFolder(path+"/"+ tempList[i]);
		    }
		}
	}	
}
