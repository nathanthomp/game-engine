# Homegrown Game Engine from Scratch in Java

This game engine implements the MVC architecture to provide a clear seperation of concerns. The MVC architecture used is an adapted MVC that adds an additional layer inbetween the view and the controller called a Renderer that is responsible for the view interaction logic, plus another additional layer inbetween the model and controller called a Manager that is responsible for the state management of the model.

```
                                                Controller
                                            +-----------------+
                                            |      Game       |
                                            +-----------------+
                                           /                   \
                            +-----------------+             +-----------------+
                Model State |     Manager     |             |    Renderer     | View Logic
                            +-----------------+             +-----------------+
                                     |                               |
                            +-----------------+             +-----------------+
                      Model |      Scene      |             |     Surface     | View
                            +-----------------+             +-----------------+
```

- **Game (Controller)**: The Game is the top level orchestrator for the game loop.
- **Renderer (View Logic)**: The Renderer contains logic for drawing pixels to the surface.
- **Surface (View)**: The Surface is the container for the view. This container is the implementation of input events and Swing components.
- **Manager (Model State)**: The Manager contains the state of the current scene and the nested scenes, represented as a stack.
- **Scene (Model)**: The responsibility of the scene is to knows what entities exist, and use systems to update those entities.


This game engine also implements an ECS architecture adapted to 3D versus 2D rendering. The Entity class represents 3D elements that are within a Scene and the Widget class represents 2D elements that can also be added to a scene.

```
Entity (Renderable 3D element)
 ├── has Transform
 └── can be subclassed (Player, Cube, Enemy)

Widget (Renderable 2D Element)
 ├── has a screen position
 ├── has a width and height
 └── can be subclassed (Text, Button)

Component (Base class)
 ├── can be implemented by Renderable
 └── can be implemented by Collidable
```
