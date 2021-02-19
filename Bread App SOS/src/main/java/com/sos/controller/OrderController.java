package com.sos.controller;

import com.sos.models.Orders;
import com.sos.models.Products;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.sos.models.User;
import com.sos.config.StageManager;
import com.sos.service.OrdersService;
import com.sos.service.ProductService;
import com.sos.service.UserService;
import com.sos.view.FxmlView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

@Controller
public class OrderController implements Initializable{

    public int temp_stock;
             
	@FXML
    private Button btnLogout;
	
    @FXML
    private Label userId;
	
    private TextField firstName;

    private TextField lastName;

    private DatePicker dob;
    
    private RadioButton rbMale;


    private RadioButton rbFemale;
    
    private ComboBox<String> cbRole;

    private TextField email;

    private PasswordField password;
    
    @FXML
    private Button reset;
	
	
	@FXML
    private MenuItem deleteUsers;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
        @Autowired
        private OrdersService orderService;
	
	private ObservableList<Orders> orderList = FXCollections.observableArrayList();
	
    @FXML
    private TextField proName;
    private TextField proDes;
    private TextField proStocks;
    private TextField proPrice;
    
    @FXML
    private TableView<Orders> orderTable;

    @FXML
    private TableColumn<Orders, Long> colID;

    @FXML
    private TableColumn<Orders, String> colName;
    @FXML
    private TableColumn<Orders, String> colCustomer;
    @FXML
    private TableColumn<Orders, String> colAddress;
    @FXML
    private TableColumn<Orders, String> colPhone;
    @FXML
    private TableColumn<Orders, String> colQUantity;
    @FXML
    private TableColumn<Orders, String> colTotal;
    @FXML
    private TableColumn<Orders, String> colStatus;
    
    @FXML
    private TableColumn<Orders, Boolean> colEdit;
    
    @FXML
    private VBox side_panel;
    @FXML
    private Label title_product;
    @FXML
    private Button button_products;
    @FXML
    private Button button_orders;
    @FXML
    private Button button_accounts;
    @FXML
    private Label des_product;
    @FXML
    private TextField proCustomer;
    @FXML
    private TextField proAddress;
    @FXML
    private TextField proPhone;
    @FXML
    private TextField proQuantity;
    @FXML
    private TextField proTotal;
    @FXML
    private Label title_product1;
    @FXML
    private Label des_product1;
    @FXML
    private Label des_product11;
    @FXML
    private Label des_product111;
    @FXML
    private Label des_product1111;
    @FXML
    private Label des_product1112;
    @FXML
    private Label des_product1113;
    @FXML
    private Label des_product1114;
    @FXML
    private Button completeOrder;

	
	private void exit(ActionEvent event) {
		Platform.exit();
    }

	
    @FXML
    private void logout(ActionEvent event) throws IOException {
    	stageManager.switchScene(FxmlView.LOGIN);    	
    }
    
    @FXML
    void reset(ActionEvent event) {
    	clearFields();
    }
    
    private void saveUser(ActionEvent event){
    	
    	if(validate("name", getName(), "[a-zA-Z]+") &&
    	   validate("des", getDes(), "[a-zA-Z]+") &&
    	   emptyValidation("stocks", getStocks() == null) ){
    		
    		if(userId.getText() == null || userId.getText() == ""){
    			
    				
    				Orders product = new Orders();
        			product.setName(getName());
                                product.setDes(getDes());
                                product.setStocks(getStocks());
                                product.setPrice(getPrice());
        			
        			
        			Orders newProduct = orderService.save(product);
        			saveAlert(newProduct);
        		
    			
    		}else{
    			Orders product = orderService.find(Long.parseLong(userId.getText()));
    			product.setName(getName());
    			product.setDes(getDes());
    			product.setStocks(getStocks());
    			product.setPrice(getPrice());
    			Orders updatedProduct =  orderService.update(product);
    			updateAlert(updatedProduct);
    		}
    		
    		clearFields();
    		loadProductDetails();
    	}
    	
    	
    }
    
    @FXML
    private void deleteUsers(ActionEvent event){
    	List<Orders> product = orderTable.getSelectionModel().getSelectedItems();
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();
		
		if(action.get() == ButtonType.OK) orderService.deleteInBatch(product);
    	
    	loadProductDetails();
    }
    
   	private void clearFields() {
               side_panel.setVisible(false);
               side_panel.setManaged(false);
               proName.clear();
               proDes.clear();
               proStocks.clear();
               proPrice.clear();
               proTotal.clear();
               proCustomer.clear();
               proAddress.clear();
               proPhone.clear();
               proQuantity.clear();
	}
	
	private void saveAlert(Orders product){
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Product saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("Saved");
		alert.showAndWait();
	}
	
	private void updateAlert(Orders user){
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Order Completed.");
		alert.setHeaderText(null);
		alert.setContentText("Order Completed");
		alert.showAndWait();
	}
	


	public String getName() {
		return proName.getText();
	}

	public String getDes() {
		return proDes.getText();
	}

	public String getStocks(){
		return proStocks.getText();
	}
	
	public String getPrice() {
		return proPrice.getText();
	}

	public String getEmail() {
		return email.getText();
	}

