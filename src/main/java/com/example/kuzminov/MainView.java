package com.example.kuzminov;

import com.example.kuzminov.entity.Buyer;
import com.example.kuzminov.GreetService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@PageTitle("MyProject")
public class MainView extends VerticalLayout {

	Grid<Buyer> grid = new Grid<>(Buyer.class); 
    BuyerForm form; 
    GreetService service;
    
    public MainView(@Autowired GreetService service) {
        this.service = service;
        addClassName("main-view");
        setSizeFull();
        configureGrid();
        configureForm();

	    add(getToolbar(), getContent());
	    updateList();
	    closeEditor();
	 }

	private Component getContent() {
	    HorizontalLayout content = new HorizontalLayout(grid, form);
	    content.setFlexGrow(2, grid); 
	    content.setFlexGrow(1, form);
	    content.addClassNames("content");
	    content.setSizeFull();
	    return content;
	}

	private void configureForm() {
        form = new BuyerForm(); 
        form.setWidth("25em");
        form.addListener(BuyerForm.SaveEvent.class, this::saveBuyer); 
        form.addListener(BuyerForm.DeleteEvent.class, this::deleteBuyer); 
        form.addListener(BuyerForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("buyer-grid");
        grid.setSizeFull();
        grid.setColumns("name", "lastname", "number", "birthday"); 
        grid.getColumns().forEach(col -> col.setAutoWidth(true)); 
        
        grid.asSingleSelect().addValueChangeListener(event ->
        editBuyer(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        Button addBuyerButton = new Button("Add Buyer");
        addBuyerButton.addClickListener(click -> addBuyer()); 
     
        HorizontalLayout toolbar = new HorizontalLayout(addBuyerButton); 
        toolbar.addClassName("toolbar");
        return toolbar;
    }
    
    public void editBuyer(Buyer buyer) { 
        form.setBuyer(buyer);
        form.setVisible(true);
        addClassName("editing");        
    }

    private void closeEditor() {
        form.setBuyer(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addBuyer() { 
        grid.asSingleSelect().clear();
        editBuyer(new Buyer());
        updateList();
    }
    
    private void saveBuyer(BuyerForm.SaveEvent event) {
        service.saveBuyer(event.getBuyer());
        updateList();
        addBuyer();
    }

    private void deleteBuyer(BuyerForm.DeleteEvent event) {
        service.deleteBuyer(event.getBuyer());
        updateList();
        addBuyer();
    }
    
    private void updateList() {
        grid.setItems(service.findAllBuyers());
    }
}