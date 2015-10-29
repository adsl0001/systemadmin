package com.project.model;

/**
 * 系统信息
 * 
 * @author dbz
 *
 */
public class SystemInfo {
	/**
	 * 系统名称
	 */
	private String osName;
	/**
	 * 可使用内存
	 */
	private long totalMemory;

	/**
	 * 剩余内存
	 */
	private long freeMemory;

	/**
	 * 已使用的物理内存
	 */
	private long usedMemory;

	/**
	 * cpu使用率
	 */
	private double cpuRatio;

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public long getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}

	public long getUsedMemory() {
		return usedMemory;
	}

	public void setUsedMemory(long usedMemory) {
		this.usedMemory = usedMemory;
	}

	public double getCpuRatio() {
		return cpuRatio;
	}

	public void setCpuRatio(double cpuRatio) {
		this.cpuRatio = cpuRatio;
	}

}
