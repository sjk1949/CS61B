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

### merge
Above is a table of the file state and it's dealing method in the merge command.
A, B, C represent differnet versions of the file.

| Split point | Given branch | Current branch | CWD | result                | givenBranchFlag | currBranchFlag |
|-------------|--------------|----------------|-----|-----------------------|-----------------|----------------|
| A           | B            | A              | -   | B(add)                | 1               | 0              |
| A           | A            | B              | -   | B                     | 0               | 1              |
| A           | B            | B              | -   | B                     | 1               | 1              |
| A           | -            | -              | B   | -(remain in CWD)      | 1               | 1              |
| -           | -            | A              | -   | A                     | 0               | 1              |
| -           | A            | -              | -   | A(checkout & add)     | 1               | 0              |
| -           | A            | A              | -   | A                     | 1               | 1              |
| -           | A            | B              | -   | AB(replace content)   | 1               | 1              |
| A           | -            | A              | -   | -(remove & untracked) | 1               | 0              |
| A           | A            | -              | -   | -                     | 0               | 1              |
| A           | B            | C              | -   | BC(replace content)   | 1               | 1              |

1. find the Split point of the Given Branch and Current Branch
2. find the version is changed or not in Given Branch, if true, set `givenBranchFlag = 1`
3. find the version is changed or not in Current Branch, if true, set `currBranchFlag = 1`

## Persistence

The directory structure looks like this:
```
CWD
 ├──────file1
 ├──────file2
 ├──────...
 └──────.gitlet
         ├──────commits
         │       ├──────a0
         │       └──────a1
         │              ├──────commit1(hash)
         │              ├──────commit2
         │              └──────commitn
         ├──────stage
         │       ├──────file1(hash)
         │       └──────file2
         ├──────objects
         │       ├──────file1(hash)
         │       └──────file2
         ├──────HEAD
         ├──────heads
         │       ├──────main
         │       └──────branch1
         └──────REMOVE
```

The `Repository` will set up all persistence.

When we use `init`, Repository will check and create `.gitlet` file and call `commit.init()` and other class init.
It'll also create file `HEAD` to record the latest commit.