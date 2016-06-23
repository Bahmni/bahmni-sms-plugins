# Introduction
This repository contains plugins to use with the Bahmni two factor authentication web application. This project is divided into sub-projects. Each sub-project contains code for a plugin.

#Developers
The build tool used in the project is gradle.

* Run `./gradlew clean build` to build all the plugins. Or for example use `./gradlew clean sms-gate-way-me:build` to build the `sms-gate-way-me` plugin.

#Installation
Steps required for installing a plugin can be found in the [web app repository's](https://github.com/Bahmni/two-factor-auth) README section

#Write your own plugin
The [web application repository](https://github.com/Bahmni/two-factor-auth/) contains a sub-project called `sms-interface` that has the contract for the plugin. You actually do not need to worry about how to add dependencies and other stuff.

* Quickly get started by copying one of sub-projects there itself and rename it. You can change the name of the jar generated and other settings in the `build.gradle` file of your sub-projects. Hack your way through the existing code in the sub-project.
