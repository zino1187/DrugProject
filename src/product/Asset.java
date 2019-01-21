package product;

import store.Store;

public class Asset {
	private int asset_id;
	private Store store;
	private Product product;
	private int price;
	private int stock;
	private String memo;
	
	public int getAsset_id() {
		return asset_id;
	}
	public void setAsset_id(int asset_id) {
		this.asset_id = asset_id;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	
}
