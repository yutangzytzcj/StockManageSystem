package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.StringUtil;
import model.PageBean;
import model.Provider;

public class ProviderDao {

	public ResultSet providerList(Connection con,PageBean pageBean,Provider provider) throws Exception{
		StringBuffer sb = new StringBuffer("select * from t_provider");
		if(provider!=null && StringUtil.isNotEmpty(provider.getProName())){
			sb.append(" and proName like '%"+provider.getProName()+"%'");
		}
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}
	
	public int providerCount(Connection con,Provider provider) throws Exception{
		StringBuffer sb = new StringBuffer("select count(*) as total from t_provider");
		if(provider!=null && StringUtil.isNotEmpty(provider.getProName())){
			sb.append(" and proName like '%"+provider.getProName()+"%'");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}

	public int providerDelete(Connection con,String delIds) throws Exception{
		String sql = "delete from t_provider where id in ("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	public int providerSave(Connection con,Provider provider) throws Exception{
		String sql = "insert t_provider value(null,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1,provider.getProId());
		pstmt.setString(2, provider.getProName());
		pstmt.setString(3, provider.getLinkman());
		pstmt.setString(4, provider.getProPhone());
		pstmt.setString(5, provider.getProDesc());
		return pstmt.executeUpdate();
	}
	
	public int providerModify(Connection con ,Provider provider) throws Exception{
		String sql = "update t_provider set proId=?,proName=?,linkman=?,proPhone=?,proDesc=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1,provider.getProId());
		pstmt.setString(2, provider.getProName());
		pstmt.setString(3, provider.getLinkman());
		pstmt.setString(4, provider.getProPhone());
		pstmt.setString(5, provider.getProDesc());
		pstmt.setInt(6, provider.getId());
		return pstmt.executeUpdate();
	}
}
