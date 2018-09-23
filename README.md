# trip-test
For this project, though we're limited to the terminal/command line for user interface, I followed the Model-View-Controller design pattern.
The reason why I followed this design pattern is that I wanted to seperate the application logic from user interface and the control between the user interface and the application logic. However, given the size of the project, one might say following that pattern is an overkill. But I tried to keep it as simple as possible so I cover all the requirements while maintaining clean code.

## Model
- __Driver__

	This class holds data and operations related to the Driver object.

- __Trip__

	This class holds data and operations related to the Trip object.

- __Controller__

	This class handles input/output of application and maintains an in memory map of data collected at any given time.

- __InputValidator__

	This class handles input validation to insure that input commands and data fields follow the format mentioned in the requirements.
	
## Usage

Navigate to the project's directory, and run the following command: `mvn clean install`. This is assuming that `maven` is installed on the machine. If maven is not installed on the machine, then please [download maven](http://maven.apache.org/download.cgi) and then [install it](http://maven.apache.org/install.html).

Include the data file `*.txt` in the project's directory. Once the maven build is successful, run the following command from the project's directory:

`java -jar target/trip-test-jar-with-dependencies.jar <filename>`

Please insure to substitute `<filename>` with the valid data file.

If the program runs successfully and you see `Success`, then please navigate to the project's directory and there should be a file `report.txt` that contains the final report.

