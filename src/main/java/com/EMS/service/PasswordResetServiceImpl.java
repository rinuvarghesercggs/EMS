package com.EMS.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.EMS.model.PasswordResetModel;
import com.EMS.model.UserModel;
import com.EMS.repository.PasswordResetRepository;

@Service
public class PasswordResetServiceImpl implements PasswordResetService{
	
	@Autowired
	private PasswordResetRepository passwordResetRepository;
	
	@Override
	public void createPasswordResetTokenForUser(UserModel user, String token) throws Exception{
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.MINUTE, 10);
	    List<PasswordResetModel> passResetModelList = passwordResetRepository.findByUserId(user.getUserId());
	    if(!passResetModelList.isEmpty()) {
	    	PasswordResetModel passResetModel = passResetModelList.get(0);
	    	passResetModel.setExpiryDate(cal.getTime());
			passResetModel.setToken(token);
			passwordResetRepository.save(passResetModel);
	    }
		else {
			PasswordResetModel myToken = new PasswordResetModel(token, user,cal.getTime());
			passwordResetRepository.save(myToken);
		}
	}

	@Override
	public String validatePasswordResetToken(long id, String token) throws Exception{
	    PasswordResetModel passToken = passwordResetRepository.findByToken(token).get(0);
	    if ((passToken == null) || (passToken.getUser().getUserId() != id)) {
	        return "InvalidToken";
	    }
	 
	    Calendar cal = Calendar.getInstance();
	    if ((passToken.getExpiryDate()
	        .getTime() - cal.getTime()
	        .getTime()) <= 0) {
	        return "Token Expired";
	    }
	 
	    UserModel user = passToken.getUser();
	    Authentication auth = new UsernamePasswordAuthenticationToken(
	      user, null, Arrays.asList(
	      new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
	    SecurityContextHolder.getContext().setAuthentication(auth);
	    return null;
	}
	
	@Override
	public String sendMail(String contextPath, Locale locale, String token, UserModel user)  throws Exception{ 
		contextPath = contextPath.replace("user/resetPassword", "");
		String url = contextPath+"pwdVerify?token=" + token + "&userId="+user.getUserId();
		String subject = "Reset Password";
		String mailBody = url;
		
		String to = user.getEmail();
        String from = "jinu.n@titechglobal.in";  
        String host = "mail.titechglobal.in"; 
        final String username = "jinu.n@titechglobal.in";
        final String password = "titech@2018";  
        
        System.out.println("TLSEmail Start"); 
        
        Properties properties = System.getProperties();  
          
        // Setup mail server 
        properties.setProperty("mail.smtp.host", host); 
        // SSL Port 
        properties.put("mail.smtp.port", "465");  
        // enable authentication 
        properties.put("mail.smtp.auth", "true");  
        // SSL Factory 
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
  
        // creating Session instance referenced to  
        // Authenticator object to pass in  
        // Session.getInstance argument 
        Session session = Session.getDefaultInstance(properties, 
        new javax.mail.Authenticator() { 
              
            // override the getPasswordAuthentication  
            // method 
            protected PasswordAuthentication  
                    getPasswordAuthentication() { 
                return new PasswordAuthentication(username, password); 
            } 
        }); 
    

	    // javax.mail.internet.MimeMessage class is mostly  
	    // used for abstraction. 
	    MimeMessage message = new MimeMessage(session);  
	      
	    // header field of the header. 
	    message.setFrom(new InternetAddress(from)); 
	    message.addRecipient(Message.RecipientType.TO,  
	                          new InternetAddress(to)); 
	    message.setSubject(subject); 
	    message.setText(mailBody); 
	  
	    // Send message 
	    Transport.send(message); 
	    System.out.println("Yo it has been sent.."); 
	    String msg = "Verification link has been successfully sent to your email ("+to+")";
		return msg;
	}
}
