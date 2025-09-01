package books.coffee;

 import books.coffee.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.User;
import model.UserDAO;

public class Interface_admin_usersController implements Initializable {

    @FXML
    private Button j19;
    @FXML
    private Button l1;
    @FXML
    private Button l2;
    @FXML
    private Button l3;
    @FXML
    private Button l4;
    @FXML
    private Button l5;
    @FXML
    private TableColumn<User, String> i4;
    @FXML
    private TableColumn<User, String> i5;
    @FXML
    private TableColumn<User, String> i6;
    @FXML
    private TableColumn<User, String> i7;
    @FXML
    private TableColumn<User, String> i8;
    @FXML
    private TableColumn<User, String> i9;
    @FXML
    private TableView<User> usersTable;

    private ObservableList<User> users;

    private UserDAO userDAO;   //userDAO: كائن للوصول إلى قاعدة البيانات وإجراء العمليات على جدول المستخدمين.

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // تهيئة UserDAO
        userDAO = new UserDAO();

        i4.setCellValueFactory(new PropertyValueFactory<User, String>("name"));  //ربط أعمدة الجدول بخصائص الكائن: PropertyValueFactory<>("name") تخبر العمود بأي خاصية (name) من كائن User يستخدم لملء الخلايا.          
        i5.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        i6.setCellValueFactory(new PropertyValueFactory<User, String>("membership"));
        i7.setCellValueFactory(new PropertyValueFactory<User, String>("amountSpent"));
        i8.setCellValueFactory(new PropertyValueFactory<User, String>("procedures"));
        setupActionsColumn();

