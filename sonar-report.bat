set changedFiles=%*
 for %%* in (.) do set CurrDirName=%%~nx*
 call  mvn sonar:sonar -Dsonar.projectName=%CurrDirName% -Dsonar.projectKey=%CurrDirName% -Dsonar.host.url=http://192.168.1.161 -Dsonar.sources=%changedFiles% -Dsonar.branch=%USERNAME% -Dsonar.test.exclusions=**/*Test*.java