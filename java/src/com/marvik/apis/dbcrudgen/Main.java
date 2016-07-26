package com.marvik.apis.dbcrudgen;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.marvik.apis.dbcrudgen.application.tasks.TasksExecutor;
import com.marvik.apis.dbcrudgen.creator.android.AndroidCRUDCreator;
import com.marvik.apis.dbcrudgen.creator.j2se.J2SECrudCreator;
import com.marvik.apis.dbcrudgen.creator.php.PHPCrudCreator;
import com.marvik.apis.dbcrudgen.database.connection.DatabaseConnectionProperties;
import com.marvik.apis.dbcrudgen.database.connection.project.ProjectDatabaseConnectionProperties;
import com.marvik.apis.dbcrudgen.platforms.android.configuration.AndroidContentProviderConfiguration;
import com.marvik.apis.dbcrudgen.platforms.android.configuration.database.AndroidDatabaseConfiguration;
import com.marvik.apis.dbcrudgen.platforms.android.configuration.database.provider.ProviderConfiguration;
import com.marvik.apis.dbcrudgen.platforms.android.configuration.database.transactions.TransactionManagerConfiguration;
import com.marvik.apis.dbcrudgen.projects.android.configuration.AndroidProjectConfiguration;
import com.marvik.apis.dbcrudgen.projects.j2se.configuration.J2SEProjectConfiguration;
import com.marvik.apis.dbcrudgen.projects.j2se.configuration.J2SEProjectMYSQLDatabaseConfiguration;
import com.marvik.apis.dbcrudgen.projects.php.configuration.PHPProjectConfiguration;
import com.marvik.apis.dbcrudgen.schemamodels.database.Database;

public class Main {
    public static void main(String[] args) {
        createComphoaComphoProject();
    }

    private static void addGiveWattsSyncDataTable() {
        Database database = new TasksExecutor().createDatabaseModel("test");

        String projectStorageDir = "C:\\Users\\victor\\Desktop\\Givewatts\\android\\SyncData";
        String projectName = "Givewatts";
        String packageName = "com.givewatts.android";
        testAndroidCrudGenerator(database, projectStorageDir, projectName, packageName);
    }

    private static void createComphoaComphoProject() {
        Database database = new TasksExecutor().createDatabaseModel("comphoa2_compho");

        String projectStorageDir = "F:\\Android\\Clients\\MosesAsiago";
        String projectName = "Camphoa";
        String packageName = "nerdygeek.camphoa";
        testAndroidCrudGenerator(database, projectStorageDir, projectName, packageName);
        testPHPCrudGenerator(database);
    }

    private static void testKCATechExpoCrudCreator(Database database) throws IOException {

        String projectName = "KCATechExpo"; // project name
        String packageName = "com.kca.tech.expo"; // java package name for the
        // whole project
        String projectStorageDir = "G:\\4thYr"; // the location where the
        // project is stored on the HDD
        String javaSrcDirs = "src";
        String libsSrcDirs = "lib";

        J2SEProjectConfiguration j2seProjectConfiguration = new J2SEProjectConfiguration(projectName, packageName,
                projectStorageDir, javaSrcDirs, libsSrcDirs);

        String databaseHost = "localhost";
        String databaseUser = "root";
        String databaseUserPassword = "";
        String databaseName = database.getDatabaseName();
        DatabaseConnectionProperties databaseConnectionProperties = new DatabaseConnectionProperties(databaseHost,
                databaseUser, databaseUserPassword, databaseName);
        String mysqlAPIsClassesSrcDirs = "database\\mysqlutils";
        String tableSchemasSrcDir = "database\\tableschemas";
        String tableModelsSrcDirs = "database\\tablemodels";
        String tablesCrudSrcDirs = "database\\tablescrud";
        J2SEProjectMYSQLDatabaseConfiguration j2seProjectMYSQLDatabaseConfiguration = new J2SEProjectMYSQLDatabaseConfiguration(
                mysqlAPIsClassesSrcDirs, databaseConnectionProperties, tableSchemasSrcDir, tableModelsSrcDirs,
                tablesCrudSrcDirs);

        j2seProjectConfiguration.setJ2SEProjectMYSQLDatabaseConfiguration(j2seProjectMYSQLDatabaseConfiguration);

        J2SECrudCreator j2seCrudCreator = new J2SECrudCreator();
        j2seCrudCreator.createProject(j2seProjectConfiguration, database);
    }

