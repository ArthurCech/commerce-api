package io.github.arthurcech.orderscrudcommerce.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.github.arthurcech.orderscrudcommerce.entity.User;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	@NotBlank(message = "Campo name obrigatório")
	@Size(min = 2, max = 255, message = "O campo name deve ter no mínimo 2 e no máximo 255 caracteres")
	private String name;
	@NotBlank(message = "Campo email obrigatório")
	@Email
	private String email;
	@NotBlank(message = "Campo phone obrigatório")
	private String phone;
	@NotBlank(message = "Campo password obrigatório")
	@Size(max = 16, message = "O campo senha deve ter no máximo 16 caracteres")
	private String password;

	public UserDTO() {
	}

	public UserDTO(Long id, String name, String email, String phone, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
	}

	public UserDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.password = user.getPassword();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return Objects.equals(id, other.id);
	}

}
