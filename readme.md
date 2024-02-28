# ApareAPI [![Build](https://github.com/9e-Docteur/ApareAPI/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/9e-Docteur/ApareAPI/actions/workflows/gradle-publish.yml)
ApareAPI is a API for multiple use in Java. Created the 1 Augustus 2023 by 9e_Docteur, this API is the result of [ApareProject](https://github.com/9e-Docteur/ApareProject). A project which constitute to create many things in Java.
This API can be used for multiple things. With it, you can create very easily events, custom mod support, and more coming later !

# Warning !
Since version 1.0, a new feature that allow you to create/send/receive packet has been implemented, however this may not be secured, if you want a version of the api without it, you can go to release, and download the jar called like "ApareAPIWithoutPackets-1.0.jar". 
Caution : People making mod can do malicious code if you install their mod with a packet containing an malicious code. Please take care of mod using packets!!!! If you do so, IT'S AT YOUR OWN RISK!!!!! If you want a version without packet features, use the jar called like "ApareAPIWithoutPackets-1.0.jar"

##Wiki 
https://loris-p.gitbook.io/apareapi-wiki/

## Licence
[© 2023 Loris P. (9e_Docteur). All rights reserved.](https://github.com/9e-Docteur/ApareAPI/blob/master/LICENCE.md)
### How to install ApareAPI on my project?
1. Add the maven to the repository on gradle or maven.<br>
Gradle:
```gradle
repositories {
maven {
        name = "DocTeam Maven on CapMine Servers"
        url = "https://maven.capmine.tech/maven/"
    }
}
```
Maven:
```maven
<repository>
     <id>github</id>
    <url>https://maven.pkg.github.com/9e_Docteur/ApareAPI</url>
    <snapshots>
       <enabled>true</enabled> -> IF YOU WANT ALPHA/BETA/DEV OF THE API
    </snapshots>
</repository>
```
2. Add the dependency
Gradle:
```gradle
dependencies {
    implementation "be.ninedocteur:apareapi:<version>"
}
```
Maven:
```maven

```
Replace ``<version>`` by the version of the api you wanna choose.

### Done !
Now you can follow tutorials on the [wiki](https://github.com/9e-Docteur/ApareAPI/wiki/).
