<h1 align="center">Jyro</h1>
<p align="center">
  <a href="https://opensource.org/licenses/MIT"><img alt="License" src="https://img.shields.io/github/license/rekybongso/jyro"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/rekybongso/jyro/stargazers/"><img alt="API" src="https://img.shields.io/github/stars/rekybongso/jyro"/></a>
  <a href="https://github.com/rekybongso/jyro/releases/tag/1.0.0"><img alt="API" src="https://img.shields.io/github/release-date/rekybongso/jyro"/></a>
</p>
<p align="center">
Just another Github API beginner android project which utilize MVVM architecture and modern Android tech-stacks.
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
MIT License

Copyright (c) 2021 Reky Bongso

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
