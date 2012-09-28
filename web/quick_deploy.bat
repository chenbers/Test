xcopy /Y /D /S src\main\webapp\*.xhtml target\tiwipro
xcopy /Y /D /S src\main\webapp\images\* target\tiwipro\images
xcopy /Y /D /S src\main\webapp\css\*.css target\tiwipro\css
xcopy /Y /D /S src\main\webapp\*.js target\tiwipro
rem xcopy /Y /D /S src\main\resources\*.properties target\tiwipro\WEB-INF\classes\
rem xcopy /Y /D /S src\main\config\appcontext\*.xml target\tiwipro\WEB-INF\
xcopy /Y /D /S ..\dao\target\classes\*.class target\tiwipro\WEB-INF\classes\
xcopy /Y /D /S target\classes\*.class target\tiwipro\WEB-INF\classes\
