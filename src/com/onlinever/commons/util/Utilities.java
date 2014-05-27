package com.onlinever.commons.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 此类用来做工具类集合
 *
 */
public class Utilities
{
	public static final String INPUT_JSON_KEY = "INPUT_JSON";
	public static final String PAYMENT_KEY = "payment_";
	private static final String KEY_ALGORITHM = "RSA";
	private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
	@SuppressWarnings("unused")
	private static final String SALT = "148bddcb4ac3f557c130938f32e87280";
	private static final String AES_KEY="ef7f8ffceb162863ceba4f5a552e1788576819bf906df6dd1680315ab1cccb89";
	private static final ThreadPoolExecutor executer =new ThreadPoolExecutor(3, 5, 10
			, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(10000));
	
	private static final ThreadPoolExecutor settleExecuter =new ThreadPoolExecutor(3, 5, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(10000));
	
	private static final ThreadPoolExecutor refundExecuter =new ThreadPoolExecutor(3, 5, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(10000));
	
	public static ThreadPoolExecutor getExecutor() {
		return executer;
	}	
	
	 /**
	 * @return the settleexecuter
	 */
	public static ThreadPoolExecutor getSettleExecuter() {
		return settleExecuter;
	}

	/**
	 * @return the refundexecuter
	 */
	public static ThreadPoolExecutor getRefundExecuter() {
		return refundExecuter;
	}

	/**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials
     * string is returned
     *
     * @param password Password or other credentials to use in authenticating
     *        this username
     * @param algorithm Algorithm used to do the digest
     *
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password) {
        byte[] unencodedPassword = password.getBytes();
        
        MessageDigest md = null;
        
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return password;
        }
        
        md.reset();
        
        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);
        
        // now calculate the hash
        byte[] encodedPassword = md.digest();
        
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        
        return buf.toString();
    }
	
	
    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials
     * string is returned
     *
     * @param password Password or other credentials to use in authenticating
     *        this username
     * @param algorithm Algorithm used to do the digest
     *
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword;
		try {
			unencodedPassword = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return password;
		}
        
        MessageDigest md = null;
        
        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            return password;
        }
        
        md.reset();
        
        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);
        
        // now calculate the hash
        byte[] encodedPassword = md.digest();
        
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        
        return buf.toString();
    }
    
    /**
     * 产生数字和字母混合的指定位数的随机密码
     * @param count 位数
     * @return 随机密码
     */
    public static String getRandomPwd(int count)
    {
       	String randomPwd = RandomStringUtils.random(count, true, false);
       	return randomPwd;
    }
    
    /**
     * 排序字段的获取
     * @param orderBy 排序
     * @return 排序字段
     */
    public static String getOrderBy(Integer orderBy) {
		if(orderBy==null)
		{
			orderBy =0;
		}
		switch (orderBy) {
		case 0:
			return "volume:desc";
		case 1:
			return "price:desc";
		case 2:
			return "price:asc";
		case 3:
			return "seller_credit:desc";
		default:
			return "volume:desc";
		}
	} 
    
    /**
     * 排序字段的获取
     * @param orderBy 排序
     * @return 排序字段
     */
    public static String getOrderKeBy(Integer orderBy) {
		if(orderBy==null)
		{
			orderBy =0;
		}
		switch (orderBy) {
		case 0:
			return "commissionNum_desc";
		case 1:
			return "price_desc";
		case 2:
			return "price_asc";
		case 3:
			return "credit_desc";
		default:
			return "commissionNum_desc";
		}
	} 
    
    public static String encodeMD5(String content){
    	return encodePassword(content + getRandomPwd(40), "md5");
    }
    
    public static String getRandomNumber(int count){
    	String random = RandomStringUtils.random(count, false, true);
       	return random;   	
    }
    
    public static String sign(byte[] data, String privateKey) throws Exception {  
        // 解密由base64编码的私钥  
        byte[] keyBytes = decryptBASE64(privateKey);  
  
        return sign(data, keyBytes);
    }
    
    public static String sign(byte[] data, byte[] privateKey) throws Exception {  
        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);  
  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
  
        // 取私钥匙对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 用私钥对信息生成数字签名  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initSign(priKey);  
        signature.update(data);  
  
