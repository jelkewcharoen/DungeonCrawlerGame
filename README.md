# Project DungeoCrawler

## Install
1. set up maven (recomend command line)
    - Windows (recommended)
        - https://maven.apache.org/install.html
    - Macos (recommended)
        - Install brew
        - brew install Maven
    - IntellIj 
        - import project as a maven project and it should work out of the box
2. `mvn clean javafx:run` will run the project
3. `mvn checkstyle::check` will run checkstyes on the project

## Create a View
1. create a folder here src/main/java/app/dungeoncrawler/views with the name of the view
    - command Windows/MacOS `mkdir src/main/java/app/dungeoncrawler/views/NewView`
2. create the same folder but in resources src/main/resources/app/dungeoncrawler/views
    - `mkdir src/main/resources/app/dungeoncrawler/views/NewView`
3. Now create your View and Controllers
    - create Java Class in 
    ```bash
    src/main/resources/app/dungeoncrawler/views/NewView/NewView.java
    src/main/resources/app/dungeoncrawler/views/NewView/NewViewController.java
    ```
    - View will have how you load the fxml refer to WelcomeView.java
    - Controller will all the event from the scene refer to WelcomeController.java
4. Create the FXML file inside the resource folder you created earlier `src/main/resources/app/dungeoncrawler/views/NewView/NewView.fxml` THIS WILL HAVE ALL YOUR VIEW CODE (VBOX, HBOX, GRIDPANE ect...) refer to WelcomeView.fxml
5. add your New View to the module expors `src/main/java/module-info.java` 
6. add your view to the AppScenes.java as part of a scene



# CS2340_team39

## 1. Creates a new branch 
```bash
git checkout -b banch-name
```
#### Example 
```bash
git checkout -b task-1
```

## 2. Shows you the changes you have done locally
```bash
git status
```

## 3. Adds your changes to a stage 
which means that your changes will differntiate with the changes that you would be doing from the point you stage

#### Example 
You completed a part of Task 1 which it was to create a Class Car, now you stage your changes so every change you make from now on will show you the diference from the point you staged your changes

```bash
git add .
```

## 4. Commits your changes, make sure you add a meaningful comment 

```bash
git commit -m "What did you do in the staged changes?"
```

## 5. Pushes your changes to your remote version of your branch 
```bash
git push origin branch-name
```

## 6. AFTER you commit you do this so your changes are sync to master 
```bash
git pull --rebase origin master
```

## 7. If you have a conflict, Once you have resolved it you have to force push in order to update your branch 
```bash
git push -f origin branch-name
```
## Resources
https://openjfx.io/openjfx-docs/#maven
https://github.com/UltraMixer/FlexBoxFX