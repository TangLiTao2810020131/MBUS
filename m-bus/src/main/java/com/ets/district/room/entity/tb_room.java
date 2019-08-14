package com.ets.district.room.entity;

public class tb_room {
	  private String id;
	  private String code;
	  private String name;
	  private String parentid;
	  private String ctime;
	  private String remark;
	  private String water_meter_id;
	  private String roomTypeName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWater_meter_id() {
		return water_meter_id;
	}

	public void setWater_meter_id(String water_meter_id) {
		this.water_meter_id = water_meter_id;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
}
