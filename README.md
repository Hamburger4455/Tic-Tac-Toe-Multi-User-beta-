Continuing my GitHub project dump!

This time, it's an older Java Tic-Tac-Toe client-server project I worked on around a year ago.
It explores TCP socket communication, server-side game logic, and a basic signature mechanism designed to detect board tampering.

I'm gradually moving my older projects from a shared Git workflow into my personal GitHub portfolio. More projects coming soon

How it works:
Start the server -> Launch client, which initializes empty board -> select pos from 1 to 9 -> client sends the board representation, signature, and selected position to the server
The server validates the request and applies the player's move
If the game has not ended -> server selects an available position for the bot -> server returns the updated board and the game status
The client then displays the result and continues until the game ends.
