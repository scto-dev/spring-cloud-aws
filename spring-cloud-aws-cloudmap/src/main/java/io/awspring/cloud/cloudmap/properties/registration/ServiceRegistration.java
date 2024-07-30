/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.awspring.cloud.cloudmap.properties.registration;

import io.awspring.cloud.cloudmap.CloudMapUtils;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import org.springframework.cloud.client.serviceregistry.Registration;
import software.amazon.awssdk.services.ec2.Ec2Client;

public class ServiceRegistration implements Registration {

	private final CloudMapRegistryProperties properties;

	private final Map<String, String> registrationDetails;

	private final CloudMapUtils UTILS = CloudMapUtils.getInstance();

	public ServiceRegistration(CloudMapRegistryProperties properties, Ec2Client ec2Client, String deploymentPlatform) {
		registrationDetails = UTILS.getRegistrationAttributes(ec2Client, deploymentPlatform);
		this.properties = properties;
	}

	@Override
	public String getInstanceId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public String getScheme() {
		return Registration.super.getScheme();
	}

	@Override
	public String getServiceId() {
		return UTILS.generateServiceId(properties.getNameSpace(), properties.getService());
	}

	@Override
	public String getHost() {
		return registrationDetails.get(UTILS.IPV_4_ADDRESS);
	}

	@Override
	public int getPort() {
		return 0;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public URI getUri() {
		return null;
	}

	@Override
	public Map<String, String> getMetadata() {
		return registrationDetails;
	}

}
