package com.example.smtpTest.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.smtpTest.model.MailInfo;

@Controller
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private JavaMailSender javaMailSender;

	@RequestMapping("/contact")
	public String contact() {

		return "/mail/contactPage";
	}

	@RequestMapping("/regForm")
	public String mailRegForm(MailInfo info) throws UnsupportedEncodingException {

		/*
		 * SimpleMailMessage msg = new SimpleMailMessage(); // 사용자가 관리자메일로 전송 // 이름 / 제목
		 * / 내용
		 * 
		 * System.out.println(info);
		 * 
		 * msg.setFrom("보낼아이디"); msg.setTo("받는아이디");
		 * msg.setSubject(info.getName());
		 * msg.setText("["+info.getEmail()+"]"+info.getContent());
		 * System.out.println(msg); try { this.javaMailSender.send(msg); } catch
		 * (Exception e) { e.printStackTrace(); }
		 * 
		 */

		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			message.setSubject("테스트 메시지입니다!", "UTF-8");
			String htmlContent = "<div style=\"background: f4f4f4; padding: 30px;\">\r\n" + 
					"			<div style=\"border: 1px solid #ddd; \">\r\n" + 
					"				<table style=\"border-collapse:collapse; line-height:2; width:100%\">\r\n" + 
					"					<tbody>\r\n" + 
					"						<tr>\r\n" + 
					"							<th>이름</th><td>"+info.getName()+"</td>\r\n" + 
					"							<th>이메일</th><td>"+info.getEmail()+"</td>\r\n" + 
					"						</tr>\r\n" + 
					"						<tr>\r\n" + 
					"							<th colspan=\"6\" style=\"border-top: 1px; padding: 20px; color:#aaa;\">문의내용</th>\r\n" + 
					"						</tr>\r\n" + 
					"						<tr>\r\n" + 
					"							<td colspan=\"6\" style=\"border-top: 1px; padding: 0 40px 40px 40px; border-bottom:1px solid; color:black;\">"+info.getContent()+"</td>\r\n" + 
					"						</tr>\r\n" + 
					"						\r\n" + 
					"					</tbody>\r\n" + 
					"				</table>\r\n" + 
					"			</div>\r\n" + 
					"		</div>";

			message.setText(htmlContent, "UTF-8", "html");

			// 보내는사람
			message.setFrom(new InternetAddress("보낼아이디", "테스트 메일"));

			// 받는사람
			message.addRecipient(RecipientType.TO, new InternetAddress("받는아이디"));
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (MailException e) {
			e.printStackTrace();
		}

		return "redirect:/mail/contact";
	}
}
