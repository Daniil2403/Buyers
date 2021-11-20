package com.example.kuzminov;

import com.example.kuzminov.entity.Buyer;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class BuyerForm extends FormLayout { 
  TextField name = new TextField("name"); 
  TextField lastname = new TextField("lastname"); 
  TextField number = new TextField("number");
  DatePicker birthday = new DatePicker("birthday");


  Binder<Buyer> binder = new BeanValidationBinder<>(Buyer.class); 
  private Buyer buyer;

  Button save = new Button("Save");
  Button delete = new Button("Delete");

  public BuyerForm() {
    addClassName("buyer-form"); 
    binder.bindInstanceFields(this); 


    add(name, 
    	lastname,
    	number,
    	birthday,
        createButtonsLayout());
  }
  
  public void setBuyer(Buyer buyer) {
      this.buyer = buyer; 
      binder.readBean(buyer); 
  }
  
  private Component createButtonsLayout() {
	  save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	  delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

	  save.addClickShortcut(Key.ENTER);

	  save.addClickListener(event -> validateAndSave()); 
	  delete.addClickListener(event -> fireEvent(new DeleteEvent(this, buyer))); 

	  binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); 
	  return new HorizontalLayout(save, delete);
	}

	private void validateAndSave() {
	  try {
	    binder.writeBean(buyer); 
	    fireEvent(new SaveEvent(this, buyer)); 
	  } catch (ValidationException e) {
	    e.printStackTrace();
	  }
	}
  
  
  
	//Events
	public static abstract class BuyerFormEvent extends ComponentEvent<BuyerForm> {
	 private Buyer buyer;
	
	 protected BuyerFormEvent(BuyerForm source, Buyer buyer) { 
	   super(source, false);
	   this.buyer = buyer;
	 }
	
	 public Buyer getBuyer() {
	   return buyer;
	 }
	}
	
	public static class SaveEvent extends BuyerFormEvent {
	 SaveEvent(BuyerForm source, Buyer buyer) {
	   super(source, buyer);
	 }
	}
	
	public static class DeleteEvent extends BuyerFormEvent {
	 DeleteEvent(BuyerForm source, Buyer buyer) {
	   super(source, buyer);
	 }
	
	}
	
	public static class CloseEvent extends BuyerFormEvent {
	 CloseEvent(BuyerForm source) {
	   super(source, null);
	 }
	}
	
	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
	   ComponentEventListener<T> listener) { 
	 return getEventBus().addListener(eventType, listener);
	}
  
}