        // جلب البيانات من قاعدة البيانات
        loadUsersFromDatabase();
    }

    private String generateProceduresInfo(User user) {
        String membership = user.getMembership();
        double amountSpent = user.getAmountSpent();
        StringBuilder procedures = new StringBuilder();

        switch (membership.toLowerCase()) {
            case "visitor":
                if (amountSpent >= 400) {
                    procedures.append("VIP Member - Eligible for gold card, Free dessert offer");
                } else if (amountSpent >= 250) {
                    procedures.append("Frequent customer - Suggest premium membership, 15% discount available");
                } else if (amountSpent >= 100) {
                    procedures.append("Regular visitor - Coffee stamp card (8/10), Referral program");
                } else {
                    procedures.append("New customer - Welcome package sent, First purchase discount");
                }
                break;

            case "cashier":
                if (amountSpent > 0) {
                    procedures.append("Staff purchase: ").append(amountSpent)
                            .append(" SAR - Employee discount applied, Monthly limit updated");
                } else {
                    procedures.append("Active staff account - Training completed, Shift scheduler access");
                }
                break;

            case "admin":
                procedures.append("System administrator - Full permissions, Audit log access, User management enabled");
                if (amountSpent > 0) {
                    procedures.append(", Personal purchases: ").append(amountSpent).append(" SAR");
                }
                break;

            default:
                procedures.append("Account review needed - Contact manager");
        }

        return procedures.toString();
    }

    @FXML
    private void handleCloseDashboard(ActionEvent event) {
        Stage stage = (Stage) j19.getScene().getWindow();
        stage.close();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main_interface.fxml"));
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.setTitle("Login");
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleControlPanelAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_admin.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
            stage.show();

            ((Stage) l1.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProductsAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_admin_product.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Product Management");
            stage.show();

            ((Stage) l2.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUsersAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_admin_users.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) l3.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAnalyticsAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Interface_admin_analtics.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) l4.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProductAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("new_product.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void setupActionsColumn() {
        i9.setCellFactory(new Callback<TableColumn<User, String>, TableCell<User, String>>() {
            @Override
            public TableCell<User, String> call(TableColumn<User, String> param) {
                return new TableCell<User, String>() {
                    final Button editButton = new Button("️Edit");
                    final Button deleteButton = new Button("Delete");
                    final javafx.scene.layout.HBox buttonsContainer = new javafx.scene.layout.HBox(5, editButton, deleteButton);//وضع الأزرار داخل HBox لتكون جنبًا إلى جنب بمسافة 5 pixels بينهم.

                    {
                        // تنسيق الأزرار
                        editButton.setStyle("-fx-background-color: #E3AE66; -fx-text-fill: black; -fx-font-size: 12px;");
                        deleteButton.setStyle("-fx-background-color: #E3AE66; -fx-text-fill: black; -fx-font-size: 12px;");
                        /* User user = getTableView().getItems().get(getIndex());: هذا هو أهم سطر في الكود كله.
                        getTableView(): يعيد المرجع للجدول (usersTable) الذي توجد بداخله هذه الخلية.
                        .getItems(): يعيد القائمة (ObservableList<User>) التي يحتويها الجدول.
                         .get(getIndex()): getIndex() هي الطلقة السحرية. هذه الدالة تعيد رقم الصف (index) الذي توجد فيه هذه الخلية بالذات الآن (0 للصف الأول، 1 للثاني، إلخ). باستخدام هذا الرقم، نستطيع استخراج كائن User المحدد من القائمة. */
                        // إضافة أحداث النقر
                        editButton.setOnAction(event -> {    //setOnAction: يحدد ما يحدث عند النقر على كل زر.
                            User user = getTableView().getItems().get(getIndex());
                            handleEditUser(user);  // الآن، بعد أن عرفنا أي مستخدم تم النقر على زر "تعديل" بجواره، نستدعي الدالة المسؤولة عن فتح نافذة التعديل لهذا المستخدم.
                        });

                        deleteButton.setOnAction(event -> {  //setOnAction: يحدد ما يحدث عند النقر على كل زر.
                            User user = getTableView().getItems().get(getIndex());
                            handleDeleteUser(user);
                        });

                        // جعل الأزرار تملأ المساحة المتاحة
                        buttonsContainer.setAlignment(Pos.CENTER);

                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(buttonsContainer);
                        }
                        // loadUsersFromDatabase();
                    }

                    private void handleEditUser(User user) {
                        try {
                            // تحديد نوع المستخدم وفتح واجهة التعديل المناسبة
                            String membership = user.getMembership();
                            String fxmlFile = "";
                            String title = "";

                            switch (membership.toLowerCase()) {
                                case "visitor":
                                    fxmlFile = "creat _account _visitor.fxml";
                                    title = "Edit visitor Account";
                                    break;
                                case "cashier":
                                    // يمكن التمييز بين أنواع الموظفين إذا كان لديك حقل إضافي
                                    fxmlFile = "creaat_account_cashair.fxml";
                                    title = "Edit Cashier Account";
                                    break;
                                case "admin":
                                    fxmlFile = "creat_account_admin.fxml";
                                    title = "Edit Admin Account";
                                    break;
                                default:
                                    fxmlFile = "creat_account_visitor.fxml";
                                    title = "Edit User Account";
                            }

                            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));//ينشئ محمل (FXMLLoader) للوصول إلى واجهة التعديل.
                            Parent root = loader.load();

                            Object controller = loader.getController(); // الحصول على المتحكم الخاص بواجهة التعديل
                            if (controller instanceof Creat_account_visitorController) { // تمرير بيانات المستخدم للمتحكم الجديد
                                ((Creat_account_visitorController) controller).loadUserData(user);
                            } else if (controller instanceof Creaat_account_cashairController) {
                                ((Creaat_account_cashairController) controller).loadUserData(user);
                            } else if (controller instanceof Creat_account_adminController) {
                                ((Creat_account_adminController) controller).loadUserData(user);
                            }

                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle(title);
                            stage.showAndWait(); // انتظار إغلاق نافذة التعديل

                            //    refreshUsersTable();
                            if (controller instanceof Creat_account_visitorController) {
                                User updatedUser = ((Creat_account_visitorController) controller).getUpdatedUser();
                                if (updatedUser != null && updateUserInDatabase(updatedUser)) {//updateUserInDatabase(updatedUser): يحاول تحديث البيانات في DB.
                                    showAlert("Success", "User updated successfully!");
                                    refreshUsersTable();
                                }
                            } else if (controller instanceof Creaat_account_cashairController) {
                                User updatedUser = ((Creaat_account_cashairController) controller).getUpdatedUser();
                                if (updatedUser != null && updateUserInDatabase(updatedUser)) {
                                    showAlert("Success", "User updated successfully!");
                                    refreshUsersTable();
                                }
                            } else if (controller instanceof Creat_account_adminController) {
                                User updatedUser = ((Creat_account_adminController) controller).getUpdatedUser();
                                if (updatedUser != null && updateUserInDatabase(updatedUser)) {
                                    showAlert("Success", "User updated successfully!");
                                    refreshUsersTable(); //refreshUsersTable(): يعيد تحميل الجدول ليعكس التغييرات.
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            showAlert("Error", "Failed to open edit window: " + e.getMessage());
                        }

                    }

                    private void handleDeleteUser(User user) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirm Delete");
                        alert.setHeaderText("Delete User");
                        alert.setContentText("Are you sure you want to delete user: " + user.getName() + "?");

                        alert.showAndWait().ifPresent(response -> {
                            if (response == javafx.scene.control.ButtonType.OK) {
                                boolean deleted = userDAO.deleteUser(user.getId());
                                if (deleted) {
                                    showAlert("Success", "User deleted successfully!");
                                    refreshUsersTable(); // تحديث الجدول
                                } else {
                                    showAlert("Error", "Failed to delete user from database!");
                                }
                            }
                        });
                    }

                    private void showAlert(String title, String message) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(title);
                        alert.setHeaderText(null);
                        alert.setContentText(message);
                        alert.showAndWait();
                    }

                    private void openCreateUserForm(String userType) {
                        try {
                            String fxmlFile = "";
                            String title = "";

                            switch (userType.toLowerCase()) {
                                case "visitor":
                                    fxmlFile = "creat _account _visitor.fxml";
                                    title = "Create visitor Account";
                                    break;
                                case "cashier":
                                    fxmlFile = "creaat_account_cashair.fxml";
                                    title = "Create Cashier Account";
                                    break;
                                case "admin":
                                    fxmlFile = "creat_account_admin.fxml";
                                    title = "Create Admin Account";
                                    break;
                                default:
                                    fxmlFile = "creat _account _visitor.fxml";
                                    title = "Create User Account";
                            }

                            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                            Parent root = loader.load();

                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle(title);
                            stage.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                            showAlert("Error", "Failed to open create user window: " + e.getMessage());
                        }
                    }
                };
            }
        });
    }

    private void loadUsersFromDatabase() {
        users = FXCollections.observableArrayList();  // تنشئ قائمة (ArrayList) خاصة من نوع

        // جلب المستخدمين من قاعدة البيانات
        List<User> userList = userDAO.getAllUsers();

        if (userList != null && !userList.isEmpty()) {
            users.addAll(userList);

            // توليد معلومات الإجراءات لكل مستخدم
            for (User user : users) {  //تكرار على كل مستخدم في القائمة.
                user.setProcedures(generateProceduresInfo(user)); //تخزن النص المُولد في حقل procedures inside the كائن User نفسه. هذا سيعرضه later في العمود الخاص به في الجدول.
            }
        } else {
            // استخدام بيانات افتراضية في حالة عدم وجود بيانات في قاعدة البيانات
            users.addAll(
                    new User(1, "Mohammed shurrab", "mohammed@gmail.com", "Visitor", 450.0),
                    new User(2, "Ahmed Safi", "ahmed@gmail.com", "Visitor", 320.0),
                    new User(3, "Jneen Osama", "jneen@gmail.com", "Admin", 210.0),
                    new User(4, "Nada Thabet", "nada@gmail.com", "Cashier", 0.0),
                    new User(5, "Samaa Ashour", "samaa@gmail.com", "Cashier", 0.0),
                    new User(6, "Aluaman Osama", "aluaman@gmail.com", "Visitor", 180.0)
            );

            for (User user : users) {
                user.setProcedures(generateProceduresInfo(user));
            }
        }

        usersTable.setItems(users);

    }

    private void refreshUsersTable() {
        loadUsersFromDatabase();
        usersTable.refresh(); // هذا السطر يفرض إعادة رسم الجدول

    }

    private boolean updateUserInDatabase(User user) { // الوسيط - يأخذ البيانات المعدلة من الواجهة ويسلمها لطبقة قاعدة البيانات.
        return userDAO.updateUser(user);
    }
}
