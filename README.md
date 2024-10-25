**About the project**

The project "Frogger" has been built with the help of the Java framework Swing, which helped us in configuring the backend of the project, and elements of the front-end.

The game is simple. The player must guide the frog to the other side of the map without colliding with other objects.

On start-up, the program loads up a custom class called GUI, which acts as the main menu for the game. The GUI class is composed of a series of JPAnels and JFrames, all which act with the intent of creating an interface for the player to utilize. As it acts as a menu for the player, it consists of three different options:

1. Start - Loads up the GameScreen class, allowing the user to start playing the game.
2. About Us - Loads up a JPanel, offering informatio regarding gameplay and such.
3. Settings - Loads up a JPanel, allowing the user to customize their experience by selecting their own characteristics for the gameplay. (such as difficulty and sound)

**How does selecting difficulty work?**
Once in  Settings, the user finds three buttons, each corresponding to a different difficulties (Easy, Medium, Hard). Through the selection of one of these buttons, the setDifficulty method will handle the speeds of the objects by multiplying with a constant variable in regards to the user's choice.

When the player selects the Start option, the gameScreen is loaded, a new Thread starts, along with a myriad of different classes:

1. **Frog** - The player class, which focuses on player movement, can be controlled through the use of the arrow keys. Furthemore, the player has distinct thread, which serves as a way to denote movement across the screen.
2. **Vehicle** - The first obstacle the player must face. The game will reset if collision is detected between the frog and the Vehicle objects (implemented by the checkCollision() method). This class is composed of 3 different subclasses, which inherit the main attributes of the Vehicle class:
   2.1. **Motorcycle** - "The smallest" class in terms of sprite size. It is the fastest of the Vehicle subclasses.
   2.2. **Car** - "Average" in terms of size. It has a medium speed.
   2.3. **Truck** - The heaviest of the Vehicle subclasses. It is the slowest of the three subclasses.
3. **Log** - Once the player successfully passes the highway area of the screen, they will have to come in contact with the logs, an object which will help them pass the river. The frog will be able to safely stand on the logs, thus getting the same horizontal speed as the logs do. By moving from a log to another, the player will successfully pass the river portion, thus winning the current game.
4. **Turtle** - Similarly to the logs, the Turtles will help the player in traversing the river portion of the level.
   4.1. **DrowningTurtle** - Unlike the super-class, the Drowning Turtle implements an individual thread which will determine it in switching sprites every few seconds. Once the Drowning Turtle "goes underwater", the player will not be able to stay on it.

To make the user's experience more entertaining, the game implements a score and a lifeCounter system. The first refers to player's advancement towards the end of the map, respectively a system which grants the player three lives.One the player either made contact with a vehicle or the water, a life is subtracted from the total amount, and the player spawns to the beggining of the map. Once the player loses three lives, a gameOver screen appears, announcing the final score for the session.

**How to run the program?**

Once you gitclone or download the zipfile, the user must run the program from the GUI class (which contains the main method).

**What are the topics of choice specific to this project?**

1. Version control - attained through the usage of Git and GitHub
2. UI/UX design - attained through the implementation of classes and methods meant to enhance the user experience

**What were the references used?**

The main source of inspiration for graphics and gameplay was the 80s arcade game "Frogger" which has a similar gameplay.
The code itself was written with the help of multiple tutorials and online resources such as StackOverflow (and other online forums) and YouTube videos, as well as the information provided in the 2IP90 Programming class.
The sprites were found through online searching (in copyright free projects).
The graphics for the buttons was made through the help of Canva.
The map was made through the use of a pixel-art site.

**Contributors:**
Sebastian Georgescu (1926209)
Bogdan Culea (2124904)

**You can contact us at:**
b.culea@student.tue.nl
s.georgescu@student.tue.nl
