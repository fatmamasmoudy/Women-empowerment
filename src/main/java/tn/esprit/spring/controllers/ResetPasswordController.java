package tn.esprit.spring.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.User;
import tn.esprit.spring.payload.LoginRequest;
import tn.esprit.spring.payload.ResetPassword;

import tn.esprit.spring.repository.UserRepository;

@RestController
public class ResetPasswordController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordencoder;

	@PostMapping("/forget")
	public String processForgotPasswordForm(@RequestBody LoginRequest loginrequest, HttpServletRequest request) {

		User user = userRepository.findByEmail(loginrequest.getEmail()).get();

		user.setResetToken(UUID.randomUUID().toString());
		userRepository.save(user);
		String appUrl = request.getScheme() + "://" + request.getServerName();
		SendMail.email("To reset your password, click the link below:\n" + appUrl + ":8083/reset?token=" + user.getResetToken(),user.getEmail(),user.getFirstName());
		
				return user.getResetToken();
	}

	@PostMapping("/reset")
	public String setNewPassword( @Valid @RequestBody ResetPassword resetpass) {

		Optional<User> user = userRepository.findByResetToken(resetpass.getToken());

		User resetUser = user.get();

		// Set new password
		resetUser.setPassword(passwordencoder.encode(resetpass.getPassword()));
		resetUser.setDesactivate(false);
		resetUser.setCounterLogin(0);
		resetUser.setResetToken(null);

		

		// Save user
		userRepository.save(resetUser);
		SendMail.email( "Your password changed succecefully, your new password is :"+resetpass.getPassword(),resetUser.getEmail(),resetUser.getFirstName());
		
		return "password updated, you received a mail";
	}
}
