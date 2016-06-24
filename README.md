# Introduction
This repository contains plugins to use with the Bahmni two factor authentication web application. This project is divided into sub-projects. Each sub-project contains code for a plugin.

#Developers
The build tool used in the project is gradle.

* Run `./gradlew clean build` to build all the plugins. Or for example use `./gradlew clean sms-gate-way-me:build` to build the `sms-gate-way-me` plugin.

#Installation
Steps required for installing a plugin can be found in the [web app repository's](https://github.com/Bahmni/two-factor-auth) README section

#sms-gate-way-me plugin
This plugin enables you to use the third party SMS service provider https://smsgateway.me/. Create an account in the website and download the android app in your mobile phone. The app asks for the email and password to register your device with the website. Whenever an SMS is sent to the service provider, it will be pushed to the Android app in your mobile, which actually sends the SMS from your device. To use this plugin in Bahmni, you have to provide email and password in the file named `SmsGatewayMe.conf` located at `/home/bahmni/.bahmni-security/`.

The format is:
```
email=REGISTERED_EMAIL
password=REGISTERED_PASSWORD
```
#Write your own plugin
The [web application repository](https://github.com/Bahmni/two-factor-auth/) contains a sub-project called `sms-interface` that has the contract for the plugin. You actually do not need to worry about how to add dependencies and other stuff.

* Quickly get started by copying one of sub-projects there itself and rename it. You can change the name of the jar generated and other settings in the `build.gradle` file in your sub-project. Hack your way through the existing code in the sub-project. But, make sure you do not change the package name from `org.bahmni.auth.smsplugin` as it is required to correctly load the plugin.
