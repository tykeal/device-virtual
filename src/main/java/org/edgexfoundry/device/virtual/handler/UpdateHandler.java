/*******************************************************************************
 * Copyright 2016-2017 Dell Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @microservice:  device-sdk-tools
 * @author: Tyler Cox, Dell
 * @version: 1.0.0
 *******************************************************************************/
package org.edgexfoundry.device.virtual.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.edgexfoundry.device.virtual.data.DeviceStore;
import org.edgexfoundry.device.virtual.data.WatcherStore;
import org.edgexfoundry.device.virtual.service.DeviceManager;

@Service
public class UpdateHandler {

	@Autowired
	private WatcherStore watchers;
	
	@Autowired
	private DeviceStore devices;
	
	@Autowired
	private DeviceManager deviceManager;
	
	public boolean addDevice(String deviceId) {
		boolean result = devices.add(deviceId); 
		if (result)
			deviceManager.provisionNewDevice(deviceId);
		return result;
	}

	public boolean updateDevice(String deviceId) {
		return devices.update(deviceId);
	}

	public boolean deleteDevice(String deviceId) {
		boolean result = devices.remove(deviceId);
		if (result)
			deviceManager.deCommissionDevice(deviceId);
		return result;
	}
	
	public boolean addWatcher(String provisionWatcher) {
		return watchers.add(provisionWatcher);
	}
	
	public boolean removeWatcher(String provisionWatcher) {
		return watchers.remove(provisionWatcher);
	}
	
	public boolean updateWatcher(String provisionWatcher) {
		return watchers.update(provisionWatcher);
	}

	public boolean updateProfile(String profileId) {
		return devices.updateProfile(profileId) && watchers.updateProfile(profileId);
	}
}