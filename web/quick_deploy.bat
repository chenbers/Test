xcopy /Y /D /S src\main\webapp\*.xhtml %CATALINA_HOME%\webapps\tiwipro
xcopy /Y /D /S src\main\webapp\*.js %CATALINA_HOME%\webapps\tiwipro
xcopy /Y /D /S target\classes\*.properties %CATALINA_HOME%\webapps\tiwipro\WEB-INF\classes\
xcopy /Y /D /S target\classes\*.class %CATALINA_HOME%\webapps\tiwipro\WEB-INF\classes\

