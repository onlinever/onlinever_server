package com.onlinever.commons.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateCodeServlet extends HttpServlet{
	private static final long serialVersionUID = -6678224119132147684L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		handleRequest(req,resp);
	}
	
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
    	handleRequest(req,resp);
	}
    
    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException{
    	HttpSession session = req.getSession();
    	ValidateCodeUtil util =  new ValidateCodeUtil();
		BufferedImage image = util.getValidateCode(session);
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(image, "jpeg",sos);
		sos.close();
    }
    
}    
