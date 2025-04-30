# PiracyControlApp

## Overview
`PiracyControlApp` is a Java-based application that helps to control piracy detection through file scanning. The app utilizes JavaFX for the graphical user interface (GUI) and requires Java 17 and JavaFX to run. This README will guide you through the setup process, including the libraries and dependencies you'll need to download and how to run the application.

## Prerequisites

Before running the application, ensure the following software is installed:

- **Java Development Kit (JDK) 17 or later**  
  [Download JDK 17](https://adoptopenjdk.net/)

- **JavaFX SDK 17.0.15**  
  [Download JavaFX SDK](https://gluonhq.com/products/javafx/)

- **Git** (if you want to clone the repository)  
  [Download Git](https://git-scm.com/)

## Setting Up the Project

### 1. **Clone the Repository**

Clone the repository to your local machine by running the following command:

```
git clone https://github.com/yourusername/PiracyControlApp.git
cd PiracyControlApp
2. Download JavaFX SDK
You need to download the JavaFX SDK. The application depends on JavaFX libraries, which are not bundled with the JDK by default.

JavaFX SDK 17.0.15: Download it from here.

Once downloaded, extract the JavaFX SDK to a directory (e.g., lib/javafx-sdk-17.0.15).

3. Compile the Java Files
Ensure the necessary Java files are in the src directory. Then, compile the source files with the following command:


javac -d bin -cp "lib/javafx-sdk-17.0.15/lib/*" src/piracy/*.java
This will compile the Java source files and output the .class files into the bin/ directory.

4. Create the JAR File
Once the Java files are compiled, you need to create a JAR file for the application. Before doing this, make sure the META-INF/MANIFEST.MF file is in place, with the following content:

META-INF/MANIFEST.MF

Manifest-Version: 1.0
Main-Class: piracy.PiracyControlApp
Class-Path: lib/javafx-sdk-17.0.15/lib/*
Now, package the .class files into a JAR file:


jar cmf META-INF/MANIFEST.MF PiracyControlApp.jar -C bin piracy

This will create the executable JAR file PiracyControlApp.jar.

5. Run the Application
To run the application, you need to specify the JavaFX module path. Use the following command to run the JAR file:

java --module-path lib/javafx-sdk-17.0.15/lib --add-modules javafx.controls,javafx.fxml -jar PiracyControlApp.jar
This will launch the application with the necessary JavaFX modules.

Running the Application for Others
To run the application on another system, the user must:

Install Java 17 or later: Download JDK 17

Download JavaFX SDK: Download JavaFX SDK

They should extract the JavaFX SDK to a directory (e.g., lib/javafx-sdk-17.0.15) and run the following command from the directory where the JAR file is located:

java --module-path lib/javafx-sdk-17.0.15/lib --add-modules javafx.controls,javafx.fxml -jar PiracyControlApp.jar
```
##Troubleshooting

Common Issues

1. Could not find or load main class piracy.PiracyControlApp
This error happens when the JAR file doesn't have the correct main class or classpath configuration. Ensure the META-INF/MANIFEST.MF file has the correct Main-Class and Class-Path entries.

2. NoClassDefFoundError: javafx/application/Application
This error happens if the JavaFX libraries are not correctly referenced. Ensure the --module-path points to the correct location where JavaFX SDK is extracted (lib/javafx-sdk-17.0.15/lib).

3. JavaFX version issues
Ensure you are using Java 17 or a compatible version of JavaFX. Check that the paths to the JavaFX SDK are correct.

##License
This project is licensed under the MIT License. See the LICENSE file for more details.

##Acknowledgments
JavaFX SDK

OpenJDK