	public String getPassword() {
		return password.getText();
	}
        public String getCustomer() {
		return proCustomer.getText();
	}
        public String getAddress() {
		return proAddress.getText();
	}
        public String getPhone() {
		return proPhone.getText();
	}
        public String getQuanity() {
		return proQuantity.getText();
	}
        public String getTotal() {
		return proTotal.getText();
	}
        public String getStatus() {
		return "Preparing...";
	}
        
        
         
  

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		orderTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		setColumnProperties();
		loadProductDetails();
                side_panel.setVisible(false);
                side_panel.setManaged(false);
	}
	
	
	
	
	private void setColumnProperties(){
	
		
		colID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
		colAddress.setCellValueFactory(new PropertyValueFactory<>("location"));
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                colQUantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
               
                colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		colEdit.setCellFactory(cellFactory);
	}
	
	Callback<TableColumn<Orders, Boolean>, TableCell<Orders, Boolean>> cellFactory = 
			new Callback<TableColumn<Orders, Boolean>, TableCell<Orders, Boolean>>()
	{
		@Override
		public TableCell<Orders, Boolean> call( final TableColumn<Orders, Boolean> param)
		{
			final TableCell<Orders, Boolean> cell = new TableCell<Orders, Boolean>()
			{
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/view.png"));
				final Button btnEdit = new Button();
                               
				
				@Override
				public void updateItem(Boolean check, boolean empty)
				{
					super.updateItem(check, empty);
					if(empty)
					{
						setGraphic(null);
						setText(null);
					}
					else{
						btnEdit.setOnAction(e ->{
							Orders product = getTableView().getItems().get(getIndex());
							updateProduct(product);
						});
						
						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
				        iv.setImage(imgEdit);
				        iv.setPreserveRatio(true);
				        iv.setSmooth(true);
				        iv.setCache(true);
						btnEdit.setGraphic(iv);
						
						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateProduct(Orders product) {
					userId.setText(Long.toString(product.getId()));
					proName.setText(product.getName());
                                        proCustomer.setText(product.getCustomer());
                                        proAddress.setText(product.getLocation());
                                        proPhone.setText(product.getPhone());
                                        proQuantity.setText(product.getQuantity());
                                        proTotal.setText(product.getTotal());
                                        side_panel.setVisible(true);
                                        side_panel.setManaged(true);
                                        temp_stock = Integer.parseInt(proStocks.getText());
                                        des_product.setText("You can add your product here.");
                                        title_product.setText("Edit Product");
                                        
					
				}
                                
                                
			};
			return cell;
		}
	};

	
	
	private void loadProductDetails(){
		orderList.clear();
		orderList.addAll(orderService.findAll());
		orderTable.setItems(orderList);
	}
	
	
	private boolean validate(String field, String value, String pattern){
		if(!value.isEmpty()){
		Pattern p = Pattern.compile(pattern);
	        Matcher m = p.matcher(value);
	        if(m.find() && m.group().equals(value)){
	            return true;
	        }else{
	        	validationAlert(field, false);            
	            return false;            
	        }
		}else{
			validationAlert(field, true);            
            return false;
		}        
    }
	
	private boolean emptyValidation(String field, boolean empty){
        if(!empty){
            return true;
        }else{
        	validationAlert(field, true);            
            return false;            
        }
    }	
	
	private void validationAlert(String field, boolean empty){
		Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if(field.equals("Role")) alert.setContentText("Please Select "+ field);
        else{
        	if(empty) alert.setContentText("Please Enter "+ field);
        	else alert.setContentText("Please Enter Valid "+ field);
        }
        alert.showAndWait();
	}

    private void showAdd(ActionEvent event) {
        
            clearFields();
            des_product.setText("You can add your product here.");
            title_product.setText("Add Product");
            side_panel.setVisible(true);
            side_panel.setManaged(true);
       
    }

    @FXML
    private void button_products(ActionEvent event) {
       stageManager.switchScene(FxmlView.PRODUCTS);    
    }

    @FXML
    private void button_orders(ActionEvent event) {
        stageManager.switchScene(FxmlView.ORDERS);    
    }

    @FXML
    private void button_accounts(ActionEvent event) {
        stageManager.switchScene(FxmlView.USER);
    }

    private void saveOrder(ActionEvent event) {
       
                userId.setText(null);
    		userId.setText("");
    	if(validate("name", getName(), "[a-zA-Z]+") &&
    	   validate("des", getDes(), "[a-zA-Z]+") &&
    	   emptyValidation("stocks", getStocks() == null) ){
    		
    		if(userId.getText() == null || userId.getText() == ""){
    			
    
                                
    				Orders product = new Orders();
                                
        			product.setName(getName());
                                product.setDes(getDes());
                                product.setStocks(getStocks());
                                product.setPrice(getPrice());
                                product.setCustomer(getCustomer());
                                product.setLocation(getAddress());
                                product.setPhone(getPhone());
                                product.setQuantity(getQuanity());
                                product.setStatus(getStatus());
                                product.setTotal(getTotal());
        			Orders newProduct = orderService.save(product);
        			orderAlert(newProduct);
                                
        		
    			
    		}else{
    			Orders product = orderService.find(Long.parseLong(userId.getText()));
    			product.setName(getName());
    			product.setDes(getDes());
    			product.setStocks(getStocks());
    			product.setPrice(getPrice());
    			Orders updatedProduct =  orderService.update(product);
    			//pdateAlert(updatedProduct);
    		}
    		
    		clearFields();
    		loadProductDetails();
    	}	
    			
    				
    	
    	
    }
    private void orderAlert(Orders product){
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Order successfully.");
		alert.setHeaderText(null);
		alert.setContentText("Order Saved");
		alert.showAndWait();
	}

    @FXML
    private void completeOrder(ActionEvent event) {
        Orders product = orderService.find(Long.parseLong(userId.getText()));
    			product.setStatus("Completed");
    			Orders updatedProduct =  orderService.update(product);
    			updateAlert(updatedProduct);
                        clearFields();
    		        orderList.clear();
		orderList.addAll(orderService.findAll());
		orderTable.setItems(orderList);
    }
  
 
   

   

  



    

    
}
