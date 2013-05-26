@echo off
cd build/classes
jar -cvmf make.manifest WebContentParser.jar com

move WebContentParser.jar "..\.."
pause