# React Native In-App Review

A lightweight TurboModule based In-App Review library for React Native (Android + iOS).<br/>
Built for <b>RN New Architecture (Fabric + TurboModules).</b>

#### 🚀 Fast • ⚡️ Type-safe • 🔥 Zero-bridge-overhead

## Installation


```sh
npm install react-native-in-app-review-turbo
```

or

```sh
yarn add react-native-in-app-review-turbo
```

## Setup

### iOS

Run:

```sh
cd ios && pod install
```

### Android
No additional setup required, autolinking and TurboModule support works out of the box.

## API

📌 ```RequestInAppReview(): Promise<boolean>```

- Triggers the in-app review flow.

📌 ```IsAvailable(): Promise<boolean>```

- Checks if the platform supports showing the review dialog.

> **⚠️ iOS Behavior Notice**  
> Apple never guarantees that the review popup will appear — even on TestFlight or production.  
> The system decides based on internal heuristics.  
> A successful `RequestInAppReview()` call does **not** guarantee the dialog will show.


## Usage

### Request In-App Review

```js
import { RequestInAppReview, IsAvailable } from 'react-native-in-app-review-turbo';

async function triggerReview() {
  try {
    const available = await IsAvailable();

    if (!available) {
      console.log("In-app review is not available on this device.");
      return false;
    }

    const success = await RequestInAppReview();

    if (success) {
      console.log("Review flow launched or completed successfully.");
    } else {
      console.log("Review flow could not be launched.");
    }

    return success;
  } catch (err) {
    console.warn("Error while requesting in-app review:", err);
    return false;
  }
}
```

## Behavior Notes (Important)

### iOS
- Review dialog may not appear every time (Apple limit: ~3 times/year per device).

- iOS simulator always shows instantly; real devices may not.

- TestFlight behaves like production — system decides whether to show UI.

### Android
- Uses Google Play In-App Review API.
- Dialog always appears if Play Store is installed & review flow allowed.


## Contributing

- [Development workflow](CONTRIBUTING.md#development-workflow)
- [Sending a pull request](CONTRIBUTING.md#sending-a-pull-request)
- [Code of conduct](CODE_OF_CONDUCT.md)

## License

MIT
