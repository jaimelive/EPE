package JPA.Entities;

import DataBean.ItemBean;
import DataBean.OrderBean;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
public class OrderEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private ClientEntity client;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<ProductEntity> productToOrder;

	@ManyToOne
	private UserEntity user;

	public OrderEntity setDateOrdered(final Timestamp dateOrdered) {
		this.dateOrdered = dateOrdered;
		return this;
	}

	public OrderEntity setDateShipped(final Timestamp dateShipped) {
		this.dateShipped = dateShipped;
		return this;
	}

	@Column(nullable = false)
	private Timestamp dateOrdered;

	@Column(nullable = false)
	private Timestamp dateShipped;

	@Column(nullable = false)
	private Boolean shipped;

	@Column(nullable = false)
	private double totalCost;

	public OrderEntity setId(final int id) {
		this.id = id;
		return this;
	}

	public Timestamp getDateShipped() {
		return dateShipped;
	}

	public Boolean getShipped() {
		return shipped;
	}

	public Timestamp getDateOrdered() {

		return dateOrdered;
	}

	public int getId() {
		return id;
	}

	public ClientEntity getClient() {
		return client;
	}

	public List<ProductEntity> getProductToOrder() {
		return productToOrder;
	}

	public UserEntity getUser() {
		return user;
	}

	public Boolean isShipped() {
		return shipped;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public OrderEntity setClient(final ClientEntity client) {
		this.client = client;
		return this;
	}

	public OrderEntity setProductToOrder(final List<ProductEntity> productToOrder) {
		this.productToOrder = productToOrder;
		return this;
	}

	public OrderEntity setUsername(final UserEntity user) {
		this.user = user;
		return this;
	}

	public OrderEntity setShipped(final Boolean shipped) {
		this.shipped = shipped;
		return this;
	}

	public OrderEntity setTotalCost(final double totalCost) {
		this.totalCost = totalCost;
		return this;
	}


	public OrderEntity setUser(final UserEntity user) {
		this.user = user;
		return this;
	}

	public OrderBean toBean() {
		OrderBean temp = new OrderBean();

		temp.setClient(this.getClient().toBean())
				.setDateOrder(this.dateOrdered)
				.setDateShipped(this.getDateShipped())
				.setId(this.getId())
				.setTotalCost(this.totalCost)
				.setUser(this.getUser().toBean());

		List<ItemBean> var = null;

		for(ProductEntity i : this.getProductToOrder()){
			var.add(i.getBean());
		}

		temp.setItemList(var);
		return temp;

	}

	public OrderEntity toEntity(OrderBean order) {

		ClientEntity c = new ClientEntity().toEntity(order.getClient());

		UserEntity user = new UserEntity()
				.toEntity(order.getUser());


		List<ProductEntity> inventory = null;

		for (ItemBean i : order.getItemList()) {
			inventory.add(new ProductEntity().toEntity(i, new PlantsEntity().setType(i.getPlanta().getType())));
		}

		this.setProductToOrder(inventory)
				.setClient(c)
				.setDateOrdered(order.getDateOrder())
				.setDateShipped(order.getDateShipped())
				.setShipped(order.getShipped())
				.setUser(user);
		return this;

	}

}