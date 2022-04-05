package com.example.java_laba_7;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField accountEnter;
    @FXML
    private Button add;
    @FXML
    private AnchorPane backgroundAdd;
    @FXML
    private TextField cityEnter;
    @FXML
    private Button exit;
    @FXML
    private TextField intercityEnter;
    @FXML
    private TextField nameEnter;
    @FXML
    private TextField secondNameEnter;
    @FXML
    private TextField senderEnter;
    @FXML
    private TextField surnameEnter;
    @FXML
    private Label title;

    private String name, surname, secondName, accountStr, cityStr, intercityStr, sender;
    private long id;
    private int account;
    private LocalTime city, intercity;
    private boolean error = false;

    @FXML
    void addAddAction(ActionEvent event) {
        Phone phone;

        readingFields();

        if(check(name, surname, secondName, accountStr, cityStr, intercityStr, sender)) {
            fillingData();
            if (!error) {
                phone = new Phone(
                        id,
                        name,
                        surname,
                        secondName,
                        account,
                        city,
                        intercity,
                        sender
                );
                PhoneList.phones.add(phone);

                ArrayList<Phone> arr;
                if(PhoneList.phonesByCities.containsKey(sender)) {
                    arr = PhoneList.phonesByCities.get(sender);
                } else {
                    arr = new ArrayList<>();
                }
                arr.add(phone);
                PhoneList.phonesByCities.put(sender, arr);

                clearFields();
            }
        } else
            alertShow("Fill in all input fields!");
    }

    @FXML
    void exitAddAction(ActionEvent event) {
        Stage stage = (Stage) add.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {

    }

    private void readingFields() {
        name = nameEnter.getText().trim();
        surname = surnameEnter.getText().trim();
        secondName = secondNameEnter.getText().trim();
        accountStr = accountEnter.getText().trim();
        cityStr = cityEnter.getText().trim();
        intercityStr = intercityEnter.getText().trim();
        sender = senderEnter.getText().trim();
    }

    private boolean check(String ... str) {
        for (int i = 0; i < str.length; i++) {
            if(str[i].equals(""))
                return false;
        }
        return true;
    }

    private void alertShow(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Attention!");
        alert.setHeaderText("InputError");
        alert.setContentText(msg);
        alert.show();
    }

    private void fillingData() {
        error = false;
        id = Math.abs(name.hashCode() + surname.hashCode() + secondName.hashCode() + sender.hashCode());
        try {
            account = Integer.parseInt(accountStr);
        } catch (NumberFormatException exp) {
            alertShow("Correct fill account.");
            error = true;
            return;
        }

        try {
            city = fillingTime(cityStr);
            intercity = fillingTime(intercityStr);
        } catch (TimeException e) {
            alertShow(TimeException.msg);
            error = true;
        } catch (TimeValidException e) {
            alertShow(TimeValidException.msg);
            error = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LocalTime fillingTime(String time) throws Exception {
        int hours, minutes, seconds;
        String[] buf = time.split(":");

        if(buf.length != 3) {
            throw new TimeException();
        } else {
            try {
                hours = Integer.parseInt(buf[0]);
                minutes = Integer.parseInt(buf[1]);
                seconds = Integer.parseInt(buf[2]);
                if (validTime(minutes) && validTime(seconds))
                    return LocalTime.of(hours, minutes, seconds);
                else {
                    throw new TimeValidException();
                }
            } catch (NumberFormatException e) {
                throw new TimeException();
            }
        }
    }

    private boolean validTime(int t) {
        return t >= 0 && t < 60;
    }

    private void clearFields() {
        nameEnter.clear();
        surnameEnter.clear();
        secondNameEnter.clear();
        accountEnter.clear();
        cityEnter.clear();
        intercityEnter.clear();
        senderEnter.clear();
    }
}



class TimeException extends Exception {
    static String msg = "Correct fill time.";
}

class TimeValidException extends Exception {
    static String msg = "Correct fill minutes and seconds.";
}