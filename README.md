# Java Common Discord Library [![Java CI with Gradle](https://github.com/CronixZero/JCDL/actions/workflows/gradle.yml/badge.svg)](https://github.com/CronixZero/JCDL/actions/workflows/gradle.yml)
JCDL is designed to make creating Bots with Java the easiest is has ever been.

JCDL is seperated into SubProjects:
- Discord BotDriver
  - Used for starting your Discord Bot and setting an easy way without lots of JDA Stuff.
- Discord Presence
  - Used for giving you Discord Bot an own look. While beeing configurable, Discord Presence keeps it easy to interact with Discord and setup some custom statuses.
- Discord Commands
  - Used for creating your own Commands without the need to do your own handling. Just tell the CommandHandler, that you made a Command and it'll do the rest.

## Discord Bot Driver
### Gradle
```implementation 'xyz.cronixzero.sapota:discord-botdriver:1.0.0'```

### Maven
```<dependency>
  <groupId>xyz.cronixzero.sapota</groupId>
  <artifactId>discord-botdriver</artifactId>
  <version>1.0.0</version>
</dependency>```

## Discord Presence
### Gradle
`implementation '``xyz.cronixzero.sapota:discord-presence:1.0.0'```

### Maven
```<dependency>
  <groupId>xyz.cronixzero.sapota</groupId>
  <artifactId>discord-presence</artifactId>
  <version>1.0.0</version>
</dependency>```
