# restutil

A cross-platform REST library that transpiles from Java to Javascript (via GWT) and to Objective-C (via j2objc).

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

