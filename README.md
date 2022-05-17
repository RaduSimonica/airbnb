# airbnb

### Requirements:

- Chrome version: 101.*
- Optional: maven 3

### Running:

- Clone this repository from the ```main``` branch using IntelliJ
- Open the maven menu ('m' icon on the top-right)
- Execute the maven test goal ```mvn clean test```
- Wait for the execution to finish
- After the execution, a report will be generated at ```report/index.html```
- Logs can be found in ```logs/``` directory

Alternatively you can find the main TestNG suite at ```src/test/java/suites/MainSuite.xml```.
Right click on the xml file -> Run...

This way a report won't generate, but you will be able to see individual tests being run in the IntelliJ runner.


### Notes:
- Chrome headless mode is disabled by default. 
  You can enable it in ```src/main/resources/config.properties``` file at line: 6 ```driver.chrome.headless = true```
- If for any reason the installed version of Chrome is not 101.* and cannot be 101.*, please check your version and
  download the appropriate Chrome drivers from ```https://chromedriver.chromium.org/downloads```. Please note that
  the framework was tested with official release drivers. Beta drivers may not work.
  - Click on three dots menu button
  - Click Settings
  - Click About Chrome
  - Download Chrome drivers for Linux, Mac and Windows
  - Rename them like: ```Linux: chromedriver_linux; Mac: chromedriver_mac; Windows: chromedriver_windows.exe```
  - Replace the existing files in ```src/main/resources/driver```
  
- The 3 tests run in parallel and are fully independent of each other.
- Due to the nature of Selenium, sometimes the tests may fail without a particular reason. If that's the case, 
  please run the suite again.
At this stage a full sweep on all the breaking points to add retries and proper waits is not necessary for the scope.
- All tests have been adapted because the requirements were a bit old and the Airbnb platform changes slightly. 
  - Example Test 1 (check that the property can accommodate at least the number of guests) has been adapted to open 
  the property page and check there the number of guests.
  - Example test 2 (open the details of the first property) has been adapted to open each property on the page since 
    the benefit exceeds the implementation cost.
- Worth to note that during the development of this framework, Google interactions were kept at a minimum.


Hope to hear from you soon and,

### Don't hate, Automate! :)
