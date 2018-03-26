package com.polidea.rxandroidble2.exceptions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.polidea.rxandroidble2.utils.GattStatusParser;

/**
 * Exception emitted when the BLE link has been disconnected either when the connection was already established
 * or was in pending connection state. This state is expected when the connection was released as a
 * part of expected behavior (with {@link android.bluetooth.BluetoothGatt#GATT_SUCCESS} state).
 *
 * @see com.polidea.rxandroidble2.RxBleDevice#establishConnection(boolean)
 */
public class BleDisconnectedException extends BleException {

    /**
     * Set when the state is not available, for example when the adapter has been switched off.
     */
    public static final int UNKNOWN_STATE = -1;

    public static class BleAdapterDisabledException extends BleException {
        // Disconnection related to disabled Bluetooth adapter
    }

    @SuppressWarnings("WeakerAccess")
    @NonNull
    public final String bluetoothDeviceAddress;
    public final int state;

    public static BleDisconnectedException adapterDisabled(String macAddress) {
        return new BleDisconnectedException(new BleDisconnectedException.BleAdapterDisabledException(), macAddress, UNKNOWN_STATE);
    }

    public BleDisconnectedException(Throwable throwable, @NonNull String bluetoothDeviceAddress, int state) {
        super(createMessage(bluetoothDeviceAddress, state), throwable);
        this.bluetoothDeviceAddress = bluetoothDeviceAddress;
        this.state = state;
    }

    public BleDisconnectedException(@NonNull String bluetoothDeviceAddress, int state) {
        super(createMessage(bluetoothDeviceAddress, state));
        this.bluetoothDeviceAddress = bluetoothDeviceAddress;
        this.state = state;
    }

    private static String createMessage(@Nullable String bluetoothDeviceAddress, int state) {
        final String gattCallbackStatusDescription = GattStatusParser.getGattCallbackStatusDescription(state);
        return "Disconnected from " + bluetoothDeviceAddress + " with status " + state + " (" + gattCallbackStatusDescription + ")";
    }
}
