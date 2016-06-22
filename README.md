# restutil

A cross-platform REST library that transpiles from Java to Javascript (via GWT) and to Objective-C (via j2objc).

## HELP WANTED

The utility basically works, but I need help from an IOS developer to implement the rest of the 
[IOSRestRequest.java](ios/src/main/java/com/rockstar/restutil/ios/IOSRestRequest.java):execute method or rather re-implement it using some proper framework that supports async requests.

So if someone is interested in helping out, I'd be really grateful and even **willing to pay a bit**. 

The project will remain open source in any case. 

Also, if you happen to know a similar project that let's me maintain the frontend logic in Java, please let me know. No need to have 2 projects doing the same!



## About

One of the biggest problems with developing apps for multiple platforms if you don't want to rely on Javascript 
based frameworks is that you need to have multiple teams develop the same code for each platform - and for each version of your app.

Google uses this approach: They develop the logic and backend communication part of the app once in Java and then use 
it for Android, IOS and Javascript. So only the UI Layer needs to be developed every time.

This way you can keep all of the knowledge about security token handling, caching, validation etc. in your Java team and 
reduce the amount of code that needs to be developed and tested dramatically. 

See [this article](http://arstechnica.com/information-technology/2014/11/how-google-inbox-shares-70-of-its-code-across-android-ios-and-the-web/)


## Now, why the restutil?

**Obviously, the REST communication alone is not enough** - you could use any native REST frameworks directly. But if you want
to implement logic in Java that does the API calls plus token handling, validation etc. then you need a Java implementation to make these calls.
 
Also there's more to a REST framework than just making the call and I don't feel like I want to reinvent the wheel.
  
The Android implementation 
([AndroidRestRequest.java](android/src/main/java/com/rockstar/restutil/android/AndroidRestRequest.java)) 
uses Volley, the GWT implementation
([GWTRestRequest.java](gwt/src/main/java/com/rockstar/restutil/gwt/GwtRestRequest.java))
(.java) uses the GwtRequestBuilder.

The IOS implementation 
([IOSRestRequest.java](ios/src/main/java/com/rockstar/restutil/ios/IOSRestRequest.java))
should use a similar approach with some well known, proven framework. There's only one gotcha:

**I'm not a Objective-C developer and never will be, which is why I need your help.**


## Building the project

### Building the IOS library

When building for the first time, you might need to install cocoapods:

    sudo gem install -V cocoapods


1. Install j2objc 1.0.2
  * https://github.com/google/j2objc/releases
1. cd into the root directory of restutil
1. cp example/iosjava/local.properties.example example/iosjava/local.properties
1. set your j2objc install dir in local.properties
1. ./gradlew clean install
  * (this may take a while when run for the first time)
1. cd example/iosjava
1. ./gradlew clean j2objcBuild

The last step will complain about a missing Pod file.
 
1. You need to create an XCode workspace and project in the _example/ios_ directory as described here:
  * https://github.com/j2objc-contrib/j2objc-gradle/blob/master/FAQ.md#what-is-the-recommended-folder-structure-for-my-app
2. Then create the Podfile with the command given in the error message.  

##### Testing the client

TODO

##### Using the generated code

The build should be configured as an ios static library project

NOTE: open the '.xcworkspace' file in Xcode. It will fail if you open the '.xcodeproj' file.

NOTE: when working with Swift, setup your bridging header:

https://github.com/j2objc-contrib/j2objc-gradle/blob/master/FAQ.md#how-do-i-develop-with-swift


#### Documentation

* http://j2objc.org/docs/
* https://github.com/j2objc-contrib/j2objc-gradle
* https://github.com/j2objc-contrib/j2objc-gradle/blob/master/FAQ.md

