Compile = javac
Run= java
Interfaces = src/HexGame.java
Classes = src/Engine.java src/StartScreen.java src/Game.java src/Program.java


Compile and Run: 
	$(Compile) $(Interfaces) $(Classes)
	$(Run) src/Program
	rm src/*.class
