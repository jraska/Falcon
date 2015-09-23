# Falcon
**Taking Android screenshots to next level.** 

*Includes dialogs, toasts and all other extra windows in your screenshot.*

[![Build Status](https://travis-ci.org/jraska/Falcon.svg?branch=master)](https://travis-ci.org/jraska/Falcon)

Usage
------

```java
// Saving screenshot to file
Falcon.takeScreenshot(activity, file);
// Take bitmap and do whatever you want
Bitmap bitmap = Falcon.takeScreenshotBitmap(activity);
```

Download
--------

Grab via Gradle: 
```groovy
compile 'com.jraska:falcon:0.2.1'
```

Q&A
------
*Why another screenshot library?*

There are plenty of great screenshot libraries, but all of them miss Dialogs, Toasts and other extra windows in Activity. Not this one! 


License
--------

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



