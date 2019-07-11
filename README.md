# DownloadSmashCustomMusic


A little java software to download musics from http://www.smashcustommusic.com/



# How to use


-That's very simple, download the latest build (https://github.com/RedStoneMatt/DownloadSmashCustomMusic/releases) or build the source code yourself using Eclipse (https://www.eclipse.org/downloads/)


-Then, in the folder where you downloaded/built the software, open the CMD, and type:

  java -jar DownloadSmashCustomMusic.jar <start id> <end id> <extension>
  
The start id is the id where you want the download to start.
  
The end id is the id where you want the download to end.

So for example, if you want to download all the songs between id 1 and 10, the command you'll have to type in the CMD will be:

  java -jar DownloadSmashCustomMusic.jar 1 10 <extension>
  
And so, the program will download every music from http://www.smashcustommusic.com/1/ to http://www.smashcustommusic.com/10/

The extension is on which extension you'd like to download the songs, for now there is only 4 available: BRSTM, BCSTM, BFSTMWIIU and BFSTMSWITCH

So for example, if you want to download all the songs between id 1 and 10, and in the BRSTM and BCSTM format, the command you'll have to type in the CMD will be:

  java -jar DownloadSmashCustomMusic.jar 1 10 brstm bcstm
  
-After a few minutes, hours or even days depending of your internet connection and of the numbers of songs you want to download, you'll be able to find your downloaded files in the folder where you put the compiled file of the software



# Known bugs


Songs with / or \ in their name won't be downloaded if you're on windows, sometimes if works, sometimes it don't.



# Have fun !


If you have any question or request, contact me on discord: RedStoneMatt#2826

