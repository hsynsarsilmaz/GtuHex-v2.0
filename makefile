Compile = javac
Run= java
Interfaces = HexGame.java
Classes = Engine.java StartScreen.java Game.java Program.java


Compile and Run: 
	$(Compile) $(Interfaces) $(Classes)
	$(Run) Program
	rm *.class
