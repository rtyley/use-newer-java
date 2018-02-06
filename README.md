# Use-Newer-Java

_A small helper library to give users a friendly message when they're using a version of Java that's too old_

### The Problem...

Does your app require a recent version of Java? Are your users likely to
have only old versions of Java installed?

If users run code with a version of Java that's too old, the fatal error message
isn't friendly - it starts like this:
`java.lang.UnsupportedClassVersionError : Unsupported major.minor version 51.0`...
and, because it's a stacktrace, carries on for several lines lines like this...

```
Exception in thread "main" java.lang.UnsupportedClassVersionError: org/eclipse/jgit/lib/Repository : Unsupported major.minor version 51.0
    at java.lang.ClassLoader.defineClass1(Native Method)
    at java.lang.ClassLoader.defineClassCond(ClassLoader.java:637)
    at java.lang.ClassLoader.defineClass(ClassLoader.java:621)
    at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:141)
    ...
```

...that's a bad message to show your user! `UnsupportedClassVersionError` means
that their version of Java is too old, and `major.minor version 51.0` means they
need at least [Java version **7**](https://stackoverflow.com/a/11432195/438886)
to get the code to run - but they probably won't know that.

### How to give the user a better message?

_Use Newer Java_ is a small library compiled for
[_old_](https://github.com/rtyley/use-newer-java/blob/ec1f3562c/pom.xml#L54)
versions of Java and designed for command-line Java tools like
[the BFG Repo-Cleaner](https://rtyley.github.io/bfg-repo-cleaner/),
that are distributed as a single uber-jar containing all dependencies
and run with the `java -jar my-app.jar` invocation.

It will display friendly messages like this to your user:

```
Looks like your version of Java (1.8) is too old to run this program.

You'll need to get Java 9 or later.
```

If the user has a version of Java that's new enough, the user won't see the
message, and _Use Newer Java_ will just pass along the command line arguments
to the main class of your command-line app, which will run normally. Your
app can be compiled for the latest version of Java - _Use Newer Java_ will
be the only code targeting old versions of Java in your app.

`UseNewerJava` is compiled targeting Java 5, meaning that it will execute
correctly on most Java VMs users are likely to have these days.

### How can I use this in my project?

1. Include `use-newer-java` as a dependency in your project
2. Set the `Main-Class:` field in the manifest of your built jar to
   `use.newer.java.Version8` or `use.newer.java.Version9`, etc as appropriate.
3. Set the `Main-Class-After-UseNewerJava-Check:` in your jar manifest to
   the normal main class of your app- _Use Newer Java_ will follow this setting,
   and invoke this code after making it's check.

That's it.

### Example

See https://github.com/rtyley/bfg-repo-cleaner/commit/a1cf8694f for an
example of adding `UseNewerJava` to a CLI app within an SBT build. It
shouldn't be too hard to take the same approach with Maven, Gradle builds
etc.



