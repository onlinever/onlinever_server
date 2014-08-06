/*
 * $Header: /u_back/cvsboss/cvsdata/repm/repm/impl/java/src/com/jia/fkcore/util/ValidateCodeUtil.java,v 1.2 2010/04/08 07:31:37 yuxq Exp $
 * $Author: yuxq $
 * $Revision: 1.2 $
 * $Date: 2010/04/08 07:31:37 $
 * �������� 2007-2-1 ����04:09:36 
 */

package com.onlinever.commons.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;



/***
 * ValidateCodeUtil
 * 
 * @author Demon
 *
 */
public class ValidateCodeUtil {

    private  String validateCode=""; 
    
    
    /**
     * 获取随机验证码
     * @param retRows 位数
     * @param send 范围
     * @return
     */
    private int[] getRands(int retRows, int send)
    {
        int[] a = new int[retRows]; 
        for(int i=0;i<a.length;i++)
        {
             a[i] = (int)(Math.random()*send+1); 
             for(int j=0;j<i;j++)
             {
                  if(a[j]==a[i])
                  {
                    i--;
                    break;
                  }
             }
        }
        return a;

    }  
    
    
    /**
     * 获取随机颜色
     * @param fc
     * @param bc
     * @return
     */
    private static Color getRandColor(int fc, int bc)
    {
        /*生成随机数*/
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    } 
    
    /**
     *  生成验证码图片
     * @return
     */
    public  BufferedImage creatImage()
    {


        /* 设置图片宽高 */
        int width = 60, height = 18;
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        /* 绘图对象 */
        Graphics g = image.getGraphics();

        /* 产生随机数 */
        Random random = new Random();

        /* 设置背景颜色 */
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);

        /* 设置字体 */
        g.setFont(new Font("Times New Roman", Font.BOLD, 14));
        
        /* 设置噪点 */
        g.setColor(Color.red);
        for (int i = 0; i < 50; i++)
        {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.drawLine(x, y, x, y);
        }
        g.setColor(Color.blue);
        for (int i = 0; i < 50; i++)
        {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.drawLine(x, y, x , y);
        }

        /* 验证码 */     
        validateCode = "";
        int[] a= getRands(4,9);
        for(int i= 0; i<a.length; i++)
        {
            String rand = ""+a[i];
            validateCode += rand;
            /* 随机颜色 */
            g.setColor(getRandColor(0,20));
            
            /*画 验证码*/
            g.drawString(rand, 13 * i + 3, 14);
        }
        /*释放资源 */
        g.dispose();        
        return image;
    } 
    
    
    
    /**
     * 获取验证码
     * @return
     */
    public String getValidateCode(){
        return this.validateCode;
    }
    
    
    /** 
     * 验证码存入sessions
     * @param session
     * @return
     */
    public BufferedImage getValidateCode(HttpSession session){
        
        BufferedImage retValue = null;
        
        if(session == null)
            return null;
        
        session.setMaxInactiveInterval(300);
        
        retValue = creatImage();
        
        session.setAttribute("validate_code", getValidateCode());
        
        return retValue;
    }
    
   
    
    
    /** 
     * 验证 验证码
     * @param session
     * @param inputValidateCode
     * @return
     */
    public boolean ValidateCode(HttpSession session, String inputValidateCode){
        
        if(session == null || inputValidateCode == null || inputValidateCode.length() <= 0)
            return false;
        
        String ValidateCode = String.valueOf(session.getAttribute("validate_code"));
        session.removeAttribute("validate_code");
        if(ValidateCode == null || ValidateCode.length() <= 0)
            return false;
        
        if(!ValidateCode.equals(inputValidateCode))
            return false;
        
        return true;
        
    }
    /**
     * 获取随机验证码
     * @return
     */
    public static char getRandomChar() {   
        Random random = new Random();   
        if (random.nextInt(5) < 3) {   
            return (char) (65 + random.nextInt(26));   
        }   
        return (char) (97 + random.nextInt(26));   
  
    } 
    
    public static void main(String[] args) throws Exception{
    	ValidateCodeUtil u = new ValidateCodeUtil();
    	File f =new File("C:\\Users\\user\\Desktop\\cnm.jpg");
    	FileOutputStream os = new FileOutputStream(f);
    	ImageIO.write(u.creatImage(), "jpg", os);
    }
    
}
