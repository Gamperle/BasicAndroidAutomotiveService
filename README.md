# Basic Android Automotive Math Service

A simple AOSP system service that provides basic arithmetic operations (addition and subtraction) through AIDL interface.

## Overview

This service demonstrates how to create a basic system service for AOSP that can be used by other applications through IPC (Inter-Process Communication) using AIDL.

## Features

- Addition operation
- Subtraction operation
- AIDL interface for IPC
- Example client implementation
- SELinux policies

## Structure

```
BasicAndroidAutomotiveService/
├── aidl/
│   └── com/example/mathservice/
│       └── IMathService.aidl          # AIDL interface definition
├── src/
│   └── com/example/mathservice/
│       └── MathService.java           # Service implementation
├── example/
│   └── MathServiceClient.java         # Example client usage
├── sepolicy/
│   ├── file_contexts                  # SELinux file contexts
│   └── mathservice.te                 # SELinux type enforcement
├── Android.bp                         # Build configuration
├── AndroidManifest.xml                # Android manifest
└── README.md
```

## Integration into AOSP

### 1. Copy to AOSP Source Tree

Copy this directory to your AOSP source tree:
```bash
cp -r BasicAndroidAutomotiveService <AOSP_ROOT>/packages/services/MathService
```

### 2. Build the Service

Navigate to your AOSP root and build:
```bash
cd <AOSP_ROOT>
source build/envsetup.sh
lunch <target>
m MathService
```

### 3. Install on Device

Push the APK to the device:
```bash
adb root
adb remount
adb push out/target/product/<device>/system/priv-app/MathService /system/priv-app/
adb reboot
```

### 4. SELinux Integration

Copy SELinux policies to your device's sepolicy directory:
```bash
cp sepolicy/* <AOSP_ROOT>/device/<manufacturer>/<device>/sepolicy/
```

Then rebuild the system image.

## API Usage

### AIDL Interface

```java
interface IMathService {
    int add(int a, int b);
    int subtract(int a, int b);
}
```

### Client Example

```java
// Create client
MathServiceClient client = new MathServiceClient(context);

// Bind to service
client.bindService();

// Use service (after connection is established)
int sum = client.add(10, 5);        // Returns 15
int diff = client.subtract(10, 5);  // Returns 5

// Unbind when done
client.unbindService();
```

### Direct Binding

```java
Intent intent = new Intent("com.example.mathservice.IMathService");
intent.setPackage("com.example.mathservice");
bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
```

## Configuration

### Permissions

The service requires system signature and runs with `android.uid.system` shared user ID. Client applications need appropriate permissions to bind to system services.

### Platform Integration

- **Certificate**: Platform signature required
- **Privileged**: Runs as privileged system app
- **Persistent**: Service is persistent and starts on boot
- **Direct Boot Aware**: Works before user unlock

## Testing

### Using adb shell

```bash
# Start the service
adb shell am startservice com.example.mathservice/.MathService

# Check if service is running
adb shell dumpsys activity services | grep MathService

# View logs
adb logcat | grep MathService
```

### Integration Test

Create a test app that binds to the service and calls the math operations. See `example/MathServiceClient.java` for implementation details.

## Extending the Service

To add more operations:

1. Add method to `IMathService.aidl`
2. Implement method in `MathService.java` in the `mBinder` stub
3. Rebuild the service

Example for multiplication:
```java
// In IMathService.aidl
int multiply(int a, int b);

// In MathService.java
@Override
public int multiply(int a, int b) throws RemoteException {
    Log.d(TAG, "multiply() called: " + a + " * " + b);
    return a * b;
}
```

## Notes

- This is a basic example for educational purposes
- In production, add proper error handling and input validation
- Consider adding permissions for sensitive operations
- Implement proper lifecycle management
- Add comprehensive logging and debugging support

## Requirements

- AOSP source tree (Android 11+)
- Platform signature access
- System/privileged app installation capability

## License

Sample code for educational purposes.