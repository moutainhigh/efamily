package com.winterframework.efamily.dto;

/** 
* @ClassName: StepGoalStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-23 下午5:19:44 
*  
*/
public class StepGoalStruc {
	private Long id;
	private Long targetStep;
	private Long currentStep;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTargetStep() {
		return targetStep;
	}
	public void setTargetStep(Long targetStep) {
		this.targetStep = targetStep;
	}
	public Long getCurrentStep() {
		return currentStep;
	}
	public void setCurrentStep(Long currentStep) {
		this.currentStep = currentStep;
	}
}
