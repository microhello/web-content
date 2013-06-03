@echo off
cd build/classes
jar -cvmf make.manifest WebContentParser.jar com

move WebContentParser.jar "..\.."
cd ../..
xcopy WebContentParser.jar "../StyleTrip-Dev/lib" /I /Y /C /F
pause