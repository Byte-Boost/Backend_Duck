package net.byteboost.duck.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.byteboost.duck.Main;
import net.byteboost.duck.utils.DBUtils;
import net.byteboost.duck.utils.EmailUtils;
import net.byteboost.duck.utils.GUIUtils;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ConfirmationPageController implements Initializable {
    @FXML
    private TextField tf_code;
    @FXML
    private Button btn_back;
    @FXML
    private Button btn_send;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Random random = new Random();
        int randomCode = random.nextInt(1000000,9000000);
        String emailContent =
        "<!DOCTYPE html> <html lang=\"pt-br\"> <head> <meta charset=\"UTF-8\"> <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>Email Confirmation</title> </head> <body style=\"margin: 0; padding:0; font-family: Arial, Helvetica, sans-serif;\" > <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"> <tr> <td> <table align=\"center\" border=\"0\" width=\"0px\" cellpadding=\"0\" cellspacing=\"0\"> <tr bgcolor=\"#18dca4\" align=\"center\"> <td style=\"padding: 10px;\"> <img src=\"https://github.com/Byte-Boost/Frontend_Duck/blob/5750f1a70815a246b94045c42eca58411efd246d/resources/images/ducklogoround.png?raw=true\" alt=\"Logo Duck\" width=\"80px\" margin=\"40px\"> </td> </tr> <tr> <td bgcolor=\"#fff\" align=\"center\"> <table border=\"0\" width=\"600px\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding: 15px; color: #002f71;\" > <tr align=\"center\"> <td> <h1>Email Confirmation</h1> <span>Please use the following access token, and prove it's really you!</span> </td> </tr> </table> <table border=\"0\" width=\"600px\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding: 0px;\"> <tr> <td align=\"center\"> <h1>"
        + randomCode +
        "</h1> </td> </tr> </table> </td> </tr> <tr> <td bgcolor=\"#dc5618\" style=\"padding: 20px;\"> <table border=\"0\" width=\"600px\" cellpadding=\"0\" cellspacing=\"0\"> <tr> <td width=\"78%\"> <span style=\"color: #fff;\">Click on the icons to the side to learn more about<br>our project. </span> </td> <td> <a href=\"https://byte-boost-team-website.vercel.app/\"> <img src=\"https://raw.githubusercontent.com/Byte-Boost/ByteBoost.team_website/main/app/favicon.ico\" alt=\"Logo Byte-Boost\" width=\"40px\"> </a> </td> <td> <a href=\"https://github.com/Byte-Boost/Duck\"> <img src=\"https://github.githubassets.com/favicons/favicon.svg\" alt=\"Logo Github\"> </a> </td> </tr> </table> </td> </tr> </table> </td> </tr> </table> </body> </html>";

        String email;
        String subject;
        if(Main.redirectedFrom == "login"){
            email = ForgetPasswordController.email;
            subject = "Your Duck Registration Code";
        } else {
            email = SignUpController.user.getUsername();
            subject = "Your Password Reset Code";
        }

        try {
            EmailUtils.sendMail(email, subject, emailContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        btn_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GUIUtils.changeScene(event, "/fxml/signup.fxml","Duck - Register");
            }
        });

        btn_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tf_code.getText().equals(String.valueOf(randomCode))) {
                    System.out.println("Sucesso! O Código está correto");
                    if (Main.redirectedFrom == "signup"){
                        DBUtils.addUser(SignUpController.user);
                    } else {
                        // Redirects to "changePassword" page
                        System.out.println("*Redirect needed*");
                    }
                    GUIUtils.changeScene(event, "/fxml/login.fxml","Duck - Login");
                } else {
                    System.out.println("Erro! Os códigos não coincidem.");
                }
            }
        });
    }
}
