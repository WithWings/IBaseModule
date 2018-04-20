package com.withwings.baseutils.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;

/**
 * 感应器 Sensor 工具类
 * <p>
 * <uses-permission android:name="android.permission.DEVICE_POWER"/>
 * <uses-permission android:name="android.permission.WAKE_LOCK"/>
 * <p>
 * 创建：WithWings 时间 2018/4/20
 * Email:wangtong1175@sina.com
 */
@SuppressWarnings({"WeakerAccess", "StatementWithEmptyBody", "unused"})
public class SensorUtils {

    private static final String LOG_TAG = "WithWings";

    /**
     * 有返回值是因为 SensorManager 需要关闭
     * <p>
     * SensorManager..unregisterListener(SensorEventListener sensorEventListener);
     *
     * @param context              上下文
     * @param levelAndFlags        用来控制获取的WakeLock对象的类型
     *                             <p>
     *                             PowerManager.PARTIAL_WAKE_LOCK : CPU处于运行状态，而屏幕和键盘背光将可以熄灭。
     *                             <p>
     *                             PowerManager.SCREEN_DIM_WAKE_LOCK : 在Android4.2(API 17)后被废弃，使用FLAG_KEEP_SCREEN_ON代替。屏幕微亮,键盘暗
     *                             <p>
     *                             PowerManager.SCREEN_BRIGHT_WAKE_LOCK : 在Android3.2(API 13)后被废弃，使用FLAG_KEEP_SCREEN_ON代替。屏幕亮,键盘暗
     *                             <p>
     *                             PowerManager.FULL_WAKE_LOCK : 在Android4.2(API 17)后被废弃，建议采用FLAG_KEEP_SCREEN_ON代替。全亮
     *                             <p>
     *                             PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK : 随着用户接近会息屏
     *                             <p>
     * @param type                 根据 type 设置监听什么
     *                             <p>
     *                             Sensor.TYPE_ACCELEROMETER ： 加速度感应检测
     *                             <p>
     *                             Sensor.TYPE_MAGNETIC_FIELD ： 磁场感应检测
     *                             <p>
     *                             Sensor.TYPE_ORIENTATION ： 方位感应检测
     *                             <p>
     *                             Sensor.TYPE_GYROSCOPE ： 回转仪感应检测
     *                             <p>
     *                             Sensor.TYPE_LIGHT ： 亮度感应检测
     *                             <p>
     *                             Sensor.TYPE_PRESSURE ： 压力感应检测
     *                             <p>
     *                             Sensor.TYPE_TEMPERATURE ： 温度感应检测
     *                             <p>
     *                             Sensor.TYPE_PROXIMITY ： 接近感应检测
     *                             <p>
     * @param samplingPeriodUs     延迟时间的精度密度
     *                             <p>
     *                             SensorManager.SENSOR_DELAY_FASTEST : 0ms
     *                             <p>
     *                             SensorManager.SENSOR_DELAY_GAME : 20ms
     *                             <p>
     *                             SensorManager.SENSOR_DELAY_UI : 60ms
     *                             <p>
     *                             SensorManager.SENSOR_DELAY_NORMAL : 200ms
     *                             <p>
     * @param iSensorEventListener 监听器
     * @return 管理器
     * if (mSensorManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
     * mSensorManager.unregisterListener(SensorEventListener sensorEventListener);
     * mSensorManager = null;
     * }
     */
    public static SensorManager startPSensor(Context context, int levelAndFlags, int type, int samplingPeriodUs, final ISensorEventListener iSensorEventListener) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            final SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            if (powerManager != null && sensorManager != null) {
                PowerManager.WakeLock wakeLock = powerManager.newWakeLock(levelAndFlags, LOG_TAG);
                wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
                sensorManager.registerListener(new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        float[] values = event.values;
                        switch (event.sensor.getType()) {
                            case Sensor.TYPE_PROXIMITY:
                                if (values[0] == 0.0) {
                                    // 贴近手机
                                } else {
                                    // 远离手机
                                }
                                break;
                            default:
                        }

                        iSensorEventListener.onSensorChanged(event);
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                        iSensorEventListener.onAccuracyChanged(sensor, accuracy);
                    }
                }, sensorManager.getDefaultSensor(type), samplingPeriodUs);
                return sensorManager;
            }
        }

        iSensorEventListener.onNonPSensor();
        return null;
    }

    public interface ISensorEventListener extends SensorEventListener {

        /**
         * 当感应器变化时
         *
         * @param event 信息类
         *              Sensor sensor = event.sensor
         *              <p>
         *              根据 sensor.getType 判断本次回调的变化提示
         *              Sensor.TYPE_ACCELEROMETER
         *              <p>
         *              加速度感应检测
         *              <p>
         *              Sensor.TYPE_MAGNETIC_FIELD
         *              <p>
         *              磁场感应检测
         *              <p>
         *              Sensor.TYPE_ORIENTATION
         *              <p>
         *              方位感应检测
         *              <p>
         *              Sensor.TYPE_GYROSCOPE
         *              <p>
         *              回转仪感应检测
         *              <p>
         *              Sensor.TYPE_LIGHT
         *              <p>
         *              亮度感应检测
         *              <p>
         *              Sensor.TYPE_PRESSURE
         *              <p>
         *              压力感应检测
         *              <p>
         *              Sensor.TYPE_TEMPERATURE
         *              <p>
         *              温度感应检测
         *              <p>
         *              Sensor.TYPE_PROXIMITY
         *              <p>
         *              接近感应检测
         */
        @Override
        void onSensorChanged(SensorEvent event);

        /**
         * 当准确度变化的时候
         *
         * @param sensor   信息类
         * @param accuracy 准确度
         *                 SensorManager.SENSOR_STATUS_ACCURACY_LOW ：低
         *                 SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM ：中等
         *                 SensorManager.SENSOR_STATUS_ACCURACY_HIGH ：高
         *                 SensorManager.SENSOR_STATUS_UNRELIABLE ：不可靠的
         */
        @Override
        void onAccuracyChanged(Sensor sensor, int accuracy);

        /**
         * 没有距离传感器
         */
        void onNonPSensor();
    }