    private static void testJ2SECrudCreator(Database database) throws IOException {

        String projectName = "WTNDJ2SE";
        String packageName = "com.victor.j2se.wtnd";
        String projectStorageDir = "G:\\4thYr";
        String javaSrcDirs = "src";
        String libsSrcDirs = "lib";

        J2SEProjectConfiguration j2seProjectConfiguration = new J2SEProjectConfiguration(projectName, packageName,
                projectStorageDir, javaSrcDirs, libsSrcDirs);

        String databaseHost = "localhost";
        String databaseUser = "root";
        String databaseUserPassword = "";
        String databaseName = database.getDatabaseName();
        DatabaseConnectionProperties databaseConnectionProperties = new DatabaseConnectionProperties(databaseHost,
                databaseUser, databaseUserPassword, databaseName);
        String mysqlAPIsClassesSrcDirs = "coreutils";
        String tableSchemasSrcDir = "database\\tableschemas";
        String tableModelsSrcDirs = "database\\tablemodels";
        String tablesCrudSrcDirs = "database\\tablescrud";
        J2SEProjectMYSQLDatabaseConfiguration j2seProjectMYSQLDatabaseConfiguration = new J2SEProjectMYSQLDatabaseConfiguration(
                mysqlAPIsClassesSrcDirs, databaseConnectionProperties, tableSchemasSrcDir, tableModelsSrcDirs,
                tablesCrudSrcDirs);

        j2seProjectConfiguration.setJ2SEProjectMYSQLDatabaseConfiguration(j2seProjectMYSQLDatabaseConfiguration);

        J2SECrudCreator j2seCrudCreator = new J2SECrudCreator();
        j2seCrudCreator.createProject(j2seProjectConfiguration, database);
    }

    private static void testAndroidCrudGenerator(Database database, String projectStorageDir, String projectName,
                                                 String packageName) {

        AndroidDatabaseConfiguration androidDatabaseConfiguration = new AndroidDatabaseConfiguration(
                database.getDatabaseName(), 1, "DatabaseManager", "database\\sqliteopenhelper",
                "database\\tableschemas", "database\\tablescrud", "database\\tablemodels");

        String contentProviderPackage = "database\\contentprovider";
        String contentProviderClass = "DataProvider";
        ProviderConfiguration providerConfiguration = new ProviderConfiguration(contentProviderClass,
                contentProviderPackage);

        String transactionManagerPackage = "database\\transactions";
        String transactionManagerClass = "TransactionsManager";
        TransactionManagerConfiguration transactionManagerConfiguration = new TransactionManagerConfiguration(
                transactionManagerPackage, transactionManagerClass);

        AndroidContentProviderConfiguration androidContentProviderConfiguration = new AndroidContentProviderConfiguration(
                providerConfiguration, transactionManagerConfiguration, androidDatabaseConfiguration);

        AndroidProjectConfiguration androidProjectConfiguration = new AndroidProjectConfiguration(
                projectStorageDir + "\\" + projectName, "app\\src\\main\\java", packageName,
                androidContentProviderConfiguration);

        AndroidCRUDCreator androidCRUDCreator = new AndroidCRUDCreator();
        androidCRUDCreator.setAndroidProjectConfiguration(androidProjectConfiguration);
        androidCRUDCreator.createProject(database);
    }

    private static void testPHPCrudGenerator(Database database) {

        PHPProjectConfiguration phpProjectConfiguration = new PHPProjectConfiguration(database.getDatabaseName());
        phpProjectConfiguration.setProjectStorageDirectory("C:\\xampp\\htdocs\\" + database.getDatabaseName() + "\\");
        phpProjectConfiguration.setProjectPHPTableCrudLowLevelScriptsStorageDirectory(
                "C:\\xampp\\htdocs\\" + database.getDatabaseName() + "\\scripts\\php\\database\\crud\\");
        phpProjectConfiguration.setProjectPHPTableCrudHighLevelScriptsStorageDirectory(
                "C:\\xampp\\htdocs\\" + database.getDatabaseName() + "\\scripts\\php\\database\\modules\\");
        phpProjectConfiguration.setProjectPHPDatabaseAPIScriptsStorageDirectory(
                "C:\\xampp\\htdocs\\" + database.getDatabaseName() + "\\scripts\\php\\database\\core-apis\\");
        phpProjectConfiguration.setProjectSQLScriptsStorageDirectory(
                "C:\\xampp\\htdocs\\" + database.getDatabaseName() + "\\scripts\\php\\database\\sql\\");

        ProjectDatabaseConnectionProperties projectDatabaseConnectionProperties = new ProjectDatabaseConnectionProperties(
                "localhost", "root", "", database.getDatabaseName());

        PHPCrudCreator phpCrudCreator = new PHPCrudCreator();
        phpCrudCreator.setProjectConfiguration(phpProjectConfiguration);
        phpCrudCreator.setProjectDatabaseConnectionProperties(projectDatabaseConnectionProperties);
        phpCrudCreator.createProject(database);

    }

