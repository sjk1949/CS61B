# Gitlet Design Document

**Name**: sjk1949

## Classes and Data Structures

### Class 1

#### Fields

1. Field 1
2. Field 2


### Main

This is the entry point to the gitlet program. It takes in arguments from the command line and calls
corresponding command in `Repository`. It also validates the arguments based on the command to ensure that the arguments
passed in are valid.

#### Fields

The class has no fields and hence no associated state, it just defers the execution to the `Repository` class.

### Repository

This class has the main logic of our program. It represents the repository of my gitlet. This class will handle all of
the gitlet command by reading/writing from/to the corect file, setting up persistence, and additional error checking.

#### Fields

1. `static final File CWD = new File(System.getProperty("user.dir"))` The current working directory. This is useful for
the other `File` objects we need to use.
2.  `public static final File GITLET_DIR = join(CWD, ".gitlet")` The hidden `.gitlet` directory, this is where
all of the state of the `Repository` will be stored. It is also package private as other classes will use it to store
their state.

### Commit

This class represents a `Commit` that will be stored in a file. Because each `Commit` has a unique hash code, we may
simply use that as the name of the file that the object is serialized to.

#### Fields

1. `private String message` the message of the commit
2. Field 2

## Algorithms

## Persistence

The directory structure looks like this:
```
CWD
 ├──────file1
 ├──────file2
 ├──────...
 └──────.gitlet
         ├──────commits
         │       ├──────commit1(hash)
         │       ├──────commit2
         │       └──────commitn
         ├──────stage
         │       ├──────file1(hash)
         │       └──────file2
         ├──────objects
         │       ├──────file1(hash)
         │       └──────file2
         ├──────HEAD
         ├──────
         └──────...
```

The `Repository` will set up all persistence.

When we use `init`, Repository will check and create `.gitlet` file and call `commit.init()` and other class init.
It'll also create file `HEAD` to record the latest commit.