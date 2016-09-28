# ravenbuild

Raven is a simple build tool, written in Java.

## Getting Started

Start with:

    ./raven help

### Developing with IntelliJ IDEA

After checking out raven, use raven to create the IntelliJ IDEA project files:

    ./raven intellij

Then open the project in the IDE. Check the project settings, especially whether the project JDK and
the language level are correct.

Then you can create a run configuration for raven itself:

![Run configuration for raven](https://raw.githubusercontent.com/ravensuite/ravenbuild/master/documentation/screenshot-raven-runconfig.png)

And one for running all tests:

![Run configuration for running all tests](https://raw.githubusercontent.com/ravensuite/ravenbuild/master/documentation/screenshot-raven-testconfig.png)

Congratulations, now you can start developing raven with IntelliJ IDEA!

## Design Goals

 - **Declarative**: The build configuration (raven.json) cannot contain any logic. There is no build script
 (unlike build.gradle) and you cannot add code to the configuration (unlike gmaven plugin). Any logic has to
 be implemented in plugins.
 - **Easy to Understand**: Every project has a simple task graph. No goals, phases, ..., just tasks that are
 executed in the right order.
 - **Easy to Extend**: Writing plugins should be very simple, especially when you only need them for a
 single project. It should be possible to just put a single Java (or JavaScript or Groovy) file in a certain
 directory, and raven recognizes it as a plugin.
 - **Easy to Debug**: Raven will try to give you great diagnostic information when something goes wrong.
 No more reading tens of megabytes of maven debug logs.
 - **Fast**: A build needs to be very fast, since you will execute it very often.

