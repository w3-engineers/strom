rem Uninstall APK, delete .ethereum and delete databases folder for all connected devices
adb devices | tail -n +2 | cut -sf 1 | xargs -iX adb -s X uninstall io.left.core.rmsample
adb devices | tail -n +2 | cut -sf 1 | xargs -iX adb -s X shell rm -r sdcard/.ethereum
adb devices | tail -n +2 | cut -sf 1 | xargs -iX adb -s X shell rm -r sdcard/databases