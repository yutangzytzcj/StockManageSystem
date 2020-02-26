package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Export;
import model.Goods;
import model.Import;
import model.PageBean;
import util.DateUtil;
import util.StringUtil;

public class ExportDao {

	public ResultSet exportList(Connection con,PageBean pageBean,Goods goods,Export exportGoods,String s_bexpoPrice,String s_eexpoPrice,String s_bexpoDate,String s_eexpoDate) throws Exception{
		StringBuffer sb = new StringBuffer("SELECT * FROM t_goods t2,t_export t1 WHERE t1.goodsId=t2.id");
		if(StringUtil.isNotEmpty(goods.getGoodsName())){
			sb.append(" and t2.goodsName like '%"+goods.getGoodsName()+"%'");
		}
		
		if(StringUtil.isNotEmpty(s_bexpoPrice)){
			sb.append(" and expoPrice >="+s_bexpoPrice+"");
		}
		if(StringUtil.isNotEmpty(s_eexpoPrice)){
			sb.append(" and expoPrice <="+s_eexpoPrice+"");
		}
		
		if(StringUtil.isNotEmpty(s_bexpoDate)){
			sb.append(" and TO_DAYS(t1.expoDate)>=TO_DAYS('"+s_bexpoDate+"')");
		}
		if(StringUtil.isNotEmpty(s_eexpoDate)){
			sb.append(" and TO_DAYS(t1.expoDate)<=TO_DAYS('"+s_eexpoDate+"')");
		}
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		
		System.out.println(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	public ResultSet exportData(Connection con) throws Exception {
		String sql = "SELECT t2.id,goodsName,expoPrice,expoDate,expoNum,expoDesc FROM t_goods t2,t_export t1 WHERE t1.goodsId=t2.id";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeQuery();
	}
	
	public int exportCount(Connection con,Goods goods,Export exportGoods,String s_bexpoPrice,String s_eexpoPrice,String s_bexpoDate,String s_eexpoDate) throws Exception{
		StringBuffer sb = new StringBuffer("select count(*) as total from t_goods t2,t_export t1 where t1.goodsId=t2.id");
		if(StringUtil.isNotEmpty(goods.getGoodsName())){
			sb.append(" and t2.goodsName like '%"+goods.getGoodsName()+"%'");
		}
		
		if(StringUtil.isNotEmpty(s_bexpoPrice)){
			sb.append(" and expoPrice >="+s_bexpoPrice+"");
		}
		if(StringUtil.isNotEmpty(s_eexpoPrice)){
			sb.append(" and expoPrice <="+s_eexpoPrice+"");
		}
		
		if(StringUtil.isNotEmpty(s_bexpoDate)){
			sb.append(" and TO_DAYS(t1.expoDate)>=TO_DAYS('"+s_bexpoDate+"')");
		}
		if(StringUtil.isNotEmpty(s_eexpoDate)){
			sb.append(" and TO_DAYS(t1.expoDate)<=TO_DAYS('"+s_eexpoDate+"')");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	public int exportDelete(Connection con,String delIds) throws Exception{
		String sql = "delete from t_export where id in ("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	public int exportSave(Connection con,Export exportGoods) throws Exception{
		String sql = "insert t_export value(null,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1,exportGoods.getGoodsId());
		pstmt.setString(2, exportGoods.getExpoPrice());
		pstmt.setString(3, DateUtil.formatDate(exportGoods.getExpoDate(), "yyyy-MM-dd HH:mm:ss"));
		pstmt.setString(4, exportGoods.getExpoNum());
		pstmt.setString(5, exportGoods.getExpoDesc());
		return pstmt.executeUpdate();
	}
	
	public int exportModify(Connection con ,Export exportGoods) throws Exception{
		String sql = "update t_export set goodsId=?,expoPrice=?,expoDate=?,expoNum=?,expoDesc=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1,exportGoods.getGoodsId());
		pstmt.setString(2, exportGoods.getExpoPrice());
		pstmt.setString(3, DateUtil.formatDate(exportGoods.getExpoDate(), "yyyy-MM-dd HH:mm:ss"));
		pstmt.setString(4, exportGoods.getExpoPrice());
		pstmt.setString(5, exportGoods.getExpoDesc());
		pstmt.setInt(6, exportGoods.getId());
		return pstmt.executeUpdate();
	}
	
	public boolean getGoodsByExportId(Connection con,String delIds) throws Exception{
		String sql = "select * from t_export where goodsId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, delIds);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
}
