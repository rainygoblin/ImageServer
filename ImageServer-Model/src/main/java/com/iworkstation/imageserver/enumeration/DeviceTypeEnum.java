package com.iworkstation.imageserver.enumeration;


public enum DeviceTypeEnum {
	Workstation("Workstation", "Workstation"), Modality("Modality",
			"Modality"), Server("Server", "Server"), Broker(
			"Broker", "Broker"), PriorsServer(
					"PriorsServer", "Server with Prior Studies for the Web Viewer");

	private String lookup;
	private String description;

	private DeviceTypeEnum(String lookup, String description) {
		this.lookup = lookup;
		this.description = description;
	}

	public String getLookup() {
		return lookup;
	}

	public String getDescription() {
		return description;
	}
}
