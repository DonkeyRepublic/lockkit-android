# Donkey Lock Kit
<img src="https://app.bitrise.io/app/b03242d68d965a84/status.svg?token=4X7nR4w8EwAj2589-hzUKQ&branch=develop"> <img src="https://maven-badges.herokuapp.com/maven-central/bike.donkey/lockkit/badge.svg?style=flat&gav=true"> <img src="https://img.shields.io/nexus/s/bike.donkey/lockkit?label=snapshots&server=https%3A%2F%2Fs01.oss.sonatype.org">
<br/>
<br/>

Donkey Lock Kit is a Kotlin Android framework that enables interaction with BLE-enabled locks that [Donkey Republic](https://donkey.bike) vehicles are equipped with. It is a supplement to [Donkey Republic x TOMP](https://github.com/DonkeyRepublic/donkey_tomp) to help aggregators fully integrate Donkey Republic services into their product.
Purpose of the framework is to enable handling of bluetooth locks that TOMP cannot access online. Note that it also handles minimal server communication to Donkey for authentication and tracking purposes, however it does not communicate with TOMP and neither calls any major transactional requests. Make sure that all the required TOMP interactions are done according to the documentation.

## Prerequisites
Donkey Lock Kit APIs are available after you authenticated with the SDK token received from Donkey Republic. It helps establish the secure usage of the framework and prevent wrong manipulation of the Donkey locks. Note that this SDK token is different from the SDK token that is used for server communication.
Contact your representative at Donkey Republic to obtain the SDK token.

### System requirements
- <b>Android 5.0 Lollipop (API 21)</b> is the minimum required version for deployment
- The framework has been built primarily with <b>Kotlin</b> in mind
- The framework has been built with <b>Gradle version 7.0</b> and <b>Android gradle version 4.2.1</b>

## Getting started
Donkey Lock Kit is available via <b>MavenCentral</b> repository. In order to apply the dependency, add the `mavenCentral` repository in the top-level gradle like this:

<br/>

```groovy
allProjects {
    repositories {
        mavenCentral() // for production ready releases
    }
}
```

<br/>

Then add the Donkey Lock Kit dependency in the module-level gradle like this:

<br/>

```groovy
dependencies {
    implementation 'bike.donkey:lockkit:<version>'
}
```

### Snapshots
For using beta releases of Donkey Lock Kit in your project, add the `maven snapshot` repository in the top-level gradle like this:

<br/>

```groovy
allProjects {
    repositories {
        maven { url 'https://s01.oss.sonatype.org/content/repositories/snapshots/' } // for beta versions (snapshots)
    }
}
```

<br/>

Then add the Donkey Lock Kit dependency in the module-level gradle the same way as for the production release version. Note however that the beta release version number will always end with a suffix `-SNAPSHOT`. Make sure not to rely on beta releases too much since they can be removed and stop being available at any future time.

## Usage
The framework exposes [DonkeyLockKit](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/index.html) Kotlin `object` that handles all the interaction with the Donkey Republic locks.

In order to interact with the vehicle locks, you must initialize the SDK with the SDK token provided to you by Donkey Republic:

<br/>

```kotlin
DonkeyLockKit.initializeSdk(context = context, sdkToken = "MyDonkeyToken", onResult = { result ->
    // The result of the initialization will be available in the callback in case of debugging
    println(result)
})
```

<br/>

Note that the initialization happens instantly and you are able to use the lock functions straight away. In the case error is thrown in the `onResult` callback - the SDK will be considered uninitialized and no further lock functions would be usable until the SDK is successfully authenticated again.
If the SDK is uninitialized during the call of lock handling functions, [UninitializedSdkError](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit.errors/-uninitialized-sdk-error/index.html) will be thrown.

## Lock handling
For identifying the specific lock, <b>lock device name</b> is used and needs to be supplied for all lock functions as an argument. It is usually displayed in the following format `AXA:C885D6B0F03113BCAC2F`. Make sure to get it from the relevant TOMP endpoint.

<br/>

In order for the lock device to execute actions - the framework uses <b>eKeys</b> that consist of `key` and `passkey` that are passed in [initializeLock](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/initialize-lock.html). This data is dynamic, cannot be reused and each device can have 1+ unique eKeys depending on number of uses and whether using one or more phone devices. Make sure to get eKey data from the relevant TOMP endpoint.

<br/>

Each lock function of the framework has `onResult` callback to let you know asynchronously the outcome of the operation. Additionally, for [unlock](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/unlock.html), [lock](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/lock.html) and [prepareEndRental](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/prepare-end-rental.html) there is also available `onUpdate` callback to let you know the [ConnectionUpdate](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit.updates/-connection-update/index.html) of the given operation.

<br/>

These are the major functions regarding lock handling:
- [initializeLock](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/initialize-lock.html) takes arguments as `key` and `passkey` to make sure the lock properly utilizes these resources to invoke desired action. Make sure to get it from the relevant TOMP endpoint.
- [unlock](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/unlock.html) invokes the unlock action of the specified lock.
- [lock](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/lock.html) invokes the lock action of the specified lock.
- [prepareEndRental](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/prepare-end-rental.html) invokes checking of the lock state including the [extraLockCheck](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit.updates/-connection-update/-extra-lock-check/index.html) - necessary operation before ending rental to make sure the vehicle is safe from any misuse. In the case of lock being unlocked, this function will try to lock it as well. Note however that this does not end the rental. Remember to call the required endpoint on TOMP.
- [finalizeLock](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/finalize-lock.html) makes sure any saved resources are cleaned from the cache and the bluetooth lock device gets disconnected. Make sure all of the lock handling is done at this point because without a new eKey it might not be possible to handle the lock again.

<br/>

Following is the recommended sequence of lock handling:
1. [initializeLock](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/initialize-lock.html) - once per rental (if invoked more times, the sequence of the keys for the given lock might be out of order and might need refresh)
2. [unlocking](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/unlock.html) and [locking](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/lock.html) - up to 255 times together on one eKey (if more times is needed, after using all, please [refresh](https://github.com/DonkeyRepublic/donkey_tomp#refresh-ekey) eKeys and [initializeLock](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/initialize-lock.html) again with a new eKey)
3. [prepareEndRental](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/prepare-end-rental.html) - just once before wanting to finish rental to make sure lock device is locked (it is okay to call it more than once)
4. [finish rental on TOMP](https://github.com/DonkeyRepublic/donkey_tomp#finishing-rental) - after successful [prepareEndRental](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/prepare-end-rental.html)
5. [finalizeLock](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/finalize-lock.html) - after successful [finish of the rental on TOMP](https://github.com/DonkeyRepublic/donkey_tomp#finishing-rental)

## Configuration
In the need of supply additional configuration to the framework, [DonkeyConfig](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-config/index.html) is provided. Default configuration is set with [Server Environment](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-config/-server-environment/index.html) to [LIVE](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-config/-server-environment/-l-i-v-e/index.html) and [Log Level](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-config/-log-level/index.html) to [DEBUG](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-config/-log-level/-d-e-b-u-g/index.html).

It is a `val` therefore it is not possible to update `DonkeyConfig` itself but only its internal variables:

<br/>

```kotlin
    // example
    DonkeyLockKit.config.environment = DonkeyConfig.ServerEnvironment.TEST
    
    // example using apply
    DonkeyLockKit.config.apply {
        environment = DonkeyConfig.ServerEnvironment.TEST
        logLevel = DonkeyConfig.LogLevel.ERROR
    }
```

<br/>

Note that it is only possible to update the `DonkeyConfig` values before calling of [initializeSdk](https://developer.donkey.bike/tomp/lockkit/docs/android/-donkey%20-lock%20-kit/bike.donkey.lockkit/-donkey-lock-kit/initialize-sdk.html) function.

## Changelog

<b>v1.1.1-SNAPSHOT</b><br/>`2021-11-25`
- fixed potential issues of having duplicates while using the Donkey Lock Kit with other libraries
- fixed issue with ExtraLockCheck not emitting result when bluetooth got disabled

<b>v1.1.0</b><br/>`2021-10-19`
- now LockKit will use `deviceName` instead of `bdAddress` for all the bluetooth functions to enable easier input + aligns with iOS

<b>v1.0.0</b><br/>`2021-09-09`
- first stable release of the SDK
