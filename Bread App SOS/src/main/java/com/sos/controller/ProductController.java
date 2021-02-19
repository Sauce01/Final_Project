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
public class ProductController implements Initializable{

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
    private TableColumn<Products, Boolean> colEdit;
	
	@FXML
    private MenuItem deleteUsers;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
	private ProductService productService;
        @Autowired
        private OrdersService orderService;
	
	private ObservableList<Products> productList = FXCollections.observableArrayList();
	
    @FXML
    private TextField proName;
    @FXML
    private TextField proDes;
    @FXML
    private TextField proStocks;
    @FXML
    private TextField proPrice;
    @FXML
    private TableView<Products> productTable;
    @FXML
    private TableColumn<Products, Long> colID;
    @FXML
    private TableColumn<Products, String> colName;
    @FXML
    private TableColumn<Products, String> colDes;
    @FXML
    private TableColumn<Products, String> colStocks;
    @FXML
    private TableColumn<Products, String> colPrice;
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
    private TextField proCustomer;
    private TextField proAddress;
    private TextField proPhone;
    private TextField proQuantity;
    private TextField proTotal;
    @FXML
    private Label title_product1;
    @FXML
    private Button saveUser;
    @FXML
    private Button addProduct;
    @FXML
    private Button makeOrder;
	
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
    
    @FXML
    private void saveUser(ActionEvent event){
    	
    	if(validate("name", getName(), "[a-zA-Z]+") &&
    	   validate("des", getDes(), "[a-zA-Z]+") &&
    	   emptyValidation("stocks", getStocks() == null) ){
    		
    		if(userId.getText() == null || userId.getText() == ""){
    			
    				
    				Products product = new Products();
        			product.setName(getName());
                                product.setDes(getDes());
                                product.setStocks(getStocks());
                                product.setPrice(getPrice());
        			
        			
        			Products newProduct = productService.save(product);
        			saveAlert(newProduct);
        		
    			
    		}else{
    			Products product = productService.find(Long.parseLong(userId.getText()));
    			product.setName(getName());
    			product.setDes(getDes());
    			product.setStocks(getStocks());
    			product.setPrice(getPrice());
    			Products updatedProduct =  productService.update(product);
    			updateAlert(updatedProduct);
    		}
    		
    		clearFields();
    		loadProductDetails();
    	}
    	
    	
    }
    
    @FXML
    private void deleteUsers(ActionEvent event){
    	List<Products> product = productTable.getSelectionModel().getSelectedItems();
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();
		
		if(action.get() == ButtonType.OK) productService.deleteInBatch(product);
    	
    	loadProductDetails();
    }
    
   	private void clearFields() {
               side_panel.setVisible(false);
               side_panel.setManaged(false);
               proName.clear();
               proDes.clear();
               proStocks.clear();
               proPrice.clear();
            
	}
	
	private void saveAlert(Products product){
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Product saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("Saved");
		alert.showAndWait();
	}
	
	private void updateAlert(Products user){
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Product updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("Updated");
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
		
		
		
		productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		setColumnProperties();
		
		
		loadProductDetails();
                side_panel.setVisible(false);
                side_panel.setManaged(false);
	}
	
	
	
	
	private void setColumnProperties(){
	
		
		colID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colDes.setCellValueFactory(new PropertyValueFactory<>("des"));
		colStocks.setCellValueFactory(new PropertyValueFactory<>("stocks"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colEdit.setCellFactory(cellFactory);
	}
	
	Callback<TableColumn<Products, Boolean>, TableCell<Products, Boolean>> cellFactory = 
			new Callback<TableColumn<Products, Boolean>, TableCell<Products, Boolean>>()
	{
		@Override
		public TableCell<Products, Boolean> call( final TableColumn<Products, Boolean> param)
		{
			final TableCell<Products, Boolean> cell = new TableCell<Products, Boolean>()
			{
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
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
							Products product = getTableView().getItems().get(getIndex());
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

				private void updateProduct(Products product) {
					userId.setText(Long.toString(product.getId()));
					proName.setText(product.getName());
                                        proDes.setText(product.getDes());
                                        proStocks.setText(product.getStocks());
                                        proPrice.setText(product.getPrice());
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
		productList.clear();
		productList.addAll(productService.findAll());
		productTable.setItems(productList);
	}
	
	/*
	 * Validations
	 */
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

    @FXML
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
    			Products product = productService.find(Long.parseLong(userId.getText()));
    			product.setName(getName());
    			product.setDes(getDes());
    			product.setStocks(getStocks());
    			product.setPrice(getPrice());
    			Products updatedProduct =  productService.update(product);
    			updateAlert(updatedProduct);
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
  
 
   

   

    private void quantityRleased(KeyEvent event) {
        try{
            
             if(Integer.parseInt(proQuantity.getText())<=Integer.parseInt(proStocks.getText())||Integer.parseInt(proQuantity.getText())==Integer.parseInt(proStocks.getText())){
                int sum = temp_stock-Integer.parseInt(proQuantity.getText());
               proStocks.setText(String.valueOf(sum));
               
               int sum1 = Integer.parseInt(proPrice.getText())*Integer.parseInt(proQuantity.getText());
               proTotal.setText(String.valueOf(sum1));
               
             }else if (proQuantity.getText().equals("")) {
                 
                proStocks.setText(String.valueOf(temp_stock));
                proQuantity.setText("");
                proTotal.setText("");
                Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Low Stock.");
		alert.setHeaderText(null);
		alert.setContentText("Low Stock, Input Below the current stocks");
		alert.showAndWait();
              
             }
             else {
                 proStocks.setText(String.valueOf(temp_stock));
                proQuantity.setText("");
                proTotal.setText("");
                Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Low Stock.");
		alert.setHeaderText(null);
		alert.setContentText("Low Stock, Input Below the current stocks");
		alert.showAndWait();
             }
        }catch(Exception ex){
            
        }
    }

    @FXML
    private void makeOrder(ActionEvent event) {
        stageManager.switchScene(FxmlView.MAKE_ORDER);
    }






    

    
}
