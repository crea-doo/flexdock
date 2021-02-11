@ECHO OFF

SET WD=%CD%
SET SD=%~dp0
SET PARAMS=%*

cd "%SD%"

call mvnw clean deploy -Pdeploy %PARAMS%

cd "%WD%"
