@ECHO OFF

SET WD=%CD%
SET SD=%~dp0
SET PARAMS=%*

cd "%SD%"

call mvnw -N io.takari:maven:0.7.7:wrapper %PARAMS%

cd "%WD%"
