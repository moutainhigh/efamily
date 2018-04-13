package com.winterframework.efamily.dto;

/** 
* @ClassName: HealthyProfileStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-5 下午2:04:31 
*  
*/
public class HealthyProfileStruc {
	
	

	private Long userId;
	private Long deviceId;
	private Long ridingCalorie=0l; //骑行热量
	   private Long runningStep=0l;    //跑步步数 单位：步数
	   private Long climbingStep=0l;   //爬山步数 单位：步数
	   private Long swimmingDistance=0l; //游泳距离 单位：米
	   private Long walkStep=0l;       //散步步数：单位：步数
	   private Float sleepTime=0f;      //睡眠时间  单位：7.5小时 保留一位小数
	   private Long heartRate=0l;      //心率数据 
	   private Long setHealthConfig=0l;//
	   private Integer sleepLockStatus;
	   private Float sitTime=0f;
	   private Long runningDistance=0l;//跑步里程
	   private Integer bloodRate=0;
	   private Integer systolicPressure=0;
	   private Integer diastolicPressure=0;
	
	public Long getRunningDistance() {
		return runningDistance;
	}
	public void setRunningDistance(Long runningDistance) {
		this.runningDistance = runningDistance;
	}
	public Integer getBloodRate() {
		return bloodRate;
	}
	public void setBloodRate(Integer bloodRate) {
		this.bloodRate = bloodRate;
	}
	public Integer getSystolicPressure() {
		return systolicPressure;
	}
	public void setSystolicPressure(Integer systolicPressure) {
		this.systolicPressure = systolicPressure;
	}
	public Integer getDiastolicPressure() {
		return diastolicPressure;
	}
	public void setDiastolicPressure(Integer diastolicPressure) {
		this.diastolicPressure = diastolicPressure;
	}
	public Float getSitTime() {
		return sitTime;
	}
	public void setSitTime(Float sitTime) {
		this.sitTime = sitTime;
	}
	public Long getRidingCalorie() {
		return ridingCalorie;
	}
	public void setRidingCalorie(Long ridingCalorie) {
		this.ridingCalorie = ridingCalorie;
	}
	public Long getSetHealthConfig() {
		return setHealthConfig;
	}
	public void setSetHealthConfig(Long setHealthConfig) {
		this.setHealthConfig = setHealthConfig;
	}
	public Long getRunningStep() {
		return runningStep;
	}
	public void setRunningStep(Long runningStep) {
		this.runningStep = runningStep;
	}
	public Long getClimbingStep() {
		return climbingStep;
	}
	public void setClimbingStep(Long climbingStep) {
		this.climbingStep = climbingStep;
	}
	public Long getSwimmingDistance() {
		return swimmingDistance;
	}
	public void setSwimmingDistance(Long swimmingDistance) {
		this.swimmingDistance = swimmingDistance;
	}
	public Long getWalkStep() {
		return walkStep;
	}
	public void setWalkStep(Long walkStep) {
		this.walkStep = walkStep;
	}
	public Float getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(Float sleepTime) {
		this.sleepTime = sleepTime;
	}
	public Long getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(Long heartRate) {
		this.heartRate = heartRate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getSleepLockStatus() {
		return sleepLockStatus;
	}
	public void setSleepLockStatus(Integer sleepLockStatus) {
		this.sleepLockStatus = sleepLockStatus;
	}
	/*private String userName;
	private Long userId;
	private String avantarURL;
	private Long steps = 0l;
	private Long heartRate = 0l;
	private Long stepsGrade = 0l;
*/
	/* 0:健康成人的心率为60～100次/分，大多数为60～80次/分 
	   1:成人每分钟心率超过100次（一般不超过 160次/分）称为窦性心动过速。
		 常见于正常人运动、兴奋、激动、吸烟、饮酒和喝浓茶后。也可见于发热、休克、贫血、甲亢、心力衰竭及应用阿托品、肾上腺素、麻黄素等。
		 心率低于60次/分者（一般在40次/分以上），称为窦性心动过缓
		 。可见于长期从事重体力劳动和运动员；病理性的见于甲状腺机能低下、颅内压增高、阻塞性黄疸、以及洋地黄、奎尼丁或心得安类药物过量或中毒。
	   2:如心率低于40次/分，应考虑有房室传导阻滞。
		 如果心率在 160～220次/分，常称为阵发性心动过速。心率过快超过160次/分，或低于40次/分，大多见于心脏病病人，病人常有心悸、胸闷、心前区不适，应及早进行详细检查，以便针对病因进行治疗*/
/*	private Integer heartRateGrade;
	
	private long calorie;
	private Double mileage=0d;
	
	private String targetStep ;*/

	/*public String getTargetStep() {
		return targetStep;
	}

	public void setTargetStep(String targetStep) {
		this.targetStep = targetStep;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAvantarURL() {
		return avantarURL;
	}

	public void setAvantarURL(String avantarURL) {
		this.avantarURL = avantarURL;
	}

	public Long getSteps() {
		return steps;
	}

	public void setSteps(Long steps) {
		this.steps = steps;
	}*/

	

	/*public Long getStepsGrade() {
		return stepsGrade;
	}

	public void setStepsGrade(Long stepsGrade) {
		this.stepsGrade = stepsGrade;
	}

	public Integer getHeartRateGrade() {
		return heartRateGrade;
	}

	public void setHeartRateGrade(Integer heartRateGrade) {
		this.heartRateGrade = heartRateGrade;
	}

	public long getCalorie() {
		return calorie;
	}

	public void setCalorie(long calorie) {
		this.calorie = calorie;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}*/
	
	
}
