# Android PrefKot Library
[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT)
 
[![pipeline status](https://gitlab.com/HossainCo/PrefKot/badges/master/pipeline.svg)](https://gitlab.com/HossainCo/PrefKot/commits/master)
[![coverage report](https://gitlab.com/HossainCo/PrefKot/badges/master/coverage.svg)](https://gitlab.com/HossainCo/PrefKot/commits/master)
 
[![Build Status](https://travis-ci.org/HossainCo/PrefKot.svg?branch=master)](https://travis-ci.org/HossainCo/PrefKot)
[![Coverage Status](https://coveralls.io/repos/github/HossainCo/PrefKot/badge.svg?branch=master)](https://coveralls.io/github/HossainCo/PrefKot?branch=master)

[![Github All Releases](https://img.shields.io/github/downloads/hossainco/PrefKot/total.svg?style=flat-square)]()
[![](https://jitpack.io/v/HossainCo/PrefKot.svg)](https://jitpack.io/#HossainCo/PrefKot)
[ ![Download](https://api.bintray.com/packages/hossainco/maven/PrefKot/images/download.svg) ](https://bintray.com/hossainco/maven/PrefKot/_latestVersion)

Simplify usage of Android SharedPreferences in Kotlin by Delegates :)

## How to use
### Usage
```kotlin
/* if default is provided then there is no need to provide Type for function */
var pref by pref(
	pref = mySharedPreferences,
	key = "message", // key default value is Kotlin Property name's
	cache = true, // cache is true by default: if its off always get new values from SharedPreferences instance, else cache latest download
	default = "Hossain Khademian", // default can be a simple Value like "Hi" or Provider of its type like "Hossain Khademian"::toLower or { data.getSomthing() }, these providers use for lazy loading !!:)
)

// in class [Context | Activity | Application | View | Fragment]:
var pref1 by pref<String>()
var pref2 by pref(key= "State") { default: "Hello World!" }

// or use extensions
val pref3 by context.pref<Boolean>()


// use them as normal values
pref = "new Value"

val data = pref3
pref3 = false // Error
```

### Gradle
```Groovy
// in root project
allprojects {
	repositories {
		// ...
		maven { url 'https://jitpack.io' }
	}
}
 
// in project
dependencies {
	compile 'com.github.hossainco:prefkot:0.1.3'
	// ...
}
```

## License
```text
MIT License
 
Copyright (c) 2017 HossainCo
 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
 
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
 
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
