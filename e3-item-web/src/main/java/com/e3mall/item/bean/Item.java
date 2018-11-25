package com.e3mall.item.bean;

import com.e3mall.pojo.TbItem;

public class Item extends TbItem {
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 取图片数组
	 * @return
	 */
	public String[] getImages(){		
		String images = this.getImage();
		if(null != images && !"".equals(images)){
			String[] imagesArr = images.split(",");
			return imagesArr;
		}
		return null;
	}
	/**
	 * 构造函数
	 * @param tbItem
	 */
	public Item(TbItem tbItem) {
		this.setBarcode(tbItem.getBarcode());
		this.setCid(tbItem.getCid());
		this.setCreated(tbItem.getCreated());
		this.setId(tbItem.getId());
		this.setImage(tbItem.getImage());
		this.setNum(tbItem.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(tbItem.getStatus());
		this.setTitle(tbItem.getTitle());
		this.setUpdated(tbItem.getUpdated());
	}

}
