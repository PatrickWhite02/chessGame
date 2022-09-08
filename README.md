# chessGame

This is a very simple chess game, featuring online and local multiplayer support.

This is my first foray into online gaming, so I learned loads about running sockets and threads, which I had also never encountered.

I made this project as a part of my concussion recovery, so I was fairly rusty going into it and had to take long breaks, but I have been able to gradually increase the number of hours a week I am able to work through making this.

To learn how to handle servers in java, I followed <a href = "https://www.codejava.net/java-se/networking/how-to-create-a-chat-console-application-in-java-using-socket" target = "_blank"> this tutorial</a> on making a simple chat server, which I then adapted to fit this project's needs. If anyone else is trying to learn Java socketing I strongly recommend it.

<br>
<b>INSTRUCTIONS</b>

building the game is rather simple; you will see a jar folder at the top of this project which is executable and ready to go (Providing you have JRE,) or you can <a href = "https://github.com/PatrickWhite02/chessGame/releases">go to my distributions tab</a>. Either of these ways will get you up and running quickly.

If you want to build the game to run on your own, make sure each class is compiled through out/production, then run java com.Board in terminal. 
<br>If you want to make your own Server, just change the String "Host" in Client to your server ip ("localhost" if you want to use your local machine) and run java net.serverSide.Server 


<a href="https://www.flaticon.com/free-icons/chess" title="chess icons" target="_blank">Chess icons created by bqlqn - Flaticon</a>

<a href="https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces" target="_blank">Images for chess pieces from wikipedia</a>