package com.onlinever.commons.util;  
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;
/**
 *  
 * @author Demon
 * 
 * @copyright (c) onlinever.com 2014
 */
public class Word2Html {    
    
	public static void main(String[] args) {
		try {    
            convert2Html("C://Users//user//Desktop//订单—开发文档.doc","C://Users//user//Desktop//订单dev.html");    
        } catch (Exception e) {    
            e.printStackTrace();    
        }
	}
    
    public static void writeFile(String content, String path) {    
        FileOutputStream fos = null;    
        BufferedWriter bw = null;    
        try {    
            File file = new File(path);    
            fos = new FileOutputStream(file);    
            bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));    
            bw.write(content);    
        } catch (FileNotFoundException fnfe) {    
            fnfe.printStackTrace();    
        } catch (IOException ioe) {    
            ioe.printStackTrace();    
        } finally {    
            try {    
                if (bw != null)    
                    bw.close();    
                if (fos != null)    
                    fos.close();    
            } catch (IOException ie) {    
            }    
        }    
    }    
    
    public static void convert2Html(String fileName, String outPutFile)    
            throws TransformerException, IOException,    
            ParserConfigurationException {    
        HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));//WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));    
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(    
                DocumentBuilderFactory.newInstance().newDocumentBuilder()    
                        .newDocument());    
         wordToHtmlConverter.setPicturesManager( new PicturesManager()    
         {    
             public String savePicture( byte[] content,    
                     PictureType pictureType, String suggestedName,    
                     float widthInches, float heightInches )    
             {    
                 return suggestedName;    
             }    
         } );    
        wordToHtmlConverter.processDocument(wordDocument);    
        //save pictures    
        List<Picture> pics=wordDocument.getPicturesTable().getAllPictures();    
        if(pics!=null){    
            for(int i=0;i<pics.size();i++){    
                Picture pic = (Picture)pics.get(i);    
                System.out.println();    
                try {    
                    pic.writeImageContent(new FileOutputStream("C:/Users/user/Desktop/"    
	                            + pic.suggestFullFileName()));    
	                } catch (FileNotFoundException e) {    
	                    e.printStackTrace();    
	                }      
	            }    
	        }    
	        Document htmlDocument = wordToHtmlConverter.getDocument();    
	        ByteArrayOutputStream out = new ByteArrayOutputStream();    
	        DOMSource domSource = new DOMSource(htmlDocument);    
	        StreamResult streamResult = new StreamResult(out);    
	    
	        TransformerFactory tf = TransformerFactory.newInstance();    
	        Transformer serializer = tf.newTransformer();    
	        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");    
	        serializer.setOutputProperty(OutputKeys.INDENT, "yes");    
	        serializer.setOutputProperty(OutputKeys.METHOD, "html");    
	        serializer.transform(domSource, streamResult);    
	        out.close();    
	        writeFile(new String(out.toByteArray()), outPutFile);    
	    }    
	}    
