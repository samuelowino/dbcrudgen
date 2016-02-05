package com.marvik.apis.dbcrudgen.application.views.windows;

import java.util.List;

import com.marvik.apis.dbcrudgen.application.tasks.TasksExecutor;
import com.marvik.apis.dbcrudgen.application.views.containers.projectconfiguration.database.mysql.MYSQLDatabaseConnectionConfigurationContainer;
import com.marvik.apis.dbcrudgen.application.views.widgets.ProjectsConfigurationWidget;
import com.marvik.apis.dbcrudgen.application.views.windows.properties.WindowProperties;
import com.marvik.apis.dbcrudgen.schemamodels.database.Database;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application  {

	private TasksExecutor tasksExecutor;

	public static String databaseName;
	/**
	 * This is the main GUI for the application where all the action take place
	 */
	public MainWindow() {
		tasksExecutor = new TasksExecutor();
	}

	/**
	 * Main method called when launching the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Initialize the main stage
		initStage(primaryStage);
	}

	private void initStage(Stage primaryStage) {

		BorderPane mainWindow = new BorderPane();
		addRightPane(mainWindow);
		addLeftPane(mainWindow);
		addBottomPane(mainWindow);
		addCenterPane(mainWindow);
		Scene mainScene = new Scene(mainWindow, WindowProperties.DEFAULT_SCREEN_WIDTH,
				WindowProperties.DEFAULT_SCREEN_HEIGHT);

		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	private void addRightPane(BorderPane mainWindow) {
		ProjectsConfigurationWidget projectsConfigurationContainer = new ProjectsConfigurationWidget();
		mainWindow.setRight(projectsConfigurationContainer);
	}

	private List<Database> mysqlDatabases;

	// Adds the left pane that originally contains all the MYSQL databases
	private void addLeftPane(BorderPane mainWindow) {

	}

	public void addBottomPane(BorderPane mainWindow) {

	}

	private MYSQLDatabaseConnectionConfigurationContainer mysqlDatabaseConnectionConfigurationContainer;
	
	public void addCenterPane(BorderPane mainWindow) {
		mysqlDatabaseConnectionConfigurationContainer = new MYSQLDatabaseConnectionConfigurationContainer();
		mysqlDatabaseConnectionConfigurationContainer.setTasksExecutor(tasksExecutor);
		mysqlDatabases = tasksExecutor.getDatabases();
		mysqlDatabaseConnectionConfigurationContainer.addDatabasesListView(mysqlDatabases);
		mainWindow.setCenter(mysqlDatabaseConnectionConfigurationContainer);

	}

	public static String getSelectedDatabase(){
		return databaseName;
	}
	public static void setSelectedDatabase(String  databaseName){
		MainWindow.databaseName = databaseName;
	}
}