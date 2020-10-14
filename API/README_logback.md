Logger added for all current servelets. See below for additional information

# Logger usage
- The settings for the logger are configured within the logback.xml within the resources folder (needs to stay in the same folder as the webpage files, to my understanding).  Currently setup to create a new log file upon each execution of the program and will name that file with a date.  Note the sizing configuration within the logback.xml may need to change later if this application runs for long periods of time.  The log files will output into the log folder in the root of the project.  Ask Will or Lawrence if you need help changing this.

- Within the class in which you want to log, have the following imports:
    ```
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    ```
- Add the following line of code within the class in which you want to log, conventionally at the top:
    ```
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    ```
- Finally, you will signal the logger to output to the log file by having it use the various methods it has including trace(), debug(), info(), warn(), and error().  Example:
    ```
     public String view(String variable) {
        log.debug("The view method was run");
        ...
     }

    ```

    ```
     try {
         ...
     } catch (Exception e) {
        log.error("Exception!" + e);
     }
     
    ```