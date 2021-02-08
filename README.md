Let There Be Light
=========

![Screenshot](https://github.com/natedawg1013/let-there-be-light/raw/dev/img/screenshot1.png)

Overview
--------
An open source light controller that takes input from a microphone, or from sound playing through a computer, and uses it to control a DMX lighting system based on user presets.

Downloads
---------
**under construction**

Windows (32-bit and 64-bit) Installer

Linux (32-bit and 64-bit) Installer

Mac OS X Installer

Standalone (portable, Windows, Linux, Mac)


Usage Notes
--------
### Windows
Run ltbl using "run.bat"

##### Common Windows problems
>```
>Can't load AMD 64-bit .dll on a
>IA 32-bit platform thrown while loading gnu.io.RXTXCommDriver
>Exception in thread "main" java.lang.reflect.InvocationTargetException
>        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
>        at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
>        at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
>        at java.lang.reflect.Method.invoke(Unknown Source)
>        at org.eclipse.jdt.internal.jarinjarloader.JarRsrcLoader.main(JarRsrcLoader.java:58)
>```
>This is likely because you have a 64-bit system but have a 32-bit installation of Java installed.
>To fix this, we recommend that install the latest 64-bit version of Java from https://java.com/en/download/

>Or you can manually run in 32-bit mode by changing "run.bat" to contain only the following
>```
>@echo off
>java -Djava.library.path=.\lib\Windows\i368-mingw32 -jar "Let There Be Light.jar"
>```
>If you decide to change "run.bat", we suggest you make a backup of it first.

### Linux
Run ltbl using "run.sh"

### Mac

Run ltbl using "run.command"

##### Common Mac problems
>```
>Sorry, try again.
>```
>This can happen when "run.command" attempts to set proper permissions on the RXTX libraries, but can't because it was unable to acquire administrator privilages. You may have typed your password incorrectly or you may not be an administrator.
>If you are an administrator and you do not have a password, you will unfortunately need to assign a password to your account or use an administrator account that has a password.
>
>https://support.apple.com/en-au/HT202035

#

>```
>open() failed with errno=13
>	at java.lang.ClassLoader$NativeLibrary.load(Native Method)
>```
>This is caused when the RXTX libraries do not have access to shared memory or the permissions are not set for execution.
>To fix this, use "run.command" as an administrator

#

>```
>java.lang.UnsatisfiedLinkError: no rxtxSerial in java.library.path thrown while loading gnu.io.RXTXCommDriver
>```
>This is caused when the RXTX library is not allowed to be executed in terminal.
>To fix this, you may need to use terminal and run this command
>```
>chmod -x ./lib/Mac_OS_X/$(uname -m)/librxtxSerial.jnilib"
>```

#

>```
>Exception in thread "main" java.lang.UnsupportedClassVersionError: ltbl/control/Runner : Unsupported major.minor version 51.0
>	at java.lang.ClassLoader.defineClass1(Native Method)
>	at java.lang.ClassLoader.defineClassCond(ClassLoader.java:637)
>	at java.lang.ClassLoader.defineClass(ClassLoader.java:621)
>	at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:141)
>	at java.net.URLClassLoader.defineClass(URLClassLoader.java:283)
>	at java.net.URLClassLoader.access$000(URLClassLoader.java:58)
>	at java.net.URLClassLoader$1.run(URLClassLoader.java:197)
>	at java.security.AccessController.doPrivileged(Native Method)
>	at java.net.URLClassLoader.findClass(URLClassLoader.java:190)
>	at java.lang.ClassLoader.loadClass(ClassLoader.java:306)
>	at java.lang.ClassLoader.loadClass(ClassLoader.java:247)
>	at java.lang.Class.forName0(Native Method)
>	at java.lang.Class.forName(Class.java:249)
>	at org.eclipse.jdt.internal.jarinjarloader.JarRsrcLoader.main(JarRsrcLoader.java:56)
>```
>This is caused because Let There Be Light was designed for Java 7 and uses the terminal to call Java. On Mac OS X, to call Java from the terminal, you must have both JRE and JDK installed.
>To fix this, download the latest JRE and JDK for your system.

>JRE: https://www.java.com/en/download/

>JDK: http://www.oracle.com/technetwork/java/javase/downloads/index.html

#
>If you are running Mac OS X 10.5.8 or earlier and any of the above issues persist, it could be because you are typing your password wrong when prompted, your account is not an administrator, or that your account does not have a password.
>If you do not have a password, you will unfortunately need to assign a password to your account or use an administrator account that has a password.

>https://support.apple.com/en-au/HT202035

TODO
----
* Get Sound-based effects working

Change Log
----------
...
