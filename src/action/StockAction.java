package action;

import java.sql.Connection;

import model.Goods;
import model.PageBean;
import model.Provider;
import model.Stock;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import util.DbUtil;
import util.JsonUtil;
import util.ResponseUtil;
import util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;

import dao.GoodsDao;
import dao.StockDao;

public class StockAction extends ActionSupport{

	private String page;
	private String rows;
	private Stock stock;
	private Goods goods;
	private String s_bimpoPrice;
	private String s_eimpoPrice;
	private String s_bexpoPrice;
	private String s_eexpoPrice;
	private String delIds;
	private String id;
	private String s_goodsName;
	
	
	
	public String getS_goodsName() {
		return s_goodsName;
	}
	public void setS_goodsName(String s_goodsName) {
		this.s_goodsName = s_goodsName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDelIds() {
		return delIds;
	}
	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getS_bimpoPrice() {
		return s_bimpoPrice;
	}
	public void setS_bimpoPrice(String s_bimpoPrice) {
		this.s_bimpoPrice = s_bimpoPrice;
	}
	public String getS_eimpoPrice() {
		return s_eimpoPrice;
	}
	public void setS_eimpoPrice(String s_eimpoPrice) {
		this.s_eimpoPrice = s_eimpoPrice;
	}
	public String getS_bexpoPrice() {
		return s_bexpoPrice;
	}
	public void setS_bexpoPrice(String s_bexpoPrice) {
		this.s_bexpoPrice = s_bexpoPrice;
	}
	public String getS_eexpoPrice() {
		return s_eexpoPrice;
	}
	public void setS_eexpoPrice(String s_eexpoPrice) {
		this.s_eexpoPrice = s_eexpoPrice;
	}


	DbUtil dbUtil = new DbUtil();
	StockDao stockDao = new StockDao();
	GoodsDao goodsDao = new GoodsDao();
	
	@Override
	public String execute() throws Exception{
		Connection con = null;
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		try {
			goods = new Goods();
			if(stock==null){
				stock = new Stock();
			}
			if(s_goodsName!=null){
				goods.setGoodsName(s_goodsName);
			}
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(stockDao.stockList(con, pageBean,goods,s_bimpoPrice,s_eimpoPrice,s_bexpoPrice,s_eexpoPrice));
			int total = stockDao.stockCount(con,goods,s_bimpoPrice,s_eimpoPrice,s_bexpoPrice,s_eexpoPrice);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String delete()throws Exception{
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int delNums=stockDao.stockDelete(con, delIds);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "ɾ��ʧ��");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String save()throws Exception{
		if(StringUtil.isNotEmpty(id)){
			stock.setId(Integer.parseInt(id));
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(id)){
				saveNums=stockDao.stockModify(con, stock);
			}else{
				saveNums=stockDao.stockSave(con, stock);
			}
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "����ʧ��");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