        return encryptBASE64(signature.sign());  
    }
    
	public static boolean verify(byte[] data, String publicKey, String sign)  
            throws Exception {  
  
        // 解密由base64编码的公钥  
        byte[] keyBytes = decryptBASE64(publicKey);  
  
        // 构造X509EncodedKeySpec对象  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
  
        // 取公钥匙对象  
        PublicKey pubKey = keyFactory.generatePublic(keySpec);  
  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initVerify(pubKey);  
        signature.update(data);  
  
        // 验证签名是否正常  
        return signature.verify(decryptBASE64(sign));  
    }
    
    public static byte[] decryptBASE64(String content) throws IOException{
		return (new BASE64Decoder()).decodeBuffer(content);
    }
    
    public static String encryptBASE64(byte[] content) {
		return (new BASE64Encoder()).encode(content);
	}
    
    public static String getParamName(String param){
    	if(StringUtils.isBlank(param)){
    		return "";
    	}
    	String paramName = "";
    	if(param.equalsIgnoreCase("code")){
    		paramName="验证码";
    	}else if(param.equalsIgnoreCase("email")){
    		paramName="电子邮件";
    	}else if(param.equalsIgnoreCase("mobile")){
    		paramName="手机号码";
    	}else if(param.equalsIgnoreCase("login_name")){
    		paramName="用户名";
    	}else if(param.equalsIgnoreCase("client_ip")){
    		paramName="客户端IP";
    	}else if(param.equalsIgnoreCase("password")){
    		paramName="密码";
    	}else if(param.equalsIgnoreCase("register_ip")){
    		paramName="注册IP";
    	}else if(param.equalsIgnoreCase("last_login_ip")){
    		paramName="最近登录IP";
    	}else if(param.equalsIgnoreCase("id")){
    		paramName="用户ID";
    	}else if(param.equalsIgnoreCase("newPassword")){
    		paramName="新密码";
    	}else if(param.equalsIgnoreCase("openid")){
    		paramName="第三方oauth用户ID";
    	}else if(param.equalsIgnoreCase("provider")){
    		paramName="oauth提供方";
    	}else{
    		paramName = param;
    	}
    	return paramName;
    }
    
    public static String encAESString(String source){
    	byte[] byteMi = null;   
        byte[] byteMing = null;   
        String strMi ="";   
        BASE64Encoder base64en = new BASE64Encoder();   
        try {
          byteMing = source.getBytes("UTF-8");   
          Cipher cipher = Cipher.getInstance("AES");   
          Key key = getAESKey();
          cipher.init(Cipher.ENCRYPT_MODE, key);
          byteMi = cipher.doFinal(byteMing);   
          strMi = base64en.encode(byteMi);   
        } catch (Exception e) {   
        	e.printStackTrace();
        } finally {   
          base64en = null;   
          byteMing = null;   
          byteMi = null;   
        }   
        return strMi;   
    }
    
    public static String decAESString(String source){
    	BASE64Decoder base64De = new BASE64Decoder();   
        byte[] byteMing = null;   
        byte[] byteMi = null;   
        String strMing = "";   
        try {   
          byteMi = base64De.decodeBuffer(source);   
          Cipher cipher = Cipher.getInstance("AES");
          Key key = getAESKey();
          cipher.init(Cipher.DECRYPT_MODE, key);   
          byteMing = cipher.doFinal(byteMi);   
          strMing = new String(byteMing, "UTF-8");   
        } catch (Exception e) {   
        	 e.printStackTrace();
        } finally {   
          base64De = null;   
          byteMing = null;   
          byteMi = null;   
        }
        return strMing;   
    }
    
    public static Key getAESKey() throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	KeyGenerator _generator = KeyGenerator.getInstance("AES");
    	SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
    	secureRandom.setSeed(AES_KEY.getBytes("UTF-8")); 
        _generator.init(128, secureRandom);
        Key key = _generator.generateKey();
        _generator = null;
        return key;
    }
    
    public static Integer parseInt(String num){
    	Integer n = null;
    	try{
    		n = (num == null) ? null : Integer.parseInt(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }
    
    public static Byte parseByte(String num){
    	Byte n = null;
    	try{
    		n = (num == null) ? null : Byte.parseByte(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }
    
   
    public static Short parseShort(String num){
    	Short n = null;
    	try{
    		n = (num == null) ? null : Short.parseShort(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }     
    
    public static Boolean parseBoolean(String num){
    	Boolean n = null;
    	try{
    		n = (num == null) ? null : Boolean.parseBoolean(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }  
    
	public static String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getHeader("Proxy-Client-IP");   
	    }   
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getHeader("WL-Proxy-Client-IP");   
	    }   
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getHeader("X-Real-IP");   
	    }  	    
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getRemoteAddr();   
	    }   
		return ip;   
	}
	
	public static String getLocalIp(){
		String ip = "";
		try {
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			ip   = addr.getHostAddress();
		} catch (UnknownHostException e) {
		}
		return ip;
	}
	
	public static StringBuilder appendParam(StringBuilder returnStr,String paramId,String paramValue){
		if(returnStr.length() > 0){
			if(paramValue != null && !paramValue.equals("")){
				returnStr.append("&").append(paramId).append("=").append(paramValue);
			}
		}else{
			if(paramValue != null && !paramValue.equals("")){
				returnStr.append(paramId).append("=").append(paramValue);
			}
		}
		return returnStr;
	}
	
	/**
	 * 数字位数不足补0
	 * @param num 数字
	 * @param strLength 位数
	 * @return
	 */
	public static String addZeroForNum2(int num, int strLength) {
		String str = String.valueOf(num);
		int strLen = str.length();		
		StringBuffer sb = new StringBuffer(strLength);
		for (;strLen < strLength; strLen++) {
			sb.append("0");//左补0
		}
		sb.append(str);
		return sb.toString();
	}
	
	public static String getFlowCode(int padding, int id, int length){
		StringBuilder sbCode = new StringBuilder(50);
		sbCode.append(DateUtil.getPaymentCodeDateStr(new Date()));
		sbCode.append(String.format("%02d", padding));
		String strId = new Integer(id).toString();
		sbCode.append(strId.length() > length ? strId.substring(strId.length() - length) : String.format("%0"+length+"d", id));
		return sbCode.toString();
	}
	
	public static String getRefundCode(int platformId, int id, int length){
		StringBuilder sbCode = new StringBuilder(50);
		sbCode.append(DateUtil.getRefundCodeDateStr(new Date()));
		sbCode.append(String.format("%02d", platformId));
		String strId = new Integer(id).toString();
		sbCode.append(strId.length() > length ? strId.substring(strId.length() - length) : String.format("%0"+length+"d", id));
		return sbCode.toString();
	}
	
}
