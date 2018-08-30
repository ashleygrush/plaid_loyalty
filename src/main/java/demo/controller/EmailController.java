package demo.controller;

import java.io.IOException;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class EmailController {


        // Replace sender@example.com with your "From" address.
        // This address must be verified with Amazon SES.
        static final String FROM = "kamishimamadoka1@gmail.com";

        // Replace recipient@example.com with a "To" address. If your account
        // is still in the sandbox, this address must be verified.
        static final String TO = "grush.ashley@gmail.com";

        // The configuration set to use for this email. If you do not want to use a
        // configuration set, comment the following variable and the
        // .withConfigurationSetName(CONFIGSET); argument below.


//        static final String CONFIGSET = "ConfigSet";

        // The subject line for the email.
        static final String SUBJECT = "Loyalty Bonus";

        // The HTML body for the email.
        static final String HTMLBODY = "<h1> Congratulations! You earned your free cup of coffee! </h1>"

                // Add the link to the customer loyalty point
                + "<p>This email is a notification to tell you that you have "
                + "reached your loyalty points to earn a free cup of coffee on your next visit.</p>"
                +"<p>Please click below to check out your loyalty points and your next purchase of your free cup of coffee!" +
                "<p> <a href='https://aws.amazon.com/sdk-for-java/'> Checkout your loyalty points here! </a> <p>";


        // The email body for recipients with non-HTML email clients.
        static final String TEXTBODY = "You have reached your points to get a free coffee! ";

        public static void main(String[] args) throws IOException {

            try {
                AmazonSimpleEmailService client =
                        AmazonSimpleEmailServiceClientBuilder.standard()
                                // Replace US_WEST_2 with the AWS Region you're using for
                                // Amazon SES.
                                .withRegion(Regions.US_WEST_2).build();
                SendEmailRequest request = new SendEmailRequest()
                        .withDestination(
                                new Destination().withToAddresses(TO))
                        .withMessage(new Message()
                                .withBody(new Body()
                                        .withHtml(new Content()
                                                .withCharset("UTF-8").withData(HTMLBODY))
                                        .withText(new Content()
                                                .withCharset("UTF-8").withData(TEXTBODY)))
                                .withSubject(new Content()
                                        .withCharset("UTF-8").withData(SUBJECT)))
                        .withSource(FROM);
                        // Comment or remove the next line if you are not using a
                        // configuration set
//                        .withConfigurationSetName(CONFIGSET);
                client.sendEmail(request);
                System.out.println("Email sent!");
            } catch (Exception ex) {
                System.out.println("The email was not sent. Error message: "
                        + ex.getMessage());
            }
        }
    }
