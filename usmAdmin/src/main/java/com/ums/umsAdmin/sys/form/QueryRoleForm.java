package com.ums.umsAdmin.sys.form;

public class QueryRoleForm extends FilterForm {
	private static final long serialVersionUID = 6365431566267808871L;
	private String hints;
	private char isDefault='N';
	
	public String getHints() {
		return hints;
	}
	public void setHints(String hints) {
		this.hints = hints;
	}
	public char getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(char isDefault) {
		this.isDefault = isDefault;
	}
	
}
