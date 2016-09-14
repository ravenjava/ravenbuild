# ravenbuild

Raven is a simple build tool, written in Java.

## Getting Started

Start with:

    ./raven.sh help

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

