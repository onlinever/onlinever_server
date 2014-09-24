package com.onlinever.commons.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;

public class Test {

	/**
	 * @param args
	 */
	public static final String ADD_URL = "http://dingdan.api.tg.local/itemBrand/importItemBrandByShopId.htm";
	
	
	public  static String appadd() {

        try {
            //创建连接
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            //请求json
            JSONObject obj = new JSONObject();
            obj.put("appid", "210");
            obj.put("shopId", "1614");     
            obj.put("startTime", "2014-08-10 00:00:00");
            obj.put("endTime", "2014-08-21 01:00:00");
            
            out.writeBytes(obj.toString());
            
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(),"UTF-8");
                sb.append(lines);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
            System.out.println(sb.toString());
            return sb.toString();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return null;

    }
	
    @SuppressWarnings("unused")
	private static String soundEnc(String source) throws Exception {
        String enc = null;
        if (source.equals(new String(source.getBytes("GB2312"), "GB2312"))) {
            enc = "GB2312";
        } else if (source.equals(new String(source.getBytes("ISO8859_1"), "ISO8859_1"))) {
            enc = "ISO8859_1";
        } else if (source.equals(new String(source.getBytes("UTF-8"), "UTF-8"))) {
            enc = "UTF-8";
        }
        return enc;
    }
    
	public static void main(String[] args) {
		  try {
			  appadd();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
