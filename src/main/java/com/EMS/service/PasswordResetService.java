package com.EMS.service;

import java.util.Locale;

import org.springframework.mail.SimpleMailMessage;

import com.EMS.model.PasswordResetModel;
import com.EMS.model.UserModel;


public interface PasswordResetService {
	
	void createPasswordResetTokenForUser(UserModel user, String token) throws Exception;
	
	void deletePasswordResetToken(PasswordResetModel passwordResetModel) throws Exception;
	
	String sendMail(String token, UserModel user) throws Exception;
	
	String validatePasswordResetToken(long id, String token) throws Exception;
	
	PasswordResetModel validateBeforeResetPassword(long id, String token) throws Exception;
}
