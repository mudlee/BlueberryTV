# Blueberry TV - Your personalised TV channel
-----------

### There are many reasons why you would want to create your own personalised TV Channel.
*	Safety - Your child will never be exposed to anything your not aware of
*	You know exactly what your child will be watching
*	You can easily change the programme guide
*	You can use vimeo or youtube links in program guide that will be downloaded automatically

### Requirements
*	Java JRE 1.6 32bit - http://www.java.com/en/download/index.jsp
*	Windows, Linux
*	If you use youtube/vimeo url-s, the application needs write permisson on movies path that you use

### Example how to run tv:
Unzip the program under dist directory then run like this:

	java -jar BlueberryTV.jar -moviesPath=e:\Movies\test\ -programGuidePath=e:\Movies\test\ProgramGuide.xml

-----------

### Example program guide
	<?xml version="1.0" encoding="UTF-8"?>
    <week>
    	<monday><!-- NOTHING --></monday>
    	<tuesday><!-- NOTHING --></tuesday>
    	<wednesday>
    		<program from="7:80">Winnie_The_Pooh.mp4</program>
    		<program from="12:00">TomAndJerry.mkv</program>
    	</wednesday>
    	<thursday><!-- NOTHING --></thursday>
    	<friday>
    		<program from="8:00">http://www.youtube.com/watch?v=-cSFPIwMEq4</program>
    		<program from="9:00">https://vimeo.com/12236680</program>
    	</friday>
    	<saturday>
    		<program from="7:80">Winnie_The_Pooh.mp4</program>
            <program from="12:00">TomAndJerry.mkv</program>
    	</saturday>
    	<sunday><!-- NOTHING --></sunday>
    </week>

Note: when no program is available, blueberry will play a built in no signal video.

### Steps to change the program guide
*	Stop the application
*	Just change the xml file
*	Start the application

Note: the application will validate your program guide, so you should not care about that.

### Available options and syntax
	-programGuidePath=PATH # must be a valid path of your program guide xml
	-moviesPath=PATH # it's a directory that contains your movie. For eg.: if the path is 'c:\Movies' and you have a movie at 'c:\Movies\sample.mkv' you can use in the xml file as 'sample.mkv'. Of course, sub folders are accepted
	-debug # turn of debug messages

### Special thanks to
*	VLC team - http://www.videolan.org/vlc/
*	VLCJ team - http://caprica.github.io/vlcj/
*	Art - http://blog.xuggle.com/
*	axet - https://github.com/axet/vget

### TODO
*	Put VLC libraries into the jar, then the first run of jar must unpack these files
*	Move to github (sorry bitbucket)
*	Change the people's mind (haha)
*	Youtube/vimeo videos handling separately (do not download again when restart, do not replase user's media if the name is same, etc.)