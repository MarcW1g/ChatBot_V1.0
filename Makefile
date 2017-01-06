CLASSPATH = acm.jar
CFLAGS = -cp .:$(CLASSPATH)

JAVA_FILES = ChatBotJava.java ChatBotDataBase.java ChatBotPerformAction.java
CLASS_FILES = $(JAVA_FILES:.java=.class)

all: chatbotjava

chatbotjava: $(CLASS_FILES)

%.class: %.java
	javac $(CFLAGS) $<

clean:
	rm -f *.class
