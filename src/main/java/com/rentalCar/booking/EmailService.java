package com.rentalCar.booking;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine; // Inject Thymeleaf TemplateEngine

    public void sendBookingConfirmationEmail(Booking booking, String recipientEmail) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Booking Confirmation - Your Car Awaits!");
            helper.setFrom("k.laghribi2939@uca.ac.ma"); // Replace with your sender email
            helper.setTo(new InternetAddress(recipientEmail));

            // Prepare the Thymeleaf context with the variables
            Context context = new Context();
            context.setVariable("carModel", booking.getCar().getModel() + " or something equally awesome!");
            context.setVariable("startDate", booking.getStartDate().format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy")));
            context.setVariable("endDate", booking.getEndDate().format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy")));
            context.setVariable("startLocation", "42 Wallaby Way, Los Angeles, CA, United States");
            context.setVariable("endLocation", "42 Wallaby Way, Los Angeles, CA, United States");
            context.setVariable("price", String.format("$%.2f", booking.getCar().getPrice()));
            context.setVariable("transmission", booking.getCar().getAutomaticTransmission());
            context.setVariable("total", String.format("$%.2f", booking.getTotalPrice()));

            // Prepare the list of extras with their quantities
            context.setVariable("items", booking.getExtrasQuantity().entrySet().stream()
                    .map(entry -> Map.of(
                            "description", entry.getKey().getName(),
                            "quantity", entry.getValue(),
                            "price", String.format("$%.2f", entry.getKey().getPrice() * entry.getValue())))
                    .collect(Collectors.toList()));

            context.setVariable("companyName", "LokoKech");

            String content = templateEngine.process("emailConfirmation", context); // template name without '.html'
            helper.setText(content, true);


            emailSender.send(message);
        } catch (Exception e) {
            // Handle exceptions during email sending (log or notify admin)
            System.err.println("Error sending booking confirmation email: " + e.getMessage());
        }
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