/*
*   加速度感应检测——Accelerometer

    Accelerometer Sensor测量的是所有施加在设备上的力所产生的加速度的负值（包括重力加速度）。加速度所使用的单位是m/sec^2，数值是加速度的负值。


    SensorEvent.values[0]：加速度在X轴的负值

    SensorEvent.values[1]：加速度在Y轴的负值

    SensorEvent.values[2]：加速度在Z轴的负值

    例如：

    当手机Z轴朝上平放在桌面上，并且从左到右推动手机，此时X轴上的加速度是正数。

    当手机Z轴朝上静止放在桌面上，此时Z轴的加速度是+9.81m/sec^2。

    当手机从空中自由落体，此时加速度是0

    当手机向上以Am/sec^2的加速度向空中抛出，此时加速度是A+9.81m/sec^2

*   重力加速度感应检测——Gravity
    重力加速度，其单位是m/sec^2，其坐标系与Accelerometer使用的一致。当手机静止时，gravity的值和Accelerometer的值是一致的。

*   线性加速度感应检测——Linear-Acceleration


    Accelerometer、Gravity和Linear-Acceleration三者的关系如下公式：

    accelerometer = gravity + linear-acceleration

*   地磁场感应检测——Magnetic-field

    地磁场的单位是micro-Tesla(uT)，检测的是X、Y、Z轴上的绝对地磁场。

*   陀螺仪感应检测——Gyroscope

    陀螺仪的单位是弧度/秒，测量的是物体分别围绕X，Y，Z轴旋转的角速度。它的坐标系与加速度传感器的坐标系相同。逆时针方向旋转的角度正的。也就是说，如果设备逆时针旋转，观察者向X，Y，Z轴的正方向看去，就报告设备是正转的。请注意，这是标准的正旋转的数学定义。

*   光线感应检测——Light

    values[0]:表示环境光照的水平，单位是SI lux。

*   位置逼近感应检测——Proximity

    values[0]:逼近的距离，单位是厘米(cm)。有一些传感器只能支持近和远两种状态，这种情况下，传感器必须报告它在远状态下的maximum_range值和在近状态下的小值。

*   旋转矢量感应检测——Rotation Vector


    旋转向量是用来表示设备的方向，它是由角度和轴组成，就是设备围绕x,y,z轴之一旋转θ角度。旋转向量的三个要素是，这样旋转向量的大小等于sin(θ/2)，旋转向量的方向等于旋转轴的方向。

    values[0]: x*sin(θ/2)
    values[1]: y*sin(θ/2)
    values[2]: z*sin(θ/2)
    values[3]: cos(θ/2) (optional: only if value.length = 4)

*   方向感应检测——Orientation


            其单位是角度

    values[0]: Azimuth(方位)，地磁北方向与y轴的角度，围绕z轴旋转(0到359)。0=North, 90=East, 180=South, 270=West
    values[1]: Pitch(俯仰),围绕X轴旋转(-180 to 180), 当Z轴向Y轴运动时是正值
    values[2]: Roll(滚)，围绕Y轴旋转(-90 to 90)，当X轴向Z轴运动时是正值

    注意：这里的定义与航空中定义的yaw、pitch和roll不同，航空中的X轴是沿着飞机的最长边(从头到尾)。

    注意：这个传感器类型存在遗留问题，请使用与getRotationMatrix()和remapCoordinateSystem()以及getOrientation()配合使用，来计算值代替得到的值。

    重要说明：由于历史的原因，以顺时针旋转的滚动角是正的（从数学上讲，它应该是逆时针方向）。
*/

}
