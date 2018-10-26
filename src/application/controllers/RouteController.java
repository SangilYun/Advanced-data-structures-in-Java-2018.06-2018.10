package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import application.CLabel;
import application.CompButtonController;
import application.MapApp;
import application.MarkerManager;
import application.SelectManager;
import application.services.RouteService;
import geography.GeographicPoint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class RouteController implements Initializable {
	
	@FXML Label BFSLabel, DijistraLabel, aStarLabel;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	
	}

	// Strings for slider labels
	public static final int BFS = 3;
    public static final int A_STAR = 2;
    public static final int DIJ = 1;
	public static final int DISABLE = 0;
	public static final int START = 1;
	public static final int DESTINATION = 2;

    private int selectedToggle = DIJ;

    private RouteService routeService;
    private Button displayButton;
    private Button hideButton;
    private Button startButton;
    private Button resetButton;
    private Button destinationButton;
    private Button visualizationButton;
    //TODO------------------------------------------------------------------------
    private Button compButton;
    //----------------------------------------------------------------------------
    private ToggleGroup group;
    private CLabel<geography.GeographicPoint> startLabel;
    private CLabel<geography.GeographicPoint> endLabel;
    private CLabel<geography.GeographicPoint> pointLabel;
    private SelectManager selectManager;
    private MarkerManager markerManager;


//TODO ------------------------------------
	public RouteController(RouteService routeService, Button displayButton, Button hideButton,
						   Button resetButton, Button startButton, Button destinationButton,
						   ToggleGroup group, List<RadioButton> searchOptions, Button visualizationButton, Button compButton,
						   CLabel<geography.GeographicPoint> startLabel, CLabel<geography.GeographicPoint> endLabel,
						   CLabel<geography.GeographicPoint> pointLabel, SelectManager manager, MarkerManager markerManager) {
        // save parameters
        this.routeService = routeService;
		this.displayButton = displayButton;
        this.hideButton = hideButton;
		this.startButton = startButton;
		this.resetButton = resetButton;
		this.destinationButton = destinationButton;
        this.group = group;
        this.visualizationButton = visualizationButton;
        this.compButton=compButton;
//------------------------------------------------------------------------
        // maybe don't need references to labels;
		this.startLabel = startLabel;
		this.endLabel = endLabel;
        this.pointLabel = pointLabel;
        this.selectManager = manager;
        this.markerManager = markerManager;
//----------------------------------------------------------------------
        setupCompButton();
        setupDisplayButtons();
        setupRouteButtons();
        setupVisualizationButton();
        setupLabels();
        setupToggle();
        //routeService.displayRoute("data/sampleroute.map");
	}


	private void setupDisplayButtons() {
		displayButton.setOnAction(e -> {
            if(startLabel.getItem() != null && endLabel.getItem() != null) {
        			routeService.displayRoute(startLabel.getItem(), endLabel.getItem(), selectedToggle);
            }
            else {
            	MapApp.showErrorAlert("Route Display Error", "Make sure to choose points for both start and destination.");
            }
		});

        hideButton.setOnAction(e -> {
        	routeService.hideRoute();
        });

        //TODO -- implement
        resetButton.setOnAction( e -> {

            routeService.reset();
        });
	}
	
	//TODO---------------------------------------------------------------------------
	private void setupCompButton() {
		compButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getClassLoader().getResource("application/controllers/root.fxml"));
					Stage stage = new Stage();
					stage.setTitle("추가");
					stage.setScene(new Scene(root));
					stage.show();
					GeographicPoint start = selectManager.getStart();
					GeographicPoint end = selectManager.getDestination();
					System.out.println("start point : " + start);
					System.out.println("end point : " + end);
					
					List<GeographicPoint> BFSpath = markerManager.getDataSet().getGraph().bfs(start, end);
					System.out.println(BFSpath);
					for(GeographicPoint each : BFSpath )
						System.out.println("each : " +each);
					System.out.println("label : "+BFSLabel.getText());
//					BFSLabel.setText(""+BFSpath.size());
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("loading failed");
				}
				System.out.println("comparison : ");
			}
		});
	}
	//-------------------------------------------------------------------------------

    private void setupVisualizationButton() {
    	visualizationButton.setOnAction( e -> {
    		markerManager.startVisualization();
    	});
    	
    }

    private void setupRouteButtons() {
    	startButton.setOnAction(e -> {
            //System.out.println();
            selectManager.setStart();
    	});

        destinationButton.setOnAction( e-> {
            selectManager.setDestination();
        });
    }


    private void setupLabels() {


    }

    private void setupToggle() {
    	group.selectedToggleProperty().addListener( li -> {
            if(group.getSelectedToggle().getUserData().equals("Dijkstra")) {
            	selectedToggle = DIJ;
            }
            else if(group.getSelectedToggle().getUserData().equals("A*")) {
            	selectedToggle = A_STAR;
            }
            else if(group.getSelectedToggle().getUserData().equals("BFS")) {
            	selectedToggle = BFS;
            }
            else {
            	System.err.println("Invalid radio button selection");
            }
    	});
    }




}
