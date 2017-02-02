# Falcon
**Take Android screenshots with Falcons bright eye!**

*Includes dialogs, toasts and all other extra windows in your screenshot.*

[![Build Status](https://travis-ci.org/jraska/Falcon.svg?branch=master)](https://travis-ci.org/jraska/Falcon)
[![Sample](https://img.shields.io/badge/Download-Sample-blue.svg)](https://drive.google.com/file/d/0B0T1YjC17C-rQ25taHBXSXE2Uzg/view?usp=sharing)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg) ](https://github.com/jraska/Falcon/blob/master/LICENSE)
[![Download](https://api.bintray.com/packages/jraska/maven/com.jraska%3Afalcon/images/download.svg) ](https://bintray.com/jraska/maven/com.jraska%3Afalcon/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Falcon-green.svg?style=true)](https://android-arsenal.com/details/1/2793)


## Usage

```java
// Saving screenshot to file
Falcon.takeScreenshot(activity, file);
// Take bitmap and do whatever you want
Bitmap bitmap = Falcon.takeScreenshotBitmap(activity);
```

Check falcon-sample and its android tests for more example usage.

## Download

Grab via Gradle:
```groovy
compile 'com.jraska:falcon:1.0.4'
```

## Spoon Compat

[Spoon][Spoon] is really great library, but it also does not include dialogs and other extra windows in its screenshots.
To keep using Spoon with advantage of Falcon you can use `FalconSpoon` class.

#### Usage of Spoon Compat
```java
// Screenshot with tag
File screenshot = FalconSpoon.screenshot(activity, tag);
// Screenshot with own test class and method name
File screenshot = FalconSpoon.screenshot(activity, tag, testClassName, testMethodName);
```

#### Gradle
```groovy
androidTestCompile 'com.jraska:falcon-spoon-compat:1.0.4'
```

## Q&A

*Why another screenshot library?*

There are plenty of great screenshot libraries, but all of them miss Dialogs, Toasts and other extra windows in Activity. Not this one!


## License

    Copyright 2015 Josef Raska

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

  [Spoon]: https://github.com/square/spoon
