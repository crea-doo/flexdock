@ECHO OFF

SET WD=%CD%
SET SD=%~dp0
SET PARAMS=%*

cd "%SD%"

call mvnw -N versions:update-child-modules %PARAMS%
call mvnw versions:commit %PARAMS%

cd "%WD%"
