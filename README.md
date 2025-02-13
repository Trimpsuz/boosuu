# Boosuu Xposed module for Busuu

[![Latest release](https://img.shields.io/github/v/release/Trimpsuz/boosuu?label=latest)](https://img.shields.io/github/v/release/Trimpsuz/boosuu?label=latest)
[![License](https://img.shields.io/badge/License-GPLv3-green.svg)](https://img.shields.io/badge/License-GPLv3-green.svg)

## Overview

This is an Xposed/LSPosed Module for [**Busuu**](https://busuu.com/).

### Tested on:

- **Busuu** v32.4.1 (1237713)
- [**LSPosed**](https://github.com/LSPosed/LSPosed) framework v1.9.2 (7024), API v100, System v12 (API 31)
- [**LSPatch by JingMatrix**](https://github.com/JingMatrix/LSPatch) framework v1.10.1 (7137), API v93, LSPatch v0.7 (430), System v15 (API 35)
- **arm64-v8a**

## Usage

By enabling the module all features are enabled. Users of **LSPosed** can choose to enable/disable logging in the app, for users of **LSPatch** this feature is not available.

## Features

- Remove (skip) all ads
- Remove premium icon from bottom bar
- Spoof premium status
- Spoof subscription status

## Installation

### Rooted device

1. Install the **Busuu** app. Version 32.4.1 (tested working) can be found [**here**](https://www.apkmirror.com/apk/busuu/busuu-learn-languages/busuu-learn-speak-languages-32-4-11237713-release/busuu-learn-speak-languages-32-4-11237713-android-apk-download/).

2. Install the **Boosuu** app from the [**latest release**](https://github.com/Trimpsuz/boosuu/releases/latest).

3. Not required, but using [**Update Locker**](https://github.com/Xposed-Modules-Repo/ru.mike.updatelocker) and [**Hide My Applist**](https://github.com/Dr-TSNG/Hide-My-Applist) can be helpful.

4. Enable the **Boosuu** module for the **Busuu** app in **LSPosed**.

5. Optionally, enable logging in the **Boosuu** app.

6. Kill and restart the **Busuu** app, there is a button for this in the **Boosuu** app.

### Non-rooted device

1. Install the **Busuu** app. Version 32.4.1 (tested working) can be found [**here**](https://www.apkmirror.com/apk/busuu/busuu-learn-languages/busuu-learn-speak-languages-32-4-11237713-release/busuu-learn-speak-languages-32-4-11237713-android-apk-download/).

> [!IMPORTANT]
> The **Busuu** app ships as **split apks**. LSPatch cannot patch these by default so you will have to merge them. The easiest way is installing the split apk and then selecting to patch an installed app in LSPatch.

2. Install [**LSPatch**](https://github.com/JingMatrix/LSPatch/releases/latest). The original [**LSPatch**](https://github.com/LSPosed/LSPatch) and [**NPatch**](https://github.com/HSSkyBoy/NPatch) may work, but they are not tested.

> [!TIP]
> If you are asked to select a directory, press `OK` to launch the file picker, create a folder under any directory, and press `Use this folder` > `Allow`.

3. Install and enable [**Shizuku**](https://github.com/RikkaApps/Shizuku/releases/latest).

4. Install the **Boosuu** app from the [**latest release**](https://github.com/Trimpsuz/boosuu/releases/latest).

5. In **LSPatch** select the **Busuu** app as the app to patch, select the **Integrated** patch mode and embed the **Boosuu** module by selecting `Select an installed app`.

6. Press `Start Patch`, after the patch is completed press `Install`. If you 
selected to patch the **Busuu** app installed on your device rather than a merged apk you will be prompted to uninstall the currently installed app.

## License
This project is licensed under the GPLv3 license. Please refer to the [LICENSE](LICENSE) file for more details.
