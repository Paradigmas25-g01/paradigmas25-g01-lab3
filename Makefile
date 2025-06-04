JAVA_HOME_PATH=/usr/lib/jvm/java-11-openjdk-amd64
JAVA_HOME := $(JAVA_HOME_PATH)
export JAVA_HOME

MVN = mvn clean compile exec:java
MVN_FLAGS = -Dexec.mainClass="FeedReaderMain" --quiet
NOERR = 2>/dev/null

.PHONY: all no_entities default_args entities_reversed clean

default_args:
	$(MVN) $(MVN_FLAGS) -Dexec.args="" $(NOERR)

named_entities:
	$(MVN) $(MVN_FLAGS) -Dexec.args="-ne" $(NOERR)

quick_heuristic:
	$(MVN) $(MVN_FLAGS) -Dexec.args="-ne -qh" $(NOERR)

random_heuristic:
	$(MVN) $(MVN_FLAGS) -Dexec.args="-ne -rh" $(NOERR)

clean:
	$(MVN) clean --quiet $(REDIR)
