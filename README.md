# Falcon
**Take Android screenshots with Falcons bright eye!**

*Includes dialogs, toasts and all other extra windows in your screenshot.*

[![Maven Central](https://img.shields.io/maven-central/v/com.jraska/falcon.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.jraska%22%20AND%20a:%22falcon%22)
[![Sample](https://img.shields.io/badge/Download-Sample-blue.svg)](https://drive.google.com/file/d/0B0T1YjC17C-rQ25taHBXSXE2Uzg/view?usp=sharing&resourcekey=0-dCAb-jbrkvStB7_vM0t54w)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg) ](https://github.com/jraska/Falcon/blob/master/LICENSE)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Falcon-green.svg?style=true)](https://android-arsenal.com/details/1/2793)


## Usage

```java
// Saving screenshot to file
Falcon.takeScreenshot(activity, file)
// Take bitmap and do whatever you want
val bitmap = Falcon.takeScreenshotBitmap(activity)
```

Check falcon-sample and its android tests for more example usage.

## Download

Grab via Gradle:
```groovy
implementation 'com.jraska:falcon:2.2.0'
```


## Q&A
*Is Falcon on Maven Central?*

Yes! Starting with version `2.2.0`.

*Why another screenshot library?*

There are plenty of great screenshot libraries, but all of them miss Dialogs, Toasts and other extra windows in Activity. Not this one!

*Why is Falcon crashing on Android 9 or higher?*

You are most likely not using the latest version of Falcon. Android 9 is supported from Falcon version `2.1.0`. Some changes were needed since we use some reflection to make the screenshots possible.


## Spoon Compat - Deprecated

[Here](https://github.com/jraska/Falcon/tree/2.1.1#spoon-compat) you can find the instructions to use [Falcon 2.1.1](https://github.com/jraska/Falcon/tree/2.1.1#spoon-compat) with [Spoon](https://github.com/square/spoon). Spoon Compat support was discontinued as [Spoon](https://github.com/square/spoon) was [last released in 2017](https://github.com/square/spoon/releases). The Spoon Compat artifacts are present on jcenter only.

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
