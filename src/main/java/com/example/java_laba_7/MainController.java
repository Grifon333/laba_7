package com.example.java_laba_7;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button addButton;
    @FXML
    private AnchorPane background;
    @FXML
    private CheckBox checkBox1;
    @FXML
    private CheckBox checkBox2;
    @FXML
    private ListView<String> citiesList;
    @FXML
    private TextField enterMoreData1;
    @FXML
    private TextField enterMoreData2;
    @FXML
    private Button exitButton;
    @FXML
    private Pagination filteredList;
    @FXML
    private Button findButton;
    @FXML
    private Pagination globalList;
    @FXML
    private Label moreDataTitle;
    @FXML
    private ListView<String> phonesList;
    @FXML
    private Button removeButton;
    @FXML
    private Button saveButton;
    @FXML
    private RadioButton search1;
    @FXML
    private RadioButton search2;
    @FXML
    private RadioButton search3;
    @FXML
    private Label searchTitle;
    @FXML
    private Button selectButton;
    @FXML
    private Button sortButton;
    @FXML
    private AnchorPane upSide;

    PhoneList list = new PhoneList();
    String fileName;

    @FXML
    void addAction(ActionEvent event) {
        switchScreen();
        updateDataAndButtons();
    }

    @FXML
    void checkBox1Action(ActionEvent event) {
        if(checkBox1.isSelected()) {
            citiesList.setVisible(true);
            showListCities();
        } else {
            citiesList.setVisible(false);
        }
    }

    @FXML
    void checkBox2Action(ActionEvent event) {
        if(checkBox2.isSelected()) {
            phonesList.setVisible(true);
            showListPhones();
        } else {
            phonesList.setVisible(false);
        }
    }

    @FXML
    void exitAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void findAction(ActionEvent event) {
        filteredList.setVisible(true);
        if(search1.isSelected()) {
            conditionOne();
        } else if(search2.isSelected()) {
            conditionTwo();
        } else if(search3.isSelected()) {
            conditionThree();
        }
    }

    @FXML
    void findToRemoveAction(ActionEvent event) {
        // visible
        selectButton.setVisible(false);
        removeButton.setVisible(true);
        setVisible(false);
        // color
        upSide.setBackground(ColorApp.upSideDrown);
        background.setBackground(ColorApp.backgroundDrown);
        // activated
        activatedButtons(true);
    }

    @FXML
    void removeAction(ActionEvent event) {
        //visible
        selectButton.setVisible(true);
        removeButton.setVisible(false);
        setVisible(true);
        //color
        upSide.setBackground(ColorApp.upSide);
        background.setBackground(ColorApp.background);
        //activated
        activatedButtons(false);

        int index = globalList.getCurrentPageIndex();
        Phone p = PhoneList.phones.remove(index);
        String key = p.getSender();

        ArrayList<Phone> arr = PhoneList.phonesByCities.get(key);
        arr.remove(p);
        if(arr.size() == 0) {
            PhoneList.phonesByCities.remove(key);
        } else {
            PhoneList.phonesByCities.put(key, arr);
        }

        updateDataAndButtons();
    }

    @FXML
    void saveAction(ActionEvent event) {
        showInputTextDialog();
        writingToFile(fileName + ".txt");
    }

    @FXML
    void search1Action(ActionEvent event) {
        if(search1.isSelected()) {
            // selected
            search2.setSelected(false);
            search3.setSelected(false);
            // visible
            moreDataTitle.setVisible(true);
            enterMoreData1.setVisible(true);
            enterMoreData2.setVisible(false);
            findButton.setVisible(true);
            // setText
            enterMoreData1.clear();
            enterMoreData1.setPromptText("Time");
        } else {
            // visible
            moreDataTitle.setVisible(false);
            enterMoreData1.setVisible(false);
            enterMoreData2.setVisible(false);
            findButton.setVisible(false);
        }
    }

    @FXML
    void search2Action(ActionEvent event) {
        if(search2.isSelected()) {
            // selected
            search1.setSelected(false);
            search3.setSelected(false);
            // visible
            moreDataTitle.setVisible(true);
            enterMoreData1.setVisible(true);
            enterMoreData2.setVisible(true);
            findButton.setVisible(true);
            // setText
            enterMoreData1.clear();
            enterMoreData2.clear();
            enterMoreData1.setPromptText("Min");
            enterMoreData2.setPromptText("Max");
        } else {
            // visible
            moreDataTitle.setVisible(false);
            enterMoreData1.setVisible(false);
            enterMoreData2.setVisible(false);
            findButton.setVisible(false);
        }
    }

    @FXML
    void search3Action(ActionEvent event) {
        if(search3.isSelected()) {
            // selected
            search1.setSelected(false);
            search2.setSelected(false);
            // visible
            moreDataTitle.setVisible(false);
            enterMoreData1.setVisible(false);
            enterMoreData2.setVisible(false);
            findButton.setVisible(true);
        } else {
            findButton.setVisible(false);
        }
    }

    @FXML
    void sortAction(ActionEvent event) {
        list.totalTalkTimeSort();
        showDetails(globalList, PhoneList.phones);
    }

    @FXML
    void initialize() {
//        list.add(new Phone(
//                0,
//                "Den",
//                "Kor",
//                "Vach",
//                300,
//                LocalTime.of(10, 20, 30),
//                LocalTime.of(0, 20, 10),
//                "Kyiv"
//        ));
    }


    void setVisible(boolean bool) {
        searchTitle.setVisible(bool);
        search1.setVisible(bool);
        search2.setVisible(bool);
        search3.setVisible(bool);
        checkBox1.setVisible(bool);
        checkBox2.setVisible(bool);
        if (bool) {
            return;
        }
        filteredList.setVisible(false);
        citiesList.setVisible(false);
        phonesList.setVisible(false);
        findButton.setVisible(false);
        moreDataTitle.setVisible(false);
        enterMoreData1.setVisible(false);
        enterMoreData2.setVisible(false);
    }

    void activatedButtons(boolean bool) {
        saveButton.setDisable(bool);
        addButton.setDisable(bool);
        sortButton.setDisable(bool);
        checkBox1.setSelected(bool);
        checkBox2.setSelected(bool);
    }

    void fillingData(Pagination pag, ArrayList<Phone> arr) {
        int a = arr.size();
        pag.setPageFactory((Integer pageIndex) -> {
            if(pageIndex < a)
                return createPage(arr, pageIndex);
            else
                return null;
        });
    }

    private VBox createPage(ArrayList<Phone> arr, int pageIndex) {
        Label text;
        VBox box = new VBox(1);
        int page = pageIndex * itemsPerPage();
        for (int i = page; i < page + itemsPerPage(); i++) {
            text = new Label("ID: " + arr.get(i).getId());
            box.getChildren().add(text);
            text = new Label("Name: " + arr.get(i).getName());
            box.getChildren().add(text);
            text = new Label("Surname: " + arr.get(i).getSurname());
            box.getChildren().add(text);
            text = new Label("Second name: " + arr.get(i).getSecond_name());
            box.getChildren().add(text);
            text = new Label("Account: " + arr.get(i).getAccount());
            box.getChildren().add(text);
            text = new Label("City: " + arr.get(i).getCity());
            box.getChildren().add(text);
            text = new Label("Intercity: " + arr.get(i).getIntercity());
            box.getChildren().add(text);
            text = new Label("Sender: " + arr.get(i).getSender());
            box.getChildren().add(text);
        }
        return box;
    }

    private int itemsPerPage() {
        return 1;
    }

    void showDetails(Pagination p, ArrayList<Phone> a) {
        int length = a.size() != 0 ? a.size() : 1;
        p.setPageCount(length);
        p.setMaxPageIndicatorCount(length);
        fillingData(p, a);
    }

    private boolean validTime(int t) {
        return t >= 0 && t < 60;
    }

    private void alertShow(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Attention!");
        alert.setHeaderText("InputError");
        alert.setContentText(msg);
        alert.show();
    }

    private void conditionOne() {
        String timeStr = enterMoreData1.getText().trim();
        LocalTime time;
        int hours, minutes, seconds;
        String[] buf = timeStr.split(":");

        if(buf.length != 3) {
            alertShow("Correct fill time");
        } else {
            try {
                hours = Integer.parseInt(buf[0]);
                minutes = Integer.parseInt(buf[1]);
                seconds = Integer.parseInt(buf[2]);
                if (validTime(minutes) && validTime(seconds)) {
                    time = LocalTime.of(hours, minutes, seconds);
                    ArrayList<Phone> arr = list.timeLimitAll(time);
                    showDetails(filteredList, arr);
                }
                else {
                    alertShow("Correct fill minutes and seconds");
                }
            } catch (NumberFormatException e) {
                alertShow("Correct fill time");
            }
        }
    }

    private void conditionTwo() {
        int min = Integer.parseInt(enterMoreData1.getText().trim());
        int max = Integer.parseInt(enterMoreData2.getText().trim());

        ArrayList<Phone> arr = list.rangeAccountAll(min, max);
        showDetails(filteredList, arr);
    }

    private void conditionThree() {
        ArrayList<Phone> arr = list.userIntercityTalkAll();
        showDetails(filteredList, arr);
    }

    private void writingToFile(String name) {
        String text;
        try (FileWriter writer = new FileWriter(name, false)) {
            for (int i = 0; i < list.size(); i++) {
                text = "ID: " + list.get(i).getId() + "\n" +
                        "Name: " + list.get(i).getName() + "\n" +
                        "Surname: " + list.get(i).getSurname() + "\n" +
                        "Second name: " + list.get(i).getSecond_name() + "\n" +
                        "Account: " + list.get(i).getAccount() + "\n" +
                        "City: " + list.get(i).getCity() + "\n" +
                        "Intercity: " + list.get(i).getIntercity() + "\n" +
                        "Sender: " + list.get(i).getSender() + "\n\n";
                writer.write(text);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showInputTextDialog() {

        TextInputDialog dialog = new TextInputDialog("D:\\Documents\\example");

        dialog.setTitle("Saving data");
        dialog.setHeaderText("Enter the location and name of the file:");
        dialog.setContentText("Navigation:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            this.fileName = name;
        });
    }

    private void showListCities() {
        citiesList.getItems().clear();

        String[] arr = PhoneList.phonesByCities.keySet().toArray(new String[0]);
        for (int i = 0; i < arr.length; i++) {
            citiesList.getItems().add(arr[i]);
        }
    }

    private void showListPhones() {
        phonesList.getItems().clear();

        for (Map.Entry<String, ArrayList<Phone>> entry : PhoneList.phonesByCities.entrySet()) {
            ArrayList<Phone> pList = entry.getValue();

            phonesList.getItems().add(entry.getKey() + ":\n");
            for(Phone p : pList) {
                phonesList.getItems().add(p.getName() + " " + p.getSurname() + " " + p.getSecond_name());
            }
            phonesList.getItems().add("\n");
        }
    }

    private void updateDataAndButtons() {
        showDetails(globalList, PhoneList.phones);

        if(PhoneList.phones.size() >= 1) {
            saveButton.setDisable(false);
            selectButton.setDisable(false);
            sortButton.setDisable(false);

            searchTitle.setVisible(true);
            search1.setVisible(true);
            search2.setVisible(true);
            search3.setVisible(true);
            checkBox1.setVisible(true);
            checkBox2.setVisible(true);
        }
        if(checkBox1.isSelected()) {
            showListCities();
        }
        if(checkBox2.isSelected()) {
            showListPhones();
        }
    }

    private void switchScreen() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("add-screen.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}