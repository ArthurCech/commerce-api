package io.github.arthurcech.orderscrudcommerce.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import io.github.arthurcech.orderscrudcommerce.entity.Category;
import io.github.arthurcech.orderscrudcommerce.entity.Product;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	@NotBlank(message = "Campo name obrigatório")
	@Size(min = 2, max = 255, message = "O campo name deve ter no mínimo 2 e no máximo 255 caracteres")
	private String name;
	@NotBlank(message = "Campo description obrigatório")
	@Size(min = 10, max = 255, message = "O campo description deve ter no mínimo 10 e no máximo 255 caracteres")
	private String description;
	@NotNull(message = "Campo price obrigatório")
	@Positive(message = "O campo price deve ter um valor positivo")
	private Double price;
	@NotBlank(message = "Campo description obrigatório")
	private String imgUrl;
	@NotNull(message = "Campo categories obrigatório")
	@Size(min = 1, message = "O campo categories deve ter pelo menos 1 item")
	@Valid
	private List<CategoryDTO> categories = new ArrayList<>();

	public ProductDTO() {
	}

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.imgUrl = product.getImgUrl();
	}

	public ProductDTO(Product product, Set<Category> categories) {
		this(product);
		categories.forEach(category -> this.categories.add(new CategoryDTO(category)));
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

}
