<h1 align="center">Jyro</h1>
<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>
<p align="center">
Just another Github API beginner android project which utilize MVVM architecture and some modern Android tech-stacks.
Searching data through Github API end-points, then store some data with Room Database (favourite or bookmarking feature).
It also provide dark or light theme that can be stored to Data Store preferences.
</p>

## Screenshots
<p align="center">
<img src="https://raw.githubusercontent.com/rekybongso/jyro/main/screenshots/20211105_125701.jpg" width="32%"/>
<img src="https://raw.githubusercontent.com/rekybongso/jyro/main/screenshots/20211105_123304.jpg" width="32%"/>
<img src="https://raw.githubusercontent.com/rekybongso/jyro/main/screenshots/20211105_123312.jpg" width="32%"/>
</p>

## Tech-stacks and Libraries
- Full written in Kotlin
- Coroutines, Flow, and Live Data
- Jetpack libraries:
  - Hilt
  - Room
  - DataStore
  - DataBinding
  - Lifecycles
  - Preferences
  - Navigation components
  - Cardview
  - Recyclerview
  - ConstraintLayout
  - CoordinatorLayout
  - ViewPager2
- Material designs
- Retrofit and Okhttp
- Coil-Kt image loading and processing
- FancyToast
- Timber
- Spotless

## How-to
1. You need to generate a personal access token. Go to [Github Docs](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) for more informations
2. In your gradle.properties file, add this line: 
```` 
```
GithubApiKey=token your_personal_access_token
```
```` 
3. Change the value with your personal access token
4. If the gradle.properties doesn't exists, just create a new one.

# License
```xml
Designed and developed by 2021 Reky Bongso

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
