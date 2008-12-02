xcopy /Y /D /S src\main\webapp\*.xhtml %CATALINA_HOME%\webapps\tiwipro
xcopy /Y /D /S src\main\webapp\images\* %CATALINA_HOME%\webapps\tiwipro\images
xcopy /Y /D /S src\main\webapp\css\*.css %CATALINA_HOME%\webapps\tiwipro\css
xcopy /Y /D /S src\main\webapp\*.js %CATALINA_HOME%\webapps\tiwipro
xcopy /Y /D /S target\classes\*.properties %CATALINA_HOME%\webapps\tiwipro\WEB-INF\classes\
xcopy /Y /D /S target\classes\*.class %CATALINA_HOME%\webapps\tiwipro\WEB-INF\classes\
xcopy /Y /D /S ..\dao\target\classes\*.class %CATALINA_HOME%\webapps\tiwipro\WEB-INF\classes\