    private static void giveWattsPHPCrudGenerator(Database database) {

        PHPProjectConfiguration phpProjectConfiguration = new PHPProjectConfiguration(database.getDatabaseName());
        phpProjectConfiguration.setProjectStorageDirectory("C:\\xampp\\htdocs\\" + database.getDatabaseName() + "-yii\\libs\\marvik\\libs\\");
        phpProjectConfiguration.setProjectPHPTableCrudLowLevelScriptsStorageDirectory(
                "C:\\xampp\\htdocs\\" + database.getDatabaseName() + "-yii\\libs\\marvik\\libs\\database\\crud\\");
        phpProjectConfiguration.setProjectPHPTableCrudHighLevelScriptsStorageDirectory(
                "C:\\xampp\\htdocs\\" + database.getDatabaseName() + "-yii\\libs\\marvik\\libs\\database\\modules\\");
        phpProjectConfiguration.setProjectPHPDatabaseAPIScriptsStorageDirectory(
                "C:\\xampp\\htdocs\\" + database.getDatabaseName() + "-yii\\libs\\marvik\\libs\\database\\core\\mysql\\");
        phpProjectConfiguration.setProjectSQLScriptsStorageDirectory(
                "C:\\xampp\\htdocs\\" + database.getDatabaseName() + "-yii\\libs\\marvik\\libs\\database\\sql\\");

        ProjectDatabaseConnectionProperties projectDatabaseConnectionProperties = new ProjectDatabaseConnectionProperties(
                "localhost", "root", "", database.getDatabaseName());

        PHPCrudCreator phpCrudCreator = new PHPCrudCreator();
        phpCrudCreator.setProjectConfiguration(phpProjectConfiguration);
        phpCrudCreator.setProjectDatabaseConnectionProperties(projectDatabaseConnectionProperties);
        phpCrudCreator.createProject(database);

    }

    private static void giveWattsSyncAndroidPHPCrudGenerator(Database database, String storageDir) {

        PHPProjectConfiguration phpProjectConfiguration = new PHPProjectConfiguration(database.getDatabaseName());
        phpProjectConfiguration.setProjectStorageDirectory("C:\\xampp\\htdocs\\" + storageDir + "\\libs\\marvik\\libs\\");
        phpProjectConfiguration.setProjectPHPTableCrudLowLevelScriptsStorageDirectory(
                "C:\\xampp\\htdocs\\" + storageDir + "\\libs\\marvik\\libs\\database\\crud\\");
        phpProjectConfiguration.setProjectPHPTableCrudHighLevelScriptsStorageDirectory(
                "C:\\xampp\\htdocs\\" + storageDir + "\\libs\\marvik\\libs\\database\\modules\\");
        phpProjectConfiguration.setProjectPHPDatabaseAPIScriptsStorageDirectory(
                "C:\\xampp\\htdocs\\" + storageDir + "\\libs\\marvik\\libs\\database\\core\\mysql\\");
        phpProjectConfiguration.setProjectSQLScriptsStorageDirectory(
                "C:\\xampp\\htdocs\\" + storageDir + "\\libs\\marvik\\libs\\database\\sql\\");

        ProjectDatabaseConnectionProperties projectDatabaseConnectionProperties = new ProjectDatabaseConnectionProperties(
                "localhost", "root", "", database.getDatabaseName());

        PHPCrudCreator phpCrudCreator = new PHPCrudCreator();
        phpCrudCreator.setProjectConfiguration(phpProjectConfiguration);
        phpCrudCreator.setProjectDatabaseConnectionProperties(projectDatabaseConnectionProperties);
        phpCrudCreator.createProject(database);
    }
}
