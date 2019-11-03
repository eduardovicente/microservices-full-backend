package com.vicentemartinez.user.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginRequestModel {

	@NotNull(message = "Email cannot be null")
	@Email(message = "Email format is not correct")
	private String email;

	@NotNull(message = "Password cannot be null")
	@Size(min = 8, max = 16, message = "Password must be equal or grater than 8 characters and less than 16 charaters")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
