# Tutorial Plugin

This plugin offers a framework for adding wands to the game and is designed to bring new programmers a taste of Java mixed with the fun of Minecraft.

## Setup

### Set up JDK

1. Download jdk 11 from https://adoptopenjdk.net/index.html?variant=openjdk11&jvmVariant=hotspot
2. Make sure that "Set JAVA_HOME variable" and "Add to PATH" are selected options during installation
3. Add its bin directory to your path
4. Make sure the output of `java -version` and `javac -version` in a command prompt both say 11.0.8, otherwise you have another version of java installed

### Install eclipse

1. Go to the eclipse downloads page and download it
2. During installation, select "Eclipse IDE for Java Developers"
3. Click "Launch"
4. Select the default workspace (C:\Users\You\eclipse-workspace or /Users/You/eclipse-workspace) as your workspace folder

### Open the project

1. Download this project folder on GitHub as a zip file
2. Unzip it into your eclipse-workspace folder so you have a directory like C:\Users\You\eclipse-workspace\TutorialPlugin (Windows) or /Users/You/eclipse-workspace/TutorialPlugin (Mac)
3. Open eclipse and go to File > Open Projects from File System > Directory... (select the TutorialPlugin folder)
4. If lots of errors show up in the java files, right-click the TutorialPlugin root and go to Maven > Update project and check the box saying "Force update of snapshots and releases" and click OK. This will make sure all dependencies are installed.

### Get Spigot (the mod server)

1. Download Spigot 1.15.2 from https://getbukkit.org/download/spigot
2. Put the downloaded jar file in its own empty folder somewhere
3. Run the Spigot server (remember this step, you will do it every time you start a programming session)
   1. Open Command Prompt (Windows) or Terminal (Mac) and `cd` to the directory the jar file is in
   2. Run `java -Xms4G -Xmx4G -jar -DIReallyKnowWhatIAmDoingISwear spigot-1.15.2.jar nogui`
4. Lots of extra files should appear, and it will ask you to accept the EULA - change the last line in the eula.txt file to say eula=true
5. Change online-mode=true to online-mode=false in the server.properties file

### Get PlugMan to speed up reloading your plugin

1. Download the jar from https://dev.bukkit.org/projects/plugman
2. Put the jar in the plugins folder of your server folder

### Get Minecraft if you don't have it:

1. Go to https://mc-launcher.com/mcl/minecraft
2. Click "request download links" - don't use adfly
3. Open TLauncher
4. If you are prompted to install Java, do so from java.com, but make sure the output of `java -version` in a command prompt still says 11.0.8. If not, then remove the new install from your path while keeping it installed.
   Click the dropdown saying "(Create at least one account)"
   Add an account (free w/o password)
   Choose any unique username, preferably one with your first name in it

## Development

1. Select minecraft version 1.15.2 from whatever Minecraft launcher you have, and open Minecraft
2. Open this project directory in eclipse
3. Edit the code however you want
4. Export the plugin as a JAR
   1. Right click the root folder ("TutorialPlugin") and go to Run As > 6 Maven Install
   2. Before the line saying "BUILD SUCCESS" it should say something like "Installing C:\Users\You\eclipse-workspace\TutorialPlugin\target\TutorialPlugin-0.0.1-SNAPSHOT.jar to C:\Users\You\.m2\repository\com\unitedlearningacademy\TutorialPlugin\0.0.1-SNAPSHOT\TutorialPlugin-0.0.1-SNAPSHOT.jar" - note that last file location since that is where your jar ended up and make sure it is the .jar and not pom.xml which comes after the jar
5. Move the exported JAR to your server's plugins folder like you did with PlugMan. If your file manager disallows this, it may be because a version of your plugin is already loaded, and you may have to unload it by typing `/plugman unload TutorialPlugin` in Minecraft. After replacing the plugin with the new version, you will have to type `/plugman load TutorialPlugin`.
6. Run the Spigot server as done before during setup. You should see a log message saying that the plugin has been loaded. Now repeat steps 3-5 as needed
